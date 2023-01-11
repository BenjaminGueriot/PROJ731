package fr.projet.com;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DragAndDropFile extends JFrame implements DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFormattedTextField field;
	private JFormattedTextField field2;
	
	public DragAndDropFile() {
		super("Glisser un fichier texte");
		
		// Set the size
		createWindow();
	}

	private void createWindow() {
		this.setSize(500, 150);
		
		JLabel label = new JLabel("Glisser un fichier texte", SwingConstants.CENTER);
		JLabel mapper = new JLabel("Nombre de mapper", SwingConstants.LEFT);
		field = new JFormattedTextField() ;
		JLabel reducer = new JLabel("Nombre de reducer", SwingConstants.LEFT);
		field2 = new JFormattedTextField() ;
		
		field.setValue(4);
		field2.setValue(2);
		field.setMaximumSize(new Dimension(200, 20));
		field2.setMaximumSize(new Dimension(200, 20));
		
		label.setMaximumSize(new Dimension(400, 400));
		
		// Connect the label with a drag and drop listener
		new DropTarget(label, this);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		this.getContentPane().add(label);
		this.getContentPane().add(mapper);
		this.getContentPane().add(field);
		this.getContentPane().add(reducer);
		this.getContentPane().add(field2);

		// Show the frame
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent event) {

		// Accept copy drops
		event.acceptDrop(DnDConstants.ACTION_COPY);

		// Get the transfer which can provide the dropped item data
		Transferable transferable = event.getTransferable();

		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		
		int nb_mapper = Integer.parseInt(field.getText());
		int nb_reducer = Integer.parseInt(field2.getText());

		List<File> files;
		try {
			
			files = (List<File>) transferable.getTransferData(flavors[0]);
			
			String link = files.get(0).getPath();		
			
			Manager.startAction(link, nb_mapper, nb_reducer);
			this.dispose();
			
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		event.dropComplete(true);

	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}


}

