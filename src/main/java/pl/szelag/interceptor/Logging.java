package pl.szelag.interceptor;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {

    @Resource
    private SessionContext sessionContext;

    @AroundInvoke
    @ExcludeClassInterceptors
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {

        StringBuilder message = new StringBuilder("Calling the method ")
                .append(invocation.getTarget().getClass().getName()).append(".")
                .append(invocation.getMethod().getName());

        message.append(" by ").append(sessionContext.getCallerPrincipal().getName());

        try {
            Object[] parameters = invocation.getParameters();

            if (null != parameters) {
                for (Object param : parameters) {
                    if (param != null) {
                        message.append(" with a parameter ")
                                .append(param.getClass().getName()).append("=")
                                .append(param).append(".");
                    } else {
                        message.append(" with a NULL parameter").append(".");
                    }
                }
            }
            long startTime = System.currentTimeMillis();
            Object result = invocation.proceed();
            long duration = System.currentTimeMillis() - startTime;

            message.append(" (execution time ")
                    .append(duration)
                    .append(" ms").append(").");

            if (result != null) {
                message.append(" Returned ")
                        .append(result.getClass().getName()).append("=")
                        .append(result).append(".");
            } else {
                message.append(" NULL value returned!");
            }
            return result;
        } catch (Exception ex) {
            message.append(" An exception has occurred: ").append(ex).append("!");
            throw ex;
        } finally {
            Logger.getGlobal().log(Level.INFO, message.toString());
        }
    }
}
