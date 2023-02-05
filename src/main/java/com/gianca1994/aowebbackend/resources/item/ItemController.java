package com.gianca1994.aowebbackend.resources.item;


import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserEquipOrUnequipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/shop/{aClass}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Item> getClassShop(@PathVariable String aClass) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        try {
            return itemService.getClassShop(aClass);
        } catch (Exception e) {
            throw new Conflict("Error getting the items of the class");
        }
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Item saveItem(@RequestBody ItemDTO newItem) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        try {
            return itemService.saveItem(newItem);
        } catch (Exception e) {
            throw new Conflict("Error saving the item");
        }
    }

    @PostMapping("/buy")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User buyItem(@RequestHeader(value = "Authorization") String token,
                        @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        try {
            return itemService.buyItem(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    nameRequestDTO
            );
        } catch (Exception e) {
            throw new Conflict("Error in buying the item");
        }
    }

    @PostMapping("/sell")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User sellItem(@RequestHeader(value = "Authorization") String token,
                         @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        try {
            return itemService.sellItem(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    nameRequestDTO
            );
        } catch (Exception e) {
            throw new Conflict("Error in selling the item");
        }
    }

    @PostMapping("/equip")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UserEquipOrUnequipDTO equipItem(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to equip an item to the user.
         * @param String token
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User user
         */
        try {
            return itemService.equipItem(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    equipUnequipItemDTO
            );
        } catch (Exception e) {
            throw new Conflict("Error in equipping the item");
        }
    }

    @PostMapping("/unequip")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UserEquipOrUnequipDTO unequipItem(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to unequip an item to the user.
         * @param String token
         * @Param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User user
         */
        try {
            return itemService.unequipItem(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    equipUnequipItemDTO
            );
        } catch (Exception e) {
            throw new Conflict("Error in unequipping the item");
        }
    }
}
