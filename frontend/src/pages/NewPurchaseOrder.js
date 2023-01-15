import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {useNavigate} from "react-router-dom";
import {ContractContext} from "../context/ContractContext";
import axios from "axios";


export const NewPurchaseOrder = () => {
    const auth = useContext(AuthContext)
    const contractContext = useContext(ContractContext)

    const [ form, setForm] = useState({
        material: '', count: null, price: null,
        orderDetails: '', providerId: null, employeeId: auth.employeeId
    })

    useEffect(() => {
        window.M.updateTextFields()
    }, [])

    const changeHandler = event => {
        setForm({...form, [event.target.name]: event.target.value})
    }

    const navigate = useNavigate()

    const newPurchaseOrderHandler = async () => {
        axios.post("/api/purchase_order/new", form)
            .then(res => {
                contractContext.contactNumber = res.data.contractNumber
                contractContext.paymentSum = res.data.paymentSum
                navigate("/contract")
            })
            .catch(err => {
                console.log(err)
                window.alert(err.response.data.message)
            })
    }

    return (
        <div className="row">
            <div className="col s6 offset-s3">
                <div className="card grey">
                    <div className="card-content white-text">
                        <span className="card-title">Заявка на необходимые материалы</span>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите необходимый материал"
                            id="material"
                            type="text"
                            name="material"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="firstName">Материал</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите количество"
                            id="count"
                            type="text"
                            name="count"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="count">Количество</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Введите цену"
                            id="price"
                            type="text"
                            name="price"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="price">Цена в рублях</label>
                    </div>
                    <div className="input-field">
                        <input
                            placeholder="Детали заказа"
                            id="orderDetails"
                            type="text"
                            name="orderDetails"
                            className="black-input"
                            onChange={changeHandler}
                        />
                        <label htmlFor="orderDetails">Детали заказа</label>
                    </div>
                    <label className="white-text">Поставщик</label>
                    <div className="input-field">
                        <select className="browser-default grey"
                                id="providerId"
                                name="providerId"
                                onChange={changeHandler}
                        >
                            <option value="" disabled selected>Выберите поставщика</option>
                            <option value="1">Поставщик 1</option>
                            <option value="2">Поставщик 2</option>
                            <option value="3">Поставщик 3</option>
                        </select>
                    </div>
                    <div className="card-action">
                        <button
                            className="btn yellow darken-4"
                            onClick={newPurchaseOrderHandler}
                        >
                            Получить от поставщика договор и счет
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}