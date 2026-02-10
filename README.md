# BUILD2D
A Java-based desktop application for creating and managing 2D floor plans with a clean, modern user interface using FlatLaf.

Features

Modern and user-friendly interface using FlatLaf
Secure login system
Interactive room creation and management
Drag-and-drop room positioning
Room type selection (Bedroom, Kitchen, Living Room, etc.)
Room rotation and modification
Save and load floor plans
Grid-based layout for accurate placement
Collision detection to prevent overlapping rooms
Prerequisites (Windows)

Ensure the following are installed on your Windows system:

Java Development Kit (JDK) 17 or higher
Apache Maven
Git (optional but recommended)
Installation Guide (Windows)

Install Java JDK 17+
Download OpenJDK from: https://adoptium.net/
Run the installer and complete the setup
Verify installation: java -version
Install Apache Maven
Download Maven from: https://maven.apache.org/download.cgi
Extract it to a directory, for example: C:\Program Files\Apache\maven
Set Environment Variables:

M2_HOME → C:\Program Files\Apache\maven
Add to PATH: %M2_HOME%\bin
Verify installation: mvn -version

Get the Project Files
You can either clone the repository or download it as a ZIP file.

Using Git (recommended):

git clone <REPOSITORY_URL> cd 2D_Floor_Planner

Or download ZIP:

Download the ZIP from GitHub
Extract it
Open Command Prompt inside the extracted folder
Building the Project (Windows)

Run the following command from the project root directory:

mvn clean install

If this command completes without errors, the build was successful.

Running the Application

Start the application using:

mvn exec:java

Default Login Credentials

Username: admin
Password: admin
Usage Guide

Login
Launch the application
Enter login credentials
Click Login or press Enter
Creating Rooms
Select a room type using the Room Type option
Set room dimensions using Set Dimension
Set the room position using Set Position
Click Add Room to place it on the grid
Modifying Rooms
Click on a room to select it
Use Properties to view room details
Use Modify options to:
Rotate the room
Remove the room
Drag rooms to reposition them
Saving and Loading
Click Download to save the floor plan
Click Open File to load an existing plan
Floor plans are saved in .2ds format
Additional Functionality
Grid system for precise alignment
Automatic door placement between adjacent rooms
Collision detection to avoid overlapping rooms
Project Structure

Login.java – Handles user authentication
RoomPlan2.java – Core application logic and user interface
pom.xml – Maven project configuration
Development (Windows)

To modify or extend the application:

Open the project in an IDE (IntelliJ IDEA, Eclipse, or VS Code)
Ensure Maven dependencies are resolved
Run the main class: Login.java
Troubleshooting (Windows)

Java Not Recognized

Verify JAVA_HOME is set correctly
Add the following to PATH: %JAVA_HOME%\bin
Restart Command Prompt
Maven Not Recognized

Verify M2_HOME is set correctly
Ensure %M2_HOME%\bin is added to PATH
Restart Command Prompt
Dependencies Not Downloading

mvn clean install -U

UI Issues

Ensure Java version is 17 or higher
Confirm FlatLaf dependency exists in pom.xml
Try rebuilding: mvn clean mvn compile mvn exec:java
License

This project is licensed under the MIT License. See the LICENSE file for details.
