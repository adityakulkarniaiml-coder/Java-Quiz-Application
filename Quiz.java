package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz extends JFrame implements ActionListener {
    
    String questions[][] = new String[20][5];
    String answers[][] = new String[20][2];
    String useranswers[][] = new String[20][1];
    JLabel qno, question, moneyLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, quit, lifeline5050, flipQuestion, hint;
    
    public static int timer = 60;
    public static int ans_given = 0;
    public static int count = 0;
    public static int score = 0;
    private long currentMoney = 0;
    
    // Lifeline tracking
    private boolean used5050 = false;
    private boolean usedFlip = false;
    private boolean usedHint = false;
    private String originalQuestion = "";
    
    String name;
    
    Quiz(String name) {
        this.name = name;
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(new Color(0, 0, 139)); // Dark blue background
        setLayout(null);
        
        // KBC header with logo
        JLabel header = new JLabel("KAUN BANEGA CROREPATI", JLabel.CENTER);
        header.setBounds(0, 0, 1440, 60);
        header.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 36));
        header.setForeground(new Color(255, 215, 0)); // Gold
        header.setBackground(new Color(0, 0, 100));
        header.setOpaque(true);
        add(header);
        
        // Money display at top
        moneyLabel = new JLabel("Current Money: ₹0", JLabel.CENTER);
        moneyLabel.setBounds(600, 70, 300, 40);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        moneyLabel.setForeground(new Color(255, 215, 0));
        moneyLabel.setBackground(new Color(25, 25, 112));
        moneyLabel.setOpaque(true);
        moneyLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        add(moneyLabel);
        
        // Question number and question
        qno = new JLabel();
        qno.setBounds(100, 150, 100, 40);
        qno.setFont(new Font("Tahoma", Font.BOLD, 28));
        qno.setForeground(Color.WHITE);
        add(qno);
        
        question = new JLabel();
        question.setBounds(100, 200, 900, 40);
        question.setFont(new Font("Tahoma", Font.BOLD, 24));
        question.setForeground(Color.WHITE);
        add(question);
        
        // Initialize company questions
        initializeQuestions();
        
        // Options with better styling
        opt1 = new JRadioButton();
        opt1.setBounds(120, 280, 800, 35);
        opt1.setBackground(new Color(0, 0, 139));
        opt1.setFont(new Font("Arial", Font.PLAIN, 20));
        opt1.setForeground(Color.WHITE);
        add(opt1);
        
        opt2 = new JRadioButton();
        opt2.setBounds(120, 330, 800, 35);
        opt2.setBackground(new Color(0, 0, 139));
        opt2.setFont(new Font("Arial", Font.PLAIN, 20));
        opt2.setForeground(Color.WHITE);
        add(opt2);
        
        opt3 = new JRadioButton();
        opt3.setBounds(120, 380, 800, 35);
        opt3.setBackground(new Color(0, 0, 139));
        opt3.setFont(new Font("Arial", Font.PLAIN, 20));
        opt3.setForeground(Color.WHITE);
        add(opt3);
        
        opt4 = new JRadioButton();
        opt4.setBounds(120, 430, 800, 35);
        opt4.setBackground(new Color(0, 0, 139));
        opt4.setFont(new Font("Arial", Font.PLAIN, 20));
        opt4.setForeground(Color.WHITE);
        add(opt4);
        
        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);
        
        // Quit button at top right
        quit = new JButton("QUIT");
        quit.setBounds(1200, 70, 120, 40);
        quit.setFont(new Font("Arial", Font.BOLD, 18));
        quit.setBackground(new Color(220, 20, 60)); // Red
        quit.setForeground(Color.WHITE);
        quit.addActionListener(this);
        add(quit);
        
        // Next button
        next = new JButton("NEXT");
        next.setBounds(1100, 500, 200, 50);
        next.setFont(new Font("Tahoma", Font.BOLD, 22));
        next.setBackground(new Color(30, 144, 255)); // Blue
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        add(next);
        
        // Lifeline buttons
        lifeline5050 = new JButton("50-50");
        lifeline5050.setBounds(1100, 570, 200, 40);
        lifeline5050.setFont(new Font("Tahoma", Font.BOLD, 18));
        lifeline5050.setBackground(new Color(255, 140, 0)); // Orange
        lifeline5050.setForeground(Color.WHITE);
        lifeline5050.addActionListener(this);
        add(lifeline5050);
        
        flipQuestion = new JButton("Flip Question");
        flipQuestion.setBounds(1100, 620, 200, 40);
        flipQuestion.setFont(new Font("Tahoma", Font.BOLD, 16));
        flipQuestion.setBackground(new Color(34, 139, 34)); // Green
        flipQuestion.setForeground(Color.WHITE);
        flipQuestion.addActionListener(this);
        add(flipQuestion);
        
        hint = new JButton("Hint");
        hint.setBounds(1100, 670, 200, 40);
        hint.setFont(new Font("Tahoma", Font.BOLD, 18));
        hint.setBackground(new Color(148, 0, 211)); // Purple
        hint.setForeground(Color.WHITE);
        hint.addActionListener(this);
        add(hint);
        
        start(count);
        updateMoneyDisplay();
        
        setVisible(true);
    }
    
    private void initializeQuestions() {
        // Question 1 - ₹1,000
        questions[0][0] = "Which company's logo features a bitten apple?";
        questions[0][1] = "Apple";
        questions[0][2] = "Microsoft";
        questions[0][3] = "Samsung";
        questions[0][4] = "Google";
        answers[0][1] = "Apple";

        // Question 2 - ₹2,000
        questions[1][0] = "Who founded Microsoft along with Paul Allen?";
        questions[1][1] = "Bill Gates";
        questions[1][2] = "Steve Jobs";
        questions[1][3] = "Mark Zuckerberg";
        questions[1][4] = "Jeff Bezos";
        answers[1][1] = "Bill Gates";

        // Question 3 - ₹4,000
        questions[2][0] = "Which company owns Instagram?";
        questions[2][1] = "Meta (Facebook)";
        questions[2][2] = "Google";
        questions[2][3] = "Twitter";
        questions[2][4] = "Amazon";
        answers[2][1] = "Meta (Facebook)";

        // Question 4 - ₹8,000
        questions[3][0] = "What is the name of Amazon's founder?";
        questions[3][1] = "Jeff Bezos";
        questions[3][2] = "Elon Musk";
        questions[3][3] = "Warren Buffett";
        questions[3][4] = "Richard Branson";
        answers[3][1] = "Jeff Bezos";

        // Question 5 - ₹16,000
        questions[4][0] = "Which company's logo has a blue bird?";
        questions[4][1] = "Twitter";
        questions[4][2] = "Facebook";
        questions[4][3] = "LinkedIn";
        questions[4][4] = "Skype";
        answers[4][1] = "Twitter";

        // Question 6 - ₹32,000
        questions[5][0] = "Who is the CEO of Tesla and SpaceX?";
        questions[5][1] = "Elon Musk";
        questions[5][2] = "Tim Cook";
        questions[5][3] = "Sundar Pichai";
        questions[5][4] = "Satya Nadella";
        answers[5][1] = "Elon Musk";

        // Question 7 - ₹64,000
        questions[6][0] = "Which company developed the Android operating system?";
        questions[6][1] = "Google";
        questions[6][2] = "Apple";
        questions[6][3] = "Microsoft";
        questions[6][4] = "IBM";
        answers[6][1] = "Google";

        // Question 8 - ₹1,25,000
        questions[7][0] = "What is the name of Nike's famous slogan?";
        questions[7][1] = "Just Do It";
        questions[7][2] = "Think Different";
        questions[7][3] = "I'm Lovin' It";
        questions[7][4] = "The Ultimate Driving Machine";
        answers[7][1] = "Just Do It";

        // Question 9 - ₹2,50,000
        questions[8][0] = "Which company is known for its 'Think Different' campaign?";
        questions[8][1] = "Apple";
        questions[8][2] = "Dell";
        questions[8][3] = "HP";
        questions[8][4] = "Lenovo";
        answers[8][1] = "Apple";

        // Question 10 - ₹5,00,000
        questions[9][0] = "Who founded Alibaba Group?";
        questions[9][1] = "Jack Ma";
        questions[9][2] = "Ma Huateng";
        questions[9][3] = "Robin Li";
        questions[9][4] = "Lei Jun";
        answers[9][1] = "Jack Ma";

        // Question 11 - ₹10,00,000
        questions[10][0] = "Which company's logo features a white swoosh?";
        questions[10][1] = "Nike";
        questions[10][2] = "Adidas";
        questions[10][3] = "Puma";
        questions[10][4] = "Reebok";
        answers[10][1] = "Nike";

        // Question 12 - ₹20,00,000
        questions[11][0] = "Who is the current CEO of Google?";
        questions[11][1] = "Sundar Pichai";
        questions[11][2] = "Satya Nadella";
        questions[11][3] = "Tim Cook";
        questions[11][4] = "Andy Jassy";
        answers[11][1] = "Sundar Pichai";

        // Question 13 - ₹40,00,000
        questions[12][0] = "Which company created the iPhone?";
        questions[12][1] = "Apple";
        questions[12][2] = "Samsung";
        questions[12][3] = "Google";
        questions[12][4] = "Microsoft";
        answers[12][1] = "Apple";

        // Question 14 - ₹80,00,000
        questions[13][0] = "What was Facebook originally called?";
        questions[13][1] = "TheFacebook";
        questions[13][2] = "Facemash";
        questions[13][3] = "SocialNet";
        questions[13][4] = "ConnectU";
        answers[13][1] = "TheFacebook";

        // Question 15 - ₹1,60,00,000
        questions[14][0] = "Which company owns YouTube?";
        questions[14][1] = "Google";
        questions[14][2] = "Microsoft";
        questions[14][3] = "Amazon";
        questions[14][4] = "Apple";
        answers[14][1] = "Google";

        // Question 16 - ₹32,00,000
        questions[15][0] = "Who is the founder of SpaceX?";
        questions[15][1] = "Elon Musk";
        questions[15][2] = "Jeff Bezos";
        questions[15][3] = "Richard Branson";
        questions[15][4] = "Bill Gates";
        answers[15][1] = "Elon Musk";

        // Question 17 - ₹64,00,000
        questions[16][0] = "Which company's logo has a red target?";
        questions[16][1] = "Target";
        questions[16][2] = "Walmart";
        questions[16][3] = "Best Buy";
        questions[16][4] = "Costco";
        answers[16][1] = "Target";

        // Question 18 - ₹1,25,00,000
        questions[17][0] = "Who founded Netflix?";
        questions[17][1] = "Reed Hastings";
        questions[17][2] = "Marc Randolph";
        questions[17][3] = "Bob Iger";
        questions[17][4] = "David Zaslav";
        answers[17][1] = "Reed Hastings";

        // Question 19 - ₹2,50,00,000
        questions[18][0] = "Which company is known for its 'Golden Arches' logo?";
        questions[18][1] = "McDonald's";
        questions[18][2] = "Burger King";
        questions[18][3] = "KFC";
        questions[18][4] = "Wendy's";
        answers[18][1] = "McDonald's";

        // Question 20 - ₹5,00,00,000
        questions[19][0] = "Who co-founded Google with Sergey Brin?";
        questions[19][1] = "Larry Page";
        questions[19][2] = "Eric Schmidt";
        questions[19][3] = "Sundar Pichai";
        questions[19][4] = "Andy Rubin";
        answers[19][1] = "Larry Page";
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            handleNext();
        } else if (ae.getSource() == quit) {
            handleQuit();
        } else if (ae.getSource() == lifeline5050) {
            use5050Lifeline();
        } else if (ae.getSource() == flipQuestion) {
            useFlipLifeline();
        } else if (ae.getSource() == hint) {
            useHintLifeline();
        }
    }
    
    private void handleNext() {
        // Check if answer is correct and update money
        if (groupoptions.getSelection() != null) {
            String selectedAnswer = groupoptions.getSelection().getActionCommand();
            if (selectedAnswer.equals(answers[count][1])) {
                updateMoney();
            }
        }
        
        repaint();
        resetOptions();
        
        ans_given = 1;
        if (groupoptions.getSelection() == null) {
           useranswers[count][0] = "";
        } else {
            useranswers[count][0] = groupoptions.getSelection().getActionCommand();
        }
        
        if (count == 19) {
            next.setEnabled(false);
            // Auto-submit when last question is answered
            if (groupoptions.getSelection() != null) {
                handleSubmit();
                return;
            }
        }
        
        count++;
        start(count);
        resetLifelinesForNewQuestion();
        updateMoneyDisplay();
    }
    
    private void handleQuit() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to quit?\n\n" +
            "You will take home: " + formatMoney(currentMoney) + "\n" +
            "Questions completed: " + count + "/20",
            "Quit Game",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            calculateFinalScore();
            setVisible(false);
            new Score(name, score);
        }
    }
    
    private void updateMoney() {
        if (count == 0) {
            currentMoney = 1000; // First question
        } else {
            currentMoney *= 2; // Double for each correct answer
        }
    }
    
    private void updateMoneyDisplay() {
        moneyLabel.setText("Current Money: " + formatMoney(currentMoney));
    }
    
    private String formatMoney(long money) {
        if (money >= 10000000) {
            return "₹" + (money / 10000000.00) + " Crore";
        } else if (money >= 100000) {
            return "₹" + (money / 100000.00) + " Lakh";
        } else {
            return "₹" + String.format("%,d", money);
        }
    }
    
    private void use5050Lifeline() {
        if (used5050) {
            JOptionPane.showMessageDialog(this, "50-50 lifeline already used!");
            return;
        }
        
        used5050 = true;
        lifeline5050.setEnabled(false);
        lifeline5050.setBackground(Color.GRAY);
        
        String correctAnswer = answers[count][1];
        List<JRadioButton> wrongOptions = new ArrayList<>();
        
        // Collect wrong options
        if (!opt1.getText().equals(correctAnswer)) wrongOptions.add(opt1);
        if (!opt2.getText().equals(correctAnswer)) wrongOptions.add(opt2);
        if (!opt3.getText().equals(correctAnswer)) wrongOptions.add(opt3);
        if (!opt4.getText().equals(correctAnswer)) wrongOptions.add(opt4);
        
        // Randomly remove two wrong options
        Collections.shuffle(wrongOptions);
        for (int i = 0; i < 2 && i < wrongOptions.size(); i++) {
            wrongOptions.get(i).setEnabled(false);
        }
        
        JOptionPane.showMessageDialog(this, "Two incorrect options have been removed!");
    }
    
    private void useFlipLifeline() {
        if (usedFlip) {
            JOptionPane.showMessageDialog(this, "Flip question lifeline already used!");
            return;
        }
        
        usedFlip = true;
        flipQuestion.setEnabled(false);
        flipQuestion.setBackground(Color.GRAY);
        
        // Store original question
        if (originalQuestion.isEmpty()) {
            originalQuestion = questions[count][0];
        }
        
        // Alternative perspective
        String flippedQuestion = "Think about this: " + questions[count][0] + 
                               "\n\nConsider the company's history and brand identity.";
        
        question.setText("<html><body style='width: 900px'>" + flippedQuestion + "</body></html>");
        JOptionPane.showMessageDialog(this, "Question has been flipped! Think about brand identity.");
    }
    
    private void useHintLifeline() {
        if (usedHint) {
            JOptionPane.showMessageDialog(this, "Hint lifeline already used!");
            return;
        }
        
        usedHint = true;
        hint.setEnabled(false);
        hint.setBackground(Color.GRAY);
        
        String currentHint = getHintForQuestion(count);
        JOptionPane.showMessageDialog(this, "Hint: " + currentHint, "Hint", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String getHintForQuestion(int questionIndex) {
        switch(questionIndex) {
            case 0: return "Think of fruit-themed technology company";
            case 1: return "One of the world's richest people for many years";
            case 2: return "Social media giant that also owns WhatsApp";
            case 3: return "Started as an online bookstore";
            case 4: return "Social media platform for short messages";
            case 5: return "Also owns X (formerly Twitter)";
            case 6: return "Search engine giant";
            case 7: return "Sportswear company with Greek goddess name";
            case 8: return "Known for innovation in personal computers";
            case 9: return "Chinese e-commerce giant";
            case 10: return "Just do it! Sportswear brand";
            case 11: return "Indian-born CEO of a search giant";
            case 12: return "Revolutionized smartphones in 2007";
            case 13: return "Started in Harvard dorm room";
            case 14: return "World's largest video platform";
            case 15: return "Visionary behind Tesla and SpaceX";
            case 16: return "Retail store with bullseye logo";
            case 17: return "Streaming service that started with DVDs";
            case 18: return "Fast food with happy meals";
            case 19: return "Stanford PhD dropout";
            default: return "Think about famous tech entrepreneurs";
        }
    }
    
    private void handleSubmit() {
        calculateFinalScore();
        setVisible(false);
        new Score(name, score);
    }
    
    private void calculateFinalScore() {
        // Convert money to score (1 rupee = 1 point for simplicity, or use your own conversion)
        score = (int) (currentMoney / 1000); // Convert to thousands for score display
    }
    
    private void resetOptions() {
        opt1.setEnabled(true);
        opt2.setEnabled(true);
        opt3.setEnabled(true);
        opt4.setEnabled(true);
    }
    
    private void resetLifelinesForNewQuestion() {
        if (!used5050) {
            lifeline5050.setBackground(new Color(255, 140, 0));
        }
        if (!usedFlip) {
            flipQuestion.setBackground(new Color(34, 139, 34));
        }
        if (!usedHint) {
            hint.setBackground(new Color(148, 0, 211));
        }
        
        originalQuestion = "";
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        String time = "Time: " + timer + "s";
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));
        
        if (timer > 0) { 
            g.drawString(time, 1100, 450);
        } else {
            g.drawString("TIME UP!", 1100, 450);
        }
        
        timer--;
        
        try {
            Thread.sleep(1000);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (ans_given == 1) {
            ans_given = 0;
            timer = 60;
        } else if (timer < 0) {
            handleTimeUp();
        }
    }
    
    private void handleTimeUp() {
        timer = 60;
        resetOptions();
        
        if (count == 18) {
            next.setEnabled(false);
            handleSubmit();
        } else if (count == 19) {
            handleSubmit();
        } else {
            if (groupoptions.getSelection() == null) {
               useranswers[count][0] = "";
            } else {
                useranswers[count][0] = groupoptions.getSelection().getActionCommand();
            }
            count++;
            start(count);
            resetLifelinesForNewQuestion();
        }
    }
    
    public void start(int count) {
        qno.setText("Q" + (count + 1) + ".");
        question.setText(questions[count][0]);
        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);
        
        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);
        
        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);
        
        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);
        
        groupoptions.clearSelection();
        originalQuestion = questions[count][0];
    }
    
    public static void main(String[] args) {
        new Quiz("User");
    }
}