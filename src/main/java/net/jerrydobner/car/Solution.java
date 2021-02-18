package net.jerrydobner.car;
/*
Design a parking lot using object-oriented principles

Goals:
- Your solution should be in Java - if you would like to use another language, please let the interviewer know.
- Boilerplate is provided. Feel free to change the code as you see fit

Assumptions:
- The parking lot can hold motorcycles, cars and vans
- The parking lot has motorcycle spots, car spots and large spots
- A motorcycle can park in any spot
- A car can park in a single compact spot, or a regular spot
- A van can park, but it will take up 3 regular spots
- These are just a few assumptions. Feel free to ask your interviewer about more assumptions as needed

Here are a few methods that you should be able to run:
- Tell us how many spots are remaining
- Tell us how many total spots are in the parking lot
- Tell us when the parking lot is full
- Tell us when the parking lot is empty
- Tell us when certain spots are full e.g. when all motorcycle spots are taken
- Tell us how many spots vans are taking up

Hey candidate! Welcome to your interview. I'll start off by giving you a Solution class. To run the code at any time, please hit the run button located in the top left corner.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.IntStream;

class Solution {



    public static void main(String[] args) {
        new Solution().run();
    }

    public void run() {
    }



}

enum SpotType {
    Motorcycle,
    Regular,
    Large
}

enum VehicleType {
    MOTORCYCLE( new ParkingRule[] {
            rule(SpotType.Motorcycle, 1),
            rule(SpotType.Regular, 1),
            rule(SpotType.Large, 1)}),
    CAR( new ParkingRule[] {
            rule(SpotType.Motorcycle, 0),
            rule(SpotType.Regular, 1),
            rule(SpotType.Large, 1)}),
    VAN( new ParkingRule[] {
            rule(SpotType.Motorcycle, 0),
            rule(SpotType.Regular, 3),
            rule(SpotType.Large, 1)});


    final ParkingRule[] rules;

    VehicleType(ParkingRule rules[]) {
        this.rules = rules;
    }

    static ParkingRule rule(SpotType type, int spotsNeeded) {
        return null;
    }

}

abstract class Vehicle {
    final static AtomicInteger counter = new AtomicInteger();
    final VehicleType type;
    final Integer id;

    Vehicle(VehicleType type) {
        this.id = counter.incrementAndGet();
        this.type = type;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object other) {
        var otherVehicle = (Vehicle)other;
        return id.equals(otherVehicle.id);
    }

}

class ParkingRule {
    final SpotType type;
    final int requiredSpots;

    public ParkingRule(SpotType type, int requiredSpots) {
        this.type = type;
        this.requiredSpots = requiredSpots;
    }
}

abstract class VehicleType {

    final SpotType[] preferenceOrder;

    VehicleType(SpotType order) {
        this.preferenceOrder = order;
    }

    public abstract int requiredSpots(SpotType type);

}

class Motorcycle extends Vehicle {

    public int requiredSpots(SpotType type) {
        return 1;
    }
}

class Car extends Vehicle {
    public int requiredSpots(SpotType type) {
        return type == SpotType.Motorcycle ? 0 : 1;
    }
}







class ParkingLot {
    int[] spots;

}
