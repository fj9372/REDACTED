import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/ingredients.css'

class CreateTeam extends Component {
    constructor(props){
        super(props);
        this.state = {
            teamName: '',
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = async () =>
    {
        if(!this.state.teamName){
            alert('Please enter a team name')
            return
        }
        this.props.callback(this.state.teamName);
    }

    updateName = (e) => {
        this.setState({teamName:e.target.value})
    }

    render()
    {
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}></ModalHeader>
            <ModalBody>
                <InputGroup>
                <InputGroupText>Team Name</InputGroupText>
                <Input onChange = {this.updateName} value={this.state.teamName} ></Input>
                </InputGroup>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick = {this.toggle}>Cancel</Button>
                <Button color="primary" onClick = {this.saveChanges}>Create</Button>
            </ModalFooter>
            </Modal>
        </div>
        )
    }
}

export default CreateTeam;
