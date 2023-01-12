import React from "react";
import {Route, Routes, Navigate} from "react-router-dom";
import {HomePage} from "./pages/HomePage";
import {AuthPage} from "./pages/AuthPage";
import {RegisterPage} from "./pages/RegisterPage";
import {NewPurchaseOrder} from "./pages/NewPurchaseOrder";
import {AccountantOrdersPage} from "./pages/AccountantOrdersPage";
import {PurchaseOrdersPage} from "./pages/PurchaseOrdersPage";
import {ContractPage} from "./pages/ContractPage";
import {OrderDetailsPage} from "./pages/OrderDetailsPage";
import {PaymentPage} from "./pages/PaymentPage";
import {StorageOrdersPage} from "./pages/StorageOrdersPage";
import {StorageDetailsPage} from "./pages/StorageDetailsPage";

export const useRoutes = department => {
    switch (department) {
        case 1:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/new_order" exact element={<NewPurchaseOrder/>}/>
                    <Route path="/contract" exact element={<ContractPage/>}/>
                    <Route path="/purchase_orders" exact element={<PurchaseOrdersPage/>}/>
                    <Route path="/purchase_order/:orderNumber" exact element={<OrderDetailsPage/>}/>
                    <Route path="*" exact element={<Navigate to="/"/>}/>
                </Routes>
            )
        case 2:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/payment" exact element={<AccountantOrdersPage/>}/>
                    <Route path="/payment/:orderNumber" exact element={<PaymentPage/>}/>
                    <Route path="*" exact element={<Navigate to="/"/>}/>
                </Routes>
            )
        case 3:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/storage" exact element={<StorageOrdersPage/>}/>
                    <Route path="/storage/:orderNumber" exact element={<StorageDetailsPage/>}/>
                    <Route path="*" exact element={<Navigate to="/"/>}/>
                </Routes>
            )
        default:
            return (
                <Routes>
                    <Route path="/register" exact element={<RegisterPage/>}/>
                    <Route path="/" exact element={<AuthPage/>}/>
                    <Route path="*" exact element={<AuthPage/>}/>
                </Routes>
            )
    }
}