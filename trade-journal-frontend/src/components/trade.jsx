import React from 'react';

const Trade = (props) => {

  const { callSaveBuyTrade, callSaveSellTrade, tradeStates, setTradeState } = props;

  return (
    <>
      <h2>Buy / Sell Trade</h2>
      <form className="container g-3 needs-validation" novalidate>
        <div className="row">
          <div className="col-1">
          </div>
          <div className="col">
            <label htmlFor="validateStockSymbol" className="form-label">Aktiensymbol:</label>
            <input id="validateStockSymbol" type="text" pattern="[A-Z]{1,5}" value={tradeStates.stockSymbol} onChange={(e) => setTradeState(prevState => ({ ...prevState, stockSymbol: e.target.value }))} required />
          </div>
          <div className="col">
            <label htmlFor="validateTradedAt" className="form-label">Handelsdatum:</label>
            <input id="validateTradedAt" type="date" value={tradeStates.tradedAt} onChange={(e) => setTradeState(prevState => ({ ...prevState, tradedAt: e.target.value }))} required />
          </div>
          <div className="col">
            <label htmlFor="validateShares" className="form-label">Anzahl:</label>
            <input id="validateShares" type="number" value={tradeStates.shares} onChange={(e) => setTradeState(prevState => ({ ...prevState, shares: e.target.value }))} required />
          </div>
          <div className="col">
            <label htmlFor="validatePrice" className="form-label">Preis (in €):</label>
            <input id="validatePrice" type="number" value={tradeStates.price} onChange={(e) => setTradeState(prevState => ({ ...prevState, price: e.target.value }))} required />
          </div>
          <div className="col">
            <button type="button" className="w-100 btn btn-primary center" onClick={callSaveBuyTrade}>Kauf</button>
            <button type="button" className="w-100 btn btn-primary center" onClick={callSaveSellTrade}>Verkauf</button>
          </div>
        </div>
      </form>
    </>
  )
}

export default Trade;