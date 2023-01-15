import {useContext} from "react";
import {AuthContext} from "../context/AuthContext";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";


export const RegisterPage = () => {
    const [form, setForm] = useState({
        firstName: '', lastName: '', middleName: '',
        birthDate: null, department: '', email: '', password: ''
    })

    useEffect(() => {
        window.M.updateTextFields()
    }, [])

    const changeHandler = event => {
        setForm({...form, [event.target.name]: event.target.value})
    }

    const navigate = useNavigate()

    const registerHandler = async () => {
        axios.post('api/employee/register', form)
            .then(() => {
                window.alert("Вы успешно зарегистрированы в системе")
                navigate('/')
            })
            .catch(err => {
                console.log(err)
                window.alert(err.response.data.message)
            })
    }

    return (
        <div className="row">
            <div className="col s6 offset-s3">
                <h1>Страница регистрации</h1>
                <div className="card grey">
                    <div className="card-content white-text">
                        <span className="card-title">Регистрация</span>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите имя"
                            id="firstName"
                            type="text"
                            name="firstName"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="firstName">Имя</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите фамилию"
                            id="lastName"
                            type="text"
                            name="lastName"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="lastName">Фамилия</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите отчество (необязательно)"
                            id="middleName"
                            type="text"
                            name="middleName"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="middleName">Отчество</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите дату рождения"
                            id="birthDate"
                            type="date"
                            name="birthDate"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="birthDate">Дата рождения</label>
                    </div>
                    <label className="white-text">Отдел</label>
                    <div className="input-field">
                        <select className="browser-default grey"
                                id="department"
                                name="department"
                                onChange={changeHandler}
                        >
                            <option value="" disabled selected>Выберите отдел</option>
                            <option value="1">Отдел продаж</option>
                            <option value="2">Бухгалтерия</option>
                            <option value="3">Склад</option>
                        </select>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите email"
                            id="email"
                            type="text"
                            name="email"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="email">E-mail</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите пароль"
                            id="password"
                            type="password"
                            name="password"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="password">Пароль</label>
                    </div>
                    <div className="card-action">
                        <button
                            className="btn yellow darken-4"
                            onClick={registerHandler}
                        >
                            Зарегистрироваться
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}