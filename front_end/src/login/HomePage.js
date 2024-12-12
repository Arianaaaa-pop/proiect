import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css'; // Create a CSS file for styling

const HomePage = () => {
    const navigate = useNavigate();

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handleRegisterClick = () => {
        navigate('/register');
    };

    return (
        <div className="homepage-container">
            <h1>Welcome to the Energy Management System</h1>
            <div className="buttons-container">
                <button className="home-btn" onClick={handleLoginClick}>Login</button>
                <button className="home-btn" onClick={handleRegisterClick}>Register</button>
            </div>
        </div>
    );
};

export default HomePage;
