
# kamppis_server API for database health

- [dbHealth](#dbhealth)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="database-health-api-default">Default</h1>

## dbHealth

<a id="opIddbHealth"></a>

`GET /api/db-health`

*Checks database connection health*

> Example responses

> 200 Response

<h3 id="dbhealth-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

