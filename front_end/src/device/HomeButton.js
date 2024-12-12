// src/components/HomeButton.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

const HomeButton = () => {
    const navigate = useNavigate();

    return (
        <button
            className="btn btn-primary"
            onClick={() => navigate('/home')}
        >
            Go to Home
        </button>
    );
};

export default HomeButton;
