# Library Management System

A console-based Java application that implements a comprehensive library management system using custom data structures and algorithms.

## Project Overview

This Library Management System allows users to manage books, patrons, and borrowing transactions through a console-based interface. The system implements various data structures and algorithms from scratch to provide efficient operations.

## Features

### Data Structures Implemented

- **Linked Lists**: Used for maintaining collections of books, patrons, transactions, and reservations
- **Binary Search Trees**: Implemented for efficient book searching by ID, title, and author
- **Queues**: Used for handling book reservation requests
- **Sorting Algorithms**: Custom bubble sort implementation for displaying books by various criteria
- **Searching Algorithms**: Linear and binary search techniques for finding books and patrons

### CRUD Operations

#### Books Management
- **Create**: Add new books to the library
- **Read**: View book details and check availability
- **Update**: Modify book information
- **Delete**: Remove books from the system

#### Patron Management
- **Create**: Register new library members
- **Read**: View patron details and borrowing history
- **Update**: Update patron information
- **Delete**: Remove patrons from the system

#### Borrowing Transactions
- **Create**: Check out books to patrons
- **Read**: View current borrows and history
- **Update**: Process returns and update status
- **Delete**: Remove transactions (not directly exposed in UI)

#### Reservation System
- **Create**: Reserve books that are currently unavailable
- **Read**: View reservation queue and status
- **Update**: Fulfill or cancel reservations
- **Delete**: Remove reservations from the system

## How to Run

1. Compile all Java files:
   ```
   javac *.java datastructures/*.java models/*.java services/*.java
   ```

2. Run the main class:
   ```
   java LibraryManagementSystem
   ```

## System Architecture

- **datastructures/**: Contains custom implementations of LinkedList, BinarySearchTree, and Queue
- **models/**: Contains entity classes (Book, Patron, Transaction, Reservation)
- **services/**: Contains service classes for business logic
- **LibraryManager.java**: Manages all services and their interactions
- **ConsoleUI.java**: Handles user interaction via console
- **LibraryManagementSystem.java**: Main class that starts the application

## Sample Data

The system comes pre-loaded with sample books and patrons for testing:

### Books
- The Great Gatsby by F. Scott Fitzgerald
- To Kill a Mockingbird by Harper Lee
- 1984 by George Orwell
- The Hobbit by J.R.R. Tolkien
- Pride and Prejudice by Jane Austen

### Patrons
- John Smith
- Emily Johnson
- Michael Brown

## Usage Examples

- Add new books to the library inventory
- Register new library members
- Borrow books for patrons
- Return books and process renewals
- Reserve books that are currently borrowed
- Search for books by title, author, or keywords
- View borrowing history for patrons
- Check and manage overdue books 