import { useState } from "react";
import "../css/schedule.scss";
import Seat from "./subcomponents/seat";
import { useEffect } from "react";
import axios from "axios";
import SockJsClient from "react-stomp";
const Schedule = (props) => {
  const [scheduleid,setScheduleid]=useState(props.match.params.id);
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
  useEffect(() => {
    axios
      .get("http://localhost:8080/cinemas/schedules/"+scheduleid)
      .then((res) => {
        handleChangeData(res.data);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <div>
      <SockJsClient
        url="http://localhost:8080/greeting"
        topics={["/topic/reply/"+scheduleid]}
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
      <button
        onClick={(e) => {
          console.log(selectedseats);
        }}
      >
        sdsd
      </button>
    </div>
  );
};
export default Schedule;
