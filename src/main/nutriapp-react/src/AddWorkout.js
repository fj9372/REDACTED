import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, InputGroup, InputGroupText, Input } from 'reactstrap';
import './css/workout.css'

class AddWorkout extends Component {
    constructor(props){
        super(props);
        this.state = {
            intensity: '',
            startTime: '',
            endTime: '',
            startDate: '',
            endDate: ''
        }
    }

    toggle = () => {
        this.props.cancel();
    }

    saveChanges = () =>
    {
        if(!this.state.intensity || !this.state.startTime || !this.state.endTime || !this.state.startDate || !this.state.endDate){
            alert("Please fill out all fields")
            return
        }
        else if(this.state.startDate > this.state.endDate){
            alert("Start Date cannot be later than End Date")
            return
        }
        else if(this.state.startDate === this.state.endDate && this.state.startTime > this.state.endTime){
            alert("Start Time cannot be later than End Time")
            return
        }
        this.props.callback(this.state.intensity, this.state.startDate, this.state.endDate, this.state.startTime, this.state.endTime);
    }

    updateStartDate = (e) => {
        this.setState({startDate: e.target.value})
    }

    updateEndDate = (e) => {
        this.setState({endDate: e.target.value})
    }

    updateStartTime = (e) => {
        this.setState({startTime: e.target.value})
    }

    updateEndTime = (e) => {
        this.setState({endTime: e.target.value})
    }

    updateIntensity = (e) => {
        this.setState({intensity: e.target.value})
    }

    render()
    {
  
    return(
        <div>
            <Modal isOpen={this.props.showHide} toggle = {this.toggle}>
            <ModalHeader toggle = {this.toggle}>{this.props.clubName}</ModalHeader>
            <ModalBody>
                <InputGroup>
                <InputGroupText>Intensity</InputGroupText>
                <select class="form-select" onChange = {this.updateIntensity}>
                    <option></option>
                    <option value="low">Low(5 Calories/min)</option>
                    <option value="medium">Medium(7 Calories/min)</option>
                    <option value="high">High(10 Calories/min)</option>
                </select>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Date Started</InputGroupText>
                <Input type="date" onChange = {this.updateStartDate}></Input>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Time Started</InputGroupText>
                <Input type="time" onChange = {this.updateStartTime}></Input>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Date Ended</InputGroupText>
                <Input type="date" onChange = {this.updateEndDate}></Input>
                </InputGroup>
                <InputGroup>
                <InputGroupText>Time Ended</InputGroupText>
                <Input type="time" onChange = {this.updateEndTime}></Input>
                </InputGroup>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick = {this.toggle}>Cancel</Button>
                <Button color="primary" onClick = {this.saveChanges}>Save</Button>
            </ModalFooter>
            </Modal>
        </div>
        )
    }
}

export default AddWorkout;
