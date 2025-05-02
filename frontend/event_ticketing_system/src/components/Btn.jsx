import {useNavigate} from "react-router-dom";




function Btn(){
    const navigate = useNavigate();

    return(
        <div className="btn">
            <button type="button" className="transition-btn" id="c2v" onClick={() => navigate("/")}> Vendor </button>
            <button type="button" className="transition-btn" id="v2c" onClick={() => navigate("/customer")}> Customer </button>
        </div>
    );
}

export default Btn