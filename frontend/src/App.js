import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import HomePage from "./components/HomePage";
import PartnershipPrograms from "./components/PartnershipPrograms";
import Users from "./components/Users";
import 'react-notifications/lib/notifications.css';
import {NotificationContainer} from "react-notifications";

class App extends Component {

    render() {
        return (
            <div>
                <Router>
                    <Routes>
                        <Route path='/' exact={true} element={<HomePage/>}/>
                        <Route path='/home' exact={true} element={<HomePage/>}/>
                        <Route path='/partnership-programs' exact={true} element={<PartnershipPrograms/>}/>
                        <Route path='/users' exact={true} element={<Users/>}/>
                    </Routes>
                </Router>
                <NotificationContainer/>
            </div>
        )
    }
}

export default App;
