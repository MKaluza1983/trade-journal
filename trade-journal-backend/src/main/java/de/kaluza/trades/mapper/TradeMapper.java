package de.kaluza.trades.mapper;

import de.kaluza.generated.model.BuyTrade;
import de.kaluza.generated.model.SellTrade;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    @Mapping(target = "isTradeClosed", source = "tradeClosed")
    @Mapping(target = "tradingDays", expression = "java(calculateTradingDays(input))")
    BuyTrade toBuyTrade(BuyTradeEntity input);

    default int calculateTradingDays(final BuyTradeEntity input) {
        return input.isTradeClosed()
                ? input.getTradingDays()
                : (int) ChronoUnit.DAYS.between(input.getTradedAt(), LocalDate.now());
    }

    @Mapping(target = "stockSymbol", source = "input.buyTrade.stockSymbol")
    SellTrade toSellTrade(SellTradeEntity input);

    List<SellTrade> toSellTradeList(List<SellTradeEntity> input);

}
