package com.pdomingo.mma.sim.domain.fighter;

import org.apache.commons.lang3.StringUtils;

public class Name {
    private String firstName;
    private String lastName;
    private String middleName;
    private String nickName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullNameWithNickName() {
        return StringUtils.isEmpty(nickName) ?
                String.format("%s %s", firstName, lastName) :
                String.format("%s \"%s\" %s", firstName, nickName, lastName);

    }
}
