# Quarkus Authentication Service

# Introduction

This application is the authentication service, with the main goal of being used to compare against similar frameworks.
The application allows you to login and gives you permission to make API calls. This service uses the Quarkus framework with RESTEasy Jax-RS
for the REST API. Qute is used for the webpage templating. H2 database is used to store user information.

# Running the Application in Dev Mode

The application can be run with the shell script:
./mvnw compile quarkus:dev

# Usage

Log in with either the 'user' or 'admin' username, where the password for both is 'pass'.
An invalid login attempt will bring you to an error page. Once logged in, the 'quarkus-credential' 
cookie is present, which gives you access to the API. For testing purposes, there is a logout method
and API endpoints to view users and their information.