import requests
from src import api_request
from multiprocessing import Process

users = [
    {
        "username": "admin",
        "password": "test",
        "email": "admin@gmail.com",
        "className": "mage"
    },
    {
        "username": "test",
        "password": "test",
        "email": "test@gmail.com",
        "className": "warrior"
    },
]


def register_worker(url_register, url_login, i):
    credentials = {'username': i['username'], 'password': i['password']}
    response = api_request.post(url=url_login, data=credentials)
    if response.status_code == 404:
        requests.post(url_register, json=i)


def register(url_register, url_login):
    print("Registered users")
    processes = []
    for i in users:
        p = Process(target=register_worker, args=(url_register, url_login, i))
        processes.append(p)
        p.start()

    for process in processes:
        process.join()


def login(url_login):
    response_login = api_request.post(
        url=url_login,
        data={'username': users[0]['username'], 'password': users[0]['password']}
    )
    return response_login.json()['token']
