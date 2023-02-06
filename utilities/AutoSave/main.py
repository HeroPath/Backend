from multiprocessing import Process
from src.users import register, login
from src.items import save_items
from src.npcs import save_npcs
from src.quests import save_quests

address_port = "localhost:8000"

url_register = f"http://{address_port}/api/v1/auth/register"
url_login = f"http://{address_port}/api/v1/auth/login"
url_ncs = f"http://{address_port}/api/v1/npcs"
url_items = f"http://{address_port}/api/v1/items"
url_quests = f"http://{address_port}/api/v1/quests"


def main():
    register(url_register, url_login)
    token = login(url_login)

    processes = [
        Process(target=save_npcs, args=(url_ncs, token)),
        Process(target=save_items, args=(url_items, token)),
        Process(target=save_quests, args=(url_quests, token))
    ]

    for process in processes:
        process.start()


if __name__ == '__main__':
    main()
