import React from 'react'
import { makeStyles, withStyles } from '@material-ui/core/styles'
import {
    Avatar,
    Hidden,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TableContainer
} from '@material-ui/core'

const StyledTableCell = withStyles(theme => ({
    head: {
        backgroundColor: theme.palette.secondary.main,
        color: theme.palette.common.white
    },
    body: {
        fontSize: 14
    }
}))(TableCell)

const StyledTableRow = withStyles(theme => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.background.default
        }
    }
}))(TableRow)

const useStyles = makeStyles(theme => ({
    root: {
        margin: theme.spacing(1),
        width: 'initial'
    }
}))

export default function({ repos })
{
    const classes = useStyles()

    return (
        <TableContainer className={classes.root} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <StyledTableCell>Avatar</StyledTableCell>
                        <StyledTableCell>Repo</StyledTableCell>
                        <StyledTableCell>Language</StyledTableCell>
                        <StyledTableCell align='right'>Stars</StyledTableCell>
                        <Hidden smDown>
                            <StyledTableCell>Description</StyledTableCell>
                        </Hidden>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {repos.map(repo => (
                        <StyledTableRow key={repo.name}>
                            <StyledTableCell>
                                <Avatar alt={repo.name} src={repo.avatarUrl} />
                            </StyledTableCell>
                            <StyledTableCell>{repo.name}</StyledTableCell>
                            <StyledTableCell>{repo.language}</StyledTableCell>
                            <StyledTableCell align='right'>{repo.stars}</StyledTableCell>
                            <Hidden smDown>
                                <StyledTableCell>{repo.description}</StyledTableCell>
                            </Hidden>
                        </StyledTableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
