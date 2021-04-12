import React from 'react'
import PropTypes from 'prop-types'
import {Button, Card, CardActions, CardContent, CardMedia, Grid, Link, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'

const useStyles = makeStyles({
    root: {
        width: 500, 
        minHeight: 350, 
        maxHeight: 350,
    },
    media: {
        marginLeft: 'auto',
        marginRight: 'auto',
        marginTop: 20,
        marginBottom: 20,
        width: 150,
        height: 150,
    },
    button: {
        margin: 'auto',
        width: 450,
    },
  })
  
function QRCode({qrCode, remove}){
    const classes = useStyles()

    // Check to see if the provided input is an HTML Link.
    // Note, if given more time I would expand this to be less easy to fool.
    const isLink = qrCode.input.includes('http', 0)

    return (
        <Grid item className={classes.root}>
            <Card>
                <CardMedia
                    className={classes.media}
                    image={qrCode.image}
                    title={qrCode.input}
                />
                <CardContent>
                    <Typography align='center' variant='h5' gutterBottom>
                        {(isLink) ? <Link href={qrCode.input}>{qrCode.input}</Link> : qrCode.input}
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button
                        className={classes.button}
                        color='secondary'
                        onClick={() => remove(qrCode.input)}
                        size='medium'>
                        Remove
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    )
}

QRCode.propTypes = {
    qrCode: PropTypes.shape({input: PropTypes.string, image: PropTypes.string}).isRequired,
    remove: PropTypes.func.isRequired,
}

export default QRCode