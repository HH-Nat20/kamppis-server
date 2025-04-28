
# kamppis_server API for logins

- [login](#login)
- [getProtectedData](#getprotecteddata)
- [loginWithGitHub](#loginwithgithub)
- [signUp](#signup)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="login-api-default">Default</h1>

## login

<a id="opIdlogin"></a>

`POST /api/login/mock`

*Login as a mock user*

<h3 id="login-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|email|query|string|true|none|

> Example responses

> 200 Response

<h3 id="login-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

## getProtectedData

<a id="opIdgetProtectedData"></a>

`GET /api/login/protected`

*Login as protected*

> Example responses

> 200 Response

<h3 id="getprotecteddata-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

## loginWithGitHub

<a id="opIdloginWithGitHub"></a>

`POST /api/login/github`

*Login with GitHub auth*

<h3 id="loginwithgithub-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|code|query|string|true|none|

> Example responses

> 200 Response

<h3 id="loginwithgithub-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Object](#schemaobject)|

## signup

<a id="opIdsignup"></a>

`POST /api/login/signup`

*Sign up*

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

<h3 id="signup-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|code|query|string|true|none|
|body|body|[UserRequest](#schemauserrequest)|true|none|

> Example responses

> 200 Response

<h3 id="signup-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Object](#schemaobject)|

# Schemas

<h2 id="tocS_Object">Object</h2>
<!-- backwards compatibility -->
<a id="schemaobject"></a>
<a id="schema_Object"></a>
<a id="tocSobject"></a>
<a id="tocsobject"></a>

```json
{}

```

### Properties

*None*

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

