import React, {useState} from 'react';
import './Tabs.css';

function Tabs() {
    const[index, setIndex] = useState(0);

    return (
        <div className="Tabs">
            <div className="TabsList" >
                <row className="tabHead" onClick={() => setIndex(0)}>
                    Tab1
                </row>
                <row className="tabHead" onClick={() => setIndex(1)}>
                    Tab2
                </row>
            </div>
            <div className="tabContact" hidden={index !== 0}>
                This is First tab content.
            </div>
            <div className="tabContact" hidden={index !== 1}>
                This is Second tab content.
            </div>
        </div>
    )
}

export default Tabs