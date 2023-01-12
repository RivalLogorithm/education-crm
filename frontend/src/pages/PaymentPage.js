import axios from "axios";
import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";

export const PaymentPage = () => {
    const [payment, setPayment] = useState([])
    const navigate = useNavigate()
    const orderNumber = useParams().orderNumber

    useEffect(() => {
        axios.get("/api/payment/get/" + orderNumber)
            .then(res => setPayment(res.data))
            .catch(err => console.log(err))
    }, [])

    const payHandler = () => {
        axios.put("/api/payment/pay/" + payment.contractNumber)
            .then(() => {
                window.alert("Договор успешно оплачен")
                navigate("/payment")
            })
            .catch(err => console.log(err))
    }

    return (
        <div className="row">
            <div className="col s6 offset-s3">
                <div className="card grey">
                    <div className="card-content white-text">
                        <span className="card-title">Оплата по договору</span>
                    </div>
                    <label className="white-text">Номер заказа</label>
                    <div className="input-field">
                        <input
                            disabled
                            value={orderNumber}
                            id="orderNumber"
                            type="text"
                            name="orderNumber"
                            className="black-input"
                        />
                    </div>
                    <label className="white-text">Номер договора</label>
                    <div className="input-field">
                        <input
                            disabled
                            value={payment.contractNumber}
                            id="contractNumber"
                            type="text"
                            name="contractNumber"
                            className="black-input"
                        />
                    </div>
                    <label className="white-text">Стоимость в рублях</label>
                    <div className="input-field">
                        <input
                            disabled
                            value={payment.paymentSum}
                            id="paymentSum"
                            type="text"
                            name="paymentSum"
                            className="black-input"
                        />
                    </div>
                    <div className="card-action">
                        <button
                            className="btn yellow darken-4"
                            onClick={payHandler}
                        >
                            Оплатить
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}