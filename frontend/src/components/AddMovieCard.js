import React, { useState } from "react"
import styled from "styled-components"

const AddMovieCardStyle = styled.div`
  width: 350px;
  height: auto;
  padding: 0 1rem 1rem;
  margin: 1rem;
  border: dashed;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s ease;
  p {
    font-size: 5rem;
  }
  :hover {
    transform: scale(1.05);
  }
`

const AddMovieCard = () => {
  const [clicked, setClicked] = useState(false)

  return (
    <AddMovieCardStyle onClick={() => setClicked(!clicked)}>
      {!clicked ? <p>+</p> : <p>-</p>}
    </AddMovieCardStyle>
  )
}

export default AddMovieCard
