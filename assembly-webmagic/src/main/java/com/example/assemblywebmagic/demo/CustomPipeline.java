package com.example.assemblywebmagic.demo;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class CustomPipeline implements Pipeline {

    List<TargetModel> provinces;

    @Override
    public void process(ResultItems resultItems, Task task) {

        if(resultItems.get("provinces") != null){
            provinces = resultItems.get("provinces");
        }

        if(resultItems.get("cities") != null && provinces.size() > 0){
            List<TargetModel> cities = resultItems.get("cities");
            for(TargetModel province : provinces){
                for(TargetModel city : cities){
                    if(province.getId().equals(city.getPId())){
                        province.getChilds().add(city);
                    }else{
                        continue;
                    }
                }
            }
        }

        if(resultItems.get("areas") != null && provinces.size() > 0){
            List<TargetModel> areas = resultItems.get("areas");
            for(TargetModel province : provinces){
                if(province.getChilds()==null){continue;
                }
                for(TargetModel city : province.getChilds()){
                    for(TargetModel area : areas){
                        if(city.getId().equals(area.getPId())){
                            city.getChilds().add(area);
                        }else{
                            continue;
                        }
                    }
                }
            }
        }
    }
}
