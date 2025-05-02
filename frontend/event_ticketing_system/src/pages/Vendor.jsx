import {useRef, useState, useEffect} from "react";


function Vendor() {

    const [isProcessing, setIsProcessing] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [ticketStatus, setTicketStatus] = useState({availableTickets: "N/A", soldTickets: "N/A"});
    const [systemStatus, setSystemStatus] = useState("System is idle");
    const [lastUpdated, setLastUpdated] = useState("");
    const inputRef = useRef(null);

    //Function to fetch ticket pool status
    const fetchTicketStatus = () => {
        fetch("http://localhost:8080/api/ticketpool-status")
            .then((response) => response.json())
            .then((data) => {
                setTicketStatus({
                    availableTickets: data.availableTickets ?? "N/A",
                    soldTickets: data.soldTickets ?? "N/A"
                });
                setLastUpdated(new Date().toLocaleTimeString());
            });
    };

    useEffect(() => {
        fetchTicketStatus();
    }, []);

    //Start Process handler
    const handleStartProcess = () => {
        const totalTickets = inputRef.current.value.trim();
        if(!totalTickets || isNaN(totalTickets) || totalTickets<=0){
            setErrorMessage("Please enter a valid number of tickets.");
            return;
        }
        setErrorMessage("");
        setIsProcessing(true);
        setSystemStatus("System is running");

        //Start vendor and customer threads by calling the backend /start endpoint
        fetch(`http://localhost:8080/api/start?totalTickets=${totalTickets}`).then(() => fetchTicketStatus());
    };

    const handleStop = () => {
        setIsProcessing(false);
        setSystemStatus("System is idle");
        //Stop vendor and customer threads by calling the backend /stop endpoint
        fetch("http://localhost:8080/api/stop").then(() => fetchTicketStatus());
    };

    const handleReset = () => {
        //adding the reset logic
        if(inputRef.current){
            inputRef.current.value = "";
        }
        setErrorMessage("");
    };


    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">Welcome to Ticketing System</h1>
            <div className="dashboard-subtitle">🎟️ Event Ticketing Dashboard</div>
            <div className="dashboard-row">
                <div className="dashboard-card vendor-card">
                    <div className="dashboard-card-title">✨ Vendor Dashboard</div>
                    <div className="dashboard-label">Total tickets to release</div>
                    <input type="text" className="dashboard-input" placeholder="Enter total number of tickets to release" ref={inputRef} />
                    {errorMessage && (<div className="dashboard-error">{errorMessage}</div>)}
                    <button className="dashboard-btn" onClick={handleStartProcess} disabled={isProcessing}> ▶ Start Process</button>
                </div>
                <div className="dashboard-card status-card">
                    <div className="dashboard-card-title">🎟️ Ticket Pool Status</div>
                    <div className="dashboard-status-row">
                        <div>
                            <div className="dashboard-label">Available Tickets</div>
                            <div className="dashboard-value dashboard-value-blue">{ticketStatus.availableTickets}</div>
                        </div>
                        <div>
                            <div className="dashboard-label">Tickets Sold</div>
                            <div className="dashboard-value dashboard-value-green">{systemStatus.soldTickets}</div>
                        </div>
                    </div>
                    <div className="dashboard-last-updated">Last updated: <span>{lastUpdated}</span></div>
                </div>
            </div>
            <div className="dashboard-card system-status-card">
                <div className="dashboard-card-title">System Status</div>
                <div className="dashboard-system-status">
                    <span className={isProcessing ? "status-dot running" : "status-dot idle"}></span>
                    {systemStatus}
                </div>
                <div className="dashboard-system-actions">
                    {isProcessing && (
                        <button className="dashboard-btn dashboard-btn-stop" onClick={handleStop}>⏹ Stop</button>
                    )}
                    <button className="dashboard-btn dashboard-btn-reset" onClick={handleReset}>🔃 Reset</button>
                </div>
            </div>
        </div>
    )
}

export default Vendor