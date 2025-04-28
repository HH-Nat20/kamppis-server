
# kamppis_server API for swipes

- [findAll](#findall)
- [swipe](#swipe)

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="swipes-api-default">Default</h1>

## findAll

<a id="opIdfindAll"></a>

`GET /api/swipes`

*Returns all swipes*

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
|*anonymous*|[[SwipeDTO](#schemaswipedto)]|false|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## swipe

<a id="opIdswipe"></a>

`POST /api/swipes`

*Adds a new swipe*

> Body parameter

```json
{
  "swipingProfileId": 0,
  "swipedProfileId": 0,
  "isRightSwipe": true
}
```

<h3 id="swipe-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[SwipeRequest](#schemaswiperequest)|true|none|

> Example responses

> 200 Response

<h3 id="swipe-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[SwipeResponse](#schemaswiperesponse)|

<aside class="success">
This operation does not require authentication
</aside>

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

<h2 id="tocS_SwipeDTO">SwipeDTO</h2>
<!-- backwards compatibility -->
<a id="schemaswipedto"></a>
<a id="schema_SwipeDTO"></a>
<a id="tocSswipedto"></a>
<a id="tocsswipedto"></a>

```json
{
  "id": 0,
  "swipingProfile": {},
  "swipedProfile": {},
  "isRightSwipe": true,
  "isMatch": true,
  "createdAt": "2019-08-24T14:15:22Z"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)¦null|false|none|none|

<h2 id="tocS_SwipeRequest">SwipeRequest</h2>
<!-- backwards compatibility -->
<a id="schemaswiperequest"></a>
<a id="schema_SwipeRequest"></a>
<a id="tocSswiperequest"></a>
<a id="tocsswiperequest"></a>

```json
{
  "swipingProfileId": 0,
  "swipedProfileId": 0,
  "isRightSwipe": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|swipingProfileId|integer(int64)|true|none|none|
|swipedProfileId|integer(int64)|true|none|none|
|isRightSwipe|boolean|true|none|none|

<h2 id="tocS_SwipeResponse">SwipeResponse</h2>
<!-- backwards compatibility -->
<a id="schemaswiperesponse"></a>
<a id="schema_SwipeResponse"></a>
<a id="tocSswiperesponse"></a>
<a id="tocsswiperesponse"></a>

```json
{
  "swipeId": 0,
  "swipingProfile": {},
  "swipedProfile": {},
  "isRightSwipe": true,
  "isMatch": true
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|swipeId|integer(int64)|true|none|none|

