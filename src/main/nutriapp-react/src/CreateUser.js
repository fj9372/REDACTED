import React, { useState } from "react";
import { Button, InputGroup, Input, InputGroupText } from 'reactstrap';
import { Link, useNavigate } from "react-router-dom";

const CreateUser = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [height, setHeight] = useState('');
    const [weight, setWeight] = useState('');
    const [targetWeight, setTargetWeight] = useState('');
    const [birthday, setBirthday] = useState('');
    const navigate = useNavigate();


    const fetchData = async (e) => {
        e.preventDefault();
        if (!username || !password || !height || !height || !targetWeight || !birthday) {
            alert("Please fill out all fields");
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: username,
                    password: password,
                    height: height,
                    weight: weight,
                    targetWeight: targetWeight,
                    birthday: birthday
                }),
            });
            const data = await response.json();
            if (data === true) {
                navigate('/login')
            } else {
                alert("Username already taken");
            }
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="centered-form">
            <form onSubmit={fetchData}>
                <h1>Create User</h1>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Username</InputGroupText>
                    <Input
                        placeholder="Enter username"
                        value={username}
                        onChange={(e)=> setUsername(e.target.value)}
                        style={{ marginBottom: '10px' }}
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Password</InputGroupText>
                    <Input
                        placeholder="Enter password"
                        value={password}
                        onChange={(e)=> setPassword(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="password"
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Height</InputGroupText>
                    <Input
                        placeholder="Enter height"
                        value={height}
                        onChange={(e)=> setHeight(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="number"
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Current Weight</InputGroupText>
                    <Input
                        placeholder="Enter current weight"
                        value={weight}
                        onChange={(e)=>setWeight(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="number"
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Target Weight</InputGroupText>
                    <Input
                        placeholder="Enter target weight"
                        value={targetWeight}
                        onChange={(e)=>setTargetWeight(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="number"
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Birthday</InputGroupText>
                    <Input
                        placeholder="Enter birthday"
                        name="birthday"
                        value={birthday}
                        onChange={(e)=>setBirthday(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="date"
                    />
                </InputGroup>
                <Button type="submit">Submit</Button>
                <Link to="/create"></Link>
            </form>
        </div>
    );
};

export default CreateUser;
