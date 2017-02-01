create table Movie(
    mvName varchar(20),
    mvDesc varchar(800),
    primary key(mvName)
);

create table Location(
    zipcode char(5),
    city varchar(20),
    primary key(zipcode)
);

create table Theater(
    theaterName varchar(20),
    zipcode char(5),
    foreign key(zipcode) references Location(zipcode),
    primary key(theaterName)
);

create table Showing(
    showingID char(6),
    theaterName varchar(20),
    mvName varchar(20),
    foreign key(theaterName) references Theater(theaterName),
    foreign key(mvName) references Movie(mvName),
    primary key(showingID)
);

create table Showtime(
    viewID char(6),
    mvTime varchar(6),
    mvName varchar(20),
    foreign key(mvName) references Movie(mvName),
    primary key(viewID)
);
