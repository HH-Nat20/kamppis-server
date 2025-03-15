# User Profiles API Documentation

## Base URL
```
https://kamppis.hellmanstudios.fi
```

## Endpoints

### 1. Create a User Profile

**POST `/api/user-profiles`**

#### Description
Creates a new user profile.

#### Request Body
- **Content-Type**: `application/json`
- **Schema**: `UserProfile`

#### Response
- **200 OK**
- **Content-Type**: `application/json`
- **Schema**: `UserProfileDTO`

---

### 2. Retrieve All User Profiles

**GET /api/user-profiles/**

#### Description
Fetches all user profiles.

#### Response
- **200 OK**
- **Content-Type**: `application/json`
- **Schema**: Array of `UserProfileDTO`

---

### 3. Retrieve a User Profile by ID

**GET `/api/user-profiles/{id}`**

#### Description
Fetches a user profile by its unique ID.

#### Path Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | integer (int64) | Unique identifier for the user profile |

#### Response
- **200 OK**
- **Content-Type**: `application/json`
- **Schema**: `UserProfileDTO`

---

### 4. Update a User Profile

**PUT `/api/user-profiles/{id}`**

#### Description
Updates an existing user profile by ID.

#### Path Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | integer (int64) | Unique identifier for the user profile |

#### Request Body
- **Content-Type**: `application/json`
- **Schema**: `UserProfileDTO`

#### Response
- **200 OK**
- **Content-Type**: `application/json`
- **Schema**: `UserProfileDTO`

---

### 5. Retrieve User Profiles Matching Criteria

**GET `/api/user-profiles/{id}/query`**

#### Description
Fetches user profiles that meet specific criteria based on the given user profile ID.

#### Path Parameters
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | integer (int64) | Unique identifier for the user profile |

#### Response
- **200 OK**
- **Content-Type**: `application/json`
- **Schema**: Array of `UserProfileDTO`

---

## Schemas

### UserProfile
Represents the user profile entity.

```json
{
  "user": { "email": "string", "status": "ACTIVE | INACTIVE", "matches": [], "id": 0 },
  "firstName": "string",
  "lastName": "string",
  "dateOfBirth": "YYYY-MM-DD",
  "gender": "MALE | FEMALE | OTHER | NOT_IMPORTANT",
  "userPhotos": [{ "name": "string", "id": 0 }],
  "bio": "string",
  "minAgePreference": 0,
  "maxAgePreference": 0,
  "preferredGenders": ["MALE", "FEMALE"],
  "preferredLocations": ["HELSINKI", "ESPOO", "VANTAA"],
  "maxRent": "LOW | MID | HIGH",
  "cleanliness": "SPOTLESS | TIDY | CASUAL | MESSY | CAREFREE",
  "lifestyle": ["EARLY_BIRD", "NIGHT_OWL", "PARTY_GOER", "HOMEBODY", "STUDENT", "WORKING"],
  "createdAt": "YYYY-MM-DDTHH:MM:SSZ",
  "updatedAt": "YYYY-MM-DDTHH:MM:SSZ",
  "deletedAt": "YYYY-MM-DDTHH:MM:SSZ",
  "id": 0
}
```

### UserProfileDTO
Represents a simplified user profile for API responses.

```json
{
  "firstName": "string",
  "lastName": "string",
  "age": 0,
  "gender": "MALE | FEMALE | OTHER | NOT_IMPORTANT",
  "userPhotos": [{ "name": "string", "id": 0 }],
  "bio": "string",
  "minAgePreference": 0,
  "maxAgePreference": 0,
  "preferredGenders": ["MALE", "FEMALE"],
  "preferredLocations": ["HELSINKI", "ESPOO", "VANTAA"],
  "maxRent": "LOW | MID | HIGH",
  "cleanliness": "SPOTLESS | TIDY | CASUAL | MESSY | CAREFREE",
  "lifestyle": ["EARLY_BIRD", "NIGHT_OWL", "PARTY_GOER", "HOMEBODY", "STUDENT", "WORKING"],
  "id": 0
}
```


