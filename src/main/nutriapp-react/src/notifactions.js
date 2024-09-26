import React, { Component } from 'react';
import './css/ingredients.css'

class Notifaction extends Component {
    constructor(props){
        super(props);
        this.state = {inbox: []}
    }
    
    fetchData = async () => {
        try {
            const url = new URL('http://localhost:8080/notification');
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({inbox:data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }
    componentDidMount(){
        this.fetchData();
    }
    render(){
        return (
            <div>
                {this.state.inbox.length > 0 ? (
                    this.state.inbox.map(item => (
                        <div>{item}</div>
                    ))
                ) : (
                    <h2>Inbox is empty</h2>
                )}
            </div>
        );
        
    
    
    }
    
    
    
    
    }export default Notifaction;