# GitHub Repository API - Spring Boot Application

## Overview
The GitHub Repository API is a Spring Boot application designed to provide API consumers with the ability to retrieve information about GitHub repositories based on a user's username. It allows users to list all GitHub repositories associated with a specific user, excluding forks, and provides details about each repository, including branch names and last commit SHAs.

## Acceptance Criteria

- Given a username and the header "Accept: application/json," the API should return a list of all non-fork GitHub repositories associated with the provided username. The response should include the repository name, owner login, branch names, and last commit SHAs.
- If the provided GitHub user does not exist, the API should return a 404 response with a JSON body containing the status code and a message explaining why the user was not found.

## Installation and Setup

1. Clone the GitHub Repository API project from the GitHub repository.
2. Build the project using Maven.
3. Run the application using `java -jar` command or by IntelliJ IDE

## Usage

### API Endpoints

- **GET /api/github/get-repositories-info**
    - Description: Retrieves GitHub repositories associated with a user.
    - Request:
        - Parameters:
            - username: The GitHub username for which repositories are to be retrieved.
        - Headers:
            - Accept: application/json
    - Response:
        - Success (200 OK):
          ```json
          [
            {
              "repositoryName": "SampleRepo",
              "ownerLogin": "user123",
              "branches": [
                {
                  "name": "main",
                  "lastCommitSHA": "c8ea58371dab9d4d50b51a623d0a92a42c243e5f"
                },
                {
                  "name": "develop",
                  "lastCommitSHA": "ec20a1d09b12fc4183e5bf0bc6d0d946d940b948"
                }
              ]
            },
            {
              "repositoryName": "AnotherRepo",
              "ownerLogin": "user123",
              "branches": [
                {
                  "name": "main",
                  "lastCommitSHA": "3b2fc9e2b9d3bf2fb911f21459de4c08b29a9f3b"
                }
              ]
            }
          ]
          ```
        - Not Found (404):
          ```json
          {
            "status": 404,
            "message": "Error loading data: User not found"
          }
          ```

## Error Handling

- If the GitHub API is unavailable or encounters an error, the API will return a 500 Internal Server Error response with a JSON body containing the error message.
- If the user with the received name cannot be found, the API will return a 404 NOT FOUND response with a JSON body containing an error message.

## Security

- The GitHub Repository API does not currently require authentication.

## Contributing

- Contributions to the GitHub Repository API project are welcome. Please fork the repository, make your changes, and submit a pull request.

## License

- This project is licensed under the MIT License. See the [LICENSE](https://choosealicense.com/licenses/mit/) file for details.

## Contact

- For questions, feedback, or support, please contact the project maintainer at igor.shishkin.work@gmail.com.
