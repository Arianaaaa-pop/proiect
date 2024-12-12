// src/components/UserUpdatePage.js

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import HomeButton from "../device/HomeButton";

const UserUpdatePage = () => {
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/all`) // Update the URL if necessary
                setUsers(response.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            }
        };

        fetchUsers();
    }, []);

    return (
        <div>
            <HomeButton />
            <h2>Select a User to Update</h2>
            <ul>
                {users.map(user => (
                    <li key={user.id}>
                        <strong>{user.name}</strong> - Email: {user.email} {/* Assuming user has name and email properties */}
                        <button className="btn btn-secondary ml-2" onClick={() => navigate(`/update-user/${user.id}`)}>
                            Update
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserUpdatePage;
