Exchange Rate Web Application

Fullstack currency converter and exchange rate history viewer using real-time data from the **European Central Bank**.

Features

- Daily ECB exchange rate sync
- Currency conversion calculator
- Last 90-day history chart
- Playwright e2e testing
- H2 in-memory DB
- Quartz daily job
- Secure backend with Spring Security

Used tech 
---
| Layer       | Tech                             | Why                                |
|-------------|----------------------------------|-------------------------------------|
| Frontend    | Angular, Chart.js, Playwright    | Modern UI + Charts + e2e testing    |
| Backend     | Spring Boot, Quartz, JPA         | Robust APIs, scheduling, persistence |
| Database    | H2 (in-memory)                   | Lightweight dev-friendly DB         |
| Security    | Spring Security                  | Basic protection setup              |
| Tests       | JUnit 5, Mockito, Playwright     | Test coverage (unit + e2e)     |
---

Download the project and run in terminal 

**docker-compose up --build**

**Frontend: http://localhost:4200**

**Backend: http://localhost:8080**
