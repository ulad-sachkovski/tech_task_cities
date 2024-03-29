# Technical task "Countries and cities"

### Task: Create an enterprise-grade "Country and city list"
### Functionality:
* Get all country names (paginated)
* Search by the city name
* Get all cities from a requested country
* Get all cities with flags of their countries
* Edit the country (allowed only for role EDITOR)
* No specific technology stack

### Selected technology stack
* Spring Boot
* Maven
* Webflux
* MongoDB
* Keycloak
* Docker

## Getting Started

### Prerequisites
* Java 17
* Docker
* Postman

### How to run
  ```shell
- Start docker compose file
- Open your browser and go keycloak login page: http://localhost:8491/auth/
- Import realm-export.json from documents folder
- Go to clients => countries-auth => settings => authorization enabled => on. Click save
- Create users in users tab. Role "EDITOR" already defined. Just map it to already created user
- Start the application

Note: When application is started, database recovers to initial state. However, id of countries will change (see implementation in config.data_init package)
  ```

### How to navigate via Postman
  ```shell
- Import Countries.postman_collection.json from documents to Postman
- All get methods are allowed without authorization
- Put method is secured and has role EDITOR rights:
      * To receive editor rights, copy "get code for access token" end-point and enter in the browser
      * Enter credentials of created user in keycloak, that has EDITOR role
      * In response header, copy code and paste to "receive access token" end-point
      * Go to clients => countries-auth => credentidals and copy secret. Paste it to receive access token" end-point
      * Copy received access token and paste to Authorization header
  ```

### How to navigate via Swagger
  ```shell
- Open http://localhost:8090/webjars/swagger-ui/index.html#/
- For receiving JWT token, watch "How to navigate via Postman"
  ```