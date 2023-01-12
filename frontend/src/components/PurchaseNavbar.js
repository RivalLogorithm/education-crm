import {AuthContext} from "../context/AuthContext";
import {NavLink, useNavigate} from "react-router-dom";
import {useContext} from "react";
import logo from "../images/purchase.png";

export const PurchaseNavbar = () => {
    const navigate = useNavigate()
    const auth = useContext(AuthContext)

    const logoutHandler = async event => {
        event.preventDefault()
        auth.logout()
        navigate("/")
    }

    return (
        <nav>
            <div className="nav-wrapper blue darken-1" style={{padding: '0 2rem'}}>
                <span className="brand-logo">
                    <a href="/">
                        <img src={logo} height="50" width="300" alt="Logo"/>
                    </a>
                </span>
                <ul id="nav-mobile" className="right hide-on-med-and-down">
                    <li><NavLink to="/new_order">Заявка на необходимые материалы</NavLink></li>
                    <li><NavLink to="/purchase_orders">Заказы</NavLink></li>
                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </nav>
    )
}