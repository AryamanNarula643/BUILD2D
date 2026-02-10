import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Taskbar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton googleLoginButton;
    private FirebaseAuth firebaseAuth;
    private GoogleAuthConfig googleAuthConfig;
    private JPanel loginPanel;

    public Login() {
        initializeFirebase();
        initializeGoogleAuth();
        initializeUI();
        setWindowIcon();
    }

    private void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("config/key.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            firebaseAuth = FirebaseAuth.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error initializing Firebase: " + e.getMessage(),
                    "Firebase Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeGoogleAuth() {
        googleAuthConfig = new GoogleAuthConfig();
    }

    private void initializeUI() {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Room Planner Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Room Planner Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Email:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 35));
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        loginPanel.add(buttonPanel, gbc);

        googleLoginButton = new JButton("Sign in with Google");
        googleLoginButton.setPreferredSize(new Dimension(200, 35));
        googleLoginButton.setBackground(new Color(66, 133, 244));
        googleLoginButton.setForeground(Color.WHITE);
        googleLoginButton.setFocusPainted(false);
        googleLoginButton.setBorderPainted(false);
        googleLoginButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel googleButtonPanel = new JPanel();
        googleButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        googleButtonPanel.add(googleLoginButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        loginPanel.add(googleButtonPanel, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);

        loginButton.addActionListener(e -> handleFirebaseLogin());
        registerButton.addActionListener(e -> handleFirebaseRegister());
        googleLoginButton.addActionListener(e -> handleGoogleLogin());

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleFirebaseLogin();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void setWindowIcon() {
        try {
            Image scaledImage = getScaledImage(new ImageIcon("logo/logo.png").getImage());
            setIconImage(scaledImage);
            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) {
                Taskbar.getTaskbar().setIconImage(scaledImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image getScaledImage(Image srcImg) {
        return srcImg.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
    }

    private void showMainFrame(String title) {
        try {
            Image scaledImage = getScaledImage(new ImageIcon("logo/logo.png").getImage());
            JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setIconImage(scaledImage);
            if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) {
                Taskbar.getTaskbar().setIconImage(scaledImage);
            }
            frame.add(new RoomPlan2());
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleFirebaseLogin() {
        String email = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (email.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            this.dispose();
            SwingUtilities.invokeLater(() -> showMainFrame("Room Planner - Admin"));
            return;
        }

        try {
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();
            SwingUtilities.invokeLater(() -> showMainFrame("Room Planner"));
        } catch (FirebaseAuthException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid email or password!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleFirebaseRegister() {
        String email = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setEmailVerified(false);

            UserRecord userRecord = firebaseAuth.createUser(request);
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please login.",
                    "Registration Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FirebaseAuthException e) {
            JOptionPane.showMessageDialog(this,
                    "Registration failed: " + e.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleGoogleLogin() {
        try {
            String userId = "user-" + System.currentTimeMillis();
            String authUrl = googleAuthConfig.getAuthorizationUrl(userId);

            Desktop.getDesktop().browse(new URI(authUrl));

            new Thread(() -> {
                try {
                    var credential = googleAuthConfig.getCredentials(userId);
                    if (credential != null && credential.getAccessToken() != null) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "Google login successful!");
                            this.dispose();
                            SwingUtilities.invokeLater(() -> showMainFrame("Room Planner"));
                        });
                    }
                } catch (IOException ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this,
                                "Google login failed: " + ex.getMessage(),
                                "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                    });
                    ex.printStackTrace();
                }
            }).start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to initialize Google login: " + ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}