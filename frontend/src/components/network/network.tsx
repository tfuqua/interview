import React from "react";
import { Octokit, RestEndpointMethodTypes } from "@octokit/rest";
export type PullRequestType = RestEndpointMethodTypes["pulls"]["list"]["response"]["data"][0];
export type DetailedPullRequestType = RestEndpointMethodTypes["pulls"]["get"]["response"]["data"];
export type RepoType = RestEndpointMethodTypes["search"]["repos"]["response"]["data"]["items"][0];
export type PullRequestCommentType = RestEndpointMethodTypes["pulls"]["listReviewComments"]["response"]["data"][0];
export type ContributorType = RestEndpointMethodTypes["repos"]["listContributors"]["response"]["data"][0];
export type AuthenticatedUserType = RestEndpointMethodTypes["users"]["getAuthenticated"]["response"]["data"];
export type GeoCodeXYZType =
  | {
      latt: string;
      longt: string;
      error?: undefined;
    }
  | {
      latt?: undefined;
      longt?: undefined;
      error: {
        description: string;
        code: string;
      };
    };
export type EnhancedContributorType = RestEndpointMethodTypes["users"]["getByUsername"]["response"]["data"] & {
  enhancedLocation?: GeoCodeXYZType;
};

export const LOCAL_STORAGE_AUTH_KEY = "oAuth";
const generateOctokit = () => {
  return new Octokit({
    auth: localStorage.getItem(LOCAL_STORAGE_AUTH_KEY),
  });
};
// Don't see a way to update auth on the fly, so we'll swap this out if they update token mid-usage
let octokit = generateOctokit();
//@ts-ignore
window.ockokit = octokit;

export type FilterType = (pullRequest: PullRequestType) => boolean;

export const Filters = {
  pullRequests: {
    bots: (pullRequest: PullRequestType) => pullRequest.user?.type !== "Bot",
    open: (pullRequest: PullRequestType) => pullRequest.state === "closed",
    closed: (pullRequest: PullRequestType) => pullRequest.state !== "closed",
  } as {
    bots: FilterType;
    open: FilterType;
    closed: FilterType;
  },
};

const sleep = (ms: number) => {
  return new Promise((resolve) => setTimeout(resolve, ms));
};

export type NetworkPropsType = {
  network?: typeof Network;
};

/**
 * I like to separate network effects out as much as possible since that tends to simplify testing (mocking).
 */
