package Logic;

//Студент
public class Student {
    private int id;
    private String name;
    private String surname;
    private String birthday;
    private boolean testResult;
    private byte diffTestResult;
    private byte examResult;

    public Student(int id, String name, String surname, String birthday, boolean testResult, byte diffTestResult, byte examResult){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.testResult = testResult;
        this.diffTestResult = diffTestResult;
        this.examResult = examResult;
    }

    public boolean hasAttestation(){
        return testResult && diffTestResult >= 3 && examResult >= 3;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setTestResult(boolean testResult) {
        this.testResult = testResult;
    }

    public boolean getTestResult() {
        return testResult;
    }

    public void setDiffTestResult(byte diffTestResult) {
        this.diffTestResult = diffTestResult;
    }

    public byte getDiffTestResult() {
        return diffTestResult;
    }

    public void setExamResult(byte examResult) {
        this.examResult = examResult;
    }

    public byte getExamResult() {
        return examResult;
    }
}
