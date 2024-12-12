// src/components/DeviceUpdatePage.js

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import HomeButton from "../HomeButton";

const DeviceUpdatePage = () => {
    const [devices, setDevices] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchDevices = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/all`);
                setDevices(response.data);
            } catch (error) {
                console.error('Error fetching devices:', error);
            }
        };

        fetchDevices();
    }, []);

    return (
        <div>
            <HomeButton/>
            <h2>Select a Device to Update</h2>
            <ul>
                {devices.map(device => (
                    <li key={device.id}>
                        <strong>{device.description}</strong> - Address: {device.address}, MHEC: {device.mhec}
                        <button className="btn btn-secondary ml-2" onClick={() => navigate(`/update-device/${device.id}`)}>
                            Update
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DeviceUpdatePage;
