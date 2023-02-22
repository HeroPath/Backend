import pandas as pd
from src import api_request


def save_items(url, token):
    print("Registered Items...")
    items = pd.read_excel("data/items.xlsx")
    itemsDataFrame = pd.DataFrame(items, columns=[
        "name", "type", "lvlMin", "classRequired", "price", "strength", "dexterity", "intelligence",
        "vitality", "luck", "shop"
    ])

    for i in itemsDataFrame.to_dict('records'):
        if pd.isna(i["classRequired"]):
            i["classRequired"] = ""

        i["shop"] = True if i["shop"] == 1 else False
        api_request.post(url=url, data=i, token=token)
