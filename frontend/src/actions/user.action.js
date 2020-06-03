import * as userService from '../services/user.service';
import {SHOW} from '../constants/action.constants';

export const showUserList = (users) => ({
    type: SHOW.USER_LIST,
    users,
});

export const getUsers = (dispatch) => {
    userService.getUsers().then(data => {
        dispatch(showUserList(data));
    })
};