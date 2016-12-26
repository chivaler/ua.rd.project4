package ua.rd.project4.controller.util.impl;

import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpSession;

public class SessionWrapperImpl implements SessionWrapper {
    private HttpSession session;

    public SessionWrapperImpl(HttpSession session) {
        this.session = session;
    }

    @Override
    public void invalidate() {
        session.invalidate();
    }

    @Override
    public void setUser(User user) {
        session.setAttribute("user", user);
    }

    @Override
    public User getUser() {
        return session==null?null:(User) session.getAttribute("user");
    }

    @Override
    public void setLanguage(String lang) {
        session.setAttribute("lang", lang);
    }
}
