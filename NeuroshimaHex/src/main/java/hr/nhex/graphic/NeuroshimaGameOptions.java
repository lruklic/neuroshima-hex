package hr.nhex.graphic;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class NeuroshimaGameOptions extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					NeuroshimaGameOptions frame = new NeuroshimaGameOptions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NeuroshimaGameOptions() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initiateComboBoxes(contentPane);

		JToggleButton tglbtnPlayer = new JToggleButton("Player 1");
		tglbtnPlayer.setBounds(15, 33, 106, 28);
		contentPane.add(tglbtnPlayer);

		JToggleButton tglbtnPlayer_1 = new JToggleButton("Player 2");
		tglbtnPlayer_1.setBounds(15, 89, 106, 28);
		contentPane.add(tglbtnPlayer_1);



		JToggleButton tglbtnPlayer_2 = new JToggleButton("Player 3");
		tglbtnPlayer_2.setBounds(15, 145, 106, 28);
		contentPane.add(tglbtnPlayer_2);


		JToggleButton tglbtnPlayer_3 = new JToggleButton("Player 4");
		tglbtnPlayer_3.setBounds(15, 201, 106, 28);
		contentPane.add(tglbtnPlayer_3);

	}

	private void initiateComboBoxes(JPanel contentPane) {

		List<String> armyNames = new ArrayList<>();

		JComboBox<String> comboBox = new JComboBox<String>();
		initiateArmyNames(armyNames);
		initiateComboBox(armyNames, comboBox);
		comboBox.setBounds(217, 33, 106, 28);
		contentPane.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(217, 89, 106, 28);
		contentPane.add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(217, 145, 106, 28);
		contentPane.add(comboBox_2);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(217, 201, 106, 28);
		contentPane.add(comboBox_3);
	}

	/**
	 * Metoda koja popunjava popis naziva vojski iz igre Neuroshima Hex.
	 * 
	 * @param armyNames lista s nazivima vojski
	 */

	private void initiateArmyNames(List<String> armyNames) {
		armyNames.add("Hegemony");
		armyNames.add("Moloch");
		armyNames.add("Borgo");
		armyNames.add("Outpost");
	}

	private void initiateComboBox(List<String> armyNames, JComboBox<String> comboBox) {
		for (String name : armyNames) {
			comboBox.addItem(name);
		}
	}
}
