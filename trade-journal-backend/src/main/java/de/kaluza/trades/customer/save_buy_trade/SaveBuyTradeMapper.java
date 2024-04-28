package de.kaluza.trades.customer.save_buy_trade;

import de.kaluza.generated.model.SaveBuyTradeRequest;
import de.kaluza.generated.model.SaveBuyTradeResponse;
import de.kaluza.trades.domains.BuyTradeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaveBuyTradeMapper {

    default BuyTradeEntity in(final String customerId, final SaveBuyTradeRequest input) {
        final var result = in(input);
        result.setCustomerId(customerId);
        return result;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "performanceInMoney", ignore = true)
    @Mapping(target = "performanceInPercent", ignore = true)
    @Mapping(target = "sellTrades", ignore = true)
    @Mapping(target = "availableShares", source = "input.shares")
    @Mapping(target = "tradeClosed", constant = "false")
    @Mapping(target = "tradingDays", ignore = true)
    BuyTradeEntity in(SaveBuyTradeRequest input);

    SaveBuyTradeResponse out(BuyTradeEntity input);
}
