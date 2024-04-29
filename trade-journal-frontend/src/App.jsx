import React, { useEffect, useState } from 'react';
import Navbar from './components/navbar'
import Trade from './components/trade'
import Trades from './components/trades'


const App = () => {

  //TODO / IDEA move to env
  const backendUrl = "http://localhost:8080";

  //TODO / IDEA separate in objects
  const [state, setState] = useState({
    "customerId": "FooBar",
    // BUY / SELL TRADE
    "stockSymbol": "",
    "tradedAt": new Date().toISOString().split('T')[0].replace(/-/g, '-'),
    "shares": "",
    "price": "",
    // GET / SEARCH
    "searchStockSymbol": "",
    "searchIsTradeClosed": "",
    "content": []
  });

  //TODO / IDEA separate file
  const handleErrorCode = (errorCode) => {
    switch (errorCode) {
      case "TRADE_IS_IN_FUTURE":
        return "Trade liegt in der Zukunft";
      case "NOT_ENOUGH_AVAILABLE_SHARES_TO_SELL":
        return "Nicht genügend Anteile zum Verkauf vorhanden";
    }
    return errorCode;
  }

  const handleError = (response) => {
    response.clone().json().then(errorBody => {
      if (errorBody.errorCode !== undefined) {
        alert(handleErrorCode(errorBody.errorCode));
      } else {
        console.log("Ungültige Dateneingabe");
      }
    });
  }

  const resetTradeForm = () => {
    setState(prevState => ({ ...prevState, stockSymbol: "" }));
    setState(prevState => ({ ...prevState, tradedAt: new Date().toISOString().split('T')[0].replace(/-/g, '-') }));
    setState(prevState => ({ ...prevState, shares: "" }));
    setState(prevState => ({ ...prevState, price: "" }));
  }

  const callGetTrades = (searchStockSymbol, searchIsTradeClosed) => {
    const searchStockSymbolParam = searchStockSymbol === "" ? "" : "stockSymbol=" + searchStockSymbol.toUpperCase();
    const searchIsTradeClosedParam = "isTradeClosed=" + searchIsTradeClosed;
    const queryParam = searchStockSymbolParam + "&" + searchIsTradeClosedParam;

    fetch(backendUrl + '/trades?' + queryParam, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'customerId': state.customerId
      },
    })
      .then(response => {
        if (!response.ok) {
          handleError(response);
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('GET /trades - OK:', data);
        setState(prevState => ({ ...prevState, content: data.content }));
      })
      .catch(error => {
        console.error('GET /trades - FAILED:', error);
      });
  }

  const callSaveBuyTrade = () => {
    const data = {
      stockSymbol: state.stockSymbol,
      tradedAt: state.tradedAt,
      shares: parseFloat(state.shares),
      price: parseFloat(state.price),
    };

    fetch(backendUrl + '/trades/buy', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'customerId': state.customerId
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (!response.ok) {
          handleError(response);          
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('POST /trades/buy - OK:', data);
        resetTradeForm();
        callGetTrades(state.searchStockSymbol, state.searchIsTradeClosed);
      })
      .catch(error => {
        console.error('POST /trades/buy - FAILED:', error);
      });
  }

  const callSaveSellTrade = () => {
    const data = {
      stockSymbol: state.stockSymbol,
      tradedAt: state.tradedAt,
      shares: state.shares,
      price: state.price,
    };
    fetch(backendUrl + '/trades/sell', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'customerId': state.customerId
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (!response.ok) {
          handleError(response);
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('POST /trades/sell - OK:', data);
        resetTradeForm();
        callGetTrades(state.searchStockSymbol, state.searchIsTradeClosed);
      })
      .catch(error => {
        console.error('POST /trades/sell - FAILED:', error);
      });
  }

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      callGetTrades(state.searchStockSymbol, state.searchIsTradeClosed);
    }
  };

  useEffect(() => {
    callGetTrades(state.searchStockSymbol, state.searchIsTradeClosed);
    // eslint-disable-next-line
  }, []);

  return (
    <div className="container text-center">
      <Navbar />
      <div>
        <label htmlFor="validateCustomerId" className="form-label">Eingeloggte KundenNr. (<b>für Tests mit Enter ändern</b>)</label>
        <br />
        <input
          id="validateCustomerId"
          required
          type="text"
          className=" text-end"
          value={state.customerId}
          onChange={(e) => { setState(prevState => ({ ...prevState, customerId: e.target.value }))}}
          onKeyPress={handleKeyPress} />
      </div>
      <br />
      <Trade tradeStates={state} setTradeState={setState} callSaveBuyTrade={callSaveBuyTrade} callSaveSellTrade={callSaveSellTrade} />
      <br />
      <Trades tradeStates={state} setTradeState={setState} callGetTrades={callGetTrades}/>
    </div>
  );

}



export default App;