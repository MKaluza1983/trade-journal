import React, { useEffect, useState } from 'react';
import Navbar from './components/navbar'
import Trade from './components/trade'
import Trades from './components/trades'
import './App.css';


const App = () => {

  //TODO / IDEA move to env
  const backendUrl = "http://localhost:8080";

  //TODO / IDEA separate in objects
  const [state, setState] = useState({
    "customerId": "FooBar",
    // BUY / SELL TRADE
    "stockSymbol": "",
    "tradedAt": new Date().toISOString().split('T')[0].replace(/-/g, '-'),
    "shares": 1.25,
    "price": 100.5,
    // GET / SEARCH
    "searchStockSymbol": "",
    "content": []
  });

  const callGetTrades = () => {
    const queryParam = state.searchStockSymbol === "" ? "" : "?stockSymbol=" + state.searchStockSymbol;

    fetch(backendUrl + '/trades' + queryParam, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'customerId': state.customerId
      },
    })
      .then(response => {
        if (!response.ok) {
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
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('POST /trades/buy - OK:', data);
        callGetTrades();
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
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log('POST /trades/sell - OK:', data);
        callGetTrades();
      })
      .catch(error => {
        console.error('POST /trades/sell - FAILED:', error);
      });
  }

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      callGetTrades();
    }
  };

  useEffect(() => {
    callGetTrades();
    // eslint-disable-next-line
  }, []);


  return (
    <div className="App container">
      <Navbar />
      <div className="col-md-12">
        <label htmlFor="validateCustomerId" className="form-label">Eingeloggte KundenNr. (<b>für Tests mit Enter ändern</b>)</label>
        <br />
        <input
          id="validateCustomerId"
          required
          type="text"
          value={state.customerId}
          onChange={(e) => { setState(prevState => ({ ...prevState, customerId: e.target.value }))}}
          onKeyPress={handleKeyPress} />
      </div>
      <br />
      <Trade tradeStates={state} setTradeState={setState} callSaveBuyTrade={callSaveBuyTrade} callSaveSellTrade={callSaveSellTrade} />
      <br />
      <Trades tradeStates={state} setTradeState={setState} callGetTrades={callGetTrades} handleKeyPress={handleKeyPress}/>
    </div>
  );

}



export default App;