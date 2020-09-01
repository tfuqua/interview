import { Button } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, { useState } from 'react';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: "10px 10px 10px 10px",
    },
    input: {
        width: "100%",
    },
    searchGrid: {
        padding: "10px 10px 10px 10px",
        alignContent: "center",
        width: "100%",
    },
    button: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        border: 0,
        borderRadius: 3,
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        color: 'white',
        height: 48,
        margin: '5px 5px 5px 5px',
        padding: '0 30px',
        fontWeight: "bold"
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 300,
    },
}));

const bookSearchPane = (props) => {
    const classes = useStyles();

    const [searchFilterState, setSearchFilterState] = useState({
        searchFilter: {
            bookName: "",
            authorName: "",
            authorSurname: "",
            minPublishDate: null,
            maxPublishDate: null,
        },
        sort: {
            sortField: "",
        },
    });

    const handleInputChange = (e) => {
        const updatedSearchFilter = { ...searchFilterState.searchFilter };
        updatedSearchFilter[e.target.name] = e.target.value;
        setSearchFilterState({
            ...searchFilterState,
            searchFilter: updatedSearchFilter
        });
    }

    const handleSortChange = (event) => {
        setSearchFilterState({
            ...searchFilterState,
            sort: {
                sortField: event.target.value
            }
        });
    };

    const handleClearClicked = () => {
        setSearchFilterState({
            searchFilter: {
                bookName: "",
                authorName: "",
                authorSurname: "",
                minPublishDate: null,
                maxPublishDate: null,
            },
            sort: {
                sortField: "",
            }
        });
    }

    const handleSearchClicked = () => {
        const sort = searchFilterState.sort.sortField ? {
            'sortField': searchFilterState.sort.sortField.split('_')[0],
            'sortDir': searchFilterState.sort.sortField.split('_')[1]
        } : {};
        props.onSearchClicked(searchFilterState.searchFilter, sort);
    }

    return (
        <div className={classes.root}>
            <Grid container spacing={3}>
                <Grid className={classes.searchGrid} item xs={12} sm={6}>
                    <TextField className={classes.input} name="bookName" value={searchFilterState.searchFilter.bookName} onChange={handleInputChange} label="Book Name" variant="filled" />
                </Grid>
                <Grid className={classes.searchGrid} item xs={12} sm={6}>
                    <TextField className={classes.input} name="authorName" value={searchFilterState.searchFilter.authorName} onChange={handleInputChange} label="Author Name" variant="filled" />
                </Grid>
                <Grid className={classes.searchGrid} item xs={12} sm={6}>
                    <TextField className={classes.input} name="authorSurname" value={searchFilterState.searchFilter.authorSurname} onChange={handleInputChange} label="Author Surname" variant="filled" />
                </Grid>
                <Grid style={{ padding: 20 }} container alignItems="flex-start" justify="flex-end" direction="row">
                    <Button onClick={handleClearClicked} className={classes.button}>Clear</Button>
                    <Button onClick={handleSearchClicked} className={classes.button}>Search</Button>
                </Grid>
                <Grid style={{ padding: 20 }} container alignItems="flex-start" justify="flex-end" direction="row">
                    <FormControl className={classes.formControl}>
                        <InputLabel htmlFor="sort-by">Sort by</InputLabel>
                        <Select
                            labelId="sort-by-select-label"
                            id="books-sort-by"
                            value={searchFilterState.sort.sortField}
                            inputProps={{
                                name: 'sort',
                                id: 'sort-by',
                            }}
                            onChange={handleSortChange}>
                            <MenuItem value={'publishDate_desc'}>Publish Date: Newest to Oldest</MenuItem>
                            <MenuItem value={'publishDate_asc'}>Publish Date: Oldest to Newest</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
            </Grid>
        </div>
    );
}

export default bookSearchPane;