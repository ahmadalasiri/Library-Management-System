# Library Management System

## Overview

The Library Management System is a Scala-based console application designed to simplify library operations, including user management, book handling, transaction tracking, and report generation.

## Features

- **Manage Users:**

  - Add new users with their national ID and name.
  - Remove users based on their national ID.
  - Update user information.
  - View a list of all users.
  - Retrieve user details by their national ID.

- **Manage Books:**

  - Add new books with title, author, ISBN, availability, and location.
  - Remove books based on their ID.
  - Update book information.
  - View a list of all books.
  - Retrieve book details by their ID.

- **Manage Transactions:**

  - Record new transactions, including user ID, book ID, checkout date, and due date.
  - Remove transactions based on their ID.
  - Update transaction information.
  - View a list of all transactions.
  - Retrieve transaction details by their ID, user ID, or book ID.

- **Generate Reports:**
  - Generate user reports.
  - Generate book reports.
  - Generate transaction reports.
  - Generate fine reports.
  - Generate overdue reports.

## Prerequisites

- Scala
- SBT (Scala Build Tool)
- PostgreSQL

## Installation

1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd library-management-system`
3. Configure the database connection in `src/main/resources/application.conf`.
4. Run the application: `sbt run`

## Usage

Follow the on-screen prompts to navigate through the application and perform various tasks.

## Project Structure

The project follows a modular structure with separate actors, services, and database components. Key components include:

- **ActorSystemManager:** Initializes the Akka Actor System and manages various actors.
- **Main:** The main entry point of the application, orchestrating user interactions.
- **UserActor:** An Akka actor responsible for user-related operations.
- **BookActor:** An Akka actor handling book-related tasks.
- **TransactionActor:** An Akka actor managing transactions.

## Contributing

Feel free to contribute by submitting bug reports, feature requests, or pull requests. Follow the [contribution guidelines](CONTRIBUTING.md) for more details.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
