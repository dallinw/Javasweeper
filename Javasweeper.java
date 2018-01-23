/**
 * Created by Dallin on 4/10/17.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Javasweeper extends JFrame implements ActionListener {
    private static final int UPPER_LIMIT = 100;
    private static final int SIZE = 20;

    private Random r;
    private JButton[] buttons;
    private Font text;
    private Font text2;
    private boolean[] isMine;
    private Container window;
    private boolean isOver;
    private boolean[] isClicked;
    private GridLayout grid;
    private int numberClicked;


    //default constructor
    public Javasweeper()
    {
        super("Minesweeper");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttons = new JButton[UPPER_LIMIT];
        isMine = new boolean[UPPER_LIMIT];
        isClicked = new boolean[UPPER_LIMIT];
        text = new Font("Times New Roman", Font.BOLD, 20);
        text2 = new Font("Times New Roman", Font.BOLD, 42);
        grid = new GridLayout(10, 10);
        isOver = false;
        numberClicked = 0;
        r = new Random();

        window = getContentPane();
        setResizable(true);
        window.setLayout(grid);


        //sets all buttons to 'X', resets style, and adds to window
        defaultButtons();
        placeMines();//generates 20 random mines in unique boxes

        setVisible(true);

    }

    public void defaultButtons()
    {
        for (int i = 0; i < buttons.length; i++)
        {

            buttons[i] = new JButton();
            buttons[i].setFont(text);
            buttons[i].setText("");
            buttons[i].addActionListener(this);
            window.add(buttons[i]);
        }

        //also initializes isClicked array
        for(int i=0;i< buttons.length;i++)
        {
            isClicked[i] = false;
        }
    }

    public void placeMines()
    {
        //clear old mines
        for (int i = 0; i < isMine.length; i++)
        {
            isMine[i] = false;
        }
        //randomly place new mines
        for (int i = 0; i < SIZE; i++)
        {
            int mine = r.nextInt(UPPER_LIMIT);
            if (isMine[mine] == false)//checks that there's not already a mine
            {
                isMine[mine] = true;
            }
            else //if there was already a mine
            {
                i--;//iterate an extra time if no mine was placed
            }
        }
    }


    public void numberOfMines(int index)
    {
        int count = 0;
        //checks if adjacent squares are mines
        boolean startRow = false;
        boolean endRow = false;
        //certain spaces can be ignored if a space is a start/end row
        //TODO: replace these exceptions with a simple modulus
        if(index == 0 || index == 10 || index == 20 || index == 30 || index == 40 || index == 50 ||
                index ==60 || index ==70 || index ==80 || index ==90)
        {
            startRow = true;
        }

        if(index == 9 || index == 19 || index == 29 || index == 39 || index == 49 || index == 59 ||
                index ==69 || index ==79 || index ==89 || index ==99)
        {
            endRow = true;
        }


        if (index >=1 && !startRow)
        {
            if (isMine[index - 1])
            {
                count++;
            }
        }
        if (index<=98 && !endRow)
        {
            if (isMine[index + 1])
            {
                count++;
            }
        }
        if (index >= 11 && !startRow)
        {
            if (isMine[index - 11])
            {
                count++;
            }
        }
        if (index >= 10)
        {
            if (isMine[index - 10])
            {
                count++;
            }
        }
        if (index >= 9 && !endRow)
        {
            if (isMine[index - 9])
            {
                count++;
            }
        }
        if (index <= 90 && !startRow)
        {
            if (isMine[index + 9])
            {
                count++;
            }
        }
        if (index <= 89)
        {
            if (isMine[index + 10])
            {
                count++;
            }
        }
        if (index <= 88 && !endRow)
        {
            if (isMine[index + 11])
            {
                count++;
            }
        }
        //set button text to number of neighboring mines
        buttons[index].setText(Integer.toString(count));
    }

    public void gameOver()
    {
        clearBoard();
        isOver = true;
        for (int i = 40; i < buttons.length; i++)
        {
            window.remove(buttons[i]);//clear off some of the buttons
        }

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setFont(text2);
        }

        //print game over in red
        String[] gameOver = {"G", "A", "M", "E", "O", "V", "E", "R"};
        for (int i = 4; i < 4 + gameOver.length; i++)
        {
            buttons[i].setForeground(Color.red);
            buttons[i].setText(gameOver[i - 4]);
        }
        //print final score
        //(magic numbers here because the choices are basically arbitrary)
        buttons[17].setFont(text);
        buttons[17].setForeground(Color.BLACK);
        buttons[17].setText("Score:");
        buttons[18].setFont(text);
        buttons[18].setForeground(Color.BLACK);
        buttons[18].setText(Integer.toString(numberClicked));

        //print click to play again
        String[] playAgain = {"CLICK", "TO", "PLAY", "AGAIN"};
        for (int i = 24; i < 24 + playAgain.length; i++)
        {
            buttons[i].setFont(text);
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setText(playAgain[i - 24]);
        }

        //print quit
        buttons[39].setForeground(Color.red);
        buttons[39].setText("QUIT");
    }

    public void youWin()
    {
        clearBoard();
        isOver = true;

        //remove some buttons
        for (int i = 40; i < buttons.length; i++)
        {
            window.remove(buttons[i]);
        }

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setFont(text2);
        }

        //print you win
        String[] youWin = {"Y", "O", "U", "", "W", "I", "N", "!"};
        for (int i = 4; i < 4 + youWin.length; i++)
        {
            buttons[i].setForeground(Color.green);
            buttons[i].setText(youWin[i - 4]);
        }

        //print final score
        //TODO: pull this out into a separate function
        buttons[17].setFont(text);
        buttons[17].setForeground(Color.BLACK);
        buttons[17].setText("Score:");
        buttons[18].setFont(text);
        buttons[18].setForeground(Color.BLACK);
        buttons[18].setText(Integer.toString(numberClicked));


        //print click to play again
        String[] playAgain = {"CLICK", "TO", "PLAY", "AGAIN"};
        for (int i = 24; i < 24 + playAgain.length; i++)
        {
            buttons[i].setFont(text);
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setText(playAgain[i - 24]);
        }

        //print quit
        buttons[39].setForeground(Color.red);
        buttons[39].setText("QUIT");

    }

    public void clearBoard()
    {
        //sets all buttons to blanks
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setText("");
        }
    }


    public void actionPerformed(ActionEvent a)
    {
        //normal button operations
            for (int i = 0; i < buttons.length; i++) {
                if (a.getSource() == buttons[i] && isMine[i] == false) {

                    numberOfMines(i);
                    //keep the user from clicking one box repeatedly
                    if (isClicked[i] == false) {
                        numberClicked++;//if this is the first time clicking this, add one to the counter
                        isClicked[i] = true;//mark that this has been clicked
                    }
                    if (numberClicked == buttons.length-SIZE) //if all non-mine buttons are clicked
                    {
                        youWin();
                    }
                }
                if (a.getSource() == buttons[i] && isMine[i] == true) {
                    isOver=true;
                    gameOver();
                }
            }

        //button operations after gameOver
        if(isOver)
        {
            for (int i = 24; i < buttons.length; i++)
            {
                if (i < 28 && a.getSource() == buttons[i])
                {
                    isOver = false;
                    numberClicked = 0;
                    window.removeAll();
                    defaultButtons();
                    placeMines();
                }
                else if (i == 39 && a.getSource() == buttons[39])
                {
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Javasweeper game = new Javasweeper();
    }
}
