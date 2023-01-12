import {useEffect, useState} from "react";
import axios from "axios";
import {StorageOrdersList} from "../components/StorageOrdersList";

export const StorageOrdersPage = () => {
    const [orders, setOrders] = useState([])

    useEffect(() => {
        axios.get("/api/storage/get")
            .then(res => setOrders(res.data))
            .catch(err => console.log(err))
    }, [])



    return (
        <div>
            <h2>Список заказов</h2>
            {<StorageOrdersList orders={orders}/>}
        </div>
    )
}