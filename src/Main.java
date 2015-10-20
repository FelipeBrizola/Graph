import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GraphMatAdj<String, Integer> g = new GraphMatAdj<>(15);

		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		g.addNode("D");
		g.addNode("E");
		g.addNode("F");
		g.addNode("G");
		g.addNode("H");
		g.addNode("I");

		g.addEdge("A", "B", 2);
		g.addEdge("A", "C", 3);
		g.addEdge("C", "D", 5);
		g.addEdge("B", "E", 1);
		g.addEdge("C", "E", 7);
		g.addEdge("D", "E", 9);
		g.addEdge("D", "H", 1);
		g.addEdge("E", "F", 8);
		g.addEdge("E", "G", 4);
		g.addEdge("H", "G", 11);
		g.addEdge("G", "I", 12);

		g.addEdge("B", "A", 2);
		g.addEdge("C", "A", 3);
		g.addEdge("D", "C", 5);
		g.addEdge("E", "B", 1);
		g.addEdge("E", "C", 7);
		g.addEdge("E", "D", 9);
		g.addEdge("H", "D", 1);
		g.addEdge("F", "E", 8);
		g.addEdge("G", "E", 4);
		g.addEdge("G", "H", 11);
		g.addEdge("I", "G", 12);

		System.out.println(g.hasPath("D", "B"));

		System.out.println(g.traversalDepth("A"));

		System.out.println(g.pathOrigDest("A", "G"));
		System.out.println(g.pathOrigDest("A", "E"));
		System.out.println(g.pathDestOrigSumEdge("A", "I"));
		System.out.println(g.pathOrigDest("A", "I"));
		System.out.println(g.pathOrigDest("E", "H"));
		System.out.println(g.pathOrigDest("I", "G"));
		System.out.println(g.pathPassTarget("A", "G", "D"));

	}

}
