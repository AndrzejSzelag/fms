package pl.szelag.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ResourceBundle;

@Named(value = "contextUtil")
public final class ContextUtil {

    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static String getSessionID() {
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

    public static String getUserName() {
        Principal user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return null == user ? "ANONYMOUS" : user.getName();
    }

    public static String invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
        return "main";
    }

    public static FacesMessage emitInternationalizedMessage(String messageId, String key) {
        final ResourceBundle resourceBundle = ContextUtil.getDefaultBundle();
        
        if (resourceBundle == null) return null;
        else {
            final FacesMessage facesMessage = new FacesMessage(resourceBundle.getString(key));
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(messageId, facesMessage);
            return facesMessage;
        }
    }

    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        return bundlePath == null ? null : ResourceBundle.getBundle(bundlePath, FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }
}
