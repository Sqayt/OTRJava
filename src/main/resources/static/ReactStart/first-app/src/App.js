import './App.css';
import React from 'react';
import Header from './Header/Header';
import ButtonAddPerson from './Button/Button';
import Tabs from './Tabs/Tabs.js';

function App() {
    return (
        <div className="AppName">
            <Header />
            <ButtonAddPerson />
            <Tabs />
        </div>
    );
}

export default App;