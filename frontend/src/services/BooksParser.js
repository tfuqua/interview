export const BOOK_SIZES = {
  SMALL: 'S',
  MEDIUM: 'M',
  LARGE: 'L',
}

/**
 * Generate cover image url
 * @param {Object} data Book cover object. Example: { key: 'isbn', value: '1235432' }
 * @param {string} size BOOK_SIZES.SMALL, BOOK_SIZES.MEDIUM or BOOK_SIZES.LARGE
 */
export function getCoverImage(data, size = BOOK_SIZES.SMALL) {
  if (data && data.key && data.value) {
    return `//covers.openlibrary.org/b/${data.key}/${data.value}-${size}.jpg`
  }
  return '';
}

/**
 * Generate author image url
 * @param {Object} data Book cover object. Example: { key: 'isbn', value: '1235432' }
 * @param {string} size BOOK_SIZES.SMALL, BOOK_SIZES.MEDIUM or BOOK_SIZES.LARGE
 */
export function getAuthorImage(data, size = BOOK_SIZES.SMALL) {
  if (data && data.key && data.value) {
    return `//covers.openlibrary.org/a/${data.key}/${data.value}-${size}.jpg`
  }
  return '';
}

/**
 * Parse from book object received from API only important data
 * that we need for UI. Also get the best key/value pair for
 * cover and author image
 * @param {Object} book API received book
 */
export function parseBookData(book) {
  const response = {};
  if (book) {
    const {
      key,
      title,
      publish_year,
      author_name,
      cover_i,
      lccn,
      isbn
    } = book;

    if (key) {
      response.key = key;
    }

    if (title) {
      response.title = title;
    }

    if (publish_year && Array.isArray(publish_year)) {
      response.year = publish_year[0];
    }

    if (author_name) {
      if (Array.isArray(author_name)) {
        response.author = author_name.join(', ');
      } else {
        response.author = author_name;
      }
    }

    // if cover index is set, use it in cover request
    if (cover_i) {
      response.cover = {
        key: 'id',
        value: cover_i,
      }
    } else if (lccn && Array.isArray(lccn) && lccn.length > 0) {
      response.cover = {
        key: 'lccn',
        value: lccn[0]
      }
    } else if (isbn && Array.isArray(isbn) && isbn.length > 0) {
      response.cover = {
        key: 'isbn',
        value: isbn[0]
      }
    }
  }
  return response;
}
