package com.thoughtmechanix.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;


public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    private final ServerWebExchange serverWebExchange;

    public FilterUtils(ServerWebExchange serverWebExchange) {
        this.serverWebExchange = serverWebExchange;
    }

    public String getCorrelationId() {

        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        String id = headers.getFirst(CORRELATION_ID);
        if (id != null) {
            return id;
        } else {
            return serverWebExchange.getAttribute(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId) {
        serverWebExchange.getAttributes().put(CORRELATION_ID, correlationId);
    }

    public final String getOrgId() {
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        if (headers.getFirst(ORG_ID) != null) {
            return headers.getFirst(ORG_ID);
        } else {
            return serverWebExchange.getAttribute(ORG_ID);
        }
    }

    public void setOrgId(String orgId) {
        serverWebExchange.getAttributes().put(ORG_ID, orgId);
    }

    public final String getUserId() {
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        if (headers.getFirst(USER_ID) != null) {
            return headers.getFirst(USER_ID);
        } else {
            return serverWebExchange.getAttribute(USER_ID);
        }

    }

    public void setUserId(String userId) {
        serverWebExchange.getAttributes().put(USER_ID, userId);
    }

    public final String getAuthToken() {
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        if (headers.getFirst(AUTH_TOKEN) != null) {
            return headers.getFirst(AUTH_TOKEN);
        } else {
            return serverWebExchange.getAttribute(AUTH_TOKEN);
        }
    }

    public String getServiceId() {

        //We might not have a service id if we are using a static, non-eureka route.
     /*    RequestContext ctx = RequestContext.getCurrentContext();

     if (ctx.get("serviceId") == null) return "";
        return ctx.get("serviceId").toString();*/
        return null;
    }


}
