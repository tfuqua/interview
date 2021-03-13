import React from "react";
import { Viewer, Entity } from "resium";
import { Cartesian3, NearFarScalar } from "cesium";
import { useParams } from "react-router-dom";
import {
  ContributorType,
  EnhancedContributorType,
  useNetworkContext,
} from "../network/network";
import LinearProgress from "@material-ui/core/LinearProgress";

const BERMUDA = {
  longitude: -71,
  latitude: 25,
};

const Entities = ({
  enhancedContributors,
}: {
  enhancedContributors: EnhancedContributorType[];
}) => {
  const [forceRender, setForceRender] = React.useState(Math.random());

  React.useEffect(() => {
    const intervalId = setInterval(() => {
      setForceRender(Math.random());
    }, 1000);
    return () => {
      clearInterval(intervalId);
    };
  }, [forceRender]);
  return (
    <>
      {enhancedContributors.map((enhancedContributor) => {
        const longitude = enhancedContributor.enhancedLocation?.longt
          ? parseFloat(enhancedContributor.enhancedLocation?.longt)
          : BERMUDA.longitude;
        const latitude = enhancedContributor.enhancedLocation?.latt
          ? parseFloat(enhancedContributor.enhancedLocation?.latt)
          : BERMUDA.latitude;
        const position = Cartesian3.fromDegrees(longitude, latitude, 100);
        return (
          <Entity
            key={enhancedContributor.id}
            description={enhancedContributor.login}
            name={enhancedContributor.login}
            position={position}
            billboard={{
              eyeOffset: new Cartesian3(
                0,
                0,
                Math.random() * enhancedContributors.length
              ),
              image: enhancedContributor.avatar_url,
              width: 32,
              height: 32,
              scaleByDistance: new NearFarScalar(1.5e2, 6, 8.0e6, 1),
            }}
          ></Entity>
        );
      })}
    </>
  );
};

const EnhancedContributors = ({
  contributors,
}: {
  contributors: ContributorType[];
}) => {
  const network = useNetworkContext();
  const [loading, setLoading] = React.useState(true);
  const [specialMessage, setSpecialMessage] = React.useState(
    `let's see if we can make this more interesting`
  );
  const [enhancedContributors, setEnhancedContributors] = React.useState(
    [] as EnhancedContributorType[]
  );

  React.useEffect(() => {
    if (loading) {
      const timeoutId = setTimeout(() => {
        setSpecialMessage(
          `${
            contributors[
              Math.max(
                0,
                parseInt(((contributors.length - 1) * Math.random()).toString())
              )
            ].login
          } sure is helpful`
        );
      }, 1000);
      return () => {
        clearTimeout(timeoutId);
      };
    }
  }, [specialMessage, contributors, loading]);

  React.useEffect(() => {
    const usernames = contributors
      .map((contributor) => contributor.login)
      .filter((username) => Boolean(username)) as string[];
    network.fetchEnhancedContributors({
      usernames,
      setLoading,
      setEnhancedContributors,
    });
  }, [contributors, network]);

  if (loading) {
    return (
      <div>
        {specialMessage} <LinearProgress />
      </div>
    );
  }
  return (
    <Viewer className="w-full h-full">
      <Entities enhancedContributors={enhancedContributors} />
    </Viewer>
  );
};

export const Contributors = () => {
  const network = useNetworkContext();
  const { repo, owner } = useParams<{
    repo: string;
    owner: string;
  }>();
  const [contributors, setContributors] = React.useState(
    [] as ContributorType[]
  );
  const [loading, setLoading] = React.useState(true);
  React.useEffect(() => {
    network.fetchRepoContributors({
      owner,
      repo,
      setLoading,
      setContributors,
    });
  }, [repo, owner, network]);

  if (loading) {
    return (
      <>
        <div>Looking up contributors</div>
        <LinearProgress className="w-full" />
      </>
    );
  }
  return <EnhancedContributors contributors={contributors.slice(0, 10)} />;
};
