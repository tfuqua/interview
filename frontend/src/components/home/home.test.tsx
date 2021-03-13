import React from "react";
import ReactDOM from "react-dom";
import Home from "./home";
import "@testing-library/jest-dom";
import {
  act,
  fireEvent,
  prettyDOM,
  render,
  screen,
} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { NetworkContextInstance } from "../network/network";
import { FakeRepo } from "../../../test/mocks";
import { generateFakeNetwork } from "../../../test/generic";
import { MemoryRouter } from "react-router-dom";

// smoke test for home
test("home renders", () => {
  act(() => {
    render(
      <NetworkContextInstance network={generateFakeNetwork()}>
        <MemoryRouter>
          <Home />
        </MemoryRouter>
      </NetworkContextInstance>
    );
  });
});

test("fetches repos when user types", () => {
  jest.useFakeTimers();
  const fakeNetwork = generateFakeNetwork();
  fakeNetwork.fetchRepos = jest.fn();
  act(() => {
    render(
      <NetworkContextInstance network={fakeNetwork}>
        <MemoryRouter>
          <Home />
        </MemoryRouter>
      </NetworkContextInstance>
    );
  });
  expect(fakeNetwork.fetchRepos).not.toHaveBeenCalled();
  expect(screen.getByTestId("repo-search")).toBeVisible();
  userEvent.type(screen.getByTestId("repo-search"), "ddf");
  expect(screen.getByTestId("repo-search")).toHaveValue("ddf");
  jest.runAllTimers();
  expect(fakeNetwork.fetchRepos).toHaveBeenCalled();
});

test("displays loading, and then repos", () => {
  jest.useFakeTimers();
  const fakeNetwork = generateFakeNetwork();
  fakeNetwork.fetchRepos = jest.fn(async ({ setLoading, setRepos }) => {
    act(() => {
      setLoading(true);
      expect(screen.getByTestId("repo-loading")).toBeVisible();
      setRepos([FakeRepo]);
      setLoading(false);
    });
  });
  act(() => {
    render(
      <NetworkContextInstance network={fakeNetwork}>
        <MemoryRouter>
          <Home />
        </MemoryRouter>
      </NetworkContextInstance>
    );
  });
  userEvent.type(screen.getByTestId("repo-search"), "ddf");
  jest.runAllTimers();
  expect(screen.getByText("JavaScript")).toBeVisible();
});
