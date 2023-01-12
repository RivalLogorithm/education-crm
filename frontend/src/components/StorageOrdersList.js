import {Link} from "react-router-dom";
import React from "react";

export const StorageOrdersList = ({orders}) => {

    return (
        <table>
            <thead>
            <th>Номер заказа</th>
            <th>Поставщик</th>
            <th>Статус проверки</th>
            <th></th>
            </thead>
            <tbody>
            {orders.length > 0 && orders.map((order) => {
                return (
                    <tr>
                        <td>{order.orderNumber}</td>
                        <td>{order.provider}</td>
                        <td>{order.status}</td>
                        {order.status === "-" &&
                            <td>
                                <Link to={`/storage/${order.orderNumber}`}>Проверить</Link>
                            </td>
                        }
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}