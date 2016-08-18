package com.cy.UtilList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试用实体类
 * @author acer
 *
 */
public class Member{
    // 格式化日期用
    private static final SimpleDateFormat MY_SDF = new SimpleDateFormat(
            "yyyy-MM-dd");

    // 几个属性
    private int id;
    private String username;
    private int level;
    private Date birthday;

    // 构造函数
    public Member(int id, String username, int level, String birthday) throws Exception {
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
}