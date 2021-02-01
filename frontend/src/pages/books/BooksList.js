import React, { useContext, useEffect, useState } from 'react';
import { BooksContext } from '../../data/BookContext';
import BookLoadMore from './BookLoadMore';
import BookCover from './BookCover';
import LoadingDialog from '../modals/LoadingDialog';

/**
 * Shows list of loaded books as a table
 */
function BooksList() {
  const [data] = useContext(BooksContext);
  const [books, setBooks] = useState([]);
  const [haveMore, setHaveMore] = useState(false);

  useEffect(() => {
    let loadedBooks = 0;
    if (data && data.books) {
      setBooks(data.books);
      loadedBooks = data.books.length;
    } else {
      setBooks([]);
    }
    // if we have more total than loaded books show "load more" button
    if (data && data.total && data.total > loadedBooks) {
      setHaveMore(true);
    } else {
      setHaveMore(false);
    }
  }, [data]);

  return (
    <div className="BooksList">
      {books.length > 0 && (
        <>
          <div className="booksGrid">
            <div className="table">
              <div className="tableHead">
                <div className="tableRow">
                <div className="tableCell">Cover</div>
                <div className="tableCell">Title</div>
                <div className="tableCell">Author(s)</div>
                <div className="tableCell">Year</div>
                </div>
              </div>
              <div className="tableBody">
                {books.map((book) => (
                  <div className="tableRow" key={book.key}>
                    <div className="tableCell">
                      <BookCover cover={book.cover} />
                    </div>
                    <div className="tableCell bookTitle">{book.title}</div>
                    <div className="tableCell bookAuthor">{book.author}</div>
                    <div className="tableCell">{book.year}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>

          { haveMore === true && (
            <BookLoadMore />
          )}
        </>
      )}

      {data.isLoading && (<LoadingDialog />)}

    </div>
  );
}

export default BooksList;