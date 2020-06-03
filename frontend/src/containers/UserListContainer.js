import React from 'react'
import {Component} from 'react';
import UserComponent from '../components/UserComponent';
import {connect} from 'react-redux';
import * as userAction from "../actions/user.action";

class UserListContainer extends Component {

    componentDidMount() {
        const {dispatch} = this.props;
        userAction.getUsers(dispatch);
    }

    render() {
        const {users} = this.props;
        if (users.length === 0)
            return null;

        return (
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Website</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((entry, index) => <UserComponent key={index} user={entry}/>)}
                </tbody>
            </table>)
    }
}

const mapStateToProps = state => {
    const {users} = state.userReducer;
    return {users: users};
};

export default connect(mapStateToProps)(UserListContainer);