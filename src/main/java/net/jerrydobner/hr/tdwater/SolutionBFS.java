package net.jerrydobner.hr.tdwater;

public class SolutionBFS {

    public final int NORTH = 0;
    public final int EAST = NORTH + 1;
    public final int SOUTH = EAST + 1;
    public final int WEST = SOUTH + 1;

    private int[][] heightMap;
    private int hsz;
    private int vsz;
    private int[][][] cache;
    private int cacheHits = 0;

    public int trapRainWater(int[][] heightMap) {
        this.heightMap = heightMap;
        this.vsz = heightMap.length;
        this.hsz = heightMap[0].length;
        cache = new int[4][vsz][hsz];

        var total = 0;
        for (int i = 1; i < vsz - 1; i++) {
            for (int j = 1; j < hsz - 1; j++) {
                var vol = determineVolumeForNode(new Cursor(i, j));
                total += vol;
            }
        }
        System.out.printf("cache hits: %d\n", cacheHits);
        return total;
    }


    private int determineVolumeForNode(Cursor c) {
        debug("\ndetermineVolumeForNode(%s) [start]\n", c);
        assert !c.isOuter();
        var height = c.getHeight();
        var wallHeight = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            var nextHeight = getEffectiveHeight(c.move(i), i);
            wallHeight = Math.min(wallHeight, nextHeight);
            debug("wallHeight=%d\n", wallHeight);
        }
        var rv = Math.max(0, wallHeight - height);
        debug("determineVolumeForNode(%s) = %d [wallHeight=%d, height=%d]\n",
                c, rv, wallHeight, height);
        return rv;

    }

    private int getEffectiveHeight(Cursor c, int direction) {
        debug("getEffectiveHeight(%s, %d)\n", c, direction);
        final var height = c.getHeight();
        if (c.isOuter()) {
            debug(">>> getEffectiveHeight(%s, %d) = %d [outer]\n", c, direction, height);
            return height;
        }
        final var cached = cache[direction][c.v][c.h];
        if (cached > 0) {
            cacheHits++;
            debug(">>> getEffectiveHeight(%s, %d) = %d [cached]\n", c, direction, cached - 1);
            return cached - 1;
        }
        final var skip = (direction + 2) % 4;
        var minNeighbor = Integer.MAX_VALUE;
        push();
        for (int i = 0; i < 4; i++) {
            if (i == skip) continue;
            var neighborHeight =
                    getEffectiveHeight(c.move(i), i);
            minNeighbor = Math.min(minNeighbor, neighborHeight);
            if (minNeighbor <= height) break;
        }
        pop();

        var rv = Math.max(height, minNeighbor);
        cache[direction][c.v][c.h] = rv + 1;

        debug(">>> getEffectiveHeight(%s, %d) = %d  [minNeighbor=%d, height=%d]\n",
                c, direction, rv, minNeighbor, height);
        return rv;
    }


    class Cursor {
        final int v;
        final int h;

        Cursor(int v, int h) {
            this.v = v;
            this.h = h;
        }

        Cursor move(int direction) {
            switch (direction) {
                case NORTH:
                    return new Cursor(v - 1, h);
                case EAST:
                    return new Cursor(v, h + 1);
                case SOUTH:
                    return new Cursor(v + 1, h);
                case WEST:
                    return new Cursor(v, h - 1);
            }
            return null;
        }

        int getHeight() {
            return heightMap[v][h];
        }

        boolean isOuter() {
            return v == 0 || h == 0 || v == vsz -1 || h == hsz -1;
        }

        @Override
        public String toString() {
            return v + ":" + h;

        }
    }

    private final static ThreadLocal<StringBuffer> DEPTH = new ThreadLocal<>() {
        @Override
        protected StringBuffer initialValue() {
            return new StringBuffer();
        }
    };

    public static void push() {
        DEPTH.get().append("  ");
    }

    public static void pop() {
        var start = DEPTH.get().length() - 2;
        DEPTH.get().delete(start, start + 2);
    }

    public static String getIndent() {
        return DEPTH.get().toString();
    }

    static void debug(String format, Object ... args) {
        System.out.printf(getIndent() + format, args);
    }
}