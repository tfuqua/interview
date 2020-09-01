import React, { useState, useEffect } from 'react';
import Grid from '@material-ui/core/Grid';
import BookCard from './BookCard'
import BookSearchPane from './BookSearchPane';
import { Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        padding: "10px 10px 10px 10px"
    },
    horizontalLine: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        opacity: "0.2",
        height: "0.6px",
        width: "75.0em",
        margin: "0 auto",
    },
    loadMoreButton: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        border: 0,
        borderRadius: 3,
        alignContent: "center",
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        color: 'white',
        height: 35,
        padding: '0 30px',
        margin: "20px 20px 20px 20px",
        fontWeight: "bold"
    }
}));

const bookList = () => {
    const classes = useStyles();
    const [booksState, setBooksState] = useState({
        books: [
        ],
        nextPageExisting: false,
        currentPage: 0,
        filters: {},
        sort: {}
    });

    useEffect(() => {
        queryBooksHandler({}, {});
    }, []);

    const queryBooksHandler = (searchFilter, sortFields) => {
        retrieveBooks(searchFilter, 0, sortFields, (response) => {
            setBooksState({
                books: [
                    ...response.data.pageResult
                ],
                nextPageExisting: response.data.nextPageExisting,
                currentPage: 0,
                filters: searchFilter,
                sort: sortFields
            });
        })
    }

    const loadMoreHandler = () => {
        const newPageNumber = booksState.currentPage + 1;
        retrieveBooks(booksState.filters, newPageNumber, booksState.sort, (response) => {
            setBooksState({
                books: [
                    ...booksState.books, ...response.data.pageResult
                ],
                nextPageExisting: response.data.nextPageExisting,
                currentPage: response.data.pageNumber,
                filters: booksState.filters,
                sort: booksState.sort
            });
        });
    }

    const retrieveBooks = (searchFilter, pageNumber, sortFields, responseHandler) => {
        Axios.get("/books", {
            params: {
                'name': searchFilter.bookName,
                'authorname': searchFilter.authorName,
                'authorsurname': searchFilter.authorSurname,
                'page': pageNumber,
                'size': 20,
                'sort': sortFields.sortField ? `${sortFields.sortField},${sortFields.sortDir}` : '',
            }
        }).then((response) => {
            responseHandler(response);
        }).catch((e) => {
            console.log(e);
        });
    }

    let loadMoreButton = null;
    if (booksState.nextPageExisting) {
        loadMoreButton =
            <div className={classes.root}>
                <hr className={classes.horizontalLine} />
                <Button className={classes.loadMoreButton} onClick={loadMoreHandler}>Load More</Button>
            </div>;
    }

    return (
        <div className={classes.root}>
            <BookSearchPane onSearchClicked={queryBooksHandler}></BookSearchPane>
            <hr className={classes.horizontalLine} />
            <div>
                <Grid container spacing={2} direction="row" justify="center" alignItems="center" style={{ padding: 24 }}>
                    {booksState.books.map(book => {
                        return (
                            <Grid item key={book.id}>
                                <BookCard book={book}></BookCard>
                            </Grid>);
                    })}
                </Grid>
            </div>
            {loadMoreButton}
        </div>
    );
}

export default bookList;