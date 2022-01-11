import { useState } from "react";
import "../css/schedule.scss";
import Seat from "./subcomponents/seat";
import { useEffect } from "react";
import axios from "axios";
import SockJsClient from "react-stomp";
const Schedule = (props) => {
  const [scheduleid, setScheduleid] = useState(props.match.params.id);
  const [selectedseats, setSelectedseats] = useState(Array());
  const [seats, setSeats] = useState(Array());
  const [data, setData] = useState(Array());
  const onSelectSeat = (id) => {
    var s = selectedseats;
    s.push(id);
    var s1 = seats.slice();
    s1[id].status = "selected";
    setSeats(s1);
    setSelectedseats(s);
  };
  const onUnselectSeat = (id) => {
    var s = selectedseats;
    s = s.filter((item) => item != id);
    setSelectedseats(s);
    var s1 = seats.slice();
    s1[id].status = "empty";
    setSeats(s1);
  };
  const convertSeatName = (seat) => {
    var x = Math.floor(seat / 8);
    return String.fromCharCode(x + 65) + String(seat + 1 - x * 8);
  };
  const handleChangeData = (data) => {
    console.log("call reload");
    if (seats.length == 0) {
      var s = Array();
      for (var i = 0; i < data.capacity; i++) {
        var status = data.seatslist[i];
        s.push({
          name: i,
          status:
            status == "0" ? "empty" : status == "1" ? "ordered" : "pending",
        });
      }
    } else {
      var s = seats.slice();
      for (var i = 0; i < data.capacity; i++) {
        var status = data.seatslist[i];
        if (status == "1" && s[i].status != "ordered") {
          if (s[i].status == "selected") {
            var s1 = selectedseats;
            s1 = s1.filter((item) => item != i);
            setSelectedseats(s1);
          }
          s[i].status = "ordered";
        }
        if (status == "2" && s[i].status != "pending") {
          if (s[i].status == "selected") {
            var s1 = selectedseats;
            s1 = s1.filter((item) => item != i);
            setSelectedseats(s1);
          }
          s[i].status = "pending";
        }
        if (status == "0" && s[i].status != "empty") {
          if (s[i].status != "selected") {
            s[i].status = "empty";
          }
        }
      }
    }
    console.log(s);
    setSeats(s);
  };
  const order=()=>{
    console.log(selectedseats);
    var form={
      "schedule_id": props.match.params.id,
      "seats":selectedseats
    }
    axios.post(`http://localhost:8080/booking/order/create`,form)
      .then(res => {
            console.log(res.data);
            if(res.data.code===0){
               this.setState({
                   message:res.data.message
               })
              }
      })
      .catch(error => console.log(error));
  }
  useEffect(() => {
    axios
      .get("http://localhost:8080/cinema/cinemas/schedules/" + scheduleid)
      .then((res) => {
        handleChangeData(res.data);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <div className="schedule_page">
      <SockJsClient
        url="http://localhost:8080/cinema/greeting"
        topics={["/topic/reply/" + scheduleid]}
        onMessage={(msg) => {
          handleChangeData(msg);
        }}
      />
      <div className="schedule_box">
        <div className="schedule_box_container">
          {seats.map((seat) => (
            <Seat
              id={seat.name}
              status={seat.status}
              seatnumber={convertSeatName(seat.name)}
              onSelectSeat={onSelectSeat}
              onUnselectSeat={onUnselectSeat}
            ></Seat>
          ))}
        </div>
      </div>
      <div className="schedule_box_right">
        <div className="color_des">
          <div className="color_des_item">
            <div className="color_pane color1"></div>
            <div className="color_pane_des">Ghế đã đặt</div>
          </div>
          <div className="color_des_item">
            <div className="color_pane color2"></div>
            <div className="color_pane_des">Đang chọn</div>
          </div>
          <div className="color_des_item">
            <div className="color_pane color3"></div>
            <div className="color_pane_des">Còn trống</div>
          </div>
          <div className="color_des_item">
            <div className="color_pane color4"></div>
            <div className="color_pane_des">Đang xử lí</div>
          </div>
        </div>
        <div className="selected_list">Ghế đã chọn: {selectedseats.map(seat=>(
          " "+convertSeatName(seat)
        ))}</div>
        <div
          className="select_seat_btn"
          onClick={(e) => {
            order();
          }}
        >
          Đặt vé
        </div>
      </div>
    </div>
  );
};
export default Schedule;
