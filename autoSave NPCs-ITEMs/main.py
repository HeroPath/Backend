import pandas as pd
import requests

local = "http://localhost:8000"
heroku = "https://ao-web.herokuapp.com"

url_register = "http://192.168.18.43:8000/api/v1/auth/register"
url_login = "http://192.168.18.43:8000/api/v1/auth/login"
url_save_ncs = "http://192.168.18.43:8000/api/v1/npcs"
url_save_items = "http://192.168.18.43:8000/api/v1/items"
url_save_quests = "http://192.168.18.43:8000/api/v1/quests"

data_register_gianca = {
    "username": "gianca",
    "password": "test",
    "email": "gianca9405@gmail.com",
    "className": "mage"
}

data_login_gianca = {
    "username": "gianca",
    "password": "test"
}

data_register_lucho = {
    "username": "lucho",
    "password": "test",
    "email": "lucho@gmail.com",
    "className": "archer"
}

data_login_lucho = {
    "username": "lucho",
    "password": "test"
}

data_register_test = {
    "username": "test",
    "password": "test",
    "email": "test@gmail.com",
    "className": "warrior"
}

data_login_test = {
    "username": "test",
    "password": "test"
}


def main():
    npcs = pd.read_excel("NpcsAo-Web.xlsx")
    items = pd.read_excel("ItemsAo-Web.xlsx")
    quests = pd.read_excel("QuestsAo-Web.xlsx")

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

    questsDataFrame = pd.DataFrame(quests, columns=[
        "name",
        "description",
        "nameNpcKill",
        "npcKillAmountNeeded",
        "userKillAmountNeeded",
        "giveExp",
        "giveGold",
        "giveDiamonds"
    ])

    response_login_lucho = requests.post(url_login, json=data_login_lucho)
    if response_login_lucho.status_code == 404:
        requests.post(url_register, json=data_register_lucho)

    response_login = requests.post(url_login, json=data_login_test)
    if response_login.status_code == 404:
        requests.post(url_register, json=data_register_test)

    response_login = requests.post(url_login, json=data_login_gianca)
    if response_login.status_code == 404:
        requests.post(url_register, json=data_register_gianca)
        response_login = requests.post(url_login, json=data_login_gianca)

    headers = {
        "Authorization": "Bearer " + str(response_login.json()['token']),
        "Content-Type": "application/json"
    }

    print("Saving npcs...")
    for i in npcsDataFrame.to_dict('records'):
        res = requests.post(url_save_ncs, headers=headers, json=i)
        print(res)

    print("Saving items...")
    for i in itemsDataFrame.to_dict('records'):
        if pd.isna(i["classRequired"]):
            i["classRequired"] = ""
        res = requests.post(url_save_items, headers=headers, json=i)
        print(res)

    print("Saving quests...")
    for i in questsDataFrame.to_dict('records'):
        res = requests.post(url_save_quests, headers=headers, json=i)
        print(res)


if __name__ == '__main__':
    main()
