import React, { useEffect, useState } from "react";
import axios from "axios";
import MovieCard from "./MovieCard";
import styled from "styled-components";


const MovieListStyle = styled.div`
  .title{
    margin: 1rem;
    text-align:center;
  }
  .cards{
  display:flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content:center;
  }
`

const MovieList = () => {
  const [loading, setLoading] = useState(true);
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/movies').then(res => {
      setMovies(res.data);
      setLoading(false);
    }).catch(e => console.log(e));
  },[])

  return(
    <MovieListStyle>
      <h1 className="title">Tekmetric Interview: Movie List</h1>
      <div className="cards">
      {!loading && movies.map(movie => 
        <MovieCard
          key={movie.title}
          movie={movie}
        />
      )}
      </div>
    </MovieListStyle>
  )
}

export default MovieList