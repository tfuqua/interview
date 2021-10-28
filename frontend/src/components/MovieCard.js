import React from "react"
import styled from "styled-components"

const MovieCardStyle = styled.div`
  :hover {
    transform: scale(1.05);
  }
  .poster {
    width: 100%;
    height: auto;
  }
  width: 350px;
  height: auto;
  padding: 0 1rem 1rem;
  margin: 1rem;
  border: solid;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  transition: all 0.2s ease;
  .supplemental {
    display: flex;
    justify-content: space-between;
    padding: 0 1rem;
    .genre {
      font-style: italic;
    }
  }
`

const MovieCard = ({ movie }) => {
  console.log(movie)
  return (
    <MovieCardStyle>
      <h2>{movie.title}</h2>
      <img className="poster" src={movie.imageUrl} />
      <p>{movie.blurb}</p>
      <div className="supplemental">
        <p className="genre">{movie.genre}</p>
        <p>{movie.rating}</p>
      </div>
    </MovieCardStyle>
  )
}

export default MovieCard
