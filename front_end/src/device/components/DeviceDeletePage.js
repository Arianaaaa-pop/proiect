// src/components/DevicedeletePage.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import HomeButton from "../HomeButton";

const DeviceDeletePage = () => {
    const [devices, setDevices] = useState([]);

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

    const deleteDevice = async (id) => {
        if (window.confirm('Are you sure you want to delete this device?')) {
            try {
                await axios.delete(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/delete/${id}`);
                alert('Device deleted successfully');
                setDevices(devices.filter(device => device.id !== id)); // Update the list after deletion
            } catch (error) {
                console.error('Error deleting device:', error);
                alert('Failed to delete device');
            }
        }
    };

    return (
        <div>
           <HomeButton/>
            <h2>Select a Device to Delete</h2>
            <ul>
                {devices.map(device => (
                    <li key={device.id}>
                        <strong>{device.description}</strong> - Address: {device.address}, MHEC: {device.mhec}
                        <button className="btn btn-danger ml-2" onClick={() => deleteDevice(device.id)}>
                            Delete
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DeviceDeletePage;
