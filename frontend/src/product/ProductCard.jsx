import {
  Box,
  Breadcrumbs,
  Card,
  CardContent,
  CardHeader,
  CardMedia,
  Link,
  makeStyles,
  Typography,
} from "@material-ui/core";
import React from "react";

const useStyles = makeStyles((theme) => ({
  card: {
    marginTop: theme.spacing(2),
    height: 700,
    width: 700,
  },
  img: {
    maxHeight: "100%",
  },
  imgContainer: {
    height: 400,
  },
}));

export const ProductCard = ({ product, handleClickFilterLink }) => {
  const classes = useStyles();

  return (
    <Card className={classes.card} variant="outlined">
      <Box maxHeight={100}>
        <CardHeader
          title={product.name}
          subheader={
            <Breadcrumbs aria-label="breadcrumb">
              <Link
                color="inherit"
                href="#"
                onClick={() =>
                  handleClickFilterLink({ searchValue: product.category })
                }
              >
                {product.category}
              </Link>
              <Link
                color="inherit"
                href="#"
                onClick={() =>
                  handleClickFilterLink({ searchValue: product.subCategory })
                }
              >
                {product.subCategory}
              </Link>
              <Link
                color="inherit"
                href="#"
                onClick={() =>
                  handleClickFilterLink({ searchValue: product.manufacturer })
                }
              >
                {product.manufacturer}
              </Link>
            </Breadcrumbs>
          }
          titleTypographyProps={{ variant: "h6" }}
          subheaderTypographyProps={{ variant: "body2" }}
        />
      </Box>

      <CardContent>
        <Box className={classes.imgContainer}>
          <CardMedia
            className={classes.img}
            component="img"
            src={product.imageUrl}
            title={product.name}
          />
        </Box>
        <Typography component="div" variant="body2" color="textSecondary">
          <Box textOverflow="ellipsis" overflow="hidden" maxHeight={100} mt={2}>
            {product.description}
          </Box>
        </Typography>

        <Typography component="div">
          <Box fontWeight="fontWeightBold" fontSize="h6.fontSize" mt={2}>
            ${product.price}
          </Box>
        </Typography>
      </CardContent>
    </Card>
  );
};
