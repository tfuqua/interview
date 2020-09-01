import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import axios from 'axios';

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.timeout = 10000

axios.interceptors.request.use(request => {
    for (const [key, value] of Object.entries(request.params)) {
        if (value == null || value === "") {
            delete request.params[key];
        }
    }
    return request;
});

axios.interceptors.request.use(function (config) {
    document.body.classList.add('loading-indicator');
    return config
}, function (error) {
    return Promise.reject(error);
});

axios.interceptors.response.use(function (response) {
    document.body.classList.remove('loading-indicator');
    return response;
}, function (error) {
    return Promise.reject(error);
});

ReactDOM.render(<App />, document.getElementById('root'));

