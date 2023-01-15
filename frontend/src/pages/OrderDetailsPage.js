import {useEffect, useState} from "react";
import {OrderDetailsList} from "../components/OrderDetailsList";
import axios from "axios";
import {useParams} from "react-router-dom";
import React from "react";

export const OrderDetailsPage = () => {
    const [order, setOrder] = useState([])
    const orderNumber = useParams().orderNumber

    useEffect(() => {
        axios.get("/api/purchase_order/" + orderNumber)
            .then(res => setOrder(res.data))
            .catch(err => console.log(err))
    }, [])
    return (
        <div>
            <h2>Детали заказа № {orderNumber}</h2>
            {<OrderDetailsList order={order}/>}
        </div>
    )
}