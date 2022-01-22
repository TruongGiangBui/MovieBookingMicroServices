import { useState } from "react";
import "../../css/home.scss";
import { useEffect } from "react";
const MoviePoster = (props) => {
  

  return (
    <div className="poster_box">
      <img src={"http://localhost:8080/movie/"+props.posterlink} alt="" />
    </div>
  );
};
export default MoviePoster;
