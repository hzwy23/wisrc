package com.wisrc.sys.webapp.entity;

public class SysUserWorktileEntity {
    private String worktileId;
    private String username;
    private String accessToken;

    public String getWorktileId() {
        return worktileId;
    }

    public void setWorktileId(String worktileId) {
        this.worktileId = worktileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "SysUserWorktileEntity{" +
                "worktileId='" + worktileId + '\'' +
                ", username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
