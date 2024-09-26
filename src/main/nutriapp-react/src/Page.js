import React, { useState } from 'react';
import { Button, InputGroup, Input, InputGroupText } from 'reactstrap';
import { Link, useNavigate} from 'react-router-dom';

const Page = ({ logIn }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const fetchData = async (e) => {
        e.preventDefault();
        if (!username || !password) {
            alert('Please fill out all fields');
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name: username,
                    password: password,
                }),
            });
            const data = await response.json();
            if (data === true) {
                logIn();
                navigate('/ingredients')
            } else {
                alert('Wrong username or password');
            }
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="centered-form">
            <form onSubmit={fetchData}>
                <h1>NUTRIAPP Login</h1>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Username</InputGroupText>
                    <Input
                        placeholder="Enter username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={{ marginBottom: '10px' }}
                    />
                </InputGroup>
                <InputGroup>
                    <InputGroupText style={{ marginBottom: '10px' }}>Password</InputGroupText>
                    <Input
                        placeholder="Enter password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{ marginBottom: '10px' }}
                        type="password"
                    />
                </InputGroup>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Link to="/create">New User?</Link>
                    <Button type="submit">Continue</Button>
                </div>
                <div style={{ display: 'flex', justifyContent: 'right', fontSize: '9px' }}>
                    By signing in you agree to our lack of Term and Privacy
                </div>
            </form>
        </div>
    );
};

export default Page;
