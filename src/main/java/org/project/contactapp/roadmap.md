Roadmap
1. Project Setup
   Ensure the project is a Maven project.
   Verify the pom.xml file includes all necessary dependencies (JavaFX, SQLite, JUnit, etc.).
2. Database Setup
   Ensure the SQLite database is correctly set up with the person table.
   Update the dbConnection class to initialize the database if it doesn't exist.
3. JavaFX GUI
   Home Page: Create a home page with buttons to navigate to different functionalities (View Contacts, Add Contact).
   View Contacts Page: Create a page to list all persons in the database.
   Add a TableView to display contacts.
   Add a search bar to filter contacts.
   Add buttons to add, update, and delete contacts.
   Add Contact Page: Create a form to add a new person.
   Update Contact Page: Create a form to update an existing person's details.
   Delete Contact Functionality: Ensure contacts can be deleted from the View Contacts page.
4. CRUD Operations
   Create: Implement functionality to add a new person to the database.
   Read: Implement functionality to list all persons from the database.
   Update: Implement functionality to update an existing person's details.
   Delete: Implement functionality to delete a person from the database.
5. Validation and Error Handling
   Add input validation for forms (e.g., required fields, valid email format).
   Implement error handling for database operations.
6. Testing
   Write unit tests for database access methods using JUnit.
   Ensure all resources are properly closed after use.
7. Documentation
   Create a README.md file explaining the project, setup instructions, and usage.
   Include any necessary SQL scripts for database setup.
8. Additional Features (Optional)
   Add functionality to upload and display a photo for each person.
   Implement categories for contacts (e.g., friend, family, work).
   Add filtering and exporting options based on categories.