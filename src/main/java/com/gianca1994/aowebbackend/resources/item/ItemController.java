package com.gianca1994.aowebbackend.resources.item;


import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.BuySellDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.EquipOrUnequipDTO;
import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of handling the requests related to the items.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping("/shop/{aClass}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Item> getClassShop(@PathVariable String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        return itemS.getClassShop(aClass);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveItem(@RequestBody ItemDTO newItem) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param ItemDTO newItem
         * @return void
         */
        itemS.saveItem(newItem);
    }

    @GetMapping("/buy/{itemBuyId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public BuySellDTO buyItem(@RequestHeader(value = "Authorization") String token,
                              @PathVariable Long itemBuyId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param Long itemBuyId
         * @return BuySellDTO
         */
        return itemS.buyItem(
                jwt.getIdFromToken(token.substring(7)),
                itemBuyId
        );
    }

    @GetMapping("/sell/{itemSellId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public BuySellDTO sellItem(@RequestHeader(value = "Authorization") String token,
                               @PathVariable Long itemSellId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String token
         * @param Long itemSellId
         * @return BuySellDTO
         */
        return itemS.sellItem(
                jwt.getIdFromToken(token.substring(7)),
                itemSellId
        );
    }

    @GetMapping("/equip/{itemEquipId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public EquipOrUnequipDTO equipItem(@RequestHeader(value = "Authorization") String token,
                                       @PathVariable Long itemEquipId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to equip an item to the user.
         * @param String token
         * @param Long itemEquipId
         * @return EquipOrUnequipDTO
         */
        return itemS.equipItem(
                jwt.getIdFromToken(token.substring(7)),
                itemEquipId
        );
    }

    @GetMapping("/unequip/{itemUnequipId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public EquipOrUnequipDTO unequipItem(@RequestHeader(value = "Authorization") String token,
                                         @PathVariable Long itemUnequipId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to unequip an item from the user.
         * @param String token
         * @Param Long itemUnequipId
         * @return EquipOrUnequipDTO
         */
        return itemS.unequipItem(
                jwt.getIdFromToken(token.substring(7)),
                itemUnequipId
        );
    }

    @GetMapping("/upgrade/{itemUpgradeId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void upgradeItem(@RequestHeader(value = "Authorization") String token,
                            @PathVariable Long itemUpgradeId) throws Conflict {
        /**
         *
         */
        itemS.upgradeItem(
                jwt.getIdFromToken(token.substring(7)),
                itemUpgradeId
        );
    }
}
