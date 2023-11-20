package com.zta.zta.models;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;

@Component
public class UserResponse {
    // {"sub":"44da2be5-c9c2-48a8-9c13-867575854d40@carbon.super","given_name":"deus","family_name":"nomas","email":"elviejas@hola.com"}
    private String sub;
    @JsonAlias("given_name")
    private String givenName;
    @JsonAlias("family_name")
    private String familyName;
    private String email;
    private boolean isValid;
   
    public String getSub() {
        return sub;
    }
    public void setSub(String sub) {
        this.sub = sub;
    }
    public String getGivenName() {
        return givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
   
    
    
}
