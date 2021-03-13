import { Network } from "../src/components/network/network";

export const sleep = (ms: number) => {
  return new Promise((resolve) => setTimeout(resolve, ms));
};

export const generateFakeNetwork = () => {
  return {
    regenerateOctokit: async () => {},
    fetchRepos: async () => {},
    fetchRepoContributors: async () => {},
    fetchAuthenticatedUser: async () => {},
    fetchEnhancedContributors: async () => {},
    fetchPullRequest: async () => {},
    fetchPullRequestComments: async () => {},
    fetchPullRequests: async () => {},
  } as typeof Network;
};
