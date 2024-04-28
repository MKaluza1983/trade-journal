package de.kaluza.trades.customer.save_buy_trade;

import de.kaluza.generated.model.SaveBuyTradeRequest;
import de.kaluza.generated.model.SaveBuyTradeResponse;
import de.kaluza.trades.utils.TradeValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveBuyTradeService {

    private final SaveBuyTradeRepository saveBuyTradeRepository;
    private final SaveBuyTradeMapper saveBuyTradeMapper;
    private final TradeValidationUtils tradeValidationUtils;

    @Transactional
    public SaveBuyTradeResponse execute(final String customerId, final SaveBuyTradeRequest request) {
        // Request -> Entity
        final var buyTrade = saveBuyTradeMapper.in(customerId, request);

        // Business logic
        tradeValidationUtils.checkTradeIsNotInFuture(request.getTradedAt());
        final var persistedBuyTrade = saveBuyTradeRepository.save(buyTrade);

        // Request <- Entity
        return saveBuyTradeMapper.out(persistedBuyTrade);
    }


}
