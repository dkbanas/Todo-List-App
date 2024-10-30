# ✅ TodoList App

> *Backend application for managing task lists, built in process of learning Spring Boot.*

## ℹ️ Overview

TodoList App is a RESTful backend service for managing personal task lists, providing authentication and personalized task management features. Users can create their own lists, add tasks, and manage them efficiently. Built with Spring Boot and JWT authentication, it offers a secure and organized environment for handling todos.

## 🌟 Key Features

- **User Authentication**: Secure registration and login using JWT token-based authentication.
- **Personal Task Lists**: Each user has access to their own private lists, containing customizable tasks.
- **Task Management**: Add items (tasks) to each list, including task details such as title, description, creation date, and completion status.
- **Pagination**: Retrieve lists with pagination, specifying the number of items per page and the page to display.
- **Sorting**: Sort task lists by date or title for easy management.

## 🛠️ How to Run

1. **Clone the repository** to your local machine.
2. **Set up the database**:
   - Create a `todo_db` database (e.g., in pgAdmin4) and import the `todo_db.sql` file.
3. **Configure Environment**:
   - Create a `.env` file in the root directory and add `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET`.
4. **Import API Collection**:
   - Import `ToDoList.postman.json` into Postman for easy testing.
5. **Run the Application**:
   - Open the project in your IDE and start the Spring Boot application.

## 📚 Technologies Used

- **Spring Boot** - for backend REST API
- **PostgreSQL** - for database management
- **JWT** - for secure user authentication
- **Postman** - for API testing (optional)

## 🚀 Next Steps

Future improvements may include implementing a frontend interface.
