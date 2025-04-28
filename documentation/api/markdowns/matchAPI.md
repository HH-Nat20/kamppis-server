
# kamppis_server API for matches

- [addMatch](#addmatch)
- [findAllMatchesForUser](#findallmatchesforuser)
- [findMatchById](#findmatchbyid)
- [removeFromMatch](#removefrommatch)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="match-api-default">Default</h1>

## addMatch

<a id="opIdaddMatch"></a>

`POST /api/matches`

*Adds a new match*

> Body parameter

```json
{
  "userIds": [
    0
  ]
}
```

<h3 id="addmatch-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[MatchRequest](#schemamatchrequest)|true|none|

> Example responses

> 200 Response

<h3 id="addmatch-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MatchDTO](#schemamatchdto)|

## findAllMatchesForUser

<a id="opIdfindAllMatchesForUser"></a>

`GET /api/matches/profiles/{id}`

*Returns all user's matches*

<h3 id="findallmatchesforuser-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="findallmatchesforuser-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|

<h3 id="findallmatchesforuser-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[UserProfileDTO](#schemauserprofiledto)]|false|none|none|

## findMatchById

<a id="opIdfindMatchById"></a>

`GET /api/matches/{id}`

*Returns a match by its id*

<h3 id="findmatchbyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="findmatchbyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MatchDTO](#schemamatchdto)|

## removeFromMatch

<a id="opIdremoveFromMatch"></a>

`POST /api/matches/{id}`

*Removes a user from match*

> Body parameter

```json
{
  "userIds": [
    0
  ]
}
```

<h3 id="removefrommatch-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[MatchRequest](#schemamatchrequest)|true|none|

> Example responses

> 200 Response

<h3 id="removefrommatch-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MatchDTO](#schemamatchdto)|

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

<h2 id="tocS_MatchDTO">MatchDTO</h2>
<!-- backwards compatibility -->
<a id="schemamatchdto"></a>
<a id="schema_MatchDTO"></a>
<a id="tocSmatchdto"></a>
<a id="tocsmatchdto"></a>

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
  "createdAt": "2019-08-24T14:15:22Z",
  "updatedAt": "2019-08-24T14:15:22Z",
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userIds|[integer]|true|none|none|
|users|[[UserSummaryDTO](#schemausersummarydto)]|true|none|none|

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

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userId|integer(int64)|true|none|none|

<h2 id="tocS_MatchRequest">MatchRequest</h2>
<!-- backwards compatibility -->
<a id="schemamatchrequest"></a>
<a id="schema_MatchRequest"></a>
<a id="tocSmatchrequest"></a>
<a id="tocsmatchrequest"></a>

```json
{
  "userIds": [
    0
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|userIds|[integer]|true|none|none|

