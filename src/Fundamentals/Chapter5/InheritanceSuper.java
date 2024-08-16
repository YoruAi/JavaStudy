package Fundamentals.Chapter5;

public class InheritanceSuper extends InheritanceABC {
    private double salary;

    public InheritanceSuper(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public final void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDiscription() {
        return "Super: " + super.getName() + getSalary();
    }
}
