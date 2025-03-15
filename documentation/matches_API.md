# **Matches API Documentation**

## **Base URL**

`https://kamppis.hellmanstudios.fi`

---

## **Create a Match**

### **POST** `/api/matches`

Creates a new match between users.

### **Request Body**

#### **Schema: MatchRequest**

| Field   | Type                 | Description                     | Required |
| ------- | -------------------- | ------------------------------- | -------- |
| userIds | `array` of `integer` | List of user IDs to be matched. | ✅ Yes    |

**Example Request**

```json
{
  "userIds": [1, 2]
}
```

### **Responses**

#### ✅ **200 OK**

Match successfully created.

**Response Body**

#### **Schema: Match**

| Field     | Type                 | Description              |
| --------- | -------------------- | ------------------------ |
| id        | `integer`            | Match ID                 |
| users     | `array` of `User`    | List of matched users    |
| createdAt | `string (date-time)` | Timestamp of creation    |
| updatedAt | `string (date-time)` | Timestamp of last update |

**Example Response**

```json
{
  "id": 101,
  "users": [
    {
      "id": 1,
      "email": "user1@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    },
    {
      "id": 2,
      "email": "user2@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    }
  ],
  "createdAt": "2025-03-15T12:00:00Z",
  "updatedAt": "2025-03-15T12:00:00Z"
}
```

---

## **Get Matches**

### **GET** `/api/matches/`

Retrieves matches.

### **Query Parameters**

| Parameter | Type      | Description                                  | Required |
| --------- | --------- | -------------------------------------------- | -------- |
| userId    | `integer` | The ID of the user to fetch matches for. If omitted, fetches all matches. | ❌ No     |

### **Responses**

#### ✅ **200 OK**

Returns a list of matches. If `userId` is provided, returns matches for the specified user; otherwise, returns all matches.

**Response Body**

#### **Schema: Match**

Same as the **Create a Match** response.

**Example Response (Fetching matches for a user)**

```json
{
  "id": 102,
  "users": [
    {
      "id": 3,
      "email": "user3@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    },
    {
      "id": 4,
      "email": "user4@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    }
  ],
  "createdAt": "2025-03-15T13:00:00Z",
  "updatedAt": "2025-03-15T13:00:00Z"
}
```

**Example Response (Fetching all matches)**

```json
[
  {
    "id": 103,
    "users": [
      {
        "id": 5,
        "email": "user5@example.com",
        "status": "ACTIVE",
        "matches": [],
        "deletedAt": null
      },
      {
        "id": 6,
        "email": "user6@example.com",
        "status": "ACTIVE",
        "matches": [],
        "deletedAt": null
      }
    ],
    "createdAt": "2025-03-15T14:00:00Z",
    "updatedAt": "2025-03-15T14:00:00Z"
  }
]
```

---

## **Get Match by ID**

### **GET** `/api/matches/{id}`

Retrieves a match by its unique identifier.

### **Path Parameters**

| Parameter | Type      | Description          | Required |
| --------- | --------- | -------------------- | -------- |
| id        | `integer` | The ID of the match. | ✅ Yes    |

### **Responses**

#### ✅ **200 OK**

Returns the match details.

**Response Body**

#### **Schema: Match**

Same as the **Create a Match** response.

**Example Response**

```json
{
  "id": 103,
  "users": [
    {
      "id": 5,
      "email": "user5@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    },
    {
      "id": 6,
      "email": "user6@example.com",
      "status": "ACTIVE",
      "matches": [],
      "deletedAt": null
    }
  ],
  "createdAt": "2025-03-15T14:00:00Z",
  "updatedAt": "2025-03-15T14:00:00Z"
}
```

## **Get Matched User Profiles**

### **GET** `GET /api/matches/profiles/{id}`

**Description:**
Retrieves all user profiles that have been matched with the specified user.

### Path Parameters:
| Parameter | Type      | Required | Description                     |
|-----------|-----------|----------|---------------------------------|
| `id`      | `integer` | Yes      | The unique identifier of the user whose matches are being retrieved. |

### Response:

#### 200 OK
**Description:** Successfully retrieved a list of matched user profiles.

**Response Body:**
```json
[
  {
    "user": {
      "id": 123,
      "email": "user@example.com",
      "status": "ACTIVE",
      "matches": [...],
      "deletedAt": null
    },
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1995-05-20",
    "gender": "MALE",
    "userPhotos": [
      {
        "name": "profile_pic.jpg",
        "createdAt": "2024-03-10T12:00:00Z",
        "updatedAt": "2024-03-12T12:00:00Z",
        "deletedAt": null
      }
    ],
    "bio": "Looking for a friendly roommate!",
    "minAgePreference": 20,
    "maxAgePreference": 30,
    "preferredGenders": ["FEMALE", "OTHER"],
    "preferredLocations": ["HELSINKI"],
    "maxRent": "MID",
    "cleanliness": "TIDY",
    "lifestyle": ["EARLY_BIRD", "STUDENT"],
    "createdAt": "2024-01-15T08:30:00Z",
    "updatedAt": "2024-02-10T14:45:00Z",
    "deletedAt": null,
    "id": 456
  }
]
```

### Response Fields:

| Field                | Type     | Description |
|----------------------|---------|-------------|
| `user`              | `object` | Contains user details. |
| `firstName`         | `string` | First name of the user. |
| `lastName`          | `string` | Last name of the user. |
| `dateOfBirth`       | `string` (date) | User's date of birth. |
| `gender`            | `string` | Gender of the user (MALE, FEMALE, OTHER, NOT_IMPORTANT). |
| `userPhotos`        | `array`  | List of user photos. |
| `bio`               | `string` | Short bio of the user. |
| `minAgePreference`  | `integer` | Minimum age preference for a match. |
| `maxAgePreference`  | `integer` | Maximum age preference for a match. |
| `preferredGenders`  | `array`  | Preferred gender(s) of a roommate. |
| `preferredLocations` | `array` | Preferred locations for living. |
| `maxRent`          | `string` | Preferred maximum rent (LOW, MID, HIGH). |
| `cleanliness`       | `string` | Cleanliness preference (SPOTLESS, TIDY, CASUAL, MESSY, CAREFREE). |
| `lifestyle`         | `array`  | Lifestyle preferences (EARLY_BIRD, NIGHT_OWL, PARTY_GOER, etc.). |
| `createdAt`         | `string` (date-time) | Timestamp when the profile was created. |
| `updatedAt`         | `string` (date-time) | Timestamp when the profile was last updated. |
| `deletedAt`         | `string` (date-time) | Timestamp if the profile was deleted. |
| `id`               | `int64`   | Unique identifier for the user profile. |

---


