package de.kaluza.trades.customer.save_sell_trade;

import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveSellTradeRepository extends JpaRepository<SellTradeEntity, UUID> {

    @Query(
            """
                    SELECT bo
                    FROM BuyTradeEntity bo
                    WHERE bo.customerId = :customerId
                    AND bo.stockSymbol = :stockSymbol
                    AND bo.isTradeClosed = false
                    AND bo.tradedAt <= :sellTradedAt
                    ORDER BY bo.tradedAt
                    """
    )
    List<BuyTradeEntity> getOpenBuyTrades(
            @Param("customerId") String customerId,
            @Param("stockSymbol") String stockSymbol,
            @Param("sellTradedAt") LocalDate sellTradedAt);

}
