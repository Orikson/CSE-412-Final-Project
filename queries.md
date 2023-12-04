# Login Screen

## Check Credentials
Input
- email: string
- password: string
Output
- UID if validated
- Empty table if invalidated

```sql
SELECT user_id FROM Users WHERE email={email} AND password={password};
```

## Create new User
Input
- email: string
- password: string
- first_name: string
- last_name: string
Output
- UID on success
- Nothing on failure

```sql
TODO
```

# Home Screen

## Get first and last name of user
Input
- UID: int
Output
- first name: string
- last name: string

```sql
SELECT first_name, last_name FROM Users WHERE user_id={UID};
```

## Delete user
Input
- UID: int
Output
- NONE

```sql
TODO
```


# Create Experiments Screen

## Get all instrument names
Input
- NONE
Output
- Instrument names: string[]

```sql
SELECT instrument_name FROM Instrument;
```

## Create job
Input
- is_completed: boolean
- private: boolean
Output
- job_id: int

```sql
TODO
```

## Create experiment
Input
- experiment_name: string
Output
- experiment_id: int

```sql
TODO
```

## Create JobInExperiment
Input
- job_id: int
- experiment_id: int
Output: 
- NONE

```sql
TODO
```

## Create "dummy" experiment log data
Input
- timestamp: int
- job_id: int
- instrument_id: int
- experiment_data: string
- symbolic_link: string
Output
- NONE

```sql
TODO
```


# My Experiments Screen

## Get all experiments for user
Input
- UID: int
Output
- experiment_id: int
- experiment_name: string

```sql
SELECT Experiment.experiment_id, Experiment.experiment_name FROM Experiment, UserQueuesExperiment WHERE Experiment.experiment_id=UserQueuesExperiment.experiment_id AND UserQueuesExperiment.user_id={UID};
```

## Get all jobs for experiment
Input
- experiment_id: int
Output
- job_id: int[]
- is_completed: int[]
- instrument_name: string[]
- instrument_id: int[]

```sql
SELECT Job.job_id, Job.is_completed, Instrument.instrument_name, Instrument.instrument_id FROM Job, Instrument, InstrumentInJob, JobInExperiment WHERE Job.job_id=JobInExperiment.job_id AND JobInExperiment.experiment_id={experiment_id} AND Instrument.instrument_id=InstrumentInJob.instrument_id AND InstrumentInJob.job_id=Job.job_id;
```

## Get experiment log for job
Input
- job_id: int
- instrument_id: int
Output
- timestamp: int (UNIX format)
- experiment_data: string
- symbolic_link: string

```sql
SELECT timestamp, experiment_data, symbolic_link FROM ExperimentLog WHERE instrument_id={instrument_id} AND job_id={job_id};
```


# My Groups Screen

## Remove user from group
Input
- UID: int
Output
- NONE

```sql
TODO
```

## Get all groups that have user
Input
- UID: int
Output
- group_id: int
- group_name: string

```sql
SELECT Groupss.group_id, Groupss.group_name FROM Groupss, UserInGroup WHERE Groupss.group_id=UserInGroup.group_id AND UserInGroup.user_id={UID};
```


# Browse Groups Screen

## Add user to group
Input
- UID: int
Output
- NONE

```sql
TODO
```

## Create new group
Input
- group_name: string
Output
- group_id: int

```sql
TODO
```

## Get all groups and # of members
Input
- NONE
Output
- group_id: int
- group_name: string
- member_count: int

TODO: verify this
```sql
SELECT Groupss.group_id, Groupss.group_name, member_count FROM Groupss, (SELECT group_id, COUNT(DISTINCT user_id) AS member_count FROM UserInGroup GROUP BY group_id) AS members WHERE Groupss.group_id=Members.group_id;
```

## Get all members of group
Input
- group_id: int
Output
- first_name: string
- last_name: string

```sql
SELECT first_name, last_name FROM Users, UserInGroup WHERE UserInGroup.group_id={group_id} AND UserInGroup.user_id=Users.user_id; 
```
