import pandas as pd
import api_request


def save_quests(url, token):
    quests = pd.read_excel("data/quests.xlsx")
    questsDataFrame = pd.DataFrame(quests, columns=[
        "name", "nameNpcKill", "npcKillAmountNeeded", "userKillAmountNeeded", "giveExp",
        "giveGold", "giveDiamonds"
    ])

    for i in questsDataFrame.to_dict('records'):
        api_request.post(url=url, data=i, token=token)

    print("Registered Quests...")
