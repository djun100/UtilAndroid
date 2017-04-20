package com.cy.DataStructure;

import com.cy.utils.UReflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
    public static String makeDummyWord(int min, int max) {
        int count=URandom.getInt(min,max);
        return URandom.getChinese(count);

    }

    /**生成50个实例*/
    public static <T> ArrayList<T> makeDummyData(Class<T> clazz){
        return makeDummyData(clazz,50);
    }

    /**生成的实例字段长度为1-100*/
    public static <T> ArrayList<T> makeDummyData(Class<T> clazz, long count){
        return makeDummyData(clazz,count,1,100,3,5);
    }

    /**生成bean实例列表
     * @param clazz bean class
     * @param count 生成实例数量
     * @param minLength 实例字段长度限制最小值
     * @param maxLength 实例字段长度限制最大值*/
    public static <T> ArrayList<T> makeDummyData(Class<T> clazz, long count
            ,int minLength,int maxLength,int minSubListLength,int maxSubListLength){

        ArrayList<T> data=new ArrayList<T>();

        for (int i = 0; i < count; i++) {
            data.add(makeDummyInstance(clazz,minLength,maxLength,null
                    ,minSubListLength,maxSubListLength));
        }
        return data;
    }
    /**
     * 生成bean实例
     * @param minLength
     * @param maxLength 字段为String时，生成的随机值长度的最小和最大值
     * @param parentInstance 递归写法，当前处理类为子类时必传父类实例，其他时候必须传null
     * */
    public static <T> T makeDummyInstance(Class<T> c, int minLength, int maxLength
            , Object parentInstance, int minSubListLength, int maxSubListLength) {
        T t = null;
        try {
            Constructor<?>[] con = c.getDeclaredConstructors();
            if (parentInstance!=null){
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
                fields[i].set(t, makeDummyWord(minLength,maxLength));
                if (fields[i].getType().toString().equals("int"))
                    fields[i].set(t,new Random().nextInt());
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
                    Class fieldArgClass= UReflect.getGenericClass(fields[i]);
                    int count=URandom.getInt(minSubListLength,maxSubListLength);
                    for (int j=0;j<count;j++) {
                        if (fieldArgClass.toString().equals("class java.lang.String")) {
                            list.add(makeDummyWord(minLength,maxLength));
                        }else {
                            list.add(makeDummyInstance(fieldArgClass,minLength,maxLength,t
                                    ,minSubListLength,maxSubListLength));
                        }
                    }
                    fields[i].set(t,list);
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
                    fields[i].set(t, makeDummyInstance(classes[j],minLength,maxLength,t
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


    public static void main(String[] args) {
//        ArrayList<BeanTest> data= makeDummyData(BeanTest.class,50,1,10);
//        for (BeanTest test:data){
//            System.out.println(test);
//        }
//        makeDummyInstance(BeanTest.class);
        System.out.println(makeDummyInstance(com.cy.DataStructure.BeanTest.class,1,10,null,3,5));
    }

}
