import React from 'react';
import Tab from "@material-ui/core/Tab";
import {Paper} from "@mui/material";

const Tabs = () => {
    const [value, setValue] = React.useState(2);

    return (
        <div style={{marginLeft:"40%"}}>
            <Paper square>
                <Tabs
                    value={value}
                    textColor="primary"
                    indicatorColor="primary"
                    onChange={(event, newValue) => {
                        setValue(newValue);
                    }}
                >
                    <Tab label="Active TAB One" />
                    <Tab label="Active TAB Two" />
                    <Tab label="Disabled TAB!" disabled />
                    <Tab label="Active Tab Three" />
                </Tabs>
            </Paper>
        </div>
    )
}