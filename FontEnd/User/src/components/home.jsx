import { useState } from "react";
import "../css/schedule.scss";
import { useEffect } from "react";
import axios from "axios";
import MoviePoster from "./subcomponents/movieposter";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";
import ScheduleItem from "./subcomponents/scheduleitem";
import { Link } from "react-router-dom";
const Home = (props) => {
  const [countries, setCountries] = useState([]);
  const [currentcountry, setCurrentcountry] = useState(1);
  const [cities, setCities] = useState([]);
  const [currentcity, setCurrentcity] = useState(1);
  const [cinemas, setCinemas] = useState([]);
  const [cinema, setCinema] = useState(1);
  const [nowshowings, setNowshowings] = useState([]);
  const [schedules, setSchedules] = useState([]);
  const [day, setDay] = useState(0);
  useEffect(() => {
    axios
      .get("http://localhost:8080/cinema/countries")
      .then((res) => {
        // console.log(res.data);
        setCountries(res.data);
      })
      .catch((error) => console.log(error));
  }, []);
  useEffect(() => {
    axios
      .get("http://localhost:8080/cinema/cities?countryid=" + currentcountry)
      .then((res) => {
        // console.log(res.data);
        setCities(res.data);
        if (res.data[0]) setCurrentcity(res.data[0]["id"]);
        else setCurrentcity("");
      })
      .catch((error) => console.log(error));
  }, [currentcountry]);
  useEffect(() => {
    axios
      .get("http://localhost:8080/cinema/cinemas?cityid=" + currentcity)
      .then((res) => {
        // console.log(res.data);
        setCinemas(res.data);
        setCinema(res.data[0]["id"]);
      })
      .catch((error) => console.log(error));
  }, [currentcity, currentcountry]);
  useEffect(() => {
    axios
      .get(
        `http://localhost:8080/cinema/cinemas/${cinema}/schedules?day=` + day
      )
      .then((res) => {
        setSchedules(res.data);
      })
      .catch((error) => console.log(error));
  }, [cinema, day]);
  useEffect(() => {
    axios
      .get(`http://localhost:8080/cinema/cinemas/${cinema}/movies/now-showing`)
      .then((res) => {
        setNowshowings(res.data);
      })
      .catch((error) => console.log(error));
  }, [cinema]);
  var today = new Date();

  return (
    <div>
      <div className="search_bar">
        <div className="search_bar_box">
          <select
            onChange={(e) => setCurrentcountry(e.target.value)}
            className="select_country"
            name="country"
            id="country"
          >
            {countries.map((c) => (
              <option value={c.id}>{c.name}</option>
            ))}
          </select>
          <select
            onChange={(e) => setCurrentcity(e.target.value)}
            className="select_country"
            name="country"
            id="country"
          >
            {cities.map((c) => (
              <option value={c.id}>{c.name}</option>
            ))}
          </select>
          <select
            onChange={(e) => setCinema(e.target.value)}
            className="select_country"
            name="country"
            id="country"
          >
            {cinemas.map((c) => (
              <option value={c.id}>{c.name}</option>
            ))}
          </select>
        </div>
      </div>
      <div className="movie_posters">
        <Swiper
          spaceBetween={0}
          slidesPerView={7}
          onSlideChange={() => console.log("slide change")}
          onSwiper={(swiper) => console.log(swiper)}
        >
          {nowshowings.map((movie) => (
            <SwiperSlide>
              <Link to={`/${cinema}/movieschedule/${movie.id}`}>
                <MoviePoster posterlink={movie.poster}></MoviePoster>
              </Link>
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
      <div className="schedules_box">
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
            {today.getDate() +
              1 +
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
            {today.getDate() +
              2 +
              "-" +
              (today.getMonth() + 1) +
              "-" +
              today.getFullYear()}
          </div>
        </div>
        <div className="homeschedulelist">
          {schedules.map((s) => (
            <ScheduleItem schedule={s}></ScheduleItem>
          ))}
        </div>
      </div>
    </div>
  );
};
export default Home;
