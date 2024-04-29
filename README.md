
## Description
Real time state full OLAP data processing with Apache Flink, Apache Pinot and Kafka


## Architecture

![App Screenshot](https://raw.githubusercontent.com/itp9177/flink_Kafka_pinot-realtime_datastream/main/architecture.png)

## Key Concept :

- Data Acquisition and processing:
  * Continuously collects weather data from multiple locations every second using the open-meteo API
  * Utilizes Apache Kafka as a distributed streaming platform to buffer and manage the data stream before it reaches Apache Flink for processing.
- Stateful Stream Processing with Apache Flink:
  * Apache Flink acts as a stream processing engine, continuously analyzing the incoming weather data stream in real-time and sink to Pinot table for efficient querying and analysis.


- OLAP (Online Analytical Processing) Data Storage with Pinot:
  * Leverages Pinot(distributed OLAP datastore) for low-latency querying and retrieval of data for near real-time insights from our Springboot App.
  * Employs a hybrid table structure within Pinot. This combines a real-time table for storing the most recent data received through Flink, along with an offline table for storing historical older data.(This enables efficient analysis of both real-time and historical data.)
- Scalability:
  * Leverage the distributed architecture of Apache Kafka, Apache Flink, and Pinot to enable deployment in a scalable cloud environment.

## Quick Start

#### With docker-composer

```javascript
git clone https://github.com/itp9177/flink_Kafka-realtime_datastream.git
docker-compose -f docker-compose.yml up
```

```javascript
Apache Flink dashboard http://127.0.0.1:8081
Apache Pinot dashboard http://127.0.0.1:9000

```
##### Start flink job
```javascript
cd first-flink-job/

```
##### Add the flink job via flink UI
```javascript

open Apache Flink dashboard on http://127.0.0.1:8081 
add the compiled flink job JAR (target/first-flink-job-0.1.jar) from flink UI and submit the job

```
##### flink job observability and metrix
![App Screenshot](https://raw.githubusercontent.com/itp9177/flink_Kafka_pinot-realtime_datastream/main/flinkjob.png)

## RoadMap
 - Distributed colud deployment of the system 
 - sample Apache pinot client to query data 
 - load testing
## License

[MIT](https://choosealicense.com/licenses/mit/)

