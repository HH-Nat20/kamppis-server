
# kamppis_server API for room profiles

- [findAll](#findbyall)
- [add](#add)
- [findById](#findbyid)
- [update](#update)
- [delete](#delete)
- [findRoomProfilesThatMeetCriteria](#findroomprofilesthatmeetcriteria)
- [findUserProfilesWhoHaveSwipedUsersRoom](#finduserprofileswhohaveswipedusersroom)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="room-profile-api-default">Default</h1>

## findAll

<a id="opIdfindAll"></a>

`GET /api/room-profiles`

*Returns all room profiles*

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
|*anonymous*|[[RoomProfileDTO](#schemaroomprofiledto)]|false|none|none|

## add

<a id="opIdadd"></a>

`POST /api/room-profiles`

*Adds a new room profile*

> Body parameter

```json
{
  "userIds": [
    0
  ],
  "flatId": 0,
  "name": "string",
  "rent": 0,
  "isPrivateRoom": true,
  "furnished": true,
  "furnishedInfo": "string",
  "bio": "string",
  "id": 0
}
```

<h3 id="add-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[RoomProfileRequest](#schemaroomprofilerequest)|true|none|

> Example responses

> 200 Response

<h3 id="add-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RoomProfileDTO](#schemaroomprofiledto)|

## findById

<a id="opIdfindById"></a>

`GET /api/room-profiles/{id}`

*Returns a room profile by its id*

<h3 id="findbyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="findbyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RoomProfileDTO](#schemaroomprofiledto)|

## update

<a id="opIdupdate"></a>

`PUT /api/room-profiles/{id}`

*Updates a room profile*

> Body parameter

```json
{
  "userIds": [
    0
  ],
  "flatId": 0,
  "name": "string",
  "rent": 0,
  "isPrivateRoom": true,
  "furnished": true,
  "furnishedInfo": "string",
  "bio": "string",
  "id": 0
}
```

<h3 id="update-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[RoomProfileRequest](#schemaroomprofilerequest)|true|none|

> Example responses

> 200 Response

<h3 id="update-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RoomProfileDTO](#schemaroomprofiledto)|

## delete

<a id="opIddelete"></a>

`DELETE /api/room-profiles/{id}`

*Deletes a room profile*

<h3 id="delete-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="delete-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Void](#schemavoid)|

## findRoomProfilesThatMeetCriteria

<a id="opIdfindRoomProfilesThatMeetCriteria"></a>

`GET /api/room-profiles/{userId}/query`

*Finds room profiles based on query initiator's preferences*

<h3 id="findroomprofilesthatmeetcriteria-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|userId|path|integer(int64)|true|none|
|page|query|integer(int32)|false|none|
|size|query|integer(int32)|true|none|

> Example responses

> 200 Response

<h3 id="findroomprofilesthatmeetcriteria-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RoomProfileDTO](#schemaroomprofiledto)|

## findUserProfilesWhoHaveSwipedUsersRoom

<a id="opIdfindUserProfilesWhoHaveSwipedUsersRoom"></a>

`GET /api/room-profiles/{roomProfileId}/swipersquery`

*Returns user profiles who have swiped the room profile*

<h3 id="finduserprofileswhohaveswipedusersroom-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|roomProfileId|path|integer(int64)|true|none|
|page|query|integer(int32)|false|none|
|size|query|integer(int32)|true|none|

> Example responses

> 200 Response

<h3 id="finduserprofileswhohaveswipedusersroom-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserProfileDTO](#schemauserprofiledto)|

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

<h2 id="tocS_RoomProfileRequest">RoomProfileRequest</h2>
<!-- backwards compatibility -->
<a id="schemaroomprofilerequest"></a>
<a id="schema_RoomProfileRequest"></a>
<a id="tocSroomprofilerequest"></a>
<a id="tocsroomprofilerequest"></a>

```json
{
  "userIds": [
    0
  ],
  "flatId": 0,
  "name": "string",
  "rent": 0,
  "isPrivateRoom": true,
  "furnished": true,
  "furnishedInfo": "string",
  "bio": "string",
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userIds|[integer]|true|none|none|
|flatId|integer(int64)|true|none|none|
|name|string¦null|false|none|none|
|rent|integer(int32)|true|none|none|
|isPrivateRoom|boolean|true|none|none|
|furnished|boolean|true|none|none|
|furnishedInfo|string¦null|false|none|none|
|bio|string|true|none|none|
|id|integer(int64)¦null|false|none|none|

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

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userId|integer(int64)|true|none|none|

