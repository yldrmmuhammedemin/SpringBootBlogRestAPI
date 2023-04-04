# Spring Boot Blog REST API

The developed REST API allows users to register and login. This API allows the creation of posts according to categories and comments on the created posts.
API endpoints are protected by Spring boot security techniques. Users can perform transactions from the endpoints through the JWT token generated during login and registration.


## Deployment

You need to have Java version 20 to run the project. You must have Docker on your system for the database. To run docker-compose and create database: 
```bash
  docker-compose up
  docker exec -it postgres bash
  psql -U myusername
  CREATE DATABASE blog_post
```


## Screenshots

Endpoints on Swagger UI

![Screenshot1](https://user-images.githubusercontent.com/77511870/229730865-8a282804-a3e2-42af-8978-6d09ac65c3a9.png)

Register and authantication processes

![Screenshot2](https://user-images.githubusercontent.com/77511870/229730920-a8d1dcfc-7f65-4aab-b06d-6f5c1a2efcf4.png)
![Screenshot3](https://user-images.githubusercontent.com/77511870/229730960-c7e6b366-f171-458c-a03f-934faba89f12.png)

Database schemas

<img width="446" alt="Screenshot4" src="https://user-images.githubusercontent.com/77511870/229731015-a98ac082-ad57-4908-a90f-e2fe072b3f73.png">
