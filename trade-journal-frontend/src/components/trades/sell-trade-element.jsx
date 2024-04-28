import React from 'react';

const SellTradeElement = (props) => {
    const { sellTrade, formatDate } = props;

    return (
        <>
            <tr>
                <td><img src="/assets/img/sell-trade-icon.png" alt="-" /></td>
                <td></td>
                <td>{formatDate(sellTrade.tradedAt)}</td>
                <td>{sellTrade.shares}</td>
                <td>{sellTrade.price + " €"}</td>
                <td></td>
                <td></td>
                <td className={0 <= sellTrade.performanceInMoney ? 'text-success' : 'text-danger'}>{sellTrade.performanceInMoney + " €"}</td>
                <td className={0 <= sellTrade.performanceInPercent ? 'text-success' : 'text-danger'}>{sellTrade.performanceInPercent + " %"}</td>
                <td>{sellTrade.tradingDays}</td>
            </tr>
        </>
    )
}

export default SellTradeElement;