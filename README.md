# Stocks application

* [Requirements](#requirements)
* [Build](#build)
* [Usage](#usage)
* [Further development](#further-development)

# Requirements
* Java 11 or higher
* Maven 3.6 or higher
* Docker + Docker Compose

# Build

Use the following commands to build and run the app via Docker Compose:
* `mvn clean package`
* `docker-compose up --build`

# Usage
When app is up, go to http://localhost:8085/swagger-ui.html and start exploring the API. Use
Open API client provided, Postman or any other tool to send requests.

DB connection credentials may be found in `docker-compose.yml` file.

# Further development
* Finish StockStatisticsView flow with SQL view already provided.
* Configure CI pipeline for more stable build process.
* Add documentation for response models.