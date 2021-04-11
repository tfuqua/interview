import React, { useState, useEffect } from 'react';
import {Loading} from '../loading/loading.component'
import './breed.styles.css';

export const Breed = props => {

    const [imgUrl, setImgUrl] = useState();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`https://dog.ceo/api/breed/${props.breed}/images/random`)
        .then(response => response.json())
        .then(breeds => setImgUrl(breeds['message']), setLoading(false));
    }, []);

    return <div className='breed-container'>
            {loading ? <Loading/> : <img className='image' alt="breed" src = {imgUrl}/>}
            <p className='text'>{props.breed}</p>
        </div>;
}