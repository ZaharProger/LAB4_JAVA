package Logic;

//Дифференцированный зачет
public class DiffTest extends AttestationWork {

    public DiffTest(byte questionsAmount){
        super(questionsAmount);
    }

    @Override
    public void calculateResult(Student student, double workResult){
        byte result;
        if (workResult >= 0.9){
            result = 5;
        }
        else if (workResult >= 0.7){
            result = 4;
        }
        else if (workResult >= 0.5){
            result = 3;
        }
        else{
            result= 2;
        }

        student.setDiffTestResult(result);
    }
}
