import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./css/App.css";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import Schedule from "./components/schedule.jsx"
import Home from "./components/home";
import MovieSchedules from "./components/movieschedule";
import Order from "./components/order";
import Admin from "./components/admin";
//sudo kill -9 `sudo lsof -t -i:9001`
function App(props) {
  
  return (
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top headernav" >
            <Link to='/' className="linkhome">Trang chủ</Link>
        </nav>
        <Switch>
        <Route
            exact
            path="/admin"
            component={Admin}
          ></Route>
        <Route
            exact
            path="/order/:id"
            component={Order}
          ></Route>
          <Route
            exact
            path="/schedules/:id"
            component={Schedule}
          ></Route>
          <Route
            exact
            path="/:cinemaid/movieschedule/:movieid"
            component={MovieSchedules}
          ></Route>
          <Route
            exact
            path="/"
            component={Home}
          ></Route>
        </Switch>
      </div>
    </Router>
  );
}
export default App;
//kill $(lsof -t -i:3000)
