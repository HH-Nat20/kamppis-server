# Swipes API Documentation

## Base URL

```
https://kamppis.hellmanstudios.fi
```

## Endpoints

### 1. Create a Swipe

#### **POST `/api/swipes`**

This endpoint allows a user to swipe on another user.

#### **Request Body**

- **Content-Type:** `application/json`

| Field         | Type    | Description                          | Required |
|---------------|---------|--------------------------------------| -------- |
| swipingUserId | integer | ID of the user performing the swipe  | ✅        |
| swipedUserId  | integer | ID of the user being swiped on       | ✅        |
| isRightSwipe  | boolean | True: right swipe, false: left swipe | ✅      |

#### **Example Request**

```json
{
  "swipingUserId": 123,
  "swipedUserId": 456,
  "isRightSwipe": true
}
```

#### **Responses**

- **200 OK** – Returns the swipe response containing the swipe ID and user details, and if the swipe resulted in a match.

**Response Body:**

```json
{
  "swipeId": 789,
  "swipingUser": {
    "email": "user1@example.com",
    "status": "ACTIVE",
    "id": 123
  },
  "swipedUser": {
    "email": "user2@example.com",
    "status": "ACTIVE",
    "id": 456
  },
  "isRightSwipe": true,
  "isMatch": false
}
```

---

### 2. Get All Swipes

#### **GET /api/swipes/**

This endpoint retrieves all swipe records.

#### **Responses**

- **200 OK** – Returns an array of swipe records.

**Response Body:**

```json
[
  {
    "swipingUser": {
      "email": "user1@example.com",
      "status": "ACTIVE",
      "id": 123
    },
    "swipedUser": {
      "email": "user2@example.com",
      "status": "ACTIVE",
      "id": 456
    },
    "createdAt": "2025-03-15T12:34:56Z",
    "id": 789
  }
]
```

---

## Schema Definitions

### **SwipeRequest**

```json
{
  "swipingUserId": 123,
  "swipedUserId": 456,
  "isRightSwipe": true
}
```

### **SwipeResponse**

```json
{
  "swipeId": 789,
  "swipingUser": {
    "email": "user1@example.com",
    "status": "ACTIVE",
    "id": 123
  },
  "swipedUser": {
    "email": "user2@example.com",
    "status": "ACTIVE",
    "id": 456
  },
  "isRightSwipe": true,
  "isMatch": false
}
```

### **Swipe**

```json
{
  "swipingUser": {
    "email": "user1@example.com",
    "status": "ACTIVE",
    "id": 123
  },
  "swipedUser": {
    "email": "user2@example.com",
    "status": "ACTIVE",
    "id": 456
  },
  "createdAt": "2025-03-15T12:34:56Z",
  "isRightSwipe": true,
  "isMatch": false,
  "id": 789
}
```

