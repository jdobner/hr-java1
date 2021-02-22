package net.jerrydobner.car;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ParkedVehicle {
    private final ParkingLot.SpotTracker tracker;
    private int spots;

    public void leave() {
        if (spots == 0) {
            throw new IllegalStateException("vehicle already left");
        }
        tracker.decrement(spots);
        spots = 0;
    }

    public SpotType getSpotType() {
        return tracker.type;
    }
}
