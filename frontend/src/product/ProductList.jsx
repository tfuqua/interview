import { Grid, makeStyles } from "@material-ui/core";
import React, { useCallback, useRef } from "react";
import { ProductCard } from "./ProductCard";

function hasMoreItems({ pageNumber, limit, totalCount }) {
  return totalCount > (pageNumber + 1) * limit;
}
const useStyles = makeStyles((theme) => ({
  root: {
    marginTop: theme.spacing(8),
  },
}));
export const ProductList = ({
  data,
  handleChangePagination,
  handleClickFilterLink,
}) => {
  const classes = useStyles();
  const observer = useRef();
  const lastProductElementRef = useCallback(
    (node) => {
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (
          data &&
          entries[0].isIntersecting &&
          hasMoreItems(data.pagination)
        ) {
          handleChangePagination({
            limit: data.pagination.limit,
            pageNumber: data.pagination.pageNumber + 1,
          });
        }
      });
      if (node) observer.current.observe(node);
    },
    [data]
  );

  return (
    <>
      {data && (
        <Grid
          container
          direction="row"
          alignItems="center"
          justify="center"
          spacing={3}
          className={classes.root}
        >
          {data.data.map((product, index) => {
            if (data.data.length === index + 1) {
              return (
                <Grid item ref={lastProductElementRef} key={product.id}>
                  <ProductCard
                    product={product}
                    handleClickFilterLink={handleClickFilterLink}
                  />
                </Grid>
              );
            } else {
              return (
                <Grid item key={product.id}>
                  <ProductCard
                    product={product}
                    handleClickFilterLink={handleClickFilterLink}
                  />
                </Grid>
              );
            }
          })}
        </Grid>
      )}
    </>
  );
};
