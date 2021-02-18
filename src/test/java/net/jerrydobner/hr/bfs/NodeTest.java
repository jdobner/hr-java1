package net.jerrydobner.hr.bfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class NodeTest {

    final Graph graph = buildGraph();

    @Test
    void testHasPathDFS1to10() {
        Assertions.assertTrue(graph.getNode(1).hasPathDFS(10));
    }

    @Test
    void testHasPathDFS4to10() {
        Assertions.assertFalse(graph.getNode(4).hasPathDFS(10));
    }


    @Test
    void testHasPathBFS1to10() {
        Assertions.assertTrue(graph.getNode(1).hasPathBFS(10));
    }

    @Test
    void testShortestPathBFS1to10() {
        var expected = List.of(1, 2, 7, 10);
        var result = nodeListToIntList(graph.getNode(1).findShortestPath(10));
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testShortestPathBFS2to8() {
        Assertions.assertEquals(
                Collections.emptyList(),
                nodeListToIntList(graph.getNode(2).findShortestPath(8)),
                "there should be no path from 2 to 8");
    }

    @Test
    void testHasPathBFS4to10() {
        Assertions.assertFalse(graph.getNode(4).hasPathBFS(10));
    }

    @Test
    void testSearchFor10(){
        search(1, 10, true);
    }

    @Test
    void testSearchFor20(){
        search(1, 20, true);
    }


    @Test
    void testSearchFor1From2(){
        search(2, 1, false);
    }

    void search(int node, int targetSum, boolean expected) {
        Assertions.assertEquals(expected, graph.getNode(node).searchForSum(targetSum));
    }

    List<Integer> nodeListToIntList(List<Graph.Node> input) {
        var output = new ArrayList<Integer>(input.size());
        input.stream().map(x -> x.id).forEach(output::add);
        return output;
    }
    Graph buildGraph() {
        int[][] edges = {
                {1,2},
                {1, 3},
                {1, 4},
                {2, 5},
                {2, 6},
                {2, 7},
                {3, 7},
                {4, 8},
                {5, 9},
                {7, 10},
                {9, 10},
        };
        return Graph.from(edges, false);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme