package com.cy.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class BeanTest {
    private String field1;
    public String field2;
    protected int field3;
    private BeanTestSub field4;

    protected List<String> field5;
    private List<BeanTestSub> field6;
    private ArrayList<BeanTestSub> field7;

    @Override
    public String toString() {
        return "BeanTest{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4=" + field4 +
                ", field5=" + field5 +
                ", field6=" + field6 +
                ", field7=" + field7 +
                '}';
    }

    public class BeanTestSub {
        private String subField1;

        @Override
        public String toString() {
            return "BeanTestSub{" +
                    "subField1='" + subField1 + '\'' +
                    '}';
        }
    }
}