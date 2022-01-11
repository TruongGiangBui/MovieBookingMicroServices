import { useState } from "react";
import "../css/order.scss";
import { useEffect } from "react";
import axios from "axios";
import { Swiper, SwiperSlide } from "swiper/react";

// Import Swiper styles
// import "swiper/css";
const Order = (props) => {
  const [orderid, setOrderid] = useState(props.match.params.id);
  const [order, setOrder] = useState("");
  const [tickets, setTickets] = useState([]);
  const convertSeatName = (seat) => {
    var x = Math.floor(seat / 8);
    return String.fromCharCode(x + 65) + String(seat + 1 - x * 8);
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/booking/order/" + orderid)
      .then((res) => {
        setOrder(res.data);
      })
      .catch((error) => console.log(error));
  }, []);
  useEffect(() => {
    axios
      .get("http://localhost:8080/booking/order/getTickets?orderid=" + orderid)
      .then((res) => {
        console.log(res.data);
        setTickets(res.data);
      })
      .catch((error) => console.log(error));
  }, []);
  const getDate=(time)=>{
    var date = new Date(time);
    return ("000" + date.getHours()).slice(-2) +
    ":" +
    ("000" + date.getMinutes()).slice(-2)
  }
  const getTime=(time)=>{
    var date = new Date(time);
    return date.getDate() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getFullYear()
  }
  return (
    <div className="order_page">
      <div className="leftpane">
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
                    Số vé:
                    </td>
                    <td>{s.id}</td>
                  </tr>
                  <tr>
                    <td style={{ width: "60%", paddingLeft: "15%" }}>
                      Chỗ ngồi:
                    </td>
                    <td>{convertSeatName(parseInt(s.seat))}</td>
                  </tr>
                  <tr>
                    <td style={{ width: "60%", paddingLeft: "15%" }}>
                      Phòng chiếu:
                    </td>
                    <td>{s.cinemaroom}</td>
                  </tr>
                  <tr>
                    <td style={{ width: "60%", paddingLeft: "15%" }}>
                      Thời gian:
                    </td>
                    <td>{getDate(s.schedule)}</td>
                  </tr>
                  <tr>
                    <td style={{ width: "60%", paddingLeft: "15%" }}>Ngày:</td>
                    <td>{getTime(s.schedule)}</td>
                  </tr>
                </table>
              </div>
            </SwiperSlide>
          ))}
          ...
        </Swiper>
      </div>
    </div>
  );
};
export default Order;
