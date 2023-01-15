import {createContext} from "react";

function noop() {
}

export const AuthContext = createContext({
    employeeId: null,
    login: noop,
    logout: noop,
    department: null
})