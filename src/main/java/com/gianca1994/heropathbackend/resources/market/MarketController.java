package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Market> getAllMarkets() {
        return marketService.getAllMarkets();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void registerItem(@RequestBody MarketRegisterDTO marketRegisterDTO) {
        marketService.registerItem(marketRegisterDTO);
    }
}
