package de.kaluza.trades.customer.save_sell_trade;

import de.kaluza.generated.model.SaveSellTradeRequest;
import de.kaluza.generated.model.SaveSellTradeResponse;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import de.kaluza.trades.utils.TradeValidationUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SaveSellTradeService {

    private final SaveSellTradeRepository saveSellTradeRepository;
    private final SaveSellTradeMapper saveSellTradeMapper;
    private final TradeValidationUtils tradeValidationUtils;

    @Transactional
    public SaveSellTradeResponse execute(
            final String customerId,
            final SaveSellTradeRequest request) {
        // TODO USER LOCK!!!
        // Preconditions
        tradeValidationUtils.checkTradeIsNotInFuture(request.getTradedAt());

        // Request -> Entity
        final var openBuyTrades =
                saveSellTradeRepository
                        .getOpenBuyTrades(
                                customerId,
                                request.getStockSymbol(),
                                request.getTradedAt());

        // Business logic
        tradeValidationUtils.validateEnoughShares(openBuyTrades, request.getShares());
        final var sellTrades = reduceShares(openBuyTrades, request);
        final var persistedSellTrades = saveSellTradeRepository.saveAll(sellTrades);

        // Request <- Entity
        return saveSellTradeMapper.out(persistedSellTrades);
    }

    private List<SellTradeEntity> reduceShares(final List<BuyTradeEntity> openBuyTrades, final SaveSellTradeRequest request) {
        var sharesToSell = request.getShares();
        final var result = new ArrayList<SellTradeEntity>();
        for (final var openBuyTrade : openBuyTrades) {
            final var openBuyTradeSoldShares = Math.min(openBuyTrade.getAvailableShares(), sharesToSell);
            final var sellTrade = saveSellTradeMapper.in(openBuyTrade, request, openBuyTradeSoldShares);
            result.add(sellTrade);
            sharesToSell -= openBuyTradeSoldShares;
            if (sharesToSell == 0.0) {
                return result;
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "NOT_ENOUGH_AVAILABLE_SHARES_TO_SELL");
    }

}
