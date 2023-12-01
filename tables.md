```sql
CREATE TABLE Users (
	user_id SERIAL,
	email VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(50),
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	PRIMARY KEY (user_id)
);

CREATE TABLE Groupss (
	group_id SERIAL PRIMARY KEY,
	group_name VARCHAR(50) NOT NULL 
);

CREATE TABLE UserInGroup (
	user_id INTEGER NOT NULL,
	group_id INTEGER NOT NULL,
	PRIMARY KEY (user_id, group_id),
	FOREIGN KEY (user_id) REFERENCES Users ON DELETE CASCADE,
	FOREIGN KEY (group_id) REFERENCES Groupss ON DELETE CASCADE
);

CREATE TABLE Experiment (
	experiment_id SERIAL,
	experiment_name VARCHAR(50),
	PRIMARY KEY (experiment_id)
);

CREATE TABLE Job (
	job_id SERIAL,
	is_completed BOOLEAN,
	private BOOLEAN,
	PRIMARY KEY (job_id)
);

CREATE TABLE Instrument (
	instrument_id SERIAL,
	instrument_name VARCHAR(50),
	PRIMARY KEY (instrument_id)
);

CREATE TABLE UserQueuesExperiment (
	user_id INTEGER NOT NULL,
	experiment_id INTEGER NOT NULL,
	PRIMARY KEY (user_id, experiment_id),
	FOREIGN KEY (user_id) REFERENCES Users ON DELETE CASCADE,
	FOREIGN KEY (experiment_id) REFERENCES Experiment ON DELETE CASCADE
);

CREATE TABLE JobInExperiment (
	job_id INTEGER NOT NULL,
	experiment_id INTEGER NOT NULL,
	PRIMARY KEY (job_id, experiment_id),
	FOREIGN KEY (job_id) REFERENCES Job ON DELETE CASCADE,
FOREIGN KEY (experiment_id) REFERENCES Experiment ON DELETE CASCADE
);

CREATE TABLE InstrumentInJob (
	instrument_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	PRIMARY KEY (instrument_id, job_id),
	FOREIGN KEY (instrument_id) REFERENCES Instrument ON DELETE CASCADE,
FOREIGN KEY (job_id) REFERENCES Job ON DELETE CASCADE
);

CREATE TABLE ExperimentLog (
	timestamp INTEGER NOT NULL,
	instrument_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	experiment_data VARCHAR(2048),
	symbolic_link VARCHAR(2048),
	PRIMARY KEY (timestamp, instrument_id, job_id),
	FOREIGN KEY (instrument_id) REFERENCES Instrument ON DELETE CASCADE,
	FOREIGN KEY (job_id) REFERENCES Job ON DELETE CASCADE
);

CREATE TABLE MolecalcLog (
	timestamp INTEGER NOT NULL,
	instrument_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	molecule_ran VARCHAR(50),
	PRIMARY KEY (timestamp, instrument_id, job_id),
	FOREIGN KEY (timestamp, instrument_id, job_id) REFERENCES ExperimentLog ON DELETE CASCADE
);

CREATE TABLE NMRLog (
	timestamp INTEGER NOT NULL,
	instrument_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	chemical_ran VARCHAR(50),
	PRIMARY KEY (timestamp, instrument_id, job_id),
	FOREIGN KEY (timestamp, instrument_id, job_id) REFERENCES ExperimentLog ON DELETE CASCADE
);

CREATE TABLE AcousticLog (
	timestamp INTEGER NOT NULL,
	instrument_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	gas_ran VARCHAR(50),
	PRIMARY KEY (timestamp, instrument_id, job_id),
	FOREIGN KEY (timestamp, instrument_id, job_id) REFERENCES ExperimentLog ON DELETE CASCADE
);
```