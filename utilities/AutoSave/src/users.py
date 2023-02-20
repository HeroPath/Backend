import requests
from src import api_request

user = {
    "username": "admin",
    "password": "test",
    "email": "admin@gmail.com",
    "className": "mage"
}


def register(url_register, url_login):
    print("Registered users")

    credentials = {'username': user['username'], 'password': user['password']}
    response = api_request.post(url=url_login, data=credentials)
    if response.status_code == 404:
        requests.post(url_register, json=user)


def login(url_login):
    response_login = api_request.post(
        url=url_login,
        data={'username': user['username'], 'password': user['password']}
    )
    return response_login.json()['token']
