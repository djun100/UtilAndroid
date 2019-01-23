package com.cy.data;

import com.cy.utils.UtilsReflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by <a href="https://github.com/djun100">cy</a> on 2016/5/21.
 */

public class UtilDummyData {

    /**
     * @param min
     * @param max
     * @return
     */
    public static String makeWord(int min, int max) {
        int count= UtilRandom.getInt(min,max);
        return UtilRandom.getChinese(count);

    }

    /**生成50个实例*/
    public static <T> ArrayList<T> makeData(Class<T> clazz){
        return makeData(clazz,50);
    }

    /**生成的实例字段长度为1-100*/
    public static <T> ArrayList<T> makeData(Class<T> clazz, long count){
        return makeData(clazz,count,1,100,3,5);
    }

    /**生成bean实例列表
     * @param clazz bean class
     * @param count 生成实例数量
     * @param minStrLength 实例字段长度限制最小值
     * @param maxStrLength 实例字段长度限制最大值*/
    public static <T> ArrayList<T> makeData(Class<T> clazz, long count
            , int minStrLength, int maxStrLength, int minSubListLength, int maxSubListLength){

        if (clazz.getName().equals(Integer.class.getName())){
            ArrayList<Integer> dataInt=new ArrayList<>();
            for (int i = 0; i < count; i++) {
                dataInt.add(UtilRandom.getInt(minStrLength, (int) (Math.pow(10,maxStrLength)-1)));
            }
            return (ArrayList<T>) dataInt;

        }else if (clazz.getName().equals(String.class.getName())){
            ArrayList<String> dataStr=new ArrayList<>();
            for (int i = 0; i < count; i++) {
                dataStr.add(makeWord(minStrLength,maxStrLength));
            }
            return (ArrayList<T>) dataStr;

        }else{
            ArrayList<T> data=new ArrayList<T>();
            for (int i = 0; i < count; i++) {
                data.add(makeInstance(clazz,minStrLength,maxStrLength,null,minSubListLength,maxSubListLength));
            }
            return data;
        }
    }
    /**
     * 生成bean实例
     * @param c
     * @param minStrLength  字段为String时，生成的随机值长度的最小值
     * @param maxStrLength 字段为String时，生成的随机值长度的最大值
     * @param parentInstance 递归写法，当前处理类为子类时必传父类实例，其他时候必须传null
     * @param minSubListLength  当字段为list类型时，需要生成的最小list长度
     * @param maxSubListLength  当字段为list类型时，需要生成的最大list长度
     * @return*/
    public static <T> T makeInstance(Class<T> c, int minStrLength, int maxStrLength
            , Object parentInstance, int minSubListLength, int maxSubListLength) {
        T t = null;
//        System.out.println(c.getName());
//        System.out.println(c.getModifiers());
        try {
            Constructor<?>[] con = c.getDeclaredConstructors();
            if (parentInstance!=null && c.getModifiers() == Modifier.PUBLIC/**public且非static*/){
                t = (T) con[0].newInstance(parentInstance);
            }else {
                t = (T) con[0].newInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            System.out.println(fields[i].getName());
            System.out.println(fields[i].getType());
            try {
                if (fields[i].getType().toString().equals("class java.lang.String"))
                fields[i].set(t, makeWord(minStrLength,maxStrLength));
                if (fields[i].getType().toString().equals("int"))
                    fields[i].set(t, UtilRandom.getInt(0,999));
                if (fields[i].getType().toString().equals("float"))
                    fields[i].set(t,new Random().nextFloat());
                if (fields[i].getType().toString().equals("boolean"))
                    fields[i].set(t,new Random().nextBoolean());
                if (fields[i].getType().toString().equals("double"))
                    fields[i].set(t,new Random().nextDouble());
                if (fields[i].getType().toString().equals("long"))
                    fields[i].set(t,new Random().nextLong());
                if (fields[i].getType().toString().equals("interface java.util.List")||
                        fields[i].getType().toString().equals("class java.util.ArrayList")){
                    ArrayList list=new ArrayList();
                    Class fieldArgClass= UtilsReflect.getGenericClass(fields[i]);
                    int count= UtilRandom.getInt(minSubListLength,maxSubListLength);
                    for (int j=0;j<count;j++) {
                        if (fieldArgClass.toString().equals("class java.lang.String")) {
                            list.add(makeWord(minStrLength,maxStrLength));
                        }else {
                            list.add(makeInstance(fieldArgClass,minStrLength,maxStrLength,t
                                    ,minSubListLength,maxSubListLength));
                        }
                    }
                    fields[i].set(t,list);
                }

                if (fields[i].getType().toString().equals("interface java.util.Map")
                        || fields[i].getType().toString().equals("class java.util.HashMap")
                        || fields[i].getType().toString().equals("class java.util.LinkedHashMap")
                        ){
                    Map map;
                    if (fields[i].getType().toString().equals("class java.util.LinkedHashMap")){
                        map=new LinkedHashMap();
                    }else {
                        map=new HashMap();
                    }
                    Class fieldArgClass= UtilsReflect.getGenericClass(fields[i]);
                    int count= UtilRandom.getInt(minSubListLength,maxSubListLength);
                    for (int j=0;j<count;j++) {
                        if (fieldArgClass.toString().equals("class java.lang.String")) {
                            map.put("k"+makeWord(minStrLength,maxStrLength),"v"+makeWord(minStrLength,maxStrLength));
                        }else {
                            map.put("k"+makeWord(minStrLength,maxStrLength),"v"+makeInstance(fieldArgClass,minStrLength,maxStrLength,t
                                    ,minSubListLength,maxSubListLength));
                        }
                    }
                    fields[i].set(t,map);
                }

                if (fields[i].getType().toString().contains("class "+c.getName())){
                    //field current dealing is class type
                    //get all declared sub classes ,not public sub classes
                    Class<?>[] classes= c.getDeclaredClasses();
//                    Class<?>[] classes= c.getClasses();
                    for (int j = 0; j < classes.length; j++) {
                        String currentSubClass=classes[j].getName();
//                        System.out.println("currentSubClass:"+currentSubClass);
                        String currentFieldType=fields[i].getType().toString();
//                        System.out.println("currentFieldType:"+currentFieldType);
                        if (currentFieldType.contains(currentSubClass)){
                    fields[i].set(t, makeInstance(classes[j],minStrLength,maxStrLength,t
                            ,minSubListLength,maxSubListLength));
//                    fields[i].set(t,classes[j].getDeclaredConstructors()[0].newInstance(t));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> T makeInstance(Class<T> c,Map<String,Object> kvs) {
        T t = null;
        try {
            Constructor<?>[] con = c.getDeclaredConstructors();
            t = (T) con[0].newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String fieldName=fields[i].getName();

            for (Map.Entry<String, Object> entry : kvs.entrySet()) {
                if (fieldName.equals(entry.getKey())){
                    try {
                        fields[i].set(t,entry.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
            return t;
    }

    public static void main(String[] args) {
//        ArrayList<BeanTest> data= makeData(BeanTest.class,50,1,10);
//        for (BeanTest test:data){
//            System.out.println(test);
//        }
//        makeInstance(BeanTest.class);
//        System.out.println(makeInstance(BeanTest.class,1,10,null,3,5));
        System.out.println(makeData(Integer.class,10,1,10,3,5));
    }

}
