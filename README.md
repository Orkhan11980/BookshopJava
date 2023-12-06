# Bookshop Database Management System


### Overview
This project is a Java-based application developed to manage the operations of a bookshop. It focuses on database interaction using JDBC and covers various aspects of database management, from basic CRUD operations to more complex transaction handling and database metadata retrieval. The application is designed as a simple yet comprehensive tool for managing a bookshop's inventory and orders.

## Features
- CRUD operations for books and authors.
- Transaction management for placing orders.
- Retrieval of database metadata like table names, column details, primary and foreign keys.
- Error handling with custom exceptions.

## Prerequisites
- Java JDK 11
- IntelliJ IDEA
- Maven
- A relational database like MySQL, PostgreSQL, etc.

## Installation and Setup
1. **Clone the Repository: " https://github.com/Orkhan11980/BookshopJava.git " **:

2. **Open the Project in IntelliJ IDEA**:
- Launch IntelliJ IDEA.
- Choose `Open` and select the cloned project directory.

3. **Database Setup**:
- Ensure your database server is running.
- Create a new database for the project.
- Run the SQL scripts located in `[location-of-sql-scripts]` to set up the necessary tables.

4. **Configure Database Connection**:
- Update the database connection details in `DatabaseConnectionManager.java` with your database credentials and URL.

5. **Build the Project with Maven:**
   `mvn clean install`

6. **To run the application, use the following command:**
   `mvn exec:java -Dexec.mainClass="org.example.Main"`


## Usage
- The `Main` class serves as the entry point of the application. After succesfully running the app  follow the on-screen prompts to interact with the system. The menu will guide you through various operations such as:

Managing authors, books, customers, orders, and order details.
Performing database metadata operations like listing tables, columns, primary keys, and foreign keys.
```java
//Choose an option:
//1: Manage Authors
//2: Manage Books
//3: Manage Customers
//4: Manage Orders
//5: Manage Order Details
//6: Database Metadata Operations
//7: Exit
//Enter choice: 

```



### Key Features
- **CRUD Operations**: The application supports creating, reading, updating, and deleting records for books and authors in the bookshop's database.
- **Transaction Management**: It includes sophisticated transaction management to handle order placements, ensuring all database operations either complete successfully as a whole or roll back in case of an error.
- **Database Metadata Analysis**: The application can retrieve and display metadata from the database, such as table names, column details, and key constraints, which is crucial for database inspection and debugging.
- **Custom Exception Handling**: To enhance error handling, the project utilizes custom exceptions, providing more informative error messages and a cleaner way to handle SQL exceptions.

### Technical Details
- **Technology Stack**: The project is built using Java with Maven for dependency management. It is developed and tested in IntelliJ IDEA.
- **Database Compatibility**: It is compatible with various relational databases like MySQL and PostgreSQL. The database interactions are managed using standard JDBC APIs.
- **Configuration**: Database connection details are managed in `DatabaseConnectionManager.java`, which needs to be configured with the correct database URL and credentials.
