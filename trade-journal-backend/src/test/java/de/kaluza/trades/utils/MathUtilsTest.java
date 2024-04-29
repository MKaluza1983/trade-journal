package de.kaluza.trades.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void round_happyPath() {
        assertThat(MathUtils.round(1.234567, 1)).isEqualTo(1.2);
        assertThat(MathUtils.round(1.234567, 2)).isEqualTo(1.23);
        assertThat(MathUtils.round(1.234567, 3)).isEqualTo(1.235);
        assertThat(MathUtils.round(1.234567, 4)).isEqualTo(1.2346);
        assertThat(MathUtils.round(1.234567, 5)).isEqualTo(1.23457);
        assertThat(MathUtils.round(1.234567, 6)).isEqualTo(1.234567);
        assertThat(MathUtils.round(1.234567, 7)).isEqualTo(1.234567);
    }

    @Test
    void round_up() {
        assertThat(MathUtils.round(1.235, 2)).isEqualTo(1.24);
    }

    @Test
    void calculatePerformanceInMoney_buy1_25_Sell2_shares3_5_plus281() {
        assertThat(MathUtils.calculatePerformanceInMoney(1.25, 2.0, 3.75)).isEqualTo(2.81);
    }

    @Test
    void calculatePerformanceInMoney_buy2_sell1_25_shares3_5minus281() {
        assertThat(MathUtils.calculatePerformanceInMoney(2.0, 1.25, 3.75)).isEqualTo(-2.81);
    }

    @Test
    void calculatePerformanceInMoney() {
        assertThat(MathUtils.calculatePerformanceInMoney(2.5, 4.0)).isEqualTo(1.5);
    }

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
    void calculatePerformanceInMoney_buy300Sell125_minus175() {
        assertThat(MathUtils.calculatePerformanceInMoney(300, 125)).isEqualTo(-175);
    }

    @Test
    void calculatePerformanceInPercent_equalPrice_zero() {
        assertThat(MathUtils.calculatePerformanceInPercent(100, 100)).isZero();
    }

    @Test
    void calculatePerformanceInPercent_buy100Sell200_100Percent() {
        assertThat(MathUtils.calculatePerformanceInPercent(100, 200)).isEqualTo(100);
    }

    @Test
    void calculatePerformanceInPercent_buy200Sell100_minus50Percent() {
        assertThat(MathUtils.calculatePerformanceInPercent(200, 100)).isEqualTo(-50);
    }

}