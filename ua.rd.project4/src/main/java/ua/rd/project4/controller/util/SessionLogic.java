package ua.rd.project4.controller.util;

public class SessionLogic {
    private final static SessionLogic instance = new SessionLogic();

    private SessionLogic() {
    }

    public static SessionLogic getInstance() {
        return instance;
    }

//    public getLogin(req)
}
