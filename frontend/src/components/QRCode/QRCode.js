import React from 'react'
import PropTypes from 'prop-types'
import {Button} from '@material-ui/core'

function QRCode({qrCode, remove}){
    return(
        <>
          <h3>{qrCode.input}</h3>
          <img src={qrCode.image}></img>
          <Button onClick={() => remove(qrCode.input)}>Remove</Button>
        </>
    )
}

QRCode.propTypes = {
    qrCode: PropTypes.shape({input: PropTypes.string, image: PropTypes.string}).isRequired,
    remove: PropTypes.func.isRequired,
}

export default QRCode