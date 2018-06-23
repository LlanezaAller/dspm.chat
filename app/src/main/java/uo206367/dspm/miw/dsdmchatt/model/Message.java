package uo206367.dspm.miw.dsdmchatt.model;

public class Message {

    private String section;
    private Data data;

    public Message(String section, Data data) {
        this.section = section;
        this.data = data;
    }

    public Message(String userName, String text){
        setSection("messages");
        setData(new Data(userName, text));
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
