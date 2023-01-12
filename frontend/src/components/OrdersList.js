import React from "react"
import {Link} from "react-router-dom";

export const OrdersList = ({orders}) => {

    return (
        <table>
            <thead>
            <th>Номер заказа</th>
            <th>Материал</th>
            <th>Поставщик</th>
            </thead>
            <tbody>
            {orders.length > 0 && orders.map((order) => {
                return (
                    <tr>
                        <td>
                            <Link to={`/purchase_order/${order.orderNumber}`}>{order.orderNumber}</Link>
                        </td>
                        <td>{order.material}</td>
                        <td>{order.provider}</td>
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}