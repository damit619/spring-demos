package org.javatigers.jaxrs.srv.cxf.aop;

import org.aspectj.lang.annotation.Pointcut;

public class AppPointcuts {
    /**
     * Identifies services
     */
    @Pointcut("@target(org.springframework.stereotype.Service) && @within(org.springframework.stereotype.Service)")
    public void targetIsService() {
    }

    /**
     * Identifies the main application types, which are
     * {@linkplain #targetIsService()}, {@linkplain #targetIsService()} or
     */
    @Pointcut("targetIsService()")
    public void targetIsMainApplicationType() {
    }

    /**
     * Identifies service layer classes
     */
    @Pointcut("within(org.javatigers.jaxrs.srv.service..*.*)")
    public void withinServices() {
    }

    @Pointcut("within(org.javatigers.jaxrs.srv.ws.services..*.*)")
    public void withinWebServiceLayer() {
    }
    
    /**
     * Identifies the core module classes
     */
    @Pointcut("withinServices() || withinWebServiceLayer()")
    public void withinCoreLayer() {
    }

    @Pointcut( "withinCoreLayer()" )
    public void tracePointcut(){}
}
