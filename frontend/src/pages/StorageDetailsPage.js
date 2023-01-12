import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {StorageDetails} from "../components/StorageDetails";
import {useEffect, useState} from "react";

export const StorageDetailsPage = () => {
    const [order, setOrder] = useState([])
    const orderNumber = useParams().orderNumber
    const navigate = useNavigate()

    useEffect(() => {
        axios.get("/api/storage/get/" + orderNumber)
            .then(res => setOrder(res.data))
            .catch(err => console.log(err))
    }, [])

    const approveHandler = () => {
        axios.put("/api/storage/approve/" + orderNumber)

        const accountingEntity = {
            debit: "10", debitName: "Материалы",
            credit: "60.1", creditName: "Расчеты с поставщиками и подрядчиками авансы выданные",
            value: order.price, additionalValue: order.nds,
            operation: "Поступление ТМЦ (" + order.provider + ")"
        }

        axios.post("/api/accounting/new_entity", accountingEntity)
            .catch(err => console.log(err))

        accountingEntity.debit = "19"
        accountingEntity.debitName = "Налог на добавленную стоимость по приобретенным ценностям"

        axios.post("/api/accounting/new_entity", accountingEntity)
            .then(() => {
                window.alert("Заказ проверен и подтвержден")
                navigate("/storage")
            })
            .catch(err => console.log(err))
    }

    return (
        <div>
            <h2>Детали заказа № {orderNumber}</h2>
            {<StorageDetails order={order}/>}
            <button className="btn blue darken-1" onClick={approveHandler}>Подтвердить получение</button>
        </div>
    )
}