import React, { useEffect, useState, useContext } from 'react';
import useTimeout from '@rooks/use-timeout';
import { getBooks } from '../../services/BooksService';
import { BooksContext, BOOK_ACTIONS } from '../../data/BookContext';
import ErrorDialog from '../modals/ErrorDialog';

// TODO: add search by author or title only (API supports that)

/**
 * Search section for books
 */
function BookSearch() {
  const [query, setQuery] = useState('');
  const [, booksDispatch ] = useContext(BooksContext);
  const [error, setError] = useState(null);

  const sendQuery = () => {
    booksDispatch({ type: BOOK_ACTIONS.LOADING_STARTED });

    getBooks(query).then((data) => {
      if (!data || data.error) {
        setError(data.error);
        booksDispatch({ type: BOOK_ACTIONS.LOADING_ENDED });
      } else {
        booksDispatch({
          type: BOOK_ACTIONS.ADD_BOOKS,
          data,
        });
      }
    });
  }

  const closeErrorDialog = () => {
    setError(null);
  }

  const { start, clear } = useTimeout(sendQuery, 1000);

  const onQueryChange = (e) => {
    setQuery(e.target.value);
    // request for API data after 1 sec of keyboard inactivity
    clear();
    start();
  }

  // clear timer when component is unmounted
  useEffect(() => clear, []);

  return (
    <div className="BookSearch">
      <input
        type="text"
        onChange={onQueryChange}
        value={query}
        placeholder="Search by name"
      />
      {error !== null && (
        <ErrorDialog
          description={error}
          onClose={closeErrorDialog}
        />
      )}
    </div>
  )
}

export default BookSearch;