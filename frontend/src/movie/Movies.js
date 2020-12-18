import "./Movies.css"
import React, {Component} from 'react';
import RemotingService from "../core/RemotingService";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {GenreEnum} from "./GenreEnum";
import {DelayInput} from "react-delay-input/lib/Component";

export default class Movies extends Component {

    constructor(props) {
        super(props);

        this.state = {
            movies: []
        }
    }

    componentDidMount() {
        this.retrieveMovies();
    }

    retrieveMovies = () => {
        const {title, year} = this.state;

        RemotingService.get(
            'movies/filter',
            {title, year},
            (movies) => this.setState({movies}));
    }

    genreTemplate = (movie) => {
        return movie.genres.map(genre => GenreEnum[genre]).join(', ');
    }

    render() {
        const {movies, title, year} = this.state;

        return (
            <div className="movies-page">
                <h1 className="text-align-center">Movies</h1>
                <div className="movies-filter">
                    <DelayInput id="title-input"
                                className="text-align-right filter-input"
                                placeholder={"Search With Title"}
                                delayTimeout={300}
                                value={title}
                                onChange={(event) =>
                                    this.setState({title: event.target.value}, this.retrieveMovies)}
                    />
                    <DelayInput id="year-input"
                                className="text-align-left filter-input"
                                placeholder={"Search With Year"}
                                delayTimeout={300}
                                value={year}
                                onChange={(event) =>
                                    this.setState({year: event.target.value}, this.retrieveMovies)}
                    />
                </div>
                <DataTable className="movies-table"
                           emptyMessage={"No items found"}
                           value={movies}
                           rows={20}
                           paginator={true}
                           paginatorPosition="bottom">
                    <Column header="Title" field="title" sortable/>
                    <Column header="Year" field="year" sortable className="text-align-center"/>
                    <Column header="Genres" body={this.genreTemplate}/>
                </DataTable>
            </div>
        );
    }
}
