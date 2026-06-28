# 🧠 MindEase - Web-Based Mental Health Support & Mood Tracking System

## 📌 Project Overview
This project is a full-stack **Mental Health Support and Mood Tracking System** built as a Group Coursework for the CS5054NT - Advanced Programming and Technologies module.

The goal is to provide an online platform that helps users track their emotional health, access mental health resources, and book counselling sessions, while giving administrators full control over users, counselors, resources, and reports.

---

## 📊 Key Objectives
- Allow users to log daily moods and track emotional history
- Provide access to categorized mental health resources
- Enable users to bookmark resources and book counselling appointments
- Allow admins to manage users, counselors, resources, categories, appointments and messages
- Build a secure, role-based web application using MVC architecture

---

## 👥 User Roles
- **Admin** — Manage users, counselors, resources, categories, appointments, messages and reports
- **User** — Log moods, view mood history, browse and bookmark resources, book appointments, contact support

---

## 🛠️ Tech Stack
- **Language:** Java
- **Architecture:** MVC (Servlets, DAO, Service layers)
- **Frontend:** JSP, HTML, CSS, JavaScript
- **Database:** MySQL
- **Security:** Session management, password hashing, form validation, authentication filter (RBAC)

---

## 📂 Project Structure

```
MINDEASE/
├── database/
│   └── mindease.sql
├── src/main/
│   ├── java/com/mindease/
│   │   ├── controller/
│   │   ├── dao/
│   │   ├── filter/
│   │   ├── model/
│   │   ├── service/
│   │   └── util/
│   └── webapp/
│       ├── css/
│       ├── images/
│       ├── js/
│       ├── WEB-INF/
│       │   ├── views/
│       │   │   ├── admin/
│       │   │   ├── auth/
│       │   │   ├── error/
│       │   │   ├── shared/
│       │   │   └── user/
│       │   └── web.xml
│       ├── about.jsp
│       └── index.jsp
└── Code.iml
```

---

## 📊 Features & Modules

### 📌 Admin Panel
- Admin dashboard with overview statistics
- User management
- Counselor management
- Category management
- Resource management
- Appointment management
- Message management
- Reports generation

---

### 📌 User Portal
- Register and login
- Log daily mood with notes and tags
- View mood history and weekly trends
- Browse mental health resources by category
- Bookmark resources
- Book and manage counselling appointments
- Contact support

---

## 🚀 Getting Started

### Database Setup
```bash
mysql -u root -p < database/mindease.sql
```
Update database connection details in `DBConnection.java` (`src/main/java/com/mindease/util/`).

### Run the Project
1. Import the project into your IDE (e.g., IntelliJ IDEA / Eclipse) as a Java EE web application.
2. Configure a servlet container (e.g., Apache Tomcat).
3. Deploy and run the application.
4. Access via `http://localhost:8080/`.

---

## 🎨 Design Theme
- Clean and calming UI suited for mental health context
- Separate desktop and mobile wireframes for every page
- Responsive and mobile-friendly layout
- Focus on usability, accessibility and clarity

---

## 🚀 Key Features
- Role-based authentication and access control
- Daily mood logging with mood tags and notes
- Mood history reporting with weekly trends
- Resource browsing, categorization and bookmarking
- Counsellor appointment booking and management
- Admin reporting and full system oversight

---

## 🛠️ Tools Used
- Java (Servlets, JSP)
- MySQL
- HTML, CSS, JavaScript
- Apache Tomcat

---

## 📌 Learning Outcomes
- Full-stack Java web application development using MVC
- Database design and normalization (up to 3NF)
- Role-based access control implementation
- Session management, validation and exception handling
- Wireframing and UI/UX design for web applications

---

## 👤 Author
**Sadikshya Karki**

Originally developed as a group project by Team **MindPatch**: Aakash Koirala, Aaryan Koirala, Sadikshya Karki, Neha Bhagat.

