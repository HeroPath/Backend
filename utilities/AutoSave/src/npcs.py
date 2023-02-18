import pandas as pd
from src import api_request


def save_npcs(url, token):
    print("Registered Npcs...")
    npcs = pd.read_excel("data/npcs.xlsx")
    npcsDataFrame = pd.DataFrame(npcs, columns=[
        "name", "level", "giveMaxExp", "giveMinExp", "giveMaxGold", "giveMinGold",
        "hp", "maxHp", "minDmg", "maxDmg", "defense", "zone"
    ])
    for i in npcsDataFrame.to_dict('records'):
        api_request.post(url=url, data=i, token=token)
