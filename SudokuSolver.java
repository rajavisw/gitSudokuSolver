import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver implements ActionListener {
    JFrame frame;
    static String turn="1";
    static boolean solFlag=true;

    JPanel[] panel=new JPanel[9];

    JPanel optionPanel;

    JPanel operationPanel;
    JButton restart,exit,check,solution;

    JButton[] optionBtns=new JButton[9];

    JButton[][] buttons=new JButton[9][9];

    JButton[] preFilled=new JButton[38];
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
    SudokuSolver(){
        frame=new JFrame();

        int x=15,y=15;
        for(int i=0;i<3;i++){
            panel[i]=new JPanel();
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3));
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }
        x=15;
        y+=175;
        for(int i=3;i<6;i++){
            panel[i]=new JPanel();
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3));
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }
        x=15;
        y+=175;
        for(int i=6;i<9;i++){
            panel[i]=new JPanel();
            panel[i].setBounds(x,y,160,160);
            panel[i].setBackground(Color.white);
            panel[i].setLayout(new GridLayout(3,3,3,3));
            x+=175;
        }

        optionPanel=new JPanel();
        optionPanel.setBounds(15,570,510,50);
        optionPanel.setLayout(new GridLayout(1,9,5,5));

        for(int i=0;i<9;i++){
            JButton btn=new JButton(i+1+"");
            btn.setSize(40,40);
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
            optionPanel.add(btn);
            optionBtns[i]=btn;
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            Border b8 = BorderFactory.createLineBorder(Color.black,2);
            btn.setBorder(b8);
            btn.setFocusPainted(false);
        }
        optionBtns[0].setBackground(Color.orange);

        frame.add(optionPanel);

        operationPanel=new JPanel();
        operationPanel.setBounds(15,650,510,50);
        operationPanel.setLayout(new GridLayout(1,4,20,20));
        operationPanel.setBackground(Color.white);

        restart=new JButton("Restart");
        restart.setSize(100,40);
        Border b2 = BorderFactory.createLineBorder(Color.black,2);
        restart.setBorder(b2);
        restart.setForeground(Color.black);
        restart.setBackground(Color.orange);
        restart.setFont(new Font("Arial", Font.BOLD, 18));
        restart.setFocusPainted(false);
        restart.addActionListener(this);

        exit=new JButton("Exit");
        exit.setSize(100,40);
        Border b3 = BorderFactory.createLineBorder(Color.black,2);
        exit.setBorder(b3);
        exit.setForeground(Color.black);
        exit.setBackground(Color.orange);
        exit.setFont(new Font("Arial", Font.BOLD, 18));
        exit.setFocusPainted(false);
        exit.addActionListener(this);

        check=new JButton("Check");
        check.setSize(100,40);
        Border b4 = BorderFactory.createLineBorder(Color.black,2);
        check.setBorder(b4);
        check.setForeground(Color.black);
        check.setBackground(Color.orange);
        check.setFont(new Font("Arial", Font.BOLD, 18));
        check.setFocusPainted(false);
        check.addActionListener(this);

        solution=new JButton("Solution");
        solution.setSize(100,40);
        Border b5 = BorderFactory.createLineBorder(Color.black,2);
        solution.setBorder(b5);
        solution.setForeground(Color.black);
        solution.setBackground(Color.orange);
        solution.setFont(new Font("Arial", Font.BOLD, 18));
        solution.setFocusPainted(false);
        solution.addActionListener(this);

        operationPanel.add(restart);
        operationPanel.add(exit);
        operationPanel.add(check);
        operationPanel.add(solution);



        frame.add(operationPanel);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                buttons[i][j]=new JButton();
                buttons[i][j].setSize(40,40);
                buttons[i][j].setForeground(Color.black);
                Border b1 = BorderFactory.createLineBorder(Color.black,2);
                buttons[i][j].setBorder(b1);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setFocusPainted(false);
            }
        }



        JButton[] temp={buttons[0][0],buttons[0][1],buttons[0][4],buttons[0][5],buttons[0][8],buttons[1][0],buttons[1][2],buttons[1][3],buttons[1][8],buttons[2][0],buttons[2][2],buttons[2][4],
                buttons[2][5],buttons[3][1],buttons[3][2],buttons[3][3],buttons[3][4],buttons[3][5],buttons[4][2],buttons[4][6],buttons[5][3],buttons[5][4],buttons[5][5],buttons[5][6],buttons[5][7],
                buttons[6][3],buttons[6][4],buttons[6][6],buttons[6][8],buttons[7][0],buttons[7][5],buttons[7][6],buttons[7][8],buttons[8][0],buttons[8][3],buttons[8][4],buttons[8][7],buttons[8][8]};
        for(int i=0;i<38;i++){
            preFilled[i]=temp[i];
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Boolean flag=true;
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


        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(i>=0 && i<=2) panel[j/3].add(buttons[i][j]);
                else if(i>=3 && i<=5) panel[(j/3)+3].add(buttons[i][j]);
                else panel[(j/3)+6].add(buttons[i][j]);
            }
        }

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
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    boolean flag=true;
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
            check.setText("Hide");
            if(solFlag){
                solFlag=false;
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        boolean flag=true;
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
            else{
                check.setText("Check");
                solFlag=true;
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        boolean flag=true;
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
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    boolean flag=true;
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


        for(JButton b:optionBtns){
            if(actionEvent.getSource()==b){
                turn=b.getText();
                changeValue(actionEvent);
            }
        }

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
