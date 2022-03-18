# Quarkus Authentication Service

## Introduction

This application is the authentication service, with the main goal of being used to compare against similar frameworks.
The application allows you to login and gives you permission to make API calls. This service uses the Quarkus framework with RESTEasy Jax-RS
for the REST API. Qute is used for the webpage templating. H2 database is used to store user information.

## Technologies

Java 17.0.1\
Quarkus 2.6.3

## Running the Application in Dev Mode

The application can be run with ```./mvnw compile quarkus:dev``` and can be reached at
[localhost:8081/login](localhost:8081/login).

## Usage

Log in with either the 'user' or 'admin' username, where the password for the user account is 'user' and the admin account is 'admin'.
An invalid login attempt will bring you to an error page. Once logged in, the 'quarkus-credential' 
cookie is present, which gives you access to the API. For testing purposes, there is a logout method
and API endpoints to view users and their information.

### Authentication

Using JPA IdentityProvider, Quarkus allows for built-in authentication with the configuration in the application.properties file.
The '/loggedin' endpoint is secured until the user is logged in. 
