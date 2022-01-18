import { useState } from "react";
import "../css/admin.scss";
import { useEffect } from "react";
import axios from "axios";
import MoviePoster from "./subcomponents/movieposter";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";
import ScheduleItem from "./subcomponents/scheduleitem";
import { Link } from "react-router-dom";
const Admin = (props) => {
  const [balance, setBalanse] = useState(0);
  const [countries, setCountries] = useState([]);
  const [currentcountry, setCurrentcountry] = useState(1);
  const [cities, setCities] = useState([]);
  const [currentcity, setCurrentcity] = useState(1);
  const [cinemas, setCinemas] = useState([]);
  const [cinema, setCinema] = useState(1);
  const [date, setDate] = useState("");
  const [movies, setmovies] = useState([]);
  const [selectedmovie, setSelected] = useState([]);
  const [l, setl] = useState(true);
  const onAddMovie = (m) => {
    if (!selectedmovie.includes(m)) {
      selectedmovie.push(m);
    }
    setSelected(selectedmovie.slice());
    console.log(selectedmovie);
  };
  useEffect(() => {
    axios
      .get("http://localhost:8080/payment/balance/6587-2880-1111-1111")
      .then((res) => {
        console.log(res.data);
        setBalanse(res.data.content);
      })
      .catch((error) => console.log(error));
  }, []);
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
      .get("http://localhost:8080/movie/api/movies")
      .then((res) => {
        console.log(res.data);
        for (var i = 0; i < res.data.length; i++) {
          res.data["selected"] = false;
        }
        setmovies(res.data);
      })
      .catch((error) => console.log(error));
  }, []);
  const gen=()=>{
    if(date=='') alert("Vui lòng chọn ngày chiếu");
    var form = {
      "movieids": [

      ],
      "date": date.split('-')[2]+'/'+date.split('-')[1]+'/'+date.split('-')[0],
      "cinemaid": parseInt(cinema)
    };
    for(var i=0;i<selectedmovie.length;i++){
      form.movieids.push(selectedmovie[i].id);
    }
    console.log(form);
    axios
      .post(`http://localhost:8023/admin/generateSchedule`, form, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
          if(res.status==200){
            alert("Tạo lịch chiếu thành công ");
          }
      })
      .catch((error) => {});
  }
  return (
    <div className="container1">
      <div className="left_pane">
        <div className="total">Tổng doanh thu: {balance} VND</div>
      </div>
      <div className="right_pane">
        <div className="genscheduleform">
          <div className="title">Tạo lịch chiếu</div>
          <div style={{ display: "flex", justifyContent: "center" }}>
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
          <div style={{ display: "flex", justifyContent: "center" }}>
            <div className="date">
              <input
                className="Datepicker"
                type="date"
                name=""
                id=""
                onChange={(e) => {
                  setDate(e.target.value);
                  console.log(e.target.value);
                }}
              />
            </div>
          </div>
          <div className="head">Chọn phim</div>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <div className="movie_list">
              {movies.map((movie) => (
                <div className="movie_item">
                  <img
                    src={"http://localhost:8080/movie/" + movie.poster}
                    alt=""
                    srcset=""
                  />
                  <div className="name">{movie.title}</div>
                  <div className="add" onClick={(e) => onAddMovie(movie)}>
                    Thêm
                  </div>
                </div>
              ))}
            </div>
          </div>
          <div className="head">Các phim đã chọn</div>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <div className="selected_list1">
              {selectedmovie.map((movie) => (
                <div className="selected_item">
                  <div className="name">{movie.title}</div>
                </div>
              ))}
            </div>
          </div>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <div className="add1" onClick={(e)=>gen()}>Tạo lịch chiếu</div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Admin;
