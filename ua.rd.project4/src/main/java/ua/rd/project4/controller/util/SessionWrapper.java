package ua.rd.project4.controller.util;

import ua.rd.project4.domain.User;

public interface SessionWrapper {
    void invalidate();
    void setUser(User user);
    User getUser();
    void setLanguage(String lang);
}
