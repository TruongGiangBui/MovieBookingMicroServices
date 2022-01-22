import { useState } from "react";
import "../css/movieschedule.scss";
import { useEffect } from "react";
import axios from "axios";
import ScheduleItem from "./subcomponents/scheduleitem";
const MovieSchedules = (props) => {
  const [day, setDay] = useState(0);
  const [schedules, setSchedules] = useState([]);
  const [movie, setmovie] = useState({});
  useEffect(() => {
    console.log(props.match.params.cinemaid);
    axios
      .get(
        `http://localhost:8080/cinema/cinemas/${props.match.params.cinemaid}/schedules?day=${day}&movieid=${props.match.params.movieid}`
      )
      .then((res) => {
        console.log(res.data);
        setSchedules(res.data);
      })
      .catch((error) => console.log(error));
  }, [day]);
  useEffect(() => {
    axios
      .get(
        "http://localhost:8080/movie/api/movies/" + props.match.params.movieid
      )
      .then((res) => {
        setmovie(res.data);
      })
      .catch((error) => console.log(error));
  }, []);
  var today = new Date();
  return (
    <div className="movie_schedule_container">
      <div className="movie_info_box">
        <div className="movie_info_1">
            <img className="movie_schedule_poster" src={"http://localhost:8080/movie/"+movie.poster} alt=""  />
            <div className="movie_schedule_name">{movie.title}</div>
            <div className="movie_schedule_trailer">
            <iframe width="450" height="280" src={movie.trailer} title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>
        </div>
      </div>
      <div className="movie_schedue_box">
      <div className="schedules_box_1">
        <div className="title">Lịch chiếu tại rạp</div>
        <div className="selectdaybox">
          <div
            onClick={(e) => {
              setDay(0);
            }}
          >
            {today.getDate() +
              "-" +
              (today.getMonth() + 1) +
              "-" +
              today.getFullYear()}
          </div>
          <div
            onClick={(e) => {
              setDay(1);
            }}
          >
            {(today.getDate()+1) +
              "-" +
              (today.getMonth() + 1) +
              "-" +
              today.getFullYear()}
          </div>
          <div
            onClick={(e) => {
              setDay(2);
            }}
          >
            {(today.getDate()+2) +
              "-" +
              (today.getMonth() + 1) +
              "-" +
              today.getFullYear()}
          </div>
        </div>
        <div className="homeschedulelist">
          {schedules.map((s) => (
            <ScheduleItem schedule={s} movie={movie}></ScheduleItem>
          ))}
        </div>
      </div>
      </div>
    </div>
  );
};
export default MovieSchedules;
