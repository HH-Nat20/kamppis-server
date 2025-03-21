# Users API

## Base URL

`https://kamppis.hellmanstudios.fi`

## Endpoints

### Create User Profile

**POST** `/api/users`

#### Summary
Creates a new user profile.

#### Request Body
```json
{
  "email": "string",
  "status": "ACTIVE" | "INACTIVE",
  "deletedAt": "string (date-time)",
  "matches": [
    {
      "users": ["User"],
      "createdAt": "string (date-time)",
      "updatedAt": "string (date-time)",
      "id": "integer (int64)"
    }
  ],
  "id": "integer (int64)"
}
```

#### Responses
- **200 OK**: Returns the created `User` object.

---

### Get All Users

**GET** `/api/users/`

#### Summary
Retrieves a list of all users.

#### Responses
- **200 OK**: Returns an array of `User` objects.

---

### Get User by ID

**GET** `/api/users/{id}`

#### Summary
Retrieves a specific user by ID.

#### Parameters
- **id** (path, required): `integer (int64)` - The ID of the user.

#### Responses
- **200 OK**: Returns the `User` object.

---

### Update User Profile

**PUT** `/api/users/{id}`

#### Summary
Updates a user profile by ID.

#### Parameters
- **id** (path, required): `integer (int64)` - The ID of the user.

#### Request Body
Same as `POST /api/users`.

#### Responses
- **200 OK**: Returns the updated `User` object.

---

### Delete User

**DELETE** `/api/users/{id}`

#### Summary
Deletes a user by ID.

#### Parameters
- **id** (path, required): `integer (int64)` - The ID of the user.

#### Responses
- **200 OK**: Returns an empty response.

---

### Restore Deleted User

**PUT** `/api/users/{id}/restore`

#### Summary
Restores a deleted user by ID.

#### Parameters
- **id** (path, required): `integer (int64)` - The ID of the user.

#### Request Body
Same as `POST /api/users`.

#### Responses
- **200 OK**: Returns the restored `User` object.

---

## Schemas

### User
```json
{
  "email": "string",
  "status": "ACTIVE" | "INACTIVE",
  "deletedAt": "string (date-time)",
  "matches": [
    {
      "users": ["User"],
      "createdAt": "string (date-time)",
      "updatedAt": "string (date-time)",
      "id": "integer (int64)"
    }
  ],
  "id": "integer (int64)"
}
```

### Match
```json
{
  "users": ["User"],
  "createdAt": "string (date-time)",
  "updatedAt": "string (date-time)",
  "id": "integer (int64)"
}
```

