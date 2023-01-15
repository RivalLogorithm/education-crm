import {useState, useCallback, useEffect} from 'react'

const storageName = 'userData'

export const useAuth = () => {
    const [ready, setReady] = useState(false)
    const [employeeId, setEmployeeId] = useState(null)
    const [department, setDepartment] = useState(null)

    const login = useCallback((id, department) => {
        setEmployeeId(id)
        setDepartment(department)

        localStorage.setItem(storageName, JSON.stringify({
            employeeId: id, department: department
        }))
    }, [])


    const logout = useCallback(() => {
        setEmployeeId(null)
        setDepartment(null)
        localStorage.removeItem(storageName)
    }, [])

    useEffect(() => {
        const data = JSON.parse(localStorage.getItem(storageName))
        if (data) {
            login(data.employeeId, data.department)
        }
        setReady(true)
    }, [login])


    return {login, logout, employeeId, department, ready}
}