
# kamppis_server API for feedbacks

- [findAll](#findall)
- [add](#add)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="feedback-api-default">Default</h1>

## findAll

<a id="opIdfindAll"></a>

`GET /api/feedback`

*Returns all given user feedbacks*

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
|*anonymous*|[[FeedbackDTO](#schemafeedbackdto)]|false|none|none|

## add

<a id="opIdadd"></a>

`POST /api/feedback`

*Adds a new user feedback*

> Body parameter

```json
{
  "feedback": "string",
  "id": 0
}
```

<h3 id="add-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[FeedbackDTO](#schemafeedbackdto)|true|none|

> Example responses

> 200 Response

<h3 id="add-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FeedbackDTO](#schemafeedbackdto)|

# Schemas

<h2 id="tocS_FeedbackDTO">FeedbackDTO</h2>
<!-- backwards compatibility -->
<a id="schemafeedbackdto"></a>
<a id="schema_FeedbackDTO"></a>
<a id="tocSfeedbackdto"></a>
<a id="tocsfeedbackdto"></a>

```json
{
  "feedback": "string",
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|feedback|string|true|none|none|
|id|integer(int64)¦null|false|none|none|

