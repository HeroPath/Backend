import pandas as pd
import api_request


def save_items(url, token):
    items = pd.read_excel("data/items.xlsx")
    itemsDataFrame = pd.DataFrame(items, columns=[
        "name", "type", "lvlMin", "classRequired", "price", "strength", "dexterity", "intelligence",
        "vitality", "luck"
    ])

    for i in itemsDataFrame.to_dict('records'):
        if pd.isna(i["classRequired"]):
            i["classRequired"] = ""
        api_request.post(url=url, data=i, token=token)

    print("Registered Items...")
