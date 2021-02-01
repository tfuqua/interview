import React, { useContext, useState } from 'react';
import { BooksContext, BOOK_ACTIONS } from '../../data/BookContext';
import { getBooks } from '../../services/BooksService';
import ErrorDialog from '../modals/ErrorDialog';

/**
 * Show "load more" button and set event to load more books on click
 */
function BookLoadMore() {
  const [data, booksDispatch] = useContext(BooksContext);
  const [error, setError] = useState(null);

  const loadMore = function() {
    const { query } = data;
    // set next page to be loaded
    const page = data.page + 1;

    booksDispatch({ type: BOOK_ACTIONS.LOADING_STARTED });

    getBooks(query, undefined, undefined, page).then((data) => {
      if (!data || data.error) {
        setError(data.error);
        booksDispatch({ type: BOOK_ACTIONS.LOADING_ENDED });
      } else {
        // add new books to the existing collection of loaded books
        booksDispatch({
          type: BOOK_ACTIONS.APPEND_BOOKS,
          data,
        })
      }
    });
  }

  const closeErrorDialog = () => {
    setError(null);
  }

  return (
    <>
      <button className="bLoadMore" onClick={loadMore}>
        Load more
      </button>
      {error !== null && (
        <ErrorDialog
          description={error}
          onClose={closeErrorDialog}
        />
      )}
    </>
  )
};

export default BookLoadMore;