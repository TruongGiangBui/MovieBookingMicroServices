import { useState } from "react";
import "../css/order.scss";
import { useEffect } from "react";
import axios from "axios";
import { Swiper, SwiperSlide } from "swiper/react";
import { useHistory } from "react-router-dom";

// Import Swiper styles
// import "swiper/css";
const Order = (props) => {
  const [orderid, setOrderid] = useState(props.match.params.id);
  const [order, setOrder] = useState("");
  const [tickets, setTickets] = useState([]);
  const [ispaid, setIspaid] = useState(false);
  const [schedule, setSchedule] = useState({});
  const [movie, setMovie] = useState({});
  const [alert, setAlert] = useState("");
  const [credit, setCredit] = useState("");
  const [cvc, setCvc] = useState("");
  const history=useHistory();
  const convertSeatName = (seat) => {
    var x = Math.floor(seat / 8);
    return String.fromCharCode(x + 65) + String(seat + 1 - x * 8);
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/booking/order/" + orderid)
      .then((res) => {
        console.log(res.data);
        setOrder(res.data);
        axios
          .get(
            "http://localhost:8080/cinema/cinemas/schedules/" +
              res.data.schedule
          )
          .then((r) => {
            console.log(r.data);
            setSchedule(r.data);
            axios
              .get("http://localhost:8080/movie/api/movies/" + r.data.movieid)
              .then((re) => {
                console.log(re.data);
                setMovie(re.data);
              })
              .catch((error) => console.log(error));
          })
          .catch((error) => {});
      })
      .catch((error) => {});
  }, []);

  useEffect(() => {
    axios
      .get(
        "http://localhost:8080/payment/payment/checkPaidOrder?orderid=" +
          orderid
      )
      .then((res) => {
        console.log(res.data);
        if (res.data["code"] == 200) {
          setIspaid(true);
          getticket();
        }
      })
      .catch((error) => {});
  }, []);

  const getticket = () => {
    axios
      .get("http://localhost:8080/booking/order/getTickets?orderid=" + orderid)
      .then((res) => {
        if (res.status == 200) {
          setTickets(res.data);
        }
      })
      .catch((error) => {});
  };
  const getDate = (time) => {
    var date = new Date(time);
    return (
      ("000" + date.getHours()).slice(-2) +
      ":" +
      ("000" + date.getMinutes()).slice(-2)
    );
  };
  const getTime = (time) => {
    var date = new Date(time);
    return (
      date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear()
    );
  };
  const convertSeatList = (seats) => {
    if (seats) {
      var arr = [];
      for (var i = 0; i < seats.length; i++) {
        arr.push(convertSeatName(seats[i]));
      }
      return arr.join(", ");
    }
    return "";
  };
  const onChangeCredit = (event) => {
    setCredit(event.target.value);
  };
  const onChangeCvc = (event) => {
    setCvc(event.target.value);
  };
  const submit = () => {
    var form = {
      orderid: orderid,
      creditcard: credit,
      cvc: cvc,
    };
    // axios.post(`http://localhost:8080/booking/order/create`,form,
    axios
      .post(`http://localhost:8024/payment/pay`, form, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        // history.push("/order/"+res.data.id)
        if (res.data.code == 200) {
          setIspaid(true);
          setAlert("");
        } else {
          setAlert(res.data.content);
        }
      })
      .catch((error) => {});
  };
  const cancel=()=>{
    axios
      .post(`http://localhost:8025/order/cancel`, {
        orderid:orderid
      }, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        history.push('/schedules/'+order.schedule);
      })
      .catch((error) => {});
  }
  return (
    <div className="order_page">
      <div className="leftpane">
        <button onClick={() => getticket()} className="getticketbtn">
          L???y v??
        </button>
        {ispaid ? (
          <Swiper
            spaceBetween={50}
            slidesPerView={1}
            onSlideChange={() => console.log("slide change")}
            onSwiper={(swiper) => console.log(swiper)}
          >
            {tickets.map((s) => (
              <SwiperSlide>
                <div className="tiketpane">
                  <div className="movie">{s.movie}</div>
                  <table>
                    <tr>
                      <td style={{ width: "60%", paddingLeft: "15%" }}>
                        S??? v??:
                      </td>
                      <td>{s.id}</td>
                    </tr>
                    <tr>
                      <td style={{ width: "60%", paddingLeft: "15%" }}>
                        Ch??? ng???i:
                      </td>
                      <td>{convertSeatName(parseInt(s.seat))}</td>
                    </tr>
                    <tr>
                      <td style={{ width: "60%", paddingLeft: "15%" }}>
                        Ph??ng chi???u:
                      </td>
                      <td>{s.cinemaroom}</td>
                    </tr>
                    <tr>
                      <td style={{ width: "60%", paddingLeft: "15%" }}>
                        Th???i gian:
                      </td>
                      <td>{getDate(s.schedule)}</td>
                    </tr>
                    <tr>
                      <td style={{ width: "60%", paddingLeft: "15%" }}>
                        Ng??y:
                      </td>
                      <td>{getTime(s.schedule)}</td>
                    </tr>
                  </table>
                </div>
              </SwiperSlide>
            ))}
          </Swiper>
        ) : (
          <div className="notice">Vui l??ng thanh to??n ????? nh???n v??</div>
        )}
      </div>
      <div className="rightpane">
        <div className="title">Thanh to??n</div>
        <div className="movie">{movie.title}</div>
        <table>
          <tr>
            <td style={{ width: "60%", paddingLeft: "15%" }}>Ph??ng chi???u:</td>
            <td>{schedule.cinemaroom}</td>
          </tr>
          <tr>
            <td style={{ width: "60%", paddingLeft: "15%" }}>Ch??? ng???i:</td>
            <td>{convertSeatList(order.seatslist)}</td>
          </tr>
          <tr>
            <td style={{ width: "60%", paddingLeft: "15%" }}>Th???i gian:</td>
            <td>{getDate(schedule.starttime)}</td>
          </tr>
          <tr>
            <td style={{ width: "60%", paddingLeft: "15%" }}>Ng??y:</td>
            <td>{getTime(schedule.endtime)}</td>
          </tr>
          <tr>
            <td style={{ width: "60%", paddingLeft: "15%" }}>
              S??? ti???n thanh to??n:
            </td>
            <td>{schedule.price}</td>
          </tr>
        </table>
        {!ispaid ? (
          <div>
            <input
              type="text"
              value={credit}
              placeholder="Nh???p s??? th??? t??n d???ng"
              onChange={(e) => onChangeCredit(e)}
            />
            <input
              type="password"
              value={cvc}
              placeholder="cvc"
              onChange={(e) => onChangeCvc(e)}
            />
            <div className="btn_pane">
              <div
                className="Confirm_btn"
                onClick={(e) => {
                  submit();
                }}
              >
                X??c nh???n
              </div>
              <div
                className="Confirm_btn"
                style={{ backgroundColor: "red" }}
                onClick={(e) => {
                  cancel();
                }}
              >
                H???y b???
              </div>
            </div>
          </div>
        ) : (
          <div className="notice1">
            Thanh to??n th??nh c??ng. Nh???n n??t nh???n v?? ????? l???y v??
          </div>
        )}
        <div className="notice">{alert}</div>
      </div>
    </div>
  );
};
export default Order;
