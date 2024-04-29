package de.kaluza.trades.customer.get_trades;

import de.kaluza.generated.model.GetTradesResponse;
import de.kaluza.generated.model.SellTrade;
import de.kaluza.trades.domains.BuyTradeEntity;
import de.kaluza.trades.domains.SellTradeEntity;
import de.kaluza.trades.mapper.TradeMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GetTradesMapper extends TradeMapper {

    @Mapping(target = "stockSymbol", source = "input.buyTrade.stockSymbol")
    SellTrade toSellTrade(SellTradeEntity input);

    default GetTradesResponse out(List<BuyTradeEntity> input) {
        return new GetTradesResponse()
                .content(
                        input
                                .stream()
                                .map(this::toBuyTrade)
                                .toList());
    }

}
