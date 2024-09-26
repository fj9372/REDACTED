import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/ingredients.css'

class AddIngredients extends Component {
    constructor(props){
        super(props);
        this.state = {
            ingredient: [],
            searchQuery: '',
            selectedID: 0,
            amount: '',
            unit: '',
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                ingredient: [],
                searchQuery: '',
                selectedID: 0,
                amount: '',
                unit: '',
            })
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = async () =>
    {
        if(this.state.selectedID === 0){
            alert('Please select an ingredient')
            return
        }
        else if(!this.state.amount){
            alert('Please enter an amount')
            return
        }
        this.props.callback(this.state.amount, this.state.selectedID, this.state.searchQuery, this.state.unit);
    }

    searchIngredient = async (e) => {
        try {
            if(e.target.value === ''){
                this.setState({ingredient: [], searchQuery: ''})
                return
            }
            this.setState({searchQuery: e.target.value, selectedID: 0, unit: '', amount:''})
            const url = new URL(`http://localhost:8080/ingredient/${e.target.value}`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
            this.setState({ingredient:data})
        } catch (error) {
            console.log(error);
        }
    }

    updateAmount = (e) => {
        this.setState({amount: e.target.value})
    }

    render()
    {
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
            <ModalBody>
                <InputGroup>
                <InputGroupText>Ingredient Name</InputGroupText>
                <Input onChange = {this.searchIngredient} value={this.state.searchQuery} ></Input>
                </InputGroup>
                {this.state.ingredient.map((item) => (
                            <div key={item[0]} className = "searchResult" 
                            onClick = {()=>(this.setState({selectedID:item[0], searchQuery:item[1], ingredient:[], unit: item[7]}))}>
                                {item[1]}
                            </div>
                ))}
                <InputGroup>
                <InputGroupText>Amount ({this.state.unit.split(' ')[1]})</InputGroupText>
                <Input type="number" onChange = {this.updateAmount} value = {this.state.amount}></Input>
                </InputGroup>
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

export default AddIngredients;
