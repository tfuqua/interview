import React, { Component } from 'react';
import { BreedList } from './components/breed-list/breed-list.component';
import { Loading } from './components/loading/loading.component';
import './App.css';

class App extends Component {

  constructor(){
    super();
    this.state = {
      breeds : [],
      loading : true
    }
  }

  componentDidMount(){
    this.setState({loading : true});
    fetch('https://dog.ceo/api/breeds/list')
      .then(response => response.json())
      .then(data => this.setState({breeds : data['message'], loading : false}));
  }

  render() {
    const breeds = this.state.breeds;
    return (
      <div className="App">
        {this.state.loading ? <Loading /> : null}
        <BreedList breeds={breeds}/>
      </div>
    );
  }
}

export default App;
