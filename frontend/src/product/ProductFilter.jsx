import { Box } from "@material-ui/core";
import AppBar from "@material-ui/core/AppBar";
import IconButton from "@material-ui/core/IconButton";
import InputBase from "@material-ui/core/InputBase";
import { makeStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import SearchIcon from "@material-ui/icons/Search";
import React, { useCallback, useEffect, useState } from "react";

const useStyles = makeStyles((theme) => ({
  title: {
    flexGrow: 1,
    display: "block",
  },
  input: {
    flexGrow: 1,
    marginLeft: theme.spacing(2),
  },
  iconButton: {
    padding: theme.spacing(2),
  },
  box: {
    display: "flex",
    borderRadius: 4,
    flexGrow: 1,
    backgroundColor: theme.palette.grey[100],
  },
}));

export const ProductFilter = ({ filter, handleChangeFiltering }) => {
  const classes = useStyles();
  const [search, setSearch] = useState("");

  const handleChange = useCallback((e) => {
    setSearch(e.target.value);
  }, []);

  const onKeyPress = useCallback(
    (e) => {
      if (e.key === "Enter") {
        handleChangeFiltering({ searchValue: search });
      }
    },
    [handleChangeFiltering, search]
  );

  useEffect(
    () => {
      if (filter && filter.searchValue) {
        setSearch(filter.searchValue);
      }
    },
    [filter]
  );

  return (
    <AppBar position="fixed" color="primary">
      <Toolbar>
        <Typography className={classes.title} variant="h6" noWrap>
          Product Store
        </Typography>
        <Box className={classes.box}>
          <InputBase
            type="search"
            className={classes.input}
            value={search}
            placeholder="Search Products"
            onChange={handleChange}
            onKeyPress={onKeyPress}
          />

          <IconButton
            type="submit"
            className={classes.iconButton}
            onClick={() => {
              handleChangeFiltering({ searchValue: search });
            }}
          >
            <SearchIcon />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
};
