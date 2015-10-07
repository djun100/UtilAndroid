package com.cy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyCommEngine implements InvocationHandler{
 
    private Object target;
 
 
    //动态生成代理对象
    public <T>T bind(T target){
        this.target=target;
        return (T)Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
    }
 
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before..... ");
         Object result=method.invoke(target,args);
        System.out.println("after.....");
        return result;
    }
}