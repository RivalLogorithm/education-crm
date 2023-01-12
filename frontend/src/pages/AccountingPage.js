import {useEffect, useState} from "react";
import axios from "axios";
import {AccountingList} from "../components/AccountingList";

export const AccountingPage = () => {
    const [accounting, setAccounting] = useState([])

    useEffect(() => {
        axios.get("/api/accounting/get")
            .then(res => setAccounting(res.data))
            .catch(err => console.log(err))
    }, [])
    return (
        <div>
            <h2>ОСВ Проводки</h2>
            {<AccountingList accounting={accounting}/>}
        </div>
    )
}