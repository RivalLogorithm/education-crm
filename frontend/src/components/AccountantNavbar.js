import {AuthContext} from "../context/AuthContext";
import {NavLink, useNavigate} from "react-router-dom";
import {useContext} from "react";

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
            <div className="nav-wrapper grey" style={{padding: '0 2rem'}}>
                <ul id="nav-mobile" className="left hide-on-med-and-down">
                    <li><NavLink to="/">Главная</NavLink></li>
                    <li><NavLink to="/payment">Движение денежных средств для оплаты ТМЦ</NavLink></li>
                    <li><NavLink to="/accounting">ОСВ Проводки</NavLink></li>
                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </nav>
    )
}