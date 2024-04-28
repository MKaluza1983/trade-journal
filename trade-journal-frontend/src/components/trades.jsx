import React, { } from 'react';
import BuyTradeElement from './trades/buy-trade-element';

const Trades = (props) => {
    const { callGetTrades, tradeStates, setTradeState, handleKeyPress } = props;
    
    return (
        <>
            <h2>Trades</h2>
            <table className="table" style={{ whiteSpace: "nowrap" }}>
                <thead>
                    <tr>
                        <th scope="col">
                            <button className="btn btn-md" onClick={callGetTrades}>
                                <img src="/assets/img/refresh-icon.png" alt="Refresh" width="25" height="25" />
                            </button>
                        </th>
                        <th scope="col"><input placeholder='Aktiensymbol' type="text" pattern="[A-Z]{1,5}" value={tradeStates.searchStockSymbol} onChange={(e) => setTradeState(prevState => ({ ...prevState, searchStockSymbol: e.target.value }))} onKeyPress={handleKeyPress}/></th>
                        <th scope="col">Handelsdatum</th>
                        <th scope="col">Anzahl</th>
                        <th scope="col">Preis</th>
                        <th scope="col">Verfügbare Anzahl</th>
                        <th scope="col">Geschlossener Trade</th>
                        <th scope="col">Performance (&euro;)</th>
                        <th scope="col">Performance (&#037;)</th>
                        <th scope="col">Haltedauer (Tagen)</th>
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