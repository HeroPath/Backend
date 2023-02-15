import pandas as pd
from src import api_request


def save_quests(url, token):
    print("Registered Quests...")
    quests = pd.read_excel("data/quests.xlsx")
    questsDataFrame = pd.DataFrame(quests, columns=[
        "name", "nameNpcKill", "npcAmountNeed", "userAmountNeed", "giveExp",
        "giveGold", "giveDiamonds"
    ])

    for i in questsDataFrame.to_dict('records'):
        api_request.post(url=url, data=i, token=token)
