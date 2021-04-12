import React from 'react'
import 'fontsource-roboto'
import {Button, Grid, TextField, Typography} from '@material-ui/core'
import {getQRCode} from './utils/network'
import QRCode from './components/QRCode/QRCode'
import {makeStyles} from '@material-ui/core/styles'

const useStyles = makeStyles({
  inputField: {
    height: 50,
    width: 500,
    marginBottom: 35,  
  },
  inputButton: {
    height: 50, 
    width: 500,
    marginBottom: 20, 
  },
})

function App() {
  // State 
  const [inputField, setInputField] = React.useState('')
  const [inputError, setInputError] = React.useState('') 
  const [loading, setLoading] = React.useState(false)
  const [qrCodes, setQrCodes] = React.useState([])
  
  const classes = useStyles()

  // Get a QR Code from the API endpoint.
  const handleGenerateClick = () => {
    // Set error helper in the case of duplicates.
    if (qrCodes.filter(qr => qr.input === inputField).length > 0) {
      setInputError('Duplicate Entry.')
      return
    }

    // Otherwise grab a new image from the API endpoint.
    setLoading(true) 
    getQRCode(inputField)
      .then(qrCode => {
        setQrCodes([qrCode, ...qrCodes])
      })
      .then(() => {
        setLoading(false)
        setInputField('')
        setInputError('')
      })
      .catch((error) => {
        setInputError('Error Communicating with API: Please Try Again.')
        setLoading(false)
      })
  }

  // Remove our QR code from our state and also release the ObjectURL used. This helps to prevent possible memory leaks.
  const handleRemoveClick = (input) => {
    const tempUrl = qrCodes.filter(qr => qr.input === input)
    setQrCodes(qrCodes.filter(qr => qr.input !== input))
    URL.revokeObjectURL(tempUrl)
  }

  return (
    <Grid
      container
      direction='column'
      justify='flex-start'
      alignItems='center'>
      
      <Grid item>
        <Typography gutterBottom variant='h2'>
          QR Code Generator
        </Typography>
      </Grid>
    
      <Grid item>
        <TextField
          align='center'
          autoFocus
          autoComplete='off'
          className={classes.inputField}
          disabled={loading}
          error={(inputError !== '')}
          helperText={inputError}
          id='outlined-basic'
          label='Enter a String or a URL'
          variant='outlined'
          value={inputField}
          onChange={(event) => setInputField(event.target.value)}
        />
      </Grid>
   
      <Grid item>
        <Button
          variant='contained'
          className={classes.inputButton}
          color='primary'
          disabled={inputField === '' || loading}
          onClick={handleGenerateClick}>
          {(loading) ? 'Loading...' : 'Generate Code'}
        </Button>
      </Grid>
  
      {qrCodes.length > 0 &&  
      <Grid 
        item
        container
        direction='column'
        justify='center'
        alignItems='center'>
        {qrCodes.map(qr => <QRCode key={qr.input} qrCode={qr} remove={handleRemoveClick}/>)}
      </Grid>}
    
    </Grid>
  )
}

export default App