package de.kaluza.trades.customer.save_buy_trade;

import de.kaluza.trades.domains.BuyTradeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveBuyTradeRepository extends JpaRepository<BuyTradeEntity, UUID> {
}