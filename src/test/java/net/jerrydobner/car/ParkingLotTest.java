package net.jerrydobner.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static net.jerrydobner.car.SpotType.*;
import static net.jerrydobner.car.VehicleType.*;
import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot(Map.of(
                Motorcycle, 5,
                Regular, 15,
                Large, 5));
        checkTracker(Motorcycle, 5, 5);
        checkTracker(Regular, 15, 15);
        checkTracker(Large, 5, 5);
    }

    private void checkTracker(SpotType type, int capacity, int remaining) {
        var tracker = parkingLot.getTracker(type);
        assertEquals(capacity, tracker.getCapacity());
        assertEquals(remaining, tracker.getRemaining());
        assertEquals(capacity - remaining, tracker.getSpotsUsed());
    }


    @Test
    void testVan() {

        var parked = parkingLot.park(VAN);
        assertNotNull(parked);
        checkTracker(Large, 5, 2);

        assertNull(parkingLot.park(VAN));
        checkTracker(Large, 5, 2);

        assertNull(parkingLot.park(VAN));
        checkTracker(Large, 5, 2);

        parked.leave();
        checkTracker(Large, 5, 5);

        parked = parkingLot.park(VAN);
        assertNotNull(parked);
        checkTracker(Large, 5, 2);

        park(MOTORCYCLE, Motorcycle, 5);
        checkTracker(Motorcycle, 5, 0);

        checkTracker(Regular, 15, 15);
        park(MOTORCYCLE, Regular, 1);
        checkTracker(Regular, 15, 14);

        park(CAR, Regular, 14);
        checkTracker(Regular, 15, 0);
        park(CAR, Large, 2);
        checkTracker(Large, 5, 0);



    }

    ParkedVehicle[] park(VehicleType vehicleType, SpotType expectedType, int count) {
        var parkedVehicles = new ParkedVehicle[count];
        for (int i = 0; i < count; i++) {
            var p = parkingLot.park(vehicleType);
            assertNotNull(p);
            if (expectedType != null) assertEquals(expectedType, p.getSpotType());
            parkedVehicles[i] = p;
        }
        return parkedVehicles;
    }

    @Test
    void testPark() {
        var result = parkingLot.park(MOTORCYCLE);
        assertNotNull(result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme