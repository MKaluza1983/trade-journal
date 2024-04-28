package de.kaluza.trades.customer.api;

import de.kaluza.generated.model.*;
import de.kaluza.generated.services.api.TradesApi;
import de.kaluza.trades.customer.get_trades.GetTradesService;
import de.kaluza.trades.customer.save_buy_trade.SaveBuyTradeService;
import de.kaluza.trades.customer.save_sell_trade.SaveSellTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class CustomerTradesRestController implements TradesApi {

    private final SaveBuyTradeService saveBuyTradeService;
    private final SaveSellTradeService saveSellTradeService;
    private final GetTradesService getTradesService;

    @Override
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<SaveBuyTradeResponse> saveBuyTrade(
            final String customerId,
            final SaveBuyTradeRequest request) {
        final var responseBody = saveBuyTradeService.execute(customerId, request);
        return new ResponseEntity<>(responseBody, CREATED);
    }

    @Override
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<SaveSellTradeResponse> saveSellTrade(
            final String customerId,
            final SaveSellTradeRequest request) {
        final var responseBody = saveSellTradeService.execute(customerId, request);
        return new ResponseEntity<>(responseBody, CREATED);
    }

    @Override
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<GetTradesResponse> getTrades(
            final String customerId,
            final String stockSymbol,
            final Boolean isTradeClosed) {
        final var responseBody = getTradesService.execute(customerId, stockSymbol, isTradeClosed);
        return ResponseEntity.ok(responseBody);
    }

}