package vo;

public class Student {
    private String stuNo;
    private String stuName;
    private String stuSex;

    public Student() {
    }

    public Student(String stuNo, String stuName, String stuSex) {
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuSex = stuSex;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }
}