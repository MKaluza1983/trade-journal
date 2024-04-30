import React, { } from 'react';
import BuyTradeElement from './trades/buy-trade-element';
import './trades.css';


const Trades = (props) => {
    const { callGetTrades, tradeStates, setTradeState } = props;

    return (
        <>
            <h2>Trades</h2>
            <table className="Trades table align-middle" style={{ whiteSpace: "nowrap"}}>
                <thead className="align-middle">
                    <tr>
                        <th scope="col">
                            <button className="btn btn-md" onClick={() => {callGetTrades(tradeStates.searchStockSymbol, tradeStates.searchIsTradeClosed)}}>
                                <img src="/assets/img/refresh-icon.png" alt="Refresh" width="25" height="25" />
                            </button>
                        </th>
                        <th scope="col">
                            <input
                                placeholder='Aktiensymbol'
                                type="text"
                                pattern="^[A-Z0-9]{1,6}$"
                                className="form-control text-end"
                                value={tradeStates.searchStockSymbol}
                                onChange={(e) => {setTradeState(prevState => ({ ...prevState, searchStockSymbol: e.target.value.toUpperCase() })); callGetTrades(e.target.value, tradeStates.searchIsTradeClosed);}} />
                        </th>
                        <th scope="col">Handelsdatum</th>
                        <th scope="col">Anzahl</th>
                        <th scope="col">Preis</th>
                        <th scope="col">Gehaltene Anzahl</th>
                        <th scope="col">Investiertes Kapital</th>
                        <th scope="col">
                            <select className="form-select" aria-label="Default select example" onChange={(e) => {setTradeState(prevState => ({ ...prevState, searchIsTradeClosed: e.target.value })); callGetTrades(tradeStates.searchStockSymbol, e.target.value);}}>
                                <option value="">Alle Trades</option>
                                <option value="false">Offene</option>
                                <option value="true">Geschlossene</option>
                            </select>
                        </th>
                        <th scope="col">+ / - (&euro;)</th>
                        <th scope="col">+ / - (&#037;)</th>
                        <th scope="col">Haltedauer</th>
                    </tr>
                </thead>
                <tbody>
                    {tradeStates.content.map(buyTrade => <BuyTradeElement key={buyTrade.id} buyTrade={buyTrade} />)}
                </tbody>
            </table>
        </>
    );
}

export default Trades;