import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import { Link, Route, useLocation, useParams } from "react-router-dom";
import { classNames } from "../stylings/stylings";
import { useAppContext } from "../pull-requests/context";
import LinearProgress from "@material-ui/core/LinearProgress";

const BreadCrumbSeparator = () => {
  return <div className="px-2">/</div>;
};

export default function Navigation() {
  const location = useLocation();
  const { repo, owner, pull_number } = useParams<{
    repo: string;
    owner: string;
    pull_number: string;
  }>();
  const { authenticatedUser, loading } = useAppContext();
  return (
    <AppBar position="static" className="flex-grow-0 flex-shrink-0">
      <Toolbar>
        <div className="flex-grow text-white flex flex-row flex-nowrap items-center">
          <Route path="/">
            <Link to="/">Github Fun</Link>
          </Route>
          <Route path="/repos/:owner/:repo">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}`}>
              {owner}'s {repo}
            </Link>
          </Route>
          <Route path="/repos/:owner/:repo/contributors">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}/contributors`}>
              contributors
            </Link>
          </Route>
          <Route path="/repos/:owner/:repo/pull-requests/open">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}/pull-requests/open`}>
              Open pull requests
            </Link>
          </Route>
          <Route path="/repos/:owner/:repo/pull-requests/closed">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}/pull-requests/closed`}>
              Closed pull requests
            </Link>
          </Route>
          <Route path="/repos/:owner/:repo/pull-requests/closed/:pull_number">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}/pull-requests/closed`}>
              {pull_number}
            </Link>
          </Route>
          <Route path="/repos/:owner/:repo/pull-requests/open/:pull_number">
            <BreadCrumbSeparator />
            <Link to={`/repos/${owner}/${repo}/pull-requests/open`}>
              {pull_number}
            </Link>
          </Route>
          <Route path="/login">
            <BreadCrumbSeparator />
            <Link to={`/login`}>Login</Link>
            <BreadCrumbSeparator />
            {location.search ? (
              <Link to={`${location.search.split("redirect=")[1]}`}>
                Go back to {location.search.split("redirect=")[1]}?
              </Link>
            ) : null}
          </Route>
        </div>
        {(() => {
          if (loading) {
            return (
              <Button
                component={Link}
                className="relative"
                to={{
                  pathname: "/login",
                  search: `?redirect=${location.pathname}`,
                }}
                color="inherit"
              >
                <span className="normal-case">Logging in</span>
                <LinearProgress
                  className="left-0 bottom-0 w-full h-full"
                  style={{ position: "absolute" }}
                />
              </Button>
            );
          } else if (authenticatedUser) {
            return (
              <Button
                component={Link}
                to={{
                  pathname: "/login",
                  search: `?redirect=${location.pathname}`,
                }}
                color="inherit"
              >
                {authenticatedUser.avatar_url ? (
                  <img
                    src={authenticatedUser.avatar_url}
                    className={classNames.avatar}
                    alt="avatar"
                  />
                ) : null}
                <span className="normal-case">{authenticatedUser.login}</span>
              </Button>
            );
          } else {
            return (
              <Button
                component={Link}
                to={{
                  pathname: "/login",
                  search: `?redirect=${location.pathname}`,
                }}
                color="inherit"
              >
                <span className="normal-case">Login</span>
              </Button>
            );
          }
        })()}
      </Toolbar>
    </AppBar>
  );
}
