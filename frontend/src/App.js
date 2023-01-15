import "materialize-css";
import {BrowserRouter as Router} from "react-router-dom";
import {useRoutes} from "./routes";
import {useAuth} from "./hooks/auth.hook";
import {AuthContext} from "./context/AuthContext";
import {Loader} from "./components/Loader";
import {PurchaseNavbar} from "./components/PurchaseNavbar";
import {AccountantNavbar} from "./components/AccountantNavbar";
import {StorageNavbar} from "./components/StorageNavbar";

function App() {
    const {login, logout, employeeId, department, ready} = useAuth();
    const routes = useRoutes(department)

    if (!ready) {
        return <Loader/>
    }

    return (
        <AuthContext.Provider value={{
            login, logout, department, employeeId
        }}>
            <Router>
                {department === 1 && <PurchaseNavbar/>}
                {department === 2 && <AccountantNavbar/>}
                {department === 3 && <StorageNavbar/>}
                <div className="container">
                    {routes}
                </div>
            </Router>
        </AuthContext.Provider>
    )
}

export default App;
