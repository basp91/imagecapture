package com.doralba.ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.doralba.core.TypeEnum;
import com.doralba.utils.DAO;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frame;
	private JTextField id_textField;
	private JTextField fecha_textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setTitle("Documentos de Indentificación");
					window.frame.setVisible(true);
					new DAO();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

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
				try {
					String name = UUID.randomUUID().toString();
					String filepath = "capturas/"+name+".png";
					ImageIO.write(image, "PNG", new File(filepath));
					System.out.println(filepath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("98px:grow"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		panel.add(capturaButton, "2, 2, 3, 1, default, top");

		JLabel idLabel = new JLabel("Id");
		panel.add(idLabel, "2, 4, right, default");

		id_textField = new JTextField();
		panel.add(id_textField, "4, 4, fill, default");
		id_textField.setColumns(10);

		JLabel tipoLabel = new JLabel("Tipo");
		panel.add(tipoLabel, "2, 6, right, default");

		JComboBox<String> tipo_comboBox = new JComboBox<String>();
		tipo_comboBox.addItem(TypeEnum.INE.getName());
		tipo_comboBox.addItem(TypeEnum.PASAPORTE.getName());
		tipo_comboBox.addItem(TypeEnum.LICENCIA.getName());
		panel.add(tipo_comboBox, "4, 6, fill, default");

		JLabel cuartoLabel = new JLabel("Cuarto");
		panel.add(cuartoLabel, "2, 8, right, center");

		JTextField cuarto_textField = new JTextField();
		cuarto_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println(cuarto_textField.getText());
			}
		});
		cuarto_textField.setColumns(10);
		panel.add(cuarto_textField, "4, 8, right, top");
				
						JButton saveButton = new JButton("Guardar");
						saveButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								
							}
						});
						
						JLabel fechaLabel = new JLabel("Fecha Entrada");
						fechaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						panel.add(fechaLabel, "2, 10, right, default");
						
						fecha_textField = new JTextField();
						fecha_textField.setText("DD/MM/AA");
						panel.add(fecha_textField, "4, 10, fill, default");
						fecha_textField.setColumns(10);
						panel.add(saveButton, "2, 12, 3, 1, default, top");

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setFPSDisplayed(false);
		webcamPanel.setFitArea(true);
		webcamPanel.setDisplayDebugInfo(false);
		webcamPanel.setImageSizeDisplayed(false);
		webcamPanel.setMirrored(false);

		splitPane.setRightComponent(webcamPanel);
		
		frame.pack();
	}
}
