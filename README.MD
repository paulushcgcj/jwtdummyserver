# JWT Dummy Server
[![CircleCI](https://circleci.com/gh/paulushcgcj/jwtdummyserver/tree/master.svg?style=svg)](https://circleci.com/gh/paulushcgcj/jwtdummyserver/tree/master)
[![codecov](https://codecov.io/gh/paulushcgcj/jwtdummyserver/branch/master/graph/badge.svg)](https://codecov.io/gh/paulushcgcj/jwtdummyserver)



**Have you ever tried to learn something new and bumped into a technical limitation?**

I have experienced this while I was learning [ReactJS](https://reactjs.org/), during the time I was trying to implement an authentication system. Almost all examples I saw were using Auth0, but I want to use a local solution, because most os the clients I've used to work had an internal security system.

That's why I've done this dummy plug to be used during JWT tests. In this example, all you have to do is an internet connection or a user file with some users to play with. The file format is just a collection of users generated from [randomuser.me](https://randomuser.me) and saved on `userdatabase.json` file. Don't worry, you don't need to generate the file by yourself, the dummyplug will fetch and generate the file for you, and print all the users you can use on console.

This dummyplug would not be possible without the internet, and I had based it on [this article](http://andreybleme.com/2017-04-01/autenticacao-com-jwt-no-spring-boot/) so many thanks :)

## How to run it

if you have a java environment where you can build apps, just run `mvnw clean package`, but you will need JDK 1.8 at least. if you don't have, just download the latest build from GitHub.

## How to test it

I recomment [Postman](https://www.getpostman.com/), because it's faster. Included on this project, you will find a Collection and an Environment file to be used on Postman. Have a try, play with it, and once you are confortable with the dynamics of the dummyplug, write your app to consume it.

To consume it, call the service url (normally http://localhost:8080/login) and send a json body with username and password like this `{"username":"paulo","password":"secret"}`. You don't need to encrypt your password to send it, remember it's just a dummy plug to test, so let's cut the complexity level to the lowest possible.