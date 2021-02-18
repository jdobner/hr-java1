package net.jerrydobner.ed.binarytree;

import net.jerrydobner.hr.util.Util;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
class TreeNodeTest {


    @BeforeEach
    void setUp() {
    }

    @Test()
    void test() {

        int[][] data = {
                {1},
                {2, 3},
                {4, 5, 6, 7}
        };

        var expected = Util.arraysToLists(data);

        var traversal = new BTraversal();
        var rootNode = traversal.arrayToTreeNode(expected);
        var parsed = traversal.treeNodeToArrays(rootNode);
        Assertions.assertEquals(parsed, expected);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme