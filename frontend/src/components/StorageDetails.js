import React from "react"

export const StorageDetails = (order) => {
    return (
        <div>
            <p><b>Поставщик</b> {order.order.provider}</p>
            <p><b>Детали заказа</b> {order.order.details}</p>
            {order.order.date !== null && <p><b>Дата поступления на склад</b> {order.order.date}</p>}
        <table>
            <thead>
            <th></th>
            <th>В заказе</th>
            <th>На складе</th>
            </thead>
            <tbody>
            <tr>
                <td><b>Материал</b></td>
                <td>{order.order.material}</td>
                <td>{order.order.storageName}</td>
            </tr>
            <tr>
                <td><b>Количество</b></td>
                <td>{order.order.count}</td>
                <td>{order.order.storageCount}</td>
            </tr>
            </tbody>
        </table>
        </div>
    )
}