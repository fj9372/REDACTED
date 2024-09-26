import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container, Button} from 'reactstrap';
import "./css/ingredients.css"
import AddRecipe from "./AddRecipe";

class Recipe extends Component
{
    constructor(props){
        super(props);
        this.state = {
            recipes: [],
            showAdd: false,
            selectIngredient: [],
            hoverIngredient: null,
            showInfo: false,
            opened: false
        }
    }

    showInformation = (item) => {
        if(!this.state.opened){
            this.setState({showInfo:true, hoverIngredient:item})
        }
    }

    closeInformation = () => {
        if(!this.state.opened){
            this.setState({showInfo:false, hoverIngredient: ''})
        }
    }

    closeAdd = () => {
        this.setState({showAdd: false});
    }

    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/recipe');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({recipes: data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    addRecipe = async (name, recipes, instruction) => {
        try {
            const response = await fetch(`http://localhost:8080/recipe`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name,
                    ingredientsNeeded: recipes,
                    prepinstructions: instruction
                }),
            });
            const data = await response.json();
            if (data === true) {
                alert("Added succesfully");
                this.fetchData()
            } else {
                alert(`Failed to add`);
            }
        } catch (error) {
            console.log(error);
        }
        this.closeAdd();
    }

    openInformation = () => {
        this.setState({opened: !this.state.opened})
    }
    
    undo = async (e) => {
        if (e.key == 'z' && e.ctrlKey) {
            try {
                const url = new URL('http://localhost:8080/undo');
                const response = await fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                }).then(this.fetchData());
            } catch (error) {
                console.log(error);
            }
        }
    }

    componentDidMount(){
        this.fetchData();
        document.addEventListener("keydown", this.undo)
    }
    
    componentWillUnmount = () => {
        document.removeEventListener("keydown", this.undo)
    }

    render()
    {
        
    return(
        <div>
            <h1>Recipes</h1>
            <div className="addNew">
            <Button onClick = {()=>this.setState({showAdd:true})} color="success" >Add New</Button>
            </div>
            <Container >
                <Row>
                {this.state.recipes.map((items) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} key = {items.name}>
                    <InputGroup>
                        <InputGroupText style={{ marginBottom: '10px' }} onMouseEnter={()=>this.showInformation(items.name)} onMouseLeave={()=>this.closeInformation()}  onClick = {()=>this.openInformation()} className = "ingredients">{items.name}</InputGroupText>
                    </InputGroup>
                    <div style={{ display: 'flex', flexDirection: 'row' }}>
                    {items.name === this.state.hoverIngredient ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Information</h3>
                        <h4>Calories(KCal/100g): {items.calories}</h4>
                        <h4>Carbohydrates(g/100g): {items.carbs}</h4>
                        <h4>Fat(g/100g): {items.fat}</h4>
                        <h4>Fiber(g/100g): {items.fiber}</h4>
                        <h4>Protein(g/100g): {items.protein}</h4>
                        </div>
                         : null}
                    {items.name === this.state.hoverIngredient ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Ingredients</h3>
                        {items.ingredientsNeeded.map((ingredient) => (
                            <ul>
                            <li key={ingredient.id}>
                                <h4>{ingredient.stock} {ingredient.units.split(' ')[1]} - {ingredient.name}</h4>
                            </li>
                            </ul>
                        ))}
                        </div>
                         : null}
                    {items.name === this.state.hoverIngredient ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Instructions</h3>
                        <textarea value = {items.prepInstructions}></textarea>
                        </div>
                         : null}
                        </div>
                    </Col>
                ))}
                </Row>
                <AddRecipe cancel = {this.closeAdd} showHide = {this.state.showAdd} callback = {this.addRecipe}></AddRecipe>
            </Container>
        </div>
        )
    }
}

export default Recipe;