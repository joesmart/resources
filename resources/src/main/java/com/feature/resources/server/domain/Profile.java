package com.feature.resources.server.domain;

/**
 * User: yanjianzou
 * Date: 10/9/12
 * Time: 10:27 AM
 */
@lombok.Data
public class Profile{
    private String auditNotifyUrl;
    private Boolean autoAudit;
    private Integer timeSpan;
    private Boolean enableAuditNotification;
}
