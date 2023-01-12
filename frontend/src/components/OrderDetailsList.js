import React from "react"
import axios from "axios";

export const OrderDetailsList = (order) => {

    const approveHandler = () => {
        axios.put("/api/purchase_order/" + order.order.orderNumber)
            .then(() => {
                window.alert("Заказ успешно подтвержден")
            })
    }

    return (
        <div>
            <table>
                <tbody>
                <tr>
                    <td><b>Номер заказа</b></td>
                    <td>{order.order.orderNumber}</td>
                </tr>
                <tr>
                    <td><b>Материал</b></td>
                    <td>{order.order.material}</td>
                </tr>
                <tr>
                    <td><b>Количество</b></td>
                    <td>{order.order.count}</td>
                </tr>
                <tr>
                    <td><b>Детали заказа</b></td>
                    <td>{order.order.details}</td>
                </tr>
                <tr>
                    <td><b>Поставщик</b></td>
                    <td>{order.order.provider}</td>
                </tr>
                <tr>
                    <td><b>Сотрудник, оформивший заказ</b></td>
                    <td>{order.order.employee}</td>
                </tr>
                <tr>
                    <td><b>Стоимость заказа (руб.)</b></td>
                    <td>{order.order.paymentSum}</td>
                </tr>
                <tr>
                    <td><b>Статус оплаты</b></td>
                    <td>{order.order.status}</td>
                </tr>
                <tr>
                    <td><b>Статус на складе</b></td>
                    <td>{order.order.storageStatus}</td>
                </tr>
                </tbody>
            </table>
            <div>
                <br/>
                {order.order.storageStatus === "Проверено" &&
                    <button className="btn blue darken-1" onClick={approveHandler}>Подтвердить получение</button>
                }
            </div>
        </div>
    )
}