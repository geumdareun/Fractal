import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 1L;

	Main main;
	ArrayList<Branch> branches = new ArrayList<Branch>();
	double rotation = 0;
	
	public Panel(Main main)
	{
		this.main = main;
		setBackground(Color.BLACK);
	}
	
	public synchronized void clear()
	{
		branches.clear();
	}
	
	public synchronized void addRotation(double theta)
	{
		this.rotation+=theta;
	}

	public synchronized void add(Branch branch)
	{
		branches.add(branch);
	}

	public void add(ArrayList<Branch> branches)
	{
		for(Branch branch : branches)
			add(branch);
	}

	private void paintBranches(Graphics2D g)
	{
		for(Branch branch : branches)
		{
			g.setColor(branch.color);
			g.setStroke(new BasicStroke((int) branch.width));
			Point p1 = branch.edge.p1;
			Point p2 = branch.edge.p2;
			g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
		}
	}

	@Override
	public synchronized void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(getWidth()/2, getHeight()/2);
		//g2.translate(0, 130);
		g2.rotate(rotation);
		paintBranches(g2);
	}
}