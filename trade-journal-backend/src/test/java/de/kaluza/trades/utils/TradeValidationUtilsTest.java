package de.kaluza.trades.utils;

import de.kaluza.trades.domains.BuyTradeEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TradeValidationUtilsTest {

    private final TradeValidationUtils sut = new TradeValidationUtils();

    private static BuyTradeEntity createWithShares(final double availableShares) {
        final var result = new BuyTradeEntity();
        result.setAvailableShares(availableShares);
        return result;
    }

    @Test
    void checkTradeIsNotInFuture_yesterday_noException() {
        assertDoesNotThrow(() -> sut.checkTradeIsNotInFuture(LocalDate.now().minusDays(1)));
    }

    @Test
    void checkTradeIsNotInFuture_today_noException() {
        assertDoesNotThrow(() -> sut.checkTradeIsNotInFuture(LocalDate.now()));
    }

    @Test
    void checkTradeIsNotInFuture_tomorrow_responseStatusException() {
        // given
        final var tomorrow = LocalDate.now().plusDays(1);

        // expect
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> sut.checkTradeIsNotInFuture(tomorrow))
                .satisfies(exception -> {
                    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(exception.getReason()).isEqualTo("TRADE_IS_IN_FUTURE");
                });
    }

    @Test
    void validateEnoughShares_enoughShares_noException() {
        // given
        final var openBuyTrades = new ArrayList<BuyTradeEntity>();
        openBuyTrades.add(createWithShares(1));
        openBuyTrades.add(createWithShares(2));


        // expect
        assertDoesNotThrow(() -> sut.validateEnoughShares(openBuyTrades, 3.0));
    }

    @Test
    void validateEnoughShares_notEnoughShares_responseStatusException() {
        // given
        final var openBuyTrades = new ArrayList<BuyTradeEntity>();
        openBuyTrades.add(createWithShares(1));
        openBuyTrades.add(createWithShares(2));

        // expect
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> sut.validateEnoughShares(openBuyTrades, 3.1))
                .satisfies(exception -> {
                    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(exception.getReason()).isEqualTo("NOT_ENOUGH_AVAILABLE_SHARES_TO_SELL");
                });
    }

}