export const AccountingList = ({accounting}) => {
    console.log(accounting.length)
    return (
        <table>
            <thead>
            <th>Код счета</th>
            <th>Наименование</th>
            <th>Счет</th>
            <th>Операция</th>
            </thead>
            <tbody>
            {accounting.length > 0 && accounting.map(acc => {
                return (
                    <tr>
                        <td>{acc.invoiceCode}</td>
                        <td>{acc.invoiceName}</td>
                        <td>{acc.value}</td>
                        <td>{acc.operation}</td>
                    </tr>
                )
            })}
            </tbody>
        </table>
    )
}