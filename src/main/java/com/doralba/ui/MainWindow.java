package com.doralba.ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField nombre_textField;
	private JTextField cuarto_textField;
	private String imageName;
	private String room;
	private Image captura;
	private JLabel lblNombre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
		
		JButton capturaButton = new JButton("Capturar");
		capturaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Webcam webcam = Webcam.getDefault();
				webcam.open();
				BufferedImage image = webcam.getImage();
				captura = (Image) image;
				try {
					ImageIO.write(image, "PNG", new File(imageName));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		capturaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("98px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		panel.add(capturaButton, "2, 2, 3, 1, default, top");
		
		lblNombre = new JLabel("Nombre");
		panel.add(lblNombre, "2, 4, left, center");
		
		nombre_textField = new JTextField();
		nombre_textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cuarto_textField.setText(nombre_textField.getText() + ".png");
			}
		});
		panel.add(nombre_textField, "4, 4, left, top");
		nombre_textField.setColumns(10);
		
		JLabel lblCuarto = new JLabel("Cuarto");
		panel.add(lblCuarto, "2, 6, left, center");
		
		cuarto_textField = new JTextField();
		cuarto_textField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				room = cuarto_textField.getText() + ".png";
			}
		});
		panel.add(cuarto_textField, "4, 6, right, top");
		cuarto_textField.setColumns(10);
		
		JButton saveButton = new JButton("Guardar");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(saveButton, "2, 8, 3, 1, default, top");
		
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setFPSDisplayed(true);
		webcamPanel.setDisplayDebugInfo(true);
		webcamPanel.setImageSizeDisplayed(true);
		webcamPanel.setMirrored(true);
		
		splitPane.setRightComponent(webcamPanel);
				
	}


}
