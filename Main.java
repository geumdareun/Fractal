import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame
{
	private static final long serialVersionUID = 1L;

	Panel panel = new Panel(this);

	public static final double GOLDEN_RATIO = 1.61803398875;
	
	public Main()
	{
		setTitle("Fractal");
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setContentPane(panel);
	}

	public static <T> ArrayList<T> createList(T t, int size)
	{
		ArrayList<T> list = new ArrayList<T>();
		for(int i=0;i<size;i++)
			list.add(t);
		return list;
	}

	public static ArrayList<Double> createEquidistantPoints(double start, double end, int n)
	{
		ArrayList<Double> points = new ArrayList<Double>();
		points.add(start);
		if(n==1)
			return points;
		double delta = (end - start)/n;
		for(int i=1;i<n;i++)
			points.add(start + i*delta);
		return points;
	}

	public static Color alpha(Color color, int alpha)
	{
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}
	
	public static Color fade(Color color, int amount)
	{
		return alpha(color, color.getAlpha() - amount);
	}
	
	public static Color darken(Color color, int amount)
	{
		int r = color.getRed() - amount;
		int g = color.getGreen() - amount;
		int b = color.getBlue() - amount;
		r = r > 0 ? r : 0;
		g = g > 0 ? g : 0;
		b = b > 0 ? b : 0;
		return new Color(r,g,b);
	}
	
	public static Color randomColor()
	{
		int r = (int) (Math.random()*256);
		int g = (int) (Math.random()*256);
		int b = (int) (Math.random()*256);
		return new Color(r,g,b);
	}

	public void draw(
			int vertexCount,
			double initialLength,
			int depth,
			double childLengthRatio,
			double seedAngle)
	{
		int WIDTH = depth*3 + 1;

		Color COLOR = Palette.rainbow(loopCount);
		int COLOR_DELTA = Palette.LOOP_SIZE/vertexCount;

		ArrayList<Double> CHILD_POSITION_RATIO_LIST = createList(1.0, vertexCount);
		ArrayList<Double> CHILD_ANGLE_LIST = createEquidistantPoints(0, 2*Math.PI+seedAngle, vertexCount);

		ArrayList<Branch> baseBranches = Branch.createBranches(
				Edge.rootsOfUnity(vertexCount, initialLength, Math.PI/(vertexCount*2)), WIDTH, COLOR);
		panel.add(baseBranches);

		for(int i=1;i<=depth;i++)
		{
			WIDTH-=3;
			COLOR = Palette.rainbow(loopCount+i*COLOR_DELTA);
			ArrayList<Branch> nextBaseBranches = new ArrayList<Branch>();
			for(Branch baseBranch : baseBranches)
			{
				ArrayList<Branch> childBranches = baseBranch.branchOut(
						CHILD_POSITION_RATIO_LIST,
						CHILD_ANGLE_LIST,
						childLengthRatio,
						WIDTH,
						COLOR);
				panel.add(childBranches);
				Branch.combineBranches(nextBaseBranches, childBranches);
			}
			baseBranches = nextBaseBranches;
		}
	}
	
	
	public void presetCollapsing(int vertexCount, double size, int density, boolean rotate, boolean morph)
	{
		if(rotate)
			panel.addRotation(Math.PI/1440);
		draw(vertexCount, size, (int)(Math.log(density)/Math.log(vertexCount)), 1/GOLDEN_RATIO, morph ? seed+=amount : 0);
	}
	
	public void presetExpanding(int vertexCount, double size, int density, boolean rotate, boolean morph)
	{
		if(rotate)
			panel.addRotation(Math.PI/1440);
		draw(vertexCount, size, (int)(Math.log(density)/Math.log(vertexCount)), GOLDEN_RATIO, morph ? seed+=amount : 0);
	}
	
	double amount = Math.PI/500;
	double seed = 0;
	int loopCount = 0;
	private void runForever()
	{
		new Thread(()->
		{
			while(true)
			{
				try
				{
					
					SwingUtilities.invokeAndWait(new Runnable()
					{
						@Override
						public void run()
						{
							panel.clear();
							presetCollapsing(3, 200, 10000, false, true);
							repaint();
							loopCount+=25;
						}
					});
				}
				catch (InvocationTargetException | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				main.setVisible(true);
			}
		});
		main.runForever();
	}
}
