## PARKING GARAGE MGMT SYSTEM

## Team Members
- Gunraj Singh
- Arjun Haldiya
- Fnu Hasham


## Overview
This project simulates a parking garage system where vehicles can enter, park in available spots, receive tickets, and pay fees when exiting.

The system supports different vehicle types such as cars and motorcycles, assigns parking spots dynamically, and calculates parking fees based on vehicle type and duration.

Users interact with the system through a command-line interface.


## Design

The system follows Object-Oriented Programming principles:

### Key Concepts Used
- **Abstraction**  
  - `Vehicle` is an abstract class representing shared vehicle properties.

- **Inheritance**  
  - `Car` and `Motorcycle` extend `Vehicle`.

- **Interface**  
  - `Parkable` defines parking-related behavior and is implemented by `Car` and `Motorcycle`.

- **Encapsulation**  
  - All fields are private with getters and setters.

- **Polymorphism**  
  - `Parkable` allows different vehicle types to calculate parking fees differently.


## Classes

### Core Classes
- Vehicle (abstract)
- Car
- Motorcycle
- ElectricVehicle
- PickupTruck
- ParkingSpot
- Ticket
- ParkingGarage
- PaymentSystem

### Interface
- `Parkable`


## Features
- Vehicle entry into garage
- Parking spot assignment
- Ticket generation
- Parking fee calculation
- Payment processing
- Vehicle removal from garage
- View available parking spots
- View garage status
- Supports multiple vehicle types (Car, Motorcycle, ElectricVehicle, PickupTruck)
