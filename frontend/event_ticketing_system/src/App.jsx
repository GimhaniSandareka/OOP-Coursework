import { useState } from 'react'
import './App.css'
import './components/TicketPool.jsx'
import TicketPool from "./components/TicketPool.jsx";
import Vendor from "./pages/Vendor.jsx";
import Btn from "./components/Btn.jsx";
import './components/DisplayBox.jsx';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Customer from "./pages/Customer.jsx";
import TicketPoolStatus from "./components/TicketPoolStatus.jsx";


function App() {


  return (
    <div className="App">



        <BrowserRouter>
            <Btn/> <br/>
            <Routes>
                <Route path="/" element={<Vendor/>}/>
                <Route path="/customer" element={<Customer/>}/>
            </Routes>
        </BrowserRouter>


    </div>
  );
}

export default App
