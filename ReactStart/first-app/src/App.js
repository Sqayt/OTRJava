import './App.css';
import React from 'react';
import Header from './Header/Header';
import ButtonAddPerson from './Button/Button';
import Tabs from './Tabs/Tabs.js';
import MyComponent from './Data/Person'

function App() {
    return (
        <div className="AppName">
            <Header />
            <ButtonAddPerson />
            <Tabs />
            <MyComponent />
        </div>
    );
}

export default App;