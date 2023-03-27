package com.gianca1994.heropathbackend.resources.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Market m WHERE m.id = :marketId")
    boolean existsById(@Param("marketId") Long marketId);

    @Query("SELECT m FROM Market m WHERE m.user.id = :userId")
    List<Market> findAllByUserId(@Param("userId") Long userId);
}

