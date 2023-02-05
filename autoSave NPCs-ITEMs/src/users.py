import requests
import api_request

users = [
    {
        "username": "gianca",
        "password": "test",
        "email": "gianca@gmail.com",
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

username_login = users[0]['username']
password_login = users[0]['password']


def register(url_register, url_login):
    for i in users:
        credentials = {'username': i['username'], 'password': i['password']}
        response = api_request.post(url=url_login, data=credentials)
        if response.status_code == 404:
            requests.post(url_register, json=i)


def login(url_login):
    response_login = api_request.post(url=url_login, data={'username': username_login, 'password': password_login})
    return response_login.json()['token']
