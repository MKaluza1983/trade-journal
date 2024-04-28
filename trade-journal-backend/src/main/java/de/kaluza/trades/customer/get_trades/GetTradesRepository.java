package de.kaluza.trades.customer.get_trades;

import de.kaluza.trades.domains.BuyTradeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetTradesRepository extends JpaRepository<BuyTradeEntity, UUID> {

    @Query(
            """
                        SELECT bo
                        FROM BuyTradeEntity bo
                        WHERE bo.customerId = :customerId
                        AND (:stockSymbol IS NULL OR bo.stockSymbol ILIKE CONCAT('%', :stockSymbol, '%'))
                        AND (:isTradeClosed IS NULL OR bo.isTradeClosed = :isTradeClosed)
                        ORDER BY bo.tradedAt DESC, bo.stockSymbol, bo.createdAt DESC
                    """
    )
    List<BuyTradeEntity> getTrades(
            @Param("customerId") String customerId,
            @Param("stockSymbol") String stockSymbol,
            @Param("isTradeClosed") Boolean isTradeClosed);

}
