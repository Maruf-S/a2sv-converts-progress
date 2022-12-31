CREATE TABLE IF NOT EXISTS USERS (
id SERIAL PRIMARY KEY,
name VARCHAR(250) NOT NULL,
easyCount INT,
midCount INT,
hardCount INT,
totalCount INT,
totalSubmission INT,
lastUpdated TIMESTAMP WITH TIME ZONE,
title VARCHAR(250) DEFAULT '' NOT NULL
);