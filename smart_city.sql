CREATE DATABASE IF NOT EXISTS smart_city;
USE smart_city;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash CHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS city_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(30) NOT NULL,
    name VARCHAR(120) NOT NULL,
    details TEXT NOT NULL,
    map_query VARCHAR(200) NOT NULL
);

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Tourist', 'Lalbagh Botanical Garden', 'A well-known botanical garden in Bengaluru.', 'Lalbagh Botanical Garden Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Tourist' AND name='Lalbagh Botanical Garden');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Tourist', 'Cubbon Park', 'A large green park in central Bengaluru.', 'Cubbon Park Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Tourist' AND name='Cubbon Park');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Tourist', 'Bangalore Palace', 'A historic palace and tourist attraction.', 'Bangalore Palace'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Tourist' AND name='Bangalore Palace');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Tourist', 'ISKCON Temple', 'A popular temple in Bengaluru.', 'ISKCON Temple Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Tourist' AND name='ISKCON Temple');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Hotel', 'Taj West End', 'Hotel in Bengaluru. Check the official listing for current details.', 'hotels near Taj West End Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Hotel' AND name='Taj West End');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Hotel', 'The Leela Palace', 'Hotel in Bengaluru. Check the official listing for current details.', 'hotels near The Leela Palace Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Hotel' AND name='The Leela Palace');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Transport', 'Namma Metro', 'Bengaluru metro rail service. Check current routes and timings.', 'Bengaluru Namma Metro stations'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Transport' AND name='Namma Metro');

INSERT INTO city_info (category, name, details, map_query)
SELECT 'Transport', 'BMTC Bus', 'City bus service. Check current routes and timings.', 'BMTC bus stops Bengaluru'
WHERE NOT EXISTS (SELECT 1 FROM city_info WHERE category='Transport' AND name='BMTC Bus');


