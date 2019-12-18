import React, { useCallback, useEffect, useState } from 'react'
import { AppBar, InputBase, Toolbar, Typography } from '@material-ui/core'
import { fade, makeStyles } from '@material-ui/core/styles'
import SearchIcon from '@material-ui/icons/Search'

const debounce = function(func, wait) {
    let timeout
    return function(...args) {
        if (timeout) clearTimeout(timeout)

        timeout = setTimeout(() => {
            timeout = null
            func.apply(null, args)
        }, wait)
    }
}

const useStyles = makeStyles(theme => ({
    appbar: {
        marginBottom: theme.spacing(1)
    },
    title: {
        flexGrow: 1
    },
    search: {
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25)
        },
        marginLeft: theme.spacing(1),
        width: 'auto'
    },
    searchIcon: {
        width: theme.spacing(7),
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
    },
    inputRoot: {
        color: 'inherit'
    },
    inputInput: {
        padding: theme.spacing(1, 1, 1, 7),
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: 120,
            '&:focus': {
            width: 200
            }
        }
    }
}))

export default function({ isFetchingData, onSearchTermChange }) {
    const
        classes = useStyles(),
        [term, setTerm] = useState(''),
        debounceOnSearhTerm = useCallback(debounce(onSearchTermChange, 500), [])

    useEffect(() => {
        debounceOnSearhTerm(term)
    }, [debounceOnSearhTerm, term])

    return (
        <AppBar position='static' className={classes.appbar}>
            <Toolbar>
                <Typography className={classes.title} variant='h6' noWrap>
                    Github Repo Search
                </Typography>
                <div className={classes.search}>
                <div className={classes.searchIcon}>
                    <SearchIcon />
                </div>
                <InputBase
                    disabled={isFetchingData}
                    placeholder='Term...'
                    classes={{
                        root: classes.inputRoot,
                        input: classes.inputInput
                    }}
                    value={term}
                    onChange={e => setTerm(e.target.value)}
                />
                </div>
            </Toolbar>
        </AppBar>
    )
}
