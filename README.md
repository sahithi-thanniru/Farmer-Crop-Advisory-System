# Farmer Crop Advisory and Market Information System

A Java and MySQL-based application designed to help farmers manage farm information, receive crop recommendations, access cultivation guidance, and compare agricultural market prices.

## Project Overview

The Farmer Crop Advisory and Market Information System combines database management with agricultural information to support informed farming decisions. The project includes a Java application and a web interface.

## Features

- **Farmer Registration:** Add, view, search, and update farmer records.
- **Land and Soil Management:** Manage land details and soil test information.
- **Crop Advisory:** Recommend crops based on soil, season, irrigation, temperature, and other available farm details.
- **Fertilizer and Cultivation:** Access fertilizer information and cultivation guidance.
- **Market Information:** Manage market details, crop prices, and price comparisons.
- **Reports:** View summaries of farmers, lands, crops, markets, and price records.
- **Previous Crop Records:** Maintain information about previously cultivated crops and yields.

## Technologies Used

- Java
- MySQL
- JDBC
- HTML
- CSS
- JavaScript
- Visual Studio Code
- Git and GitHub

## Project Structure

```text
Farmer-Crop-Advisory-System/
├── src/
│   ├── dao/
│   ├── model/
│   ├── service/
│   ├── ui/
│   ├── util/
│   └── web/
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── .gitignore
└── README.md
```

## Prerequisites

- Java Development Kit (JDK)
- MySQL Server
- Visual Studio Code or another Java IDE
- MySQL Connector/J

## Setup Instructions

1. Clone or download this repository.
2. Install and start MySQL Server.
3. Create the `farmer_advisory` database.
4. Configure the database URL, username, and password in the database connection class.
5. Ensure that the required database tables exist.
6. Compile the Java source files using the MySQL Connector/J library.
7. Run the console application or start the web server, depending on the version you want to use.

Refer to the source code for the current database configuration and application entry points.

## Learning Outcomes

- Java application development
- Database design and SQL operations
- JDBC connectivity
- CRUD operations
- Separation of application logic using DAO, model, and service layers
- Web interface development
- Version control using Git and GitHub

## Future Enhancements

- Weather API integration
- More advanced crop recommendation logic
- Live market price integration
- Farmer authentication and role-based access
- Responsive mobile-friendly interface

## Author

Sahithi Thanniru

## License

A license can be added if you decide to distribute or reuse this project publicly.
