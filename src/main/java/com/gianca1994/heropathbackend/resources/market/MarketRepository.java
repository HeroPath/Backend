package com.gianca1994.heropathbackend.resources.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    @Query("SELECT m FROM Market m WHERE m.userId != :userId")
    List<Market> findAllExceptUserId(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Market m WHERE m.id = :marketId")
    boolean existsById(@Param("marketId") Long marketId);

    List<Market> findAllByUserId(Long userId);

    @Query("SELECT COUNT(m) FROM Market m WHERE m.userId = :userId")
    int countItemsPublishedByUserId(@Param("userId") Long userId);

}

