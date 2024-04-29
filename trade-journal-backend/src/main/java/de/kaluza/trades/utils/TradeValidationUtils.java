package de.kaluza.trades.utils;

import de.kaluza.trades.domains.BuyTradeEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@NoArgsConstructor
public class TradeValidationUtils {

    public void checkTradeIsNotInFuture(final LocalDate tradedAt) {
        if (LocalDate.now().isBefore(tradedAt)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "TRADE_IS_IN_FUTURE");
        }
    }

    public void validateEnoughShares(final List<BuyTradeEntity> openBuyTrades, final double sellShares) {
        final var availableShares =
                openBuyTrades
                        .stream()
                        .mapToDouble(BuyTradeEntity::getAvailableShares)
                        .sum();
        if (availableShares < sellShares) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "NOT_ENOUGH_AVAILABLE_SHARES_TO_SELL");
        }
    }

}
