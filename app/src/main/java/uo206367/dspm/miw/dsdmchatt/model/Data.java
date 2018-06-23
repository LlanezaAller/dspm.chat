package uo206367.dspm.miw.dsdmchatt.model;

public class Data {

    private String operation;
    private String name;
    private String userName;
    private String text;

    public Data(String operation, String name, String userName, String text) {
        this.operation = operation;
        this.name = name;
        this.userName = userName;
        this.text = text;
    }

    public Data(String name, String text) {
        this.operation = "text";
        this.name = name;
        this.userName = name;
        this.text = text;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
