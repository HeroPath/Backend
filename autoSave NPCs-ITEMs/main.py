import pandas as pd
import requests

local = "localhost:8000"

url_register = f"http://{local}/api/v1/auth/register"
url_login = f"http://{local}/api/v1/auth/login"
url_save_ncs = f"http://{local}/api/v1/npcs"
url_save_items = f"http://{local}/api/v1/items"
url_save_quests = f"http://{local}/api/v1/quests"

users = [
    {
        "username": "gianca",
        "password": "test",
        "email": "gianca9405@gmail.com",
        "className": "mage"
    },
    {
        "username": "lucho",
        "password": "test",
        "email": "lucho@gmail.com",
        "className": "archer"
    },
    {
        "username": "test",
        "password": "test",
        "email": "test@gmail.com",
        "className": "warrior"
    },
]



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

    for i in users:
        credentials = {'username': i['username'], 'password': i['password']}
        response = requests.post(url_login, json=credentials)
        if response.status_code == 404:
            requests.post(url_register, json=i)

    response_login = requests.post(url_login, json={'username': users[0]['username'], 'password': users[0]['password']})

    headers = {
        "Authorization": "Bearer " + str(response_login.json()['token']),
        "Content-Type": "application/json"
    }

    def save_to_server(url, headers, data):
        res = requests.post(url, headers=headers, json=data)
        print(res)

    print("Saving npcs...")
    for i in npcsDataFrame.to_dict('records'):
        save_to_server(url_save_ncs, headers, i)

    print("Saving items...")
    for i in itemsDataFrame.to_dict('records'):
        if pd.isna(i["classRequired"]):
            i["classRequired"] = ""
        save_to_server(url_save_items, headers, i)

    print("Saving quests...")
    for i in questsDataFrame.to_dict('records'):
        save_to_server(url_save_quests, headers, i)


if __name__ == '__main__':
    main()
