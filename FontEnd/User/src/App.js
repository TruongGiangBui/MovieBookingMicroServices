import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./css/App.css";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import Schedule from "./components/schedule.jsx"


function App(props) {
  
  return (
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top headernav" >

        </nav>
        <Switch>
          <Route
            exact
            path="/schedules/:id"
            component={Schedule}
          ></Route>

        </Switch>
      </div>
    </Router>
  );
}
export default App;
