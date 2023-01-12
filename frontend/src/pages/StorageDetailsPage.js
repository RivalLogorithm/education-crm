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
            .then(() => {
                window.alert("Заказ проверен и подтвержден")
                navigate("/storage")
            })
            .catch(err => {
                window.alert(err)
                console.log(err)
            })
    }

    return (
        <div>
            <h2>Детали заказа № {orderNumber}</h2>
            {<StorageDetails order={order}/>}
            <button className="btn blue darken-1" onClick={approveHandler}>Подтвердить получение</button>
        </div>
    )
}