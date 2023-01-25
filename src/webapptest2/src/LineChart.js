import React from "react";
import 'chart.js/auto';
import { Chart } from "react-chartjs-2";


function LineChart({ chartData }) {
  return (
    <div className="chart-container">
      <h2 style={{ textAlign: "center" }}>Line Chart</h2>
      <Chart 
        type="line"
        data={chartData}
        options={{
            plugins: {
                title: {
                display: true,
                text: "Users Gained between 2016-2020"
                },
                legend: {
                display: false
                }
            }
        }}

      />
    </div>
  );
}
export default LineChart;