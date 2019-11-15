package com.cy.io;

public abstract class LogListener {
    public abstract void onLog(String level, String tag, String content);
}