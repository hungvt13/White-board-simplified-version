import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class Whiteboard extends JFrame
{
	final int FRAME_WIDTH = 1000; 
	final int FRAME_HEIGHT = 1000; 
	private Canvas drawPane;
	private Box vert, horz1, horz2, horz3, horz4, horz5;
	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	private JTable table;
	private JTextField t1;
	private	JSplitPane utilPane;
	private JPanel buttonPane, infoPane;
	private JFrame board;
	private JScrollPane scrollpane;
	private StringTableModel tableModel;
	
	public static void main(String[] args)
	{
		// Create a white board
        Whiteboard whiteboard = new Whiteboard(); 		
	}
	
	
	public Whiteboard() 
	{
		// Set up the Whiteboard with different components
		setCanvasAndPanel();
		setButtons();
		createTable();
		createBoxes();		
		setUtilPane();
		fillCanvas();
	}	
	
	private void setCanvasAndPanel()
	{
		//Size of Window
		final int FRAME_WIDTH = 1000; 
		final int FRAME_HEIGHT = 1000; 
		
		//Set the Canvas and panel
		drawPane = new Canvas(this);
		//New Whiteboard Frame
		board = new JFrame("Whiteboard");
		board.setLayout(new BorderLayout());

		//Set the Canvas and panel
		JSplitPane  utilPane = new JSplitPane();
		JPanel buttonPane = new JPanel();
		JPanel infoPane = new JPanel();
	}
	
	private void createBoxes()
	{
		//Make boxes
		vert = Box.createVerticalBox();
		horz1 = Box.createHorizontalBox();
		horz2 = Box.createHorizontalBox();
		horz3 = Box.createHorizontalBox();		
		horz4 = Box.createHorizontalBox();	
		horz5 = Box.createHorizontalBox();
		
		//Box Code and addition to buttonPane
		vert.add(horz1);
		vert.add(horz2);
		vert.add(horz3);
		vert.add(horz4);
		vert.add(horz5);
		horz1.add(b1);
		horz1.add(b2);
		horz1.add(b3);
		horz1.add(b4);
		horz2.add(b5);
		horz3.add(t1);
		horz4.add(b6);
		horz4.add(b7);
		horz4.add(b8);	
		horz5.add(b9);
		horz5.add(b10);
	}
	
	private void createTable()
	{	
		tableModel = new StringTableModel();
		table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
        scrollpane = new JScrollPane(table); 
        scrollpane.setPreferredSize(new Dimension(380, 400));
	}
	
	private void setButtons() 
	{
		//ButtonCode
		b1 = new JButton("Add Rect");
		b2 = new JButton("Add Oval");
		b3 = new JButton("Add Line");
		b4 = new JButton("Add Text");
		b5 = new JButton("Set Color");
		b6 = new JButton("Move to Front");
		b7 = new JButton("Move to Back");
		b8 = new JButton("Remove");
		b9 = new JButton("Save");
		b10 = new JButton("Load");
		t1 = new JTextField();
		
		t1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {				
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
                updateTextChange(e);
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
                updateTextChange(e); 
			}
		});
	         
		// Add rectangle
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DRectModel d = new DRectModel(100, 100, 100, 100, Color.GRAY);
				drawPane.addShape(d);
				drawPane.repaint();
			}
		});
		
		// Add oval
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DOvalModel d = new DOvalModel(300, 300, 100, 100, Color.GRAY);
				drawPane.addShape(d);
				drawPane.repaint();
			}
		});
		
		// Add line
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point p1 = new Point(100, 90);
				Point p2 = new Point(50, 40);
				DLineModel d = new DLineModel();
				d.setPoints(p1, p2);
				drawPane.addShape(d);
				drawPane.repaint();
			}
		});
		
		// Add text
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTextModel d = new DTextModel();
				d.setBounds(0, 0, 50, 50);
				drawPane.addShape(d);
				drawPane.repaint();
			}
		});
		
		//Set color
		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(Whiteboard.this, "Select A Color", drawPane.getSelectedShape().getColor()); 
                
                if (selectedColor != null) {
                	drawPane.recolorShape(selectedColor);	
                }
			}
		});
		
		// Add shape to front
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                drawPane.moveSelectedShapeToFront();
			}
		});
		
		// Add shape to back
		b7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawPane.moveSelectedShapeToBack();
			}
		});
				
		
		// Remove a shape
		b8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (drawPane.isAShapeSelected()) {
					drawPane.removeShape();	
					repaint();
				}				
			}
		});
		
		//Save a whiteBoard
		b9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("File Name", null);
				if (result != null) {
					File f = new File(result);
					open(f);
				}
			}
		});
		
		//Load a Whiteborad
		b10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void setUtilPane() 
	{
		
		utilPane = new JSplitPane();
		buttonPane = new JPanel();
		infoPane = new JPanel();
		
		//infoPane Code
		infoPane.add(scrollpane);
		
		
		//utilPane Code
		utilPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		utilPane.setDividerSize(5);
		utilPane.setTopComponent(buttonPane);
		utilPane.setBottomComponent(infoPane);
		
		//buttonPane Code
		buttonPane.add(vert);
		for (Component comp : horz1.getComponents()) 
		{
			JComponent c = (JComponent) comp;
			c.setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		for (Component comp : horz2.getComponents()) 
		{
			JComponent c = (JComponent) comp;
			c.setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		for (Component comp : horz3.getComponents()) 
		{
			JComponent c = (JComponent) comp;
			c.setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		for (Component comp : horz4.getComponents()) 
		{
			JComponent c = (JComponent) comp;
			c.setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		
		//Add canvas and buttonPane to the frame
		board.add(utilPane, BorderLayout.LINE_START);
		board.add(drawPane, BorderLayout.CENTER);
				
	}
	
	private void fillCanvas() 
	{
		//Add canvas and buttonPane to the frame
		board.add(utilPane, BorderLayout.LINE_START);
		board.add(drawPane, BorderLayout.CENTER);
		
		//Generic Swing things
		board.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);
	}
	
	// Update the selected shape
    public void updateTableSelect(DShape selectedShape) { 
        table.clearSelection(); 
        if(selectedShape != null) { 
            int index = tableModel.getRowForSpecificModel(selectedShape
            		.getModel()); 
            table.setRowSelectionInterval(index, index); 
        } 
    } 
    
    // A new shape added. Invoke table model's method to add shape to its shape list
	public void addShapeToTable(DShape shape) {
		tableModel.addAShapeModel(shape.getModel());
		updateTableSelect(shape); 
	} 
    
    // Shape has moved to back. Invoke table model's method to re-order the shape list
    public void shapeMovedBack(DShape shape) { 
        tableModel.moveShapeToBack(shape.getModel()); 
        updateTableSelect(shape); 
    } 
     
    // Shape has moved to front. Invoke table model's method to re-order the shape list
    public void shapeMovedFront(DShape shape) { 
        tableModel.moveShapeToFront(shape.getModel()); 
        updateTableSelect(shape); 
    }
    
    // Shape has been removed. Invoke table model's method to remove from shape list
    public void shapeRemoved(DShape shape) { 
        tableModel.removeAShapeModel(shape.getModel()); 
        updateTableSelect(null); 
    }
    
    public void updateFont(String text, String font) { 
        t1.setText(text); 
    } 
    
    private void updateTextChange(DocumentEvent e) { 
    	// If the shape selected is DText, do the update to text field
        if(drawPane.isAShapeSelected() && drawPane.getSelectedShape() instanceof DText) 
            drawPane.setText(t1.getText()); 
    } 
    
    //Opens a file by opening an array of things and then instantiating those
    public void open(File file)
    {
    	DShapeModel[] list = null;
    	try {
    		XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream( new FileInputStream (file)));
    	}
    	catch (Exception ignored){}
    }
    
    //Saves a file by turning things into an xml file
    public void save()
    {
    	
    }
     
}
