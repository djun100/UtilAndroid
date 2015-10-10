package com.cy.DataStructure;

import java.util.ArrayList;

public class UtilArrayList<E> extends ArrayList<E> {
        public UtilArrayList<E> addOne(E e){
            add(e);
            return this;
        }
    }