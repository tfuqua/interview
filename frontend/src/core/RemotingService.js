import axios from 'axios';

axios.defaults.baseURL = "http://localhost:8080/api/";

const noop = () => {
}

const get = (url, params, successCallback = noop, failCallback = noop) => {
    axios.get(url, {params})
        .then((response) => {
            successCallback(response.data);
        }, (error) => {
            failCallback(error);
        });
    ;
};

export default {get};
