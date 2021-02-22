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

import java.util.*;

import lombok.*;

import static java.lang.String.format;
import static net.jerrydobner.car.SpotType.*;
import static net.jerrydobner.car.VehicleType.*;

enum SpotType {
    Motorcycle,
    Regular,
    Large
}

enum VehicleType {
    MOTORCYCLE,
    CAR,
    VAN
}



@Data
class ParkingRule {
    final SpotType type;
    final int requiredSpots;
}




public class ParkingLot {
    private final Map<VehicleType, Map<SpotType, Integer>> parkingRules =
            new EnumMap<>(VehicleType.class);
    private final Map<SpotType, SpotTracker> trackers = new EnumMap<>(SpotType.class);

    ParkingLot(Map<SpotType, Integer> capacity) {
        addRule(MOTORCYCLE, Motorcycle, 1);
        addRule(MOTORCYCLE, Regular, 1);
        addRule(MOTORCYCLE, Large, 1);
        addRule(CAR, Regular, 1);
        addRule(CAR, Large, 1);
        addRule(VAN, Large, 3);

        capacity.entrySet().forEach(
                x -> trackers.put(x.getKey(), new SpotTracker(x.getKey(), x.getValue())));
    }

     private void addRule(VehicleType vtype, SpotType stype, int spotsRequired) {
        var rules = parkingRules.computeIfAbsent(vtype, x -> new LinkedHashMap<>());
        rules.put(stype, spotsRequired);
    }



    public SpotTracker.ParkedVehicle park(VehicleType type) {
        for (var rule : parkingRules.get(type).entrySet()) {
            var reservation = trackers.get(rule.getKey()).park(rule.getValue());
            if (reservation != null) {
                return reservation;
            }
        }
        return null;
    }

    public Map<SpotType, SpotTracker> getTrackers() {
        return Map.copyOf(trackers);
    }

    public SpotTracker getTracker(SpotType type) {
        return trackers.get(type);
    }

    public int capacity(SpotType type) {
        return trackers.get(type).getCapacity();
    }


    public int remaining(SpotType type) {
        return trackers.get(type).getRemaining();
    }


    public int occupied(SpotType type) {
        return trackers.get(type).getSpotsUsed();
    }

    @Data
    static class SpotTracker {
        private final SpotType type;
        private final int capacity;
        private int spotsUsed;


        ParkedVehicle park(int spots) {
            if (spotsUsed + spots > capacity) {
                return null;
            } else {
                spotsUsed += spots;
                return new ParkedVehicle(spots);
            }
        }

        @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
        class ParkedVehicle {
            final int spots;

            void leave() {
                if (spotsUsed < spots) throw new IllegalArgumentException(
                        format("current spots used (%d) is less than spots leaving (%d)", spotsUsed, spots));
                spotsUsed -= spots;
            }
        }

        int getRemaining() {
            return capacity - spotsUsed;
        }

    }

}
