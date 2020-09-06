import React, {useEffect, useRef, useState} from 'react'
import {makeStyles} from "@material-ui/core/styles";
import DeleteTwoToneIcon from "@material-ui/icons/DeleteTwoTone";
import EditTwoToneIcon from '@material-ui/icons/EditTwoTone'
import Grid from '@material-ui/core/Grid';
import Paper from "@material-ui/core/Paper";
import axios from "axios"
import './dashboard.css'
import Modal from 'react-modal'
import AddUser from "./AddUser";
import Pagination from '@material-ui/lab/Pagination';

Modal.setAppElement('#root');

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        background: '#fff1fd'
    },
    editIcon: {
        color: '#9744be'
    }
}));

const userDefaultData = {
    id:"",
    emailId: "",
    firstName: "",
    lastName: "",
    website: "",
    phoneNumber: ""
};

function Dashboard(props) {

    const [modelIsOpen, setIsOpen] = useState(false);
    const [users, setUsers] = useState([]);
    const [currentUser, setCurrentUser] = useState(userDefaultData);
    const [popUpTitle,setPopUpTitle] = useState("ADD USER");
    const loaded = useRef(false);
    const [pageCount, setPageCount] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);

    let options = {
        headers: {
            'Content-Type': 'application/json',
        },
        params: {
            pageNo: currentPage,
            pageSize: 12
        }
    };

    const addClickHandler = () => {
        setPopUpTitle("ADD USER");
        setCurrentUser(userDefaultData);
        setIsOpen(true);
    };

    const deleteClickHandler = (user) => {

        console.log(user);
        axios.delete('http://localhost:8080/users/'+ user.id ,{})
            .then(res => {
                console.log("Succesfully Deleted");
                getUsers()
            }).catch( err => {
                console.log(err)
            }
        )
    };

    const handlePageChange = (event,value) => {
        setCurrentPage(value)
    }

    const editClickHandler = (e,user) => {
        setPopUpTitle("EDIT USER");
        setCurrentUser(user);
    };

    const getUsers = () => {
        axios.get('http://localhost:8080/users', options
        )
            .then(res => {
                console.log(res.data);
                setPageCount(res.data.noOfPages);
                setUsers(res.data.usersList);
            })
            .catch(err => {
                console.log(err)
            })
    };

    useEffect(() => {
        getUsers()
    }, [currentPage]);

    useEffect(() => {
        if(loaded.current){
            setIsOpen(true);
        }else{
            loaded.current = true;
        }
    }, [ currentUser ]);

    const classes = useStyles();

    return (
        <div className='dashboard'>
            <div className='header primary-color'>
                <h1 className='dashTitle'>DASHBOARD</h1>
            </div>
            <div className='body'>
                <div className='actionBar'>
                    <button className='addButton secondary-color' onClick={addClickHandler}>ADD USER</button>
                </div>
                <div className={classes.root}>
                    <Grid container spacing={2} direction="row" justify="center" alignItems="center" style={{padding: 14}}>
                        {users.map(user => (
                            <Grid item xs={12} sm={6} lg={4} key={user.id}>
                                <Paper className={`${classes.paper}`}>
                                    <div className='item-layout'>
                                        <div className='leftCard'>
                                            <div className='title'> {user.firstName} {user.lastName}</div>
                                            <div className='desc'>{user.phoneNumber}</div>
                                            <div className='desc'>{user.emailId}</div>
                                            <div className='desc'>{user.website}</div>
                                        </div>
                                        <div className='rightCard'>
                                            <div className='iconGroup'>
                                                <EditTwoToneIcon className={classes.editIcon} onClick={(e) => {
                                                    editClickHandler(e,user)
                                                }}/>
                                                <DeleteTwoToneIcon onClick={() => {
                                                    deleteClickHandler(user);
                                                }}/>
                                            </div>
                                        </div>
                                    </div>
                                </Paper>

                            </Grid>
                        ))}
                    </Grid>
                </div>
            </div>

            <Pagination className='pagination' count={ pageCount }onChange={handlePageChange}/>

            <Modal className='myModal' isOpen={modelIsOpen} onRequestClose={() => setIsOpen(false)}
                   style={{
                       overlay: {},
                       content: {
                           backgroundColor: 'white',
                           margin: 'auto',
                           marginTop: '100px',
                           width: 'fit-content',
                           height: 'auto',
                           alignContent: 'center',
                           maxWidth: '90%',
                       }
                   }}>
                <AddUser title={ popUpTitle } user={ currentUser } onClosePopup={() => setIsOpen(false)} onSubmit={() => {
                    getUsers()
                    setIsOpen(false)
                }}/>
            </Modal>
        </div>
    );
}

export default Dashboard;
