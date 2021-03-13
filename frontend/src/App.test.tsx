import React from "react";
import App from "./App";
import "@testing-library/jest-dom";
import { act, render, screen } from "@testing-library/react";
import { generateFakeNetwork } from "../test/generic";

/**
 * basic smoke test aka does the app render
 */
test("example with testing-library", async () => {
  act(() => {
    render(<App network={generateFakeNetwork()} />);
  });
  expect(screen.getByTestId("root")).toBeVisible();
});
