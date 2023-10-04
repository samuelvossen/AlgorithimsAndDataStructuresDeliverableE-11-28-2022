import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	public DelivC(File in, Graph gr) {
		inputFile = in;
		g = gr;

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
		String outputFileName = baseFileName.concat("_out.txt");
		outputFile = new File(outputFileName);
		if (outputFile.exists()) { // For retests
			outputFile.delete();
		}

		try {
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		System.out.println("DelivC:  To be implemented");
		output.println("DelivC:  To be implemented");
		output.flush();
		// --------------------------------------------------------------------------------

		ArrayList<Node> list = g.nodeList;
		Node startNode = null;
		int tentativeLength = 0;
		Queue<Node> q = new LinkedList<Node>();
		PriorityQueue<Node> tempQ = new PriorityQueue<>(new CompareEdge());
		String gg = "S";
		PriorityQueue<Node> pq = new PriorityQueue<>(new CompareEdge());
		for (Node n : list) {
			if (n.getVal().compareToIgnoreCase(gg) == 0) {
				n.setDistancefromStart(0);
				startNode = n;
				pq.add(n);

			} else {
				n.setDistancefromStart(Integer.MAX_VALUE);
				pq.add(n);

			}
		}
		System.out.println("");
		System.out.println("PATH" + "          " + "DIST");
		System.out.println(pq.peek().getAbbrev() + "          " + pq.peek().getDistancefromStart());

		for (Edge edge : pq.peek().getOutgoingEdges()) {
			if (edge.getDist() + edge.getTail().getDistancefromStart() < edge.getHead().getDistancefromStart()) {
				pq.remove(edge.getHead());
				edge.getHead().setDistancefromStart(edge.getDist() + edge.getTail().getDistancefromStart());
				pq.add(edge.getHead());
				edge.getHead().setPrevNode(edge.getTail());
				tempQ.add(edge.head);

				System.out.println(edge.getTail().getAbbrev() + "->" + edge.getHead().getAbbrev() + "          "
						+ edge.getHead().getDistancefromStart());
			}
		}
		boolean stop = false;
		while (pq.size() > 0 && stop == false) {
			if (pq.peek().getVal().compareToIgnoreCase("G") == 0) {
				break;
			}
			System.out.println(pq.peek().getAbbrev());
			if (tempQ.size() > 0) {
				tentativeLength = tempQ.peek().getDistancefromStart();
			}
			tempQ.removeAll(tempQ);
			pq.remove();
			for (Edge edge : pq.peek().getOutgoingEdges()) {

				int tempNum = edge.getDist() + edge.getTail().getDistancefromStart();
				if (edge.getDist() + edge.getTail().getDistancefromStart() < edge.getHead().getDistancefromStart()
						&& edge.getHead().getAbbrev()
								.compareToIgnoreCase(edge.getTail().getPrevNode().getAbbrev()) != 0) {
					pq.remove(edge.getHead());
					edge.getHead().setDistancefromStart(edge.getDist() + edge.getTail().getDistancefromStart());
					pq.add(edge.getHead());
					edge.getHead().setPrevNode(edge.getTail());
					tempQ.add(edge.head);

					System.out.println(edge.getTail().getAbbrev() + "->" + edge.getHead().getAbbrev() + "          "
							+ edge.getHead().getDistancefromStart());
					if (edge.getHead().getVal().compareToIgnoreCase("G") == 0) {
						stop = true;
						break;

					}
				}
				if (edge.getHead().getAbbrev().compareToIgnoreCase(edge.getTail().getPrevNode().getAbbrev()) != 0) {
					System.out.println("test: " + edge.getTail().getAbbrev() + "->" + edge.getHead().getAbbrev()
							+ "          " + tempNum);
				}

			}

		}

	}

	public String pathfinder(Node node, Node startNode) {
		Node tempNode = node;
		String string = tempNode.getAbbrev();
		while (tempNode.getVal().compareToIgnoreCase("S") != 0) {
			string = tempNode.getPrevNode().getAbbrev() + "->" + string;
		}
		string = startNode.getAbbrev() + "->" + string;
		return string;
	}

	public class CompareEdge implements Comparator<Node> {// creates a comparator class called compareEdges

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			return compareTo(o1, o2); // calls comparTo method
		}

		public int compareTo(Node e1, Node e2) {
			if (e1.getDistancefromStart() > e2.getDistancefromStart()) {// compares if node e1 is larger than node e2,
																		// if so returns 1
				return 1;
			}
			if (e1.getDistancefromStart() < e2.getDistancefromStart()) {// compares if node e1 is smaller than node e2,
																		// if so returns -1
				return -1;
			}
			return e1.getAbbrev().compareToIgnoreCase(e2.getAbbrev());// returns either 1, -1, or zero, based on
			// Alphabetical order

		}
	}

}
