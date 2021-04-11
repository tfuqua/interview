import React from 'react';
import {Breed} from '../breed/breed.component';

import './breed-list.styles.css';

export const BreedList = props => {
    return <div className='breed-list'>
        {props.breeds.map(breed => 
            <Breed key={breed} breed={breed}/>
        )}
    </div>;
}