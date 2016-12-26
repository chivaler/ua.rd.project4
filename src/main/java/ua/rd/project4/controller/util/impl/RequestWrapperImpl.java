package ua.rd.project4.controller.util.impl;

import ua.rd.project4.controller.exceptions.RequestAttributeNotPermittedException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class RequestWrapperImpl implements RequestWrapper {
    private HttpServletRequest req;
    private final Set<String> deniedToReadParameters = new HashSet<>();
    private final Set<String> deniedToWriteAttributes = new HashSet<>();

    {
        deniedToReadParameters.add("test");
        deniedToWriteAttributes.add("test");
    }

    public RequestWrapperImpl (HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void setAttribute(String attributeName, Object value) {
        if (deniedToWriteAttributes.contains(attributeName))
            throw new RequestAttributeNotPermittedException("tried setAttr: "+attributeName);
        req.setAttribute(attributeName, value);
    }

    @Override
    public String getParameter(String parameter) {
        if (deniedToReadParameters.contains(parameter))
            throw new RequestAttributeNotPermittedException("tried getParam: "+parameter);
        return req.getParameter(parameter);
    }

    @Override
    public SessionWrapper getSessionWrapper(boolean toCreate) {
        return new SessionWrapperImpl(req.getSession(toCreate));
    }
}
