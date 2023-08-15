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

1. Go to https://maven.apache.org/download.cgi and download the Maven Zip file. Afterwards unzip it to your "C: driver" into the 
`C:\Program Files`

2. Set up Path Variables <br>
-Click on the search bar on the bottom left of your screen and search for "edit the system variables" <br>
-Click "Environment Variables..." on the bottom right <br>
-At "System variables" double click "PATH", then click "new" and add the path to the bin directory of your Apache Maven download. For example:  `C:\Program Files\apache-maven-3.9.4\bin` (Change version accordingly) and press "OK". <br>
-Then create a new User Variable on top with the Variable Name: `MAVEN_HOME` and Variable Value: `C:\Program Files\apache-maven-3.9.4` (Change to the right version or better just copy the path from your explorer) --> Then confirm by clicking "Ok" three times.

3. Download Git from https://git-scm.com/downloads and download the appropriate Git version for your OS. <br>
- execute the Git Installer and accept every pre-defined setting while clicking through the Installer.

4. Go to the IDE of your choice and clone the repository in the command line
`git clone https://gitlab.com/SchimSlady/SecurityAssistant`

5. Go the directory folder just cloned by using `cd <application_name>` and open the SecurityAssistant folder in your IDE.


6. The security ontology used for this application is stored in the folder `/SecurityAssistant/src/main/java/com/example/ontology/files`. <br>
Before your run the application for the first time, the `file path` variable of the locally stored security ontology must be changed manually. In the class `/SecurityAssistant/src/main/java/com/example/ontology/InitJena.java` change the filePath in line 58 to the absolute path to your locally stored security ontology. <br>
This should look like this: `filePath = "file:/C:/Users/Wiwi-Admin/SecurityAssistant/src/main/java/com/example/ontology/files/Mueller2023_Security_shortened.rdf"; <br>
It's important that forward slashes "/" are used!

7. Then build the application: `mvn clean package`

8. Run the main method in `SecurityAssistant/src/main/java/com/example/SecurityAssistant`.

9. Open your web browser and navigate to `http://localhost:8080` to access the application.

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

