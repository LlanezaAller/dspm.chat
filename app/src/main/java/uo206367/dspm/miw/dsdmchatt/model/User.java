package uo206367.dspm.miw.dsdmchatt.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    private int id;

    private String userName;
    private String password;

    public User(){

    }

    /**
    public User(String userName, String password){
        this.userName = userName;
        this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }
**/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
