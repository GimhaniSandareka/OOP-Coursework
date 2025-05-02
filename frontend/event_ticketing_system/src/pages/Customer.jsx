import {useRef, useState, useEffect} from "react";

function Customer() {

    const [availableTickets, setAvailableTickets] = useState("N/A");
    const [purchaseHistory, setPurchaseHistory] = useState([]);
    const [eventInfo, setEventInfo] = useState({
        name: "Custom Event",
        date: "Oct 30, 2024",
        location: "Custom Location"
    });

    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const inputRef = useRef(null);
    const customerId = 1;  // Simple static customer ID for demonstration

    // Fetch available tickets
    const fetchAvailableTickets = () => {
        fetch("http://localhost:8080/api/ticketpool-status")
            .then((response) => response.json())
            .then((data) => {
                setAvailableTickets(data.availableTickets ?? "N/A");
            });
    };

    // Fetch purchase history
    const fetchPurchaseHistory = () => {
        fetch(`http://localhost:8080/api/purchases/${customerId}`)
        .then((response) => response.json())
        .then((data) => {
            setPurchaseHistory(data);
        });
    };

    useEffect(() => {
        fetchAvailableTickets();
        fetchPurchaseHistory();
        // TODO: Fetch event info from backend
    }, []);

    // Purchase tickets handler
    const handlePurchase = () => {
        const count = inputRef.current.value.trim();
        if (!count || isNaN(count) || count <= 0) {
            setErrorMessage("Please enter a valid number of tickets");
            setSuccessMessage("");
            return;
        }
        setErrorMessage("");
        setSuccessMessage("");
        fetch("http://localhost:8080/api/purchase", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ customerId, ticketCount: parseInt(count) }),
        })
        .then(async (response) => {
            if (!response.ok) {
                const text = await response.text();
                throw new Error(text || "Purchase failed");
            }
            return response.json();
        })
            .then((data) => {
                setSuccessMessage("Purchase successful!");
                inputRef.current.value = "";
                fetchAvailableTickets();
                fetchPurchaseHistory();
            }).catch((err) => {
                setErrorMessage(err.message);
                setSuccessMessage("");
        });
    };

    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">Welcome to Ticketing System</h1>
            <div className="dashboard-subtitle">👨🏼 Customer Dashboard</div>
            <div className="dashboard-row">
                <div className="dashboard-card purchase-card">
                    <div className="dashboard-card-title">🛒 Purchase Tickets</div>
                    <div className="dashboard-label">Available Tickets</div>
                    <div className="dashboard-value dashboard-value-blue">{availableTickets}</div>
                    <div className="dashboard-label">Number of tickets to purchase</div>
                    <input type="text" className="dashboard-input" placeholder="Enter number of tickets" ref={inputRef} />
                    {errorMessage && (<div className="dashboard-error">{errorMessage}</div>)}
                    {successMessage && (<div className="dashboard-sucess">{successMessage}</div>)}
                    <button className="dashboard-btn" onClick={handlePurchase}>📃 Purchase Tickets</button>
                </div>
                <div className="dashboard-card history-card">
                    <div className="dashboard-card-title">⏰ Purchase History</div>
                    <div className="dashboard-history-list">
                        {purchaseHistory.length === 0 && <div className="dashboard-label">No purchase yet.</div> }
                        {purchaseHistory.map((item) => (
                            <div className="dashboard-history-item" key={item.id}>
                                <span className="dashboard-history-id">🎟️ #{item.id}</span>
                                <span className="dashboard-history-date">{new Date(item.purchaseTime).toLocaleString()}</span>
                                <span className="dashboard-history-count">{item.ticketCount} ticket{item.ticketCount > 1 ? "s" : ""}</span>
                                <span className="dashboard-history-status completed">✔️ completed</span>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
            <div className="dashboard-card event-info-card">
                <div className="dashboard-card-title">✨ Event Information</div>
                <div className="dashboard-event-info-row">
                    <div>
                        <div className="dashboard-label">Event Name</div>
                        <div className="dashboard-value dashboard-value-bold">{eventInfo.name}</div>
                    </div>
                    <div>
                        <div className="dashboard-label">Date</div>
                        <div className="dashboard-value dashboard-value-bold">{eventInfo.date}</div>
                    </div>
                    <div>
                        <div className="dashboard-label">Location</div>
                        <div className="dashboard-value dashboard-value-bold">{eventInfo.location}</div>
                    </div>
                </div>
                <div className="dashboard-event-note">
                    Tickets are non-refundable. Each ticket grants entry for one person.
                </div>
            </div>
        </div>
    );



}

export default Customer

