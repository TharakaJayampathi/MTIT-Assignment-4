# MTIT-Assignment-4
## Micro Service - Spring Boot 
## Project Structure

* User Service
* Product Service
* Order Service
* Account Service
* Delivary Service

## Postman API Requests

### Authentication

Sign Up `http://localhost:8082/api/auth/signup`
– Request JSON Body:
```
{
	"username":"UserName",
	"email":"sample@email.com",
	"password":"password"
}
```

Sign In `http://localhost:8082/api/auth/signin`
– Request JSON Body:
```
{
	"username":"UserName",
	"email":"sample@email.com",
	"password":"password"
}
```

```diff
+ Note: Copy the accessToken(TokenType:bearer) and use it to Authenticate*
```

### Products

Add Product `http://localhost:8082/api/addProducts`
– Request JSON Body:
```
{
	"name": "product 03",
        "description": "product description 03",
        "price": 50000.0
}
```


Update a Product `http://localhost:8082/api/updateProduct`
– Request JSON Body:
```
  {
        "id":"2",
        "name": "product 02",
        "description": "product description 02",
        "price": 50000.0
  }
```

Get All Product `http://localhost:8082/api/getAllProducts`

Get a Product `http://localhost:8082/api/getProduct/{product Id}`

Delete a Product `http://localhost:8082/api/deleteProducts/{product Id}`

### Orders

Add Order `http://localhost:8082/api/addOrder`
– Request JSON Body:
```
{
        "productId":productId,
        "quantity": quantity
}
```

Update a Order `http://localhost:8082/api/updateOrder`
– Request JSON Body:
```
{
        "id":orderId,
        "userId":userId,
        "productId":productId,
        "quantity": quantity
}
```

Get all orders of logged user `http://localhost:8082/api/getOrdersByUserId`

Get a Order `http://localhost:8082/api/getOrder/{order Id}`

Delete a Order `http://localhost:8082/api/deleteOrder/{order Id}`

## User Service Configuration
### User Registration, User Login and Authorization process.

### Dependency
– or MySQL:
```xml
        <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <scope>runtime</scope>
        </dependency>
```
### Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`

- For MySQL
`user-service/src/main/resource/application.properties`
```
spring.datasource.url= jdbc:mysql://localhost:3306/testdb?useSSL=false
spring.datasource.username= root
spring.datasource.password= 123456

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto= update

# App Properties
bezkoder.app.jwtSecret= bezKoderSecretKey
bezkoder.app.jwtExpirationMs= 86400000
```
### Run Spring Boot application
```
mvn spring-boot:run
```

### Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
## Other Services Dependency Configuration
```xml
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-boot-starter</artifactId>
			<version>3.0.0</version>
		</dependency>

		<dependency>
			<groupId>com.jayway.restassured</groupId>
			<artifactId>json-path</artifactId>
			<version>2.4.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId>
			<version>3.25.2</version>
		</dependency>
```
## Other Services Resources Configuration

Open `src/main/resources/application.properties`

- For Product Service
```
server.port=8081

spring.jpa.database-platform=com.sliit.mtit62.productservice.persistence.SQLDialect
spring.jpa.hibernate.ddl-auto=update


spring.datasource.url = jdbc:sqlite:products.db
spring.datasource.driver-class-name = org.sqlite.JDBC

spring.datasource.initialization-mode=always

spring.datasource.username = admin
spring.datasource.password = admin
```

- For Delivary Service
```
server.port=8182

spring.jpa.database-platform=com.sliit.mtit62.deliveryservice.persistence.SQLDialect
spring.jpa.hibernate.ddl-auto=update


spring.datasource.url = jdbc:sqlite:deliveries.db
spring.datasource.driver-class-name = org.sqlite.JDBC

spring.datasource.initialization-mode=always

spring.datasource.username = admin
spring.datasource.password = admin
```

- For Order Service
```
server.port=8183

spring.jpa.database-platform=com.sliit.mtit62.orderservice.persistence.SQLDialect
spring.jpa.hibernate.ddl-auto=update


spring.datasource.url = jdbc:sqlite:orders.db
spring.datasource.driver-class-name = org.sqlite.JDBC

spring.datasource.initialization-mode=always

spring.datasource.username = admin
spring.datasource.password = admin
```

- For Account Service
```
server.port=8184

spring.jpa.database-platform=com.sliit.mtit62.accountservice.persistence.SQLDialect
spring.jpa.hibernate.ddl-auto=update


spring.datasource.url = jdbc:sqlite:accounts.db
spring.datasource.driver-class-name = org.sqlite.JDBC

spring.datasource.initialization-mode=always

spring.datasource.username = admin
spring.datasource.password = admin
```
