import React from 'react'; 
import Header from './components/Header'
import Home from './components/Home'
import Holidays from './components/Holidays'

import { Routes, Route } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css'
import Container from 'react-bootstrap/Container';

function App() {
    return (
      <Container>
        <Header/>
        <Routes>
          <Route path='/' element={<Home/>} />
          <Route path='/holidays' element={<Holidays/>}/>
        </Routes>
      </Container>
    );
  }

export default App;
