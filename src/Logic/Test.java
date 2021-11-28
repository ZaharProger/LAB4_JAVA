package Logic;

//Зачет
public class Test extends AttestationWork {

    public Test(byte questionsAmount){
        super(questionsAmount);
    }

    @Override
    public void calculateResult(Student student, double workResult){
        student.setTestResult(workResult >= 0.5);
    }
}
