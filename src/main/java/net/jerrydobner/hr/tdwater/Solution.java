package net.jerrydobner.hr.tdwater;

import java.util.Arrays;

public class Solution {

    final int NORTH = 0;
    final int EAST = NORTH + 1;
    final int SOUTH = EAST + 1;
    final int WEST = SOUTH + 1;

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
        System.out.printf(format, args);
    }


    public int trapRainWater(int[][] heightMap) {
        final var vsz = heightMap.length;
        final var hsz = heightMap[0].length;
        final var nodes = new Node[vsz][hsz];

        for (int i = 0; i < vsz; i++) {
            for (int j = 0; j < hsz; j++) {
                var description = i + ":" + j;
                if (i == 0 || i == vsz - 1 || j == 0 || j == hsz - 1) {
                    nodes[i][j] = new Node(description, heightMap[i][j]);
                } else {
                    nodes[i][j] = new InnerNode(description, heightMap[i][j]);
                }
            }
        }
        for (int i = 1; i < vsz - 1; i++) {
            for (int j = 1; j < hsz - 1; j++) {
                var n = nodes[i][j];
                n.setNeighbor(NORTH, nodes[i - 1][j]);
                n.setNeighbor(EAST, nodes[i][j + 1]);
                n.setNeighbor(SOUTH, nodes[i + 1][j]);
                n.setNeighbor(WEST, nodes[1][j - 1]);
            }
        }

        var total = 0;
        for (int i = 1; i < vsz - 1; i++) {
            for (int j = 1; j < hsz - 1; j++) {
                var vol = nodes[i][j].getVolume();
                total += vol;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int[][] input = {
                {1, 4, 3, 1, 3, 2},
                {3, 2, 1, 3, 2, 4},
                {2, 3, 3, 2, 3, 1}
        };
        var result = new Solution().trapRainWater(input);
        assert result == 4 : "result is not 4, instead it's " + result;
        Solution.debug("result: %s\n\n\n", result);
        int[][] input2 = {{12,13,1,12},{13,4,13,12},{13,8,10,12},{12,13,12,12},{13,13,13,13}};

        result = new Solution().trapRainWater(input);
        //assert result == 4 : "result is not 4, instead it's " + result;
        Solution.debug("result: %s\n", result);

    }


}

class Node {
    final int height;
    final String description;

    Node(String description, int height) {
        this.description =
                (getClass() == Node.class ? "N " : "I ") + description + " (" + height + ")";
        this.height = height;
    }

    int getHeight(int direction) {
        Solution.debug(
                "%s%s [GH] dir=%d\n", Solution.getIndent(), description, direction);
        //if (depth == 0) new Exception("test").printStackTrace();
        return height;
    }

    void setNeighbor(int direction, Node node) {
        throw new UnsupportedOperationException();
    }

    int getVolume() {
        throw new UnsupportedOperationException("Outer Node has no volume");
    }

    @Override
    public String toString() {
        return description;
    }

    public String describeConnections() {
        return "";
    }
}


class InnerNode extends Node {
    final Node[] neighbors;
    Integer[] heights = new Integer[4];
    String neighborsAsString;

    InnerNode(String description, int height) {
        super(description, height);
        neighbors = new Node[4];
    }

    public int getHeight(int direction) {
        var skip = (direction + 2) % 4;
        var minNeighbor = Integer.MAX_VALUE;
        Solution.debug(
                "%s%s [gh] dir=%d, skip=%d, neighbors=%s\n",
                Solution.getIndent(), description, direction, skip, describeConnections());
        if (heights[direction] == null) {
            Solution.push();
            for (int i = 0; i < 4; i++) {
                if (i == skip) continue;
                var neighborHeight = neighbors[i].getHeight(i);
                minNeighbor = Math.min(minNeighbor, neighborHeight);
                if (neighborHeight <= height) break;
            }
            heights[direction] = Math.max(height, minNeighbor);
            Solution.pop();
        }
        Solution.debug(
                "%s%s [GH] dir=%d, skip=%d, minNeighbor=%d, effectiveHeight=%d neighbors=%s\n",
                Solution.getIndent(), description, direction, skip, minNeighbor, heights[direction], describeConnections());
        return heights[direction];
    }

    int getVolume() {
        Solution.debug("%s [gv] neighbors=%s\n", description, describeConnections());
        Solution.push();
        var wallHeight = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            wallHeight = Math.min(wallHeight, neighbors[i].getHeight(i));
        }
        var rv = Math.max(0, wallHeight - height);
        Solution.pop();
        Solution.debug("%s [GV] wallHeight=%d, vol=%d, neighbors=%s\n\n",
                description, wallHeight, rv, describeConnections());
        return rv;
    }

    @Override
    void setNeighbor(int direction, Node node) {
        neighbors[direction] = node;
    }

    public String describeConnections() {
        if (null == neighborsAsString) {
            neighborsAsString = Arrays.toString(neighbors);
        }
        return neighborsAsString;
    }
}