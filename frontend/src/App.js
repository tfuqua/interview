import React from 'react'
import 'fontsource-roboto'
import {Button, Container, TextField, Typography} from '@material-ui/core'
import {getQRCode} from './utils/network'

function App() {
  // Store input for our text box.
  const [inputField, setInputField] = React.useState('')
  const [loading, setLoading] = React.useState(false)
  const [qrCodes, setQrCodes] = React.useState([])

  // Get a QR Code from the API endpoint.
  const handleGenerateClick = () => {
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

  // Remove our QR code from our state and also release the ObjectURL used to stop possible memory leaks.
  const handleRemoveClick = (input) => {
    const tempUrl = qrCodes.filter(qr => qr.input === input)
    setQrCodes(qrCodes.filter(qr => qr.input !== input))
    URL.revokeObjectURL(tempUrl)
  }

  return(
    <Container>
      <Typography align='center' gutterBottom variant='h2'>QR Codeler</Typography>
      <TextField align='center' id='outlined-basic' variant='outlined' value={inputField} onChange={(event) => setInputField(event.target.value)}/>
      <Button variant='contained' color='primary' onClick={handleGenerateClick}>Generate Code</Button>
      <Typography align='center' gutterBottom variant='h2'>{(loading) ? 'Loading' : 'Not Loading'}</Typography>
      {qrCodes.map(qr => {
        return(
        <>
          <h3>{qr.input}</h3>
          <img src={qr.image}></img>
          <Button onClick={() => handleRemoveClick(qr.input)}>Remove</Button>
        </>
        )
      })}
    </Container>
  )
}

export default App