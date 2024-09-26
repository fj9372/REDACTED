import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';

class EditIngredients extends Component {
    constructor(props){
        super(props);
        this.state = {
            id: '',
            ingredientName: '',
            stock: '',
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                id:this.props.ingredient.id,
                ingredientName: this.props.ingredient.name,
                stock: this.props.ingredient.stock,
            })
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = () =>
    {
        this.props.callback(this.state.id, this.state.stock);
    }

    updateAmount = (e) => {
        this.setState({stock: e.target.value})
    }

    render()
    {
  
    return(
        <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
        <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
        <ModalBody>
            <InputGroup>
            <InputGroupText>Ingredient Name</InputGroupText>
            <Input value = {this.state.ingredientName}></Input>
            </InputGroup>
            <InputGroup>
            <InputGroupText>Amount</InputGroupText>
            <Input onChange = {this.updateAmount} value = {this.state.stock} type = "number"/>
            </InputGroup>
        </ModalBody>
        <ModalFooter>
            <Button color="secondary" onClick = {this.toggle}>Cancel</Button>
            <Button color="primary" onClick = {this.saveChanges}>Save</Button>
        </ModalFooter>
        </Modal>
        )
    }
}

export default EditIngredients;
