
This is a Spring boot application that contains one API endpoint.

This API endpoint can be used to retrieve information about a GitHub user's repositories, including the repository name, owner's login, and branch information. The endpoint can be accessed using a URL like http://example.com/info?username=github_username, where github_username is the GitHub username for which the repository information is being requested.

#### API Reference

API endpoint: GET /info

Request parameters:
- username (required): The GitHub username for which to retrieve repository information

Response format: application/json

Response codes:
- 200 OK: The request was successful and the response contains a JSON array of repository information
- 400 Bad Request: The request was missing the required "username" parameter or the parameter value was invalid
- 404 Not found: The specified username was not found
- 406 Not acceptable: The requested format is not supported

Response JSON example : 

```
[
  {
    "name": "repo",
    "ownerLogin": "github_username",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "i9j0k1l2"
      }
    ]
  }
]

```
### Requirements
-   Java 17 


### Application Start

##### CONFIG 

The application is using the Github API.
This is a rate limited api, so if you want to have full access, you will need to configure a GitHub API Token (Note: It still works without this, but you won't be able to do many requests or check big accounts)
So you can set environment variable `GITHUB_TOKEN={YOUR_GITHUB_TOKEN}`
##### Using IntelliJ IDEA

Hit the run button of GithubRetrieveApplication main function

##### Using terminal

```bash
mvn spring-boot:run
```

By default the application runs on port 8080 

The information API is available at 
```bash 
/info?username={username}
```

Example: http://localhost:8080/info?username=SelesCalin