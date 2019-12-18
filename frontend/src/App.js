import React, { useEffect, useState } from 'react';
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
    const
        classes = useStyles(),
        [ searchTerm, setSearchTerm ] = useState('')
    
    useEffect(() => {
        if (searchTerm) {
            console.log(searchTerm)
        }
    }, [searchTerm])

    return (
        <div className="App">
            <SearchBar 
                onSearchTermChange={searchTerm => setSearchTerm(searchTerm)}
            />
            <Paper className={classes.root}>
                <Typography component="p">
                    Please submit searh term to see relevant repositories!
                </Typography>
            </Paper>
        </div>
    );
}

export default App;
