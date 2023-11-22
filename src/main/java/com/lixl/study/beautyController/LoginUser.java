package com.lixl.study.beautyController;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUser {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String tenantId;
    private String tenantName;
    private TenantType tenantType;
    private String companyName;

    public LoginUser(String userId, String username, String password, String tenantId, String tenantName, TenantType tenantType) {
        this(userId, username, password, tenantId, tenantName, tenantType, "-");
    }

    public LoginUser(String userId, String username, String password, String tenantId, String tenantName, TenantType tenantType, String companyName) {

        this.userId = userId;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantType = tenantType;
        this.companyName = companyName;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public TenantType getTenantType() {
        return this.tenantType;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public void setTenantName(final String tenantName) {
        this.tenantName = tenantName;
    }

    public void setTenantType(final TenantType tenantType) {
        this.tenantType = tenantType;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }
}
