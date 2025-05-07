import "../styles/Header.css";
import logo from "../images/logo.png";
import { FaSearch, FaSignInAlt } from "react-icons/fa";

export default function Header() {
    return (
        <div id="header">
            <div className="header-left">
                <img src={logo} alt="Logo" className="logo" />
                <span className="name">Glacial</span>
            </div>
            <div className="header-right">
                <div className="search-bar">
                    <FaSearch />
                    <input type="text" placeholder="Search..." />
                </div>
                <ul className="nav-links">
                    <li><a href="" target="_blank" rel="">Docs</a></li>
                    <li><a href="" target="_blank" rel="">Support</a></li>
                    <li><a href="" target="_blank" rel="">About</a></li>
                    <li>
                        <button>
                            <FaSignInAlt /> Sign In
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    );
}