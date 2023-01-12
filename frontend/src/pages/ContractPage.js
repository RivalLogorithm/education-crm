import {useContext} from "react";
import {AuthContext} from "../context/AuthContext";
import {ContractContext} from "../context/ContractContext";
import axios from "axios";
import {useNavigate} from "react-router-dom";


export const ContractPage = () => {
    const auth = useContext(AuthContext)
    const contractContext = useContext(ContractContext)
    const navigate = useNavigate()

    const submitForPayment = () => {
        axios.put("api/purchase_order/submit/" + contractContext.contactNumber)
            .then(res => {
                window.alert(res.data)
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
                <div className="card grey">
                    <div className="card-content white-text">
                        <span className="card-title">Заявка на необходимые материалы</span>
                    </div>
                    <label className="white-text">Номер договора</label>
                    <div className="input-field">
                        <input
                            disabled
                            value={contractContext.contactNumber}
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
                            value={contractContext.paymentSum}
                            id="paymentSum"
                            type="text"
                            name="paymentSum"
                            className="black-input"
                        />
                    </div>
                    <div className="card-action">
                        <button
                            className="btn yellow darken-4"
                            onClick={submitForPayment}
                        >
                            Передать на оплату
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}