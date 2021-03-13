import React from "react";
import { Link, useParams } from "react-router-dom";
import Markdown from "react-markdown";
import { classNames } from "../../stylings/stylings";
import {
  FilterType,
  PullRequestType,
  useNetworkContext,
} from "../../network/network";
import LinearProgress from "@material-ui/core/LinearProgress";
import { Callout } from "../../callout/callout";
import { Divider } from "../../divider/divider";

const PullRequestPreview = ({
  pullRequest,
}: {
  pullRequest: PullRequestType;
}) => {
  const { repo, owner } = useParams<{ repo: string; owner: string }>();

  return (
    <Link
      to={`/repos/${owner}/${repo}/pull-requests/${
        pullRequest.state === "open" ? "open" : "closed"
      }/${pullRequest.number}`}
      className={classNames.card}
    >
      <div className="flex flex-row flex-nowrap items-center">
        <img
          src={pullRequest.user?.avatar_url}
          className="rounded-full border-2 border-white bg-gray-100 w-7 h-7"
          alt="avatar"
        />
        <div className="pl-2">{pullRequest.user?.login}</div>
      </div>
      <div className="">{pullRequest.title}</div>
      {pullRequest.body ? (
        <div className="max-h-48 overflow-hidden">
          <Markdown allowDangerousHtml disallowedTypes={["link"]}>
            {pullRequest.body}
          </Markdown>
        </div>
      ) : null}
    </Link>
  );
};

const List = ({ filters = [] }: { filters?: FilterType[] }) => {
  const network = useNetworkContext();

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
  }, [repo, owner, network]);

  const relevantPullRequests = filters.reduce(
    (filteredPullRequests, filter) => {
      return filteredPullRequests.filter(filter);
    },
    pullRequests
  );

  if (loading) {
    return (
      <>
        <div>Loading pull requests</div>
        <LinearProgress className="w-full" />
      </>
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

  if (relevantPullRequests.length === 0) {
    return <Callout color="info">No pull requests found.</Callout>;
  }

  return (
    <div className="w-full h-full">
      {relevantPullRequests.map((pullRequest) => {
        return (
          <div className="pb-2 pr-2" key={pullRequest.id}>
            <PullRequestPreview pullRequest={pullRequest} />
          </div>
        );
      })}
    </div>
  );
};

export default List;
