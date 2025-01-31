public class Person {
    private String ad;
    private int age;
    private String programlamaNot;
    private String veriTabaniNot;
    private String IsletmeNot;
    private String sistemAnaliziNot;
    private String projeYonetimiNot;
    private String alinanKurs;
    private String ing;
    private String okuduguBolum;

    public Person(String ad,int age, String programlamaNot,String veriTabaniNot,String IsletmeNot,String sistemAnaliziNot, String projeYonetimiNot,String alinanKurs, String ing, String okuduguBolum) {
        this.ad=ad;
        this.age = age;
        this.alinanKurs = alinanKurs;
        this.ing = ing;
        this.okuduguBolum = okuduguBolum;
        this.IsletmeNot=IsletmeNot;
        this.programlamaNot=programlamaNot;
        this.projeYonetimiNot=projeYonetimiNot;
        this.sistemAnaliziNot=sistemAnaliziNot;
        this.veriTabaniNot=veriTabaniNot;

    }
    public String getAd() {
        return ad;
    }

    public int getAge() {
        return age;
    }

    public String getAlinanKurs(){
        return alinanKurs;
    }
    public String getIng(){
        return ing;
    }
    public String getOkuduguBolum(){
        return okuduguBolum;
    }
    public String getSistemAnaliziNot(){
        return sistemAnaliziNot;
    }
    public String getProjeYonetimiNot(){
        return projeYonetimiNot;
    }
    public String getIsletmeNot(){
        return IsletmeNot;
    }
    public String getProgramlamaNot(){
        return programlamaNot;
    }
    public String getVeriTabaniNot(){
        return veriTabaniNot;
    }

}