package Logic;

//Экзамен
public class Exam extends AttestationWork {

    public Exam(byte questionsAmount){
        super(questionsAmount);
    }

    @Override
    public void calculateResult(Student student, double workResult){
        byte result;
        if (workResult >= 0.9){
            result = 5;
        }
        else if (workResult >= 0.8){
            result = 4;
        }
        else if (workResult >= 0.6){
            result = 3;
        }
        else{
            result= 2;
        }

        if (student.getDiffTestResult() >= 3 && student.getTestResult() && result != 5){
            result += 1;
        }

        student.setExamResult(result);
    }
}
