import React, {Component} from 'react';
import axios from 'axios';
import Breweries from "./Breweries";

class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            records: []
        };

        axios.get('https://api.openbrewerydb.org/breweries')
            .then((response) => {
                this.setState({
                    ...this.state,
                    records: response.data
                });
            })
            .catch((error) => {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                {/*This is just to show passing props to child component. We could have render the table right here.*/}
                <Breweries breweries={this.state.records}/>
            </div>
        )
    }
}

export default Home;