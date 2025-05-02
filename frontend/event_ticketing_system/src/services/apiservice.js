import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const apiService = {
    // Customers
    getCustomers: () => axios.get(`${API_BASE_URL}/customers`),
    getCustomerById: (id) => axios.get(`${API_BASE_URL}/customers/${id}`),
    addCustomer: (customer) => axios.post(`${API_BASE_URL}/customer/add`, customer),
    updateCustomer: (id, customer) => axios.put(`${API_BASE_URL}/customer/update/${id}`, customer),
    deleteCustomer: (id) => axios.delete(`${API_BASE_URL}/customer/delete/${id}`),

    // Tickets
    getTickets: () => axios.get(`${API_BASE_URL}/tickets`),
    getTicketById: (id) => axios.get(`${API_BASE_URL}/tickets/${id}`),
    addTicket: (ticket) => axios.post(`${API_BASE_URL}/ticket/add`, ticket),
    updateTicket: (id, ticket) => axios.put(`${API_BASE_URL}/ticket/update/${id}`, ticket),
    deleteTicket: (id) => axios.delete(`${API_BASE_URL}/ticket/delete/${id}`),

    // Vendors
    getVendors: () => axios.get(`${API_BASE_URL}/vendors`),
    getVendorById: (id) => axios.get(`${API_BASE_URL}/vendors/${id}`),
    addVendor: (vendor) => axios.post(`${API_BASE_URL}/vendor/add`, vendor),
    updateVendor: (id, vendor) => axios.put(`${API_BASE_URL}/vendor/update/${id}`, vendor),
    deleteVendor: (id) => axios.delete(`${API_BASE_URL}/vendor/delete/${id}`),

    // Start/Stop System
    startSystem: () => axios.get(`${API_BASE_URL}/api/start`),
    stopSystem: () => axios.get(`${API_BASE_URL}/api/stop`),
};

export default apiService;
