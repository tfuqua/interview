import React, { useReducer } from 'react';

const defaultData = {
  books: [],
  total: 0,
  page: 0,
  query: '',
  isLoading: false,
}

function booksReducer(state, action) {
  switch (action.type) {
    case BOOK_ACTIONS.ADD_BOOKS:
      if (action.data) {
        return action.data;
      };
      break;
    case BOOK_ACTIONS.APPEND_BOOKS:
      // on book append, add new loaded books to existing list of books
      if (action.data) {
        const {books, total, page, query} = action.data;
        return {
          books: state.books ? state.books.concat(books) : books,
          total,
          page,
          query,
        }
      }
      break;
    case BOOK_ACTIONS.LOADING_STARTED:
      return {...state, ...{ isLoading: true }};
    case BOOK_ACTIONS.LOADING_ENDED:
      return {...state, ...{ isLoading: false }};
    default: return state;
  }

  return state;
}

export const BOOK_ACTIONS = {
  ADD_BOOKS:      'add_books',
  APPEND_BOOKS:   'append_books',
  LOADING_STARTED:'load_start',
  LOADING_ENDED:  'load_ended',
}

export const BooksContext = React.createContext();

/**
 * Books list context data wrapper
 */
export const BooksCollection = function BooksCollection(props) {
  const [data, dispatch] = useReducer(booksReducer, defaultData);

  return (
    <BooksContext.Provider value={[data, dispatch]} {...props} />
  )
}
