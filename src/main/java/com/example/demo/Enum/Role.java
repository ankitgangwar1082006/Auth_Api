package com.example.demo.Enum;

public enum Role{
    ADMIN,
    USER;
    public boolean isExist(String role)
    {
        for(Role r :Role.values())
        {
            if(role.equals(r.toString())) return true;
        }
        return false;
    }
}
