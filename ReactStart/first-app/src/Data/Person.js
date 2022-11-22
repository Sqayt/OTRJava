import axios from "axios";
import React from 'react';
import {createStore} from "redux";


function MyComponent() {
    const apiUrl = 'http://localhost:8081/task/api/v1/persons';

    const initState = {
        id: '',
        fullName: '',
        directorFullName: '',
        branchName: '',
        countTask: ''
    }

    return (
        <div className='data'>
            <h1></h1>
        </div>
    )
}


export default MyComponent