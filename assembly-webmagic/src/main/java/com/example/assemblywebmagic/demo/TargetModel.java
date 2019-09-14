package com.example.assemblywebmagic.demo;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

@Data
public class TargetModel {

    Integer id;
    Integer pId;
    String name;
    String urls;
    String level;
    String sort;
    String code;
    String longcode;
    List<TargetModel> childs=Lists.newArrayList();

}
