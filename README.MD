# JWT Dummy Server
[![CircleCI](https://circleci.com/gh/paulushcgcj/jwtdummyserver/tree/master.svg?style=svg)](https://circleci.com/gh/paulushcgcj/jwtdummyserver/tree/master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c20273d454fe4ce38ccbbdaaa46ebded)](https://app.codacy.com/app/paulushc/jwtdummyserver?utm_source=github.com&utm_medium=referral&utm_content=paulushcgcj/jwtdummyserver&utm_campaign=badger)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/ef3fef2c873a47e08ec251ce96e2b630)](https://www.codacy.com/app/paulushc/jwtdummyserver?utm_source=github.com&utm_medium=referral&utm_content=paulushcgcj/jwtdummyserver&utm_campaign=Badge_Coverage)


[![Github All Releases](https://img.shields.io/github/downloads/paulushcgcj/jwtdummyserver/total.svg)](https://github.com/paulushcgcj/jwtdummyserver)
[![GitHub tag](https://img.shields.io/github/tag/paulushcgcj/jwtdummyserver.svg)](https://github.com/paulushcgcj/jwtdummyserver)
[![Github commits (since latest release)](https://img.shields.io/github/commits-since/paulushcgcj/jwtdummyserver/latest.svg)](https://github.com/paulushcgcj/jwtdummyserver)
[![GitHub issues](https://img.shields.io/github/issues-raw/paulushcgcj/jwtdummyserver.svg)](https://github.com/paulushcgcj/jwtdummyserver)
[![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/paulushcgcj/jwtdummyserver.svg)](https://github.com/paulushcgcj/jwtdummyserver)
[![GitHub top language](https://img.shields.io/github/languages/top/paulushcgcj/jwtdummyserver.svg)](https://github.com/paulushcgcj/jwtdummyserver)


**Have you ever tried to learn something new and bumped into a technical limitation?**

I have experienced this while I was learning [ReactJS](https://reactjs.org/), during the time I was trying to implement an authentication system. Almost all examples I saw were using Auth0, but I want to use a local solution, because most os the clients I've used to work had an internal security system.

That's why I've done this dummy plug to be used during JWT tests. In this example, all you have to do is an internet connection or a user file with some users to play with. The file format is just a collection of users generated from [randomuser.me](https://randomuser.me) and saved on `userdatabase.json` file. Don't worry, you don't need to generate the file by yourself, the dummyplug will fetch and generate the file for you, and print all the users you can use on console.

This dummyplug would not be possible without the internet, and I had based it on [this article](http://andreybleme.com/2017-04-01/autenticacao-com-jwt-no-spring-boot/) so many thanks :)

## How to run it

if you have a java environment where you can build apps, just run `mvnw clean package`, but you will need JDK 1.8 at least. if you don't have, just download the latest build from [GitHub](https://github.com/paulushcgcj/jwtdummyserver/releases) or from [BinTray](https://bintray.com/paulushc/org.paulushc/download_file?file_path=org%2Fpaulushc%2Fjwt-dummy-server%2F1.0.0%2Fjwt-dummy-server-1.0.0.jar).

## How to test it

I recomment [Postman](https://www.getpostman.com/), because it's faster. Included on this project, you will find a Collection and an Environment file to be used on Postman. Have a try, play with it, and once you are confortable with the dynamics of the dummyplug, write your app to consume it.

To consume it, call the service url (normally http://localhost:8080/login) and send a json body with username and password like this `{"username":"paulo","password":"secret"}`. You don't need to encrypt your password to send it, remember it's just a dummy plug to test, so let's cut the complexity level to the lowest possible.

# JWT Dummy Server Release Notes

## Version 1.0

- Added the initial version
- Supporting authorized and open urls
- Added some code to log error