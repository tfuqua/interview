import React, { useState, useEffect} from 'react'
import './index.scss'

export default function HousesData() {
  const [Houses, fetchHouses] = useState([])
  const getData = () => {
    fetch('https://wizard-world-api.herokuapp.com/Houses')
      .then((res) => res.json())
      .then((res) => {
        fetchHouses(res)
      })
  }

  useEffect(() => {
    getData()
  
  }, [])

  


  return (
 <>
    {Houses.map((item, i) => (
     <div className='wrapper'>  
      <div className="card">
    <p key={i} className="element">Element: {item.element}</p>
    <h1>{item.name}</h1> 
    <hr />
    <p className="animal"> Animal: {item.animal} </p>
    <p className="founder">Founder: {item.founder}</p>
    </div>
    </div> 
              ))}
              </>
)
}
