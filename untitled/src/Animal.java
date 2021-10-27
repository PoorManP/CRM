
public class Animal {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //run
    public void run() {
        System.out.println(name + "is runing");
    }

    public void fly() {
        System.out.println(name + "is flying");
    }
}
