package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserService;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {
    private final int MAX_ITEMS_INVENTORY = 24;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;

    public List<Item> getClassShop(String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        List<Item> items = itemRepository.findAll();
        items.removeIf(item -> !item.getClassRequired().equals(aClass));
        items.sort(Comparator.comparing(Item::getLvlMin));
        return items;
    }

    public Item saveItem(ItemDTO newItem) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        Item item = itemRepository.findByName(newItem.getName().toLowerCase());
        if (item != null) throw new Conflict("Item already exists");

        if (Objects.equals(newItem.getName(), "")) throw new BadRequest("Name cannot be empty");
        if (Objects.equals(newItem.getType(), "")) throw new BadRequest("Type cannot be empty");
        if (newItem.getLvlMin() <= 0) throw new BadRequest("LvlMin cannot be less than 0");
        if (newItem.getPrice() < 0) throw new BadRequest("Price cannot be less than 0");

        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 ||
                newItem.getVitality() < 0 || newItem.getLuck() < 0
        ) throw new BadRequest("Stats cannot be less than 0");


        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings", "potion");
        if (!itemsEnabledToEquip.contains(newItem.getType()))
            throw new Conflict("You can't equip more than one " + newItem.getType() + " item");

        if (Objects.equals(newItem.getClassRequired(), "")) newItem.setClassRequired("none");

        return itemRepository.save(new Item(
                newItem.getName().toLowerCase(),
                newItem.getType(),
                newItem.getLvlMin(),
                newItem.getClassRequired(),
                newItem.getPrice(),
                1,
                newItem.getStrength(),
                newItem.getDexterity(),
                newItem.getIntelligence(),
                newItem.getVitality(),
                newItem.getLuck())
        );
    }

    public void buyItem(String token, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param String name
         * @return none
         */
        User user = userService.getProfile(token);
        if (user == null) throw new NotFound("User not found");

        Item itemBuy = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound("Item not found");

        if (user.getGold() < itemBuy.getPrice()) throw new Conflict("You don't have enough gold");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY && !user.getInventory().getItems().contains(itemBuy))
            throw new Conflict("Inventory is full");

        user.setGold(user.getGold() - itemBuy.getPrice());

        if (user.getInventory().getItems().contains(itemBuy)) itemBuy.setAmount(itemBuy.getAmount() + 1);
        else user.getInventory().getItems().add(itemBuy);
        userService.updateUser(user);
    }

    public void sellItem(String token, NameRequestDTO nameRequestDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String token
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        User user = userService.getProfile(token);
        if (user == null) throw new NotFound("User not found");

        Item itemBuy = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound("Item not found");

        if (!user.getInventory().getItems().contains(itemBuy)) throw new NotFound("Item not found in inventory");
        user.setGold(user.getGold() + (itemBuy.getPrice() / 2));

        if (itemBuy.getAmount() > 1) itemBuy.setAmount(itemBuy.getAmount() - 1);
        else user.getInventory().getItems().remove(itemBuy);
        userService.updateUser(user);
    }

    public User equipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userService.getProfile(token);
        if (user == null) throw new NotFound("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound("Item not found"));
        if (!user.getInventory().getItems().contains(item)) throw new NotFound("Item not found in inventory");
        if (!Objects.equals(user.getAClass().getName(), item.getClassRequired()) && !Objects.equals(item.getClassRequired(), "none"))
            throw new Conflict("The item does not correspond to your class");

        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!itemsEnabledToEquip.contains(itemEquipedOld.getType()))
                throw new Conflict("You can't equip more than one " + itemEquipedOld.getType() + " item");
            if (Objects.equals(itemEquipedOld.getType(), item.getType()))
                throw new Conflict("You can't equip two items of the same type");
        }

        if (user.getLevel() < item.getLvlMin())
            throw new Conflict("You can't equip an item that requires level " + item.getLvlMin());

        if (Objects.equals(item.getType(), "potion")) {
            user.setHp(user.getMaxHp());
        } else {
            user.getEquipment().getItems().add(item);
            user.swapItemToEquipmentOrInventory(item, true);
        }

        if (item.getAmount() > 1) item.setAmount(item.getAmount() - 1);
        else user.getInventory().getItems().remove(item);

        userService.updateUser(user);
        return user;
    }

    public User unequipItem(String token, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userService.getProfile(token);
        if (user == null) throw new NotFound("User not found");

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound("Item not found"));
        if (!user.getEquipment().getItems().contains(item)) throw new NotFound("Item not found in equipment");
        if (user.getInventory().getItems().size() >= MAX_ITEMS_INVENTORY) throw new Conflict("Inventory is full");

        user.getEquipment().getItems().remove(item);
        if (user.getInventory().getItems().contains(item)) item.setAmount(item.getAmount() + 1);

        user.getInventory().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userService.updateUser(user);
        return user;
    }
}
