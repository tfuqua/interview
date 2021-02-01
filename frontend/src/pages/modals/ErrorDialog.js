import React from 'react';
import Modal from './Modal';
import './ErrorDialog.scss';

export default function ErrorDialog({ title = 'Error', description, ...props }) {
  return (
    <Modal {...props}>
      <div className="errorDialog">
        <div>{title}</div>
        <div>{description}</div>
      </div>
    </Modal>
  )
};
