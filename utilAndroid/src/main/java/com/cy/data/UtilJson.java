package com.cy.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UtilJson {

    /**
     * json 转 包含map的类型
     * */
    public static <T> T json2MapRelative(String json) {
        T map = JSON.parseObject(json, new TypeReference<T>() {});
        return map;
    }

    public static void main(String[] args) {
        Set<List<String>> listSet = new HashSet<>();
        List<String> list = new ArrayList<>();
        list.add("hh");
        list.add("ss");
        listSet.add(list);
        String listSetJson = JSON.toJSONString(listSet);
        System.out.println("test set:"+listSetJson);

//        Set<String> jsonSet = json2Map(setJson);
//        System.out.println(JSON.toJSONString(jsonSet));
        Set<List<String>> jsonListSet =JSON.parseObject(listSetJson,Set.class);
        System.out.println("test set:"+JSON.toJSONString(jsonListSet));



        Map<String, Set<List<String>>> map = new HashMap<>();
        map.put("host", listSet);
        String mapJson = JSON.toJSONString(map);
        System.out.println("test setMap:"+mapJson);

        Map<String, Set<String>> jsonMap =
                json2MapRelative(mapJson);
        System.out.println("test setMap:"+JSON.toJSONString(jsonMap));



        List<Map<String,Set<List<String>>>> mapList=new ArrayList<>();
        mapList.add(map);
        String listJson = JSON.toJSONString(mapList);
        System.out.println("test mapList:"+listJson);

        List<Map<String, Set<List<String>>>> jsonList =
                json2MapRelative(listJson);
        System.out.println("test mapList:"+JSON.toJSONString(jsonList));
    }
}