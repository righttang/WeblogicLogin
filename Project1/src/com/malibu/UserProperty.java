package com.malibu;

import java.util.List;

public class UserProperty {
    
    public String propertyName;
    public List propertyList;
    public UserProperty() {
        super();
    }

    public UserProperty(String propertyName, List propertyList) {
        super();
        this.propertyName = propertyName;
        this.propertyList = propertyList;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyList(List propertyList) {
        this.propertyList = propertyList;
    }

    public List getPropertyList() {
        return propertyList;
    }
}
