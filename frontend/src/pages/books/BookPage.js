import React from 'react';
import BookSearch from './BookSearch';
import { BooksCollection } from '../../data/BookContext';
import BooksList from './BooksList';
import './BookPage.scss';

function BookPage() {
  return (
    <div className="BookPage">
      <div className="container">
        <BooksCollection>
          <BookSearch />
          <BooksList />
        </BooksCollection>
      </div>
    </div>
  )
}

export default BookPage;