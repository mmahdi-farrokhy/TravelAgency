# Travel Agency Flight Booking Program
NOTE: To run this program make these steps.
 1. Install [javafx-sdk-17.0.7](https://download2.gluonhq.com/openjfx/17.0.7/openjfx-17.0.7_windows-x64_bin-sdk.zip).
 2. Open the project in Intellij Idea, then go to Settings -> Appearance & Bahavior -> Path Variables. Click the + button, set Name as PATH_TO_FX and Value as location of javafx-sdk-17.0.7 folder in your computer.
 3. In the project open "Edit Configuration" window, under "Application" select "Main.java" file in the main package.
 4. Click on "Modify Options", choose "Add VM Options" and write this command in the "VM Options" box without the quotes:
    "--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls"
5. Click OK and open "Project Structure".
6. In "Libraries" tab click the + button and navigate to "Lib" folder in the javafx-sdk-17.0.7 directory.
7. Choose "Lib" folder and click OK.
8. Now the program will run properly.

___
This program is designed to allow users to book flights through a user-friendly interface.
The program is built using:
- Back-End: Java 17
- GUI: JavaFX
- Database: MySQL

## Main Window

The main window of the application has 4 buttons and 3 text fields:
- Username
- Full Name
- Phone Number

## Buttons

The following buttons are available on the main window:

### Login / Sign Up

Clicking this button will open the login page. If the user does not have an account, they can click the link on the page that says "Don't have an account yet? Click here to create one." to create a new user account.

### Edit Customer

If the user has logged in, they will be able to edit their information by clicking the "Edit Customer" button. This will open a page where the user can update their information and save it to the database. If no user is logged in, an error message will be displayed.

### Flights List

Clicking the "Flights List" button will display a page that contains a list of available flights. Users can filter flights by origin airport, destination airport, departure date and arrival date. Once a flight is selected, users can click the "Book" button to proceed to the booking page.

### Booking Page

The booking page displays the details of the flight, including the user's information, flight information, number of tickets, distance, and price of the order. Clicking the "Register The Order" button will save the order to the database.

### Order History

Clicking the "Order History" button will display a page containing a table that lists all registered orders.
