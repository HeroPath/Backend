import pandas as pd
import api_request


def save_npcs(url, token):
    npcs = pd.read_excel("data/npcs.xlsx")
    npcsDataFrame = pd.DataFrame(npcs, columns=[
        "name", "level", "giveMaxExp", "giveMinExp", "giveMaxGold", "giveMinGold",
        "hp", "maxHp", "minDmg", "maxDmg", "defense", "zone"
    ])
    for i in npcsDataFrame.to_dict('records'):
        api_request.post(url=url, data=i, token=token)

    print("Registered Npcs...")
