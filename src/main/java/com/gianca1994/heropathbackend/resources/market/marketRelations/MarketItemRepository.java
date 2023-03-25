package com.gianca1994.heropathbackend.resources.market.marketRelations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {
}
