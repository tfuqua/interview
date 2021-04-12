import React from 'react'
import 'fontsource-roboto'
import {Button, Container, TextField, Typography} from '@material-ui/core'
import {getQRCode} from './utils/network'

function App() {
  // Store input for our text box.
  const [inputField, setInputField] = React.useState('')
  const [loading, setLoading] = React.useState(false)
  const [qrCodes, setQrCodes] = React.useState([])

  // TODO: Handle button click, check for value and get QR Code from API endpoint.
  const handleClick = () => {
    setLoading(true) 
    getQRCode(inputField)
      .then(qrCode => {
        console.log(qrCode)
        setQrCodes([...qrCodes, qrCode])
      })
      .then(() => {
        setLoading(false)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  return(
    <Container>
      <Typography align='center' gutterBottom variant='h2'>QR Codeler</Typography>
      <TextField align='center' id='outlined-basic' variant='outlined' value={inputField} onChange={(event) => setInputField(event.target.value)}/>
      <Button variant='contained' color='primary' onClick={handleClick}>Generate Code</Button>
      <Typography align='center' gutterBottom variant='h2'>{(loading) ? 'Loading' : 'Not Loading'}</Typography>
      {qrCodes.map(qr => {
        return(
        <>
          <h3>{qr.input}</h3>
          <img src={qr.image}></img>
        </>
        )
      })}
    </Container>
  )
}

export default App