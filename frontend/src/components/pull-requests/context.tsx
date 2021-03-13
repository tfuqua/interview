import React from "react";
import {
  AuthenticatedUserType,
  LOCAL_STORAGE_AUTH_KEY,
  useNetworkContext,
} from "../network/network";

const AppContext = React.createContext({
  loading: false,
  oAuth: "",
  setOAuth: (oAuth: string) => {},
  authenticatedUser: null as null | AuthenticatedUserType,
  setAuthenticatedUser: (user: AuthenticatedUserType | null) => {},
});

type AppContextType = {
  children: React.ReactNode;
};

export const useAppContext = () => {
  const appContext = React.useContext(AppContext);

  return appContext;
};

export const AppContextInstance = ({ children }: AppContextType) => {
  const network = useNetworkContext();
  const [loading, setLoading] = React.useState(false);
  const [oAuth, setOAuth] = React.useState(
    localStorage.getItem(LOCAL_STORAGE_AUTH_KEY) || ""
  );
  const [authenticatedUser, setAuthenticatedUser] = React.useState(
    null as null | AuthenticatedUserType
  );

  React.useEffect(() => {
    localStorage.setItem(LOCAL_STORAGE_AUTH_KEY, oAuth);
    network.regenerateOctokit();
    if (oAuth) {
      network.fetchAuthenticatedUser({ setLoading, setAuthenticatedUser });
    } else {
      setAuthenticatedUser(null);
    }
  }, [oAuth, network]);
  return (
    <AppContext.Provider
      value={{
        loading,
        oAuth,
        setOAuth,
        authenticatedUser,
        setAuthenticatedUser,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};
