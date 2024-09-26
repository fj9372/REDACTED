import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/ingredients.css'

class AddMeal extends Component {
    constructor(props){
        super(props);
        this.state = {
            name: '',
            recipes: [],
            addRecipes: [],
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                name: '',
                recipes: [],
                addRecipes: [],
            })
            this.getRecipes();
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = async () =>
    {
        if(!this.state.name){
            alert('Please enter a meal name')
            return
        }
        else if(this.state.addRecipes.length === 0){
            alert('Please add at least one recipe')
            return
        }
        this.props.callback(this.state.name, this.state.addRecipes);
    }

    getRecipes = async () => {
        try {
            const url = new URL(`http://localhost:8080/recipe`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
            this.setState({recipes:data})
        } catch (error) {
            console.log(error);
        }
    }

    updateName = (e) => {
        this.setState({name: e.target.value})
    }

    removeRecipe = (recipe) => {
        const updatedRecipes = [...this.state.recipes];
        const removeRecipe = this.state.addRecipes.filter(rec => rec.name !== recipe.name);
        updatedRecipes.push(recipe);
        this.setState({
            addRecipes: removeRecipe,
            recipes: updatedRecipes
        });
    }

    addRecipe = (recipe) => {
        const updatedRecipes = [...this.state.addRecipes];
        const removeRecipe = this.state.recipes.filter(rec => rec.name !== recipe.name);
        updatedRecipes.push(recipe);
        this.setState({
            addRecipes: updatedRecipes,
            recipes: removeRecipe
        });
    }

    render()
    {
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
            <ModalBody>
                <InputGroup>
                <InputGroupText>Meal Name {this.state.unit}</InputGroupText>
                <Input onChange = {this.updateName} value = {this.state.name}></Input>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Recipes</InputGroupText>
                </InputGroup>
                {this.state.addRecipes.map((item) => (
                            <div key={item.name} className = "searchResult" 
                            onClick = {()=>this.removeRecipe(item)}>
                                {item.name}
                            </div>
                ))}
                <InputGroup>
                <InputGroupText>Add Recipes</InputGroupText>
                </InputGroup>
                {this.state.recipes.map((item) => (
                            <div key={item.name} className = "searchResult" 
                            onClick = {()=>this.addRecipe(item)}>
                                {item.name}
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

export default AddMeal;
