import {AuthContext} from "../context/AuthContext";
import {NavLink, useNavigate} from "react-router-dom";
import {useContext} from "react";
import logo from "../images/storage.png";

export const StorageNavbar = () => {
    const navigate = useNavigate()
    const auth = useContext(AuthContext)

    const logoutHandler = async event => {
        event.preventDefault()
        auth.logout()
        navigate("/")
    }

    return (

        <nav>
            <div className="nav-wrapper grey" style={{padding: '0 2rem'}}>
                <span className="brand-logo">
                    <a href="/">
                        <img src={logo} height="50" width="150" alt="Logo"/>
                    </a>
                </span>
                <ul id="nav-mobile" className="right hide-on-med-and-down">
                    <li><NavLink to="/storage">Проверка материалов</NavLink></li>
                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </nav>
    )
}