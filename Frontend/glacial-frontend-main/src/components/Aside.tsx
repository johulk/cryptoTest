import "../styles/Aside.css";
import { FiHome, FiBell, FiMail, FiUser, FiSettings } from "react-icons/fi";
import avatar from "../images/ronaldo.png";

export default function Aside() {
    return (
        <aside id="aside">
            <div className="user-box">
                <img src={avatar} alt="Logo" className="user-logo" />
                <div className="user-info">
                    <span className="user-name">Cristiano Ronaldo</span>
                    <span className="user-username">@cr7goat</span>
                </div>
            </div>
            <nav className="nav-menu">
                <ul>
                    <li className="selected">
                        <a href="/home">
                            <FiHome className="nav-icon" />
                            Home
                        </a>
                    </li>
                    <li>
                        <a href="/notifications">
                            <FiBell className="nav-icon" />
                            Notifications
                        </a>
                    </li>
                    <li>
                        <a href="/messages">
                            <FiMail className="nav-icon" />
                            Messages
                        </a>
                    </li>
                    <li>
                        <a href="/profile">
                            <FiUser className="nav-icon" />
                            Profile
                        </a>
                    </li>
                    <li>
                        <a href="/settings">
                            <FiSettings className="nav-icon" />
                            Settings
                        </a>
                    </li>
                </ul>
            </nav>
        </aside>
    );
}
