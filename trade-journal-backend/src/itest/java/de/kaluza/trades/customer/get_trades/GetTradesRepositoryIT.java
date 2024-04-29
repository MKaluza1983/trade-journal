package de.kaluza.trades.customer.get_trades;

import de.kaluza.trades.domains.BuyTradeEntity;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class GetTradesRepositoryIT {

    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String ANOTHER_CUSTOMER_ID = "ANOTHER_" + CUSTOMER_ID;

    private static final String STOCK_SYMBOL = "STOCK";
    private static final String ANOTHER_STOCK_SYMBOL = "ANOTHER_" + STOCK_SYMBOL;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GetTradesRepository sut;

    @Test
    void getTrades_noRequiredParameterSet_found2BuyTradeEntity() {
        // given
        final var expectedBuyTradeEntity1 =
                testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, STOCK_SYMBOL, false));
        final var expectedBuyTradeEntity2 =
                testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));
        testEntityManager.persistAndFlush(createBuyTradeEntity(ANOTHER_CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));

        // when
        final var result =
                sut.getTrades(
                        CUSTOMER_ID,
                        null,
                        null);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(List.of(expectedBuyTradeEntity2, expectedBuyTradeEntity1));
    }

    @Test
    void getTrades_byStockSymbol_found1BuyTradeEntity() {
        // given
        final var expectedBuyTradeEntity1 = testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, STOCK_SYMBOL, false));
        testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));
        testEntityManager.persistAndFlush(createBuyTradeEntity(ANOTHER_CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));

        // when
        final var result =
                sut.getTrades(
                        CUSTOMER_ID,
                        STOCK_SYMBOL,
                        false);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(List.of(expectedBuyTradeEntity1));
    }

    @Test
    void getTrades_allParameterSet_found1BuyTradeEntity() {
        // given
        final var expectedBuyTradeEntity1 = testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, STOCK_SYMBOL, false));
        testEntityManager.persistAndFlush(createBuyTradeEntity(CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));
        testEntityManager.persistAndFlush(createBuyTradeEntity(ANOTHER_CUSTOMER_ID, ANOTHER_STOCK_SYMBOL, true));

        // when
        final var result =
                sut.getTrades(
                        CUSTOMER_ID,
                        STOCK_SYMBOL,
                        false);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(List.of(expectedBuyTradeEntity1));
    }

    private static BuyTradeEntity createBuyTradeEntity(final String customerId, final String stockSymbol, final boolean isTradeClosed) {
        final var result = new BuyTradeEntity();
        result.setCustomerId(customerId);
        result.setStockSymbol(stockSymbol);
        result.setTradedAt(LocalDate.now());
        result.setShares(1.0);
        result.setPrice(250);
        result.setInvestedCapital(250);
        result.setAvailableShares(1.0);
        result.setPerformanceInMoney(0.0);
        result.setPerformanceInPercent(0.0);
        result.setTradeClosed(isTradeClosed);
        result.setTradingDays(null);
        return result;
    }

}
