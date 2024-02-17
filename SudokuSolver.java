import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver implements ActionListener {

    // creating Jframe
    JFrame frame;

    static String turn="1";
    static boolean solFlag=true;
    
    //creating panel array to store all 9 panels
    JPanel[] panel=new JPanel[9];

    //creating panel for number options
    JPanel optionPanel;

    //creating panel for operations like restart,exit,solution and check.
    JPanel operationPanel;

    //creating buttons for operations
    JButton restart,exit,check,solution;

    //creating buttons array to store number options buttons
    JButton[] optionBtns=new JButton[9];

    //creating 2D buttons array to store all 81 buttons
    JButton[][] buttons=new JButton[9][9];

    //creating buttons array to store some prefilled buttos
    JButton[] preFilled=new JButton[38];

    //creating integer array to store answers
    static int[][] answer={
            {5,3,1,4,2,9,8,7,6},
            {6,7,4,5,1,8,9,3,2},
            {8,2,9,3,7,6,1,4,5},
            {2,1,8,6,5,7,4,9,3},
            {9,6,7,2,3,4,5,8,1},
            {4,5,3,9,8,1,6,2,7},
            {3,9,6,1,4,2,7,5,8},
            {7,4,2,8,6,5,3,1,9},
            {1,8,5,7,9,3,2,6,4}
    };

    //constructor
    SudokuSolver(){
        //Initialize frame
        frame=new JFrame();

        //arranging =first row panels in the frame
        int x=15,y=15;
        for(int i=0;i<3;i++){
            //Initialize new panel
            panel[i]=new JPanel();
            //Set the size of panel
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }

        //arranging second row panels in the frame
        x=15;
        y+=175;
        for(int i=3;i<6;i++){
            //Initialize new panel
            panel[i]=new JPanel();
            //Set the size of panel
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }

        //arranging third row panels in the frame
        x=15;
        y+=175;
        for(int i=6;i<9;i++){
            //Initialize new panel
            panel[i]=new JPanel();
            //Set the sze of panel
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }

        //Initialize panel for number options
        optionPanel=new JPanel();
        optionPanel.setBounds(15,570,510,50);
        optionPanel.setLayout(new GridLayout(1,9,5,5));

        //Arranging number option buttons in option panel
        for(int i=0;i<9;i++){
            //Intialize new button
            JButton btn=new JButton(i+1+"");
            btn.setSize(40,40);
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
            //add to option panel
            optionPanel.add(btn);
            optionBtns[i]=btn;
            //setting font family and font size
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            //add acton listner to number option buttons
            btn.addActionListener(this);
            Border b8 = BorderFactory.createLineBorder(Color.black,2);
            btn.setBorder(b8);
            btn.setFocusPainted(false);
        }
        optionBtns[0].setBackground(Color.orange);

        //adding option panel to the main frame
        frame.add(optionPanel);

        //Initialize panel for operations
        operationPanel=new JPanel();
        operationPanel.setBounds(15,650,510,50);
        operationPanel.setLayout(new GridLayout(1,4,20,20));
        operationPanel.setBackground(Color.white);

        //Initalize button for restart operation
        restart=new JButton("Restart");
        restart.setSize(100,40);
        Border b2 = BorderFactory.createLineBorder(Color.black,2);
        restart.setBorder(b2);
        restart.setForeground(Color.black);
        restart.setBackground(Color.orange);
        restart.setFont(new Font("Arial", Font.BOLD, 18));
        //remove line around text
        restart.setFocusPainted(false);
        //add action listner to restart button
        restart.addActionListener(this);

        //Initalize button for exit operation
        exit=new JButton("Exit");
        exit.setSize(100,40);
        Border b3 = BorderFactory.createLineBorder(Color.black,2);
        exit.setBorder(b3);
        exit.setForeground(Color.black);
        exit.setBackground(Color.orange);
        exit.setFont(new Font("Arial", Font.BOLD, 18));
        //remove line around text
        exit.setFocusPainted(false);
        //add action listner to exit button
        exit.addActionListener(this);

        //Initalize button for check operation
        check=new JButton("Check");
        check.setSize(100,40);
        Border b4 = BorderFactory.createLineBorder(Color.black,2);
        check.setBorder(b4);
        check.setForeground(Color.black);
        check.setBackground(Color.orange);
        check.setFont(new Font("Arial", Font.BOLD, 18));
        //remove line around text
        check.setFocusPainted(false);
        //add action listner to check button
        check.addActionListener(this);

        //Initalize button for solution operation
        solution=new JButton("Solution");
        solution.setSize(100,40);
        Border b5 = BorderFactory.createLineBorder(Color.black,2);
        solution.setBorder(b5);
        solution.setForeground(Color.black);
        solution.setBackground(Color.orange);
        solution.setFont(new Font("Arial", Font.BOLD, 18));
        //remove line around text
        solution.setFocusPainted(false);
        //add action listner to solution button
        solution.addActionListener(this);

        //add restart,exit,check,solution to operation panel
        operationPanel.add(restart);
        operationPanel.add(exit);
        operationPanel.add(check);
        operationPanel.add(solution);


        //add operation panel to frame
        frame.add(operationPanel);

        //Initialize all sudoku 81 buttons
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                buttons[i][j]=new JButton();
                buttons[i][j].setSize(40,40);
                buttons[i][j].setForeground(Color.black);
                Border b1 = BorderFactory.createLineBorder(Color.black,2);
                buttons[i][j].setBorder(b1);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                buttons[i][j].setBackground(Color.white);
                //add action listner to sudoku button
                buttons[i][j].addActionListener(this);
                //remove line around text
                buttons[i][j].setFocusPainted(false);
            }
        }


        //temporary array to store prefilled buttons
        JButton[] temp={buttons[0][0],buttons[0][1],buttons[0][4],buttons[0][5],buttons[0][8],buttons[1][0],buttons[1][2],buttons[1][3],buttons[1][8],buttons[2][0],buttons[2][2],buttons[2][4],
                buttons[2][5],buttons[3][1],buttons[3][2],buttons[3][3],buttons[3][4],buttons[3][5],buttons[4][2],buttons[4][6],buttons[5][3],buttons[5][4],buttons[5][5],buttons[5][6],buttons[5][7],
                buttons[6][3],buttons[6][4],buttons[6][6],buttons[6][8],buttons[7][0],buttons[7][5],buttons[7][6],buttons[7][8],buttons[8][0],buttons[8][3],buttons[8][4],buttons[8][7],buttons[8][8]};
        
        //storing prefilled buttons
        for(int i=0;i<38;i++){
            preFilled[i]=temp[i];
        }

        //assigning prefilled buttons content to the frame
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Boolean flag=true;
                //checking for prefilled buttons
                for(JButton p:preFilled){
                    System.out.println(p);
                    if(buttons[i][j]==p){
                        flag=false;
                    }
                }
                if(!flag){
                    buttons[i][j].setText(answer[i][j]+"");
                    buttons[i][j].setBackground(Color.orange);
                    buttons[i][j].setForeground(Color.black);
                }
            }
        }

        //adding all sudoku buttons to respective panels
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                //adding sudoku buttons to the first row
                if(i>=0 && i<=2) panel[j/3].add(buttons[i][j]);
                //adding sudoku buttons to the second row
                else if(i>=3 && i<=5) panel[(j/3)+3].add(buttons[i][j]);
                //adding sudoku buttons to the third row
                else panel[(j/3)+6].add(buttons[i][j]);
            }
        }

        //add 9 panels to the frame
        for(int i=0;i<9;i++){
            frame.add(panel[i]);
        }


        frame.setBounds(100,100,555,800);
        frame.setTitle("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(null);
        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(actionEvent.getSource()==restart){
            //perform restart operaton
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    boolean flag=true;
                    //checking for prefilled buttons
                    for(JButton p:preFilled){
                        if(buttons[i][j]==p){
                            flag=false;
                        }
                    }
                    if(!flag) continue;
                    buttons[i][j].setBackground(Color.white);
                    buttons[i][j].setText("");
                }
            }
        }
        if(actionEvent.getSource()==exit){
            int exit=JOptionPane.showConfirmDialog(frame,"Do you want to exit?","Exit",JOptionPane.YES_NO_OPTION);
            if(exit==JOptionPane.YES_NO_OPTION){
                System.exit(0);
            }
        }
        if(actionEvent.getSource()==check){
            //perform ckeck operation
            check.setText("Hide");
            //checking the filled buttons
            if(solFlag){
                solFlag=false;
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        boolean flag=true;
                        //checking for prefilled buttons
                        for(JButton p:preFilled) {
                            if(buttons[i][j]==p){
                                flag=false;
                            }
                        }
                        if(!flag) continue;
                        if(!buttons[i][j].getText().equals("")){
                            if(buttons[i][j].getText().equals(answer[i][j]+"")){
                                buttons[i][j].setBackground(Color.green);
                            }
                            else{
                                buttons[i][j].setBackground(Color.red);
                            }
                        }

                    }
                }
            }
            //hiding the checked buttons
            else{
                check.setText("Check");
                solFlag=true;
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        boolean flag=true;
                        //checking for prefilled buttons
                        for(JButton p:preFilled) {
                            if(buttons[i][j]==p){
                                flag=false;
                            }
                        }
                        if(!flag) continue;
                        if(!buttons[i][j].getText().equals("")){
                            buttons[i][j].setBackground(Color.white);
                        }
                    }
                }
            }
        }
        if(actionEvent.getSource()==solution){
            //perform solution operaton
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    boolean flag=true;
                    //checking for prefilled buttons
                    for(JButton p:preFilled){
                        if(p==buttons[i][j]){
                            flag=false;
                        }
                    }
                    if(!flag) continue;
                    buttons[i][j].setText(answer[i][j]+"");
                    buttons[i][j].setBackground(Color.white);

                }
            }
        }


        //checking if the number buttons are clicked or not.
        for(JButton b:optionBtns){
            if(actionEvent.getSource()==b){
                turn=b.getText();
                changeValue(actionEvent);
            }
        }

        //checking if the sudoku buttons are clicked or not.
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                boolean flag=true;
                for(JButton p:preFilled){
                    if(actionEvent.getSource()==p){
                        flag=false;
                    }
                }
                if(flag && buttons[i][j]==actionEvent.getSource()){
                    buttons[i][j].setText(turn);
                }
            }
        }
    }


    private void changeValue(ActionEvent actionEvent) {
        for(JButton b:optionBtns){
            b.setBackground(Color.black);
            if(actionEvent.getSource()==b){
                b.setBackground(Color.orange);
            }
        }
    }

    public static void main(String[] args) {
        SudokuSolver sudokuSolver=new SudokuSolver();
    }
}
