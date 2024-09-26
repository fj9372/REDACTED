import { Component } from "react";
import { Modal, ModalHeader, ModalBody, ModalFooter, Button} from 'reactstrap';
import "./css/ingredients.css"

class PrepareMeal extends Component
{
    constructor(props){
        super(props);
        this.state = {
            meal: null,
            ingredients: [],
            currentRecipe: 0,
            enoughIngredient: true
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.showHide && !prevProps.showHide) {
            this.setState({
                meal: this.props.selectedMeal,
                enoughIngredient: true
            })
            this.getIngredients();
        }
    }

    toggle = () => {
        console.log(this.state.meal.recipesNeeded[0])
        this.props.cancel();
    }

    getIngredients = async () => {
        try {
            const url = new URL('http://localhost:8080/ingredients');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
            this.setState({ingredients: data})
        } catch (error) {
            console.log(error);
        }
    }

    nextRecipe = () =>{
        if(this.state.currentRecipe < this.state.meal.recipesNeeded.length-1){
            this.setState({currentRecipe: this.state.currentRecipe + 1})
        }
    }

    lastRecipe = () =>{
        if(this.state.currentRecipe > 0){
            this.setState({currentRecipe: this.state.currentRecipe - 1})
        }
    }

    eatMeal = () =>{
        if(!this.state.enoughIngredient){
            alert("You do not have enough ingredients to CONSUME this meal")
            return
        }
        else{
            this.props.callback(this.state.meal)
        }
    }

    render()
    {
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
            <ModalBody>
                {this.state.meal !== null && (
                    <div>
                        <h2>{this.state.meal.recipesNeeded[this.state.currentRecipe].name} ({this.state.currentRecipe+1}/{this.state.meal.recipesNeeded.length})</h2>
                        <div className = "prepareInstruction">
                            <h3>Instructions</h3>
                            <h4>{this.state.meal.recipesNeeded[this.state.currentRecipe].prepInstructions}</h4>
                        </div>
                    </div>
                )}
                <div className = "IngredientRequirement">
                <h3>Ingredient Requirement</h3>
                {this.state.meal !== null && this.state.meal.recipesNeeded[this.state.currentRecipe].ingredientsNeeded.map((ingredient, index) => {
                    const ingredientExist = this.state.ingredients.findIndex(ing => ing.id === ingredient.id)
                    if (ingredientExist === -1) {
                        return <h4 key={index}>You do not have any {ingredient.name}</h4>;
                    } else { 
                        if(this.state.ingredients[ingredientExist].stock < ingredient.stock){
                            return <h4 key={index} className = "prepareIngredient">You do not have enough {ingredient.name}</h4>;
                        }
                        else{
                            return <h4 key={index} className = "prepareIngredient">You have enough {ingredient.name}</h4>;
                        }
                    }
                })}
                </div>
            </ModalBody>
            <ModalFooter className="d-flex justify-content-between">
                <Button color="primary" onClick = {()=>this.lastRecipe()}>Previous</Button>
                <Button color="secondary" onClick = {()=>this.nextRecipe()}>Next</Button>
            </ModalFooter>
            {this.state.meal !== null && this.state.currentRecipe >= this.state.meal.recipesNeeded.length-1 && (
                <Button color="info" onClick={() => this.eatMeal()} className="float-right">CONSUME!</Button>
            )}
            
            </Modal>
        </div>
        )
    }
}

export default PrepareMeal;