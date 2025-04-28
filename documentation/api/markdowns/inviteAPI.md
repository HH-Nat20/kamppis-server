
# kamppis_server API for invites

- [addRoomProfileInvite](#addroomprofileinvite)
- [joinRoom](#joinroom)

## Notes

- This API is available at `https://kamppis.hellmanstudios.fi`
- The API follows RESTful principles
- All data is exchanged in JSON format
- Most endpoints require authentication via tokens

Base URLs:

* <a href="https://kamppis.hellmanstudios.fi">https://kamppis.hellmanstudios.fi</a>

<h1 id="invite-api-default">Default</h1>

## addRoomProfileInvite

<a id="opIdaddRoomProfileInvite"></a>

`POST /api/invites/generate-invitetoken/{roomProfileId}`

*Adds a new room profile invite*

<h3 id="addroomprofileinvite-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|roomProfileId|path|integer(int64)|true|none|

> Example responses

> 200 Response

<h3 id="addroomprofileinvite-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[InviteResponse](#schemainviteresponse)|

## joinRoom

<a id="opIdjoinRoom"></a>

`PUT /api/invites/join/{inviteToken}`

*Adds a user to a room through the invite*

<h3 id="joinroom-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|inviteToken|path|string|true|none|
|authToken|header|string|true|none|

> Example responses

> 200 Response

<h3 id="joinroom-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[InviteResponse](#schemainviteresponse)|

# Schemas

<h2 id="tocS_InviteResponse">InviteResponse</h2>
<!-- backwards compatibility -->
<a id="schemainviteresponse"></a>
<a id="schema_InviteResponse"></a>
<a id="tocSinviteresponse"></a>
<a id="tocsinviteresponse"></a>

```json
{
  "inviteToken": "string",
  "expiresAt": "2019-08-24T14:15:22Z",
  "message": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|inviteToken|string|true|none|none|
|expiresAt|string(date-time)|true|none|none|
|message|string¦null|false|none|none|

