package model;

public class Code {
    private Coding[] coding;

    public Code() {
    }

    public Code(Coding[] coding) {
        this.coding = coding;
    }

    public Coding[] getCoding() {
        return coding;
    }

    public void setCoding(Coding[] coding) {
        this.coding = coding;
    }
}
