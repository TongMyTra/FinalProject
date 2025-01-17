CREATE TABLE BusTicket (
	[Ticket ID] int identity(1,1) PRIMARY KEY NOT NULL,
	[Bus Name] nvarchar(50) NOT NULL,
	[Customer Name] nvarchar(50) NOT NULL,
	Seating nchar(10) NOT NULL,
	Price int NOT NULL,
	DayTicket nchar(10) NOT NULL

);
