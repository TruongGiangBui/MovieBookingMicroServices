import { useState } from "react";
import { useEffect } from "react";
import axios from "axios";
import "../../css/home.scss";
import { Link } from "react-router-dom";
const ScheduleItem = (props) => {
  const [schedule, setSchedule] = useState(props.schedule);
  var date = new Date(props.schedule.starttime);
  const [movie, setmovie] = useState({});
  useEffect(() => {
    if (props.movie) {
      setmovie(props.movie);
    } else {
      axios
        .get("http://localhost:8080/movie/api/movies/" + schedule.movieid)
        .then((res) => {
          console.log(res.data);
          setmovie(res.data);
        })
        .catch((error) => console.log(error));
    }
  }, [props.schedule]);
  return (
    <Link to={`/schedules/${schedule.id}`}>
      <div className="scheduleitem">
        <div className="left">
          <div className="info">Tên phim: {movie.title}</div>
          <div className="info">Đạo diễn: {movie.director}</div>
          <div className="info">Thời lượng: {movie.runtime} phút</div>
        </div>
        <div className="center">
          <div className="info">Phòng chiếu: {schedule.cinemaroom}</div>
          <div className="info">
            Giờ bắt đầu:{" "}
            {("000" + date.getHours()).slice(-2) +
              ":" +
              ("000" + date.getMinutes()).slice(-2)}
          </div>
          <div className="info">
            Ngày:{" "}
            {date.getDate() +
              "/" +
              (date.getMonth() + 1) +
              "/" +
              date.getFullYear()}
          </div>
        </div>
        <div className="right">
          <div className="info">Số ghế: {schedule.capacity}</div>
          <div className="info">Định dạng: {movie.format}</div>
        </div>
      </div>
    </Link>
  );
};
export default ScheduleItem;
