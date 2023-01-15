import {Link} from "react-router-dom";
import React from "react";

export const AccountantOrdersList = ({orders}) => {

    return (
        <table>
            <thead>
            <th>Номер заказа</th>
            <th>Оригинал договора</th>
            <th>Статус оплаты</th>
            <th></th>
            </thead>
            <tbody>
            {orders.length > 0 && orders.map((order) => {
                return (
                    <tr>
                        <td>{order.orderNumber}</td>
                        {order.original && <td>Найден</td>}
                        {!order.original && <td>Не найден</td>}
                        <td>{order.status}</td>
                        {!order.paid && order.original &&
                            <td>
                                <Link to={`/payment/${order.orderNumber}`}>Оплатить</Link>
                            </td>
                        }
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}