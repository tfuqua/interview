import React, {Component} from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import TablePagination from "@material-ui/core/TablePagination";

class Breweries extends Component {
    constructor(props) {
        super(props);
        this.props = props;

        this.state = {
            rowsPerPage: 5,
            page: 0
        };

        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
    }

    handleChangePage(event, newPage) {
        this.setState({
            ...this.state,
            page: newPage
        });
    }

    handleChangeRowsPerPage(event) {
        this.setState({
            ...this.state,
            rowsPerPage: parseInt(event.target.value, 10),
            page: 0
        });
    }

    render() {
        return (
            <div>
                <TableContainer className="breweries_table_container" component={Paper}>
                    <Table className="breweries_table" aria-label="simple table">
                        <TableHead className="table_header">
                            <TableRow className="table_row">
                                <TableCell className="first_column">Brewery Name</TableCell>
                                <TableCell className="column" align="right">Brewery Type</TableCell>
                                <TableCell className="column" align="right">Brewery Street</TableCell>
                                <TableCell className="column" align="right">Brewery City</TableCell>
                                <TableCell className="column" align="right">Brewery State</TableCell>
                                <TableCell className="column" align="right">Brewery Postal Code</TableCell>
                                <TableCell className="column" align="right">Brewery Country</TableCell>
                                <TableCell className="column" align="right">Brewery Longitude</TableCell>
                                <TableCell className="column" align="right">Brewery Latitude</TableCell>
                                <TableCell className="column" align="right">Brewery Phone</TableCell>
                                <TableCell className="column" align="right">Brewery Website URL</TableCell>
                                <TableCell className="column" align="right">Brewery Updated At</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody className="table_body">
                            {this.props.breweries.slice(
                                this.state.page * this.state.rowsPerPage,
                                this.state.page * this.state.rowsPerPage + this.state.rowsPerPage
                            ).map(row => (
                                <TableRow className="table_row" key={row.id}>
                                    <TableCell component="th" scope="row">
                                        {row.name}
                                    </TableCell>
                                    <TableCell align="right">{row.brewery_type}</TableCell>
                                    <TableCell align="right">{row.street}</TableCell>
                                    <TableCell align="right">{row.city}</TableCell>
                                    <TableCell align="right">{row.state}</TableCell>
                                    <TableCell align="right">{row.postal_code}</TableCell>
                                    <TableCell align="right">{row.country}</TableCell>
                                    <TableCell align="right">{row.longitude}</TableCell>
                                    <TableCell align="right">{row.latitude}</TableCell>
                                    <TableCell align="right">{row.phone}</TableCell>
                                    <TableCell align="right">{row.website_url}</TableCell>
                                    <TableCell align="right">{row.updated_at}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={this.props.breweries.length}
                    rowsPerPage={this.state.rowsPerPage}
                    page={this.state.page}
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </div>
        )
    }
}

export default Breweries;