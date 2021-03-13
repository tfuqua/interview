import React from "react";
import { useParams } from "react-router-dom";
import Markdown from "react-markdown";
import Link from "@material-ui/core/Link";
import { useScrollToTopOnMount } from "../../hooks/hooks";
import {
  DetailedPullRequestType,
  useNetworkContext,
} from "../../network/network";
import LinearProgress from "@material-ui/core/LinearProgress";
import { Divider } from "../../divider/divider";
import formatRelative from "date-fns/formatRelative";
import { Comments } from "../comments/comments";
import { classNames } from "../../stylings/stylings";
import { Callout } from "../../callout/callout";

const PullRequestActions = ({
  pullRequest,
}: {
  pullRequest: DetailedPullRequestType;
}) => {
  if (pullRequest.head.repo === null) {
    return <div>Not able to provide more actions for this pull request</div>;
  }

  const githubUrl = `${pullRequest.head.repo.html_url}/tree/${pullRequest.head.ref}`;
  const sandboxUrl = githubUrl.replace("github.com", "githubbox.com");
  return (
    <>
      <div className="flex flex-row items-center">
        <Link href={githubUrl} className="border-2 p-2 block" target="_blank">
          View code on github
        </Link>
        <div className="px-2"></div>
        <Link
          href={pullRequest.html_url}
          className="border-2 p-2 block"
          target="_blank"
        >
          View pull request on github
        </Link>
        <div className="px-2"></div>
        <Link href={sandboxUrl} className="border-2 p-2 block" target="_blank">
          View in code sandbox
        </Link>
      </div>
    </>
  );
};

// https://codesandbox.io/s/github/remarkjs/react-markdown?utm_medium=plugin

const PullRequest = () => {
  const network = useNetworkContext();

  useScrollToTopOnMount();
  const { repo, owner, pull_number } = useParams<{
    repo: string;
    owner: string;
    pull_number: string;
  }>();
  const [pullRequest, setPullRequest] = React.useState(
    null as null | DetailedPullRequestType
  );
  const [loading, setLoading] = React.useState(true);
  React.useEffect(() => {
    network.fetchPullRequest({
      repo,
      owner,
      pull_number,
      setPullRequest,
      setLoading,
    });
  }, [repo, owner, pull_number, network]);

  if (loading) {
    return (
      <>
        <div>Looking up pull request details</div>
        <LinearProgress className="w-full" />
      </>
    );
  }

  if (pullRequest) {
    const stateClassName =
      pullRequest.state === "closed" ? "bg-red-400" : "bg-green-400";
    return (
      <div>
        <div className="flex flex-row flex-wrap items-center text-xl py-2 pl-9 ">
          <div className="flex flex-row flex-nowrap items-center mb-1">
            <div className="">{pullRequest.title}</div>
            <div
              className={`${stateClassName} leading-7 text-white w-auto inline px-3 rounded-full text-sm ml-2 self-center flex flex-column items-center`}
            >
              {pullRequest.state}
            </div>
          </div>
          <div className="flex flex-row flex-nowrap items-center text-base">
            <Divider variant="vertical" size="small" />
            <PullRequestActions pullRequest={pullRequest} />
          </div>
        </div>
        <Divider color="accent" size="small" />
        <div className="flex flex-row flex-nowrap items-start pl-9">
          <img
            src={pullRequest.user?.avatar_url}
            className={classNames.avatar}
            alt="avatar"
          />
          <div className="">
            <div className="">
              {pullRequest.user?.login}{" "}
              <span className="opacity-50 ">
                {formatRelative(new Date(pullRequest.created_at), new Date())}
              </span>
            </div>
            {pullRequest.body ? (
              <div>
                <Markdown allowDangerousHtml>{pullRequest.body}</Markdown>
              </div>
            ) : (
              <div>
                <span className="italic">No summary provided</span>
              </div>
            )}
          </div>
        </div>
        <div className="pl-9">
          <Comments />
        </div>
      </div>
    );
  }
  return <Callout color="error">Pull request not found.</Callout>;
};

export default PullRequest;
