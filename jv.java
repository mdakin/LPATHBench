import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

class LongestPathFinder {

    ArrayList<Node> readPlaces() throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("agraph"));
        String s = in.readLine();
        int numNodes = Integer.parseInt(s);
        ArrayList<Node> nodes = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node());
        }
        while (in.ready()) {
            String ln = in.readLine();
            String[] nums = ln.split("[ \t]+");
            if (nums.length != 3) {
                break;
            }
            int node = Integer.parseInt(nums[0]);
            int neighbour = Integer.parseInt(nums[1]);
            int cost = Integer.parseInt(nums[2]);
            nodes.get(node).neighbours.add(new Route(neighbour, cost));
        }
        in.close();
        return nodes;
    }

    int getLongestPath(ArrayList<Node> nodes, int nodeID, boolean[] visited) {
        visited[nodeID] = true;
        int dist, max = 0;
        for (Route neighbour : nodes.get(nodeID).neighbours) {
            if (!visited[neighbour.dest]) {
                dist = neighbour.cost + getLongestPath(nodes, neighbour.dest, visited);
                if (dist > max) {
                    max = dist;
                }
            }
        }
        visited[nodeID] = false;
        return max;
    }
}

class Route {
    int dest, cost;

    Route(int dest, int cost) {
        this.dest = dest;
        this.cost = cost;
    }
}

class Node {
    ArrayList<Route> neighbours = new ArrayList<>();
}

class jv {
    public static void main(String[] args) throws Exception {
        LongestPathFinder p = new LongestPathFinder();
        ArrayList<Node> nodes = p.readPlaces();
        boolean[] visited = new boolean[nodes.size()];
        Arrays.fill(visited, false);
        long start = System.currentTimeMillis();
        int len = p.getLongestPath(nodes, 0, visited);
        long duration = System.currentTimeMillis() - start;
        System.out.printf("%d LANGUAGE Java %d\n", len, duration);
    }
}
