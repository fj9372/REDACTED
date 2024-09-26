import { Component } from "react";
import {InputGroup, InputGroupText, Row, Col, Container, Button} from 'reactstrap';
import "./css/ingredients.css"
import InviteTeam from "./InviteTeam"
import CreateTeam from "./CreateTeam";

class Teams extends Component
{
    constructor(props){
        super(props);
        this.state = {
            members: [],
            workoutInfo: [],
            challengeRanking: [],
            current: '',
            showInfo: false,
            teamName: '',
            showAdd: false,
            showCreate: false,
        }
    }

    closeAdd = () => {
        this.setState({showAdd: false});
    }

    closeCreate = () => {
        this.setState({showCreate: false});
    }

    inviteMember = async (name) => {
        try {
            const response = await fetch(`http://localhost:8080/teamuser`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(name),
            });
            const data = await response.json();
            if (data === true) {
                alert("Invited succesfully");
                this.fetchData()
            } else {
                alert(`Failed to add`);
            }
        } catch (error) {
            console.log(error);
        }
        this.closeAdd();
    }

    createTeam = async (name) => {
        try {
            const response = await fetch(`http://localhost:8080/team`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(name),
            });
            const data = await response.json();
            if (data === true) {
                alert("Created succesfully");
                this.fetchData()
            } else {
                alert(`Failed to create`);
            }
        } catch (error) {
            console.log(error);
        }
        this.closeCreate();
    }

    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/team');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            console.log(data)
            this.setState({members:data, teamName:data[1]})

            const url2 = new URL('http://localhost:8080/challenge');
            const response2 = await fetch(url2, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data2 = await response2.json();
            console.log(data2)
            this.setState({challengeRanking:data2})
        } catch (error) {
            console.log(error);
        }
    }

    createChallenge = async () => {
        try {
            const url = new URL('http://localhost:8080/challenge');
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

    getWorkout = async (name) => {
        try {
            const url = new URL(`http://localhost:8080/workout/${name}`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({workoutInfo:data, current:name, showInfo:!this.state.showInfo})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    leaveTeam = async () => {
        try {
            const url = new URL('http://localhost:8080/team');
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            if(data === true){
                alert("You have left the team")
                this.fetchData()
            }
        } catch (error) {
            console.log(error);
        }
    }

    render()
    {
    const num = 0;
        
    return(
        <div>
            <h1>Teams</h1>
            <div className="addNew">
                {this.state.members.length === 0 && <Button color="success" onClick ={()=>this.setState({showCreate:true})}>Create</Button>}
                {this.state.members.length !== 0 && <Button color="success" onClick ={()=>this.setState({showAdd:true})}>Invite</Button>}
                <span style={{marginRight: "10px"}}></span>
                {this.state.members.length !== 0 && <Button color="danger" onClick = {()=>this.leaveTeam()}>Leave</Button>}
            </div>
            <h3>{this.state.teamName}</h3>
            <Container>
                <Row>
                {this.state.members.map((member, index) => (
                    <Col xs = {12} sm = {8} md = {6} lg = {12} >
                        {index % 2 === 0 ? (
                            <InputGroup>
                                <InputGroupText style={{ marginBottom: '10px' }} className = "ingredients" onClick = {()=>this.getWorkout(member)}>{member}</InputGroupText>
                            </InputGroup>                        
                        ) : (
                            null
                        )}
                        
                        {this.state.current === member && this.state.showInfo ? 
                        <div>
                        {this.state.workoutInfo.length === 0 ? (
                            <h2>No Info</h2>
                        ) : (
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
                        )}
                    </div>
                        : null
                        }
                    </Col>
                ))}
                </Row>
            </Container>
            <h3>Challenge Ranking</h3>
            {this.state.challengeRanking[0] === 'none' ? (
                    <div>
                        <h1>No Challenge Currently</h1>
                        <div className="addNew">
                        <Button onClick={() => this.createChallenge()} color="success">Create New</Button>
                        </div>
                    </div>
            ) : (
                this.state.challengeRanking.length > 0 ? (
                    <div className="ranking">
                        {this.state.challengeRanking.map((user, index) => (
                            <div key={index}>
                                    <h4>{user}</h4>
                            </div>
                        ))}
                    </div>
                ) : (
                    <h1>No one has worked out...</h1>
                )
            )}
            <InviteTeam cancel = {this.closeAdd} showHide = {this.state.showAdd} callback = {this.inviteMember}></InviteTeam>
            <CreateTeam cancel = {this.closeCreate} showHide = {this.state.showCreate} callback = {this.createTeam}></CreateTeam>
        </div>
        )
    }
}

export default Teams;