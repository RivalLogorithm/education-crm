import React from "react";
import {Route, Routes, Navigate} from "react-router-dom";
import {HomePage} from "./pages/HomePage";

export const useRoutes = () => {
    return (
        <Routes>
            <Route path="/" exact element={<HomePage/>}/>
            <Route path="*" exact element={<Navigate to="/"/>}/>
        </Routes>
    )
}