# FYP Cloud Hosted Membership System

A full-stack cloud-based membership management system built using Spring Boot.  
This system provides both user and admin portals and is deployed on AWS with scalable architecture.

---

## 🚀 Features

### 👤 Member Portal
- User registration & login
- Subscribe to membership plans
- Redeem vouchers
- View membership status and benefits

### 🛠 Admin Portal
- Dashboard with analytics charts
- Manage members, vouchers, and activities
- Monitor system usage and engagement

### ⚙️ Core Functionalities
- Role-based access control
- Member level processing logic
- Point accumulation & redemption system
- Secure authentication using Spring Security

---

## 🛠 Tech Stack
- Java
- Spring Boot
- Thymeleaf
- MySQL

### ☁️ Cloud Architecture (AWS)
- EC2 (Application Hosting)
- RDS (Database)
- ALB (Load Balancer)
- ASG (Auto Scaling Group)

---

## 🌐 Live Demo
👉 http://howziyang.servemp3.com/member/login

You can directly access the system without local setup.

---

## 📦 Project Structure
- `controller` – Handles HTTP requests
- `service` – Business logic layer
- `repository` – Data access layer
- `entity` – Domain models

---

## 💻 Local Setup (Optional)

1. Configure MySQL (e.g. XAMPP)
2. Import database: membership_db.sql
3. Rename `application-example.properties` to `application.properties` and update your database credentials before running the project.
4. Run: mvn spring-boot:run

## 🗄 Database
- MySQL (Local & AWS RDS supported)
- SQL script included: `membership_db.sql`

---

## 📌 Highlights
- Designed with scalable cloud architecture (ALB + ASG)
- Implements secure authentication & authorization
- Demonstrates full-stack development with real-world deployment

---

## 👨‍💻 Author
Jeffrey How
