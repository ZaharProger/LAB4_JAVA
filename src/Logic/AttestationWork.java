package Logic;

//Аттестационная работа
public abstract class AttestationWork {
    protected byte questionsAmount;

    protected AttestationWork(byte questionsAmount){
        this.questionsAmount = questionsAmount;
    }

    //Подсчет среднего арифметического за ответы на вопросы
    public double startWork(){
        double sum = 0;
        byte question = 0;
        while (question < questionsAmount){
            sum += (Math.random() >= 0.5)? 1 : 0;
            ++question;
        }

        return sum / questionsAmount;
    }
    //Определение оценки (в зависимости от типа работы)
    public abstract void calculateResult(Student student, double workResult);
}
