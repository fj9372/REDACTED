import { Component } from "react";
import {Input, Button} from 'reactstrap';
import './css/goal.css'

class Goal extends Component
{
    constructor(props){
        super(props);
        this.state = {
            goalInfo: [],
            userInfo: [],
            weight: 0,
            targetWeight:0,
            changesMade: false,
        }
    }


    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/goal');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({goalInfo:data.goal})

            const userUrl = new URL('http://localhost:8080/user');
            const userResponse = await fetch(userUrl, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const userData = await userResponse.json();
            this.setState({userInfo:userData, weight:userData.weight, targetWeight:userData.targetWeight})
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

    updateWeight = (e) => {
        this.setState({ 
            weight: e.target.value   
        }, () => {
            if(parseFloat(this.state.weight) !== this.state.userInfo.weight){
                this.setState({changesMade:true})
            }
            else{
                this.setState({changesMade:false})
            }
        });
    }

    updateTargetWeight = (e) => {
        this.setState({ 
            targetWeight: e.target.value   
        }, () => {
            if(parseFloat(this.state.targetWeight) !== this.state.userInfo.targetWeight){
                this.setState({changesMade:true})
            }
            else{
                this.setState({changesMade:false})
            }
        });
    }

    reset = () =>{
        this.setState({
            weight: this.state.userInfo.weight,
            targetWeight: this.state.userInfo.targetWeight,
            changesMade:false
        })
    }

    saveChanges = async () =>{
        try {
            if(!this.state.weight || !this.state.targetWeight){
                alert('Please fill the fields')
                return
            }
            const response = await fetch(`http://localhost:8080/goal`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    weight: this.state.weight,
                    targetWeight: this.state.targetWeight
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
        this.setState({changesMade:false})
    }

    render()
    {
    return(
        <div>
            <h1>Goal Information</h1>
            <div className="weight-info">
                <h4>Current Goal: {this.state.goalInfo.goalType}</h4>
                <h4>Current Weight</h4>
                <Input value = {this.state.weight} onChange = {this.updateWeight} type="number"></Input>
                <h4>Target Weight</h4>
                <Input value = {this.state.targetWeight} onChange = {this.updateTargetWeight} type="number"></Input>
                <h4>Nutrition Information</h4>
                <textarea value={
                    `Target Calories(Cal): ${this.state.goalInfo && this.state.goalInfo.targetCalories !== undefined ? this.state.goalInfo.targetCalories.toFixed(2) : ''}
                    \nTarget Carbohydrates(g): ${this.state.goalInfo && this.state.goalInfo.targetCarbs !== undefined ? this.state.goalInfo.targetCarbs.toFixed(2) : ''}
                    \nTarget Fat(g): ${this.state.goalInfo && this.state.goalInfo.targetFat !== undefined ? this.state.goalInfo.targetFat.toFixed(2) : ''}
                    \nTarget Fiber(g): ${this.state.goalInfo && this.state.goalInfo.targetFiber !== undefined ? this.state.goalInfo.targetFiber.toFixed(2) : ''}
                    \nTarget Protein(g): ${this.state.goalInfo && this.state.goalInfo.targetProtein !== undefined ? this.state.goalInfo.targetProtein.toFixed(2) : ''}`
                } readOnly={true}/>
                <div style={{ 
                    display: 'flex', 
                    justifyContent: 'space-between', 
                    marginTop: '10px', 
                    opacity: this.state.changesMade ? 1 : 0, 
                    transform: `translateY(${this.state.changesMade ? '0' : '50px'})`,
                    transition: 'opacity 0.5s ease, transform 0.5s ease'
                }}>
                    <Button type="submit" color="secondary" onClick={() => this.reset()}>Reset</Button>
                    <Button type="submit" color="success" onClick={() => this.saveChanges()}>Save Changes</Button>
                </div>
            </div>
        </div>
        )
    }
}

export default Goal;