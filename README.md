## Git API Integration
### Summary
A simple Spring boot app that combines a couple of public Git API endpoints 
to grab information for a given user, such as their profile metadata and repos.

### Usage
The app is written using OpenJDK23, make sure your environment is set to use 
a flavor of Java 23.  
In order to start up, we run the typical spring boot command through maven:  
`./mvnw spring-boot:run`
The endpoint is available at: `/api/<user>` and the default port to communicate is 8080

To run the included tests:  
`./mvnw clean compile test`

### Interacting with App
Given a username, it will return the metadata for the user as well as its repositories.

Example: `localhost:8080/api/octocat`

Expected Response:
```
{
    user_name: "octocat",
    display_name: "The Octocat",
    avatar: "https://avatars3.githubusercontent.com/u/583231?v=4",
    geo_location: "San Francisco",
    email: null,
    url: "https://github.com/octocat ",
    created_at: "2011-01-25 18:44:36",
    repos: [
        { 
            name: "boysenberry-repo-1",
            url: "https://github.com/octocat/boysenberry-repo-1"
        }, ...
    ]
}
```

### Decisions
1. Due to the rate limiting and general usage of a third-party API, I decided to cache the call to the user's repository 
 given the assumption that repos creation does not happen frequently (given that we are only utilizing the name and url pieces). 
 I originally thought to cache the User metadata call, however, I noticed that there are a number of fields that can easily
 be changed (such as the user_name, display_name, and avatar). 
2. Another consideration I made was to allow for configuration of an auth token to open up the github API further, however
given the time limitations, I decided to forgoe the implementation. 
3. I decided to have, essentially, two sets of DTOs (3rd party data vs expected return data). Had the only difference of 
the return fields and fetched fields were the camel vs snake case, I would have (maybe) considered one set. However, also
taking into account nature of third party apis, I think it was wise to split it to further segregate the input/output as
well as notating what we _can_ control vs what is given to us.