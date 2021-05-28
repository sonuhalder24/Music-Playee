package com.example.musicplayee;

public class User {
    private String userName;
    private String email;
    private String password;
    private String checked;

    public User(){

    }
    public User(String userName, String email, String password,String checked) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.checked=checked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
