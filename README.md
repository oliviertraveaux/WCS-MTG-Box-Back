
# MTG Box BACK 

MTGBOX is an application dedicated to trading products related to Magic: The Gathering. This README provides detailed information on setting up, deploying, and using the backend of the application.




![Logo](https://camo.githubusercontent.com/367cedafe82ef348a8c3ceb2583abe123868721c735356e10b9d9cd94437e419/68747470733a2f2f692e706f7374696d672e63632f7843396a33534b302f6d74672d6261636b67726f756e642d352d666f746f722d323032343036323331343131322e706e67)


## Prerequisites

Before you begin, ensure you have the following installed on your machine:

**Java 21** 

**Maven 3.6+** 

**MySQL 8+** 


## Installation

- Clone the Repository

```bash
  git clone https://github.com/your-username/WCS-MTG-Box-Back.git

```

- Configure the Database

Create a MySQL database named mtgbox and configure the connection settings in the application.properties file.


```bash
spring.datasource.url=jdbc:mysql://localhost:3306/mtgbox
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

- Build the Project

```bash
  mvn clean install

```


- Run the Application
Start the Spring Boot application.


```bash
  mvn spring-boot:run

```

    
## Features

- Manage Collections: Add, update, and remove cards.
- Trade Cards: Send and receive trade offers.
- Browse Collections: Explore other users' card collections.


## API Reference

#### When the project is launched, you can go to the Swagger.

```http
  http://localhost:8080/swagger-ui/index.html
```




![Logo](https://i.postimg.cc/rm0pDjgj/Capture-d-cran-du-2024-07-16-11-55-26.png)

## Running Tests

To run tests, run the following command

```bash
  mvn test
```


## Authors

This project was developed by [Romain Timmer](https://github.com/RtimmerGH), [Olivier Traveaux](https://github.com/oliviertraveaux)  and [Antoine Cassagne ](https://github.com/cassaga) during  the CDA program at Wild Code School.
