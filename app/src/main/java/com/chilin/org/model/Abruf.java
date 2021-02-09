package com.chilin.org.model;

import lombok.Data;

@Data
public class Abruf {

    private String name;
    private String anfang;
    private String ende;
    private String stunden;

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Abruf)){
            return false;
        }
        Abruf otherAbruf = (Abruf) object;
        return this.name.equals(otherAbruf.getName()) &&
                this.anfang.equals(otherAbruf.getAnfang()) &&
                this.ende.equals(otherAbruf.getEnde()) &&
                this.stunden.equals(otherAbruf.getStunden());
    }

}
