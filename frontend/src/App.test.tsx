import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import "@testing-library/jest-dom"
import { render, screen } from '@testing-library/react'

/**
 * Super basic smoke test
 */
it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<App />, div);
});

test('example with testing-library', () => {
  render(<App/>)
  expect(screen.getByTestId('root')).toBeVisible()
})