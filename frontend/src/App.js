import React from 'react';
import { Paper, Typography } from "@material-ui/core"
import { makeStyles } from '@material-ui/core/styles';
import SearchBar from './components/search-bar'

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(1),
        padding: theme.spacing(3, 2),
        textAlign: 'center'
    },
}));

function App() {
    const classes = useStyles();

    return (
        <div className="App">
            <SearchBar/>
            <Paper className={classes.root}>
                <Typography component="p">
                    Please submit searh term to see relevant repositories!
                </Typography>
            </Paper>
        </div>
    );
}

export default App;
