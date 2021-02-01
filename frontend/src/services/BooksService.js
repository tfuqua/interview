import axios from 'axios';
import { parseBookData } from './BooksParser';

function onData(result) {
  if (
    result
    && result.status < 400
    && result.statusText === 'OK'
  ) {
    return result.data;
  }
  throw new Error('Error getting response');
}

function onError(error) {
  return {
    error: error && error.message ? error.message : 'Error getting response'
  };
}

/**
 * Search for books
 * @param {string} query general query that search for anything in the books data
 * @param {string} author search only for author name
 * @param {string} title search only in books title
 * @param {number} page for paging (first page is 0)
 */
export function getBooks(query, author, title, page = 0) {
  return new Promise((resolve) => {
    const params = {
      limit: 10,
    };

    if (query) {
      params.q = query;
    }

    if (author) {
      params.author = author;
    }

    if (title) {
      params.title = title;
    }

    if (page > 0) {
      params.page = ++page;
    }

    resolve(
      axios.get('//openlibrary.org/search.json', { params })
        .then(onData)
        .then((data) => {
          let books = [];
          let total = 0;

          if  (data) {
            if (data.docs && data.numFound) {
              const { docs, numFound } = data;
              total = numFound || 0;
              if (Array.isArray(docs)) {
                docs.forEach((book) => books.push(
                  parseBookData(book)
                ));
              }
            } else if (typeof data === 'string') {
              throw new Error('Error parsing data from server!');
            }
          }
          return {
            books,
            total,
            page,
            query,
          }
        })
        .catch(onError)
    );
  });
}
