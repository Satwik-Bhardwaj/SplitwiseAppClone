# Splitwise Clone API

This Spring Boot project serves as a backend API for a clone of Splitwise, a popular expense management application. The API provides endpoints for managing users, groups, expenses, and settlements, facilitating seamless expense tracking and splitting among users.

## Features

- **User Management**: Allows users to register, login, and manage their profiles.
- **Group Management**: Enables users to create, join, and manage groups for shared expenses.
- **Expense Tracking**: Facilitates the creation, editing, and deletion of expenses within groups.
- **Settlements**: Automatically calculates and records settlements between users to balance expenses.

## Technologies Used

- **Spring Boot**: A powerful framework for building Java-based applications, providing a robust development environment.
- **Spring Security**: Utilized for implementing JWT (JSON Web Token) authentication and authorization.
- **Spring Data JPA**: Provides a convenient way to access relational databases, simplifying database interactions.
- **MySQL**: Chosen as the database management system for storing user data, group information, and expenses.
- **JSON Web Tokens (JWT)**: Used for secure authentication by generating tokens for authorized users.

## Setup

1. **Clone the Repository**:
   ```
   git clone https://github.com/Satwik-Bhardwaj/SplitwiseAppClone.git
   ```

2. **Database Configuration**:
    - Install MySQL and create a database named `Splitwise`.
    - Update `application.properties` with your MySQL username and password.

3. **Run the Application**:
    - Navigate to the project directory.
    - Execute the following command:
      ```
      ./mvnw spring-boot:run
      ```

[//]: # (4. **API Documentation**:)

[//]: # (    - Access the API documentation at `http://localhost:8080/swagger-ui.html`.)

## Usage

- **Authentication**: Use the `/api/v1/auth/login` endpoint to obtain a JWT token by providing valid credentials.
- **Endpoints**: Utilize the provided endpoints to perform various operations such as user registration, group creation, expense management, etc.
- **Authorization**: Ensure proper authorization by including the JWT token in the Authorization header for secured endpoints.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, feel free to submit a pull request or open an issue.

[//]: # (## License)

[//]: # ()
[//]: # (This project is licensed under the [MIT License]&#40;LICENSE&#41;.)

## Contact

For any inquiries or support, please contact satwikbhardwaj123@gmail.com.
