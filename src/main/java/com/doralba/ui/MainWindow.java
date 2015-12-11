package com.doralba.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.doralba.core.TypeEnum;
import com.doralba.utils.DAO;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainWindow {

	private JFrame frame;
	private JTextField id_textField;
	private JTextField fecha_textField;
	private BufferedImage bufferedImage;
	private JLabel capturePreview;

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
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		
		JSplitPane verticalSplitPane = new JSplitPane();
		verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setBorder(null);
		verticalSplitPane.setContinuousLayout(true);
		
		splitPane.setLeftComponent(verticalSplitPane);
		splitPane.setBorder(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("98px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		verticalSplitPane.setTopComponent(panel);
		
		JButton capturaButton = new JButton("Capturar");
		capturaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Webcam webcam = Webcam.getDefault();
				webcam.open();
				bufferedImage = webcam.getImage();
				
				Image image = bufferedImage.getScaledInstance(320, 240, Image.SCALE_REPLICATE);
				capturePreview.setIcon(new ImageIcon(image));
				
			}
		});
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
				try {
					String name = UUID.randomUUID().toString();
					String filepath = "capturas/"+name+".png";
					ImageIO.write(bufferedImage, "PNG", new File(filepath));
					System.out.println(filepath);
					//TODO operación de guardar datos en base de datos
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel fechaLabel = new JLabel("Fecha Entrada");
		fechaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(fechaLabel, "2, 10, right, default");

		fecha_textField = new JTextField();
		fecha_textField.setText("DD/MM/AA");
		panel.add(fecha_textField, "4, 10, fill, default");
		fecha_textField.setColumns(10);
		panel.add(saveButton, "2, 12, 3, 1, default, top");

		
		JPanel imagePanel = new JPanel();
		imagePanel.setBorder(null);
		imagePanel.setLayout(new GridLayout(0, 1, 0, 0));
		capturePreview = new JLabel();
		imagePanel.add(capturePreview);
		verticalSplitPane.setBottomComponent(imagePanel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(76)
					.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		frame.getContentPane().setLayout(groupLayout);

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setFPSDisplayed(false);
		webcamPanel.setFitArea(true);
		webcamPanel.setDisplayDebugInfo(false);
		webcamPanel.setImageSizeDisplayed(false);
		webcamPanel.setMirrored(false);

		splitPane.setRightComponent(webcamPanel);

	}
}
