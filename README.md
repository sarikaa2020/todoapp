Todo Application (Java GUI + Database):

A Java-based Todo Application built using Swing GUI, DAO design pattern, and database connectivity.
This application allows users to create, update, delete, and manage tasks with persistent storage.

Features:

 Add new todo tasks

 Update existing tasks

 Delete tasks

 View all tasks

 Database-backed persistence

 Interactive Java Swing GUI

 Tech Stack:

| Layer        | Technology         |
| ------------ | ------------------ |
| Language     | Java               |
| UI           | Java Swing         |
| Database     | MySQL (via JDBC)   |
| Build Tool   | Maven              |
| Architecture | DAO Pattern        |
| IDE          | VS Code / IntelliJ |

Project Structure:

todoapp/
│
├── src/main/java/com/todo
│   ├── dao
│   │   └── TodoAppDAO.java
│   │
│   ├── gui
│   │   └── TodoAppGUI.java
│   │
│   ├── model
│   │   └── Todo.java
│   │
│   ├── util
│   │   └── DatabaseConnection.java
│   │
│   └── Main.java
│
├── .vscode
├── target
├── pom.xml
└── README.md

How to Run the Project:

🔹 Prerequisites

->Java 8 or higher

->Maven

->MySQL Database

->Git


