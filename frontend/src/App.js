import React, { useEffect, useState } from 'react';
import axios from 'axios'
import { SearchBar, SearchInformation } from './components'

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
        [ searchTerm, setSearchTerm ] = useState(''),
        [data, setData] = useState({ ...initialState })
    
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
            <SearchInformation
                searchTerm={searchTerm}
                totalCount={data.totalCount}
                hasErrorOccurred={data.hasErrorOccurred}
                isFetchingData={data.isFetchingData}
            />
        </div>
    );
}

export default App;
