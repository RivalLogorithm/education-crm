import React from "react";
import {Route, Routes, Navigate} from "react-router-dom";
import {HomePage} from "./pages/HomePage";
import {AuthPage} from "./pages/AuthPage";
import {RegisterPage} from "./pages/RegisterPage";
import {NewPurchaseOrder} from "./pages/NewPurchaseOrder";
import {PaymentPage} from "./pages/PaymentPage";
import {PurchaseOrdersPage} from "./pages/PurchaseOrdersPage";
import {CheckStoragePage} from "./pages/CheckStoragePage";

export const useRoutes = department => {
    switch (department) {
        case 1:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/new_order" exact element={<NewPurchaseOrder/>}/>
                    <Route path="/purchase_orders" exact element={<PurchaseOrdersPage/>}/>
                    <Route path="*" exact element={<Navigate to="/"/>}/>
                </Routes>
            )
        case 2:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/payment" exact element={<PaymentPage/>}/>
                    <Route path="*" exact element={<Navigate to="/"/>}/>
                </Routes>
            )
        case 3:
            return (
                <Routes>
                    <Route path="/" exact element={<HomePage/>}/>
                    <Route path="/check_storage" exact element={<CheckStoragePage/>}/>
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