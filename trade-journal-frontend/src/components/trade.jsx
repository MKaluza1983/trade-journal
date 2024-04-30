import React from 'react';

const Trade = (props) => {

  const { callSaveBuyTrade, callSaveSellTrade, tradeStates, setTradeState } = props;

  return (
    <>
      <h2>Kauf / Verkauf</h2>
      <form className="needs-validation" novalidate action="javascript:void(0);">
        <div className="row">
          <div className="col-2 offset-2">
            <label htmlFor="validateStockSymbol" className="form-label">Aktiensymbol:</label>
            <input id="validateStockSymbol" className="form-control text-end" type="text" pattern="^[A-Z0-9]{1,6}$" value={tradeStates.stockSymbol} onChange={(e) => setTradeState(prevState => ({ ...prevState, stockSymbol: e.target.value.toUpperCase() }))} required />
          </div>
          <div className="col-2">
            <label htmlFor="validateTradedAt" className="form-label">Handelsdatum:</label>
            <input id="validateTradedAt" className="form-control text-end" type="date" value={tradeStates.tradedAt} onChange={(e) => setTradeState(prevState => ({ ...prevState, tradedAt: e.target.value }))} required />
          </div>
          <div className="col-2">
            <label htmlFor="validateShares" className="form-label">Anzahl:</label>
            <input id="validateShares" className="form-control text-end" type="number" value={tradeStates.shares} onChange={(e) => setTradeState(prevState => ({ ...prevState, shares: e.target.value }))} required />
          </div>
          <div className="col-2">
            <label htmlFor="validatePrice" className="form-label">Preis:</label>
            <div className="input-group">
              <input id="validatePrice" className="form-control text-end" type="number" value={tradeStates.price} onChange={(e) => setTradeState(prevState => ({ ...prevState, price: e.target.value }))} required />
              <div className="input-group-prepend">
                <div className="input-group-text">€</div>
              </div>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
          <div className="col-2 offset-4">
            <button className="w-100 btn btn-primary center" onClick={callSaveBuyTrade}>Kauf</button>
          </div>
          <div className="col-2">
            <button className="w-100 btn btn-primary center" onClick={callSaveSellTrade}>Verkauf</button>
          </div>
        </div>
      </form>
    </>
  )
}

export default Trade;