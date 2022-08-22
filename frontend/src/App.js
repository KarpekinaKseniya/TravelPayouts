import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import HomePage from "./components/HomePage";

class App extends Component {

    render() {
        return (
            <BrowserRouter>
                <Routes>
                    <Route path='/' exact={true} element={<HomePage/>}/>
                    <Route path='/home' exact={true} element={<HomePage/>}/>
                </Routes>
            </BrowserRouter>
        )
    }
}

export default App;
