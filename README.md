# Security Assistant Application

![App Screenshot1](src\screenshots\ScreenshotUserForm.png)
![App Screenshot2](src\screenshots\ScreenshotFeedback.png)
![App Screenshot3](src\screenshots\ScreenshotRecommendations.png)

## Description

The Security Assistant Application is a web-based tool that helps manage security-related tasks and recommendations for different companies. It allows users to track security infrastructure, password change policies, and risk mitigation strategies.

## Features

- Manage Security Infrastructure: Add, edit, and delete security infrastructure details for various companies.
- Track Password Change Policies: Monitor password change policies for each company and their frequency.
- Risk Mitigation Strategies: Review risk mitigation strategies for non-implemented security measures.
- Overall Feedback: Collect and view overall feedback from users regarding the application.

## Technologies Used

- Java
- Apache Jena
- Spring Boot
- Thymeleaf (for HTML templates)
- MySQL (or your preferred database)
- Docker (optional - for containerization)

## Installation and Setup

1. Clone the repository:
git clone https://gitlab.com/SchimSlady/SecurityAssistant


2. Build the project using Maven:
`cd security-assistant`
`mvn package`


3. Set up the database:
- Create a MySQL database with the appropriate name and credentials.
- Update the `application.properties` file with your database details.

4. Run the application:
`mvn spring-boot:run`


5. Open your web browser and navigate to `http://localhost:8080` to access the application.

## Usage

- Log in to the application using your credentials or create a new account.
- Once logged in, you can manage security infrastructure, password change policies, and view overall feedback.
- Use the navigation menu to access different sections of the application.

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please submit an issue or a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

