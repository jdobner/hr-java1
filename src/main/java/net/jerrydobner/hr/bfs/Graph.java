package net.jerrydobner.hr.bfs;

import java.util.*;

public class Graph {
    private final Map<Integer, Node> nodeLookup;

    public static class Node {
        final int id;
        final List<Node> adjacentNodes = new ArrayList<>();

        public Node(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return id == node.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" +
                     id +
                    ", adjacent=" + adjacentNodes +
                    '}';
        }

        public boolean hasPathDFS(int target) {
            return hasPathDFS(target, new HashSet<>());
        }

        private boolean hasPathDFS(int target, Set<Integer> visited) {
            if (target == this.id) return true;
            if (visited.add(this.id)) {
                for (var node : adjacentNodes) {
                    if (node.hasPathDFS(target, visited)) return true;
                }
            }
            return false;
        }


        public boolean searchForSum(int s) {
            return searchForSum(s, 0, new HashSet<>());
        }

        private boolean searchForSum(int s, int sum, Set<Integer> visited) {
            sum += this.id;
            if (sum == s) {
                return true;
            } else if (sum < s) {
                if (visited.add(this.id)) {
                    for (var node : adjacentNodes) {
                        if (node.searchForSum(s, sum, visited)) return true;
                    }
                }
            }

            return false;
        }

        public boolean hasPathBFS(int target) {
            return !findShortestPath(target).isEmpty();
        }

        private static class SearchItem {
            final SearchItem parent;
            final Node node;
            final private String description;


            SearchItem(Node node, SearchItem parent) {
                this.node = node;
                this.parent = parent;
                var s = new StringBuffer(getClass().getSimpleName()).append(" [");
                description = describe(s).append("]").toString();
            }

            private SearchItem newItem(Node node) {
                return new SearchItem(node, this);
            }

            List<Node> getFullPath() {
                var newPath = new LinkedList<Node>();
                for (var item = this; item != null; item = item.parent) {
                    newPath.addFirst(item.node);
                }
                //Stream.iterate(this, it -> it.parent != null, it -> it.parent).map(it -> it.node).forEach(newPath::addFirst);
                return newPath;
            }

            SearchItem getParent() {
                return parent;
            }

            private StringBuffer describe(StringBuffer s) {
                if (parent != null) {
                    parent.describe(s).append("->");
                }
                return s.append(node.id);
            }

            @Override
            public String toString() {
                return description;
            }
        }

        public List<Node> findShortestPath(int target) {
            final Set<Node> visited = new HashSet<>();
            var q = new LinkedList<>(Collections.singleton(new SearchItem(this, null)));
            do {
                final var newQ = new LinkedList<SearchItem>();
                for (var item : q) {
                    if (item.node.id == target) {
                        return item.getFullPath();
                    } else {
                        visited.add(item.node);
                        item.node.adjacentNodes.stream().filter(x -> !visited.contains(x))
                                .map(item::newItem).forEach(newQ::add);
                    }
                }
                q = newQ;
            } while (!q.isEmpty());
            return Collections.emptyList();
        }
    }


    private Graph(Map<Integer, Node> data) {
        this.nodeLookup = data;
    }

    public Node getNode(int id) {
        return nodeLookup.get(id);
    }


    public static Graph from(int[][] edges, boolean bidirectional) {
        var data = new HashMap<Integer, Node>();
        for (var pair : edges) {
            assert pair.length == 2;
            Node node1 = data.computeIfAbsent(pair[0], Node::new);
            Node node2 = data.computeIfAbsent(pair[1], Node::new);
            node1.adjacentNodes.add(node2);
            if (bidirectional) {
                node2.adjacentNodes.add(node1);
            }
        }
        return new Graph(data);
    }
}
