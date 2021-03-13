import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  RouteProps,
} from "react-router-dom";
import Home from "./components/home/home";
import { AppContextInstance } from "./components/pull-requests/context";
import PullRequest from "./components/pull-requests/id/id";
import Navigation from "./components/navigation/navigation";
import List from "./components/pull-requests/list/list";
import RepoComponent from "./components/repos/id";
import { Contributors } from "./components/contributors/contributors";
import {
  Filters,
  Network,
  NetworkPropsType,
  NetworkContextInstance,
} from "./components/network/network";
import { Auth } from "./components/auth/auth";

const TypicalRouteWrapper = ({
  children,
  ...routeProps
}: { children: React.ReactNode } & RouteProps) => {
  // Since we use the map on contributors, this allows us to have it auto flex to the size it needs to be
  const specialClassForContributors =
    typeof routeProps.path === "string" &&
    routeProps.path.endsWith("/contributors")
      ? "overflow-hidden"
      : "";
  return (
    <Route {...routeProps}>
      <Navigation></Navigation>
      <div
        data-testid="root"
        className={`p-4 h-full w-full flex-grow flex-shrink ${specialClassForContributors}`}
      >
        {children}
      </div>
    </Route>
  );
};

const App = ({ network = Network }: NetworkPropsType) => {
  return (
    <NetworkContextInstance network={network}>
      <AppContextInstance>
        <Router>
          <Switch>
            <TypicalRouteWrapper path="/repos/:owner/:repo/pull-requests/open/:pull_number">
              <PullRequest />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/repos/:owner/:repo/pull-requests/closed/:pull_number">
              <PullRequest />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/repos/:owner/:repo/pull-requests/open">
              <List
                filters={[
                  Filters.pullRequests.bots,
                  Filters.pullRequests.closed,
                ]}
              />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/repos/:owner/:repo/pull-requests/closed">
              <List
                filters={[Filters.pullRequests.bots, Filters.pullRequests.open]}
              />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/repos/:owner/:repo/contributors">
              <Contributors />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/repos/:owner/:repo">
              <RepoComponent />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/login">
              <Auth />
            </TypicalRouteWrapper>
            <TypicalRouteWrapper path="/">
              <Home />
            </TypicalRouteWrapper>
          </Switch>
        </Router>
      </AppContextInstance>
    </NetworkContextInstance>
  );
};

export default App;
