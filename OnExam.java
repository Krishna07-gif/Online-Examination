import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    // use get() method to view the variable value
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }
}

class UserManagement {
    private static final String FILE_PATH = "users.csv";
    private static Object timer;
    private User currentUser;

    // Load user data from the file and return a list of User objects
    public List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String username = data[0];
            String password = data[1];
            String profile = data[2];
            User user = new User(username, password, profile);
            users.add(user);
        }
        reader.close();
        return users;
    }

    // Save the list of User objects to the file
    public void saveUsers(List<User> users) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        for (User user : users) {
            writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getProfile());
            writer.newLine();
        }
        writer.close();
    }

    // Prompt the user for login credentials and verify them against the user data
    public User login() throws IOException {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Please run the program in a command-line environment.");
            return null;
        }

        String username = console.readLine("Enter your username: ");
        char[] passwordChars = console.readPassword("Enter your password: ");
        String password = new String(passwordChars);

        clearConsole();
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    private void clearConsole() {
    }

    // Prompt the user for registration information and add a new User object to the list
    public void register() throws IOException {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Please run the program in a command-line environment.");
            return;
        }

        String username = console.readLine("Enter a username: ");
        char[] passwordChars = console.readPassword("Enter a password: ");
        String password = new String(passwordChars);

        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already taken.");
                return;
            }
        }

        String profile = console.readLine("Enter your profile information: ");
        User newUser = new User(username, password, profile);
        users.add(newUser);
        saveUsers(users);
        System.out.println("Registration successful!");
    }

    // Prompt the user for updated profile information and update the user's profile in the list
    public void updateProfile(User user) throws IOException {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Please run the program in a command-line environment.");
            return;
        }

        String newProfile = console.readLine("Enter your updated profile information: ");
        user = new User(user.getUsername(), user.getPassword(), newProfile);
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                saveUsers(users);
                System.out.println("Profile updated successfully!");
                return;
            }
        }
    }

    // Prompt the user for a new password and update the user's password in the list
    public void updatePassword(User user) throws IOException {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Please run the program in a command-line environment.");
            return;
        }

        char[] newPasswordChars = console.readPassword("Enter your new password: ");
        String newPassword = new String(newPasswordChars);
        user = new User(user.getUsername(), newPassword, user.getProfile());
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                saveUsers(users);
                System.out.println("Password updated successfully!");
                return;
            }
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logout Successful!");
    }

    // check if user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Display the main menu after successful login
    public void displayMainMenu(User user) throws IOException {
        currentUser = user;
        while (isLoggedIn()) {
            // Display menu options
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start MCQ");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            // Read user's choice
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateProfile(user);
                    break;
                case 2:
                    updatePassword(user);
                    break;
                case 3:
                    startMCQ();
                    break;
                case 4:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static class Question {
        private String prompt;
        private String answer;

        public Question(String prompt, String answer) {
            this.prompt = prompt;
            this.answer = answer;
        }

        public String getPrompt() {
            return prompt;
        }

        public String getAnswer() {
            return answer;
        }
    }

    public void startMCQ() {
    Question[] questions = {
        new Question("What color are apples?\n(a)Red/Green\n(b)Orange\n(c)Magenta\n", "a"),
        new Question("What color are bananas?\n(a)Yellow\n(b)Orange\n(c)Pink\n", "a"),
        new Question("What color are grapes?\n(a)Yellow\n(b)Green\n(c)Purple\n", "b"),
        new Question("What color are mangoes?\n(a)Red/Yellow\n(b)Green\n(c)Voilet\n", "a")
    };
    takeTest(questions);

    // Add Time for MCQ
    int totalTime = 10; // Total time in seconds
    Timer timer = new Timer();
    TimerTaskExample task = new TimerTaskExample(totalTime, timer);
    timer.scheduleAtFixedRate(task, 0, 1000);
}


class TimerTaskExample extends TimerTask {
    private int seconds = 0;
    private int totalTime; // total time for the timer
    private Timer timer;
    private boolean timeUp = false; // flag to check if time is up

    public TimerTaskExample(int totalTime, Timer timer) {
        this.totalTime = totalTime;
        this.timer = timer;
    }

    @Override
    public void run() {
        System.out.println("Timer: " + seconds++ + " seconds");
        if (seconds >= totalTime) {
            System.out.println("Time's Up! Submitting answer...");
            timeUp = true;
            timer.cancel();
        }
    }

    public boolean isTimeUp() {
        return timeUp;
    }
}


    public static void takeTest(Question[] questions) {
    Scanner sc = new Scanner(System.in);
    int score = 0;
    for (int i = 0; i < questions.length; i++) {
        System.out.println(questions[i].getPrompt());
        String answer = sc.nextLine();
        if (answer.equals(questions[i].getAnswer())) {
            score++;
        }
        if (timer != null && ((TimerTaskExample) timer).isTimeUp()) {
            System.out.println("Time's up! Submitting answer...");
            break;
        }
    }
    System.out.println("You got " + score + "/" + questions.length);
}


public static void main(String[] args) throws IOException {
    UserManagement userManagement = new UserManagement();

    while (true) {
        // Display menu options
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        // Read user's choice
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                User user = userManagement.login();
                if (user != null) {
                    // Perform actions after successful login
                    System.out.println("Performing actions after login...");
                    userManagement.displayMainMenu(user);
                }
                break;
            case 2:
                userManagement.register();
                break;
            case 3:
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
}