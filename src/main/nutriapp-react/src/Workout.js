import { Component } from "react";
import {Button, Container, Col, Row} from 'reactstrap';
import AddWorkout from "./AddWorkout";
import './css/workout.css';

class Workout extends Component
{
    constructor(props){
        super(props);
        this.state = {
            workoutInfo: [],
            showModal: false,
        }
    }


    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/workout');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({workoutInfo: data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    componentDidMount(){
        this.fetchData();
    }


    addWorkout = async (intensity, startDate, endDate, startTime, endTime) => {
        try {
            const start =`${startDate} ${startTime}`
            const end = `${endDate} ${endTime}`
            const response = await fetch(`http://localhost:8080/${intensity}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    startTime: start,
                    endTime: end
                }),
            });
            const data = await response.json();
            if (data === true) {
                alert("Workout Created!");
                this.fetchData()
            } else {
                alert("Unable to Create");
            }
        } catch (error) {
            console.log(error);
        }
        this.closeModal();
    }

    closeModal = () => {
        this.setState({showModal: false});
    }

    render()
    {

    return(
        <div>
            <h1>Workouts</h1>
            <div className="create">
                <Button onClick = {()=>this.setState({showModal:true})} color="success" >Create New</Button>
            </div>
            <Container>
            <Row>
                {this.state.workoutInfo.map((items) => {
                    const startTime = new Date(items.startTime);
                    const endTime = new Date(items.endTime);
                    const timeDifference = endTime.getTime() - startTime.getTime();
                    const hours = Math.floor(timeDifference / (1000 * 60 * 60));
                    const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
                    return (
                        <Col key={items.workoutID} xs={12} sm={8} md={6} lg={4}>
                            <div className="info">
                                <h4>{hours} hours {minutes} minutes</h4>
                                <h4>{items.calories} Calories burned</h4>
                            </div>
                            <div className="info-two">
                                <h4>From {items.startTime} to {items.endTime}</h4>
                            </div>
                        </Col>
                    );
                })}
            </Row>
            </Container>
            <AddWorkout cancel = {this.closeModal} showHide = {this.state.showModal} callback = {this.addWorkout}></AddWorkout>
        </div>
        )
    }
}

export default Workout;