import React, { Component } from 'react';
import Home from "./components/Home";
//import logo from './logo.svg';
 

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h2>Welcome to the interview app!</h2>
        </header>
        {/*This is my Home Page component*/}
        <Home/>
      </div>
    );
  }
}

export default App;
