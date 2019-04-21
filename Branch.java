import java.awt.Color;
import java.util.ArrayList;

public class Branch
{
	Edge edge;
	double width;
	Color color;
	
	public Branch(Edge edge, double width, Color color)
	{
		this.edge = edge;
		this.width = width;
		this.color = color;
	}
	
	public ArrayList<Branch> branchOut(
			ArrayList<Double> childPositionRatioList,
			ArrayList<Double> childAngleList,
			double childLengthRatio,
			int childWidth,
			Color childColor)
	{
		ArrayList<Branch> branches = new ArrayList<Branch>();
		int n = childPositionRatioList.size();
		for(int i=0;i<n;i++)
		{
			double positionRatio = childPositionRatioList.get(i);
			double angle = childAngleList.get(i);
			
			Point childBasePoint = edge.weightedPosition(positionRatio);
			Point childPoint1 = edge.weightedPosition(1-childLengthRatio);
			Point childPoint2 = edge.p2.copy();
			Edge childEdge = new Edge(childPoint1, childPoint2);
			childEdge.rebase(childBasePoint);
			childEdge.rotate(childBasePoint, angle);
			Branch childBranch = new Branch(childEdge, childWidth, childColor);
			branches.add(childBranch);
		}
		return branches;
	}
	
	public static ArrayList<Branch> createBranches(ArrayList<Edge> edges, double width, Color color)
	{
		ArrayList<Branch> branches = new ArrayList<Branch>();
		for(Edge edge : edges)
			branches.add(new Branch(edge, width, color));
		return branches;
	}
	
	public static ArrayList<Branch> combineBranches(ArrayList<Branch> container, ArrayList<Branch> elements)
	{
		for(Branch element : elements)
			container.add(element);
		return container;
	}
	
}
