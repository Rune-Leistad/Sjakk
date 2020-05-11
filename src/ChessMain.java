import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class ChessMain {

	private JFrame frame;
	private JPanel boardPanel;
	
	private JLabel infoWhite;
	private JLabel infoBlack;
	private JLabel whiteTime;
	private JLabel blackTime;
	
	private JButton btnStartGame;
	
	private Board board;
	private MoveManager moveManager;
	
	//private BoardSquare squareChosen;
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmTimer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		
					ChessMain window = new ChessMain();
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
	public ChessMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1096, 815);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		boardPanel = new JPanel();
		boardPanel.setBounds(0, 16, 720, 736);
		boardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		boardPanel.setPreferredSize(new Dimension(720,720));
		frame.getContentPane().add(boardPanel);
		boardPanel.setLayout(new GridLayout(8,8,0,0));
		infoWhite = new JLabel();
		infoWhite.setBounds(0, 752, 1078, 16);
		infoBlack = new JLabel();
		infoBlack.setBounds(0, 0, 1078, 16);
		
		frame.getContentPane().add(infoBlack);
		infoBlack.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(infoWhite);
		infoWhite.setHorizontalAlignment(SwingConstants.CENTER);
		
		whiteTime = new JLabel("White");
		whiteTime.setBounds(824, 472, 170, 16);
		frame.getContentPane().add(whiteTime);
		
		blackTime = new JLabel("Black");
		blackTime.setBounds(824, 160, 170, 16);
		frame.getContentPane().add(blackTime);
		
		btnStartGame = new JButton("Start game");
		btnStartGame.setBounds(847, 581, 97, 25);
		frame.getContentPane().add(btnStartGame);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		mntmTimer = new JMenuItem("Timer");
		mnOptions.add(mntmTimer);
	/*	btnStartGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Timer timer = new Timer();
				timer.start();
			}
			
		}); */
		
		initializeBoard();
		moveManager = new MoveManager(board);
	}
	/*
	private class Timer extends Thread {
		private long lastCheck;
		private long whiteTimeLeft;
		private long blackTimeLeft;
		
		Timer() {
			super();
			lastCheck = System.currentTimeMillis();
			whiteTimeLeft = 300000;
			blackTimeLeft = 300000;
		}
		
		public void run() {
			do{
				if(moveManager.whiteTurn())
					whiteTimeLeft -= (System.currentTimeMillis() - lastCheck);
				else
					blackTimeLeft -= (System.currentTimeMillis() - lastCheck);
				
				try {
					int min = (int)whiteTimeLeft/60000;
					int sec = (int)(whiteTimeLeft/1000)%60;
					whiteTime.setText(String.format("White time remaining:  %02d:%02d", min, sec));
					
					min = (int)blackTimeLeft/60000;
					sec = (int)(blackTimeLeft/1000)%60;
					blackTime.setText(String.format("Black time remaining:  %02d:%02d", min, sec));
					lastCheck = System.currentTimeMillis();
					Thread.sleep(200);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} while(blackTimeLeft > 0 && whiteTimeLeft > 0);
		}
		
	} */
	
	private void initializeBoard() {
		final BoardSquare[][] squares;
		squares = new BoardSquare[8][8];
		for(int i = 0; i < 8; i++) {
			for(int y = 0; y < 8; y++) {
				squares[i][y] = new BoardSquare(i, y);
				squares[i][y].setOpaque(true);
				squares[i][y].setSize(new Dimension(90,90));
				squares[i][y].setHorizontalAlignment(SwingConstants.CENTER);
				addMouseListener(squares[i][y]);
				
				if((i+y) % 2 == 0)
					squares[i][y].setBG(new Color(184,134,11));
				else
					squares[i][y].setBG(new Color(255,255,255));
					
				boardPanel.add(squares[i][y]);
			}
			
		}
		
		board = new Board(squares, generateWhitePieces(squares), generateBlackPieces(squares));
	}
	
	private void addMouseListener(final BoardSquare bs) {
		bs.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				moveManager.legalMove(board, bs);
			}
		});
	}
	
	private ArrayList<Piece> generateWhitePieces(BoardSquare[][] squares) {
		ArrayList<Piece> whitePieces = new ArrayList<Piece>();
		whitePieces.add(new Rook(true));
		whitePieces.add(new Rook(true));
		whitePieces.add(new Knight(true));
		whitePieces.add(new Knight(true));
		whitePieces.add(new Bishop(true));
		whitePieces.add(new Bishop(true));
		whitePieces.add(new Queen(true));
		whitePieces.add(new King(true));
		
		for(int i = 0; i < 8; i++)
			whitePieces.add(new Pawn(true));
		
		return whitePieces;
	}
	
	private ArrayList<Piece> generateBlackPieces(BoardSquare[][] squares) {
		ArrayList<Piece> blackPieces = new ArrayList<Piece>();
		
		blackPieces.add(new Rook(false));
		blackPieces.add(new Rook(false));
		blackPieces.add(new Knight(false));
		blackPieces.add(new Knight(false));
		blackPieces.add(new Bishop(false));
		blackPieces.add(new Bishop(false));
		blackPieces.add(new Queen(false));
		blackPieces.add(new King(false));
		
		for(int i = 0; i < 8; i++)
			blackPieces.add(new Pawn(false));
		
		return blackPieces;
	}
}
