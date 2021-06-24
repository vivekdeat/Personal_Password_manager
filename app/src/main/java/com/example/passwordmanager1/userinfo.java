package com.example.passwordmanager1;

public class userinfo {
    private static String Username;
    private static String Password;
    private static String Recovery_option;
    private static String Recovery_ans;

    public userinfo() {
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getRecovery_option() {
        return Recovery_option;
    }

    public static void setRecovery_option(String recovery_option) {
        Recovery_option = recovery_option;
    }

    public static String getRecovery_ans() {
        return Recovery_ans;
    }

    public static void setRecovery_ans(String recovery_ans) {
        Recovery_ans = recovery_ans;
    }
}
