create table AUTHOR
(
	ID INT auto_increment,
	AUTHOR VARCHAR(64) not null
);

create unique index AUTHOR_ID_UINDEX
	on AUTHOR (ID);

alter table AUTHOR
	add constraint AUTHOR_PK
		primary key (ID);

create table QUOTE
(
	ID INT auto_increment,
	AUTHOR_ID INT not null,
	QUOTE TEXT not null,
	constraint QUOTE_PK
		primary key (ID),
	constraint QUOTE_FK
		foreign key (AUTHOR_ID) references AUTHOR (ID)
);

create unique index QUOTE_ID_UINDEX
	on QUOTE (ID);
