import requests


def post(url="", data=None, token=""):
    if data is None:
        data = {}

    headers = {
        "Authorization": "Bearer " + str(token),
        "Content-Type": "application/json"
    }

    if token != "":
        response = requests.post(url=url, json=data, headers=headers)
        return response
    else:
        response = requests.post(url=url, json=data, headers={"Content-Type": "application/json"})
        return response
