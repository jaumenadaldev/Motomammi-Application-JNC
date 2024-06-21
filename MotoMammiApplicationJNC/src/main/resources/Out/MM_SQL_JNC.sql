create database if not exists MM_JNC;

USE MM_JNC;

CREATE TABLE MM_PROVIDERS(
     Id INT primary key auto_increment,
     CodProv VARCHAR(5) unique,
     NameProv VARCHAR(100),
     DateIni DATE,
     DateEnd DATE,
     Swiact BOOLEAN
);

CREATE TABLE MM_TRANSLATIONS(
     Id INT primary key auto_increment,
     CodProv VARCHAR(5),
     InternalCode VARCHAR(255),
     ExternalCode VARCHAR(255),
     DateIni DATE,
     DateEnd DATE,
     FOREIGN KEY (CodProv) REFERENCES MM_PROVIDERS (CodProv)
);

CREATE TABLE MM_INTERFACE(
     Id INT auto_increment primary key,
     CodExternal VARCHAR(10),
     CodProv VARCHAR(5),
     ContJson LONGTEXT,
     CreationDate TIMESTAMP,
     LastUpdate TIMESTAMP,
     CreatedBy VARCHAR(255),
     UpdatedBy VARCHAR(255),
     CodError INT,
     ErrorMessage VARCHAR(4000),
     StatusProcess varchar(10) DEFAULT 'N',
     Operation varchar(20),
     `Resource` varchar(20),
     FOREIGN KEY (CodProv) REFERENCES MM_PROVIDERS (CodProv)
);

INSERT INTO MM_PROVIDERS (Id, CodProv, NameProv, DateIni, DateEnd, Swiact) VALUES
    (1, 'CAX', 'Caixabank', '2020-01-01', NULL, True),
    (2, 'SAB', 'Sabadell', '2020-02-01', '2023-02-01', False),
    (3, 'ING', 'ING Direct', '2020-03-01', NULL, True),
    (4, 'SAN', 'Santander', '2020-04-01', '2023-04-01', False),
    (5, 'BBV', 'BBVA', '2020-05-01', '2025-05-01', True);

CREATE TABLE IF NOT EXISTS MM_CUSTOMERS(
    Id int auto_increment primary key,
    `Name` varchar(50),
    FirstName varchar(50),
    LastName varchar(50),
    BirthDate date,
    PostalCode varchar(20),
    StreetType varchar(50),
    City varchar(50),
    `NumberStreet` int,
    PhoneNumber varchar(50),
    Dni varchar(10) not null unique,
    LicenseType varchar(10),
    CarPlate varchar(10) unique,
    Email varchar(100),
    Gender varchar(20)
    );

CREATE TABLE IF NOT EXISTS MM_VEHICLES(
    Id int auto_increment primary key,
    CarPlate varchar(10) unique,
    VehicleType varchar(50),
    Brand varchar(50),
    Model varchar(50),
    FOREIGN KEY (CarPlate) references MM_CUSTOMERS(CarPlate)
    );

CREATE TABLE IF NOT EXISTS MM_PARTS(
    Id int auto_increment primary key,
    Dni varchar(10) not null,
    ClaimNumber varchar(20) unique,
    PolicyNumber varchar(20),
    ClaimDate date,
    `Description` varchar(255),
    `Status` varchar(20),
    Amount double,
    FOREIGN KEY (Dni) references MM_CUSTOMERS(Dni)
    );

CREATE TABLE IF NOT EXISTS MM_INVOICES(
    Id int auto_increment primary key,
    InvoiceNumber varchar(20) not null unique,
    IssueDate date,
    CodProv varchar(5),
    Dni varchar(10),
    CarPlate varchar(10),
    Cost double,
    Send boolean default false,
    FOREIGN KEY (Dni) REFERENCES MM_CUSTOMERS(Dni),
    FOREIGN KEY (CodProv) REFERENCES MM_PROVIDERS(CodProv),
    FOREIGN KEY (CarPlate) REFERENCES MM_VEHICLES(CarPlate)
    );

INSERT INTO MM_INVOICES (InvoiceNumber, IssueDate, CodProv, Dni, CarPlate, Cost, Send) VALUES
     ('INV001', '2024-05-28', 'CAX', '12345678-Z', '1234ABC', 2000.50, false),
     ('INV002', '2024-05-02', 'CAX', '23456789-Y', '2345BCD', 1500.00, false),
     ('INV003', '2024-05-28', 'CAX', '34567890-X', '3456CDE', 5000.00, false),
     ('INV004', '2024-01-04', 'CAX', '45678901-W', '4567DEF', 750.00, false),
     ('INV005', '2024-01-05', 'CAX', '56789012-V', '5678EFG', 300.00, false);


INSERT INTO MM_TRANSLATIONS (CodProv, InternalCode, ExternalCode, DateIni, DateEnd) VALUES
     ('CAX', 'Carrer', 'Calle', '2020-01-01', '2025-05-01'),
     ('CAX', 'Passeig', 'Paseo', '2020-01-01', '2025-05-01'),
     ('CAX', 'Avinguda', 'Avenida', '2020-01-01', '2025-05-01'),
     ('ING', 'Ronda', 'Ronda', '2020-03-01', NULL),
     ('ING', 'Plaça', 'Plaza', '2020-03-01', NULL),
     ('ING', 'Camí', 'Camino', '2020-03-01', NULL),
     ('SAN', 'Via', 'Via', '2020-04-01', '2023-04-01'),
     ('SAN', 'Rambla', 'Rambla', '2020-04-01', '2023-04-01'),
     ('SAN', 'Carretera', 'Carretera', '2020-04-01', '2023-04-01'),
     ('BBV', 'Bulevard', 'Bulevar', '2020-05-01', NULL),
     ('BBV', 'Travessera', 'Travesía', '2020-05-01', NULL),
     ('BBV', 'Gran Via', 'Gran Vía', '2020-05-01', NULL);