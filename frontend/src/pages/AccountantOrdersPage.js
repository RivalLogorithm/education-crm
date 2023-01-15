import {AccountantOrdersList} from "../components/AccountantOrdersList";
import {useEffect, useState} from "react";
import axios from "axios";

export const AccountantOrdersPage = () => {
    const [orders, setOrders] = useState([])

    useEffect(() => {
        axios.get("/api/payment/get_orders")
            .then(res => setOrders(res.data))
            .catch(err => console.log(err))
    }, [])

    return (
        <div>
            <h2>Список заказов</h2>
            {<AccountantOrdersList orders={orders}/>}
        </div>
    )
}