package Fundamentals.Chapter5;

public final class InheritanceSon extends InheritanceSuper {
    private double bonus;

    public InheritanceSon(String name, double salary) {
        super(name, salary);
        bonus = 0;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalary() {
        return super.getSalary() + bonus;
    }

    public String getDiscription() {
        return "Son: " + super.getName() + getSalary();
    }
}
