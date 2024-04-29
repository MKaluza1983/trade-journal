package de.kaluza.trades.utils;

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

    public static double calculatePerformanceInMoney(final double buyPrice, final double sellPrice, final double sellShares) {
        return round(
                sellShares *
                        calculatePerformanceInMoney(
                                buyPrice,
                                sellPrice),
                2);
    }

    static double calculatePerformanceInMoney(final double buyPrice, final double sellPrice) {
        return sellPrice < buyPrice
                ? -1 * (buyPrice - sellPrice)
                : sellPrice - buyPrice;
    }

    public static double calculatePerformanceInPercent(final double buyPrice, final double sellPrice) {
        return MathUtils.round(
                (sellPrice < buyPrice
                        ? -1 * (1 - (sellPrice / buyPrice))
                        : (sellPrice / buyPrice) - 1.0) * 100.0, 2);
    }

}
