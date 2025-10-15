1️⃣ Create Database (if not exists)
CREATE DATABASE IF NOT EXISTS calculator_db;

USE calculator_db;

2️⃣ Create Table
CREATE TABLE IF NOT EXISTS calculations (
    id INT AUTO_INCREMENT PRIMARY KEY,       -- Unique ID for each calculation
    num1 BIGINT NOT NULL,                     -- First number
    num2 BIGINT NOT NULL,                     -- Second number
    operation VARCHAR(10) NOT NULL,          -- Operation type (add, sub, mul)
    result BIGINT NOT NULL,                   -- Result of the operation
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Auto timestamp
);

Technology Stack

Frontend:

HTML5 – Structure web pages and forms

CSS3 – Styling, colors, and layout

Bootstrap 5 (optional) – Responsive design and UI components

Backend:

Java 11+ – Core programming language

Java Servlet (Servlet API) – Handle HTTP requests and responses

JSP (JavaServer Pages) – Render dynamic content

Database:

MySQL 8.x – Store calculation data

JDBC (Java Database Connectivity) – Connect backend with MySQL

Build & Dependency Management:

Maven – Build project, manage dependencies, and generate WAR file

Server / Deployment:

Apache Tomcat 9 – Web application server to deploy WAR files

