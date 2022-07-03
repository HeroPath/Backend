import pandas as pd
import requests

local = "http://localhost:8000/"
heroku = "https://ao-web.herokuapp.com/"

url_register = "http://ao-web.herokuapp.com/api/v1/auth/register"
url_login = "http://ao-web.herokuapp.com/api/v1/auth/login"
url_save_ncs = "http://ao-web.herokuapp.com/api/v1/npcs"
url_save_items = "http://ao-web.herokuapp.com/api/v1/items"

data_register_gianca = {
    "username": "gianca",
    "password": "test",
    "email": "gianca9405@gmail.com",
    "classId": 1
}

data_login_gianca = {
    "username": "gianca",
    "password": "test"
}

data_register_lucho = {
    "username": "lucho",
    "password": "test",
    "email": "lucho@gmail.com",
    "classId": 1
}

data_login_lucho = {
    "username": "lucho",
    "password": "test"
}


def main():
    npcs = pd.read_excel("NpcsAo-Web.xlsx")
    items = pd.read_excel("ItemsAo-Web.xlsx")
    npcsDataFrame = pd.DataFrame(npcs, columns=[
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
    itemsDataFrame = pd.DataFrame(items, columns=[
        "name",
        "type",
        "lvlMin",
        "classRequired",
        "price",
        "strength",
        "dexterity",
        "intelligence",
        "vitality",
        "luck"
    ])

    response_login = requests.post(url_login, json=data_login_gianca)
    if response_login.status_code == 404:
        requests.post(url_register, json=data_register_gianca)
        response_login = requests.post(url_login, json=data_login_gianca)

    response_login_lucho = requests.post(url_login, json=data_login_lucho)
    if response_login_lucho.status_code == 404:
        requests.post(url_register, json=data_register_lucho)

    headers = {
        "Authorization": "Bearer " + str(response_login.json()['token']),
        "Content-Type": "application/json"
    }

    print("Saving npcs...")
    for i in npcsDataFrame.to_dict('records'):
        res = requests.post(url_save_ncs, headers=headers, json=i)
        print(res.json())

    print("Saving items...")
    for i in itemsDataFrame.to_dict('records'):
        if pd.isna(i["classRequired"]):
            i["classRequired"] = ""
        res = requests.post(url_save_items, headers=headers, json=i)
        print(res.json())


if __name__ == '__main__':
    main()
