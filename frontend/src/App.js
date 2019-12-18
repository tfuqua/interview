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
}

function App() {
    const
        classes = useStyles(),
        [ searchTerm, setSearchTerm ] = useState(''),
        [data, setData] = useState({
            totalCount: undefined,
            repos: []
        }),
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
            </Paper> : null
    
    useEffect(() => {
        const fetchAndSetData = async () => {
            const result = await fetchRepos(searchTerm)
            setData(result)
        }

        if (searchTerm) {
            fetchAndSetData()
        } else {
            setData({ totalCount: undefined, repos: [] })
        }
    }, [searchTerm])

    return (
        <div className="App">
            <SearchBar 
                onSearchTermChange={searchTerm => setSearchTerm(searchTerm)}
            />
            { noSearchPerformedMessage }
            { dataInformationMessage }
        </div>
    );
}

export default App;
