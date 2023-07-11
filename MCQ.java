import java.util.Scanner;
public class MCQ {
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
    public static void main(String[] args) {
        String q1 = "What color are apples?\n" + "(a)Red/Green\n(b)Orange\n(c)Magenta\n";
        String q2 = "What color are bananas?\n" + "(a)Yellow\n(b)Orange\n(c)Pink\n";
        String q3 = "What color are grapes?\n" + "(a)Yellow\n(b)Green\n(c)Purple\n";
        String q4 = "What color are mangoes?\n" + "(a)Red/Yellow\n(b)Green\n(c)Voilet\n";
        String q5 = "What color are guava?\n" + "(a)Black\n(b)Brown\n(c)Green\n";

        Question[] questions = {
                new Question(q1, "a"),
                new Question(q2, "a"),
                new Question(q3, "b"),
                new Question(q4, "a"),
                new Question(q5, "c")
        };
        takeTest(questions);
    }
    public static void takeTest(Question [] questions) {
        Scanner sc = new Scanner(System.in);
        int score = 0;
        for(int i=0;i<questions.length;i++){
            System.out.println(questions[i].prompt);
            String answer = sc.nextLine();
            if(answer.equals(questions[i].answer)){
                score++;
            }
        }
        System.out.println("You got " + score + "/" + questions.length);
    }
}
