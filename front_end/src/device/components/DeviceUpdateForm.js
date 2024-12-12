// src/components/DeviceUpdateForm.js
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import HomeButton from "../HomeButton";

const DeviceUpdateForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [device, setDevice] = useState({
        description: '',
        address: '',
        mhec: '',
        userId: '',
    });

    useEffect(() => {
        const fetchDevice = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/${id}`);
                setDevice(response.data);
            } catch (error) {
                console.error('Error fetching device:', error);
            }
        };

        fetchDevice();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setDevice((prevDevice) => ({ ...prevDevice, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/update/${id}`, device);
            alert('Device updated successfully');
            navigate('/device-list'); // Redirect after successful update
        } catch (error) {
            console.error('Error updating device:', error);
            alert('Error updating device');
        }
    };

    return (
        <div> <HomeButton/>
        <form onSubmit={handleSubmit}>
            <h2>Update Device</h2>
            <label>Description:</label>
            <input
                type="text"
                name="description"
                value={device.description}
                onChange={handleChange}
                required
            />

            <label>Address:</label>
            <input
                type="text"
                name="address"
                value={device.address}
                onChange={handleChange}
                required
            />

            <label>Max Hourly Energy Consumption:</label>
            <input
                type="number"
                name="mhec"
                value={device.mhec}
                onChange={handleChange}
                required
            />

            <button type="submit">Update Device</button>
        </form>
        </div>
    );
};

export default DeviceUpdateForm;
