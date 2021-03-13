import TextField from "@material-ui/core/TextField";
import React from "react";
import { Divider } from "../divider/divider";
import { classNames } from "../stylings/stylings";
import { Callout } from "../callout/callout";
import { useAppContext } from "../pull-requests/context";
import LinearProgress from "@material-ui/core/LinearProgress";
//1edbe0810cb87b6c4b7897ddc7188e696458394a

export const Auth = () => {
  const { oAuth, setOAuth, loading, authenticatedUser } = useAppContext();
  return (
    <div>
      <Callout color="info">
        The github API allows more usage for authenticated users, so while this
        isn't required, it may make your experience better. The search for repo
        API is particularly limited, with only 10 searches allowed per minute.
        <Divider size="smallest" color="info"></Divider>
        <div className="flex flex-row items-center">
          <a
            className={`${classNames.card}`}
            href="https://github.com/settings/tokens/new"
            target="_blank"
            rel="noreferrer"
          >
            Generate new token
          </a>
          <a
            className={`${classNames.card}`}
            href="https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token#creating-a-token"
            target="_blank"
            rel="noreferrer"
          >
            Read more about github and oauth
          </a>
        </div>
      </Callout>
      <Divider size="smallest" className="my-4"></Divider>
      <TextField
        variant="outlined"
        fullWidth
        name="oauth"
        label="OAuth token"
        placeholder="Enter a github oAuth token here"
        value={oAuth}
        onChange={(e) => {
          setOAuth(e.target.value);
        }}
        autoFocus
      />
      <Divider size="smallest" className="my-4" />
      <div>
        {(() => {
          if (loading) {
            return (
              <>
                Let's see who you are
                <LinearProgress />
              </>
            );
          }
          if (authenticatedUser) {
            return (
              <Callout color="success">
                Hi there {authenticatedUser.login}
              </Callout>
            );
          }
          if (oAuth) {
            return (
              <Callout color="error">
                Hmm, I can't find anyone associated with that token. Could you
                double check it?
              </Callout>
            );
          }
        })()}
      </div>
      <div></div>
    </div>
  );
};
