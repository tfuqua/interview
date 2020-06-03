import {SHOW} from '../constants/action.constants';

const userReducer = (state = {users: []}, action = {}) => {
    if (action.type === SHOW.USER_LIST) {
        return Object.assign({}, state, {
            users: action.users
        });
    }
    return state;
};

export default userReducer;