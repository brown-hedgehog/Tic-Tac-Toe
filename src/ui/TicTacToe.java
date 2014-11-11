package ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class TicTacToe extends JApplet implements MouseListener {
	private static String PLAYERX = "Player X";
	private static String PLAYERO = "Player O";
	private String playerName = PLAYERX;
	private JButton[][] button;
	private JLabel playerNumber;
	private Panel buttonsPanel;
	private String X = "X";
	private String O = "O";
	private String XorO;

	public void init() {
		initComponents();
	}

	private void initComponents() {
		buttonsPanel = new Panel();
		buttonsPanel.setLayout(new GridLayout(4, 3));
		playerNumber = new JLabel(playerName, SwingConstants.CENTER);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 60);
		button = new JButton[3][3];
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[i][j] = new JButton();
				button[i][j].setFont(buttonFont);
				button[i][j].addMouseListener(this);
				buttonsPanel.add(button[i][j]);
			}
		}
		setPlayerName(PLAYERX);
		buttonsPanel.add(playerNumber);
		add(buttonsPanel);
	}

	private void setPlayerName(String playerName) {
		this.playerName = playerName;
		playerNumber.setText(playerName + ", your turn.");
	}

	private void reset() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[i][j].setText("");
			}
		}
		setPlayerName(PLAYERX);
	}

	public void mouseClicked(MouseEvent e) {
		JButton currentButton = (JButton) e.getComponent();

		if (currentButton.getText() != "") {
			return;
		}

		XorO = X;
		currentButton.setText(XorO);
		checkWorkflowConditions(XorO);
		XorO = changeXorO(XorO);
		computerMove(XorO);
	}

	private void computerMove(String XorO) {
		setPlayerName(PLAYERO);
		JButton computerButton = findThirdEmptyInARow(XorO);
		if (computerButton == null) {
			computerButton = findThirdEmptyInARow(changeXorO(XorO));
			changeXorO(XorO);
			if (computerButton == null) {
				computerButton = placeInCenter(XorO);
				if (computerButton == null) {
					computerButton = placeInCorner(XorO);
					if (computerButton == null) {
						computerButton = placeOnSide(XorO);
					}
				}
			}
		}
		computerButton.setText(XorO);

		checkWorkflowConditions(XorO);
		XorO = changeXorO(XorO);
	}

	private void checkWorkflowConditions(String XorO) {
		String[] str = { "OK" };

		if (findThreeInARow(XorO)) {
			String winnerName = (playerName == PLAYERX) ? PLAYERO : PLAYERX;
			JOptionPane.showOptionDialog(this,
					winnerName.concat(" won!!! Congratulations!!!"),
					"Congratulations!", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, str, " OK");
			reset();
		}

		if (!findEmptyCells()) {
			reset();
		}
	}

	private boolean findThreeInARow(String XorO) {
		return threeHorizontal(XorO) ? true : (threeVertical(XorO) ? true
				: (threeDiagonal(XorO) ? true : false));
	}

	private boolean threeHorizontal(String XorO) {

		for (int i = 0; i < button.length; i++) {
			for (int j = 0, counter = 0; j < button[i].length; j++) {
				if (button[i][j].getText() == XorO) {
					counter += 1;
				} else {
					break;
				}
				if (counter == button.length) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean threeVertical(String XorO) {

		for (int i = 0; i < button.length; i++) {
			for (int j = 0, counter = 0; j < button[i].length; j++) {
				if (button[j][i].getText() == XorO) {
					counter = counter + 1;
				} else {
					break;
				}
				if (counter == button.length) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean threeDiagonal(String XorO) {

		for (int i = 0, counter = 0; i < button.length; i++) {
			int j = i;
			if (button[i][j].getText() == XorO) {
				counter = counter + 1;
			} else {
				break;
			}
			if (counter == button.length) {
				return true;
			}

		}

		for (int i = 0, counter = 0; i < button.length; i++) {
			int j = (button[i].length - 1) - i;
			if (button[i][j].getText() == XorO) {
				counter = counter + 1;
			} else {
				break;
			}
			if (counter == button.length) {
				return true;
			}
		}

		return false;
	}

	private boolean findEmptyCells() {

		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				if (button[i][j].getText() == "") {
					return true;
				}
			}
		}
		return false;
	}

	private JButton findThirdEmptyInARow(String XorO) {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0, counter = 0; j < button[i].length; j++) {
				if (button[i][j].getText() == XorO) {
					counter = counter + 1;
				}
				if (counter == 2) {
					for (int k = 0; k < button[i].length; k++) {
						if (button[i][k].getText() == "") {
							return button[i][k];
						}
					}
				}
			}
		}

		for (int i = 0; i < button.length; i++) {
			for (int j = 0, counter = 0; j < button[i].length; j++) {
				if (button[j][i].getText() == XorO) {
					counter = counter + 1;
				}
				if (counter == 2) {
					for (int k = 0; k < button[i].length; k++) {
						if (button[k][i].getText() == "") {
							return button[k][i];
						}
					}
				}
			}
		}

		for (int i = 0, j = button.length - 1, counter = 0; i < button.length
				&& j >= 0; i++, j--) {
			if (button[j][i].getText() == XorO) {
				counter = counter + 1;
			}
			if (counter == 2) {
				for (int k = 0, l = button.length - 1; k < button.length
						&& l >= 0; k++, l--) {
					if (button[k][l].getText() == "")
						return button[k][l];
				}
			}

		}

		for (int i = 0, counter = 0; i < button.length; i++) {
			int j = i;
			if (button[i][j].getText() == XorO) {
				counter = counter + 1;
			}
			if (counter == 2) {
				for (int k = 0; k < button.length; k++) {
					int l = k;
					if (button[k][l].getText() == "") {
						return button[k][l];
					}
				}
			}
		}

		return null;
	}

	private JButton placeInCenter(String XorO) {
		int i = (button.length - 1) / 2;
		int j = (button[i].length - 1) / 2;
		return (button[i][j].getText() == "") ? button[i][j] : null;
	}

	private JButton placeInCorner(String XorO) {
		return button[0][0].getText() == "" ? button[0][0]
				: (button[button.length - 1][button.length - 1].getText() == "" ? button[button.length - 1][button.length - 1]
						: (button[0][button.length - 1].getText() == "" ? button[0][button.length - 1]
								: (button[button.length - 1][0].getText() == "" ? button[button.length - 1][0]
										: null)));
	}

	private JButton placeOnSide(String XorO) {
		return button[0][(button.length - 1) / 2].getText() == "" ? button[0][(button.length - 1) / 2]
				: (button[(button.length - 1) / 2][button.length - 1].getText() == "" ? button[(button.length - 1) / 2][button.length - 1]
						: (button[button.length - 1][(button.length - 1) / 2]
								.getText() == "" ? button[button.length - 1][(button.length - 1) / 2]
								: (button[(button.length - 1) / 2][0].getText() == "" ? button[(button.length - 1) / 2][0]
										: null)));
	}

	private String changeXorO(String XorO) {
		return XorO == X ? (XorO = O) : (XorO = X);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}