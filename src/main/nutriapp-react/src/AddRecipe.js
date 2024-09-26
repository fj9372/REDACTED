import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/ingredients.css'
import AddIngredients from './AddIngredients';

class AddRecipe extends Component {
    constructor(props){
        super(props);
        this.state = {
            name: '',
            ingredients: [],
            instruction: '',
            showAdd: false,
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                name: '',
                ingredients: [],
                instruction: '',
                showAddd: false
            })
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = async () =>
    {
        if(!this.state.name){
            alert('Please enter a recipe name')
            return
        }
        else if(this.state.ingredients.length === 0){
            alert('Please add at least one ingredient')
            return
        }
        else if(!this.state.ingredients){
            alert('Please enter instructions')
            return
        }
        this.props.callback(this.state.name, this.state.ingredients, this.state.instruction);
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

    addIngredient = (amount, id, name, unit) => {
        const ingredientExist = this.state.ingredients.findIndex(ingredient => ingredient.id === id);
        if (ingredientExist !== -1) {
            this.setState(prevState => ({
                ingredients: prevState.ingredients.map((ingredient) => {
                    if (id === ingredient.id) {
                        const newStock = parseInt(ingredient.stock) + parseInt(amount);
                        return { ...ingredient, stock: newStock };
                    } else {
                        return ingredient;
                    }
                })
            }));
        } else {
            const newIngredient = {
                id: id,
                name: name,
                stock: amount,
                unit: unit
            };
            this.setState(prevState => ({
                ingredients: [...prevState.ingredients, newIngredient]
            }));
        }
        this.closeAdd()
    }

    closeAdd = () => {
        this.setState({showAdd: false});
    }

    updateName = (e) => {
        this.setState({name: e.target.value})
    }

    updateInstruction = (e) => {
        this.setState({instruction: e.target.value})
    }

    removeIngredient = (id) => {
        const updatedIngredients = this.state.ingredients.filter(ingredient => ingredient.id !== id);
        this.setState({
            ingredients: updatedIngredients
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
                <InputGroupText>Recipe Name {this.state.unit}</InputGroupText>
                <Input onChange = {this.updateName} value = {this.state.name}></Input>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Ingredients</InputGroupText>
                <Button onClick = {()=>this.setState({showAdd:true})} color="success" >Add</Button>
                </InputGroup>
                {this.state.ingredients.map((item) => (
                            <div key={item.id} className = "searchResult" 
                            onClick = {()=>this.removeIngredient(item.id)}>
                                {item.stock} {item.unit.split(' ')[1]} - {item.name}
                            </div>
                ))}
                <InputGroup>
                <InputGroupText>Preparation Instructions</InputGroupText>
                <Input onChange = {this.updateInstruction} value = {this.state.instruction} type = "textarea"></Input>
                </InputGroup>
                
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick = {this.toggle}>Cancel</Button>
                <Button color="primary" onClick = {this.saveChanges}>Add</Button>
            </ModalFooter>
            
            </Modal>
            <AddIngredients cancel = {this.closeAdd} showHide = {this.state.showAdd} callback = {this.addIngredient}></AddIngredients>
        </div>
        )
    }
}

export default AddRecipe;
