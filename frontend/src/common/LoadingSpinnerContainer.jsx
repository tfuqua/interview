import { Backdrop, CircularProgress, makeStyles } from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
  backdrop: {
    backgroundColor: "#ffffff80",
    zIndex: theme.zIndex.drawer + 1,
  },
}));

export const LoadingSpinnerContainer = ({ loading, children }) => {
  const classes = useStyles();

  return (
    <div>
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
      {children}
    </div>
  );
};
