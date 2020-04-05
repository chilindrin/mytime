package com.chilin.org.actions;

public enum ChilinAction {

    CREATE_BACKUP("createBackup");

    private final String action;
    private ChilinAction(String action){
        this.action = action;
    }

    public String getAction(){
        return this.action;
    }

}
