import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/ingredients.css'

class InviteTeam extends Component {
    constructor(props){
        super(props);
        this.state = {
            users: [],
            searchQuery: '',
            selectedUser: '',
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                users: [],
                searchQuery: '',
                selectedUser: '',
            })
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = async () =>
    {
        if(!this.state.selectedUser){
            alert('Please select a user')
            return
        }
        this.props.callback(this.state.selectedUser);
    }

    searchUser = async (e) => {
        try {
            if(e.target.value === ''){
                this.setState({ingredient: [], searchQuery: ''})
                return
            }
            this.setState({searchQuery: e.target.value, selectedID: 0})
            const url = new URL(`http://localhost:8080/searchuser/${e.target.value}`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({users:data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    render()
    {
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
            <ModalBody>
                <InputGroup>
                <InputGroupText>User</InputGroupText>
                <Input onChange = {this.searchUser} value={this.state.searchQuery} ></Input>
                </InputGroup>
                {this.state.users.map((item) => (
                            <div key={item} className = "searchResult" 
                            onClick = {()=>(this.setState({selectedUser: item, searchQuery:item, users: []}))}>
                                {item}
                            </div>
                ))}
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick = {this.toggle}>Cancel</Button>
                <Button color="primary" onClick = {this.saveChanges}>Add</Button>
            </ModalFooter>
            </Modal>
        </div>
        )
    }
}

export default InviteTeam;
