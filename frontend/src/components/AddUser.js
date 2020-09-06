import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Form, Formik, Field, ErrorMessage} from 'formik'
import * as Yup from 'yup'
import './addUser.css'

let options = {
    headers: {
        'Content-Type': 'application/json',
    }
};

let initialValues = {
    emailId: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    website: ""
};

const validationSchema = Yup.object({
    emailId: Yup
        .string()
        .required('Required')
        .email('Email must have proper format'),
    firstName: Yup.string().required('Required'),
    lastName: Yup.string().required('Required'),
    phoneNumber: Yup.string().required('Required'),
    website: Yup.string().required('Required')
});

function AddUser(props) {

    const [user,setUser] = useState(initialValues);

    const onSubmit = values => {
        let data = {
            "emailId": values.emailId,
            "firstName": values.firstName,
            "lastName": values.lastName,
            "phoneNumber": values.phoneNumber,
            "website": values.website
        };


        console.log(data)

        if(user.id){
            console.log("Going here")
            axios.put('http://localhost:8080/users/' + user.id, data, options).then((res) => {
                console.log(res.data);
                props.onSubmit();
            }).catch((err) => {
                console.log(err);
            })
        }else{
            axios.post('http://localhost:8080/users/', data, options).then((res) => {
                console.log(res.data);
                props.onSubmit();
            }).catch((err) => {
                console.log(data);
                console.log(err);
            })
        }
    };

    useEffect(() =>{
        setUser(props.user)
    }, [props]);

    useEffect(() => {
        if(props.user){
            initialValues = props.user;
        }
    }, []);

    return (
        <div className='modalContainer'>
            <div className='primary-color'>
                <h2 className='modalTitle'> { props.title }</h2>
            </div>
            <Formik
                enableReinitialize={true}
                initialValues={user}
                validationSchema={validationSchema}
                onSubmit={ onSubmit }>
                <Form className='container'>
                    <div className='formControl'>
                        {/*<label htmlFor='email'>Email</label>*/}
                        <Field className='formikClass' name='emailId' placeholder='Email' type='email'/>
                        <ErrorMessage name='emailId'>
                            {errorMsg => <div className='error'>{errorMsg}</div>}
                        </ErrorMessage>
                    </div>
                    <div className='formControl'>
                        {/*<label htmlFor='firstName'>First Name</label>*/}
                        <Field className='formikClass' name='firstName' placeholder='First Name'
                               type='text'/>
                        <ErrorMessage name='firstName'>
                                {errorMsg => <div className='error'>{errorMsg}</div>}
                        </ErrorMessage>
                    </div>

                    <div className='formControl'>
                        {/*<label htmlFor='lastName'>Last Name</label>*/}
                        <Field className='formikClass' name='lastName' type='text' placeholder='Last Name'/>
                        <ErrorMessage name='lastName'>
                            {errorMsg => <div className='error'>{errorMsg}</div>}
                        </ErrorMessage>
                    </div>

                    <div className='formControl'>
                        {/*<label htmlFor='phoneNumber'>Phone Number</label>*/}
                        <Field className='formikClass' name='phoneNumber' type='text' placeholder='Phone Number'/>
                        <ErrorMessage name='phoneNumber'>
                            {errorMsg => <div className='error'>{errorMsg}</div>}
                        </ErrorMessage>
                    </div>

                    <div className='formControl'>
                        {/*<label htmlFor='website'>Website</label>*/}
                        <Field className='formikClass' name='website' type='text' placeholder='Website'/>
                        <ErrorMessage name='website'>
                            {errorMsg => <div className='error'>{errorMsg}</div>}
                        </ErrorMessage>
                    </div>
                    <div className='btnGroup'>
                        <button className='btn-cancel btn light-color' type='submit' onClick={props.onClosePopup}>
                            CANCEL
                        </button>
                        <button className='btn-submit btn secondary-color' type='submit'>
                            SUBMIT
                        </button>
                    </div>
                </Form>
            </Formik>
        </div>
    );
}

export default AddUser
