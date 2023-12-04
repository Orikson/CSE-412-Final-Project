TODO list for CSE 412 Final Project
[x] Design flow of all screens
[x] Make list of all functionalities that need to be implemented
[x] Make list of all SQL queries required to make the above work
[x] Create PostgreSQL server and database from table creation queries we made before
[ ] Make all SQL queries
[x] Add Java PostgreSQL driver dependency into the code
[ ] Integrate SQL commands into JavaFX application
[ ] Write the User manual
[ ] Record 5 minute demo

====================
Screen functionality
====================

{SQL} - SQL query required
{JAVA} - Java computation required

Java Generics
[x] AppState
    [x] AppState.UID - Current user ID logged into the system

Login Screen
[x] Sign in
    [x] {SQL} Check credentials against credentials in databases
    [x] {JAVA} Get textfield input, send Query, give error if invalidated, GOTO Home if validated; set AppState.UID = Q.UID
[x] Sign up
    [x] {JAVA} Ensure text fields are non-empty and are validly formatted, send SQL Query; upon success, GOTO Home; set AppState.UID = Q.UID
    [x] {SQL} Create new User with provided information

Home Screen
[x] Generics
    [x] {SQL} Get first and last name of user with U.UID = AppState.UID
    [x] {JAVA} Give welcome message ("Welcome FIRST LAST")
[x] Buttons
    [x] Delete Account
        [x] {JAVA} Give confirmation message to confirm deletion, send SQL Query; on success, GOTO Login
        [x] {SQL} Delete user with U.UID = AppState.UID
    [x] Create Experiments
        [x] {JAVA} GOTO Create Experiments
    [x] My Experiments
        [x] {JAVA} GOTO My Experiments
    [x] My Groups
        [x] {JAVA} GOTO My Groups
    [x] Browse Groups
        [x] {JAVA} GOTO Browse Groups

Create Experiments Screen
[ ] Buttons
    [x] Back
        [x] {JAVA} GOTO Home
    [ ] Create Job
        [ ] {JAVA} Open menu to create a job; menu should list all instruments as radio buttons and have a checkbox for private/public
            [ ] {JAVA} Accept/Cancel buttons
            [ ] {JAVA} Upon accept, add job to table
        [ ] {SQL} Get all instrument names to list as radio buttons in menu
    [ ] Delete Job
        [ ] {JAVA} Delete currently selected job from table
        [ ] {JAVA} Give error message if no job selected
    [ ] Queue Experiment
        [ ] {JAVA} Validate all entries are non-empty, send SQL query, clear table and textfield; upon success, give a message about experiments being queued, GOTO Home
        [ ] {JAVA} Create dummy data for experiment log
        [ ] {SQL} Create each job in job list, create experiment, create relation between experiment and each job
            [ ] {SQL} Create job
            [ ] {SQL} Create experiment
            [ ] {SQL} Create JobInExperiment
            [ ] {SQL} Create "dummy" experiment log data
[ ] Table
    [ ] {JAVA} Update table after job is created
    [ ] {JAVA} Update table after job is deleted
    [ ] {JAVA} Return list of jobs for experiment in parsable format

My Experiments Screen
[x] Buttons
    [x] Back
        [x] GOTO Home
[ ] Experiments Table
    [ ] {SQL} Get all experiments associated with AppState.UID
    [ ] {JAVA} Send SQL Query, and list all experiments associated with user in table
    [ ] {JAVA} When entry is selected, send SQL Query to get all job/instruments associated with experiment; update Jobs in Experiment table with the results
    [ ] {SQL} Get all jobs associated with experiment (by experiment ID)
[ ] Jobs in Experiment Table
    [ ] {JAVA} When entry is selected, send SQL Query to get experiment log associated with the job/instrument pair; update relevant Experiment log entries 
    [ ] {SQL} Get experiment log associated with selected job/instrument pair
[ ] Experiment Log
    [ ] {JAVA} Make sure timestamp, experiment data, and symbolic link are updated when entry in Jobs in Experiment table is selected

My Groups Screen
[x] Buttons
    [x] Back
        [x] GOTO Home
    [x] Leave Group
        [x] {JAVA} Send SQL query to remove from group; give message on success/failure; send SQL Query to update Groups table
        [x] {JAVA} Give error if no group selected
        [x] {SQL} Delete AppState.UID membership from group
[x] Groups Table
    [x] {JAVA} Send SQL Query; update Groups table
    [x] {SQL} Get all groups that have AppState.UID as a member

Browse Groups Screen
[ ] Buttons
    [x] Back
        [x] GOTO Home
    [ ] Join Group
        [ ] {JAVA} Validate that a group is selected; Send SQL query using selected group to update membership; give message on success/failure; send SQL Query to update members table
        [ ] {SQL} Add membership of AppState.UID to selected group
    [ ] Create Group
        [ ] {JAVA} Open popup to enter group information such as group name
            [ ] {JAVA} Cancel button, closes popup
            [ ] {JAVA} Create Group button; sends SQL query to create group, and upon success, closes popup and updates the Group table
        [ ] {SQL} Create a new group with specified information; Make AppState.UID a member of this group
[ ] Groups Table
    [ ] {SQL} Get all groups, and the number of members in each group
[ ] Members Table
    [ ] {SQL} Get all members of a selected group
