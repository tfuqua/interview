import React, { useState } from 'react';
import { getCoverImage, BOOK_SIZES } from '../../services/BooksParser';

/**
 * Shows cover image as a thumbnail, and on mouse hover shows bigger the same image
 * @param {Object} cover key->value object for cover image
 */
export default function BookCover({ cover }) {
  const [hoverImage, setHoverImage] = useState(null);
  const [hoverPosition, setHoverPosition] = useState();

  const imageHover = (e) => {
    try {
      if (cover) {
        setHoverImage(getCoverImage(cover, BOOK_SIZES.MEDIUM));
        imageMouseMove(e);
      }
    } catch (e) {};
  }

  const imageHout = () => {
    setHoverImage(null);
    setHoverPosition(null);
  }

  const imageMouseMove = (e) => {
    setHoverPosition({
      x: e.clientX + 10,
      y: e.clientY - 30,
    });
  }

  return (
    <>
      <img
        src={getCoverImage(cover, BOOK_SIZES.SMALL)}
        // alt={`${book.author} - ${book.title}`}
        alt=""
        onMouseOver={imageHover}
        onMouseMove={imageMouseMove}
        onMouseOut={imageHout}
      />
      {hoverImage && hoverPosition && (
          <img
            src={hoverImage}
            alt=""
            style={{
              position: 'fixed',
              left: hoverPosition.x,
              top: hoverPosition.y
            }}
          />
        )}
    </>
  )
}