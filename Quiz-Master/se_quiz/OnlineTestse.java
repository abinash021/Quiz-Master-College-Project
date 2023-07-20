//Subject: Software Engineering
//Semester: 5th
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.Timer;

class OnlineTestse extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  private static final int TIME_LIMIT = 10 * 60; // Time limit in seconds (10 minutes)
  private Timer timer;
  private int elapsedTime;
  private JLabel timerLabel;
  private static String USER_PASSWORD = "123456";
  private static String EMAIL = "test@gmail.com";
  private static String REGDNO = "F20014024025";
  private static String OWNER_PASSWORD = "gpbbbsr";
  // create a string variable accesseble to all methods and can be changed

  public static String pname;
  public static String prollno;
  public static Map<Integer, String> questions = new TreeMap<Integer, String>();
  public static Map<Integer, String> answers = new TreeMap<Integer, String>();
  public static Map<Integer, Integer> correctAnswersBank = new HashMap<Integer, Integer>();
  public static Map<Integer, Integer> controlCurrentMapper = new HashMap<Integer, Integer>();
  public static Map<Integer, Integer> bookmarkMap = new HashMap<Integer, Integer>();
  public static List<Integer> currentPrintList = new ArrayList<Integer>();
  public static boolean bkmarkFlag;
  JLabel label;
  JRadioButton radioButton[] = new JRadioButton[5];
  JButton btnNext, btnBookmark;
  ButtonGroup bg;

  public static void Login() {
    // String rollno = JOptionPane.showInputDialog("Enter Registration No");
    // String name = JOptionPane.showInputDialog("Enter Full Name");
    // String email = JOptionPane.showInputDialog("Enter Email");
    // // String regdno = JOptionPane.showInputDialog("Enter Registration No");
    // String password = JOptionPane.showInputDialog("Enter Password");
    // // take email, registration number as input
    // if (password.equals(USER_PASSWORD)) {
    //   JOptionPane.showMessageDialog(null, "Welcome to Online Test");
    // } else {
    //   JOptionPane.showMessageDialog(null, "Incorrect Password! Try Again");
    //   Login();
    // }
  }

  //   run login

  public static int getRandomNumber() {
    Random rand = new Random();
    return rand.nextInt(40) + 1;
  }

  int count = 0, current = getRandomNumber(), x = 1, y = 1, now = 0, control =
    1, qCount = 10, qBase = 50;
  List<Integer> qList = new ArrayList<Integer>();
  int m[] = new int[qCount];
  Set<Integer> bkSet = new HashSet<Integer>();

  // create a login screen with background image

  // create jFrame with radioButton and JButton
  OnlineTestse(String s) {
    super(s);
    label = new JLabel();
    add(label);
    bg = new ButtonGroup();
    for (int i = 0; i < 5; i++) {
      radioButton[i] = new JRadioButton();
      add(radioButton[i]);
      bg.add(radioButton[i]);
    }
    btnNext = new JButton("Next");
    btnBookmark = new JButton("Bookmark");
    btnNext.addActionListener(this);
    btnBookmark.addActionListener(this);
    add(btnNext);
    add(btnBookmark);
    set();
    label.setBounds(30, 40, 1500, 50);
    label.setFont(new Font("Verdana", Font.PLAIN, 20));
    //radioButton[0].setBounds(50, 80, 200, 20);
    radioButton[0].setBounds(50, 80, 750, 50);
    radioButton[1].setBounds(50, 150, 750, 50);
    radioButton[2].setBounds(50, 220, 750, 50);
    radioButton[3].setBounds(50, 290, 750, 50);
    radioButton[0].setFont(new Font("Verdana", Font.PLAIN, 20));
    radioButton[1].setFont(new Font("Verdana", Font.PLAIN, 20));
    radioButton[2].setFont(new Font("Verdana", Font.PLAIN, 20));
    radioButton[3].setFont(new Font("Verdana", Font.PLAIN, 20));
    btnNext.setBounds(100, 450, 150, 30);
    btnBookmark.setBounds(270, 450, 150, 30);
    btnNext.setFont(new Font("Verdana", Font.PLAIN, 15));
    btnBookmark.setFont(new Font("Verdana", Font.PLAIN, 15));
    btnBookmark.enable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    //setLocation(250, 100);
    setVisible(true);
    //setSize(600, 350);
    setSize(1800, 750);

    timerLabel = new JLabel();
    timerLabel.setBounds(1500, 100, 200, 30);
    timerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
    add(timerLabel);

    // Start the timer
    elapsedTime = 0;
    timer =
      new Timer(
        1000,
        new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            elapsedTime++;
            int remainingTime = TIME_LIMIT - elapsedTime;
            if (remainingTime >= 0) {
              // Update the timer label
              int minutes = remainingTime / 60;
              int seconds = remainingTime % 60;
              String timeString = String.format("%02d:%02d", minutes, seconds);
              timerLabel.setText("Time Left: " + timeString);
            } else {
              // Time limit reached, automatically submit the test
              // submitTest();
            }
          }
        }
      );
    timer.start();
  }

  // handle all actions based on event
  public void actionPerformed(ActionEvent e) {
    controlCurrentMapper.put(1, 1);
    if (e.getSource() == btnNext) {
      //btnBookmark.enable(true);
      for (
        Enumeration<AbstractButton> buttons = bg.getElements();
        buttons.hasMoreElements();
      ) {
        AbstractButton button = buttons.nextElement();

        if (button.isSelected()) {
          answers.put(control, button.getText());
          System.out.println(answers);
        }
      }

      if (check()) {
        count = count + 1;
      }
      control = control + 1;
      current = questionGenerator();
      set();
      if (control >= qCount) {
        btnNext.setEnabled(false);
        btnBookmark.setText("Result");
      }
    } else {
      for (int i = 0, y = 1; i < x; i++, y++) {
        if (e.getActionCommand().equals("Bookmark" + y)) {
          ((JButton) e.getSource()).setEnabled(false);
          now = current;
          current = m[y];
          System.out.println("the bookmark current is " + m[y]);
          control = bookmarkMap.get(y);
          set();
          if (check()) count = count + 1;
          current = now;
        }
      }
    }

    if (e.getActionCommand().equals("Bookmark")) {
      JButton bk = new JButton("Bookmark" + x);
      bk.setBounds(1550, 20 + 30 * x, 120, 30);
      add(bk);
      bk.addActionListener(this);
      m[x] = current;
      bookmarkMap.put(x, control);
      x++;
      //current++;
      control = control + 1;
      current = questionGenerator();
      set();
      if (control >= qCount) btnBookmark.setText("Result");
      setVisible(false);
      setVisible(true);
    } else {
      for (int i = 0, y = 1; i < x; i++, y++) {
        if (e.getActionCommand().equals("Bookmark" + y)) {
          //btnBookmark.enable(false);
          ((JButton) e.getSource()).setEnabled(false);

          now = current;
          current = m[y];
          System.out.println("the bookmark current is " + m[y]);
          control = bookmarkMap.get(y);
          set();
          if (check()) count = count + 1;
          current = now;
        }
      }
    }

    if (e.getActionCommand().equals("Result")) {
      for (
        Enumeration<AbstractButton> buttons = bg.getElements();
        buttons.hasMoreElements();
      ) {
        AbstractButton button = buttons.nextElement();

        if (button.isSelected()) {
          //System.out.println("your selected option is "+button.getText());
          answers.put(control, button.getText());
          System.out.println(answers);
          //return button.getText();
        }
      }
      if (check()) count = count + 1;
      current++;
      JOptionPane.showMessageDialog(this, "correct answers= " + count);
      generateItextExamPdf(count);
      System.exit(0);
    }

    if (e.getActionCommand().equals("Result")) {
      for (
        Enumeration<AbstractButton> buttons = bg.getElements();
        buttons.hasMoreElements();
      ) {
        AbstractButton button = buttons.nextElement();

        if (button.isSelected()) {
          //System.out.println("your selected option is "+button.getText());
          answers.put(control, button.getText());
          System.out.println(answers);
          //return button.getText();
        }
      }
      if (check()) count = count + 1;
      current++;
      JOptionPane.showMessageDialog(this, "correct answers= " + count);
      // generateItextExamPdf(count);
      System.exit(0);
    }
    if (e.getActionCommand().equals("Result")) {
      // Existing code

      // Stop the timer
      timer.stop();
    }
  }

  // SET Questions with options
  void set() {
    radioButton[4].setSelected(true);
    //label.setText("Question "+control+":");
    if (current == 0) {
      label.setText(
        "Question " +
        control +
        ": What is the first step in the software development lifecycle?"
      );
      radioButton[0].setText("System Design");
      radioButton[1].setText("Coding");
      radioButton[2].setText("System Testing");
      radioButton[3].setText("Preliminary Investigation and Analysis");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What is the first step in the software development lifecycle?",
        "System Design",
        "Coding",
        "System Testing",
        "Preliminary Investigation and Analysis",
        null
      );
    }
    if (current == 1) {
      label.setText(
        "Question " +
        control +
        ": What does the study of an existing system refer to?"
      );
      radioButton[0].setText("Details of DFD");
      radioButton[1].setText("Feasibility Study");
      radioButton[2].setText("System Analysis");
      radioButton[3].setText("System Planning");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What does the study of an existing system refer to?",
        "Details of DFD",
        "Feasibility Study",
        "System Analysis",
        "System Planning",
        null
      );
    }
    if (current == 2) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is involved in the system planning and designing phase of the SDLC?"
      );
      radioButton[0].setText("Sizing");
      radioButton[1].setText("Parallel run");
      radioButton[2].setText("Specification freeze");
      radioButton[3].setText("All of the above");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is involved in the system planning and designing phase of the SDLC?",
        "Sizing",
        "Parallel run",
        "Specification freeze",
        "All of the above",
        null
      );
    }
    if (current == 3) {
      label.setText("Question " + control + ":  What does RAD stand for?");
      radioButton[0].setText("Rapid Application Document");
      radioButton[1].setText("Rapid Application Development");
      radioButton[2].setText("Relative Application Development");
      radioButton[3].setText("None of the above");

      setPrintFormat(
        control,
        current,
        "Question " + control + ":  What does RAD stand for?-",
        "Rapid Application Document",
        "Rapid Application Development",
        "Relative Application Development",
        "None of the above",
        null
      );
    }
    if (current == 4) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following prototypes does not associated with Prototyping Model?"
      );
      radioButton[0].setText("Domain Prototype");
      radioButton[1].setText("Vertical Prototype");
      radioButton[2].setText("Horizontal Prototype");
      radioButton[3].setText("Diagonal Prototype");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following prototypes does not associated with Prototyping Model?",
        "Domain Prototype",
        "Vertical Prototype",
        "Horizontal Prototype",
        "Diagonal Prototype",
        null
      );
    }
    if (current == 5) {
      label.setText(
        "Question " + control + ": The major drawback of RAD model is ___."
      );
      radioButton[0].setText(
          "It requires highly skilled developers/designers."
        );
      radioButton[1].setText("It necessitates customer feedbacks.");
      radioButton[2].setText("It increases the component reusability.");
      radioButton[3].setText("Both (a) & (c)");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":  The major drawback of RAD model is ___.",
        "It requires highly skilled developers/designers.",
        "It necessitates customer feedbacks.",
        "It increases the component reusability.",
        "Both (a) & (c)",
        null
      );
    }
    if (current == 6) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following does not relate to Evolutionary Process Model?"
      );
      radioButton[0].setText("Incremental Model");
      radioButton[1].setText("Concurrent Development Model");
      radioButton[2].setText("WINWIN Spiral Model");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following does not relate to Evolutionary Process Model?",
        "Incremental Model",
        "Concurrent Development Model",
        "WINWIN Spiral Model",
        "All of the above",
        null
      );
    }
    if (current == 7) {
      label.setText(
        "Question " +
        control +
        ": What is the major drawback of the Spiral Model?"
      );
      radioButton[0].setText("Higher amount of risk analysis");
      radioButton[1].setText("Doesn't work well for smaller projects");
      radioButton[2].setText("Additional functionalities are added later on");
      radioButton[3].setText("Strong approval and documentation control");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What is the major drawback of the Spiral Model?",
        "Higher amount of risk analysis",
        "Doesn't work well for smaller projects",
        "Additional functionalities are added later on",
        "Strong approval and documentation control",
        null
      );
    }
    if (current == 8) {
      label.setText(
        "Question " + control + ":  Model selection is based on ____."
      );
      radioButton[0].setText("Requirements");
      radioButton[1].setText("Development team & users");
      radioButton[2].setText("Project type & associated risk");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Model selection is based on ____.",
        "Requirements",
        "Development team & users",
        "Project type & associated risk",
        "All of the above",
        null
      );
    }
    if (current == 9) {
      label.setText(
        "Question " + control + ": Which of the following option is correct?"
      );
      radioButton[0].setText(
          "The prototyping model facilitates the reusability of components."
        );
      radioButton[1].setText("RAD Model facilitates reusability of components");
      radioButton[2].setText(
          "Both RAD & Prototyping Model facilitates reusability of components"
        );
      radioButton[3].setText("None");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":  Which of the following option is correct?",
        "The prototyping model facilitates the reusability of components.",
        "RAD Model facilitates reusability of components",
        "Both RAD & Prototyping Model facilitates reusability of components",
        "None",
        null
      );
    }
    if (current == 10) {
      label.setText(
        "Question " +
        control +
        ": Which of the following models doesn't necessitate defining requirements at the earliest in the lifecycle?"
      );
      radioButton[0].setText("RAD & Waterfall");
      radioButton[1].setText("Prototyping & Waterfall");
      radioButton[2].setText("Spiral & Prototyping");
      radioButton[3].setText("Spiral & RAD");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following models doesn't necessitate defining requirements at the earliest in the lifecycle?",
        "RAD & Waterfall",
        "Prototyping & Waterfall",
        "Spiral & Prototyping",
        "Spiral & RAD",
        null
      );
    }
    if (current == 11) {
      label.setText(
        "Question " +
        control +
        ": When the user participation isn't involved, which of the following models will not result in the desired output?"
      );
      radioButton[0].setText("Prototyping & Waterfall");
      radioButton[1].setText("Prototyping & RAD");
      radioButton[2].setText("Prototyping & Spiral");
      radioButton[3].setText("RAD & Spiral");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": When the user participation isn't involved, which of the following models will not result in the desired output?",
        "Prototyping & Waterfall",
        "Prototyping & RAD",
        "Prototyping & Spiral",
        "RAD & Spiral",
        null
      );
    }
    if (current == 12) {
      label.setText(
        "Question " +
        control +
        ": Which of the following model will be preferred by a company that is planning to deploy an advanced version of the existing software in the market?"
      );
      radioButton[0].setText("Spiral");
      radioButton[1].setText("Iterative Enhancement");
      radioButton[2].setText("RAD");
      radioButton[3].setText("Both (b) and (c)");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following model will be preferred by a company that is planning to deploy an advanced version of the existing software in the market?",
        "Spiral",
        "Iterative Enhancement",
        "RAD",
        "Both (b) and (c)",
        null
      );
    }
    if (current == 13) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is an example of Black Box and Functional Processing? "
      );
      radioButton[0].setText("First Generation Language");
      radioButton[1].setText("Second Generation Language");
      radioButton[2].setText("Third Generation Language");
      radioButton[3].setText("Fourth Generation Language");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is an example of Black Box and Functional Processing?",
        "First Generation Language",
        "Second Generation Language",
        "Third Generation Language",
        "Fourth Generation Language",
        null
      );
    }
    if (current == 14) {
      label.setText(
        "Question " +
        control +
        ": ____ is identified as fourth generation language."
      );
      radioButton[0].setText("Unix shell");
      radioButton[1].setText("C++");
      radioButton[2].setText("COBOL");
      radioButton[3].setText("FORTRAN");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": ____ is identified as fourth generation language.",
        "Unix shell",
        "C++",
        "COBOL",
        "FORTRAN",
        null
      );
    }
    if (current == 15) {
      label.setText(
        "Question " +
        control +
        ": Which of the following model has a major downfall to a software development life cycle in terms of the coding phase? -"
      );
      radioButton[0].setText("4GT Model");
      radioButton[1].setText("Waterfall Model");
      radioButton[2].setText("RAD Model");
      radioButton[3].setText("Spiral Model");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following model has a major downfall to a software development life cycle in terms of the coding phase?",
        "4GT Model",
        "Waterfall Model",
        "RAD Model",
        "Spiral Model",
        null
      );
    }
    if (current == 16) {
      label.setText(
        "Question " +
        control +
        ":   Which of the following falls under the category of software products?"
      );
      radioButton[0].setText("Firmware, CAD");
      radioButton[1].setText("Embedded, CAM");
      radioButton[2].setText("Customized, Generic");
      radioButton[3].setText("CAD, Embedded");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":   Which of the following falls under the category of software products?",
        "Firmware, CAD",
        "Embedded, CAM",
        "Customized, Generic",
        "CAD, Embedded",
        null
      );
    }
    if (current == 17) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following activities of the generic process framework delivers a feedback report?"
      );
      radioButton[0].setText("Deployment");
      radioButton[1].setText("Planning");
      radioButton[2].setText("Modeling");
      radioButton[3].setText("Construction");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following activities of the generic process framework delivers a feedback report?",
        "Deployment",
        "Planning",
        "Modeling",
        "Construction",
        null
      );
    }
    if (current == 18) {
      label.setText(
        "Question " +
        control +
        ": Which of the following refers to internal software equality?"
      );
      radioButton[0].setText("Scalability");
      radioButton[1].setText("Reusability");
      radioButton[2].setText("Reliability");
      radioButton[3].setText("Usability");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following refers to internal software equality?",
        "Scalability",
        "Reusability",
        "Reliability",
        "Usability",
        null
      );
    }
    if (current == 19) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following activities is not recommended for software processes in software engineering?"
      );
      radioButton[0].setText("Software Evolution");
      radioButton[1].setText("Software Verification");
      radioButton[2].setText("Software Testing & Validation");
      radioButton[3].setText("Software designing");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following activities is not recommended for software processes in software engineering?",
        "Software Evolution",
        "Software Verification",
        "Software Testing & Validation",
        "Software designing",
        null
      );
    }
    if (current == 20) {
      label.setText(
        "Question " +
        control +
        ": The agile software development model is built based on ____."
      );
      radioButton[0].setText("Linear Development");
      radioButton[1].setText("Incremental Development");
      radioButton[2].setText("Iterative Development");
      radioButton[3].setText("Both Incremental and Iterative Development");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The agile software development model is built based on ____.",
        "Linear Development",
        "Incremental Development",
        "Iterative Development",
        "Both Incremental and Iterative Development",
        null
      );
    }
    if (current == 21) {
      label.setText(
        "Question " +
        control +
        ": The ____ model helps in representing the system's dynamic behavior."
      );
      radioButton[0].setText("Object Model");
      radioButton[1].setText("Context Model");
      radioButton[2].setText("Behavioral Model");
      radioButton[3].setText("Data Model");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The ____ model helps in representing the system's dynamic behavior.",
        "Object Model",
        "Context Model",
        "Behavioral Model",
        "Data Model",
        null
      );
    }
    if (current == 22) {
      label.setText(
        "Question " +
        control +
        ": The _____ and _____ are the two major dimensions encompassed in the Spiral model."
      );
      radioButton[0].setText("Diagonal, Perpendicular");
      radioButton[1].setText("Perpendicular, Radial");
      radioButton[2].setText("Angular, diagonal");
      radioButton[3].setText("Radial, Angular");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  The ____ and _____ are the two major dimensions encompassed in the Spiral model.",
        "Diagonal, Perpendicular",
        "Perpendicular, Radial",
        "Angular, diagonal",
        "Radial, Angular",
        null
      );
    }
    if (current == 23) {
      label.setText(
        "Question " +
        control +
        ": Which of the following technique is involved in certifying the sustained development of legacy systems?"
      );
      radioButton[0].setText("Reengineering");
      radioButton[1].setText("Forward engineering");
      radioButton[2].setText("Reverse engineering");
      radioButton[3].setText("Reverse engineering and Reengineering");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following technique is involved in certifying the sustained development of legacy systems?",
        "Reengineering",
        "Forward engineering",
        "Reverse engineering",
        "Reverse engineering and Reengineering",
        null
      );
    }
    if (current == 24) {
      label.setText(
        "Question " +
        control +
        ": An erroneous system state that results in an unexpected system behavior is acknowledged as?"
      );
      radioButton[0].setText("System failure");
      radioButton[1].setText("Human error or mistake");
      radioButton[2].setText("System error");
      radioButton[3].setText("System fault");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": An erroneous system state that results in an unexpected system behavior is acknowledged as?",
        "System failure",
        "Human error or mistake",
        "System error",
        "System fault",
        null
      );
    }
    if (current == 25) {
      label.setText(
        "Question " +
        control +
        ": What is the name of the approach that follows step-by-step instructions for solving a problem?"
      );
      radioButton[0].setText("An Algorithm");
      radioButton[1].setText("A Plan");
      radioButton[2].setText("A List");
      radioButton[3].setText("Sequential Structure");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What is the name of the approach that follows step-by-step instructions for solving a problem?",
        "An Algorithm",
        "A Plan",
        "A List",
        "Sequential Structure",
        null
      );
    }
    if (current == 26) {
      label.setText(
        "Question " +
        control +
        ": Which of the following word correctly summarized the importance of software design?"
      );
      radioButton[0].setText("Quality");
      radioButton[1].setText("Complexity");
      radioButton[2].setText("Efficiency");
      radioButton[3].setText("Accuracy");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following word correctly summarized the importance of software design?",
        "Quality",
        "Complexity",
        "Efficiency",
        "Accuracy",
        null
      );
    }
    if (current == 27) {
      label.setText(
        "Question " +
        control +
        ": What does a data store symbol in the DFD signify?"
      );
      radioButton[0].setText("Logical File");
      radioButton[1].setText("Physical File");
      radioButton[2].setText("Data Structure");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What does a data store symbol in the DFD signify?",
        "Logical File",
        "Physical File",
        "Data Structure",
        "All of the above",
        null
      );
    }
    if (current == 28) {
      label.setText(
        "Question " + control + ": _____ is not a direct measure of SE process."
      );
      radioButton[0].setText("Effort");
      radioButton[1].setText("Cost");
      radioButton[2].setText("Efficiency");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": _____ is not a direct measure of SE process.",
        "Effort",
        "Cost",
        "Efficiency",
        "All of the above",
        null
      );
    }
    if (current == 29) {
      label.setText(
        "Question " +
        control +
        ":  What is the main task of project indicators?"
      );
      radioButton[0].setText(
          "To evaluate the ongoing project's status and track possible risks."
        );
      radioButton[1].setText("To evaluate the ongoing project's status.");
      radioButton[2].setText("To track potential risks.");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What is the main task of project indicators?",
        "To evaluate the ongoing project's status and track possible risks.",
        "To evaluate the ongoing project's status.",
        "To track potential risks.",
        "None of the above",
        null
      );
    }
    if (current == 30) {
      label.setText(
        "Question " + control + ":  What is the main intent of project metrics?"
      );
      radioButton[0].setText("For strategic purposes");
      radioButton[1].setText("To minimize the development schedule.");
      radioButton[2].setText(
          "To evaluate the ongoing project's quality on a daily basis"
        );
      radioButton[3].setText("Both (b) & (c)");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  What is the main intent of project metrics?",
        "For strategic purposes",
        "To minimize the development schedule.",
        "To evaluate the ongoing project's quality on a daily basis",
        "Both (b) & (c)",
        null
      );
    }
    if (current == 31) {
      label.setText(
        "Question " +
        control +
        ":  Name the graphical practice that depicts the meaningful changes that occurred in metrics data."
      );
      radioButton[0].setText("Function point analysis");
      radioButton[1].setText("Control Chart");
      radioButton[2].setText("DRE(Defect Removal Efficiency)");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Name the graphical practice that depicts the meaningful changes that occurred in metrics data.",
        "Function point analysis",
        "Control Chart",
        "DRE(Defect Removal Efficiency)",
        "None of the above",
        null
      );
    }
    if (current == 32) {
      label.setText(
        "Question " +
        control +
        ": Which parameters are essentially used while computing the software development cost?"
      );
      radioButton[0].setText("Hardware and Software Costs");
      radioButton[1].setText("Effort Costs");
      radioButton[2].setText("Travel and Training Costs");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which parameters are essentially used while computing the software development cost?",
        "Hardware and Software Costs",
        "Effort Costs",
        "Travel and Training Costs",
        "All of the above",
        null
      );
    }
    if (current == 33) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is an incorrect activity for the configuration management of a software system?"
      );
      radioButton[0].setText("Change management");
      radioButton[1].setText("System management");
      radioButton[2].setText("Internship management");
      radioButton[3].setText("Version management");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is an incorrect activity for the configuration management of a software system?",
        "Change management",
        "System management",
        "Internship management",
        "Version management",
        null
      );
    }
    if (current == 34) {
      label.setText(
        "Question " +
        control +
        ":  The project planner examines the statement of scope and extracts all-important software functions, which is known as?"
      );
      radioButton[0].setText("Planning process");
      radioButton[1].setText("Decomposition");
      radioButton[2].setText("Association");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  The project planner examines the statement of scope and extracts all-important software functions, which is known as?",
        "Planning process",
        "Decomposition",
        "Association",
        "All of the above",
        null
      );
    }
    if (current == 35) {
      label.setText(
        "Question " +
        control +
        ": Which of the following does not complement the decomposition techniques but offers a potential estimation approach for their impersonal growth?"
      );
      radioButton[0].setText("Empirical estimation models");
      radioButton[1].setText("Decomposition techniques");
      radioButton[2].setText("Automated estimation tools");
      radioButton[3].setText("Both (a) & (c)");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following does not complement the decomposition techniques but offers a potential estimation approach for their impersonal growth?",
        "Empirical estimation models",
        "Decomposition techniques",
        "Automated estimation tools",
        "Both (a) & (c)",
        null
      );
    }
    if (current == 36) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is not included in the total effort cost?"
      );
      radioButton[0].setText("Costs of lunch time food");
      radioButton[1].setText("Costs of support staff");
      radioButton[2].setText("Costs of networking and communications");
      radioButton[3].setText(
          "Costs of air conditioning and lighting in the office space"
        );
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following is not included in the total effort cost?",
        "Costs of lunch time food",
        "Costs of support staff",
        "Costs of networking and communications",
        "Costs of air conditioning and lighting in the office space",
        null
      );
    }
    if (current == 37) {
      label.setText(
        "Question " +
        control +
        ": What is developed by utilizing the historical cost function?"
      );
      radioButton[0].setText("Parkinson's Law");
      radioButton[1].setText("Expert judgment");
      radioButton[2].setText("Algorithmic cost modeling");
      radioButton[3].setText("Estimation by analogy");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What is developed by utilizing the historical cost function?",
        "Parkinson's Law",
        "Expert judgment",
        "Algorithmic cost modeling",
        "Estimation by analogy",
        null
      );
    }
    if (current == 38) {
      label.setText(
        "Question " +
        control +
        ": Which of the following model has a misconception that systems are built by utilizing reusable components, scripts, and database programs?"
      );
      radioButton[0].setText("The reuse model");
      radioButton[1].setText("An early designed model");
      radioButton[2].setText("An application-composition model");
      radioButton[3].setText("A post architecture model");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following model has a misconception that systems are built by utilizing reusable components, scripts, and database programs?",
        "The reuse model",
        "An early designed model",
        "An application-composition model",
        "A post architecture model",
        null
      );
    }
    if (current == 39) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is used to predict the effort as a function of LOC or FP?"
      );
      radioButton[0].setText("COCOMO");
      radioButton[1].setText("FP-Based estimation");
      radioButton[2].setText("Both (a) & (b)");
      radioButton[3].setText("Process-based estimation");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is used to predict the effort as a function of LOC or FP?",
        "COCOMO",
        "FP-Based estimation",
        "Both (a) & (b)",
        "Process-based estimation",
        null
      );
    }
    if (current == 40) {
      label.setText(
        "Question " +
        control +
        ": Once the requirements are stabilized, the basic architecture of the software can be established. Which of the following version of the COCOMO model conforms to the given statement?"
      );
      radioButton[0].setText("Application composition model");
      radioButton[1].setText("Post-architecture-stage model");
      radioButton[2].setText("Early design stage model");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Once the requirements are stabilized, the basic architecture of the software can be established. Which of the following version of the COCOMO model conforms to the given statement?",
        "Application composition model",
        "Post-architecture-stage model",
        "Early design stage model",
        "All of the above",
        null
      );
    }
    if (current == 41) {
      label.setText(
        "Question " +
        control +
        ": Which of the following threatens the quality and timeliness of the produced software?"
      );
      radioButton[0].setText("Business risks");
      radioButton[1].setText("Potential risks");
      radioButton[2].setText("Technical risks");
      radioButton[3].setText("Known risks");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following threatens the quality and timeliness of the produced software?",
        "Business risks",
        "Potential risks",
        "Technical risks",
        "Known risks",
        null
      );
    }
    if (current == 42) {
      label.setText(
        "Question " +
        control +
        ": Which of the following refers to the systematic attempt, which is implemented to ascertain the threats to any project plan?"
      );
      radioButton[0].setText("Performance risk");
      radioButton[1].setText("Risk Identification");
      radioButton[2].setText("Risk Projection");
      radioButton[3].setText("Support Risk");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following refers to the systematic attempt, which is implemented to ascertain the threats to any project plan?",
        "Performance risk",
        "Risk Identification",
        "Risk Projection",
        "Support Risk",
        null
      );
    }
    if (current == 43) {
      label.setText(
        "Question " +
        control +
        ": Which of the following standards is used by the aviation industry?"
      );
      radioButton[0].setText("CTRADO-172B");
      radioButton[1].setText("RTCADO-178B");
      radioButton[2].setText("RTRADO-178B");
      radioButton[3].setText("CTCADO-178B");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following color will generate with the intersection of three primary RGB colors?",
        "CTRADO-172B",
        "RTCADO-178B",
        "RTRADO-178B",
        "CTCADO-178B",
        null
      );
    }
    if (current == 44) {
      label.setText(
        "Question " +
        control +
        ": Third-Party Certification for software standards is based on _____."
      );
      radioButton[0].setText("Ul 1996, Second Edition");
      radioButton[1].setText("Ul 1998, Second Edition");
      radioButton[2].setText("Ul 1992, Second Edition");
      radioButton[3].setText("UT 1998, Second Edition");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Third-Party Certification for software standards is based on _____.",
        "Ul 1996, Second Edition",
        "Ul 1998, Second Edition",
        "Ul 1992, Second Edition",
        "UT 1998, Second Edition",
        null
      );
    }
    if (current == 45) {
      label.setText(
        "Question " + control + ":  Software is defined as _______"
      );
      radioButton[0].setText(
          "set of programs, documentation & configuration of data"
        );
      radioButton[1].setText("set of programs");
      radioButton[2].setText("documentation and configuration of data");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Software is defined as _______",
        "set of programs, documentation & configuration of data",
        "set of programs",
        "documentation and configuration of data",
        "None of the above",
        null
      );
    }
    if (current == 46) {
      label.setText("Question " + control + ": What is Software Engineering?");
      radioButton[0].setText("Designing a software");
      radioButton[1].setText("Testing a software");
      radioButton[2].setText(
          "Application of engineering principles to the design a software"
        );
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": What is Software Engineering?",
        "Designing a software",
        "Testing a software",
        "Application of engineering principles to the design a software",
        "None of the above",
        null
      );
    }
    if (current == 47) {
      label.setText(
        "Question " + control + ": Who is the father of Software Engineering?"
      );
      radioButton[0].setText("Margaret Hamilton");
      radioButton[1].setText("Watts S. Humphrey");
      radioButton[2].setText("Alan Turing");
      radioButton[3].setText("Boris Beizer");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":Who is the father of Software Engineering?",
        "Margaret Hamilton",
        "Watts S. Humphrey",
        "Alan Turing",
        "Boris Beizer",
        null
      );
    }
    if (current == 48) {
      label.setText(
        "Question " + control + ": What are the features of Software Code?"
      );
      radioButton[0].setText("Simplicity");
      radioButton[1].setText("Accessibility");
      radioButton[2].setText("Modularity");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": What are the features of Software Code?",
        "Simplicity",
        "Accessibility",
        "Modularity",
        "All of the above",
        null
      );
    }
    if (current == 49) {
      label.setText(
        "Question " +
        control +
        ": ______ is a software development activity that is not a part of software processes."
      );
      radioButton[0].setText("Validation");
      radioButton[1].setText("Specification");
      radioButton[2].setText("Development");
      radioButton[3].setText("Dependence");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": ______ is a software development activity that is not a part of software processes.",
        "Validation",
        "Specification",
        "Development",
        "Dependence",
        null
      );
    }
    label.setBounds(30, 40, 900, 50);
    for (int i = 0, j = 0; i <= 240; i += 80, j++) radioButton[j].setBounds(
        50,
        80 + i,
        900,
        50
      );
    btnNext.setBounds(100, 450, 150, 30);
    btnBookmark.setBounds(270, 450, 150, 30);
    btnNext.setFont(new Font("Verdana", Font.PLAIN, 15));
    btnBookmark.setFont(new Font("Verdana", Font.PLAIN, 15));
  }

  // declare right answers.
  boolean check() {
    if (current == 0) return (radioButton[3].isSelected());
    if (current == 1) return (radioButton[2].isSelected());
    if (current == 2) return (radioButton[3].isSelected());
    if (current == 3) return (radioButton[1].isSelected());
    if (current == 4) return (radioButton[3].isSelected());
    if (current == 5) return (radioButton[3].isSelected());
    if (current == 6) return (radioButton[3].isSelected());
    if (current == 7) return (radioButton[1].isSelected());
    if (current == 8) return (radioButton[3].isSelected());
    if (current == 9) return (radioButton[2].isSelected());
    if (current == 10) return (radioButton[2].isSelected());
    if (current == 11) return (radioButton[1].isSelected());
    if (current == 12) return (radioButton[3].isSelected());
    if (current == 13) return (radioButton[3].isSelected());
    if (current == 14) return (radioButton[0].isSelected());
    if (current == 15) return (radioButton[0].isSelected());
    if (current == 16) return (radioButton[2].isSelected());
    if (current == 17) return (radioButton[0].isSelected());
    if (current == 18) return (radioButton[1].isSelected());
    if (current == 19) return (radioButton[1].isSelected());
    if (current == 20) return (radioButton[3].isSelected());
    if (current == 21) return (radioButton[2].isSelected());
    if (current == 22) return (radioButton[3].isSelected());
    if (current == 23) return (radioButton[3].isSelected());
    if (current == 24) return (radioButton[2].isSelected());
    if (current == 25) return (radioButton[0].isSelected());
    if (current == 26) return (radioButton[0].isSelected());
    if (current == 27) return (radioButton[0].isSelected());
    if (current == 28) return (radioButton[3].isSelected());
    if (current == 29) return (radioButton[2].isSelected());
    if (current == 30) return (radioButton[0].isSelected());
    if (current == 31) return (radioButton[3].isSelected());
    if (current == 32) return (radioButton[2].isSelected());
    if (current == 33) return (radioButton[3].isSelected());
    if (current == 34) return (radioButton[2].isSelected());
    if (current == 35) return (radioButton[1].isSelected());
    if (current == 36) return (radioButton[0].isSelected());
    if (current == 37) return (radioButton[0].isSelected());
    if (current == 38) return (radioButton[2].isSelected());
    if (current == 39) return (radioButton[2].isSelected());
    if (current == 40) return (radioButton[2].isSelected());
    if (current == 41) return (radioButton[0].isSelected());
    if (current == 42) return (radioButton[2].isSelected());
    if (current == 43) return (radioButton[2].isSelected());
    if (current == 44) return (radioButton[1].isSelected());
    if (current == 45) return (radioButton[1].isSelected());
    if (current == 46) return (radioButton[0].isSelected());
    if (current == 47) return (radioButton[2].isSelected());
    if (current == 48) return (radioButton[1].isSelected());
    if (current == 49) return (radioButton[2].isSelected());
    return false;
  }

  public static void main(String s[]) {
    // create login ibject and run
    Login();
    prollno = JOptionPane.showInputDialog("Enter Registration No");
    pname = JOptionPane.showInputDialog("Enter Full Name");
    new OnlineTestse("Online Test App");
  }

  public int questionGenerator() {
    Random random = new Random();
    int x = random.nextInt(qBase - 1);
    if (qList.contains(x)) {
      questionGenerator();
    } else {
      qList.add(x);
      controlCurrentMapper.put(control, x);
    }
    return x;
  }

  public static void generateItextExamPdf(
    // String regdno,
    // String name,
    int correctAns
  ) {
    try {
      //location where PDF will be generated
      System.out.println("Holding in answers map" + answers);
      System.out.println(
        "Holding in control current map" + controlCurrentMapper
      );
      FileOutputStream fos = new FileOutputStream(
        "C:\\quiz_master_result\\se_result\\" + prollno + ".pdf"
      );
      System.out.println("PDF File generated");
      //creates an instance of PDF document
      Document doc = new Document();
      //doc writer for the PDF
      PdfWriter writer = PdfWriter.getInstance(doc, fos);
      //writer.setEncryption(USER_PASSWORD.getBytes(), OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_256);
      //opens the PDF
      doc.open();
      // adding title to the PDF with center align
      Paragraph title = new Paragraph(
        "                                      Software Engineering",
        FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD)
      );
      // title.setAlignment(Element.ALIGN_CENTER);
      // title.setTextAlignment(TextAlignment.CENTER);

      doc.add(title);
      //adding empty line
      doc.add(new Paragraph("\n"));
      // adding name
      doc.add(
        new Paragraph(
          "Name : " + pname,
          FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)
        )
      );
      //adding empty line
      doc.add(new Paragraph("\n"));

      //adding registration number
      doc.add(
        new Paragraph(
          "Registration Number : " + prollno,
          FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)
        )
      );
      //adding empty line
      doc.add(new Paragraph("\n"));
      //adding date
      doc.add(
        new Paragraph(
          "Date : " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()),
          FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)
        )
      );
      //adding empty line
      doc.add(new Paragraph("\n"));
      //adding time
      doc.add(
        new Paragraph(
          "Time : " + new SimpleDateFormat("HH:mm:ss").format(new Date()),
          FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD)
        )
      );
      //adding empty line
      doc.add(new Paragraph("\n"));
      //adding empty line

      //adding paragraphs to the PDF
      for (int key : questions.keySet()) {
        doc.add(new Paragraph(questions.get(key)));
      }
      doc.add(new Paragraph("\n"));
      doc.add(
        new Paragraph("The selections of the candidate is as follow :- ")
      );
      for (int akey : answers.keySet()) {
        doc.add(
          new Paragraph(
            "Selected option for Question no " +
            akey +
            " is : " +
            answers.get(akey)
          )
        );
      }
      doc.add(new Paragraph("\n"));
      doc.add(new Paragraph("Total Correct Answers : " + correctAns));
      doc.add(new Paragraph("\n\n"));
      // doc.add(new Paragraph("xxxx End of Autogenerated PDF xxxx "));
      //closes the document
      doc.close();
      //closes the output stream
      fos.close();
    } catch (Exception e) { //catch the exception if any
      //prints the occurred exception
      e.printStackTrace();
    }
  }

  public static void setPrintFormat(
    int actualQnun,
    int currentVal,
    String qs,
    String ops1,
    String ops2,
    String ops3,
    String ops4,
    String ops5
  ) {
    String print =
      qs +
      "\n" +
      "o " +
      ops1 +
      "\n" +
      "o " +
      ops2 +
      "\n" +
      "o " +
      ops3 +
      "\n" +
      "o " +
      ops4 +
      "\n\n";
    questions.put(actualQnun, print);
  }
}
