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
- Create users. Role "EDITOR" already defined. Just map it to already created user
- Start the application

Note: When application is started, database recovers to initial state. However, id of countries will change (see implementation in config.data_init package)
  ```

### How to navigate
  ```shell
- Import Countries.postman_collection.json from documents to Postman
- All get methods are allowed without authorization
- Put method is secured and has role EDITOR right:
      * To receive editor rights, copy "get code for access token" end-point and enter in the browser
      * In response header, copy code and paste to "receive access token" end-point
      * Copy received access token and paste to Authorization header
  ```