export const Network = {
  regenerateOctokit: () => {
    octokit = generateOctokit();
  },
  fetchAuthenticatedUser: async ({
    setAuthenticatedUser,
    setLoading,
  }: {
    setAuthenticatedUser: React.Dispatch<
      React.SetStateAction<null | AuthenticatedUserType>
    >;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  }) => {
    setLoading(true);
    Network.regenerateOctokit();
    try {
      const response = await octokit.users.getAuthenticated({
        cacheBust: Date.now(), // otherwise we can get stuck authenticated when we aren't :(
      });
      setAuthenticatedUser(response.data);
    } catch (err) {
      setAuthenticatedUser(null);
    } finally {
      setLoading(false);
    }
  },
  fetchRepos: async ({
    q,
    setRepos,
    setLoading,
    setError = () => {},
    controller,
  }: {
    q: string;
    setRepos: React.Dispatch<React.SetStateAction<RepoType[]>>;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
    setError?: React.Dispatch<React.SetStateAction<null | string>>;
    controller?: AbortController;
  }) => {
    if (q === "") {
      return;
    }
    setLoading(true);
    try {
      const searchResponse = await octokit.search.repos({
        q,
        request: {
          ...(controller
            ? {
                signal: controller.signal,
              }
            : {}),
        },
      });
      setRepos(searchResponse.data.items);
      setError(null);
    } catch (err) {
      console.log(err);
      setError(err);
      setRepos([]);
    } finally {
      setLoading(false);
    }
  },
  fetchEnhancedContributors: async ({
    usernames,
    setEnhancedContributors,
    setLoading,
  }: {
    usernames: string[];
    setEnhancedContributors: React.Dispatch<
      React.SetStateAction<EnhancedContributorType[]>
    >;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  }) => {
    setLoading(true);

    try {
      const enhancedContributors = [] as EnhancedContributorType[];
      // a map would be better using Promise.all (allowing parallal fetches), but unfortunately we need to rate limit the geocode api :(
      for (let index = 0; index < usernames.length; index++) {
        const username = usernames[index];
        const enhancedContributor = await octokit.users.getByUsername({
          username,
        });
        if (enhancedContributor.data.location) {
          let enhancedLocation = (await (
            await fetch(
              `https://geocode.xyz/${encodeURI(
                enhancedContributor.data.location
              )}?json=1`
            )
          ).json()) as GeoCodeXYZType;
          if (enhancedLocation.error) {
            // fallback to first word if entire location fails (I saw Scottsdale, AZ fail, but not Scottsdale ..)
            await sleep(50);
            enhancedLocation = (await (
              await fetch(
                `https://geocode.xyz/${encodeURI(
                  enhancedContributor.data.location.split(",")[0]
                )}?json=1`
              )
            ).json()) as GeoCodeXYZType;
          }

          enhancedContributors.push({
            ...enhancedContributor.data,
            enhancedLocation,
          });
          await sleep(50); // otherwise the geocode api will erroneously say no matches, this reduces that possibility
        } else {
          enhancedContributors.push(enhancedContributor.data);
        }
      }
      setEnhancedContributors(enhancedContributors);
    } catch (err) {
    } finally {
      setLoading(false);
    }
  },
  fetchRepoContributors: async ({
    owner,
    repo,
    setContributors,
    setLoading,
  }: {
    owner: string;
    repo: string;
    setContributors: React.Dispatch<React.SetStateAction<ContributorType[]>>;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  }) => {
    setLoading(true);
    const contributors = await octokit.repos.listContributors({ owner, repo });
    setContributors(contributors.data);
    setLoading(false);
  },
  fetchPullRequestComments: async ({
    owner,
    repo,
    pull_number,
    setComments,
    setLoading,
  }: {
    owner: string;
    repo: string;
    pull_number: string;
    setComments: React.Dispatch<React.SetStateAction<PullRequestCommentType[]>>;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  }) => {
    setLoading(true);
    try {
      const pr = await octokit.pulls.listReviewComments({
        owner,
        repo,
        pull_number: parseInt(pull_number),
      });
      setComments(pr.data);
    } catch (err) {
    } finally {
      setLoading(false);
    }
  },
  fetchPullRequest: async ({
    owner,
    repo,
    pull_number,
    setPullRequest,
    setLoading,
  }: {
    owner: string;
    repo: string;
    pull_number: string;
    setPullRequest: React.Dispatch<
      React.SetStateAction<null | DetailedPullRequestType>
    >;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
  }) => {
    setLoading(true);

    try {
      const pr = await octokit.pulls.get({
        owner,
        repo,
        pull_number: parseInt(pull_number),
      });

      setPullRequest(pr.data);
    } catch (err) {
    } finally {
      setLoading(false);
    }
  },
  fetchPullRequests: async ({
    owner,
    repo,
    setPullRequests,
    setLoading,
    setError = () => {},
  }: {
    owner: string;
    repo: string;
    setPullRequests: React.Dispatch<React.SetStateAction<PullRequestType[]>>;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
    setError?: React.Dispatch<React.SetStateAction<null | string>>;
  }) => {
    setLoading(true);
    try {
      const prs = await octokit.pulls.list({
        owner,
        repo,
        state: "all",
      });
      setPullRequests(prs.data);
      setError(null);
    } catch (err) {
      setError(err);
      console.log(err);
    } finally {
      setLoading(false);
    }
  },
};

type NetworkContextType = {
  children: React.ReactNode;
};

const NetworkContext = React.createContext(Network);

export const useNetworkContext = () => {
  const networkContext = React.useContext(NetworkContext);

  return networkContext;
};

export const NetworkContextInstance = ({
  children,
  network = Network,
}: NetworkContextType & NetworkPropsType) => {
  return (
    <NetworkContext.Provider value={network}>
      {children}
    </NetworkContext.Provider>
  );
};
