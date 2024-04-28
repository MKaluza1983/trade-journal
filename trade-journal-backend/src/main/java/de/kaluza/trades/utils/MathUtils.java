package de.kaluza.trades.utils;

import de.kaluza.generated.model.SaveSellTradeRequest;
import de.kaluza.trades.domains.BuyTradeEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        final var bd = BigDecimal.valueOf(value);
        return bd.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public static double calculatePerformanceInMoney(final BuyTradeEntity openBuyTrade, final SaveSellTradeRequest request) {
        return round(
                request.getShares() *
                        calculatePerformanceInMoney(
                                openBuyTrade.getPrice(),
                                request.getPrice()),
                2);
    }

    static double calculatePerformanceInMoney(final double buyPrice, final double sellPrice) {
        return sellPrice < buyPrice
                ? -1 * (buyPrice - sellPrice)
                : sellPrice - buyPrice;
    }

    public static double calculatePerformanceInPercent(final BuyTradeEntity openBuyTrade, final SaveSellTradeRequest request) {
        return MathUtils.round(
                (request.getPrice() < openBuyTrade.getPrice()
                        ? -1 * (1 - (request.getPrice() / openBuyTrade.getPrice()))
                        : (request.getPrice() / openBuyTrade.getPrice()) - 1.0) * 100.0, 2);
    }

}
