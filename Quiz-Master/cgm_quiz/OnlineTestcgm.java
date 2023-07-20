//Subject: Computer Graphics & Multimedia
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

class OnlineTestcgm extends JFrame implements ActionListener {
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
  OnlineTestcgm(String s) {
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
      label.setText("Question " + control + ": GUI stands for -");
      radioButton[0].setText("Graphics uniform interaction");
      radioButton[1].setText("Graphical user interaction");
      radioButton[2].setText("Graphical user interface");
      radioButton[3].setText("None of the above");

      setPrintFormat(
        control,
        current,
        "Question " + control + ": GUI stands for -",
        "Graphics uniform interaction",
        "Graphical user interaction",
        "Graphical user interface",
        "None of the above",
        null
      );
    }
    if (current == 1) {
      label.setText("Question " + control + ": Graphics can be -");
      radioButton[0].setText("Simulation");
      radioButton[1].setText("Drawing");
      radioButton[2].setText("Movies, photographs");
      radioButton[3].setText("All of the above");

      setPrintFormat(
        control,
        current,
        "Question " + control + ": Graphics can be -",
        "Simulation",
        "Drawing",
        "Movies, photographs",
        "All of the above",
        null
      );
    }
    if (current == 2) {
      label.setText("Question " + control + ": CAD stands for -");
      radioButton[0].setText("Computer art design");
      radioButton[1].setText("Computer-aided design");
      radioButton[2].setText("Car art design");
      radioButton[3].setText("None of the above");

      setPrintFormat(
        control,
        current,
        "Question " + control + ": CAD stands for -",
        "Computer art design",
        "Computer-aided design",
        "Car art design",
        "None of the above",
        null
      );
    }
    if (current == 3) {
      label.setText(
        "Question " +
        control +
        ":  The components of Interactive computer graphics are -"
      );
      radioButton[0].setText("A monitor");
      radioButton[1].setText("Display controller");
      radioButton[2].setText("Frame buffer");
      radioButton[3].setText("All of the above");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  WThe components of Interactive computer graphics are -",
        "A monitor",
        "Display controller",
        "Frame buffer",
        "All of the above",
        null
      );
    }
    if (current == 4) {
      label.setText(
        "Question " +
        control +
        ":  A user can make any change in the image using -"
      );
      radioButton[0].setText("Interactive computer graphics");
      radioButton[1].setText("Non-Interactive computer graphics");
      radioButton[2].setText("Both (a) & (b)");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In the Cellular Network, on which of the following, the cell's shape depends?",
        "Interactive computer graphics",
        "Non-Interactive computer graphics",
        "Both (a) & (b)",
        "None of the above",
        null
      );
    }
    if (current == 5) {
      label.setText("Question " + control + ": What is a pixel mask?");
      radioButton[0].setText("a string containing only 0's");
      radioButton[1].setText("a string containing only 1's");
      radioButton[2].setText("a string containing two 0's");
      radioButton[3].setText("a string containing both 1's and 0's");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":  What is a pixel mask?",
        "a string containing only 0's",
        "a string containing only 1's",
        "a string containing two 0's",
        "a string containing both 1's and 0's",
        null
      );
    }
    if (current == 6) {
      label.setText(
        "Question " +
        control +
        ":  The higher number of pixels gives us a ____ image?"
      );
      radioButton[0].setText("Better");
      radioButton[1].setText("Worst");
      radioButton[2].setText("Smaller");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The higher number of pixels gives us a ____ image?",
        "Better",
        "Worst",
        "Smaller",
        "None of the above",
        null
      );
    }
    if (current == 7) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following is the primarily used output device?"
      );
      radioButton[0].setText("Video monitor");
      radioButton[1].setText("Scanner");
      radioButton[2].setText("Speaker");
      radioButton[3].setText("Printer");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following is the primarily used output device?",
        "Video monitor",
        "Scanner",
        "Speaker",
        "Printer",
        null
      );
    }
    if (current == 8) {
      label.setText(
        "Question " +
        control +
        ":  Which one of the following terms is used for the area of the computer captured by an application?"
      );
      radioButton[0].setText("Display");
      radioButton[1].setText("Window");
      radioButton[2].setText("Viewport");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following terms is used for the area of the computer captured by an application?",
        "Display",
        "Window",
        "Viewport",
        "None of the above",
        null
      );
    }
    if (current == 9) {
      label.setText(
        "Question " + control + ": Aspect Ratio can be defined as -"
      );
      radioButton[0].setText(
          "The ratio of the vertical points to horizontal points"
        );
      radioButton[1].setText("of pixels");
      radioButton[2].setText("Both (a) & (b)");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":  Aspect Ratio can be defined as -",
        "The ratio of the vertical points to horizontal points",
        "of pixels",
        "Both (a) & (b)",
        "None of the above",
        null
      );
    }
    if (current == 10) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is not the pattern of line?"
      );
      radioButton[0].setText("Dotted line");
      radioButton[1].setText("Dashed line");
      radioButton[2].setText("Dark line");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is not the pattern of line?",
        "Dotted line",
        "Dashed line",
        "Dark line",
        "All of the above",
        null
      );
    }
    if (current == 11) {
      label.setText("Question " + control + ": DDA stands for -");
      radioButton[0].setText("Direct differential analyzer");
      radioButton[1].setText("Data differential analyzer");
      radioButton[2].setText("Direct difference analyzer");
      radioButton[3].setText("Digital differential analyzer");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": DDA stands for -",
        "Direct differential analyzer",
        "Data differential analyzer",
        "Direct difference analyzer",
        "Digital differential analyzer",
        null
      );
    }
    if (current == 12) {
      label.setText(
        "Question " +
        control +
        ": From the given list of options, which one is the accurate and efficient line-generating algorithm?"
      );
      radioButton[0].setText("Midpoint algorithm");
      radioButton[1].setText("DDA algorithm");
      radioButton[2].setText("Bresenham's Line algorithm");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": From the given list of options, which one is the accurate and efficient line-generating algorithm?",
        "Midpoint algorithm",
        "DDA algorithm",
        "Bresenham's Line algorithm",
        "None of the above",
        null
      );
    }
    if (current == 13) {
      label.setText(
        "Question " +
        control +
        ": The process of positioning an object along a straight line path from one coordinate point to another is called "
      );
      radioButton[0].setText("Translation");
      radioButton[1].setText("Reflection");
      radioButton[2].setText("Shearing");
      radioButton[3].setText("Transformation");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The process of positioning an object along a straight line path from one coordinate point to another is called",
        "Translation",
        "Reflection",
        "Shearing",
        "Transformation",
        null
      );
    }
    if (current == 14) {
      label.setText(
        "Question " +
        control +
        ": Which of the following equation is used in 2D translation to move a point(x,y) to the new point (x',y')?"
      );
      radioButton[0].setText("x' = x + ty and y' = y + tx");
      radioButton[1].setText("x' = x - tx and y' = y - ty");
      radioButton[2].setText("x' = x + tx and y' = y + ty");
      radioButton[3].setText("x' = x + tx and y' = y - ty");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following equation is used in 2D translation to move a point(x,y) to the new point (x',y')?",
        "x' = x + ty and y' = y + tx",
        "x' = x - tx and y' = y - ty",
        "x' = x + tx and y' = y + ty",
        "x' = x + tx and y' = y - ty",
        null
      );
    }
    if (current == 15) {
      label.setText(
        "Question " +
        control +
        ": The process of repositioning an object along a circular path is called -"
      );
      radioButton[0].setText("Translation");
      radioButton[1].setText("Rotation");
      radioButton[2].setText("Scaling");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The process of repositioning an object along a circular path is called -",
        "Translation",
        "Rotation",
        "Scaling",
        "None of the above",
        null
      );
    }
    if (current == 16) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following is must be specified to generate a rotation?"
      );
      radioButton[0].setText("Rotational distance");
      radioButton[1].setText("Rotation angle");
      radioButton[2].setText("Co-ordinates");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following is must be specified to generate a rotation?",
        "Rotational distance",
        "Rotation angle",
        "Co-ordinates",
        "None of the above",
        null
      );
    }
    if (current == 17) {
      label.setText(
        "Question " + control + ": A positive value of the rotation angle -"
      );
      radioButton[0].setText("rotates an object in the clockwise direction");
      radioButton[1].setText(
          "rotates an object in the counter-clockwise direction"
        );
      radioButton[2].setText("Both of the above");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": A positive value of the rotation angle -",
        "rotates an object in the clockwise direction",
        "rotates an object in the counter-clockwise direction",
        "Both of the above",
        "None of the above",
        null
      );
    }
    if (current == 18) {
      label.setText(
        "Question " +
        control +
        ": Which of the following transformation is used for altering the object's size?"
      );
      radioButton[0].setText("Translation");
      radioButton[1].setText("Scaling");
      radioButton[2].setText("Rotation");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following transformation is used for altering the object's size?",
        "Translation",
        "Scaling",
        "Rotation",
        "None of the above",
        null
      );
    }
    if (current == 19) {
      label.setText(
        "Question " +
        control +
        ": What happens if the values of scaling factors sx and sy less than 1 (i.e., sx<1 and sy<1)?"
      );
      radioButton[0].setText("No change in the object's size");
      radioButton[1].setText("Reduce the object's size");
      radioButton[2].setText("Increase the object's size");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": What happens if the values of scaling factors sx and sy less than 1 (i.e., sx<1 and sy<1)?",
        "No change in the object's size",
        "Reduce the object's size",
        "Increase the object's size",
        "None of the above",
        null
      );
    }
    if (current == 20) {
      label.setText(
        "Question " +
        control +
        ": In which of the following case, the uniform scaling will be produced?"
      );
      radioButton[0].setText(
          "Values of scaling factors sx and sy are unequal."
        );
      radioButton[1].setText("Values of scaling factors sx and sy are equal.");
      radioButton[2].setText("Both of the above");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In which of the following case, the uniform scaling will be produced?",
        "Values of scaling factors sx and sy are unequal.",
        "Values of scaling factors sx and sy are equal.",
        "Both of the above",
        "None of the above",
        null
      );
    }
    if (current == 21) {
      label.setText(
        "Question " +
        control +
        ": The Cohen-Sutherland algorithm divides the two-dimensional space in how many regions?"
      );
      radioButton[0].setText("4");
      radioButton[1].setText("8");
      radioButton[2].setText("9");
      radioButton[3].setText("23");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The Cohen-Sutherland algorithm divides the two-dimensional space in how many regions?",
        "4",
        "8",
        "9",
        "23",
        null
      );
    }
    if (current == 22) {
      label.setText(
        "Question " +
        control +
        ": The 4-bit code of the bottom-region among the nine regions divided using the Cohen-Sutherland algorithm?"
      );
      radioButton[0].setText("0000");
      radioButton[1].setText("0010");
      radioButton[2].setText("0110");
      radioButton[3].setText("0101");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  The 4-bit code of the bottom-region among the nine regions divided using the Cohen-Sutherland algorithm?",
        "0000",
        "0010",
        "0110",
        "0101",
        null
      );
    }
    if (current == 23) {
      label.setText(
        "Question " +
        control +
        ": According to the Cohen-Sutherland algorithm, where the line lies, if the 4-bit code of both ends is 0000, and also the logical OR gives 0000??"
      );
      radioButton[0].setText("Half outside half inside");
      radioButton[1].setText("Completely inside");
      radioButton[2].setText("Completely outside");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": According to the Cohen-Sutherland algorithm, where the line lies, if the 4-bit code of both ends is 0000, and also the logical OR gives 0000?",
        "Half outside half inside",
        "Completely inside",
        "Completely outside",
        "None of the above",
        null
      );
    }
    if (current == 24) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following is the most commonly used and basic input device?"
      );
      radioButton[0].setText("Mouse");
      radioButton[1].setText("Printer");
      radioButton[2].setText("Scanner");
      radioButton[3].setText("Keyboard");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following is the most commonly used and basic input device?",
        "Mouse",
        "Printer",
        "Scanner",
        "Keyboard",
        null
      );
    }
    if (current == 25) {
      label.setText(
        "Question " +
        control +
        ": Which of the following device is used for the 3D positioning of an object?"
      );
      radioButton[0].setText("Trackball");
      radioButton[1].setText("Mouse");
      radioButton[2].setText("Spaceball");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": HWhich of the following device is used for the 3D positioning of an object?",
        "Trackball",
        "Mouse",
        "Spaceball",
        "All of the above",
        null
      );
    }
    if (current == 26) {
      label.setText("Question " + control + ": Which is not the input device?");
      radioButton[0].setText("Impact printers");
      radioButton[1].setText("Trackball");
      radioButton[2].setText("Mouse");
      radioButton[3].setText("Keyboard");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following is considered as the heart of the Global Systems for Mobiles (or GSM)?",
        "Impact printers",
        "Trackball",
        "Mouse",
        "Keyboard",
        null
      );
    }
    if (current == 27) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is an example of the impact device?"
      );
      radioButton[0].setText("Laser Printer");
      radioButton[1].setText("Inkjet Printer");
      radioButton[2].setText("Line Printer");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is an example of the impact device?",
        "Laser Printer",
        "Inkjet Printer",
        "Line Printer",
        "None of the above",
        null
      );
    }
    if (current == 28) {
      label.setText(
        "Question " +
        control +
        ": Which of the following allows us to select the screen positions with the touch of a finger?"
      );
      radioButton[0].setText("Mouse");
      radioButton[1].setText("Trackball");
      radioButton[2].setText("Touch Panel");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following allows us to select the screen positions with the touch of a finger?",
        "Mouse",
        "Trackball",
        "Touch Panel",
        "None of the above",
        null
      );
    }
    if (current == 29) {
      label.setText(
        "Question " +
        control +
        ":  Which is a common device for painting or selecting the object's co-ordinate positions?"
      );
      radioButton[0].setText("Digitizer");
      radioButton[1].setText("Touch panel");
      radioButton[2].setText("Image scanner");
      radioButton[3].setText("Keyboard");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which is a common device for painting or selecting the object's co-ordinate positions?",
        "Digitizer",
        "Touch panel",
        "Image scanner",
        "Keyboard",
        null
      );
    }
    if (current == 30) {
      label.setText("Question " + control + ":  Grayscale is used for -");
      radioButton[0].setText("Random scan display");
      radioButton[1].setText("Monitors with color capability");
      radioButton[2].setText("Monitors with no color capability");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":  Grayscale is used for -",
        "Random scan display",
        "Monitors with color capability",
        "Monitors with no color capability",
        "All of the above",
        null
      );
    }
    if (current == 31) {
      label.setText(
        "Question " +
        control +
        ":  Clipping in computer graphics is primarily used for -"
      );
      radioButton[0].setText("zooming");
      radioButton[1].setText("copying");
      radioButton[2].setText("removing objects and lines");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Clipping in computer graphics is primarily used for -",
        "zooming",
        "copying",
        "removing objects and lines",
        "All of the above",
        null
      );
    }
    if (current == 32) {
      label.setText(
        "Question " + control + ": Random scan systems are used for -"
      );
      radioButton[0].setText("Color drawing application");
      radioButton[1].setText("Pixel drawing application");
      radioButton[2].setText("Line drawing application");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Random scan systems are used for -",
        "Color drawing application",
        "Pixel drawing application",
        "Line drawing application",
        "None of the above",
        null
      );
    }
    if (current == 33) {
      label.setText(
        "Question " +
        control +
        ": How many phosphor color dots at each pixel position in a shadow mask CRT?"
      );
      radioButton[0].setText("1");
      radioButton[1].setText("7");
      radioButton[2].setText("2");
      radioButton[3].setText("3");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": How many phosphor color dots at each pixel position in a shadow mask CRT?",
        "1",
        "7",
        "2",
        "3",
        null
      );
    }
    if (current == 34) {
      label.setText("Question " + control + ": Shadow mask method is used in ");
      radioButton[0].setText("Raster scan system");
      radioButton[1].setText("Random scan system");
      radioButton[2].setText("Both (a) & (b)");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Shadow mask method is used in ",
        "Raster scan system",
        "Random scan system",
        "Both (a) & (b)",
        "None of the above",
        null
      );
    }
    if (current == 35) {
      label.setText(
        "Question " +
        control +
        ": In which of the following CRT methods, there is an occurrence of convergence problem?"
      );
      radioButton[0].setText("Shadow mask method");
      radioButton[1].setText("Beam penetration");
      radioButton[2].setText("Both of the above");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":In which of the following CRT methods, there is an occurrence of convergence problem?",
        "Shadow mask method",
        "Beam penetration",
        "Both of the above",
        "None of the above",
        null
      );
    }
    if (current == 36) {
      label.setText(
        "Question " +
        control +
        ": Which of the following uses the Beam penetration method?"
      );
      radioButton[0].setText("Raster scan system");
      radioButton[1].setText("Random scan system");
      radioButton[2].setText("Both (a) & (b)");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following uses the Beam penetration method?",
        "Raster scan system",
        "Random scan system",
        "Both (a) & (b)",
        "None of the above",
        null
      );
    }
    if (current == 37) {
      label.setText("Question " + control + ": Plasma panel is a type of -");
      radioButton[0].setText("Emissive display");
      radioButton[1].setText("Non-Emissive display");
      radioButton[2].setText("Printer");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Plasma panel is a type of -",
        "Emissive display",
        "Non-Emissive display",
        "Printer",
        "None of the above",
        null
      );
    }
    if (current == 38) {
      label.setText(
        "Question " +
        control +
        ": Which of the following algorithm is used to fill the interior of a polygon?"
      );
      radioButton[0].setText("Boundary fill algorithm");
      radioButton[1].setText("Scan line polygon fill algorithm");
      radioButton[2].setText("Flood fill algorithm");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following algorithm is used to fill the interior of a polygon?",
        "Boundary fill algorithm",
        "Scan line polygon fill algorithm",
        "Flood fill algorithm",
        "All of the above",
        null
      );
    }
    if (current == 39) {
      label.setText(
        "Question " +
        control +
        ": Which of the algorithm is used to color a pixel if it is not colored and leaves it if it is already filled?"
      );
      radioButton[0].setText("Boundary fill algorithm");
      radioButton[1].setText("Scan line polygon fill algorithm");
      radioButton[2].setText("Flood fill algorithm");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the algorithm is used to color a pixel if it is not colored and leaves it if it is already filled?",
        "Boundary fill algorithm",
        "Scan line polygon fill algorithm",
        "Flood fill algorithm",
        "All of the above",
        null
      );
    }
    if (current == 40) {
      label.setText("Question " + control + ":  A spline can be defined as -.");
      radioButton[0].setText("Curved strip");
      radioButton[1].setText("A smooth curve is drawn using a pencil");
      radioButton[2].setText(
          "A flexible strip used to generate a smooth curve through a designated set of points"
        );
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": A spline can be defined as -",
        "Curved strip",
        "A smooth curve is drawn using a pencil",
        "A flexible strip used to generate a smooth curve through a designated set of points",
        "None of the above",
        null
      );
    }
    if (current == 41) {
      label.setText(
        "Question " +
        control +
        ": Which of the following are the 2d color models?"
      );
      radioButton[0].setText("RGB and CMK");
      radioButton[1].setText("RGB and CMG");
      radioButton[2].setText("RGB and CMYK");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following are the 2d color models?",
        "RGB and CMK",
        "RGB and CMG",
        "RGB and CMYK",
        "All of the above",
        null
      );
    }
    if (current == 42) {
      label.setText("Question " + control + ": RGB color model is used for -");
      radioButton[0].setText("Painting");
      radioButton[1].setText("sketching");
      radioButton[2].setText("printing");
      radioButton[3].setText("computer display");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": RGB color model is used for -",
        "Painting",
        "sketching",
        "printing",
        "computer display",
        null
      );
    }
    if (current == 43) {
      label.setText(
        "Question " +
        control +
        ": Which of the following color will generate with the intersection of three primary RGB colors?"
      );
      radioButton[0].setText("Green");
      radioButton[1].setText("Dark Red");
      radioButton[2].setText("Dark Blue");
      radioButton[3].setText("White");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following color will generate with the intersection of three primary RGB colors?",
        "Green",
        "Dark Red",
        "Dark Blue",
        "White",
        null
      );
    }
    if (current == 44) {
      label.setText(
        "Question " +
        control +
        ": The intersection of primary colors in the CMYK color model will generate the -"
      );
      radioButton[0].setText("Green");
      radioButton[1].setText("White Color");
      radioButton[2].setText("Black Color");
      radioButton[3].setText("Dark Red");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The intersection of primary colors in the CMYK color model will generate the -",
        "Green",
        "White Color",
        "Black Color",
        "Dark Red",
        null
      );
    }
    if (current == 45) {
      label.setText(
        "Question " +
        control +
        ":  Select the set of colors produced in the beam-penetration method of the color CRT -"
      );
      radioButton[0].setText("Red, Green, Blue");
      radioButton[1].setText("Cyan, Magenta, Blue");
      radioButton[2].setText("Red, Green, Orange, Yellow");
      radioButton[3].setText(
          "Green, Black, OrangeThe phase of determining the appropriate pixels for representing images or graphics object is called as -"
        );
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Select the set of colors produced in the beam-penetration method of the color CRT -",
        "Red, Green, Blue",
        "Cyan, Magenta, Blue",
        "Red, Green, Orange, Yellow",
        "Green, Black, OrangeThe phase of determining the appropriate pixels for representing images or graphics object is called as -",
        null
      );
    }
    if (current == 46) {
      label.setText(
        "Question " +
        control +
        ": The phase of determining the appropriate pixels for representing images or graphics object is called as -"
      );
      radioButton[0].setText("Translation");
      radioButton[1].setText("Transformation");
      radioButton[2].setText("Rasterization");
      radioButton[3].setText("Scaling");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The phase of determining the appropriate pixels for representing images or graphics object is called as -",
        "Translation",
        "Transformation",
        "Rasterization",
        "Scaling",
        null
      );
    }
    if (current == 47) {
      label.setText(
        "Question " +
        control +
        ": The process of displaying 3D into a 2D display unit is called as -"
      );
      radioButton[0].setText("Resolution");
      radioButton[1].setText("Projection");
      radioButton[2].setText("Rasterization");
      radioButton[3].setText("Transformation");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The process of displaying 3D into a 2D display unit is called as -",
        "Resolution",
        "Projection",
        "Rasterization",
        "Transformation",
        null
      );
    }
    if (current == 48) {
      label.setText(
        "Question " +
        control +
        ": The video device with reduced volume, power consumption and weight is -"
      );
      radioButton[0].setText("CRT");
      radioButton[1].setText("Flat-panel display");
      radioButton[2].setText("Portable display");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The video device with reduced volume, power consumption and weight is -",
        "CRT",
        "Flat-panel display",
        "Portable display",
        "None of the above",
        null
      );
    }
    if (current == 49) {
      label.setText(
        "Question " + control + ": Plasma panel is also called as -"
      );
      radioButton[0].setText("Non-emissive display");
      radioButton[1].setText("Liquid crystal display");
      radioButton[2].setText("Gas discharge display");
      radioButton[3].setText("None of these");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": Plasma panel is also called as -",
        "Non-emissive display",
        "Liquid crystal display",
        "Gas discharge display",
        "None of these",
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
    if (current == 0) return (radioButton[2].isSelected());
    if (current == 1) return (radioButton[3].isSelected());
    if (current == 2) return (radioButton[2].isSelected());
    if (current == 3) return (radioButton[3].isSelected());
    if (current == 4) return (radioButton[0].isSelected());
    if (current == 5) return (radioButton[3].isSelected());
    if (current == 6) return (radioButton[0].isSelected());
    if (current == 7) return (radioButton[0].isSelected());
    if (current == 8) return (radioButton[2].isSelected());
    if (current == 9) return (radioButton[0].isSelected());
    if (current == 10) return (radioButton[2].isSelected());
    if (current == 11) return (radioButton[3].isSelected());
    if (current == 12) return (radioButton[2].isSelected());
    if (current == 13) return (radioButton[0].isSelected());
    if (current == 14) return (radioButton[2].isSelected());
    if (current == 15) return (radioButton[1].isSelected());
    if (current == 16) return (radioButton[1].isSelected());
    if (current == 17) return (radioButton[1].isSelected());
    if (current == 18) return (radioButton[1].isSelected());
    if (current == 19) return (radioButton[1].isSelected());
    if (current == 20) return (radioButton[1].isSelected());
    if (current == 21) return (radioButton[2].isSelected());
    if (current == 22) return (radioButton[2].isSelected());
    if (current == 23) return (radioButton[1].isSelected());
    if (current == 24) return (radioButton[3].isSelected());
    if (current == 25) return (radioButton[2].isSelected());
    if (current == 26) return (radioButton[0].isSelected());
    if (current == 27) return (radioButton[2].isSelected());
    if (current == 28) return (radioButton[2].isSelected());
    if (current == 29) return (radioButton[0].isSelected());
    if (current == 30) return (radioButton[2].isSelected());
    if (current == 31) return (radioButton[2].isSelected());
    if (current == 32) return (radioButton[2].isSelected());
    if (current == 33) return (radioButton[3].isSelected());
    if (current == 34) return (radioButton[0].isSelected());
    if (current == 35) return (radioButton[0].isSelected());
    if (current == 36) return (radioButton[1].isSelected());
    if (current == 37) return (radioButton[0].isSelected());
    if (current == 38) return (radioButton[2].isSelected());
    if (current == 39) return (radioButton[0].isSelected());
    if (current == 40) return (radioButton[2].isSelected());
    if (current == 41) return (radioButton[2].isSelected());
    if (current == 42) return (radioButton[3].isSelected());
    if (current == 43) return (radioButton[3].isSelected());
    if (current == 44) return (radioButton[2].isSelected());
    if (current == 45) return (radioButton[2].isSelected());
    if (current == 46) return (radioButton[2].isSelected());
    if (current == 47) return (radioButton[1].isSelected());
    if (current == 48) return (radioButton[1].isSelected());
    if (current == 49) return (radioButton[2].isSelected());
    return false;
  }

  public static void main(String s[]) {
    // create login ibject and run
    Login();
    prollno = JOptionPane.showInputDialog("Enter Registration No");
    pname = JOptionPane.showInputDialog("Enter Full Name");
    new OnlineTestcgm("Online Test App");
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
        "C:\\quiz_master_result\\cgm_result\\" + prollno + ".pdf"
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
        "                           Computer Graphics & Multimeida",
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
