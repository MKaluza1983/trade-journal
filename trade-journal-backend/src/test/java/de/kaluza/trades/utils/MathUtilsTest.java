package de.kaluza.trades.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void calculatePerformanceInMoney_buyAndSellAreEqual_zero() {
        // given
        final var price = 100;

        // expect
        assertThat(MathUtils.calculatePerformanceInMoney(price, price)).isZero();
    }

    @Test
    void calculatePerformanceInMoney_buy125Sell300_175() {
        assertThat(MathUtils.calculatePerformanceInMoney(125, 300)).isEqualTo(175);
    }

    @Test
    void calculatePerformanceInMoney_buy300Sell125_Minus175() {
        assertThat(MathUtils.calculatePerformanceInMoney(300, 125)).isEqualTo(-175);
    }
}