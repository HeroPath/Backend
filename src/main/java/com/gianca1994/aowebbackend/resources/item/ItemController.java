package com.gianca1994.aowebbackend.resources.item;


import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.item.dto.request.EquipUnequipItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.BuySellDTO;
import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.EquipOrUnequipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/buy")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public BuySellDTO buyItem(@RequestHeader(value = "Authorization") String token,
                              @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return BuySellDTO
         */
        return itemS.buyItem(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName().toLowerCase()
        );
    }

    @PostMapping("/sell")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public BuySellDTO sellItem(@RequestHeader(value = "Authorization") String token,
                               @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return BuySellDTO
         */
        return itemS.sellItem(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName().toLowerCase()
        );
    }

    @PostMapping("/equip")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public EquipOrUnequipDTO equipItem(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to equip an item to the user.
         * @param String token
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return EquipOrUnequipDTO
         */
        return itemS.equipItem(
                jwt.getUsernameFromToken(token.substring(7)),
                equipUnequipItemDTO.getId()
        );
    }

    @PostMapping("/unequip")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public EquipOrUnequipDTO unequipItem(@RequestHeader(value = "Authorization") String token,
                                         @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to unequip an item from the user.
         * @param String token
         * @Param EquipUnequipItemDTO equipUnequipItemDTO
         * @return EquipOrUnequipDTO
         */
        return itemS.unequipItem(
                jwt.getUsernameFromToken(token.substring(7)),
                equipUnequipItemDTO.getId()
        );
    }
}
