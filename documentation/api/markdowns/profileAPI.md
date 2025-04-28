
# kamppis_server API for profiles

- [findAll](#findall)
- [findById](#findbyid)
- [updateProfile](#updateprofile)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="profile-api-default">Default</h1>

## findAll

<a id="opIdfindAll"></a>

`GET /api/profiles`

*Returns all profiles*

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
|*anonymous*|[[ProfileDTO](#schemaprofiledto)]|false|none|none|

## findById

<a id="opIdfindById"></a>

`GET /api/profiles/{id}`

*Returns a profile by its id*

<h3 id="findbyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="findbyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProfileDTO](#schemaprofiledto)|

## updateProfile

<a id="opIdupdateProfile"></a>

`PUT /api/profiles/{id}`

*Updates a profile*

> Body parameter

```json
{}
```

<h3 id="updateprofile-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[ProfileDTO](#schemaprofiledto)|true|none|

> Example responses

> 200 Response

<h3 id="updateprofile-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProfileDTO](#schemaprofiledto)|

# Schemas

<h2 id="tocS_ProfileDTO">ProfileDTO</h2>
<!-- backwards compatibility -->
<a id="schemaprofiledto"></a>
<a id="schema_ProfileDTO"></a>
<a id="tocSprofiledto"></a>
<a id="tocsprofiledto"></a>

```json
{}

```

### Properties

*None*

