//Subject: Mobile Computing
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

class OnlineTestmc extends JFrame implements ActionListener {
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
  OnlineTestmc(String s) {
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

  // SET Questions with options
  void set() {
    radioButton[4].setSelected(true);
    //label.setText("Question "+control+":");
    if (current == 0) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following usually stores all user-related data that is also relevant to GSM mobile systems?"
      );
      radioButton[0].setText("VLR");
      radioButton[1].setText("HMR");
      radioButton[2].setText("CMR");
      radioButton[3].setText("SIM");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following usually stores all user-related data that is also relevant to GSM mobile systems?",
        "VLR",
        "HMR",
        "CMR",
        "SIM",
        null
      );
    }
    if (current == 1) {
      label.setText(
        "Question " +
        control +
        ":  In which one of the following codes with specific characteristics can be applied to the transmission?"
      );
      radioButton[0].setText("CDMA");
      radioButton[1].setText("GPRS");
      radioButton[2].setText("GSM");
      radioButton[3].setText("All of the above");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  In which one of the following codes with specific characteristics can be applied to the transmission?",
        "CDMA",
        "GPRS",
        "GSM",
        "All of the above",
        null
      );
    }
    if (current == 2) {
      label.setText(
        "Question " +
        control +
        ": Which of the following offers packet mode data transfer service over the cellular network?"
      );
      radioButton[0].setText("TCP");
      radioButton[1].setText("GPRS");
      radioButton[2].setText("GSM");
      radioButton[3].setText("None of the above");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following offers packet mode data transfer service over the cellular network?",
        "TCP",
        "GPRS",
        "GSM",
        "None of the above",
        null
      );
    }
    if (current == 3) {
      label.setText(
        "Question " +
        control +
        ":  Which one of the following enables us to use the entire bandwidth simultaneously?"
      );
      radioButton[0].setText("TDMA");
      radioButton[1].setText("CDMA");
      radioButton[2].setText("FDMA");
      radioButton[3].setText("All of the above");

      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which one of the following enables us to use the entire bandwidth simultaneously?",
        "TDMA",
        "CDMA",
        "FDMA",
        "All of the above",
        null
      );
    }
    if (current == 4) {
      label.setText(
        "Question " +
        control +
        ":  In the Cellular Network, on which of the following, the cell's shape depends?"
      );
      radioButton[0].setText("Political conditions");
      radioButton[1].setText("Social Conditions");
      radioButton[2].setText("Environment Condition");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In the Cellular Network, on which of the following, the cell's shape depends?",
        "Political conditions",
        "Social Conditions",
        "Environment Condition",
        "None of the above",
        null
      );
    }
    if (current == 5) {
      label.setText(
        "Question " +
        control +
        ": In a Cellular network, which of the following is used to use the same frequency for others?"
      );
      radioButton[0].setText("Frequency hopping");
      radioButton[1].setText("Frequency reuse");
      radioButton[2].setText("Frequency planning");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  In a Cellular network, which of the following is used to use the same frequency for others?",
        "Frequency hopping",
        "Frequency reuse",
        "Frequency planning",
        "None of the above",
        null
      );
    }
    if (current == 6) {
      label.setText(
        "Question " +
        control +
        ":  Which of the following uses wireless as the mode of communication for transferring or exchanging data between various mobiles over a short-range?"
      );
      radioButton[0].setText("Ad hoc computing");
      radioButton[1].setText("Mobile computing");
      radioButton[2].setText("Bluetooth technology");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following uses wireless as the mode of communication for transferring or exchanging data between various mobiles over a short-range?",
        "Ad hoc computing",
        "Mobile computing",
        "Bluetooth technology",
        "None of the above",
        null
      );
    }
    if (current == 7) {
      label.setText(
        "Question " +
        control +
        ": The main aim of the file system is to support________"
      );
      radioButton[0].setText("Transparent access to data");
      radioButton[1].setText("Efficient access to data");
      radioButton[2].setText("Consistent access to data");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The main aim of the file system is to support________",
        "Transparent access to data",
        "Efficient access to data",
        "Consistent access to data",
        "All of the above",
        null
      );
    }
    if (current == 8) {
      label.setText(
        "Question " +
        control +
        ":  In a distributed system, a client sends the request, and the server provides_____"
      );
      radioButton[0].setText("Data");
      radioButton[1].setText("Service");
      radioButton[2].setText("Information");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In a distributed system, a client sends the request, and the server provides_____",
        "Data",
        "Service",
        "Information",
        "All of the above",
        null
      );
    }
    if (current == 9) {
      label.setText(
        "Question " +
        control +
        ": One of the essential challenges of distributed systems apply to DFS is/are"
      );
      radioButton[0].setText("Migration of data");
      radioButton[1].setText("Concurrent access to data");
      radioButton[2].setText("Replication of data");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  One of the essential challenges of distributed systems apply to DFS is/are",
        "Migration of data",
        "Concurrent access to data",
        "Replication of data",
        "All of the above",
        null
      );
    }
    if (current == 10) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following can be considered as the features of CODA?"
      );
      radioButton[0].setText("A disconnected operation for mobile computing");
      radioButton[1].setText("It is freely available under a liberal license");
      radioButton[2].setText(
          "It provides high performance through client-side persistent caching"
        );
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following can be considered as the features of CODA?",
        "A disconnected operation for mobile computing",
        "It is freely available under a liberal license",
        "It provides high performance through client-side persistent caching",
        "All of the above",
        null
      );
    }
    if (current == 11) {
      label.setText(
        "Question " +
        control +
        ": Which one of the file systems applies gossip protocols?"
      );
      radioButton[0].setText("CODA");
      radioButton[1].setText("Ficus");
      radioButton[2].setText("Rover");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the file systems applies gossip protocols?",
        "CODA",
        "Ficus",
        "Rover",
        "None of the above",
        null
      );
    }
    if (current == 12) {
      label.setText(
        "Question " +
        control +
        ": Which of the following modes are supported by the Mio-NFS?"
      );
      radioButton[0].setText("Connected");
      radioButton[1].setText("Loosely connected");
      radioButton[2].setText("Disconnected");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following modes are supported by the Mio-NFS?",
        "Connected",
        "Loosely connected",
        "Disconnected",
        "All of the above",
        null
      );
    }
    if (current == 13) {
      label.setText(
        "Question " +
        control +
        ": Which of the following can be considered as the advantage of using frequency reuse?"
      );
      radioButton[0].setText(
          "The same spectrum can be allocated to the other networks"
        );
      radioButton[1].setText("Only a limited spectrum is required");
      radioButton[2].setText("Increase capacity");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following can be considered as the advantage of using frequency reuse?",
        "The same spectrum can be allocated to the other networks",
        "Only a limited spectrum is required",
        "Increase capacity",
        "All of the above",
        null
      );
    }
    if (current == 14) {
      label.setText(
        "Question " +
        control +
        ": Which of the following can be considered as the primary function of snooping TCP?"
      );
      radioButton[0].setText(
          "To buffer data close to the mobile host to perform fast local retransmission in case of packet loss."
        );
      radioButton[1].setText("Congestion control");
      radioButton[2].setText("Flow control");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following can be considered as the primary function of snooping TCP?",
        "To buffer data close to the mobile host to perform fast local retransmission in case of packet loss.",
        "Congestion control",
        "Flow control",
        "None of the above",
        null
      );
    }
    if (current == 15) {
      label.setText(
        "Question " +
        control +
        ": In which one of the following, the slow and fast hopping is used?"
      );
      radioButton[0].setText("GSM");
      radioButton[1].setText("GPRS");
      radioButton[2].setText("FHSS");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":In which one of the following, the slow and fast hopping is used?",
        "GSM",
        "GPRS",
        "FHSS",
        "None of the above",
        null
      );
    }
    if (current == 16) {
      label.setText(
        "Question " +
        control +
        ": Mobile Computing allows transmission of data from one wireless-enabled device to another_____"
      );
      radioButton[0].setText("Any device");
      radioButton[1].setText("Wired device");
      radioButton[2].setText("Wireless-enabled device");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Mobile Computing allows transmission of data from one wireless-enabled device to another_____",
        "Any device",
        "Wired device",
        "Wireless-enabled device",
        "None of the above",
        null
      );
    }
    if (current == 17) {
      label.setText(
        "Question " +
        control +
        ": Which of the following can be considered as the drawbacks of the Mobile and Wireless Devices?"
      );
      radioButton[0].setText("Smaller keypads");
      radioButton[1].setText("Consumes power rapidly");
      radioButton[2].setText("Requires a big power source");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following can be considered as the drawbacks of the Mobile and Wireless Devices?",
        "Smaller keypads",
        "Consumes power rapidly",
        "Requires a big power source",
        "All of the above",
        null
      );
    }
    if (current == 18) {
      label.setText(
        "Question " +
        control +
        ": In general, a mobile computing environment can also be considered as the type of __________ environment."
      );
      radioButton[0].setText("Grid computing");
      radioButton[1].setText("Mobile computing");
      radioButton[2].setText("Distributed computing");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":In general, a mobile computing environment can also be considered as the type of __________ environment.",
        "Grid computing",
        "Mobile computing",
        "Distributed computing",
        "None of the above",
        null
      );
    }
    if (current == 19) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is a fundamental principle of wireless communication?"
      );
      radioButton[0].setText("Electromagnetic waves");
      radioButton[1].setText("Microwaves");
      radioButton[2].setText("Both A and B");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is a fundamental principle of wireless communication?",
        "Electromagnetic waves",
        "Microwaves",
        "Both A and B",
        "None of the above",
        null
      );
    }
    if (current == 20) {
      label.setText(
        "Question " +
        control +
        ": Which of the following statements is correct about the FHSS?"
      );
      radioButton[0].setText("FHSS is a type of narrowband signal");
      radioButton[1].setText("It uses the 78 frequency in the 2.4 GHz");
      radioButton[2].setText(
          "It is referred as Frequency Hopping Spread Spectrum"
        );
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following statements is correct about the FHSS?",
        "FHSS is a type of narrowband signal",
        "It uses the 78 frequency in the 2.4 GHz",
        "It is referred as Frequency Hopping Spread Spectrum",
        "All of the above",
        null
      );
    }
    if (current == 21) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is required to transmit the digital information using a certain frequency by translating it into an analog signal?"
      );
      radioButton[0].setText("Demodulation");
      radioButton[1].setText("Modulation");
      radioButton[2].setText("QPSK");
      radioButton[3].setText("BSPK");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is required to transmit the digital information using a certain frequency by translating it into an analog signal?",
        "Demodulation",
        "Modulation",
        "QPSK",
        "BSPK",
        null
      );
    }
    if (current == 22) {
      label.setText(
        "Question " +
        control +
        ": When was the 2G communication introduced in the market?"
      );
      radioButton[0].setText("1982");
      radioButton[1].setText("1984");
      radioButton[2].setText("1986");
      radioButton[3].setText("1988");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  When was the 2G communication introduced in the market?",
        "1982",
        "1984",
        "1986",
        "1988",
        null
      );
    }
    if (current == 23) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is supported data rates of 4G Networks?"
      );
      radioButton[0].setText("1024kbps");
      radioButton[1].setText("100mbs");
      radioButton[2].setText("200mbps");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following is supported data rates of 4G Networks?",
        "1024kbps",
        "100mbs",
        "200mbps",
        "None of the above",
        null
      );
    }
    if (current == 24) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following is considered as the GSM supplementary service?"
      );
      radioButton[0].setText("Emergency number");
      radioButton[1].setText("SMS");
      radioButton[2].setText("Call forwarding");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which one of the following is considered as the GSM supplementary service?",
        "Emergency number",
        "SMS",
        "Call forwarding",
        "All of the above",
        null
      );
    }
    if (current == 25) {
      label.setText(
        "Question " +
        control +
        ": How many sub-systems are Global Systems for Mobiles?"
      );
      radioButton[0].setText("4");
      radioButton[1].setText("3");
      radioButton[2].setText("2");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": How many sub-systems are Global Systems for Mobiles?",
        "4",
        "3",
        "2",
        "None of the above",
        null
      );
    }
    if (current == 26) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is considered as the heart of the Global Systems for Mobiles (or GSM)?"
      );
      radioButton[0].setText("Networks Switching Sub System");
      radioButton[1].setText("Operational Support Sub-system");
      radioButton[2].setText("Base Station Subsystem");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  Which of the following is considered as the heart of the Global Systems for Mobiles (or GSM)?",
        "Networks Switching Sub System",
        "Operational Support Sub-system",
        "Base Station Subsystem",
        "None of the above",
        null
      );
    }
    if (current == 27) {
      label.setText(
        "Question " + control + ": The term 'HLR'stands for the ______"
      );
      radioButton[0].setText("Home Location Register");
      radioButton[1].setText("House Location Register");
      radioButton[2].setText("Home Live Register");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": The term 'HLR'stands for the ______",
        "Home Location Register",
        "House Location Register",
        "Home Live Register",
        "None of the above",
        null
      );
    }
    if (current == 28) {
      label.setText(
        "Question " +
        control +
        ": BSC comes under which of the following category?"
      );
      radioButton[0].setText("Operation");
      radioButton[1].setText("Radio");
      radioButton[2].setText("Network");
      radioButton[3].setText("Mobile");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": BSC comes under which of the following category?",
        "Operation",
        "Radio",
        "Network",
        "Mobile",
        null
      );
    }
    if (current == 29) {
      label.setText("Question " + control + ":  The term TDM stands for___.");
      radioButton[0].setText("Time Division Multiplexing");
      radioButton[1].setText("Transfer Multiplexing");
      radioButton[2].setText("Tedious Division Multiplexing");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ": The term TDM stands for___.",
        "Time Division Multiplexing",
        "Transfer Multiplexing",
        "Tedious Division Multiplexing",
        "None of the above",
        null
      );
    }
    if (current == 30) {
      label.setText(
        "Question " +
        control +
        ":  In which of the following, the single-channel has the ability to carry all transmissions simultaneously?"
      );
      radioButton[0].setText("In the Code Division, Multiple Access (or CDMA)");
      radioButton[1].setText("In the Time Division Multiple Access (or TDMA)");
      radioButton[2].setText(
          "In the Frequency Division Multiple Access (or FDMA)"
        );
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":  In which of the following, the single-channel has the ability to carry all transmissions simultaneously?",
        "In the Code Division, Multiple Access (or CDMA)",
        "In the Time Division Multiple Access (or TDMA)",
        "In the Frequency Division Multiple Access (or FDMA)",
        "None of the above",
        null
      );
    }
    if (current == 31) {
      label.setText(
        "Question " +
        control +
        ":  In which one of the following times is specifically divided into several time slots that are in the fixed patterns?"
      );
      radioButton[0].setText("CDMA");
      radioButton[1].setText("TDMA");
      radioButton[2].setText("FDMA");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In which one of the following times is specifically divided into several time slots that are in the fixed patterns?",
        "CDMA",
        "TDMA",
        "FDMA",
        "All of the above",
        null
      );
    }
    if (current == 32) {
      label.setText(
        "Question " +
        control +
        ": How many types of security services are provided by the GSM?"
      );
      radioButton[0].setText("1");
      radioButton[1].setText("2");
      radioButton[2].setText("3");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": How many types of security services are provided by the GSM?",
        "1",
        "2",
        "3",
        "None of the above",
        null
      );
    }
    if (current == 33) {
      label.setText(
        "Question " +
        control +
        ": In which one of the following, the early FM push to talk telephone system, was used?"
      );
      radioButton[0].setText("Half Duplex");
      radioButton[1].setText("Full Duplex");
      radioButton[2].setText("Simple Duplex");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": In which one of the following, the early FM push to talk telephone system, was used?",
        "Half Duplex",
        "Full Duplex",
        "Simple Duplex",
        "None of the above",
        null
      );
    }
    if (current == 34) {
      label.setText(
        "Question " + control + ": The paging system can be used for _______"
      );
      radioButton[0].setText("Sending numeric messages");
      radioButton[1].setText("Audio Calls");
      radioButton[2].setText("Sending alphanumeric messages");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " + control + ":The paging system can be used for _______",
        "Sending numeric messages",
        "Audio Calls",
        "Sending alphanumeric messages",
        "All of the above",
        null
      );
    }
    if (current == 35) {
      label.setText(
        "Question " +
        control +
        ": Which of the following statements about the Half Duplex is correct?"
      );
      radioButton[0].setText("It is a type of communication in one direction");
      radioButton[1].setText(
          "It is a type of communication in one direction at a time"
        );
      radioButton[2].setText(
          "It is a type of by directional communication at the same time"
        );
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following statements about the Half Duplex is correct?",
        "It is a type of communication in one direction",
        "It is a type of communication in one direction at a time",
        "It is a type of by directional communication at the same time",
        "None of the above",
        null
      );
    }
    if (current == 36) {
      label.setText(
        "Question " +
        control +
        ": The term _________ refers to transporting a mobile station from one base station to another base station."
      );
      radioButton[0].setText("Roamer");
      radioButton[1].setText("Forward channel");
      radioButton[2].setText("Handoff or hand over");
      radioButton[3].setText("MIN");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The term _________ refers to transporting a mobile station from one base station to another base station.",
        "Roamer",
        "Forward channel",
        "Handoff or hand over",
        "MIN",
        null
      );
    }
    if (current == 37) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following statements is correct about the PCN?"
      );
      radioButton[0].setText("Wireless concept of making calls");
      radioButton[1].setText("Irrespective of the location of the user");
      radioButton[2].setText("For receiving calls");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which one of the following statements is correct about the PCN?",
        "Wireless concept of making calls",
        "Irrespective of the location of the user",
        "For receiving calls",
        "All of the above",
        null
      );
    }
    if (current == 38) {
      label.setText(
        "Question " +
        control +
        ": The IMT-2000 is a digital Mobile network that functions as the ____________."
      );
      radioButton[0].setText("Cordless");
      radioButton[1].setText("Pager");
      radioButton[2].setText("Lower earth orbit satellite");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The IMT-2000 is a digital Mobile network that functions as the ____________.",
        "Cordless",
        "Pager",
        "Lower earth orbit satellite",
        "All of the above",
        null
      );
    }
    if (current == 39) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following can help in avoiding the interferences between the neighboring base station?"
      );
      radioButton[0].setText("Assigning different group of channels");
      radioButton[1].setText(
          "Using transmitters with the different power level"
        );
      radioButton[2].setText("Using different antennas");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which one of the following can help in avoiding the interferences between the neighboring base station?",
        "Assigning different group of channels",
        "Using transmitters with the different power level",
        "Using different antennas",
        "All of the above",
        null
      );
    }
    if (current == 40) {
      label.setText(
        "Question " +
        control +
        ":  Radio capacity may be increased in cellular by_____."
      );
      radioButton[0].setText("Increase in the radio spectrum");
      radioButton[1].setText(
          "Increasing the number of base stations & reusing the channels"
        );
      radioButton[2].setText("None of the above");
      radioButton[3].setText("Both a & b");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Radio capacity may be increased in cellular by_____.",
        "Increase in the radio spectrum",
        "Increasing the number of base stations & reusing the channels",
        "None of the above",
        "Both a & b",
        null
      );
    }
    if (current == 41) {
      label.setText(
        "Question " +
        control +
        ": Which of the following statements about the Full Duplex is correct?"
      );
      radioButton[0].setText("It is a type of communication in one direction");
      radioButton[1].setText(
          "It is a type of communication in one direction at a time"
        );
      radioButton[2].setText("It is a type of bi-directional communication");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": Which of the following statements about the Full Duplex is correct?",
        "It is a type of communication in one direction",
        "It is a type of communication in one direction at a time",
        "It is a type of bi-directional communication",
        "None of the above",
        null
      );
    }
    if (current == 42) {
      label.setText(
        "Question " +
        control +
        ": The hexagon shape is used for radio coverage because"
      );
      radioButton[0].setText("It uses the maximum area for coverage");
      radioButton[1].setText("Fewer number of cells are required");
      radioButton[2].setText("It approximates a circular radiation pattern");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ": The hexagon shape is used for radio coverage because",
        "It uses the maximum area for coverage",
        "Fewer number of cells are required",
        "It approximates a circular radiation pattern",
        "All of the above",
        null
      );
    }
    if (current == 43) {
      label.setText(
        "Question " +
        control +
        ": The frequency modulation for mobile communication systems was invented by_____."
      );
      radioButton[0].setText("David Bohm");
      radioButton[1].setText("Nikola Tesla");
      radioButton[2].setText("Albert Einstein");
      radioButton[3].setText("Edwin Armstrong");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":The frequency modulation for mobile communication systems was invented by_____.",
        "David Bohm",
        "Nikola Tesla",
        "Albert Einstein",
        "Edwin Armstrong",
        null
      );
    }
    if (current == 44) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is also known as the developer of the world's first cellular system?"
      );
      radioButton[0].setText("Bellcore and Motorola");
      radioButton[1].setText("Nippon Telephone and Telegraph (NTT)");
      radioButton[2].setText("Qualcomm");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following is also known as the developer of the world's first cellular system?",
        "Bellcore and Motorola",
        "Nippon Telephone and Telegraph (NTT)",
        "Qualcomm",
        "None of the above",
        null
      );
    }
    if (current == 45) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following is not referred as the characteristics of the cellular telephone system?"
      );
      radioButton[0].setText("Large frequency spectrum");
      radioButton[1].setText("Limited frequency spectrum");
      radioButton[2].setText("Accommodate a large number of users");
      radioButton[3].setText("None of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which one of the following is not referred as the characteristics of the cellular telephone system?",
        "Large frequency spectrum",
        "Limited frequency spectrum",
        "Accommodate a large number of users",
        "None of the above",
        null
      );
    }
    if (current == 46) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is known as one of the responsibilities of mobile Switching Centre (or MSC) in cellular telephone systems?"
      );
      radioButton[0].setText("Connection of mobile to PSTN");
      radioButton[1].setText("Connection of base station to MSC");
      radioButton[2].setText("Connection of mobile to base stations");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following is known as one of the responsibilities of mobile Switching Centre (or MSC) in cellular telephone systems?",
        "Connection of mobile to PSTN",
        "Connection of base station to MSC",
        "Connection of mobile to base stations",
        "All of the above",
        null
      );
    }
    if (current == 47) {
      label.setText(
        "Question " +
        control +
        ": Which of the following is also referred to as the functions of forward Voice Channel?"
      );
      radioButton[0].setText("Initiating Mobile calls");
      radioButton[1].setText("Broadcasting all traffic for mobiles");
      radioButton[2].setText(
          "Voice transmission from the base station to mobiles"
        );
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following is also referred to as the functions of forward Voice Channel?",
        "Initiating Mobile calls",
        "Broadcasting all traffic for mobiles",
        "Voice transmission from the base station to mobiles",
        "All of the above",
        null
      );
    }
    if (current == 48) {
      label.setText(
        "Question " +
        control +
        ": Which one of the following is transmitted along with the call initiation request during of call by the mobiles?"
      );
      radioButton[0].setText("SCM");
      radioButton[1].setText("ESN");
      radioButton[2].setText("MIN");
      radioButton[3].setText("All of the above");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which one of the following is transmitted along with the call initiation request during of call by the mobiles?",
        "SCM",
        "ESN",
        "MIN",
        "All of the above",
        null
      );
    }
    if (current == 49) {
      label.setText(
        "Question " +
        control +
        ": Which of the following file systems provides similar features that are also offered by the Andrew File system?"
      );
      radioButton[0].setText("ROVER");
      radioButton[1].setText("CODA");
      radioButton[2].setText("Both A & B");
      radioButton[3].setText("None of these");
      setPrintFormat(
        control,
        current,
        "Question " +
        control +
        ":Which of the following file systems provides similar features that are also offered by the Andrew File system?",
        "ROVER",
        "CODA",
        "Both A & B",
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

  boolean check() {
    if (current == 0) return (radioButton[3].isSelected());
    if (current == 1) return (radioButton[0].isSelected());
    if (current == 2) return (radioButton[1].isSelected());
    if (current == 3) return (radioButton[1].isSelected());
    if (current == 4) return (radioButton[2].isSelected());
    if (current == 5) return (radioButton[1].isSelected());
    if (current == 6) return (radioButton[2].isSelected());
    if (current == 7) return (radioButton[3].isSelected());
    if (current == 8) return (radioButton[2].isSelected());
    if (current == 9) return (radioButton[3].isSelected());
    if (current == 10) return (radioButton[3].isSelected());
    if (current == 11) return (radioButton[1].isSelected());
    if (current == 12) return (radioButton[3].isSelected());
    if (current == 13) return (radioButton[3].isSelected());
    if (current == 14) return (radioButton[0].isSelected());
    if (current == 15) return (radioButton[2].isSelected());
    if (current == 16) return (radioButton[2].isSelected());
    if (current == 17) return (radioButton[3].isSelected());
    if (current == 18) return (radioButton[2].isSelected());
    if (current == 19) return (radioButton[0].isSelected());
    if (current == 20) return (radioButton[3].isSelected());
    if (current == 21) return (radioButton[1].isSelected());
    if (current == 22) return (radioButton[3].isSelected());
    if (current == 23) return (radioButton[1].isSelected());
    if (current == 24) return (radioButton[2].isSelected());
    if (current == 25) return (radioButton[1].isSelected());
    if (current == 26) return (radioButton[0].isSelected());
    if (current == 27) return (radioButton[0].isSelected());
    if (current == 28) return (radioButton[1].isSelected());
    if (current == 29) return (radioButton[0].isSelected());
    if (current == 30) return (radioButton[0].isSelected());
    if (current == 31) return (radioButton[1].isSelected());
    if (current == 32) return (radioButton[2].isSelected());
    if (current == 33) return (radioButton[0].isSelected());
    if (current == 34) return (radioButton[3].isSelected());
    if (current == 35) return (radioButton[1].isSelected());
    if (current == 36) return (radioButton[2].isSelected());
    if (current == 37) return (radioButton[3].isSelected());
    if (current == 38) return (radioButton[3].isSelected());
    if (current == 39) return (radioButton[0].isSelected());
    if (current == 40) return (radioButton[1].isSelected());
    if (current == 41) return (radioButton[2].isSelected());
    if (current == 42) return (radioButton[0].isSelected());
    if (current == 43) return (radioButton[3].isSelected());
    if (current == 44) return (radioButton[1].isSelected());
    if (current == 45) return (radioButton[0].isSelected());
    if (current == 46) return (radioButton[0].isSelected());
    if (current == 47) return (radioButton[2].isSelected());
    if (current == 48) return (radioButton[3].isSelected());
    if (current == 49) return (radioButton[1].isSelected());
    return false;
  }

  public static void main(String s[]) {
    // create login ibject and run
    Login();
    prollno = JOptionPane.showInputDialog("Enter Registration No");
    pname = JOptionPane.showInputDialog("Enter Full Name");
    new OnlineTestmc("Online Test App");
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
        "C:\\quiz_master_result\\mc_result\\" + prollno + ".pdf"
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
        "                                        Mobile Computing",
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
