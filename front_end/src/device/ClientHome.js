// src/components/ClientHome.js
import React, { useState, useEffect } from 'react';
import './ClientHome.css'; // Import the CSS file for styling

const ClientHome = ({ userId }) => {
    const [devices, setDevices] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDevices = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/user-devices?userId=${userId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch devices');
                }
                const data = await response.json();
                setDevices(data);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchDevices();
    }, [userId]);

    return (
        <div className="client-home-container">
            <h2 className="welcome-message">Welcome, Client</h2>
            <h3>Your Devices</h3>
            {error && <div className="error-message">{error}</div>}
            {devices.length > 0 ? (
                <ul className="device-list">
                    {devices.map((device) => (
                        <li key={device.id} className="device-item">
                            <h4>Device ID: {device.id}</h4>
                            <p><strong>Description:</strong> {device.description}</p>
                            <p><strong>Address:</strong> {device.address}</p>
                            <p><strong>MHEC:</strong> {device.mhec}</p>
                        </li>
                    ))}
                </ul>
            ) : (
                <div className="no-devices-message">No devices found.</div>
            )}
        </div>
    );
};

export default ClientHome;
