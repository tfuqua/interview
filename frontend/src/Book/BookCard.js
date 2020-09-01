import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import React from 'react';
import Moment from 'moment';

const useStyles = makeStyles({
    root: {
        width: 250,
        height: 400,
    },
    bookImg: {
        height: "75%",
        paddingTop: '100%',
    },
    bookTitle: {
        textOverflow: "ellipsis",
        display: "block",
        overflow: "hidden",
        maxHeight: "3.9em",
        lineHeight: "1.3em",
        display: "-webkit-box",
        "-webkit-line-clamp": 3,
        "-webkit-box-orient": "vertical",
        component: "h2",
    },
    bookInfo: {
        color: "textSecondary",
        component: "span",
        paddingTop: 15,
    },
});


const bookCard = (props) => {
    const classes = useStyles();

    const publishDate = Moment(props.book.publishDate).format("DD MMM YYYY");

    return (
        <Card className={classes.root}>
            <CardActionArea>
                <CardMedia
                    className={classes.bookImg}
                    image={props.book.imageUrl}
                    title={props.book.name}
                />
                <CardContent>
                    <Typography variant="body1" className={classes.bookTitle}>
                        {props.book.name}
                    </Typography>
                    <Typography variant= "body2" className={classes.bookInfo}>
                        <div>
                            <b>Author: </b>{props.book.authorName} {props.book.authorSurname}
                        </div>
                        <div>
                            <b>Publish Date: </b>{publishDate}
                        </div>
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default bookCard;