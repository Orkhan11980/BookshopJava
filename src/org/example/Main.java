package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.model.Authors;
import org.example.util.DatabaseConnection;
import org.example.util.DatabaseMetadataUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {



        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("Choose an option:");
                System.out.println("1: Manage Authors");
                System.out.println("2: Manage Books");
                System.out.println("3: Manage Customers");
                System.out.println("4: Manage Orders");
                System.out.println("5: Manage Order Details");
                System.out.println("6: Database Metadata Operations");
                System.out.println("7: Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        manageAuthors(scanner);
                        break;
                    case 2:
                        manageBooks(scanner);
                        break;
                    case 3:
                        manageCustomers(scanner);
                        break;
                    case 4:
                        manageOrders(scanner);
                        break;
                    case 5:
                        manageOrderDetails(scanner);
                        break;
                    case 6:
                        manageDatabaseMetadata(scanner);
                        break;
                    case 7:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }

    private static void manageAuthors(Scanner scanner) {
        AuthorDAOImpl authorDao = new AuthorDAOImpl();

        boolean running = true;
        while (running) {
            System.out.println("Author Management:");
            System.out.println("1: Add Author");
            System.out.println("2: View All Authors");
            System.out.println("3: Update Author");
            System.out.println("4: Delete Author");
            System.out.println("5: Return to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addAuthor(scanner, authorDao);
                    break;
                case 2:
                    viewAllAuthors(authorDao);
                    break;
                case 3:
                    updateAuthor(scanner, authorDao);
                    break;
                case 4:
                    deleteAuthor(scanner, authorDao);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addAuthor(Scanner scanner, AuthorDAOImpl authorDao) {
        System.out.print("Enter author name: ");
        String name = scanner.nextLine();
        System.out.print("Enter author bio: ");
        String bio = scanner.nextLine();

        Authors author = new Authors();
        author.setName(name);
        author.setBio(bio);

        authorDao.addAuthor(author);
    }

    private static void viewAllAuthors(AuthorDAOImpl authorDao) {
        List<Authors> authors = authorDao.getAllAuthors();
        for (Authors author : authors) {
            System.out.println("ID: " + author.getAuthorID() + ", Name: " + author.getName() + ", Bio: " + author.getBio());
        }
    }

    private static void updateAuthor(Scanner scanner, AuthorDAOImpl authorDao) {
        System.out.print("Enter author ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new bio: ");
        String bio = scanner.nextLine();

        Authors author = new Authors();
        author.setAuthorID(id);
        author.setName(name);
        author.setBio(bio);

        authorDao.updateAuthor(author);
    }

    private static void deleteAuthor(Scanner scanner, AuthorDAOImpl authorDao) {
        System.out.print("Enter author ID to delete: ");
        int id = scanner.nextInt();

        authorDao.deleteAuthor(id);
    }

    private static void manageBooks(Scanner scanner) {
        BookDAOImpl bookDao = new BookDAOImpl();

        boolean running = true;
        while (running) {
            System.out.println("Book Management:");
            System.out.println("1: Add Book");
            System.out.println("2: View All Books");
            System.out.println("3: Update Book");
            System.out.println("4: Delete Book");
            System.out.println("5: Return to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook(scanner, bookDao);
                    break;
                case 2:
                    viewAllBooks(bookDao);
                    break;
                case 3:
                    updateBook(scanner, bookDao);
                    break;
                case 4:
                    deleteBook(scanner, bookDao);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addBook(Scanner scanner, BookDAOImpl bookDao) {
        System.out.print("Enter Author ID: ");
        int authorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Stock: ");
        int stock = scanner.nextInt();

        Books book = new Books();
        book.setAuthorID(authorId);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStock(stock);

        bookDao.addBook(book);
    }

    private static void viewAllBooks(BookDAOImpl bookDao) {
        List<Books> books = bookDao.getAllBooks();
        for (Books book : books) {
            System.out.println("ID: " + book.getBookID() + ", Title: " + book.getTitle() + ", Author ID: " + book.getAuthorID() +
                    ", ISBN: " + book.getIsbn() + ", Price: " + book.getPrice() + ", Stock: " + book.getStock());
        }
    }

    private static void updateBook(Scanner scanner, BookDAOImpl bookDao) {
        System.out.print("Enter Book ID to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new Author ID: ");
        int authorId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Stock: ");
        int stock = scanner.nextInt();

        Books book = new Books();
        book.setBookID(bookId);
        book.setAuthorID(authorId);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStock(stock);

        bookDao.updateBook(book);
    }

    private static void deleteBook(Scanner scanner, BookDAOImpl bookDao) {
        System.out.print("Enter Book ID to delete: ");
        int bookId = scanner.nextInt();

        bookDao.deleteBook(bookId);
    }


    private static void manageCustomers(Scanner scanner) {
        CustomerDAOImpl customerDao = new CustomerDAOImpl();

        boolean running = true;
        while (running) {
            System.out.println("Customer Management:");
            System.out.println("1: Add Customer");
            System.out.println("2: View All Customers");
            System.out.println("3: Update Customer");
            System.out.println("4: Delete Customer");
            System.out.println("5: Return to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCustomer(scanner, customerDao);
                    break;
                case 2:
                    viewAllCustomers(customerDao);
                    break;
                case 3:
                    updateCustomer(scanner, customerDao);
                    break;
                case 4:
                    deleteCustomer(scanner, customerDao);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addCustomer(Scanner scanner, CustomerDAOImpl customerDao) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Customers customer = new Customers();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setAddress(address);

        customerDao.addCustomer(customer);
    }

    private static void viewAllCustomers(CustomerDAOImpl customerDao) {
        List<Customers> customers = customerDao.getAllCustomers();
        for (Customers customer : customers) {
            System.out.println("ID: " + customer.getCustomerID() + ", Name: " + customer.getFirstName() + " " +
                    customer.getLastName() + ", Email: " + customer.getEmail() + ", Address: " + customer.getAddress());
        }
    }

    private static void updateCustomer(Scanner scanner, CustomerDAOImpl customerDao) {
        System.out.print("Enter customer ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        Customers customer = new Customers();
        customer.setCustomerID(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setAddress(address);

        customerDao.updateCustomer(customer);
    }

    private static void deleteCustomer(Scanner scanner, CustomerDAOImpl customerDao) {
        System.out.print("Enter customer ID to delete: ");
        int id = scanner.nextInt();

        customerDao.deleteCustomer(id);
    }


    private static void manageOrders(Scanner scanner) {
        OrderDAO orderDao = new OrderDAOImpl();
        OrderDetailsDAO orderDetailsDao = new OrderDetailsDAOImpl();

        boolean orderManagementMenu = true;
        while (orderManagementMenu) {
            System.out.println("Order Management Menu:");
            System.out.println("1: Add Order");
            System.out.println("2: View All Orders");
            System.out.println("3: View Order by ID");
            System.out.println("4: Update Order");
            System.out.println("5: Delete Order");
            System.out.println("6: Go Back to Main Menu");
            System.out.print("Enter choice: ");
            int orderChoice = scanner.nextInt();
            scanner.nextLine();

            switch (orderChoice) {
                case 1:
                    addOrder(scanner, orderDao, orderDetailsDao);
                    break;
                case 2:
                    viewAllOrders(orderDao);
                    break;
                case 3:
                    viewOrderById(scanner, orderDao);
                    break;
                case 4:
                    updateOrder(scanner, orderDao);
                    break;
                case 5:
                    deleteOrder(scanner, orderDao, orderDetailsDao);
                    break;
                case 6:
                    orderManagementMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private static void addOrder(Scanner scanner, OrderDAO orderDao, OrderDetailsDAO orderDetailsDao) {
        try {

            System.out.println("Enter Customer ID:");
            int customerId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Order Date (yyyy-MM-dd):");
            String orderDateString = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp orderDate = new Timestamp(dateFormat.parse(orderDateString).getTime());

            System.out.println("Enter Order Status:");
            String status = scanner.nextLine();

            Orders newOrder = new Orders();
            newOrder.setCustomerID(customerId);
            newOrder.setOrderDate(orderDate);
            newOrder.setStatus(status);


            List<OrderDetails> orderDetailsList = new ArrayList<>();
            boolean addingMore = true;
            while (addingMore) {
                System.out.println("Enter Book ID for order detail:");
                int bookId = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter quantity:");
                int quantity = Integer.parseInt(scanner.nextLine());

                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setBookID(bookId);
                orderDetail.setQuantity(quantity);
                orderDetailsList.add(orderDetail);

                System.out.println("Add more items to this order? (yes/no)");
                addingMore = scanner.nextLine().trim().equalsIgnoreCase("yes");
            }


            orderDao.addOrder(newOrder, orderDetailsList);

            System.out.println("Order added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding order: " + e.getMessage());
        }
    }

    private static void viewAllOrders(OrderDAO orderDao) {
        List<Orders> orders = orderDao.getAllOrders();

        if (!orders.isEmpty()) {

            System.out.println("All Orders:");
            for (Orders order : orders) {
                System.out.println("Order ID: " + order.getOrderID());
                System.out.println("Customer ID: " + order.getCustomerID());
                System.out.println("Order Date: " + order.getOrderDate());
                System.out.println("Status: " + order.getStatus());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No orders found.");
        }
    }

    private static void viewOrderById(Scanner scanner, OrderDAO orderDao) {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        Orders order = orderDao.getOrderById(orderId);
        if (order != null) {

            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Customer ID: " + order.getCustomerID());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Status: " + order.getStatus());
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }

    private static void updateOrder(Scanner scanner, OrderDAO orderDao) {
        try {
            System.out.println("Enter Order ID:");
            int orderId = Integer.parseInt(scanner.nextLine());

            Orders orderToUpdate = orderDao.getOrderById(orderId);
            if (orderToUpdate == null) {
                System.out.println("Order not found.");
                return;
            }

            System.out.println("Enter new Customer ID (current: " + orderToUpdate.getCustomerID() + "):");
            orderToUpdate.setCustomerID(Integer.parseInt(scanner.nextLine()));

            System.out.println("Enter new Order Date (yyyy-[m]m-[d]d hh:mm:ss, current: " + orderToUpdate.getOrderDate() + "):");
            orderToUpdate.setOrderDate(Timestamp.valueOf(scanner.nextLine()));

            System.out.println("Enter new Order Status (current: " + orderToUpdate.getStatus() + "):");
            orderToUpdate.setStatus(scanner.nextLine());

            orderDao.updateOrder(orderToUpdate);

            System.out.println("Order updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
        }
    }


    private static void deleteOrder(Scanner scanner, OrderDAO orderDao, OrderDetailsDAO orderDetailsDao) {
        try {
            System.out.println("Enter Order ID to delete:");
            int orderId = Integer.parseInt(scanner.nextLine());


            Orders order = orderDao.getOrderById(orderId);
            if (order == null) {
                System.out.println("Order not found.");
                return;
            }

            orderDetailsDao.deleteOrderDetails(orderId);


            orderDao.deleteOrder(orderId);
            System.out.println("Order and associated order details deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid numeric Order ID.");
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }





    private static void manageOrderDetails(Scanner scanner) {

        OrderDetailsDAO orderDetailsDao = new OrderDetailsDAOImpl();


        boolean running = true;


        while (running) {
            System.out.println("\nOrder Details Management Menu:");
            System.out.println("1: Add Order Detail");
            System.out.println("2: View Order Detail by ID");
            System.out.println("3: View All Order Details");
            System.out.println("4: Update Order Detail");
            System.out.println("5: Delete Order Detail");
            System.out.println("6: Return to Main Menu");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addOrderDetail(scanner, orderDetailsDao);
                    break;
                case 2:
                    viewOrderDetailById(scanner, orderDetailsDao);
                    break;
                case 3:
                    viewAllOrderDetails(orderDetailsDao);
                    break;
                case 4:
                    updateOrderDetail(scanner, orderDetailsDao);
                    break;
                case 5:
                    deleteOrderDetail(scanner, orderDetailsDao);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private static void addOrderDetail(Scanner scanner, OrderDetailsDAO orderDetailsDao) {
        try {
            System.out.println("Enter Order ID:");
            int orderId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Book ID:");
            int bookId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());

            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrderID(orderId);
            orderDetail.setBookID(bookId);
            orderDetail.setQuantity(quantity);

            orderDetailsDao.addOrderDetails(orderDetail);

            System.out.println("Order Detail added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error adding order detail: " + e.getMessage());
        }
    }
    private static void viewOrderDetailById(Scanner scanner, OrderDetailsDAO orderDetailsDao) {
        try {
            System.out.println("Enter Order Detail ID:");
            int orderDetailId = Integer.parseInt(scanner.nextLine());

            OrderDetails orderDetail = orderDetailsDao.getOrderDetailsById(orderDetailId);
            if (orderDetail != null) {
                System.out.println(orderDetail);
            } else {
                System.out.println("Order detail not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error retrieving order detail: " + e.getMessage());
        }
    }
    private static void viewAllOrderDetails(OrderDetailsDAO orderDetailsDao) {
        try {
            List<OrderDetails> orderDetailsList = orderDetailsDao.getAllOrderDetails();
            for (OrderDetails orderDetail : orderDetailsList) {
                System.out.println(orderDetail);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving all order details: " + e.getMessage());
        }
    }

    private static void updateOrderDetail(Scanner scanner, OrderDetailsDAO orderDetailsDao) {
        try {
            System.out.println("Enter Order Detail ID:");
            int orderDetailId = Integer.parseInt(scanner.nextLine());

            OrderDetails orderDetail = orderDetailsDao.getOrderDetailsById(orderDetailId);
            if (orderDetail == null) {
                System.out.println("Order detail not found.");
                return;
            }

            System.out.println("Enter new Order ID (current: " + orderDetail.getOrderID() + "):");
            orderDetail.setOrderID(Integer.parseInt(scanner.nextLine()));

            System.out.println("Enter new Book ID (current: " + orderDetail.getBookID() + "):");
            orderDetail.setBookID(Integer.parseInt(scanner.nextLine()));

            System.out.println("Enter new Quantity (current: " + orderDetail.getQuantity() + "):");
            orderDetail.setQuantity(Integer.parseInt(scanner.nextLine()));

            orderDetailsDao.updateOrderDetails(orderDetail);
            System.out.println("Order detail updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error updating order detail: " + e.getMessage());
        }
    }
    private static void deleteOrderDetail(Scanner scanner, OrderDetailsDAO orderDetailsDao) {
        try {
            System.out.println("Enter Order Detail ID to delete:");
            int orderDetailId = Integer.parseInt(scanner.nextLine());

            orderDetailsDao.deleteOrderDetails(orderDetailId);
            System.out.println("Order detail deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error deleting order detail: " + e.getMessage());
        }
    }




    private static void manageDatabaseMetadata(Scanner scanner) {

        Connection connection = DatabaseConnection.getConnection();
        DatabaseMetadataUtil dbMetadataUtil = new DatabaseMetadataUtil(connection);

        boolean running = true;

        while (running) {
            System.out.println("\nDatabase Metadata Management Menu:");
            System.out.println("1: List All Tables");
            System.out.println("2: List Table Columns");
            System.out.println("3: List Primary Keys");
            System.out.println("4: List Foreign Keys");
            System.out.println("5: Return to Main Menu");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listAllTables(dbMetadataUtil);
                    break;
                case 2:
                    listTableColumns(scanner, dbMetadataUtil);
                    break;
                case 3:
                    listPrimaryKeys(scanner, dbMetadataUtil);
                    break;
                case 4:
                    listForeignKeys(scanner, dbMetadataUtil);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void listAllTables(DatabaseMetadataUtil dbMetadataUtil) {
        try {
            dbMetadataUtil.listAllTables();
        } catch (SQLException e) {
            System.out.println("Error listing tables: " + e.getMessage());
        }
    }

    private static void listTableColumns(Scanner scanner, DatabaseMetadataUtil dbMetadataUtil) {
        System.out.println("Enter table name:");
        String tableName = scanner.nextLine();
        try {
            dbMetadataUtil.listTableColumns(tableName);
        } catch (SQLException e) {
            System.out.println("Error listing table columns: " + e.getMessage());
        }
    }

    private static void listPrimaryKeys(Scanner scanner, DatabaseMetadataUtil dbMetadataUtil) {
        System.out.println("Enter table name:");
        String tableName = scanner.nextLine();
        try {
            dbMetadataUtil.listPrimaryKeys(tableName);
        } catch (SQLException e) {
            System.out.println("Error listing primary keys: " + e.getMessage());
        }
    }

    private static void listForeignKeys(Scanner scanner, DatabaseMetadataUtil dbMetadataUtil) {
        System.out.println("Enter table name:");
        String tableName = scanner.nextLine();
        try {
            dbMetadataUtil.listForeignKeys(tableName);
        } catch (SQLException e) {
            System.out.println("Error listing foreign keys: " + e.getMessage());
        }
    }







}




