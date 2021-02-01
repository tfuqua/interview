import React, { Component } from 'react';
//import logo from './logo.svg';
import ErrorCatcher from './pages/errors/ErrorCatcher';
import BookPage from './pages/books/BookPage';

class App extends Component {
  render() {
    return (
      <ErrorCatcher>
        <div className="App">
          <div>
            <h1>Books library</h1>
          </div>
          <BookPage />
        </div>
      </ErrorCatcher>
    )
  }
}

export default App;
