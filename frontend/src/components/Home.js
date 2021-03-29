import React from "react";
import {Jumbotron, Button} from 'react-bootstrap';
import externalResourceConfig from '../configs/externalResources.json'

function Home() {
    return <Jumbotron>
    <h1>Hello!</h1>
    <p>
      This is a simple application, which is getting data from <a href={externalResourceConfig.HOLIDAYS_SERVICE_URL}>here</a>.
    </p>
    <p>
      In order to view the data, please navigate to the Holidays screen.
    </p>
  </Jumbotron>
}

export default Home;