package com.raydata.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserTaskNodeInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private double width;
    private double height;
    private double x;
    private double y;
    private String taskKey;
    private String name;
    private List<CheckInfo> checkInfoList;
}
