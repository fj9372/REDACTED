import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container} from 'reactstrap';
import './css/ingredients.css'

class History extends Component
{
    constructor(props){
        super(props);
        this.state = {
            history: [],
            showInfo: false,
            opened: false,
            hoverHistory: null,
        }
    }

    showInformation = (item) => {
        if(!this.state.opened){
            this.setState({showInfo:true, hoverHistory:item})
        }
    }

    closeInformation = () => {
        if(!this.state.opened){
            this.setState({showInfo:false, hoverHistory: ''})
        }
    }

    openInformation = () => {
        this.setState({opened: !this.state.opened})
    }

    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/history');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({history:data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    componentDidMount(){
        this.fetchData();
    }

    render()
    {
        
    return(
        <div>
            <h1>History</h1>
            <Container >
                <Row>
                {this.state.history.map((items) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} key = {items.name}>
                    <InputGroup>
                        <InputGroupText style={{ marginBottom: '10px' }} onMouseEnter={()=>this.showInformation(items)} onMouseLeave={()=>this.closeInformation()} onClick = {()=>this.openInformation()} className = "ingredients">{items.date.trim().substring(0,11)}</InputGroupText>
                    </InputGroup>
                    <div style={{ display: 'flex', flexDirection: 'row' }}>
                    {items === this.state.hoverHistory ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Information</h3>
                        <h4>Calories Consumed: {items.caloriesConsumed}</h4>
                        <h4>Target Calories: {items.caloriesTarget}</h4>
                        <h4>Weight: {items.weight}</h4>
                        </div>
                         : null}
                    {items === this.state.hoverHistory ? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Workouts</h3>
                        {items.workouts.map((workout) => (
                            <ul>
                            <li key={workout}>
                                <h4>{workout.startTime} to {workout.endTime}</h4>
                                <h4>{workout.calories} Calories burned</h4>
                            </li>
                            </ul>
                        ))}
                        </div>
                         : null}
                    {items === this.state.hoverHistory? 
                        <div className="RecipeIngredient" style={{ display: 'inline-block', marginRight: '20px' }}>
                        <h3>Meals</h3>
                        {items.meals.map((meal) => (
                            <div>
                            <ul>
                                <li key={meal}>
                                    <h4>{meal.name}</h4>
                                </li>
                            </ul>
                            </div>
                        ))}
                        </div>
                         : null}
                        </div>
                    </Col>
                ))}
                </Row>
            </Container>
        </div>
        )
    }
}

export default History;