package de.kaluza.trades.customer.get_trades;

import de.kaluza.generated.model.BuyTrade;
import de.kaluza.generated.model.GetTradesResponse;
import de.kaluza.generated.model.SellTrade;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetTradesMapper {

    @Mapping(target = "stockSymbol", source = "input.buyTrade.stockSymbol")
    SellTrade toSellTrade(SellTradeEntity input);

    List<SellTrade> toSellTradeList(List<SellTradeEntity> input);

    @Mapping(target = "isTradeClosed", source = "tradeClosed")
    BuyTrade toBuyTrade(BuyTradeEntity input);

    default GetTradesResponse out(List<BuyTradeEntity> input) {
        return new GetTradesResponse()
                .content(
                        input
                                .stream()
                                .map(this::toBuyTrade)
                                .toList());
    }

}
