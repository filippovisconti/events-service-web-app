# siw-events-service

A web app that helps in the management of an event management company, built with Springboot and deployed to Azure

### Requisites

We are going to design and implement a web application for an event management company.

The company can take care of various types of events:
each event has

- a name,
- a description,
- an organizer,
- a start time,
- a duration,
- a list of one or more activities,
- and a list of participants.

An organizer can take care of one or more events.

For each organizer, it's

- first name,
- last name
- and place of birth

are of interest.

For each proposed activity, we need to keep track of

- name,
- description,
- duration (number of hours),
- (a list of) necessary pieces of equipment

For each piece of equipment (or tool), it's going to be recorded

- its name,
- description,
- and cost.

The system can be accessed by administrators and standard users.
The viewing of the events is available without authentication, whereas everything else needs the user to be logged in.
Standard users can sign up for any event, and browse through all of the detail pages, but no editing will be allowed.
Standard user will be able to see the list of events which they have signed up for.

Admins, instead, can create every object and manage every single aspect of the system.

### URI standard

Resources available to **unauthenticated** users will start with the "/public/" prefix.

Resources only available to **authenticated** users will start with the "/protected/" prefix.

Resources only available to **administrators** will start with the "/admin/" prefix.

### Class diagram

For a better understanding of the persistence layer, a class diagram is available as PDF and also as editable .drawio file.
