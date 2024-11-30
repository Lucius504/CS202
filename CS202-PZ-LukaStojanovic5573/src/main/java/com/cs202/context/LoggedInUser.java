package com.cs203.context;

import com.cs203.model.User;

public class LoggedInUser {
    private static LoggedInUser instance;
    private User user;

    private LoggedInUser() {
    }

    public static synchronized LoggedInUser getInstance() {
        if (instance == null)
            instance = new LoggedInUser();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clear() {
        this.user = null; // Logout
    }
}
