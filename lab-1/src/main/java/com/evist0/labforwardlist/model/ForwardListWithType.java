package com.evist0.labforwardlist.model;

import java.io.Serializable;

public class ForwardListWithType implements Serializable {
    private final String typeName;
    private final ForwardList forwardList;

    public ForwardListWithType(String typeName, ForwardList forwardList) {
        this.typeName = typeName;
        this.forwardList = forwardList;
    }

    public ForwardList getForwardList() {
        return forwardList;
    }

    public String getTypeName() {
        return typeName;
    }
}
