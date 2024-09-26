import { Component } from "react";
import './css/search.css';
import {InputGroup, InputGroupText} from 'reactstrap';
import { Link } from 'react-router-dom'; // Import Link from React Router

class Search extends Component
{
    constructor(props){
        super(props);
        this.state = {
            users: [],
            selectedUser: '',
            recipes: [],
            inventory: [],
            meals: []
        }
    }

    handleChange = (event) => {
        const selectedUser = event.target.value;
        this.setState({selectedUser: event.target.value});
        this.getInventory(selectedUser);
        this.getRecipes(selectedUser);
        this.getMeals(selectedUser);
    };

    //whatever data you need to fetch
    fetchData = async () => {
        
    }

    getAllUsers = async () => {
        try {
            const url = new URL(`http://localhost:8080/getallusers`);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({users:data})
            console.log(data)
        } catch (error) {
            console.log(error);
        }
    }

    getInventory = async (user) => {
        try{
            const newUrl = `http://localhost:8080/inventory/${user}`;
            const url = new URL(newUrl);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({inventory: data})
            console.log(data)
        } catch (error){
            console.log(error);
        }

    }
    getRecipes = async (user) => {
        try{
            const newUrl = `http://localhost:8080/recipe/${user}`;
            const url = new URL(newUrl);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({recipes: data})
            console.log(data)
        } catch (error){
            console.log(error);
        }
    }

    getMeals = async (user) => {
        try{
            const newUrl = `http://localhost:8080/meal/${user}`;
            const url = new URL(newUrl);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const data = await response.json();
            this.setState({meals: data})
            console.log(data)
        } catch (error){
            console.log(error);
        }
    }


    //called when the page loads
    componentDidMount(){
        this.getAllUsers();
    }

    render(){
        const {selectedUser, users, inventory, recipes, meals} = this.state;

        return(
            <div >
                <div className="center">
                <h1>You are in guest mode!<br/>Select a User to View:</h1>
                <select value={selectedUser} onChange={this.handleChange}>
                    <option value="">Select a user</option>
                    {users.map((user, index) => (
                        <option key={index} value={user}>
                            {user}
                        </option>))}
                </select>
                </div>
                <div className="grid-container">
                    <div className="column">
                        <h2>Ingredients</h2>
                        {inventory.map((item) => (
                        <InputGroup>
                            <InputGroupText style={{ marginBottom: '10px' }} className = "ingredients">{item.name}</InputGroupText>
                            <InputGroupText style={{ marginBottom: '10px' }} className="ingredients">{item.stock} {item.units.split(' ')[1]}</InputGroupText>
                        </InputGroup>
                        ))}
                    </div>
                    <div className="column">
                        <h2>Recipes</h2>
                        {recipes.map((item) => (
                        <InputGroup>
                            <InputGroupText style={{ marginBottom: '10px' }} className = "ingredients">{item.name}</InputGroupText>
                        </InputGroup>  
                        ))}
                    </div>
                    <div className="column">
                        <h2>Meals</h2>
                        {meals.map((item) => (
                        <InputGroup>
                            <InputGroupText style={{ marginBottom: '10px' }} className = "ingredients">{item.name}</InputGroupText>
                        </InputGroup>  
                        ))}
                    </div>
                </div>
            </div>
            )
        }
}

export default Search;