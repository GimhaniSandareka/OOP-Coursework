import React, { useEffect, useState } from 'react';

const TicketPoolStatus = () => {
    const [ticketPoolStatus, setTicketPoolStatus] = useState({ availableTickets: 0, soldTickets: 0 });

    useEffect(() => {
        // Fetch the ticket pool status from the backend
        const fetchTicketPoolStatus = async () => {
            const response = await fetch('http://localhost:8080/api/ticketpool-status');
            const data = await response.json();
            setTicketPoolStatus(data);
        };

        fetchTicketPoolStatus();

        // Setting an interval to refresh the status every few seconds
        const interval = setInterval(fetchTicketPoolStatus, 5000); // Refresh every 5 seconds

        return () => clearInterval(interval);  // Cleanup on unmount
    }, []);

    return (
        <div>
            <h2>Ticket Pool Status</h2>
            <p>Available Tickets: {ticketPoolStatus.availableTickets}</p>
            <p>Sold Tickets: {ticketPoolStatus.soldTickets}</p>
        </div>
    );
};

export default TicketPoolStatus;
