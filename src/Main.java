import java.util.*;

class Question {
    String questionText;
    List<String> options;
    char correctOption;

    public Question(String questionText, List<String> options, char correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = Character.toUpperCase(correctOption);
    }

    public boolean isCorrect(char answer) {
        return Character.toUpperCase(answer) == correctOption;
    }
}

class Quiz {
    String name;
    List<Question> questions = new ArrayList<>();

    public Quiz(String name) {
        this.name = name;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void takeQuiz(Scanner scanner) {
        int score = 0;
        System.out.println("\nStarting quiz: " + name);
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.questionText);
            char optionChar = 'A';
            for (String opt : q.options) {
                System.out.println(optionChar + ". " + opt);
                optionChar++;
            }
            System.out.print("Your answer: ");
            char userAnswer = scanner.next().charAt(0);
            if (q.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! Correct answer: " + q.correctOption);
            }
        }
        System.out.println("\nQuiz completed! Score: " + score + "/" + questions.size());
    }
}


public class Main {
    static Map<String, Quiz> quizMap = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Generator CLI!");
        String command;
        while (true) {
            System.out.print("\nEnter command (type 'help' to see commands): ");
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "create":
                    createQuiz();
                    break;
                case "add":
                    addQuestionToQuiz();
                    break;
                case "take":
                    takeQuiz();
                    break;
                case "list":
                    listQuizzes();
                    break;
                case "help":
                    printHelp();
                    break;
                case "exit":
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Unknown command. Type 'help' for list of commands.");
            }
        }
    }

    static void createQuiz() {
        System.out.print("Enter quiz name: ");
        String name = scanner.nextLine().trim();
        if (quizMap.containsKey(name)) {
            System.out.println("Quiz already exists.");
        } else {
            quizMap.put(name, new Quiz(name));
            System.out.println("Quiz '" + name + "' created.");
        }
    }

    static void addQuestionToQuiz() {
        System.out.print("Enter quiz name: ");
        String name = scanner.nextLine().trim();
        Quiz quiz = quizMap.get(name);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        System.out.print("Enter question: ");
        String questionText = scanner.nextLine();
        List<String> options = new ArrayList<>();
        for (char opt = 'A'; opt <= 'D'; opt++) {
            System.out.print("Option " + opt + ": ");
            options.add(scanner.nextLine());
        }
        System.out.print("Enter correct option (A/B/C/D): ");
        char correctOption = scanner.nextLine().charAt(0);
        quiz.addQuestion(new Question(questionText, options, correctOption));
        System.out.println("Question added.");
    }

    static void takeQuiz() {
        System.out.print("Enter quiz name: ");
        String name = scanner.nextLine().trim();
        Quiz quiz = quizMap.get(name);
        if (quiz == null || quiz.questions.isEmpty()) {
            System.out.println("Quiz not found or has no questions.");
        } else {
            quiz.takeQuiz(scanner);
        }
    }

    static void listQuizzes() {
        if (quizMap.isEmpty()) {
            System.out.println("No quizzes available.");
        } else {
            System.out.println("Available quizzes:");
            for (String name : quizMap.keySet()) {
                System.out.println("- " + name);
            }
        }
    }

    static void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("create  - Create a new quiz");
        System.out.println("add     - Add question to an existing quiz");
        System.out.println("take    - Take a quiz");
        System.out.println("list    - List all available quizzes");
        System.out.println("help    - Show this help message");
        System.out.println("exit    - Exit the program");
    }
}