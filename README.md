# Onboarding chat bot 

Chat bot application to assist with onboarding new staff to the company, this chat bot will interact with the user 
through a series of simple stimulus responses, rules based AI is another way of looking at the bot. 

Needed to get a copy of the codebase

```
Git 
Maven 
Java jdk 8 (GraalVM) was used to build the system
Ngrok
```


# To get the system locally running

```
git clone 
mvn clean install 
start ngrok with either http or https and port number 
change the url in slack api to point to ngrok 
```

# Unit testing

```
Unit tests have not been added to the code base for the following reasons
1. The code base is very small as such it does not include a lot of logical processing warranting unit tests 
2. The code base has a few small bespoke functions that handle xml template processing only 
3. The code base handles all of the other work through imported libraries that have been tested
4. If you have an instance of the system running tests will automatically fail due to auth errors 
    as slack will reject the incoming requests. 
```


# Deploy to live 

To§ deploy to a live system the project includes a docker file, that file is used to build an image the image 
itself is used to run in the wild, to run the image you will need to obtain a bot user token from slack through the 
slack api and set that as an environment variable using the -e SLACK_TOKEN flag  


# Hat tip to libraries used to build this bot 

* JSlack 
* Spring boot 
* Docker
