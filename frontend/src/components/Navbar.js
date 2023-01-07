import React from 'react'
import {NavLink} from 'react-router-dom'

export const Navbar = () => {
    return (
        <nav>
            <div className="nav-wrapper grey" style={{padding: '0 2rem'}}>
                <ul id="nav-mobile" className="left hide-on-med-and-down">
                    <li><NavLink to="/">Главная</NavLink></li>
                </ul>
            </div>
        </nav>
    )
}