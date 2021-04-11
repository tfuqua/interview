import React from 'react';
import { LinearProgress } from '@material-ui/core';
import './loading.styles.css';

export const Loading = () => {
    return (
        <div className='root'>
            <LinearProgress color="primary" />
        </div>
    );
};
