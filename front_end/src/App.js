
import React, {useState} from 'react';
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';
import Home from './device/Home';
import DeviceList from './device/components/DeviceList';
import DeviceInsert from "./device/components/DeviceInsert";
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import DeviceDeletePage from "./device/components/DeviceDeletePage";
import DeviceUpdatePage from "./device/components/DeviceUpdatePage";
import DeviceUpdateForm from "./device/components/DeviceUpdateForm";
import UserList from "./user/UserList";
import UserInsert from "./user/UserInsert";
import UserUpdatePage from "./user/UserUpdatePage";
import UserUpdateForm from "./user/UserUpdateForm";
import UserDeletePage from "./user/UserDeletePage";
import Login from "./login/Login";
import HomePage from "./login/HomePage";
import Register from "./login/Register";
import ClientHome from "./device/ClientHome";


function App() {
    const [user, setUser] = useState(null);

    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<Login setUser={setUser} />} />

                    <Route
                        path="/admin"
                        element={user?.role === 'ADMIN' ? <Home /> : <Navigate to="/login" />}
                    />
                    <Route
                        path="/client"
                        element={user?.role === 'CLIENT' ? <ClientHome userId={user?.userId} /> : <Navigate to="/login" />}
                    />

                    <Route path="/register" element={<Register />} />

                    <Route path="/home" element={<Home />} />

                    <Route path="/insert-device" element={<DeviceInsert />} />
                    <Route path="/device-list" element={<DeviceList />} />
                    <Route path="/update-device" element={<DeviceUpdatePage />} />
                    <Route path="/update-device/:id" element={<DeviceUpdateForm />} />
                    <Route path="/delete-device" element={<DeviceDeletePage />} />

                    <Route path="/user-list" element={<UserList />} />
                    <Route path="/insert-user" element={<UserInsert />} />
                    <Route path="/update-user" element={<UserUpdatePage />} />
                    <Route path="/update-user/:id" element={<UserUpdateForm />} />
                    <Route path="/delete-user" element={<UserDeletePage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
