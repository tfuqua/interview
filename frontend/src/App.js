import React, { useEffect, useState } from 'react';
import { Paper, Typography } from "@material-ui/core"
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios'
import SearchBar from './components/search-bar'

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(1),
        padding: theme.spacing(3, 2),
        textAlign: 'center'
    },
}))

const fetchRepos = async function(searchTerm) {
    const result = await axios(
        `https://api.github.com/search/repositories?q=${searchTerm}`
    )

    try {
        const
        totalCount = result.data.total_count,
        repos = result.data.items.map(item => {
            return {
                name: item.full_name,
                avatarUrl: item.owner.avatar_url,
                description: item.description,
                stars: item.stargazers_count,
                language: item.language
            }
        })

        return { repos, totalCount }
    } catch {
        throw Error('An error occurred')
    }
}

const initialState = {
    hasErrorOccurred: false,
    isFetchingData: false,
    repos: [],
    totalCount: undefined
}

function App() {
    const
        classes = useStyles(),
        [ searchTerm, setSearchTerm ] = useState(''),
        [data, setData] = useState({ ...initialState }),
        noSearchPerformedMessage = searchTerm ? null :
            <Paper className={classes.root}>
                <Typography component="p">
                    Please submit searh term to see relevant repositories!
                </Typography>
            </Paper>,
        dataInformationMessage = data.totalCount ? 
            <Paper className={classes.root}>
                <Typography component="p">
                    A total of {data.totalCount} repos are found!
                </Typography>
            </Paper> : null,
        errorMessage = data.hasErrorOccurred ? 
            <Paper className={classes.root}>
                <Typography component="p">
                    An unexpected error occurred; please try again later!
                </Typography>
            </Paper> : null,
        loadingMessage = data.isFetchingData ? 
            <Paper className={classes.root}>
                <Typography component="p">
                    Data fetcing is in progress; hold tight!
                </Typography>
            </Paper> : null
    
    useEffect(() => {
        const fetchAndSetData = async () => {
            setData({ ...initialState, isFetchingData: true })
            const result = await fetchRepos(searchTerm)
            setData({ ...initialState, ...result })
        }

        if (searchTerm) {
            try {
                fetchAndSetData()
            } catch {
                setData({ ...initialState, hasErrorOccurred: true })
            }
        } else {
            setData({ ...initialState })
        }
    }, [searchTerm])

    return (
        <div className="App">
            <SearchBar 
                onSearchTermChange={searchTerm => setSearchTerm(searchTerm)}
            />
            { noSearchPerformedMessage }
            { dataInformationMessage }
            { errorMessage }
            { loadingMessage }
        </div>
    );
}

export default App;
