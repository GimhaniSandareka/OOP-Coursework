import {useEffect, useState} from "react";

import axios from "axios";
import TicketPoolStatus from "./TicketPoolStatus.jsx";

function TicketPool(){
    const [ticketStatus, setTicketStatus] = useState({});
    const [isRunning, setIsRunning] = useState(false);
    const [log, setLog] = useState([]);

    useEffect(() => {
        //Polling the backend to update ticket pool status
        const interval = setInterval(() => {
            axios.get("http://localhost:8080/tickets/status").then((response) => {
                setTicketStatus(response.data);
            }).catch((error) => {
                console.error("Error fetching ticket status:", error);
            });
        },1000); //update every second
        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const eventSource = new EventSource("http://localhost:8080/tickets/logs");
        eventSource.onmessage = (event) => {
            const logMessage = JSON.parse(event.data);
            setLog(prevLog => [...prevLog, logMessage]);
        }
        return () => eventSource.close();
    }, []);

    return (
        <div className="ticket-pool">

            <fieldset>
                <h2>Ticket Pool Status</h2>
                <p><h3>Tickets Available: {TicketPoolStatus.availableTickets}</h3></p>
                <p><h3>Maximum Capacity: 150</h3></p>



            </fieldset>

        </div>
    )


}

export default TicketPool