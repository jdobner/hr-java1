package net.jerrydobner.hr.util;

public class Util {
    public static void assertAssert() {
        boolean assertOn = false;
        assert assertOn = true;
        if (!assertOn) {
            throw new IllegalStateException("assertions are off");
        }
    }
}
