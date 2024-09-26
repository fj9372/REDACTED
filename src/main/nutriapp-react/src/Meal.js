import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container, Button} from 'reactstrap';
import "./css/ingredients.css"
import AddMeal from "./AddMeal";
import PrepareMeal from "./PrepareMeal";

class Meal extends Component
{
    constructor(props){
        super(props);
        this.state = {
            meals: [],
            showAdd: false,
            showPrepare: false,
            hoverMeal: null,
            selectedMeal: [],
            showInfo: false,
            opened: false
        }
    }

    showInformation = (item) => {
        if(!this.state.opened){
            this.setState({showInfo:true, hoverMeal:item})
        }
    }

    closeInformation = () => {
        if(!this.state.opened){
            this.setState({showInfo:false, hoverMeal: ''})
        }
    }

    closeAdd = () => {
        this.setState({showAdd: false});
    }

    closePrepare = () => {
        this.setState({showPrepare: false});
    }

    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/meal');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({meals:data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    openInformation = (meal) => {
        if(this.state.opened){
            this.setState({opened: !this.state.opened, selectedMeal: []})
        }
        else {
            this.setState({opened: !this.state.opened, selectedMeal:meal})
        }
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

    addMeal = async (name, recipes) => {
        try {
            const response = await fetch(`http://localhost:8080/meal`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name,
                    recipesNeeded: recipes,
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

    eatMeal = async (meal)  => {
        try {
            const response = await fetch(`http://localhost:8080/historymeal`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(meal),
            });
            const data = await response.json();
            if (data === true) {
                alert("CONSUMED succesfully");
            } else {
                alert(`Failed to add`);
            }
        } catch (error) {
            console.log(error);
        }
        this.closePrepare();
    }

    render()
    {
        
    return(
        <div>
            <h1>Meals</h1>
            <div className="addNew">
            <Button onClick = {()=>this.setState({showAdd:true})} color="success" >Add New</Button>
            </div>
            <Container >
                <Row>
                {this.state.meals.map((items) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} key = {items.name}>
                    <InputGroup>
                        <InputGroupText style={{ marginBottom: '10px' }} onMouseEnter={()=>this.showInformation(items.name)} onMouseLeave={()=>this.closeInformation()} onClick = {()=>this.openInformation(items)} className = "ingredients">{items.name}</InputGroupText>
                    </InputGroup>
                    <div style={{ display: 'flex', flexDirection: 'row' }}>
                    {items.name === this.state.hoverMeal ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Information</h3>
                        <h4>Calories(KCal/100g): {items.calories}</h4>
                        <h4>Carbohydrates(g/100g): {items.carbs}</h4>
                        <h4>Fat(g/100g): {items.fat}</h4>
                        <h4>Fiber(g/100g): {items.fiber}</h4>
                        <h4>Protein(g/100g): {items.protein}</h4>
                        </div>
                         : null}
                    {items.name === this.state.hoverMeal ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Recipes</h3>
                        {items.recipesNeeded.map((recipe) => (
                            <ul>
                            <li key={recipe.name}>
                                <h4>{recipe.name}</h4>
                            </li>
                            </ul>
                        ))}
                        </div>
                         : null}
                    {items.name === this.state.hoverMeal ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Total Ingredients</h3>
                        {items.recipesNeeded.map((recipe) => (
                            <div>
                            {recipe.ingredientsNeeded.map((ingredient) => (
                                <ul>
                                <li key={ingredient.id}>
                                    <h4>{ingredient.stock} {ingredient.units.split(' ')[1]} - {ingredient.name}</h4>
                                </li>
                                </ul>
                            ))}
                            </div>
                        ))}
                        </div>
                         : null}
                        </div>
                    </Col>
                ))}
                </Row>
                <AddMeal cancel = {this.closeAdd} showHide = {this.state.showAdd} callback = {this.addMeal}></AddMeal>
                <PrepareMeal cancel = {this.closePrepare} showHide = {this.state.showPrepare} callback = {this.eatMeal} selectedMeal = {this.state.selectedMeal}></PrepareMeal>
                <div style={{ 
                    display: 'flex', 
                    justifyContent: 'center', 
                    marginTop: '10px', 
                    opacity: this.state.selectedMeal.length !== 0 ? 1 : 0, // Adjusted condition
                    transform: `translateY(${this.state.selectedMeal.length !== 0 ? '0' : '50px'})`, // Adjusted condition
                    transition: 'opacity 0.5s ease, transform 0.5s ease'
                }}>
                    <Button onClick = {()=>this.setState({showPrepare:true})} color="success">Prepare</Button>
                </div>
            </Container>
        </div>
        )
    }
}

export default Meal;