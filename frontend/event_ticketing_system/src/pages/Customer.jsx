import {useRef, useState, useEffect} from "react";

function Customer() {
    const [availableTickets, setAvailableTickets] = useState("N/A");
    const [purchaseHistory, setPurchaseHistory] = useState([]);
    const [eventInfo, setEventInfo] = useState({ name: "", date: "", location: "" });
    const [customers, setCustomers] = useState([]);
    const [selectedCustomerId, setSelectedCustomerId] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const inputRef = useRef(null);

    // Fetch available tickets
    const fetchAvailableTickets = () => {
        fetch("http://localhost:8080/api/ticketpool-status")
            .then((response) => response.json())
            .then((data) => {
                setAvailableTickets(data.availableTickets ?? "N/A");
            });
    };

    // Fetch purchase history
    const fetchPurchaseHistory = (customerId) => {
        if (!customerId) return setPurchaseHistory([]);
        fetch(`http://localhost:8080/api/purchases/${customerId}`)
            .then((response) => response.json())
            .then((data) => {
                setPurchaseHistory(data);
            });
    };

    // Fetch customers
    const fetchCustomers = () => {
        fetch("http://localhost:8080/customers")
            .then((response) => response.json())
            .then((data) => {
                setCustomers(data);
                if (data.length > 0) setSelectedCustomerId(data[0].id);
            });
    };

    // Fetch event info
    const fetchEventInfo = () => {
        fetch("http://localhost:8080/api/event-info")
            .then((response) => response.json())
            .then((data) => setEventInfo(data));
    };

    useEffect(() => {
        fetchAvailableTickets();
        fetchCustomers();
        fetchEventInfo();
    }, []);

    useEffect(() => {
        fetchPurchaseHistory(selectedCustomerId);
    }, [selectedCustomerId]);

    // Purchase tickets handler
    const handlePurchase = () => {
        const count = inputRef.current.value.trim();
        if (!selectedCustomerId) {
            setErrorMessage("Please select a customer.");
            setSuccessMessage("");
            return;
        }
        if (!count || isNaN(count) || count <= 0) {
            setErrorMessage("Please enter a valid number of tickets.");
            setSuccessMessage("");
            return;
        }
        setErrorMessage("");
        setSuccessMessage("");
        fetch("http://localhost:8080/api/purchase", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ customerId: parseInt(selectedCustomerId), ticketCount: parseInt(count) })
        })
            .then(async (response) => {
                if (!response.ok) {
                    const text = await response.text();
                    throw new Error(text || "Purchase failed");
                }
                return response.json();
            })
            .then(() => {
                setSuccessMessage("Purchase successful!");
                inputRef.current.value = "";
                fetchAvailableTickets();
                fetchPurchaseHistory(selectedCustomerId);
            })
            .catch((err) => {
                setErrorMessage(err.message);
                setSuccessMessage("");
            });
    };

    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">Ticket System</h1>
            <div className="dashboard-subtitle">👨🏼 Customer Dashboard</div>
            <div style={{ marginBottom: "1.5rem" }}>
                <label className="dashboard-label" htmlFor="customer-select">Select Customer: </label>
                <select
                    id="customer-select"
                    className="dashboard-input"
                    value={selectedCustomerId}
                    onChange={e => setSelectedCustomerId(e.target.value)}
                    style={{ maxWidth: 300 }}
                >
                    {customers.map(c => (
                        <option key={c.id} value={c.id}>{c.name} ({c.email})</option>
                    ))}
                </select>
            </div>
            <div className="dashboard-row">
                <div className="dashboard-card purchase-card">
                    <div className="dashboard-card-title">🛒 Purchase Tickets</div>
                    <div className="dashboard-label">Available Tickets</div>
                    <div className="dashboard-value dashboard-value-blue">{availableTickets}</div>
                    <div className="dashboard-label">Number of tickets to purchase</div>
                    <input type="text" className="dashboard-input" placeholder="Enter number of tickets" ref={inputRef} />
                    {errorMessage && (<div className="dashboard-error">{errorMessage}</div>)}
                    {successMessage && (<div className="dashboard-success">{successMessage}</div>)}
                    <button className="dashboard-btn" onClick={handlePurchase}>
                        📃 Purchase Tickets
                    </button>
                </div>
                <div className="dashboard-card history-card">
                    <div className="dashboard-card-title">⏰ Purchase History</div>
                    <div className="dashboard-history-list">
                        {purchaseHistory.length === 0 && <div className="dashboard-label">No purchases yet.</div>}
                        {purchaseHistory.map((item) => (
                            <div className="dashboard-history-item" key={item.id}>
                                <span className="dashboard-history-id">🎟️ #{item.id}</span>
                                <span className="dashboard-history-date">{new Date(item.purchaseTime).toLocaleString()}</span>
                                <span className="dashboard-history-count">{item.ticketCount} ticket{item.ticketCount > 1 ? "s" : ""}</span>
                                <span className="dashboard-history-status completed">✔ completed</span>
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

export default Customer;

