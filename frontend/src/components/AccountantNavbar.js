import {AuthContext} from "../context/AuthContext";
import {NavLink, useNavigate} from "react-router-dom";
import {useContext} from "react";
import logo from "../images/accountant.png"

export const AccountantNavbar = () => {
    const navigate = useNavigate()
    const auth = useContext(AuthContext)

    const logoutHandler = async event => {
        event.preventDefault()
        auth.logout()
        navigate("/")
    }

    return (
        <nav>
            <div className="nav-wrapper orange darken-2" style={{padding: '0 2rem'}}>
                <span className="brand-logo">
                    <a href="/">
                        <img src={logo} height="50" width="250" alt="Logo"/>
                    </a>
                </span>
                <ul id="nav-mobile" className="right hide-on-med-and-down">
                    <li><NavLink to="/payment">Движение денежных средств для оплаты ТМЦ</NavLink></li>
                    <li><NavLink to="/accounting">ОСВ Проводки</NavLink></li>
                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </nav>
    )
}