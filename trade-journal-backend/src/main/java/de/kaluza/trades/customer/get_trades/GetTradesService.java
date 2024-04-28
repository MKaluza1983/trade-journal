package de.kaluza.trades.customer.get_trades;

import de.kaluza.generated.model.GetTradesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTradesService {

    private final GetTradesRepository getTradesRepository;
    private final GetTradesMapper getTradesMapper;

    @Transactional(readOnly = true)
    public GetTradesResponse execute(final String customerId, final String stockSymbol, final Boolean isTradeClosed) {
        // Business logic
        final var persistedOrders =
                getTradesRepository
                        .getTrades(
                                customerId,
                                stockSymbol,
                                isTradeClosed);

        // Request <- Entity
        return getTradesMapper.out(persistedOrders);
    }

}
