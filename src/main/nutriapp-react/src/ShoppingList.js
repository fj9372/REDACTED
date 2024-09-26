import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container, Button} from 'reactstrap';
import "./css/ingredients.css"

class ShoppingList extends Component
{
    constructor(props){
        super(props);
        this.state = {
            list: [],
            criteria: 'Overall Stock',
            showInfo: false,
            opened: false,
            hoverIngredient: null,
        }
    }

    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/shopping');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
            this.setState({list:data})
        } catch (error) {
            console.log(error);
        }
    }

    componentDidMount(){
        this.fetchData();
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

    openInformation = () => {
        this.setState({opened: !this.state.opened})
    }
    
    changeCriteria = async () => {
        try {
            const response = await fetch(`http://localhost:8080/shopping`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.state.criteria),
            });
            const data = await response.json();
            if (data === true) {
                this.fetchData()
                if(this.state.criteria === "Overall Stock"){
                    this.setState({criteria: "Recipe"})
                }
                else{
                    this.setState({criteria: "Overall Stock"})
                }
            } else {
                alert(`Failed to change`);
            }
        } catch (error) {
            console.log(error);
        }
    }

    render()
    {
        
    return(
        <div>
            <h1>Shopping List</h1>
            <h3>By {this.state.criteria}</h3>
            <div className="addNew">
            <Button onClick = {()=>this.changeCriteria()}>Change</Button>
            </div>
            <Container>
                <Row>
                {this.state.list.map((items) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} key = {items.id}>
                    <InputGroup>
                        <InputGroupText style={{ marginBottom: '10px' }} onMouseEnter={()=>this.showInformation(items.name)} onMouseLeave={()=>this.closeInformation()} onClick = {()=>this.openInformation()}className = "ingredients">{items.name}</InputGroupText>
                    </InputGroup>
                    {items.name === this.state.hoverIngredient ? 
                        <div className = "IngredientInfo">
                        <h3>Information</h3>
                        <h4>Serving Size: {items.units}</h4>
                        <h4>Calories(KCal/100g): {items.calories}</h4>
                        <h4>Carbohydrates(g/100g): {items.carbs}</h4>
                        <h4>Fat(g/100g): {items.fat}</h4>
                        <h4>Fiber(g/100g): {items.fiber}</h4>
                        <h4>Protein(g/100g): {items.protein}</h4>
                        {this.state.criteria === 'Overall Stock' ? 
                        <h4>You have {items.stock} {items.units.split(' ')[1]} in stock</h4> :
                        <h4>You need {items.stock} {items.units.split(' ')[1]}</h4>
                        }
                        </div>
                         : null}
                    </Col>
                ))}
                </Row>
            </Container>
        </div>
        )
    }
}

export default ShoppingList;