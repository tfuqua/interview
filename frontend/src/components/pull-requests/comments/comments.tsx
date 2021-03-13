import { formatRelative } from "date-fns";
import React from "react";
import { useParams } from "react-router-dom";
import Markdown from "react-markdown";
import {
  PullRequestCommentType,
  useNetworkContext,
} from "../../network/network";
import { classNames } from "../../stylings/stylings";
import LinearProgress from "@material-ui/core/LinearProgress";

const Comment = ({ comment }: { comment: PullRequestCommentType }) => {
  return (
    <div className="flex flex-row flex-nowrap items-start">
      <img
        src={comment.user?.avatar_url}
        className={classNames.avatar}
        alt="avatar"
      />
      <div className="">
        <div className="flex flex-row flex-nowrap items-center">
          {comment.user?.login}{" "}
          <span className="opacity-50 px-2">
            {formatRelative(new Date(comment.created_at), new Date())}
          </span>
          <a
            href={comment.html_url}
            className={`w-auto opacity-50`}
            target="_blank"
            rel="noreferrer"
          >
            (context)
          </a>
        </div>
        {comment.body ? (
          <div>
            <Markdown allowDangerousHtml>{comment.body}</Markdown>
          </div>
        ) : (
          <div>
            <span className="italic">No comment text provided</span>
          </div>
        )}
      </div>
    </div>
  );
};

export const Comments = () => {
  const network = useNetworkContext();

  const { repo, owner, pull_number } = useParams<{
    repo: string;
    owner: string;
    pull_number: string;
  }>();
  const [comments, setComments] = React.useState(
    [] as PullRequestCommentType[]
  );
  const [loading, setLoading] = React.useState(true);
  React.useEffect(() => {
    network.fetchPullRequestComments({
      owner,
      repo,
      pull_number,
      setLoading,
      setComments,
    });
  }, [repo, owner, pull_number, network]);

  if (loading) {
    return (
      <>
        <div>Loading comments</div>
        <LinearProgress className="w-full" />
      </>
    );
  }
  return (
    <div>
      {comments.map((comment) => {
        return <Comment key={comment.id} comment={comment} />;
      })}
    </div>
  );
};
