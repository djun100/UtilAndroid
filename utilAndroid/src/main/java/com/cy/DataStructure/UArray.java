package com.cy.DataStructure;

import java.lang.reflect.Array;

/**
 * 把一维数组拆分为二维数组。 
 * tips:null也占据数组长度
 *  
 */  
public class UArray {
  /** 
   * 转化一维数组为二维数组。 
   *  
   * @param arr 
   *          原始一维数组 
   * @param colsize 
   *          列的宽度 
   * @param defaultValue 
   *          默认值 
   * @return 转化好的二维数组 
   * 
   * 	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object[] sa= new String[]{"我","了","个","去"};
//		ArrayList<String> alSa= UtilTransList.transArrayToArrayList(sa);
//		System.out.println(alSa.toString());
		Object sas[][]= transOneToTow(sa, 2, 0);
		ArrayList<Object> alSa1=UtilTransList.transArrayToArrayList(sas[0]);
		ArrayList<Object> alSa2=UtilTransList.transArrayToArrayList(sas[1]);
		System.out.println(alSa1.toString());
		System.out.println(alSa2.toString());
	}
   */  
  @SuppressWarnings("unchecked")
public static <T> T[][] transOneToTow(T[] arr, int colsize, T defaultValue) {  
    int rows = (arr.length + colsize + 1) / colsize;  
	T[][] arr2 = (T[][])new Object[rows][colsize];  
    for (int row = 0; row < rows; row++) {  
      for (int col = 0; col < colsize; col++) {  
        if (row * colsize + col < arr.length) {  
          arr2[row][col] = arr[row * colsize + col];  
        } else {  
          arr2[row][col] = defaultValue;  
        }  
      }  
    }  
    return arr2;  
  }  
  /**获取二维数组的指定行的非空子数组
 * @param channels2
 * @param row 第几行，从0开始
 * @return
 */
@SuppressWarnings("unchecked")
public static  <T> T[] transTowToOne(T[][] channels2,int row){
	int temp=0;
	if (channels2.length<=row ) {
		return null;
	}  
	T[] channels=null;
	//获取null项的个数
	for (int i = 0; i < channels2[0].length; i++) {
		if(channels2[row][i]==null)
			temp+=1;
	}
	for (int i = 0; i < channels2[0].length; i++) {
		channels=(T[])new 	Object[channels2[0].length-temp] ;
	}
	for (int i = 0; i < channels2[0].length-temp; i++) {
		channels[i]=channels2[row][i];
	}
	  
	  return channels;
  }

	/**
	 * 增大数组长度
	 * 
	 * @param oldArray
	 * @param newSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object enlarge(Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		return newArray;
	}
	/**
	 * @param list
	 * @return [0]ss<br>
	 * 			[1]ss<br>
	 * 			...
	 */
	public static <T> String toString(T[] list){
		if (list==null) {
			return null;
		}
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<list.length;i++){
			sb.append("["+i+"]").append(list[i]).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Description: Array add length
	 * @param oldArray
	 * @param addLength
	 * @return Object
	 */
	public static Object arrayAddLength(Object oldArray,int addLength) {
		Class c = oldArray.getClass();
		if(!c.isArray())return null;
		Class componentType = c.getComponentType();
		int length = Array.getLength(oldArray);
		int newLength = length + addLength;
		Object newArray = Array.newInstance(componentType,newLength);
		System.arraycopy(oldArray,0,newArray,0,length);
		return newArray;
	}
	/**
	 * Description: Array reduce lenght
	 * @param oldArray
	 * @param reduceLength
	 * @return Object
	 */
	public static Object arrayReduceLength(Object oldArray,int reduceLength) {
		Class c = oldArray.getClass();
		if(!c.isArray())return null;
		Class componentType = c.getComponentType();
		int length = Array.getLength(oldArray);
		int newLength = length - reduceLength;
		Object newArray = Array.newInstance(componentType,newLength);
		System.arraycopy(oldArray,0,newArray,0,newLength);
		return newArray;
	}
}  