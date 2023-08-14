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

1. Go to https://www.docker.com/ and download for your operating system. Then double click the download to install the application.

2. Restart your Computer.

3. (Sometimes necessary) command line: `wsl --update`

4. Create / Sign in with your Docker account inside the Docker Desktop App

5. Go to https://maven.apache.org/download.cgi and download the Maven Zip file. Afterwards unzip it to your "C: driver" into the 
`C:\Program Files`

6. Set up Path Variables
-Click on the search bar on the bottom left of your screen and search for "edit the system variables"
-Click "Environment Variables..." on the bottom right
-At "System variables" double click "PATH", then click "new" and add the path to the bin directory of your Apache Maven download. For example:  `C:\Program Files\apache-maven-3.9.4\bin` (Change version accordingly)
-Then create a new User Variable on top with the Variable Name: `MAVEN_HOME` and Variable Value: `C:\Program Files\apache-maven-3.9.4` (Change to the right version or better just copy the path from your explorer) --> Then confirm by clicking "Ok" three times.

7. Go to the IDE of your choice and clone the repository in the command line
`git clone https://gitlab.com/SchimSlady/SecurityAssistant`

8. Go the directory folder just cloned by using `cd <application_name>` 

9. Before your run the application for the first time, the 'file path' variable of your locally stored security ontology must be changed manually. In the class "/SecurityAssistant/src/main/java/com/example/ontology/InitJena.java" change the filePath in line 58 to your locally stored security ontology. 

10. Then build the application: `mvn clean package`

11. Build the Docker image by using `docker build -t securityassistant .` (You can also use a different name instead of securityassistant)

12. Run the application `docker run -p 8080:8080 securityassistant`

13. Open your web browser and navigate to `http://localhost:8080` to access the application.

## Usage

- Log in to the application using your credentials or create a new account.
- Once logged in, you can manage security infrastructure, password change policies, and view overall feedback.
- Use the navigation menu to access different sections of the application.

## Important things to know
- The following questions in the User Input Form are currently not provided with any functionality in the back-end:
    - "Wo werden die Daten bei Ihnen abgelegt?"
    - "Arbeiten Sie mit einem externen Sicherheitsdienstleister zusammen?"
    - "Bitte spezifizieren Sie die Anzahl PCs:"
    - "Haben Sie einen Drucker im Einsatz der mit dem Internet verbunden ist?"
    


## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please submit an issue or a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

