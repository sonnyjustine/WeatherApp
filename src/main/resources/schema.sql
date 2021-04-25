CREATE SCHEMA weather_test_db

DROP TABLE IF EXISTS hibernate_sequence;
CREATE TABLE hibernate_sequence (
  next_val bigint DEFAULT NULL
)

DROP TABLE IF EXISTS weather_data;
CREATE TABLE weather_data (
  weather_data_id bigint NOT NULL,
  description varchar(255) DEFAULT NULL,
  icon varchar(10) DEFAULT NULL,
  weather_group varchar(100) DEFAULT NULL,
  PRIMARY KEY (weather_data_id)
)

DROP TABLE IF EXISTS weather_log;
CREATE TABLE weather_log (
  id bigint NOT NULL,
  city_id bigint DEFAULT NULL,
  city_name varchar(100) DEFAULT NULL,
  cloudiness int DEFAULT NULL,
  country_code char(2) DEFAULT NULL,
  latitude double DEFAULT NULL,
  longitude double DEFAULT NULL,
  feels_like_temp double DEFAULT NULL,
  humidity int DEFAULT NULL,
  max_temp double DEFAULT NULL,
  min_temp double DEFAULT NULL,
  pressure double DEFAULT NULL,
  temperature double DEFAULT NULL,
  visibility int DEFAULT NULL,
  weather_date datetime DEFAULT NULL,
  wind_degree int DEFAULT NULL,
  wind_speed double DEFAULT NULL,
  weather_data_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY idx_weather_log_city_name (city_name),
  KEY fk_weather_data_id (weather_data_id),
  CONSTRAINT fk_weather_data_id FOREIGN KEY (weather_data_id) REFERENCES weather_data (weather_data_id)
)