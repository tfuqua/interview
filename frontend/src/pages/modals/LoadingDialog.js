import React from 'react';
import Modal from './Modal';
import './LoadingDialog.css';

export default function LoadingDialog() {
  return (
    <Modal isModal={false}>
      <div className="loadingDialog">
        Loading ...
      </div>
    </Modal>
  )
};
