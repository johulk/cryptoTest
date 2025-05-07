import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import HomePage from "./pages/HomePage";
import AuthPage from "./pages/AuthPage";
import ProfilePage from "./pages/ProfilePage";
import SettingsPage from "./pages/SettingsPage";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <Router>
            <Routes>
                <Route path="/" element={<HomePage/>} />
                <Route path="/auth" element={<AuthPage/>} />
                {/* <Route path="/profile/:username" element={<ProfilePage/>} /> */}
                <Route path="/profile" element={<ProfilePage/>} />
                <Route path="/settings" element={<SettingsPage/>} />
            </Routes>
        </Router>
    </StrictMode>
);