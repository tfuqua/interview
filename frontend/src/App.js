import React, { useEffect, useState } from 'react';
import axios from 'axios'
import { makeStyles } from '@material-ui/core/styles'
import { SearchBar, SearchInformation, SearchResult } from './components'

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

const useStyles = makeStyles(theme => ({
    stickyHeader: {
        backgroundColor: theme.palette.common.white,
        position: 'sticky',
        top: 0,
        zIndex: 3
    }
}))

function App() {
    const
        classes = useStyles(),
        [ searchTerm, setSearchTerm ] = useState(''),
        [data, setData] = useState({ ...initialState }),
        { repos, totalCount, isFetchingData, hasErrorOccurred } = data
    
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
        <div className='App'>
            <div className={classes.stickyHeader}>
                <SearchBar
                    isFetchingData={isFetchingData}
                    onSearchTermChange={searchTerm => setSearchTerm(searchTerm)}
                />
                <SearchInformation
                    searchTerm={searchTerm}
                    totalCount={totalCount}
                    hasErrorOccurred={hasErrorOccurred}
                    isFetchingData={isFetchingData}
                />
            </div>
            { repos.length > 0 ?
                <SearchResult repos={repos}/> : null
            }
        </div>
    );
}

export default App;
