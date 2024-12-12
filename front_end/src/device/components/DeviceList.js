// src/components/DeviceList.js
import React, { useState } from 'react';
import {useNavigate} from "react-router-dom";
import HomeButton from "../HomeButton";

const DeviceList = () => {
    const navigate = useNavigate();
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchDevices = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await fetch(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/all`);
            if (!response.ok) {
                throw new Error('Failed to fetch devices');
            }
            const data = await response.json();
            setDevices(data); // Assuming your API returns an array of devices
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <HomeButton />
            <h2>Device List</h2>
            <button className="btn btn-primary" onClick={fetchDevices}>Load Devices</button>
            {loading && <div>Loading devices...</div>}
            {error && <div>Error: {error}</div>}
            {devices.length > 0 ? (
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th>Address</th>
                        <th>Description</th>
                        <th>Mhec</th>
                    </tr>
                    </thead>
                    <tbody>
                    {devices.map(device => (
                        <tr key={device.id}>
                            <td>{device.address}</td>
                            <td>{device.description}</td>
                            <td>{device.mhec}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <div>No users found.</div>
            )}
        </div>
    );
};

export default DeviceList;
