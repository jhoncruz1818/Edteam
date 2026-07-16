package com.edteam.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.firebase")
public class FirebaseProperties {

    private String projectId = "apputp-3adb7";
    private String credentialsPath = "";
    private boolean verifyTokens = false;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public void setCredentialsPath(String credentialsPath) {
        this.credentialsPath = credentialsPath;
    }

    public boolean isVerifyTokens() {
        return verifyTokens;
    }

    public void setVerifyTokens(boolean verifyTokens) {
        this.verifyTokens = verifyTokens;
    }
}
