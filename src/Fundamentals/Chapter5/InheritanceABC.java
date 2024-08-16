package Fundamentals.Chapter5;

public abstract class InheritanceABC {
    private String name;

    public InheritanceABC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getDiscription();

}
