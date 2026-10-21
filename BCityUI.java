import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class BCityUI extends JFrame {
    private final CardLayout pageLayout = new CardLayout();
    private final JPanel pages = new JPanel(pageLayout);
    private final UserDAO userDAO = new UserDAO();
    private final CityDAO cityDAO = new CityDAO();

    public BCityUI() {
        setTitle("Bangalore Smart City Guide");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pages.add(loginPanel(), "Login");
        pages.add(signUpPanel(), "SignUp");
        pages.add(mainPanel(), "Main");
        setContentPane(pages);
        pageLayout.show(pages, "Login");
        setVisible(true);
    }

    private JPanel loginPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(52, 73, 94));
        JLabel title = heading("Login");
        title.setBounds(420, 45, 250, 35);
        p.add(title);

        JLabel userLabel = label("Username:");
        userLabel.setBounds(300, 140, 120, 30);
        JTextField username = new JTextField();
        username.setBounds(420, 140, 240, 32);

        JLabel passLabel = label("Password:");
        passLabel.setBounds(300, 195, 120, 30);
        JPasswordField password = new JPasswordField();
        password.setBounds(420, 195, 240, 32);

        JButton login = new JButton("Login");
        login.setBounds(420, 255, 105, 34);
        login.addActionListener((ActionEvent e) -> {
            String u = username.getText().trim();
            String pw = new String(password.getPassword());
            if (u.isEmpty() || pw.isEmpty()) {
                message(p, "Please enter username and password.");
                return;
            }
            try {
                if (userDAO.login(u, pw)) pageLayout.show(pages, "Main");
                else message(p, "Invalid username or password.");
            } catch (SQLException ex) {
                databaseError(p, ex);
            }
        });

        JButton signup = new JButton("Sign Up");
        signup.setBounds(540, 255, 120, 34);
        signup.addActionListener(e -> pageLayout.show(pages, "SignUp"));

        p.add(userLabel); p.add(username); p.add(passLabel); p.add(password);
        p.add(login); p.add(signup);
        return p;
    }

    private JPanel signUpPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(new Color(44, 62, 80));
        JLabel title = heading("Create Account");
        title.setBounds(360, 35, 330, 35);
        p.add(title);

        JLabel[] labels = {label("Full Name:"), label("Email:"), label("Username:"), label("Password:")};
        JTextField fullName = new JTextField();
        JTextField email = new JTextField();
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JComponent[] fields = {fullName, email, username, password};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(280, 105 + i * 52, 120, 30);
            fields[i].setBounds(410, 105 + i * 52, 250, 32);
            p.add(labels[i]);
            p.add(fields[i]);
        }

        JButton create = new JButton("Create Account");
        create.setBounds(410, 330, 155, 35);
        create.addActionListener(e -> {
            String name = fullName.getText().trim();
            String mail = email.getText().trim();
            String user = username.getText().trim();
            String pass = new String(password.getPassword());
            if (name.isEmpty() || mail.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                message(p, "Please fill in all fields.");
                return;
            }
            if (!mail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                message(p, "Please enter a valid email address.");
                return;
            }
            if (pass.length() < 6) {
                message(p, "Password must contain at least 6 characters.");
                return;
            }
            try {
                if (userDAO.register(name, mail, user, pass)) {
                    message(p, "Account created. Please log in.");
                    pageLayout.show(pages, "Login");
                }
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062) message(p, "That username is already registered.");
                else databaseError(p, ex);
            }
        });
        JButton back = new JButton("Back to Login");
        back.setBounds(575, 330, 135, 35);
        back.addActionListener(e -> pageLayout.show(pages, "Login"));
        p.add(create); p.add(back);
        return p;
    }

    private JPanel mainPanel() {
        JPanel main = new JPanel(new BorderLayout());
        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        nav.setBackground(new Color(46, 204, 113));
        JPanel content = new JPanel(new CardLayout());

        addContentCard(content, "Home", homePanel());
        addContentCard(content, "Tourist", infoPanel("Tourist", "Tourist Places"));
        addContentCard(content, "Hotels", infoPanel("Hotel", "Hotels"));
        addContentCard(content, "Transport", infoPanel("Transport", "Transport Services"));
        addContentCard(content, "Map", mapPanel());

        String[] tabs = {"Home", "Tourist", "Hotels", "Transport", "Map"};
        for (String tab : tabs) {
            JButton b = new JButton(tab);
            b.setBackground(Color.WHITE);
            b.addActionListener(e -> ((CardLayout) content.getLayout()).show(content, tab));
            nav.add(b);
        }
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> pageLayout.show(pages, "Login"));
        nav.add(logout);

        main.add(nav, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        return main;
    }

    private void addContentCard(JPanel parent, String name, JPanel panel) {
        parent.add(panel, name);
    }

    private JPanel homePanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(new Color(241, 196, 15));
        JLabel title = new JLabel("Welcome to Bangalore Smart City Guide", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(new Color(52, 73, 94));
        JLabel subtitle = new JLabel("Explore tourist places, hotels, transport and maps", JLabel.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        p.add(title, BorderLayout.CENTER);
        p.add(subtitle, BorderLayout.SOUTH);
        return p;
    }

    private JPanel infoPanel(String category, String heading) {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JLabel title = new JLabel(heading, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton refresh = new JButton("Load from Database");
        refresh.addActionListener(e -> {
            try {
                area.setText(cityDAO.getInfo(category));
                area.setCaretPosition(0);
            } catch (SQLException ex) {
                databaseError(p, ex);
            }
        });
        // Load data when the panel is first displayed.
        try {
            area.setText(cityDAO.getInfo(category));
        } catch (SQLException ex) {
            area.setText("Could not load database information.\nCheck MySQL setup, then click Load from Database.\n\n" + ex.getMessage());
        }

        JButton mapBtn = new JButton("Open Google Maps");
        mapBtn.addActionListener(e -> openMap(category.equals("Tourist") ? "tourist places in Bengaluru"
                : category.equals("Hotel") ? "hotels in Bengaluru" : "transport services in Bengaluru", p));

        JPanel south = new JPanel();
        south.add(refresh);
        south.add(mapBtn);
        p.add(title, BorderLayout.NORTH);
        p.add(new JScrollPane(area), BorderLayout.CENTER);
        p.add(south, BorderLayout.SOUTH);
        return p;
    }

    private JPanel mapPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        JPanel inner = new JPanel(new GridLayout(3, 1, 8, 8));
        JLabel prompt = new JLabel("Enter a place or address in Bengaluru:");
        JTextField location = new JTextField(30);
        JButton open = new JButton("Search on Google Maps");
        open.addActionListener(e -> {
            String value = location.getText().trim();
            if (value.isEmpty()) {
                message(p, "Please enter a location.");
                return;
            }
            openMap(value, p);
        });
        inner.add(prompt); inner.add(location); inner.add(open);
        p.add(inner);
        return p;
    }

    private void openMap(String query, Component parent) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            Desktop.getDesktop().browse(new URI("https://www.google.com/maps/search/?api=1&query=" + encoded));
        } catch (Exception ex) {
            message(parent, "Unable to open Google Maps. Check your default browser.");
        }
    }

    private JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Verdana", Font.BOLD, 24));
        l.setForeground(Color.WHITE);
        return l;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }

    private void message(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }

    private void databaseError(Component parent, SQLException ex) {
        JOptionPane.showMessageDialog(parent,
                "Database operation failed.\nCheck that MySQL is running, smart_city.sql was executed,\n"
                + "the JDBC driver is added, and DBConnection.java has the correct password.\n\n"
                + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BCityUI::new);
    }
}
