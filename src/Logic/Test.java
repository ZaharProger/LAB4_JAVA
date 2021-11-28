package Logic;

//Зачет
public class Test extends AttestationWork{

    public Test(byte questionsAmount){
        super(questionsAmount);
    }

    @Override
    public void calculateResult(Student student){
        student.setTestResult(examine() >= 0.5);
    }
}
