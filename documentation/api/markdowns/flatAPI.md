
# kamppis_server API for flats

- [findAll](#findall)
- [add](#add)
- [findById](#findbyid)
- [update](#update)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="flat-api-default">Default</h1>

## findAll

<a id="opIdfindAll"></a>

`GET /api/flats`

*Returns all flats*

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
|*anonymous*|[[FlatDTO](#schemaflatdto)]|false|none|none|

## add

<a id="opIdadd"></a>

`POST /api/flats`

*Adds a new flat*

> Body parameter

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

<h3 id="add-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[FlatDTO](#schemaflatdto)|true|none|

> Example responses

> 200 Response

<h3 id="add-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FlatDTO](#schemaflatdto)|

## findById

<a id="opIdfindById"></a>

`GET /api/flats/{id}`

*Returns a flat by its id*

<h3 id="findbyid-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="findbyid-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FlatDTO](#schemaflatdto)|

## update

<a id="opIdupdate"></a>

`PUT /api/flats/{id}`

*Updates a flat*

> Body parameter

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

<h3 id="update-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)|true|none|
|body|body|[FlatDTO](#schemaflatdto)|true|none|

> Example responses

> 200 Response

<h3 id="update-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[FlatDTO](#schemaflatdto)|

# Schemas

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

