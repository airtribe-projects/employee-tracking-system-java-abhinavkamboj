# Employee Management System

This is a Java-based web application built using Spring Boot that provides an Employee Management System with advanced features. The application offers CRUD (Create, Read, Update, Delete) operations for managing employees, departments, and projects, along with secure authentication via OAuth2 and OpenID Connect.

## Features

### 1. CRUD Operations:
- **Employees**: Add, update, delete, and view employees.
- **Departments**: Add, update, delete, and view departments.
- **Projects**: Add, update, delete, and view projects.

### 2. Database Interaction:
- Uses MySQL for data persistence.
- Utilizes Spring Data JPA to interact with the database.
- Custom queries to search employees, calculate project budgets, and find unassigned employees.

### 3. OAuth2.0 and OpenID Connect:
- Integrates Google as an Identity Provider for user authentication.

### 4. Caching:
- Implemented using EhCache for frequently accessed data such as employee lists, department details, and project statuses.

## Getting Started

### Prerequisites
- **Java 17**
- **MySQL**
- **Spring Boot 3.4.0**
- **Maven** or **Gradle**

### Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/employee-management-system.git
   cd employee-management-system
   ```

2. Update application properties:
   - Edit `src/main/resources/application.properties` to match your MySQL database credentials.

3. Build and run the application:
   ```bash
   ./gradlew bootRun
   ```
   Or use Maven:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at:
   - **Frontend**: `http://localhost:8080`
   - **API Endpoints**: `/employees`, `/departments`, `/projects`

### Google OAuth2 Configuration
1. Set up credentials in Google Cloud Console.
2. Update your client ID and client secret in `application.properties`.
3. Ensure the redirect URI matches your server configuration (`http://localhost:8080/login/oauth2/code/google`).

### Testing with Postman
- Obtain a JWT token via the `/login/oauth2/code/google` endpoint.
- Use the token in Postman to access the secured endpoints.
- Example header:
  ```
  Authorization: Bearer YOUR_ACCESS_TOKEN
  ```

## Project Structure
```
- src/
  - main/
    - java/
      - com.example.ems/
        - controller/
          - DepartmentController.java
          - EmployeeController.java
          - ProjectController.java
        - entity/
          - Department.java
          - Employee.java
          - Project.java
        - service/
          - impl/
            - DepartmentServiceImpl.java
            - EmployeeServiceImpl.java
            - ProjectServiceImpl.java
          - DepartmentService.java
          - EmployeeService.java
          - ProjectService.java
        - repository/
          - DepartmentRepository.java
          - EmployeeRepository.java
          - ProjectRepository.java
        - config/
          - CacheConfig.java
          - SecurityConfig.java
        - dto/
          - DepartmentDTO.java
          - EmployeeDTO.java
          - ProjectDTO.java
    - resources/
      - application.properties
```

## Dependencies
- **Spring Boot**: Core framework.
- **Spring Security**: Provides authentication and role-based authorization.
- **Spring Data JPA**: Database interaction.
- **EhCache**: Caching.
- **JUnit** & **Mockito**: Testing.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Contact
For any questions or inquiries, please reach out at your.email@example.com.

