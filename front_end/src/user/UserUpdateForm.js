import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import HomeButton from "../device/HomeButton";

const UserUpdateForm = () => {
    const { id } = useParams(); // Get the user ID from the URL
    const navigate = useNavigate();
    const [user, setUser] = useState({
        name: '',
        email:'',
        role: '',
    });

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/${id}`); // Use environment variable
                setUser(response.data);
            } catch (error) {
                console.error('Error fetching user:', error);
            }
        };

        fetchUser();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser((prevUser) => ({ ...prevUser, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/update/${id}`, user); // Use environment variable
            alert('User updated successfully');
            navigate('/user-list'); // Redirect after successful update
        } catch (error) {
            console.error('Error updating user:', error);
            alert('Error updating user');
        }
    };

    return (
        <div>
            <HomeButton />
            <form onSubmit={handleSubmit}>
                <h2>Update User</h2>
                <label>name:</label>
                <input
                    type="text"
                    name="name"
                    value={user.name}
                    onChange={handleChange}
                    required
                />
                <label>email:</label>
                <input
                    type="email"
                    name="email"
                    value={user.email}
                    onChange={handleChange}
                    required
                />

                <label>Role:</label>
                <select
                    name="role"
                    value={user.role}
                    onChange={handleChange}
                    required
                >
                    <option value="">Select Role</option>
                    <option value="ADMIN">ADMIN</option>
                    <option value="CLIENT">CLIENT</option>
                </select>

                <button type="submit">Update User</button>
            </form>
        </div>
    );
};

export default UserUpdateForm;
