package NUTRiAPPApplication;

public class loginForm {
    private String name;
    private String password;
    private double height; 
    private double weight;
    private double targetWeight;
    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getTargetWeight(){
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight){
        this.targetWeight = targetWeight;
    }

    public String getBirthday(){
        return birthday;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

}