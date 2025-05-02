function DisplayBox(){

    const box1 = "Tickets Available : "
    const box2 = "Maximum Capacity : "
    let value1 = 0; //val 1 and 2 is taken from the inputs
    let value2 = 100;

    return (
        <div className="display-box">
            <h3>{box1} {value1} </h3>
            <h3>{box2} {value2}</h3>
        </div>
    )
}
export default DisplayBox