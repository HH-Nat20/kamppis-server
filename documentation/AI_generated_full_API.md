# kamppis_server API Documentation

## Overview

This document provides a comprehensive reference for the kamppis_server API. The API allows clients to interact with the server for managing user accounts, profiles, matches, invites, and feedback.

## Base URL

```
https://kamppis.hellmanstudios.fi
```


## Authentication

Most endpoints require authentication. Authentication is handled through tokens:
- `authToken` - Required for protected endpoints
- `inviteToken` - Used for joining rooms

## API Endpoints

### Matches

#### Create a Match

```
POST /api/matches
```


Creates a new match.

**Request Body:**
```json
{
  "$ref": "#/components/schemas/MatchRequest"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/MatchDTO"
}
```


#### Get Match by ID

```
GET /api/matches/{id}
```


Retrieves a specific match by its ID.

**Parameters:**
- `id` (path, required) - Match ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/MatchDTO"
}
```


#### Remove from Match

```
POST /api/matches/{id}
```


Removes a participant from a match.

**Parameters:**
- `id` (path, required) - Match ID (integer, int64)

**Request Body:**
```json
{
  "$ref": "#/components/schemas/MatchRequest"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/MatchDTO"
}
```


#### Get All Matches for User

```
GET /api/matches/
```


Retrieves all matches for a specific user.

**Parameters:**
- `userId` (query, optional) - User ID (integer, int64)

**Response:**
```json
[
  {
    "$ref": "#/components/schemas/MatchDTO"
  }
]
```


#### Get All User Profiles for Match

```
GET /api/matches/profiles/{id}
```


Retrieves all user profiles associated with a specific match.

**Parameters:**
- `id` (path, required) - Match ID (integer, int64)

**Response:**
```json
[
  {
    "$ref": "#/components/schemas/UserProfileDTO"
  }
]
```


### Invites

#### Join Room with Invite Token

```
PUT /api/invites/join/{inviteToken}
```


Allows a user to join a room using an invite token.

**Parameters:**
- `inviteToken` (path, required) - Invitation token (string)
- `authToken` (header, required) - Authentication token (string)

**Response:**
```json
{
  "$ref": "#/components/schemas/InviteResponse"
}
```


#### Generate Invite Token

```
POST /api/invites/generate-invitetoken/{roomProfileId}
```


Generates an invite token for a specific room.

**Parameters:**
- `roomProfileId` (path, required) - Room profile ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/InviteResponse"
}
```


### Authentication

#### Mock Login

```
POST /api/login/mock
```


Provides a mock login mechanism for testing purposes.

**Parameters:**
- `email` (query, required) - User's email (string)

**Response:**
- A string token

#### GitHub Login

```
POST /api/login/github
```


Allows users to authenticate using GitHub.

**Parameters:**
- `code` (query, required) - Authorization code from GitHub (string)

**Response:**
```json
{
  "$ref": "#/components/schemas/Object"
}
```


#### Signup

```
POST /api/login/signup
```


Creates a new user account.

**Parameters:**
- `code` (query, required) - Authorization code (string)

**Request Body:**
```json
{
  "$ref": "#/components/schemas/UserRequest"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/Object"
}
```


#### Protected Data

```
GET /api/login/protected
```


Retrieves protected data (requires authentication).

**Response:**
- A string with protected data

### Feedback

#### Submit Feedback

```
POST /api/feedback
```


Submits user feedback.

**Request Body:**
```json
{
  "$ref": "#/components/schemas/FeedbackDTO"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/FeedbackDTO"
}
```


#### Get All Feedback

```
GET /api/feedback/
```


Retrieves all feedback submissions.

**Response:**
```json
[
  {
    "$ref": "#/components/schemas/FeedbackDTO"
  }
]
```


### Profiles

#### Update Profile

```
PUT /api/profiles/{id}
```


Updates a user profile.

**Parameters:**
- `id` (path, required) - Profile ID (integer, int64)

**Request Body:**
```json
{
  "$ref": "#/components/schemas/ProfileDTO"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/ProfileDTO"
}
```


#### Get Profile by ID

```
GET /api/profiles/{id}
```


Retrieves a specific profile by ID.

**Parameters:**
- `id` (path, required) - Profile ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/ProfileDTO"
}
```


#### Get All Profiles

```
GET /api/profiles
```


Retrieves all profiles.

**Response:**
```json
[
  {
    "$ref": "#/components/schemas/ProfileDTO"
  }
]
```


### Users

#### Update User

```
PUT /api/users/{id}
```


Updates a user's information.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Request Body:**
```json
{
  "$ref": "#/components/schemas/UserRequest"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/UserDTO"
}
```


#### Delete User

```
DELETE /api/users/{id}
```


Deletes a user account.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/Void"
}
```


#### Get User by ID

```
GET /api/users/{id}
```


Retrieves a specific user by ID.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/UserDTO"
}
```


#### Restore User

```
PUT /api/users/{id}/restore
```


Restores a previously deleted user account.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/UserDTO"
}
```


#### Update User Preferences

```
PUT /api/users/{id}/preferences
```


Updates a user's preferences.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Request Body:**
```json
{
  "$ref": "#/components/schemas/UserPreferenceRequest"
}
```


**Response:**
```json
{
  "$ref": "#/components/schemas/UserPreferenceDTO"
}
```


#### Get User Preferences

```
GET /api/users/{id}/preferences
```


Retrieves the preferences for a specific user.

**Parameters:**
- `id` (path, required) - User ID (integer, int64)

**Response:**
```json
{
  "$ref": "#/components/schemas/UserPreferenceDTO"
}
```


## Data Models

The API uses the following data models:

- **MatchDTO**: Represents a match between users
- **MatchRequest**: Contains information needed to create or modify a match
- **UserProfileDTO**: Represents a user profile in the context of matches
- **InviteResponse**: Contains information about an invitation
- **UserRequest**: Contains information for creating or updating a user
- **UserDTO**: Represents a user account
- **FeedbackDTO**: Contains user feedback
- **ProfileDTO**: Represents a user profile
- **UserPreferenceRequest**: Contains information for updating user preferences
- **UserPreferenceDTO**: Represents user preferences

## Error Handling

The API uses standard HTTP status codes to indicate the success or failure of requests:

- 200 OK: The request was successful
- 400 Bad Request: The request was invalid
- 401 Unauthorized: Authentication is required
- 403 Forbidden: The user does not have permission
- 404 Not Found: The requested resource was not found
- 500 Internal Server Error: An unexpected error occurred on the server

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens