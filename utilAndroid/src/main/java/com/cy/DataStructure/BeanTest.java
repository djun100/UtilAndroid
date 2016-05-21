package com.cy.DataStructure;

public class BeanTest{
        private String  field1;
        public String  field2;
        protected String field3;
        protected int field4;
        private BeanTestSub field5;

    @Override
    public String toString() {
        return "BeanTest{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4=" + field4 +
                ", field5=" + field5 +
                '}';
    }
    public class BeanTestSub{
        private String  subField1;

        @Override
        public String toString() {
            return "BeanTestSub{" +
                    "subField1='" + subField1 + '\'' +
                    '}';
        }
    }
}