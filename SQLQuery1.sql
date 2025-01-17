CREATE TABLE Ticket (
    [Ticket ID] INT IDENTITY(1,1) PRIMARY KEY,
    [Bus Name] NVARCHAR(50) NOT NULL,
    [Customer Name] NVARCHAR(50) NOT NULL,
    Seating VARCHAR(2) NOT NULL,
    Price INT NOT NULL,
    DayTicket DATETIME DEFAULT GETDATE(),
    Destination NVARCHAR(50) 
);