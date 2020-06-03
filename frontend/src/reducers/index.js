import {combineReducers, createStore} from 'redux'
import userReducer from './user.reducer';

const reducer = combineReducers({
    userReducer: userReducer
});

const store = createStore(
    reducer
);

export default store;