import React from 'react';

const SellTradeElement = (props) => {
    const { sellTrade, formatDate } = props;

    return (
        <>
            <tr className="table-light">
                <th scope="row"><img src="/assets/img/sell-trade-icon.png" alt="-" /></th>
                <td>{sellTrade.stockSymbol}</td>
                <td>{formatDate(sellTrade.tradedAt)}</td>
                <td>{sellTrade.shares}</td>
                <td>{sellTrade.price + " €"}</td>
                <td></td>
                <td></td>
                <td></td>
                <td className={0 <= sellTrade.performanceInMoney ? 'text-success' : 'text-danger'}>{sellTrade.performanceInMoney + " €"}</td>
                <td className={0 <= sellTrade.performanceInPercent ? 'text-success' : 'text-danger'}>{sellTrade.performanceInPercent + " %"}</td>
                <td>{sellTrade.tradingDays + (sellTrade.tradingDays === 1 ? ' Tag' : ' Tage')}</td>
            </tr>
        </>
    )
}

export default SellTradeElement;