import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import axios from "axios";
import {useNavigate} from "react-router-dom";


export const AuthPage = () => {
    const auth = useContext(AuthContext)

    const [form, setForm] = useState({
        email: '', password: ''
    })

    useEffect(() => {
        window.M.updateTextFields()
    }, [])

    const changeHandler = event => {
        setForm({...form, [event.target.name]: event.target.value})
    }

    const navigate = useNavigate()

    const registerHandler = async () => {
        navigate('/register')
    }

    const loginHandler = async () => {
        axios.post('api/employee/login', form)
            .then(res => {
                auth.login(res.data.employeeId, res.data.department.departmentId)
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
                <h1>Страница авторизации</h1>
                <div className="card grey">
                    <div className="card-content white-text">
                        <span className="card-title">Авторизация</span>
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
                            onClick={loginHandler}
                            style={{marginRight: 10}}
                        >
                            Войти
                        </button>
                        <button
                            className="btn grey darken-1"
                            onClick={registerHandler}
                        >
                            Регистрация
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}