import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container, Button} from 'reactstrap';
import EditIngredients from "./EditIngredients";
import AddIngredients from "./AddIngredients"
import './css/ingredients.css'
import Notifaction from "./notifactions";
class Ingredients extends Component
{
    constructor(props){
        super(props);
        this.state = {
            ingredients: [],
            showModal: false,
            showAdd: false,
            selectIngredient: [],
            hoverIngredient: null,
            showInfo: false,
            opened: false,
            exportType: "csv"
        }
    }


    fetchData = async () => {
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

    closeModal = () => {
        this.setState({showModal: false});
    }

    closeAdd = () => {
        this.setState({showAdd: false});
    }

    setIngredients = (ingredient) => {
        this.setState({selectIngredient: ingredient, showModal:true})
    }

    updateIngredients = async (ingredientID, amount) => {
        try {
            const response = await fetch(`http://localhost:8080/ingredients`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: ingredientID,
                    stock: amount
                }),
            });
            const data = await response.json();
            if (data === true) {
                alert("Changed succesfully");
                this.fetchData()
            } else {
                alert(`Failed to change`);
            }
        } catch (error) {
            console.log(error);
        }
        this.closeModal();
    }

    addIngredient = async (amount, id, name, unit) => {
        try {
            const response = await fetch(`http://localhost:8080/ingredients`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: id,
                    stock: amount
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

    changeType = (e) => {
        this.setState({exportType: e.target.value})
    }

    export = async () => {
        try {
            const url = new URL(`http://localhost:8080/export/${this.state.exportType}`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    render()
    {
        
    return(
        <div>
            <h1>Inventory</h1>
            <div className="addNew">
            <Button onClick = {()=>this.setState({showAdd:true})} color="success" >Add New</Button>
            <span style={{marginRight: "10px"}}></span>
            <Button onClick = {()=>this.export()} color="secondary" >Export</Button>
            <select onChange = {this.changeType}>
                <option value = "csv">
                    CSV
                </option>
                <option value = "json">
                    JSON
                </option>
                <option value = "xml">
                    XML
                </option>
            </select>
            </div>
            <Container>
                <Row>
                {this.state.ingredients.map((items) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} key = {items.id}>
                    <InputGroup>
                        <InputGroupText style={{ marginBottom: '10px' }} onMouseEnter={()=>this.showInformation(items.name)} onMouseLeave={()=>this.closeInformation()} onClick = {()=>this.openInformation()}className = "ingredients">{items.name}</InputGroupText>
                        <InputGroupText style={{ marginBottom: '10px' }} className="ingredients" onClick={()=>this.setIngredients(items)}>{items.stock} {items.units.split(' ')[1]}</InputGroupText>
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
                        </div>
                         : null}
                    </Col>
                ))}
                </Row>
                <EditIngredients cancel = {this.closeModal} showHide = {this.state.showModal} ingredient = {this.state.selectIngredient} callback = {this.updateIngredients}></EditIngredients>
                <AddIngredients cancel = {this.closeAdd} showHide = {this.state.showAdd} callback = {this.addIngredient}></AddIngredients>
            </Container>
        </div>
        )
    }
}

export default Ingredients;