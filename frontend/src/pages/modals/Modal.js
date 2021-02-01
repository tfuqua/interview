import { useEffect, useState } from 'react';
import ReactDOM from 'react-dom';

const modalDOM = document.getElementById('modal');

export default function Modal({ children, isModal = true, onClose }) {
  const [modal, setModal] = useState(null);

  const dialogClick = () => {
    if (onClose) {
      onClose();
    }
  }

  useEffect(() => {
    let dialogClasses = 'modalDialog';
    const dom = document.createElement('div');

    if (onClose) {
      dom.addEventListener('click', dialogClick);
    }

    if (isModal) {
      dialogClasses += ' blocking';
    }
    dom.className = dialogClasses;

    modalDOM.appendChild(dom);

    setModal(dom);

    return () => {
      if (onClose) {
        dom.removeEventListener('click', dialogClick);
      }
      modalDOM.removeChild(dom);
    }
  }, []);

  return modal !== null && ReactDOM.createPortal(children, modal);
};
