
# kamppis_server API for images

- [getImage](#getimage)
- [uploadImage](#uploadimage)
- [deleteImage](#deleteimage)
- [updateImage](#updateimage)

Base URLs:

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="image-api-default">Default</h1>

## getImage

<a id="opIdgetImage"></a>

`GET /api/images/get/{userId}/{filename}`

*Returns an image uploaded by the user*

<h3 id="getimage-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|userId|path|integer(int64)|true|none|
|filename|path|string|true|none|

> Example responses

> 200 Response

<h3 id="getimage-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[Resource](#schemaresource)|

## uploadImage

<a id="opIduploadImage"></a>

`POST /api/images/{userId}`

*Adds a new image to the user*

<h3 id="uploadimage-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|userId|path|integer(int64)|true|none|
|image|query|string(binary)|true|none|
|isProfilePhoto|query|boolean|false|none|

> Example responses

> 200 Response

<h3 id="uploadimage-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

## deleteImage

<a id="opIddeleteImage"></a>

`DELETE /api/images/{userId}/{photoId}`

*Deletes a user's image*

<h3 id="deleteimage-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|userId|path|integer(int64)|true|none|
|photoId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="deleteimage-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

## updateImage

<a id="opIdupdateImage"></a>

`PUT /api/images/{userId}/{photoId}`

*Updates an image*

<h3 id="updateimage-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|userId|path|integer(int64)|true|none|
|photoId|path|integer(int64)|true|none|
|isProfilePhoto|query|boolean|false|none|

> Example responses

> 200 Response

<h3 id="updateimage-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

# Schemas

<h2 id="tocS_Resource">Resource</h2>
<!-- backwards compatibility -->
<a id="schemaresource"></a>
<a id="schema_Resource"></a>
<a id="tocSresource"></a>
<a id="tocsresource"></a>

```json
{}

```

### Properties

*None*

