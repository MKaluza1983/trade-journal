import React from 'react';
import SellTradeElement from './sell-trade-element';

const BuyTradeElement = (props) => {

    const { buyTrade } = props;

    //TODO / IDEA Extract to utilty
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();
        return `${day}.${month}.${year}`;
    };

    return (
        <>
            <tr>
                <th scope="row"><img src="/assets/img/buy-trade-icon.png" alt="+" /></th>
                <td>{buyTrade.stockSymbol}</td>
                <td>{formatDate(buyTrade.tradedAt)}</td>
                <td>{buyTrade.shares}</td>
                <td>{buyTrade.price} &euro;</td>
                <td>{buyTrade.availableShares}</td>
                <td>{buyTrade.investedCapital + " €"}</td>
                <td>{buyTrade.isTradeClosed ? "Geschlossen" : "Offen"}</td>
                <td className={0 <= buyTrade.performanceInMoney ? 'text-success' : 'text-danger'}>{buyTrade.performanceInMoney == null ? "" : buyTrade.performanceInMoney + " €"}</td>
                <td className={0 <= buyTrade.performanceInPercent ? 'text-success' : 'text-danger'}>{buyTrade.performanceInPercent == null ? "" : buyTrade.performanceInPercent + " %"}</td>
                <td>{buyTrade.tradingDays + (buyTrade.tradingDays == 1 ? ' Tag' : ' Tage')}</td>
            </tr>
            {buyTrade.sellTrades.map(sellTrade => <SellTradeElement key={sellTrade.id} buyTradePrice={buyTrade.price} sellTrade={sellTrade} formatDate={formatDate} />)}
        </>
    )
}

export default BuyTradeElement;