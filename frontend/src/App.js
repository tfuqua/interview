import React, { Component } from 'react';
//import logo from './logo.svg';
 

import BookList from './Book/BookList';

class App extends Component {
  render() {
    return (
      <div className="App">
        <div>
          <BookList/>
        </div>
      </div>
    );
  }
}

export default App;
