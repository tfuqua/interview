import React from "react";
import styled from "styled-components";

const MovieCardStyle= styled.div`
  width: 350px;
  padding: 0 1rem 1rem;
  margin: 1rem;
  border:solid;
  display: flex;
  flex-direction:column;
  justify-content:space-between;
  .supplemental{
    display: flex;
    justify-content:space-between;
    padding: 0 1rem;
    .genre{
      font-style: italic;
    }
  }

`

const MovieCard = ({movie}) => {
  console.log(movie)
  return(
    <MovieCardStyle>
      <h2>{movie.title}</h2>
      <img src={movie.imageUrl}/>
      <p>{movie.blurb}</p>
      <div className="supplemental">
        <p className="genre">{movie.genre}</p>
        <p>{movie.rating}</p>
      </div>
    </MovieCardStyle>
  )
}

export default MovieCard