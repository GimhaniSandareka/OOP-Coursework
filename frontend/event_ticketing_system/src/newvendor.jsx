import { useState, useRef } from "react";

function Vendor() {
  const [isProcessing, setIsProcessing] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [ticketStatus, setTicketStatus] = useState({});
  const inputRef = useRef(null);

  // Function to fetch ticket pool status
  const fetchTicketStatus = () => {
    fetch("http://localhost:8080/api/ticketpool-status")
      .then((response) => response.json())
      .then((data) => setTicketStatus(data));
  };

  // Start Process Handler
  const handleStartProcess = () => {
    if (!inputRef.current || inputRef.current.value.trim() === "") {
      setErrorMessage("This field cannot be empty");
      return;
    }
    if (!/^\d+$/.test(inputRef.current.value.trim())) {
      setErrorMessage("Please enter a valid integer.");
      return;
    }
    setErrorMessage("");
    setIsProcessing(true);
    const totalTickets = inputRef.current.value.trim();

    // Start vendor and customer threads by calling the backend /start endpoint
    fetch(`http://localhost:8080/api/start?totalTickets=${totalTickets}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data); // Log response (can be updated to notify user)
      });
  };

  // Stop Process Handler
  const handleStop = () => {
    setIsProcessing(false);
    // Stop vendor and customer threads by calling the backend /stop endpoint
    fetch("http://localhost:8080/api/stop")
      .then((response) => response.json())
      .then((data) => {
        console.log(data); // Log response (can be updated to notify user)
      });
  };

  // Reset input
  const handleReset = () => {
    if (inputRef.current) {
      inputRef.current.value = "";
    }
    setErrorMessage("");
  };

  // Fetch ticket status on initial load or when button is clicked
  const handleFetchStatus = () => {
    fetchTicketStatus();
  };

  return (
    <div className="vendor">
      <fieldset>
        <h1>Vendor Dashboard</h1>
        <div className="input-field">
          <label htmlFor="vendor-input" className="vendor-input">
            Total tickets to release
          </label>
          <input
            type="text"
            className="vendor-input"
            id="vendor-input"
            placeholder="Enter total no of tickets"
            ref={inputRef}
          />
        </div>
        {errorMessage && (
          <p className="error-message" style={{ color: "red" }}>
            {errorMessage}
          </p>
        )}
        <br />
        <div className="start-process">
          {isProcessing ? (
            <>
              <button className="vendor-stop" type="button" onClick={handleStop}>
                Stop Process
              </button>
              <button className="vendor-reset" type="button" onClick={handleReset}>
                Reset
              </button>
            </>
          ) : (
            <button className="vendor-process" type="button" onClick={handleStartProcess}>
              Start Process
            </button>
          )}
        </div>
      </fieldset>

      {/* Ticket Pool Status Section */}
      <div className="ticket-status">
        <h3>Ticket Pool Status:</h3>
        <p>Tickets Available: {ticketStatus.availableTickets || "N/A"}</p>
        <p>Tickets Sold: {ticketStatus.soldTickets || "N/A"}</p>
        <button className="fetch-status" onClick={handleFetchStatus}>
          Update Status
        </button>
      </div>
    </div>
  );
}

export default Vendor;
