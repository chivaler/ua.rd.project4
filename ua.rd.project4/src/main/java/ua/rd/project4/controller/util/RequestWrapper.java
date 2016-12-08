package ua.rd.project4.controller.util;

public interface RequestWrapper {
    void setAttribute(String attributeName, Object value);
    String getParameter(String attributeName);
    SessionWrapper getSessionWrapper(boolean toCreate);
    default SessionWrapper getSessionWrapper() {
        return getSessionWrapper(false);
    }

}
