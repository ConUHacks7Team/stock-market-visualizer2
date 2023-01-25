import './App.css';
import { useEffect, useState } from "react";
import { Data } from "./Data";
import PieChart from "./PieChart";
import LineChart from "./LineChart";
const ws = new WebSocket('ws://localhost:8080/ws/stock');
var labels = [];
var userGains = [];

function addData(label, data) {
  labels.push(label);
  userGains.push(data);
}


export default function App() {
  const [haveData, setHaveData] = useState(false); //here
  const [chartData, setChartData] = useState({});

  useEffect(() => {
    ws.onopen = () => {
      console.log('WebSocket opened');
      ws.send(JSON.stringify({subscription_symbol: "KGJUJ"})); /// SUBSCRIPTION SYMBOL
      setChartData({
        labels: [], 
        datasets: [
          {
            label: "No data yet",
            data: [],
            backgroundColor: [],
            borderColor: "black",
            borderWidth: 2
          }
        ]
      });
      setHaveData(true);
    }
    
    ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        if(data.length === 0) {
          setChartData({
            labels: [], 
            datasets: [
              {
                label: "No data yet",
                data: [],
                backgroundColor: [],
                borderColor: "black",
                borderWidth: 2
              }
            ]
          });
        } else {
        // Extract the relevant data
          console.log(data);
          const newLabel = data.TimeStamp;
          const newGain = data.OrderPrice;
          console.log(newLabel, newGain);
          addData(newLabel, newGain);
          
          // Update the chartData state
          setChartData({
            labels: labels, 
            datasets: [
              {
                label: "Order Price",
                data: userGains,
                backgroundColor: [
                  "rgba(75,192,192,1)",
                  "#ecf0f1",
                  "#50AF95",
                  "#f3ba2f",
                  "#2a71d0"
                ],
                borderColor: "black",
                borderWidth: 2
              }
            ]
          });
          setHaveData(true);
        }
      } catch(error){
        setHaveData(false);
      }
      
  
      ws.onerror = (error) => {
        console.log(`WebSocket error: ${error}`);
      }
  
      ws.onclose = () => {
        console.log('WebSocket closed');
      }}
      
      

    return () => {
      //ws.close();
    }
  }, []);

  if (!haveData) {
    return (
      <div className="App">
        <p>Loading...</p>
      </div>
    );
  } else {
    return (
      <div className="App">
        <p>Using Chart.js in React</p>
        <LineChart chartData={chartData} />
        <PieChart chartData={chartData} />
      </div>
    );
  }
}

// export default App;
