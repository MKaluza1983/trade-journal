package de.kaluza.trades.customer.save_sell_trade;

import de.kaluza.generated.model.SaveSellTradeRequest;
import de.kaluza.generated.model.SaveSellTradeResponse;
import de.kaluza.generated.model.SellTrade;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import de.kaluza.trades.utils.MathUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Mapper(componentModel = "spring")
public interface SaveSellTradeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "performanceInMoney", ignore = true)
    @Mapping(target = "performanceInPercent", ignore = true)
    @Mapping(target = "tradingDays", ignore = true)
    @Mapping(target = "buyTrade", ignore = true)
    SellTradeEntity in(SaveSellTradeRequest input);

    default SellTradeEntity in(final BuyTradeEntity openBuyTrade, final SaveSellTradeRequest request, final double soldShares) {
        final var result = in(request);
        result.setShares(soldShares);
        result.setPerformanceInMoney(MathUtils.calculatePerformanceInMoney(openBuyTrade, request));
        result.setPerformanceInPercent(MathUtils.calculatePerformanceInPercent(openBuyTrade, request));
        result.setTradingDays(ChronoUnit.DAYS.between(openBuyTrade.getTradedAt(), request.getTradedAt()));

        // Add relation
        result.setBuyTrade(openBuyTrade);
        openBuyTrade.getSellTrades().add(result);

        // Reduce available shares
        openBuyTrade.setAvailableShares(openBuyTrade.getAvailableShares() - soldShares);
        final var hasNoAvailableShares = openBuyTrade.getAvailableShares() == 0.0;
        if (hasNoAvailableShares) {
            openBuyTrade.setTradeClosed(true);
            final var lastSellTradedAt =
                    openBuyTrade
                            .getSellTrades()
                            .stream()
                            .map(SellTradeEntity::getTradedAt)
                            .max(LocalDate::compareTo)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "NO_LAST_TRADE"));

            final var buyTradePerformanceInMoney =
                    openBuyTrade
                            .getSellTrades()
                            .stream()
                            .mapToDouble(SellTradeEntity::getPerformanceInMoney)
                            .sum();
            openBuyTrade.setPerformanceInMoney(buyTradePerformanceInMoney);

            final var buyTradePerformanceInPercent =
                    openBuyTrade
                            .getSellTrades()
                            .stream()
                            .mapToDouble(
                                    sellTrade -> sellTrade.getPerformanceInPercent() * sellTrade.getShares() / openBuyTrade.getShares())
                            .sum();
            openBuyTrade.setPerformanceInPercent(MathUtils.round(buyTradePerformanceInPercent, 2));

            openBuyTrade.setTradingDays(ChronoUnit.DAYS.between(openBuyTrade.getTradedAt(), lastSellTradedAt));
        }
        return result;
    }

    @Mapping(target = "stockSymbol", source = "input.buyTrade.stockSymbol")
    SellTrade toSellTrade(SellTradeEntity input);

    default SaveSellTradeResponse out(final List<SellTradeEntity> input) {
        return new SaveSellTradeResponse()
                .sellTrades(
                        input
                                .stream()
                                .map(this::toSellTrade)
                                .toList());
    }
}
