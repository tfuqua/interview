import React from 'react'
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress, Paper, Typography } from '@material-ui/core'

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(1),
        padding: theme.spacing(3, 2),
        textAlign: 'center'
    }
}))

const Message = ({ text, children }) => {
    const classes = useStyles()

    return (
        <Paper className={classes.root}>
            <Typography component='p'>
                { text }
            </Typography>
            {children}
        </Paper> 
    )
}

export default function({ searchTerm, totalCount, hasErrorOccurred, isFetchingData }) {
    const
        noSearchPerformedMessage = searchTerm ? null :
            <Message text={'Please submit searh term to see relevant repositories'}/>,
        dataInformationMessage = totalCount ?
            <Message text={`A total of ${totalCount} repos are found; displaying first 30!`}/> : null,
        errorMessage = hasErrorOccurred ?
            <Message text={'An unexpected error occurred; please try again later!'}/> : null,
        loadingMessage = isFetchingData ?
            <Message text={'Data fetcing is in progress; hold tight!'}>
                <CircularProgress/>
            </Message> : null

    return (
        <>
            { noSearchPerformedMessage }
            { dataInformationMessage }
            { errorMessage }
            { loadingMessage }
        </>
    )
}