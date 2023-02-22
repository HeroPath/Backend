package com.gianca1994.aowebbackend.resources.item;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of handling the requests related to the items.
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsById(@NonNull Long id);
    boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i JOIN i.user u WHERE i.id = :itemId AND u.id = :userId")
    boolean existsByIdAndUserId(@Param("itemId") Long itemId, @Param("userId") Long userId);

    @Query("SELECT i.itemLevel FROM Item i WHERE i.id = :id")
    int findItemLevelById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i WHERE i.id = :itemId AND i.type IN :enabledEquip AND i.type != :potionName")
    boolean isItemUpgradeable(@Param("itemId") Long itemId, @Param("enabledEquip") List<String> enabledEquip, @Param("potionName") String potionName);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i WHERE i.id = :itemId AND i.user.id IS NULL")
    boolean isUserIdNull(@Param("itemId") Long itemId);

    @Query("SELECT i FROM Item i WHERE i.classRequired = :classRequired AND i.user IS NULL AND i.shop = true ORDER BY i.lvlMin ASC")
    List<Item> findByClassRequiredAndUserIsNullAndShopIsTrueOrderByLvlMinAsc(@Param("classRequired") String classRequired);

    @Query("SELECT i FROM Item i WHERE i.user.id = :userId AND i.name = :gemName")
    List<Item> findGemByUserId(@Param("userId") Long userId, @Param("gemName") String gemName);
}
