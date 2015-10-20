import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class GraphMatAdj<N, A> {

	private class Node<N> {
		public Node(N elem) {
			this.elem = elem;
			unMark();
		}

		private N elem;
		private boolean mark;

		private void mark() {
			this.mark = true;
		}

		private void unMark() {
			this.mark = false;
		}

		private boolean isMarked() {
			return this.mark;
		}

		private N getElem() {
			return elem;
		}

		private void setElem(N elem) {
			this.elem = elem;
		}
	}

	private ArrayList<Node> lNodes;
	private A[][] matAdj;

	private void unMarkAllNodes() {
		for (int i = 0; i < lNodes.size(); i++)
			lNodes.get(i).unMark();
	}

	private void markAllNodes() {
		for (int i = 0; i < lNodes.size(); i++)
			lNodes.get(i).mark();
	}

	public GraphMatAdj(int cap) {
		lNodes = new ArrayList<Node>();
		matAdj = (A[][]) new Object[cap][cap];
	}

	public Node<N> getNodeFromElem(N node) {
		for (int i = 0; i < lNodes.size(); i++) {
			if (node.equals(lNodes.get(i).getElem()))
				return lNodes.get(i);
		}
		return null;
	}

	public ArrayList<Node> getAllAdj(N node) {
		int indexFromNode = getIndexFromNode(node);
		ArrayList<Node> res = new ArrayList<Node>();
		for (int i = 0; i < matAdj.length; i++)
			if (matAdj[indexFromNode][i] != null)
				res.add(lNodes.get(i));
		return res;
	}

	public int getIndexFromNode(N node) {
		for (int i = 0; i < lNodes.size(); i++)
			if (node.equals(lNodes.get(i).getElem()))
				return i;
		return -1;
	}

	public void addNode(N node) {
		Node<N> n = new Node<>(node);
		lNodes.add(n);
	}

	public void addEdge(N nodeA, N nodeB, A edge) {
		int indexA = getIndexFromNode(nodeA);
		int indexB = getIndexFromNode(nodeB);
		if (indexA != -1 && indexB != -1)
			matAdj[indexA][indexB] = edge;
	}

	public ArrayList<N> pathOrigDest(N orig, N dest) {
		unMarkAllNodes();
		return pathOrigDestAux(orig, dest);
	}

	private ArrayList<N> pathOrigDestAux(N orig, N dest) {
		int i = getIndexFromNode(orig);
		ArrayList<N> res = null;
		if (orig.equals(dest)) {
			res = new ArrayList<>();
			res.add(dest);
			return res;
		}
		lNodes.get(i).mark();

		for (Node<N> n : this.getAllAdj(orig)) {
			if (!n.isMarked())
				res = pathOrigDestAux(n.getElem(), dest);
			if (res != null) {
				res.add(0, orig);
				return res;
			}
		}
		return res;
	}

	public int pathDestOrigSumEdge(N orig, N dest) {
		unMarkAllNodes();
		int total = -1;
		return pathDestOrigSumEdgeAux(orig, dest, total);
	}

	private int pathDestOrigSumEdgeAux(N orig, N dest, int total) {
		int origIndex = getIndexFromNode(orig);
		lNodes.get(origIndex).mark();

		if (orig.equals(dest)) {
			total = 0;
			return total;
		}
		for (Node<N> n : getAllAdj(orig)) {
			if (!n.isMarked())
				total = pathDestOrigSumEdgeAux(n.getElem(), dest, total);

			if (total >= 0) {
				total += (int) getEdgeValue(orig, n.getElem());
				return (total);
			}
		}
		return total;
	}

	public ArrayList<N> pathAvoidTarget(N orig, N dest, N target) {
		unMarkAllNodes();
		Node<N> n = getNodeFromElem(target);
		n.mark();
		return pathOrigDestAux(orig, dest);
	}

	private A getEdgeValue(N orig, N dest) {
		int indexOrig = getIndexFromNode(orig);
		int indexDest = getIndexFromNode(dest);
		A edge = matAdj[indexOrig][indexDest];
		return edge;
	}

	public ArrayList<N> pathPassTarget(N orig, N dest, N target) {
		ArrayList<N> res = null;
		ArrayList<N> firstPath = new ArrayList<>(pathOrigDest(orig, target));
		ArrayList<N> secondtPath = null;
		if (firstPath != null) {
			secondtPath = new ArrayList<>(pathOrigDestAux(
					firstPath.get(firstPath.size() - 1), dest));
		}
		if (secondtPath != null) {
			res = new ArrayList<>();
			res.addAll(firstPath);
			res.addAll(firstPath.size() - 1, secondtPath);
			res.remove(res.size() - 1);
		}
		return res;
	}

	private ArrayList<N> traversalDepthAux(N orig, ArrayList<N> res) {
		Node<N> n = getNodeFromElem(orig);
		n.mark();
		res.add(n.getElem());

		for (Node<N> node : getAllAdj(n.getElem())) {
			if (!node.isMarked())
				traversalDepth(node.getElem());
		}
		return res;
	}

	public ArrayList<N> traversalDepth(N orig) {
		ArrayList<N> res = new ArrayList<N>();
		return traversalDepthAux(orig, res);
	}

	private List<N> traversalWidthAux(N orig, ArrayList<N> res) {
		Node<N> n = getNodeFromElem(orig);
		Queue<N> q = new LinkedList<N>();
		q.add(orig);
		n.mark();
		while (q.size() != 0) {
			N aux = q.poll();
			res.add(aux);

			for (Node<N> node : getAllAdj(aux)) {
				if (!node.isMarked()) {
					node.mark();
					q.add(node.getElem());
				}
			}
		}
		return res;
	}

	public List<N> traversalWidth(N orig) {
		unMarkAllNodes();
		ArrayList<N> res = new ArrayList<N>();
		return traversalWidthAux(orig, res);
	}

	public boolean hasPath(N orig, N dest) {
		boolean res = false;
		Node<N> n = getNodeFromElem(orig);
		n.mark();

		for (Node<N> node : getAllAdj(n.getElem())) {
			if (!node.isMarked())
				if (node.getElem().equals(dest))
					res = true;
				else
					res = hasPath(node.getElem(), dest);
			if (res)
				return res;
		}
		return res;
	}
}
