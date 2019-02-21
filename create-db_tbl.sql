create database FinalProject; 
use FinalProject;

create table Item(
	ID int auto_increment,
    ItemCode varchar(10) unique not null,
    ItemDescription varchar(50),
    Price decimal(4,2) default 0.0,
    primary key(ID));
    
create table Purchase(
	ID int auto_increment,
	ItemID int, 
    foreign key (ItemID) references Item(ID),
    Quantity int not null,
    PurchaseDate datetime default now(),
	primary key(ID));
    
create table Shipment(
	ID int auto_increment,
    ItemID int, 
    foreign key (ItemID) references Item(ID),
    Quantity int not null,
    ShipmentDate date unique not null,
    primary key(ID));