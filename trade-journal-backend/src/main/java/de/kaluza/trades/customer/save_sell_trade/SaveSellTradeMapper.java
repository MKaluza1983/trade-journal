package de.kaluza.trades.customer.save_sell_trade;

import de.kaluza.generated.model.SaveSellTradeRequest;
import de.kaluza.generated.model.SaveSellTradeResponse;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import de.kaluza.trades.mapper.TradeMapper;
import de.kaluza.trades.utils.MathUtils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Mapper(componentModel = "spring")
public interface SaveSellTradeMapper extends TradeMapper {

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
        result.setTradingDays((int) ChronoUnit.DAYS.between(openBuyTrade.getTradedAt(), request.getTradedAt()));

        // Add relation
        result.setBuyTrade(openBuyTrade);
        openBuyTrade.getSellTrades().add(result);

        // Reduce available shares / invested capital
        final var availableShares = openBuyTrade.getAvailableShares() - soldShares;
        openBuyTrade.setAvailableShares(availableShares);
        openBuyTrade.setInvestedCapital(availableShares * openBuyTrade.getPrice());
        final var hasNoAvailableShares = availableShares == 0.0;

        // Calculate performance
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

        if (hasNoAvailableShares) {
            openBuyTrade.setTradeClosed(true);
            final var lastSellTradedAt =
                    openBuyTrade
                            .getSellTrades()
                            .stream()
                            .map(SellTradeEntity::getTradedAt)
                            .max(LocalDate::compareTo)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "NO_LAST_TRADE"));

            openBuyTrade.setTradingDays((int) ChronoUnit.DAYS.between(openBuyTrade.getTradedAt(), lastSellTradedAt));
        }
        return result;
    }

    default SaveSellTradeResponse out(final List<SellTradeEntity> input) {
        return new SaveSellTradeResponse()
                .sellTrades(
                        input
                                .stream()
                                .map(this::toSellTrade)
                                .toList());
    }
}
