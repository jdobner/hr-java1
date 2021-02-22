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

    }

    @Test
    void testPark() {
        var result = parkingLot.park(MOTORCYCLE);
        assertNotNull(result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme