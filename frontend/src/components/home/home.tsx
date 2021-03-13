import React from "react";
import { Link, useLocation } from "react-router-dom";
import LinearProgress from "@material-ui/core/LinearProgress";
import TextField from "@material-ui/core/TextField";
import { classNames } from "../stylings/stylings";
import { RepoType, useNetworkContext } from "../network/network";
import { Divider } from "../divider/divider";
import { Callout } from "../callout/callout";

const RepoLink = ({
  owner,
  name,
  stars,
  language,
}: {
  owner: string;
  name: string;
  stars?: string;
  language?: string;
}) => {
  return (
    <Link
      to={`/repos/${owner}/${name}`}
      className={`${classNames.card} w-full`}
    >
      <div>{name}</div>
      <div className="opacity-50 group-hover:opacity-100 group-focus:opacity-100">
        {stars ? (
          <div>
            <span>Stars: </span>
            {stars}
          </div>
        ) : null}
        {language ? (
          <div>
            <span>Language: </span>
            {language}
          </div>
        ) : null}
      </div>
    </Link>
  );
};

const Home = () => {
  const network = useNetworkContext();
  const [searchText, setSearchText] = React.useState("");
  const [repos, setRepos] = React.useState([] as RepoType[]);
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState(null as null | string);
  const location = useLocation();

  React.useEffect(() => {
    if (searchText) {
      setLoading(true);
      const controller = new AbortController();
      const timeoutId = setTimeout(() => {
        network.fetchRepos({
          q: searchText,
          setLoading,
          setRepos,
          setError,
          controller,
        });
      }, 500);
      return () => {
        controller.abort();
        clearTimeout(timeoutId);
      };
    } else {
      setRepos([]);
      setLoading(false);
    }
  }, [searchText, network]);
  const hasRateLimitError = error?.toString().includes("rate limit");
  return (
    <div className="">
      <TextField
        value={searchText}
        label="Search for a specific repo"
        variant="outlined"
        onChange={(e) => {
          setSearchText(e.target.value);
        }}
        fullWidth
        autoFocus
        inputProps={{
          "data-testid": "repo-search",
        }}
      />
      <div className="flex flex-row flex-wrap p-2">
        {loading ? (
          <LinearProgress className="w-full" data-testid="repo-loading" />
        ) : null}
        {hasRateLimitError ? (
          <Callout color="warning">
            <div>{error?.toString()}</div>
            <Divider color="warning" size="smallest" />
            <div>
              <Link
                to={{
                  pathname: "/login",
                  search: `?redirect=${location.pathname}`,
                }}
                className={classNames.card}
              >
                Go login
              </Link>
            </div>
          </Callout>
        ) : null}
        {repos.length === 0 && !error && !loading && searchText.length > 0 ? (
          <Callout color="info">
            Couldn't find anything matching that search.
            <Divider color="info" size="smallest" />
            <a
              className={classNames.card}
              href="https://docs.github.com/en/rest/reference/search#constructing-a-search-query"
              target="_blank"
              rel="noreferrer"
            >
              Check the docs for how to construct more advanced searches
            </a>
          </Callout>
        ) : null}
        {repos.map((repo) => {
          return (
            <Link
              to={`/repos/${repo.owner.login}/${repo.name}`}
              className={`${classNames.card} w-full`}
              key={repo.id}
            >
              <div>{repo.name}</div>
              <div className="opacity-50 group-hover:opacity-100 group-focus:opacity-100">
                <div>
                  <span>Stars: </span>
                  {repo.stargazers_count}
                </div>
                <div>
                  <span>Language: </span>
                  {repo.language}
                </div>
              </div>
            </Link>
          );
        })}
      </div>
      <Divider />
      <div className="p-4">
        <div>
          Or go with a suggested repo, here are some of my personal favorites:
        </div>
        <Divider size="small"></Divider>
        <RepoLink name="ddf" owner="codice" />
        <RepoLink name="ddf-ui" owner="codice" />
        <RepoLink name="react" owner="facebook" />
        <RepoLink name="vue" owner="vuejs" />
        <RepoLink name="material-ui" owner="mui-org" />
      </div>
    </div>
  );
};

export default Home;
