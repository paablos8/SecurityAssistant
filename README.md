# Security Assistant Application

## Description

The Security Assistant Application is a web-based tool that helps esepecially small and medium sized enterprises with the selection of IT-security measures. As smaller companies usually do not have the resources and knowledge to make good security decisions, this assistant should start right there and provide tailor-made recommendations to improve IT security. To be able to provide these individual recommendations, the assistant tries to capture the current individual situation of the companies.

## Features

- Capturing of the current individual IT infrastructure through a input form
![App Screenshot1](src/screenshots/ScreenshotUserForm.png)
- Generation of personalized IT-security recommendations and display on the web
![App Screenshot3](src/screenshots/ScreenshotRecommendations.png)
- Prioritization of the recommendations and description of origin
- Description of the benefits of already implemented security measures
- Calculation of current compliance score
- (Download of the recommendations in a .txt file)
- Confidential treatment of personal user data and secure storing of user information
- Edition and deletion of all company data if required
- Admin console for the providers with detailed user feedback about the application and about the clarity of recommendations
![App Screenshot2](src/screenshots/ScreenshotFeedback.png)
- The recommendations are also enriched with statistical information about the implementation of security measures in similar-sized companies in related industries

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
-At "System variables" double click "PATH", then click "new" and add the path to the bin directory of your Apache Maven download. For example:  `C:\Program Files\apache-maven-3.9.4\bin` (Change version accordingly) <br>
-Then create a new User Variable on top with the Variable Name: `MAVEN_HOME` and Variable Value: `C:\Program Files\apache-maven-3.9.4` (Change to the right version or better just copy the path from your explorer) --> Then confirm by clicking "Ok" three times.

3. Go to the IDE of your choice and clone the repository in the command line
`git clone https://gitlab.com/SchimSlady/SecurityAssistant`

4. Go the directory folder just cloned by using `cd <application_name>` 

5. Before your run the application for the first time, the 'file path' variable of your locally stored security ontology must be changed manually. In the class "/SecurityAssistant/src/main/java/com/example/ontology/InitJena.java" change the filePath in line 58 to your locally stored security ontology. 

6. Then build the application: `mvn clean package`

7. Open your web browser and navigate to `http://localhost:8080` to access the application.

## Usage

- To generate a recommendation you have to provide a detailed overview over your current infrastsructure and accept the processing of your user data
- You can see the displayed recommendations after a loading time. Press more information to get further insights on the recommendation titles
- Hover the red exlamation marks to see more statistical information about the implementation of certain security measures of similar companies
- Insert your username in the delete/edit input field to delete/edit your input data
- Provide the provider with feedback by giving feedback about the clarity of the input form or the recommendations through the emojis

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

