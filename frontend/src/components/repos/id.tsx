import React from "react";
import { Link, useParams } from "react-router-dom";
import {
  Filters,
  PullRequestType,
  useNetworkContext,
} from "../network/network";
import { classNames } from "../stylings/stylings";
import { useScrollToTopOnMount } from "../hooks/hooks";
import LinearProgress from "@material-ui/core/LinearProgress";
import { Callout } from "../callout/callout";
import { Divider } from "../divider/divider";

const RepoComponent = () => {
  const network = useNetworkContext();
  useScrollToTopOnMount();
  const { repo, owner } = useParams<{ repo: string; owner: string }>();
  const [pullRequests, setPullRequests] = React.useState(
    [] as PullRequestType[]
  );
  const [loading, setLoading] = React.useState(true);
  const [error, setError] = React.useState(null as null | string);

  React.useEffect(() => {
    network.fetchPullRequests({
      owner,
      repo,
      setPullRequests,
      setLoading,
      setError,
    });
  }, [owner, network, repo]);

  if (loading) {
    return (
      <div>
        Loading information on {repo} <LinearProgress />
      </div>
    );
  }
  const notFoundError = error?.toString().includes("Not Found");

  if (notFoundError) {
    return (
      <Callout color="error">
        Could not find that repo. Please check that it exists and that also have
        access.
        <Divider color="error" size="smallest"></Divider>
        <a
          className={classNames.card}
          href={`https://github.com/${owner}/${repo}`}
          target="_blank"
          rel="noreferrer"
        >
          Check on github
        </a>
      </Callout>
    );
  }
  if (error) {
    return <Callout color="error">{error?.toString()}</Callout>;
  }

  const openPullRequests = pullRequests
    .filter(Filters.pullRequests.bots)
    .filter(Filters.pullRequests.closed);
  const closedPullRequests = pullRequests
    .filter(Filters.pullRequests.bots)
    .filter(Filters.pullRequests.open);

  return (
    <div className="flex flex-row flex-wrap">
      <Link
        to={`/repos/${owner}/${repo}/pull-requests/open`}
        className={classNames.card}
      >
        {openPullRequests.length} open pull requests
      </Link>
      <Link
        to={`/repos/${owner}/${repo}/pull-requests/closed`}
        className={classNames.card}
      >
        {closedPullRequests.length} closed pull requests
      </Link>
      <Link
        to={`/repos/${owner}/${repo}/contributors`}
        className={classNames.card}
      >
        Visualize Contributors
      </Link>
    </div>
  );
};

export default RepoComponent;
