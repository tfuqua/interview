import React from "react";

export const useScrollToTopOnMount = () => {
  React.useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
};
