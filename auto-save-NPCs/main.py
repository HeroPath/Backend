import pandas
import requests

test = pandas.read_excel("NpcsAo-Web.xlsx")

dataframe = pandas.DataFrame(test, columns=[
    "name",
    "level",
    "giveMaxExp",
    "giveMinExp",
    "giveMaxGold",
    "giveMinGold",
    "hp",
    "maxHp",
    "minDmg",
    "maxDmg",
    "defense",
    "zone"
])

data_register = {
    "username": "gianca",
    "password": "test",
    "email": "gianca9405@gmail.com",
    "classId": 1
}

data_login = {
    "username": "gianca",
    "password": "test"
}

response_login = requests.post("https://ao-web.herokuapp.com/api/v1/auth/login", json=data_login)

if response_login.status_code == 404:
    response_register = requests.post("https://ao-web.herokuapp.com/api/v1/auth/register", json=data_register)

    response_login = requests.post("https://ao-web.herokuapp.com/api/v1/auth/login", json=data_login)

headers = {
    "Authorization": "Bearer " + str(response_login.json()['token']),
    "Content-Type": "application/json"
}

for i in dataframe.to_dict('records'):
    res = requests.post("http://ao-web.herokuapp.com/api/v1/npcs", headers=headers, json=i)
    print(res.json())
