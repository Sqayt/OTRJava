import './App.css';
import React, { Component } from 'react';
import Header from './Header/Header';
import ButtonAddPerson from './Button/Button';

function App() {
    const divStyle = {
        textAlign: 'center'
    }
    return (
        <div className="AppName" style={divStyle}>
            <Header />
            <ButtonAddPerson />
        </div>
    );
}

export default App;