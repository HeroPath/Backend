import pandas
import requests

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

    response_login = requests.post("http://ao-web.herokuapp.com/api/v1/auth/login", json=data_login)

    response_login_lucho = requests.post("http://ao-web.herokuapp.com/api/v1/auth/login", json=data_login_lucho)

    if response_login_lucho.status_code == 404:
        requests.post("http://ao-web.herokuapp.com/api/v1/auth/register", json=data_register_lucho)

    if response_login.status_code == 404:
        requests.post("http://ao-web.herokuapp.com/api/v1/auth/register", json=data_register)

        response_login = requests.post("http://ao-web.herokuapp.com/api/v1/auth/login", json=data_login)

    headers = {
        "Authorization": "Bearer " + str(response_login.json()['token']),
        "Content-Type": "application/json"
    }

    for i in dataframe.to_dict('records'):
        res = requests.post("http://ao-web.herokuapp.com/api/v1/npcs", headers=headers, json=i)
        print(res.json())


if __name__ == '__main__':
    main()
