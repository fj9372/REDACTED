import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import './css/index.css';
import ReactDOM from 'react-dom/client';
import Page from './Page';
import CreateUser from './CreateUser';
import Ingredients from './Ingredients';
import Goal from './Goal';
import Workout from './Workout';
import Recipe from './Recipe';
import Meal from './Meal';
import History from './History';
import ShoppingList from './ShoppingList';
import Search from './Search';
import Teams from './Teams';
import Notifaction from './notifactions';

export default class App extends Component {
  constructor(props){
    super(props);
    this.state = {
        loggedIn: false,
    }
  }

  logIn = () => {
    console.log("hi")
    this.setState({loggedIn:true})
  }

  logOut = () => {
    this.setState({loggedIn:false})
  }

  render() {
    return (
      <BrowserRouter>
        <div>
          <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container">
                <ul className="navbar-nav">
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/ingredients" className="nav-link">Ingredients</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/goal" className="nav-link">Goal</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/workout" className="nav-link">Workout</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/recipe" className="nav-link">Recipe</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/meal" className="nav-link">Meal</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/history" className="nav-link">History</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/shopping" className="nav-link">Shopping List</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {!this.state.loggedIn ? (
                      <Link to="/search" className="nav-link">Search</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/team" className="nav-link">Teams</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {this.state.loggedIn ? (
                      <Link to="/notification" className="nav-link">Notifications</Link>
                    ) : null}
                  </li>
                  <li className="nav-item">
                    {!this.state.loggedIn ? (
                      <Link to="/login" className="nav-link">Login</Link>
                    ) : null}
                  </li>
                  {this.state.loggedIn ? (
                      <Link to="/login" className="nav-link" onClick = {()=>this.logOut()}>Logout</Link>
                    ) : null}
                </ul>
            </div>
          </nav>
          <Routes>
            <Route index element={<Page logIn={this.logIn}/>} />
            <Route path="*" element={<Page logIn={this.logIn}/>} />
            <Route path="/create" element={<CreateUser />} />
            <Route path="/ingredients" element={<Ingredients />} />
            <Route path="/goal" element={<Goal />} />
            <Route path="/workout" element={<Workout />} />
            <Route path="/recipe" element={<Recipe />} />
            <Route path="/meal" element={<Meal />} />
            <Route path="/history" element={<History />} />
            <Route path="/shopping" element={<ShoppingList />} />
            <Route path="/search" element={<Search />} />
            <Route path="/team" element={<Teams />} />
            <Route path="/notification" element={<Notifaction/>}/>
          </Routes>
        </div>
      </BrowserRouter>
    );
  }
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
