package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import InputData.azim.mostdesired.DataReader;
import model.sajjad.mostDesired.sVertex;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;

	/**
	 * 
	 * @author sajjad
	 * 
	 * The JFrame which is main frame for graph representation. This class
	 * simply contains ViewGraphPanel which does all graph visualization.
	 *
	 */

public class ViewGraphFrame extends JFrame {

	private static final long serialVersionUID = -7496390669979535394L;
	
	

	private ArrayList<LinkedList<NodeAndWeight>> graph;
	private ArrayList<sVertex> verticesProp;
	private CloseListener closeListener;
	private Dimension scrSize;
	private DataReader dataReader;
	private ArrayList<Integer> k;

	public ViewGraphFrame(String filename, CloseListener closeListener) throws IOException {
		
		this.scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.closeListener = closeListener;
		this.dataReader = new DataReader(filename);
		this.verticesProp = new ArrayList<>();
		this.graph = dataReader.getNodesList_In();
		
		initf();
		initVertices();
		initializationComp();
		
		setVisible(true);
	}
	
	public ViewGraphFrame(String filename, CloseListener closeListener, ArrayList<Integer> k) throws IOException {
		
		this.scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.closeListener = closeListener;
		this.dataReader = new DataReader(filename);
		this.verticesProp = new ArrayList<>();
		this.graph = dataReader.getNodesList_In();
		this.k = k;
		
		initf();
		initVertices();
		initializationComp();
		
		setVisible(true);
	}
	
	
	private void initf() {
		
		
		setSize(1000, 700);
		addWindowListener(exitListener);
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		setLayout(new BorderLayout(100, 100));
		
	}
	
	private void initializationComp() {
		
		ViewGraphPanel panel = new ViewGraphPanel(scrSize, verticesProp);
		JScrollPane spanel = new JScrollPane(panel);
		spanel.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		spanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(spanel, BorderLayout.CENTER);
		
	}
	
	private void initVertices() {
		
		for (int i = 0; i < graph.size(); i++) {
			
			LinkedList<NodeAndWeight> vertexTmp = graph.get(i);
			int d = (vertexTmp.size() * 200) / graph.size();
			
			sVertex sv = new sVertex(i, new Random().nextInt(1000) + 100, new Random().nextInt((1000)) + 100, (d), false);
			
			if (k != null && k.contains(i)) {
				sv.setInK(true);
			}
			
			Iterator<NodeAndWeight> iterator = vertexTmp.iterator();
			
			while (iterator.hasNext()) {
				
				sv.addNeighbor((int) iterator.next().getAdjacentVertex());
			}
			verticesProp.add(sv);
		}
		
	}

	WindowListener exitListener = new WindowAdapter() {

		@Override
		public void windowClosing(WindowEvent e) {
			int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (confirm == 0) {
				
				closeListener.doClose(ViewGraphFrame.this);

			}
		}
	};

}
