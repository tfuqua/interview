import React, { Component } from 'react';
import {
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  TablePagination,
  Typography,
} from '@material-ui/core';

export default class AnimalTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      animals: [],
      rowsPerPage: 5,
      page: 0,
    }

    this.handleChangePage = this.handleChangePage.bind(this);
    this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
  }

  componentDidMount() {
    fetch('https://cat-fact.herokuapp.com/facts')
      .then(response => response.json())
      .then((response) => this.setState({
        animals: response.all
      }))
  }

  handleChangePage = (event, newPage) => {
    this.setState({
      page: newPage
    });
  };

  handleChangeRowsPerPage = event => {
    this.setState({
      rowsPerPage: parseInt(event.target.value, 10),
      page: 0,
    })  
  };

  render() {
    const { page, rowsPerPage, animals } = this.state;
    return animals.length > 0 ? (
      <>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>#</TableCell>
              <TableCell>Text</TableCell>
              <TableCell>User ID</TableCell>
              <TableCell>User Name</TableCell>
              <TableCell>Upvotes</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {animals
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((animal, index) => {
                const rawData = animal;
                return (
                  <TableRow key={rawData._id}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell>{rawData.text}</TableCell>
                    <TableCell>{rawData.user._id}</TableCell>
                    <TableCell>{rawData.user.name.first + ' ' +   rawData.user.name.last}</TableCell>
                    <TableCell>{rawData.upvotes}</TableCell>
                  </TableRow>
                )
            })}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={animals.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={this.handleChangePage}
          onChangeRowsPerPage={this.handleChangeRowsPerPage}
        />
      </>
      ) : (
        <Typography variant="h4">
          Loading ...
        </Typography>
    )
  }
}