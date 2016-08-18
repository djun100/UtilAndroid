package com.cy.UtilList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试用实体类
 * @author acer
 *
 */
public class MemberComparable implements Comparable<MemberComparable>{
    // 格式化日期用
    private static final SimpleDateFormat MY_SDF = new SimpleDateFormat(
            "yyyy-MM-dd");

    // 几个属性
    private int id;
    private String username;
    private int level;
    private Date birthday;

    // 构造函数
    public MemberComparable(int id, String username, int level, String birthday) throws Exception {
        this.id = id;
        this.username = username;
        this.level = level;
        this.birthday = new Date(MY_SDF.parse(birthday).getTime());
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public Date getBirthday() {
        return birthday;
    }

    // 返回打印用
/*    @Override
    public String toString() {
        return id + "|" + username + "|" + level + "|" + MY_SDF.format(birthday);
    }*/

    @Override
    public String toString() {
        return "MemberComparable{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", level=" + level +
                ", birthday=" + MY_SDF.format(birthday) +
                '}';
    }

    // 注意：如果使用MySortList类，则此方法不再需要。因为此方法是提供给Collections.sort方法使用的。
    @Override
    public int compareTo(MemberComparable m) {
        // 只能对一个字段做比较，如果做整个对象的比较就实现不了按指定字段排序了。
        return this.getUsername().compareTo(m.getUsername());
    }
}