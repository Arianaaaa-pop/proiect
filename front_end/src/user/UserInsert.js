import React, { useState } from 'react';
import axios from 'axios';
import HomeButton from "../device/HomeButton";

const UserInsert = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate role input
        if (role !== 'CLIENT' && role !== 'ADMIN') {
            alert('Role must be either CLIENT or ADMIN');
            return;
        }

        try {
            const user = {
                name: name,
                email: email,
                password: password,
                role: role,  // Ensure role is included
            };

            const response = await axios.post(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/insert`, user);
            alert(`User created with ID: ${response.data}`); // Adjust this if your response is different
        } catch (error) {
            console.error('Error inserting user:', error);
            alert('Error inserting user');
        }
    };

    return (
        <div>
            <HomeButton />
            <form onSubmit={handleSubmit}>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required // Add required validation
                />

                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required // Add required validation
                />

                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required // Add required validation
                />

                <label>Role:</label>
                <select
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    required // Add required validation
                >
                    <option value="">Select a role</option>
                    <option value="CLIENT">CLIENT</option>
                    <option value="ADMIN">ADMIN</option>
                </select>

                <button type="submit">Insert User</button>
            </form>
        </div>
    );
};

export default UserInsert;
