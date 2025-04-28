
# kamppis_server API for users

- [findAll](#findall)
- [addUser](#adduser)
- [findUserById](#finduserbyid)
- [updateUser](#updateuser)
- [deleteById](#deletebyid)
- [findAllMock](#findallmock)
- [getCopyOfUserData](#getcopyofuserdata)
- [restoreById](#restorebyid)
- [getUserPreferences](#getuserpreferences)
- [updateUserPreferences](#updateuserpreferences)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URL:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

## findAll

<a id="opIdfindAll"></a>

`GET /api/users`

*Returns all users*

> Example responses

> 200 Response

<h3 id="findall-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="findall-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[UserDTO](#schemauserdto)]|false|none|none|
|» roomProfiles|[[RoomProfileDTO](#schemaroomprofiledto)]¦null|false|none|none|
|» id|integer(int64)¦null|false|none|none|

## addUser

<a id="opIdaddUser"></a>

`POST /api/users`

*Adds a new user*

> Body parameter

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "dateOfBirth": "2019-08-24",
  "id": 0
}
```

<h3 id="adduser-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[UserRequest](#schemauserrequest)|true|none|

> Example responses

> 200 Response

<h3 id="adduser-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserDTO](#schemauserdto)|

## findUserById

<a id="opIdfindUserById"></a>

`GET /api/users/{id}`

*Returns a single user by its id*

<h3 id="finduserbyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="finduserbyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserDTO](#schemauserdto)|

## updateUser

<a id="opIdupdateUser"></a>

`PUT /api/users/{id}`

*Updates a user*

> Body parameter

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "dateOfBirth": "2019-08-24",
  "id": 0
}
```

<h3 id="updateuser-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UserRequest](#schemauserrequest)|true|none|

> Example responses

> 200 Response

<h3 id="updateuser-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserDTO](#schemauserdto)|

## deleteById

<a id="opIddeleteById"></a>

`DELETE /api/users/{id}`

*Deletes a user*

<h3 id="deletebyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="deletebyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Void](#schemavoid)|

## findAllMock

<a id="opIdfindAllMock"></a>

`GET /api/users/mock`

*Returns all mock users*

> Example responses

> 200 Response

<h3 id="findallmock-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="findallmock-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[UserDTO](#schemauserdto)]|false|none|none|
|» roomProfiles|[[RoomProfileDTO](#schemaroomprofiledto)]¦null|false|none|none|
|» id|integer(int64)¦null|false|none|none|

## getCopyOfUserData

<a id="opIdgetCopyOfUserData"></a>

`GET /api/users/{id}/copy`

*GET api/users/{id}/copy*

<h3 id="getcopyofuserdata-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="getcopyofuserdata-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserDataDTO](#schemauserdatadto)|

## restoreById

<a id="opIdrestoreById"></a>

`PUT /api/users/{id}/restore`

*Restores a deleted user*

<h3 id="restorebyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="restorebyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserDTO](#schemauserdto)|

## getUserPreferences

<a id="opIdgetUserPreferences"></a>

`GET /api/users/{id}/preferences`

*Returns user's roommate and room preferences*

<h3 id="getuserpreferences-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="getuserpreferences-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserPreferenceDTO](#schemauserpreferencedto)|

## updateUserPreferences

<a id="opIdupdateUserPreferences"></a>

`PUT /api/users/{id}/preferences`

*Returns user's roommate and room preferences*

> Body parameter

```json
{
  "roomPreference": {
    "userId": 0,
    "maxRent": 0,
    "hasPrivateRoom": true,
    "maxRoommates": 0,
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  },
  "roommatePreference": {
    "userId": 0,
    "minAgePreference": 0,
    "maxAgePreference": 0,
    "genderPreferences": [
      "MALE"
    ],
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  }
}
```

<h3 id="updateuserpreferences-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[UserPreferenceRequest](#schemauserpreferencerequest)|true|none|

> Example responses

> 200 Response

<h3 id="updateuserpreferences-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserPreferenceDTO](#schemauserpreferencedto)|

# Schemas

<h2 id="tocS_UserSummaryDTO">UserSummaryDTO</h2>
<!-- backwards compatibility -->
<a id="schemausersummarydto"></a>
<a id="schema_UserSummaryDTO"></a>
<a id="tocSusersummarydto"></a>
<a id="tocsusersummarydto"></a>

```json
{
  "firstName": "string",
  "lastName": "string",
  "age": 0,
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "isOnline": true,
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|firstName|string|true|none|none|
|lastName|string|true|none|none|
|age|integer(int64)¦null|false|none|none|
|gender|string|true|none|none|
|lookingFor|string¦null|false|none|none|
|isOnline|boolean¦null|false|none|none|
|id|integer(int64)¦null|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|gender|MALE|
|gender|FEMALE|
|gender|OTHER|
|gender|NOT_IMPORTANT|
|lookingFor|OTHER_USER_PROFILES|
|lookingFor|ROOM_PROFILES|
|lookingFor|OTHER_USER_PROFILES_OR_ROOM_PROFILES|
|lookingFor|USER_PROFILES|

<h2 id="tocS_ProfilePhotoDTO">ProfilePhotoDTO</h2>
<!-- backwards compatibility -->
<a id="schemaprofilephotodto"></a>
<a id="schema_ProfilePhotoDTO"></a>
<a id="tocSprofilephotodto"></a>
<a id="tocsprofilephotodto"></a>

```json
{
  "profileId": 0,
  "url": "string",
  "isProfilePhoto": true,
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|profileId|integer(int64)|true|none|none|
|url|string|true|none|none|
|isProfilePhoto|boolean|true|none|none|
|id|integer(int64)¦null|false|none|none|

<h2 id="tocS_UserProfileDTO">UserProfileDTO</h2>
<!-- backwards compatibility -->
<a id="schemauserprofiledto"></a>
<a id="schema_UserProfileDTO"></a>
<a id="tocSuserprofiledto"></a>
<a id="tocsuserprofiledto"></a>

```json
{
  "userId": 0,
  "user": {
    "firstName": "string",
    "lastName": "string",
    "age": 0,
    "gender": "MALE",
    "lookingFor": "OTHER_USER_PROFILES",
    "isOnline": true,
    "id": 0
  },
  "bio": "string",
  "cleanliness": "SPOTLESS",
  "pets": "PET_OWNER",
  "lifestyle": [
    "EARLY_BIRD"
  ],
  "photos": [
    {
      "profileId": 0,
      "url": "string",
      "isProfilePhoto": true,
      "id": 0
    }
  ],
  "id": 0
}

```

### Properties

| Name        | Type                                      |Required|Restrictions|Description|
|-------------|-------------------------------------------|---|---|---|
| userId      | integer(int64)                            |true|none|none|
| user        | [[UserSummaryDTO](#schemausersummarydto)]¦null                |false|none|none|
| bio         | string¦null                               |false|none|none|
| cleanliness | string¦null                               |false|none|none|
| pets        | string¦null                               |false|none|none|
| lifestyle   | [string]¦null                             |false|none|none|
| photos      | [[ProfilePhotoDTO](#schemaprofilephotodto)]¦null |false|none|none|

<h2 id="tocS_FlatDTO">FlatDTO</h2>
<!-- backwards compatibility -->
<a id="schemaflatdto"></a>
<a id="schema_FlatDTO"></a>
<a id="tocSflatdto"></a>
<a id="tocsflatdto"></a>

```json
{
  "name": "string",
  "description": "string",
  "location": "HELSINKI",
  "totalRoommates": 0,
  "petHousehold": true,
  "flatUtilities": [
    "WIFI"
  ],
  "roomProfileIds": [
    0
  ],
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|name|string|true|none|none|
|description|string|true|none|none|
|location|string|true|none|none|
|totalRoommates|integer(int32)|true|none|none|
|petHousehold|boolean¦null|false|none|none|
|flatUtilities|[string]¦null|false|none|none|
|roomProfileIds|[integer]¦null|false|none|none|
|id|integer(int64)¦null|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|location|HELSINKI|
|location|ESPOO|
|location|TAMPERE|
|location|VANTAA|
|location|OULU|
|location|TURKU|
|location|JYVASKYLA|
|location|KUOPIO|
|location|LAHTI|
|location|PORI|
|location|KOUVOLA|
|location|JOENSUU|
|location|LAPPEENRANTA|
|location|HAMEENLINNA|
|location|VAASA|
|location|SEINAJOKA|
|location|ROVANIEMI|
|location|MIKKELI|
|location|SALO|
|location|KOTKA|
|location|PORVOO|
|location|KOKKOLA|
|location|HYVINKAA|
|location|LOHJA|
|location|JARVENPAA|
|location|NURMIJARVI|
|location|KIRKKONUMMI|
|location|TUUSULA|
|location|RAUMA|
|location|KERAVA|
|location|KAJAANI|
|location|KAARINA|
|location|NOKIA|
|location|YLOJARVI|
|location|KANGASALA|
|location|SAVONLINNA|
|location|VIHTI|
|location|RIIHIMAKI|
|location|RAASEPORI|
|location|IMATRA|
|location|RAISIO|
|location|RAAHE|
|location|LEMPAALA|
|location|SASTAMALA|
|location|HOLLOLA|
|location|SIPOO|
|location|TORNIO|
|location|SIILINJARVI|
|location|IISALMI|
|location|MANTSALA|

<h2 id="tocS_RoomProfileDTO">RoomProfileDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroomprofiledto"></a>
<a id="schema_RoomProfileDTO"></a>
<a id="tocSroomprofiledto"></a>
<a id="tocsroomprofiledto"></a>

```json
{
  "userIds": [
    0
  ],
  "users": [
    {
      "firstName": "string",
      "lastName": "string",
      "age": 0,
      "gender": "MALE",
      "lookingFor": "OTHER_USER_PROFILES",
      "isOnline": true,
      "id": 0
    }
  ],
  "flat": {
    "name": "string",
    "description": "string",
    "location": "HELSINKI",
    "totalRoommates": 0,
    "petHousehold": true,
    "flatUtilities": [
      "WIFI"
    ],
    "roomProfileIds": [
      0
    ],
    "id": 0
  },
  "name": "string",
  "totalRoommates": 0,
  "location": "HELSINKI",
  "rent": 0,
  "isPrivateRoom": true,
  "furnished": true,
  "furnishedInfo": "string",
  "photos": [
    {
      "profileId": 0,
      "url": "string",
      "isProfilePhoto": true,
      "id": 0
    }
  ],
  "bio": "string",
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userIds|[integer]|true|none|none|
|users|[[UserSummaryDTO](#schemausersummarydto)]¦null|false|none|none|

<h2 id="tocS_UserDTO">UserDTO</h2>
<!-- backwards compatibility -->
<a id="schemauserdto"></a>
<a id="schema_UserDTO"></a>
<a id="tocSuserdto"></a>
<a id="tocsuserdto"></a>

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "dateOfBirth": "2019-08-24",
  "age": 0,
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "status": "ACTIVE",
  "isOnline": true,
  "matchIds": [
    0
  ],
  "userProfile": {
    "userId": 0,
    "user": {
      "firstName": "string",
      "lastName": "string",
      "age": 0,
      "gender": "MALE",
      "lookingFor": "OTHER_USER_PROFILES",
      "isOnline": true,
      "id": 0
    },
    "bio": "string",
    "cleanliness": "SPOTLESS",
    "pets": "PET_OWNER",
    "lifestyle": [
      "EARLY_BIRD"
    ],
    "photos": [
      {
        "profileId": 0,
        "url": "string",
        "isProfilePhoto": true,
        "id": 0
      }
    ],
    "id": 0
  },
  "roomProfiles": [
    {
      "userIds": [
        0
      ],
      "users": [
        {
          "firstName": "string",
          "lastName": "string",
          "age": 0,
          "gender": "MALE",
          "lookingFor": "OTHER_USER_PROFILES",
          "isOnline": true,
          "id": 0
        }
      ],
      "flat": {
        "name": "string",
        "description": "string",
        "location": "HELSINKI",
        "totalRoommates": 0,
        "petHousehold": true,
        "flatUtilities": [
          "WIFI"
        ],
        "roomProfileIds": [
          0
        ],
        "id": 0
      },
      "name": "string",
      "totalRoommates": 0,
      "location": "HELSINKI",
      "rent": 0,
      "isPrivateRoom": true,
      "furnished": true,
      "furnishedInfo": "string",
      "photos": [
        {
          "profileId": 0,
          "url": "string",
          "isProfilePhoto": true,
          "id": 0
        }
      ],
      "bio": "string",
      "id": 0
    }
  ],
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|firstName|string|true|none|none|
|lastName|string|true|none|none|
|email|string|true|none|none|
|dateOfBirth|string(date)|true|none|none|
|age|integer(int64)¦null|false|none|none|
|gender|string|true|none|none|
|lookingFor|string¦null|false|none|none|
|status|string¦null|false|none|none|
|isOnline|boolean¦null|false|none|none|
|matchIds|[integer]¦null|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|gender|MALE|
|gender|FEMALE|
|gender|OTHER|
|gender|NOT_IMPORTANT|
|lookingFor|OTHER_USER_PROFILES|
|lookingFor|ROOM_PROFILES|
|lookingFor|OTHER_USER_PROFILES_OR_ROOM_PROFILES|
|lookingFor|USER_PROFILES|
|status|ACTIVE|
|status|INACTIVE|
|status|DELETED|

<h2 id="tocS_ProfilePhotoDataDTO">ProfilePhotoDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemaprofilephotodatadto"></a>
<a id="schema_ProfilePhotoDataDTO"></a>
<a id="tocSprofilephotodatadto"></a>
<a id="tocsprofilephotodatadto"></a>

```json
{
  "url": "string",
  "isProfilePhoto": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "deletedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|url|string|true|none|none|
|isProfilePhoto|boolean|true|none|none|
|createdAt|string(date-time)|true|none|none|
|updatedAt|string(date-time)¦null|false|none|none|
|deletedAt|string(date-time)¦null|false|none|none|

<h2 id="tocS_UserProfileDataDTO">UserProfileDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemauserprofiledatadto"></a>
<a id="schema_UserProfileDataDTO"></a>
<a id="tocSuserprofiledatadto"></a>
<a id="tocsuserprofiledatadto"></a>

```json
{
  "bio": "string",
  "cleanliness": "SPOTLESS",
  "pets": "PET_OWNER",
  "lifestyle": [
    "EARLY_BIRD"
  ],
  "photos": [
    {
      "url": "string",
      "isProfilePhoto": true,
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "deletedAt": "2019-08-24T14:15:22Z"
    }
  ],
  "status": "ACTIVE",
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "deletedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|bio|string|true|none|none|
|cleanliness|string¦null|false|none|none|
|pets|string¦null|false|none|none|
|lifestyle|[string]¦null|false|none|none|
|photos|[[ProfilePhotoDataDTO](#schemaprofilephotodatadto)]|true|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|cleanliness|SPOTLESS|
|cleanliness|TIDY|
|cleanliness|CASUAL|
|cleanliness|MESSY|
|cleanliness|CAREFREE|
|pets|PET_OWNER|
|pets|OK_WITH_PETS|
|pets|PREFER_NO_PETS|

<h2 id="tocS_FlatDataDTO">FlatDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemaflatdatadto"></a>
<a id="schema_FlatDataDTO"></a>
<a id="tocSflatdatadto"></a>
<a id="tocsflatdatadto"></a>

```json
{
  "name": "string",
  "description": "string",
  "location": "HELSINKI",
  "totalRoommates": 0,
  "petHousehold": true,
  "flatUtilities": [
    "WIFI"
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|name|string|true|none|none|
|description|string|true|none|none|
|location|string|true|none|none|
|totalRoommates|integer(int32)|true|none|none|
|petHousehold|boolean¦null|false|none|none|
|flatUtilities|[string]¦null|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|location|HELSINKI|
|location|ESPOO|
|location|TAMPERE|
|location|VANTAA|
|location|OULU|
|location|TURKU|
|location|JYVASKYLA|
|location|KUOPIO|
|location|LAHTI|
|location|PORI|
|location|KOUVOLA|
|location|JOENSUU|
|location|LAPPEENRANTA|
|location|HAMEENLINNA|
|location|VAASA|
|location|SEINAJOKA|
|location|ROVANIEMI|
|location|MIKKELI|
|location|SALO|
|location|KOTKA|
|location|PORVOO|
|location|KOKKOLA|
|location|HYVINKAA|
|location|LOHJA|
|location|JARVENPAA|
|location|NURMIJARVI|
|location|KIRKKONUMMI|
|location|TUUSULA|
|location|RAUMA|
|location|KERAVA|
|location|KAJAANI|
|location|KAARINA|
|location|NOKIA|
|location|YLOJARVI|
|location|KANGASALA|
|location|SAVONLINNA|
|location|VIHTI|
|location|RIIHIMAKI|
|location|RAASEPORI|
|location|IMATRA|
|location|RAISIO|
|location|RAAHE|
|location|LEMPAALA|
|location|SASTAMALA|
|location|HOLLOLA|
|location|SIPOO|
|location|TORNIO|
|location|SIILINJARVI|
|location|IISALMI|
|location|MANTSALA|

<h2 id="tocS_RoomProfileDataDTO">RoomProfileDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroomprofiledatadto"></a>
<a id="schema_RoomProfileDataDTO"></a>
<a id="tocSroomprofiledatadto"></a>
<a id="tocsroomprofiledatadto"></a>

```json
{
  "flat": {
    "name": "string",
    "description": "string",
    "location": "HELSINKI",
    "totalRoommates": 0,
    "petHousehold": true,
    "flatUtilities": [
      "WIFI"
    ]
  },
  "name": "string",
  "rent": 0,
  "isPrivateRoom": true,
  "furnished": true,
  "furnishedInfo": "string",
  "photos": [
    {
      "url": "string",
      "isProfilePhoto": true,
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "deletedAt": "2019-08-24T14:15:22Z"
    }
  ],
  "bio": "string",
  "status": "ACTIVE",
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "deletedAt": "2019-08-24T14:15:22Z"
}

```

### Properties

*None*

<h2 id="tocS_RoommatePreferenceDataDTO">RoommatePreferenceDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroommatepreferencedatadto"></a>
<a id="schema_RoommatePreferenceDataDTO"></a>
<a id="tocSroommatepreferencedatadto"></a>
<a id="tocsroommatepreferencedatadto"></a>

```json
{
  "minAgePreference": 0,
  "maxAgePreference": 0,
  "genderPreferences": [
    "MALE"
  ],
  "locationPreferences": [
    "HELSINKI"
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|minAgePreference|integer(int32)¦null|false|none|none|
|maxAgePreference|integer(int32)¦null|false|none|none|
|genderPreferences|[string]¦null|false|none|none|
|locationPreferences|[string]¦null|false|none|none|

<h2 id="tocS_RoomPreferenceDataDTO">RoomPreferenceDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroompreferencedatadto"></a>
<a id="schema_RoomPreferenceDataDTO"></a>
<a id="tocSroompreferencedatadto"></a>
<a id="tocsroompreferencedatadto"></a>

```json
{
  "maxRent": 0,
  "hasPrivateRoom": true,
  "maxRoommates": 0,
  "locationPreferences": [
    "HELSINKI"
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|maxRent|integer(int32)¦null|false|none|none|
|hasPrivateRoom|boolean¦null|false|none|none|
|maxRoommates|integer(int32)¦null|false|none|none|
|locationPreferences|[string]¦null|false|none|none|

<h2 id="tocS_UserDataDTO">UserDataDTO</h2>
<!-- backwards compatibility -->
<a id="schemauserdatadto"></a>
<a id="schema_UserDataDTO"></a>
<a id="tocSuserdatadto"></a>
<a id="tocsuserdatadto"></a>

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "dateOfBirth": "2019-08-24",
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "status": "ACTIVE",
  "isOnline": true,
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "deletedAt": "2019-08-24T14:15:22Z",
  "userProfile": {
    "bio": "string",
    "cleanliness": "SPOTLESS",
    "pets": "PET_OWNER",
    "lifestyle": [
      "EARLY_BIRD"
    ],
    "photos": [
      {
        "url": "string",
        "isProfilePhoto": true,
        "createdAt": "2019-08-24T14:15:22Z",
        "updatedAt": "2019-08-24T14:15:22Z",
        "deletedAt": "2019-08-24T14:15:22Z"
      }
    ],
    "status": "ACTIVE",
    "createdAt": "2019-08-24T14:15:22Z",
    "updatedAt": "2019-08-24T14:15:22Z",
    "deletedAt": "2019-08-24T14:15:22Z"
  },
  "roomProfiles": [
    {
      "flat": {
        "name": "string",
        "description": "string",
        "location": "HELSINKI",
        "totalRoommates": 0,
        "petHousehold": true,
        "flatUtilities": [
          "WIFI"
        ]
      },
      "name": "string",
      "rent": 0,
      "isPrivateRoom": true,
      "furnished": true,
      "furnishedInfo": "string",
      "photos": [
        {
          "url": "string",
          "isProfilePhoto": true,
          "createdAt": "2019-08-24T14:15:22Z",
          "updatedAt": "2019-08-24T14:15:22Z",
          "deletedAt": "2019-08-24T14:15:22Z"
        }
      ],
      "bio": "string",
      "status": "ACTIVE",
      "createdAt": "2019-08-24T14:15:22Z",
      "updatedAt": "2019-08-24T14:15:22Z",
      "deletedAt": "2019-08-24T14:15:22Z"
    }
  ],
  "roommatePreference": {
    "minAgePreference": 0,
    "maxAgePreference": 0,
    "genderPreferences": [
      "MALE"
    ],
    "locationPreferences": [
      "HELSINKI"
    ]
  },
  "roomPreference": {
    "maxRent": 0,
    "hasPrivateRoom": true,
    "maxRoommates": 0,
    "locationPreferences": [
      "HELSINKI"
    ]
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|firstName|string|true|none|none|
|lastName|string|true|none|none|
|email|string|true|none|none|
|dateOfBirth|string(date)|true|none|none|
|gender|string|true|none|none|
|lookingFor|string¦null|false|none|none|
|status|string¦null|false|none|none|
|isOnline|boolean¦null|false|none|none|
|createdAt|string(date-time)|true|none|none|
|updatedAt|string(date-time)¦null|false|none|none|
|deletedAt|string(date-time)¦null|false|none|none|
| roommatePreference | [[RoommatePreferenceDTO]](#schemaroommatepreferencedto)¦null |true|none|none|
| roomPreference     | [[RoomPreferenceDTO]](#schemaroompreferencedto)¦null         |true|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|gender|MALE|
|gender|FEMALE|
|gender|OTHER|
|gender|NOT_IMPORTANT|
|lookingFor|OTHER_USER_PROFILES|
|lookingFor|ROOM_PROFILES|
|lookingFor|OTHER_USER_PROFILES_OR_ROOM_PROFILES|
|lookingFor|USER_PROFILES|
|status|ACTIVE|
|status|INACTIVE|
|status|DELETED|

<h2 id="tocS_UserRequest">UserRequest</h2>
<!-- backwards compatibility -->
<a id="schemauserrequest"></a>
<a id="schema_UserRequest"></a>
<a id="tocSuserrequest"></a>
<a id="tocsuserrequest"></a>

```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "gender": "MALE",
  "lookingFor": "OTHER_USER_PROFILES",
  "dateOfBirth": "2019-08-24",
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|firstName|string|true|none|none|
|lastName|string|true|none|none|
|email|string|true|none|none|
|gender|string|true|none|none|
|lookingFor|string¦null|false|none|none|
|dateOfBirth|string(date)|true|none|none|
|id|integer(int64)¦null|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|gender|MALE|
|gender|FEMALE|
|gender|OTHER|
|gender|NOT_IMPORTANT|
|lookingFor|OTHER_USER_PROFILES|
|lookingFor|ROOM_PROFILES|
|lookingFor|OTHER_USER_PROFILES_OR_ROOM_PROFILES|
|lookingFor|USER_PROFILES|

<h2 id="tocS_Void">Void</h2>
<!-- backwards compatibility -->
<a id="schemavoid"></a>
<a id="schema_Void"></a>
<a id="tocSvoid"></a>
<a id="tocsvoid"></a>

```json
{}

```

### Properties

*None*

<h2 id="tocS_RoomPreferenceDTO">RoomPreferenceDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroompreferencedto"></a>
<a id="schema_RoomPreferenceDTO"></a>
<a id="tocSroompreferencedto"></a>
<a id="tocsroompreferencedto"></a>

```json
{
  "userId": 0,
  "maxRent": 0,
  "hasPrivateRoom": true,
  "maxRoommates": 0,
  "locationPreferences": [
    "HELSINKI"
  ],
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userId|integer(int64)¦null|false|none|none|
|maxRent|integer(int32)¦null|false|none|none|
|hasPrivateRoom|boolean¦null|false|none|none|
|maxRoommates|integer(int32)¦null|false|none|none|
|locationPreferences|[string]¦null|false|none|none|
|id|integer(int64)¦null|false|none|none|

<h2 id="tocS_RoommatePreferenceDTO">RoommatePreferenceDTO</h2>
<!-- backwards compatibility -->
<a id="schemaroommatepreferencedto"></a>
<a id="schema_RoommatePreferenceDTO"></a>
<a id="tocSroommatepreferencedto"></a>
<a id="tocsroommatepreferencedto"></a>

```json
{
  "userId": 0,
  "minAgePreference": 0,
  "maxAgePreference": 0,
  "genderPreferences": [
    "MALE"
  ],
  "locationPreferences": [
    "HELSINKI"
  ],
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userId|integer(int64)¦null|false|none|none|
|minAgePreference|integer(int32)¦null|false|none|none|
|maxAgePreference|integer(int32)¦null|false|none|none|
|genderPreferences|[string]¦null|false|none|none|
|locationPreferences|[string]¦null|false|none|none|
|id|integer(int64)¦null|false|none|none|

<h2 id="tocS_UserPreferenceDTO">UserPreferenceDTO</h2>
<!-- backwards compatibility -->
<a id="schemauserpreferencedto"></a>
<a id="schema_UserPreferenceDTO"></a>
<a id="tocSuserpreferencedto"></a>
<a id="tocsuserpreferencedto"></a>

```json
{
  "roomPreference": {
    "userId": 0,
    "maxRent": 0,
    "hasPrivateRoom": true,
    "maxRoommates": 0,
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  },
  "roommatePreference": {
    "userId": 0,
    "minAgePreference": 0,
    "maxAgePreference": 0,
    "genderPreferences": [
      "MALE"
    ],
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  },
  "id": 0
}

```

### Properties

| Name               | Type                                                         |Required|Restrictions|Description|
|--------------------|--------------------------------------------------------------|---|---|---|
| roomPreference     | [[RoomPreferenceDTO]](#schemaroompreferencedto)¦null         |true|none|none|
| roommatePreference | [[RoommatePreferenceDTO]](#schemaroommatepreferencedto)¦null |true|none|none|
|id|integer(int64)¦null|false|none|none|

<h2 id="tocS_UserPreferenceRequest">UserPreferenceRequest</h2>
<!-- backwards compatibility -->
<a id="schemauserpreferencerequest"></a>
<a id="schema_UserPreferenceRequest"></a>
<a id="tocSuserpreferencerequest"></a>
<a id="tocsuserpreferencerequest"></a>

```json
{
  "roomPreference": {
    "userId": 0,
    "maxRent": 0,
    "hasPrivateRoom": true,
    "maxRoommates": 0,
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  },
  "roommatePreference": {
    "userId": 0,
    "minAgePreference": 0,
    "maxAgePreference": 0,
    "genderPreferences": [
      "MALE"
    ],
    "locationPreferences": [
      "HELSINKI"
    ],
    "id": 0
  }
}

```

### Properties

| Name               | Type                                                         |Required|Restrictions|Description|
|--------------------|--------------------------------------------------------------|---|---|---|
| roomPreference     | [[RoomPreferenceDTO]](#schemaroompreferencedto)¦null         |true|none|none|
| roommatePreference | [[RoommatePreferenceDTO]](#schemaroommatepreferencedto)¦null |true|none|none|
|id|integer(int64)¦null|false|none|none|


