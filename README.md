# stock-market-visualizer2
This project is our choosen challenge for the 2023 ConuHacks Hackathon at Concordia University.

## Author
- Adam Qacha

## Overview
### Backend
The backend is written in Java using the `Spring` framework. It is responsible to do these tasks:
- Host a WebSocket server which has the following responsabilities : 
  - Provide information about the most recent trades
  - Provide information about past trades of a certain stock
  - Provide information about exchanges
- Simulate a stock market system
  - Simulate a clock that starts at 9:28
  - Generate events on NewOrderRequest, NewOrderAcknowledged, CancelRequest, Cancelled and Trade
  - Read transactions from the JSON file and generate events at the appropriate time

The program uses the JSON files provided by the sponsor representative Michael Cristiano at NBC on ConUHacks VII's Discord server.

### Frontend
