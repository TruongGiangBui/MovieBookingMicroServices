import { useState } from "react";
import "../../css/schedule.scss";
import { useEffect } from "react";
const Seat = (props) => {
  const [status, setStatus] = useState(props.status);
  useEffect(() => {
    setStatus(props.status)
  }, [props])

  return (
    <div className="seat_box">
      <div
        className="seat"
        onClick={(event) => {
          if (status == "empty") {
            props.onSelectSeat(props.id);
            // setStatus("selected");
          } else if (status == "selected") {
            props.onUnselectSeat(props.id);
            // setStatus("empty");
          }
        }}
      >

        <div className={status}>
            {props.seatnumber}
        </div>
      </div>
    </div>
  );
};
export default Seat;
