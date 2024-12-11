
# Event Ticketing System

The project is a Real-Time Event Ticketing System, focusing on the use of Object-Oriented Programming (OOP) principles and the Producer-Consumer pattern to simulate a dynamic ticketing environment. The system will handle concurrent ticket releases and purchases while maintaining data integrity. The project includes a CLI, Frontend GUI and Backend integrated with APIs.


## Technology Stack

**Frontend:** Vite, React, JavaScript (fetch API)

**Backend:** Springboot, Java

**Communication:** HTTP with SSE (Server-Sent Events)


## Getting Started

**Prerequisites:**

    1. Node.js (>=14.x)
    2. npm
    3. Java Development Kit (JDK) 17 or higher
    4. Maven


## Frontend Setup

**1. Navigate to the `frontend` directory**

```bash
  cd frontend
```

**2. Install dependencies**

```bash
  npm install
```

**3. Start the development server:**

```bash
  npm run dev
```

The Vite app should now be running on `http://localhost:5173`
## Features

- Multi Threaded producer-consumer CLI
- Frontend GUI
- RESTful API: Supports CRUD operations (GET, POST, PUT, DELETE) for ticket management.
- Optimized Development: Vite and React provide a fast and modular development environment

## Troubleshooting

- **CORS** Issues:  If the frontend and backend run on different origins, configure CORS in the backend:
```Spring Boot
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5173");
            }
        };
    }
}
```
- **Network Debugging:** Use browser developer tools to inspect API requests and SSE connections.


