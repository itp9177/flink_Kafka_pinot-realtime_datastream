
## Description
Real time state full OLAP data processing with apache flink, apache pinot and kafka

Key Features:

- Data Acquisition and processing:
  * Continuously collects weather data from multiple locations every second using the open-meteo API
  * Utilizes Apache Kafka as a distributed streaming platform to buffer and manage the data stream before it reaches Apache Flink for processing.
- Stateful Stream Processing with Apache Flink:
  * Apache Flink acts as a stream processing engine, continuously analyzing the incoming weather data stream in real-time and sink to Pinot table for efficient querying and analysis.


- OLAP (Online Analytical Processing) Data Storage with Pinot:
  * Leverages Pinot(distributed OLAP datastore) for low-latency querying and retrieval of data for near real-time insights from our Springboot App.
  * Employs a hybrid table structure within Pinot. This combines a real-time table for storing the most recent data received through Flink, along with an offline table for storing historical older data. This enables efficient analysis of both real-time and historical trends.
- Scalability:
  * Utilize the distributed stricture of
## Quick Start

#### With docker-composer

```javascript
git clone https://github.com/itp9177/flink_Kafka-realtime_datastream.git
docker-compose -f docker-compose.yml up
```
## Architecture

![App Screenshot](https://raw.githubusercontent.com/itp9177/flink_Kafka_pinot-realtime_datastream/main/architecture.png)

## License

[MIT](https://choosealicense.com/licenses/mit/)

