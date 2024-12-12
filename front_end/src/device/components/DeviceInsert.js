import React, { useState, useEffect } from 'react';
import axios from 'axios';
import HomeButton from "../HomeButton";

const DeviceInsert = () => {
    const [description, setDescription] = useState('');
    const [address, setAddress] = useState('');
    const [mhec, setMhec] = useState('');
    const [userId, setUserId] = useState('');
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/all`);
                setUsers(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        fetchUsers();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const device = {
                userId:  userId,
                description:description,
                address:address,
                mhec:mhec,
            };

            const response = await axios.post(`${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/insert`, device);
            console.log('Backend URL:', `${process.env.REACT_APP_BACKEND_DEVICE_URL}/devices/insert`);
            console.log(device);

            alert(`Device created with ID: ${response.data}`);
        } catch (error) {
            console.error('Error inserting device:', error);

            alert('Error inserting device');
        }
    };

    return (
        <div>
            <HomeButton />
            <form onSubmit={handleSubmit}>
                <label>Description:</label>
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />

                <label>Address:</label>
                <input type="text" value={address} onChange={(e) => setAddress(e.target.value)} />

                <label>Max Hourly Energy Consumption:</label>
                <input type="number" value={mhec} onChange={(e) => setMhec(e.target.value)} />

                <label>User ID:</label>
                <select value={userId} onChange={(e) => setUserId(e.target.value)}>
                    <option value="">Select a user</option>
                    {users.map(user => (
                        <option key={user.id} value={user.id}>
                            {user.name} (ID: {user.id})
                        </option>
                    ))}
                </select>

                <button type="submit">Insert Device</button>
            </form>
        </div>
    );
};

export default DeviceInsert;
