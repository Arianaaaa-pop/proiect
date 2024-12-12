import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Home.css'; // Import your CSS file

const Home = () => {
    const navigate = useNavigate();

    return (
        <div style={{ backgroundColor: '#ffe4e1', minHeight: '100vh', padding: '20px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div style={{ maxWidth: '400px', width: '100%', margin: '0 20px' }}>
                <h1 style={{ textAlign: 'center' }}>Welcome to the Energy Management System</h1>

                <div style={{ backgroundColor: '#333', color: 'white', padding: '20px', borderRadius: '10px', marginBottom: '20px' }}>
                    <h2>User Management</h2>
                    <Link to="/user-list">
                        <button className="btn btn-light" style={{ width: '100%', marginBottom: '10px' }}>View All Users</button>
                    </Link>
                    <button className="btn btn-primary" style={{ width: '100%', marginBottom: '10px' }} onClick={() => navigate('/insert-user')}>Insert User</button>
                    <button className="btn btn-secondary" style={{ width: '100%', marginBottom: '10px' }} onClick={() => navigate('/update-user')}>Update User</button>
                    <button className="btn btn-danger" style={{ width: '100%' }} onClick={() => navigate('/delete-user')}>Delete User</button>
                </div>

                <div style={{ backgroundColor: '#333', color: 'white', padding: '20px', borderRadius: '10px' }}>
                    <h2>Device Management</h2>
                    <Link to="/device-list">
                        <button className="btn btn-light" style={{ width: '100%', marginBottom: '10px' }}>View All Devices</button>
                    </Link>
                    <button className="btn btn-primary" style={{ width: '100%', marginBottom: '10px' }} onClick={() => navigate('/insert-device')}>Insert Device</button>
                    <button className="btn btn-secondary" style={{ width: '100%', marginBottom: '10px' }} onClick={() => navigate('/update-device')}>Update Device</button>
                    <button className="btn btn-danger" style={{ width: '100%' }} onClick={() => navigate('/delete-device')}>Delete Device</button>
                </div>
            </div>
        </div>
    );
};

export default Home;
