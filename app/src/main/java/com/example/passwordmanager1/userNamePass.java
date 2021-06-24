package com.example.passwordmanager1;

public class userNamePass {
    String sitename,password;

    public userNamePass(String sitename, String password) {
        this.sitename = sitename;
        this.password = password;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
