import {OrdersList} from "../components/OrdersList";
import {useEffect, useState} from "react";
import axios from "axios";

export const PurchaseOrdersPage = () => {
    const [orders, setOrders] = useState([])

    useEffect(() => {
        axios.get("/api/purchase_order/get_orders")
            .then(res => setOrders(res.data))
            .catch(err => console.log(err))
    }, [])

    return (
        <div>
            <h2>Список заказов</h2>
            {<OrdersList orders={orders}/>}
        </div>
    )
}