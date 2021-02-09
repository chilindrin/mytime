package com.chilin.org.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Day implements Serializable {

    private String dayRegistered;
    private String comingTime;
    private String leavingTime;
    private String beginnPause;
    private String endePause;

}
