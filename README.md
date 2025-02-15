# Bookstore API

A Java API to manage books, genres, and authors using Spring Boot, PostgreSQL and DBeaver for data management

## Features
- List all books, authors and genres.
- Get a single book, author or genre by ID.
- Get books by author, by genre or by authors born on/after a specific year
- Get author born on/after a specific year
- Add new books, authors, and genres.

## Technologies Used
- Java, Spring Boot
- PostgreSQL, JDBC, SimpleJdbcInsert
- DBeaver

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/MattLab0/Bookstore
   ```
2. Set up PostgreSQL and create a database named `bookstore`
3. Configure the environment variable `DB_PASSWORD` for database access
4. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## History
- Italian version
- Translated in English
- Implemented publication year and page number in books
- Added birth year to author, fixed endpoints and queries
- Added ID to genre, improved query security

