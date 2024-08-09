# Splitwise Clone API

This Spring Boot project serves as a backend API for a clone of Splitwise, a popular expense management application. The API provides endpoints for managing users, groups, expenses, and settlements, facilitating seamless expense tracking and splitting among users.

## Features

- **User Management**: Allows users to register, login, and manage their profiles.
- **Group Management**: Enables users to create, join, and manage groups for shared expenses.
- **Expense Tracking**: Facilitates the creation, editing, and deletion of expenses within groups.

## Technologies Used

- **Spring Boot**: A powerful framework for building Java-based applications, providing a robust development environment.
- **Spring Security**: Utilized for implementing JWT (JSON Web Token) authentication and authorization.
- **Spring Data JPA**: Provides a convenient way to access relational databases, simplifying database interactions.
- **MySQL**: Chosen as the database management system for storing user data, group information, and expenses.
- **JSON Web Tokens (JWT)**: Used for secure authentication by generating tokens for authorized users.

## Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Satwik-Bhardwaj/SplitwiseAppClone.git
   ```

2. **Database Configuration**:
    - Install MySQL and create a database named `Splitwise`.
    - Update `application.properties` with your MySQL username and password:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/Splitwise
      spring.datasource.username=root
      spring.datasource.password=root
      ```

3. **OAuth Configuration**:
    - Obtain credentials from GCP to enable OAuth functionality.
    - Update `application.properties` with the following parameters:
      ```properties
      spring.security.oauth2.client.registration.google.client-id=abc123
      spring.security.oauth2.client.registration.google.client-secret=good123
      ```
    - If you do not require OAuth2 functionality, you can remove the related parameters and the corresponding controllers and services.

4. **Run the Application**:
    - Navigate to the project directory.
    - Execute the following command:
      ```bash
      ./mvnw spring-boot:run
      ```

[//]: # (5. **API Documentation**:)

[//]: # (    - Access the API documentation at `http://localhost:8080/swagger-ui.html`.)

## Usage

- **Authentication**: Use the `/api/v1/auth/login` endpoint to obtain a JWT token by providing valid credentials.
- **Endpoints**: Utilize the provided endpoints to perform various operations such as user registration, group creation, expense management, etc.
- **Authorization**: Ensure proper authorization by including the JWT token in the Authorization header for secured endpoints.

## Contributing

This project is open-source and welcomes contributions from anyone. Feel free to submit a pull request or open an issue if you'd like to contribute.

## Contact

For any inquiries or support, please contact satwikbhardwaj123@gmail.com.

Let me know if you need any more changes!
