package Logic;

//Экзамен
public class Exam extends AttestationWork{

    public Exam(byte questionsAmount){
        super(questionsAmount);
    }

    @Override
    public void calculateResult(Student student){
        double average = examine();
        byte result;
        if (average >= 0.9){
            result = 5;
        }
        else if (average >= 0.8){
            result = 4;
        }
        else if (average >= 0.6){
            result = 3;
        }
        else{
            result= 2;
        }

        if (student.getDiffTestResult() >= 3 && student.getTestResult() && result != 5){
            result += 1;
        }

        student.setExamtResult(result);
    }
}
