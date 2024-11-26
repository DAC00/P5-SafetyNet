package com.opcr.safetynet_alert.model;

import java.util.List;

public class ChildFromAddress {

    private List<Child> children;

    private List<PersonSimple> adults;

    public ChildFromAddress(List<Child> childList, List<PersonSimple> personSimpleList) {
        this.children = childList;
        this.adults = personSimpleList;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<PersonSimple> getAdults() {
        return adults;
    }

    public void setAdults(List<PersonSimple> adults) {
        this.adults = adults;
    }
}
