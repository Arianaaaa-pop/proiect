import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import HomeButton from "../device/HomeButton";

const UserList = () => {
    const navigate = useNavigate();
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchUsers = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await fetch(`${process.env.REACT_APP_BACKEND_PERSON_URL}/users/all`);
            if (!response.ok) {
                throw new Error('Failed to fetch users');
            }
            const data = await response.json();
            setUsers(data); // Assuming your API returns an array of users
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <HomeButton />
            <h2>User List</h2>
            <button className="btn btn-primary" onClick={fetchUsers}>Load Users</button>
            {loading && <div>Loading users...</div>}
            {error && <div>Error: {error}</div>}
            {users.length > 0 ? (
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>{user.role}</td>
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

export default UserList;
