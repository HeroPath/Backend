# aoweb-backend

<h1>Documentation</h1>

## User
| Function | Url | Method | Data |
| --- | --- | --- | --- |
| Register | https://ao-web.herokuapp.com/api/v1/auth/register | POST | {"username": "", "password": "", "email": "", classId: ""} |
| Login | https://ao-web.herokuapp.com/api/v1/auth/login | POST | {"username": "", "password": ""} |
| Profile | https://ao-web.herokuapp.com/api/v1/users/profile | GET | header = { BearerToken } |
| Ranking | https://ao-web.herokuapp.com/api/v1/users/ranking | GET | header = { BearerToken } |
| AddSkillsPoints | https://ao-web.herokuapp.com/api/v1/users/add-skill-points | POST | header = { BearerToken } and {"skillPointName": "", "amount": 0} |
| EquipItem | https://ao-web.herokuapp.com/api/v1/users/equip-item | POST | header = { BearerToken } and {"id": "idItem"} |
| UnequipItem | https://ao-web.herokuapp.com/api/v1/users/unequip-item | POST | header = { BearerToken } and {"id": "idItem"} |
| Buy Items | https://ao-web.herokuapp.com/api/v1/users/buyitem | POST | header = { BearerToken } and {"name": "nameItem"}|
| Sell Items | https://ao-web.herokuapp.com/api/v1/users/sellitem | POST | header = { BearerToken } and {"name": "nameItem"}|

## Classes
| Function | Url | Method | Data |
| --- | --- | --- | --- |
| ClasesList | https://ao-web.herokuapp.com/api/v1/classes | GET | No requiere nada. |

## Npcs
| Function | Url | Method | Data |
| --- | --- | --- | --- |
| NpcList | https://ao-web.herokuapp.com/api/v1/npcs | GET | header = { BearerToken } |
| NpcByName | https://ao-web.herokuapp.com/api/v1/npcs/{name} | GET | header = { BearerToken } |
| NpcByZone | https://ao-web.herokuapp.com/api/v1/npcs/zone/{zone} | GET | header = { BearerToken } |
| SaveNpc | https://ao-web.herokuapp.com/api/v1/npcs | POST | header = { BearerToken } and {"name":"", "level": 0, "giveMaxExp":0, "giveMinExp":0, "giveMaxGold":0, "giveMinGold":0, "hp":0, "maxHp":0, "minDmg":0, "maxDmg":0, "defense":0, "zone": ""} |

## Combat
| Function | Url | Method | Data |
| --- | --- | --- | --- |
| PvpUserVsUser | https://ao-web.herokuapp.com/api/v1/users/attack-user | POST | header = { BearerToken } and { "name": "" } |
| PvpUserVsNPC | https://ao-web.herokuapp.com/api/v1/users/attack-npc | POST | header = { BearerToken } and { "name": "" } |
