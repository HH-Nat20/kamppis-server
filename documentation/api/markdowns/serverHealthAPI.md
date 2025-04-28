
# kamppis_server API for server health

- [serverHealth](#serverhealth)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="server-health-api-default">Default</h1>

## serverHealth

<a id="opIdserverHealth"></a>

`GET /api/health`

*Checks server's health status*

> Example responses

> 200 Response

<h3 id="serverhealth-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|

