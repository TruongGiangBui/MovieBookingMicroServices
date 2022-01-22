
  
import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import "./css/index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import {configureStore} from './store';
import * as actions from './actions';
import { Provider } from 'react-redux';
import { UserProvider } from './UserContext'
// const data={
//     token: localStorage.getItem('apptoken')
// };

const store = configureStore();
// store.dispatch(actions.setData(data));

ReactDOM.render(

        <BrowserRouter>
            <App />
        </BrowserRouter>,
    document.getElementById("root")
);

serviceWorker.unregister();