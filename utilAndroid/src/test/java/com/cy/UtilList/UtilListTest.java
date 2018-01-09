package com.cy.UtilList;

import com.cy.data.UtilCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class UtilListTest {
    // 测试函数
    public static void main(String[] args) throws Exception {
        // 生成自定义对象，然后对它按照指定字段排序
        List<MemberComparable> listMemberComparable = new ArrayList<MemberComparable>();
        listMemberComparable.add(new MemberComparable(1, "wm123", 3, "1992-12-01"));
        listMemberComparable.add(new MemberComparable(2, "a234", 8, "1995-12-01"));
        listMemberComparable.add(new MemberComparable(3, "m456", 12, "1990-12-01"));
        System.out.println("listMemberComparable当前顺序...");
        System.out.println(listMemberComparable);
        List<Member> listMember = new ArrayList<Member>();
        listMember.add(new Member(1, "wm123", 3, "1992-12-01"));
        listMember.add(new Member(2, "a234", 8, "1995-12-01"));
        listMember.add(new Member(3, "m456", 12, "1990-12-01"));
        listMember.add(new Member(4, "主任", 13, "1991-12-01"));
        listMember.add(new Member(5, "元首", 13, "1991-12-01"));
        listMember.add(new Member(5, "将军", 13, "1991-12-01"));
        System.out.println("listMember当前顺序...");
        System.out.println(listMember);

        // 方式一排序输出
        System.out.println("listMemberComparable默认排序（用自带的compareTo方法）后...");
        Collections.sort(listMemberComparable);
        System.out.println(listMemberComparable);
        System.out.println("listMemberComparable倒序（用自带的compareTo方法）后...");
        Collections.sort(listMemberComparable, Collections.reverseOrder());
        System.out.println(listMemberComparable);

        // 方式二排序输出
//        UtilSortList<Member> msList = new UtilSortList<Member>();
        UtilCollection.sortByMethod(listMember, "getUsername", false);
        System.out.println("Member按字段用户名排序后...");
        System.out.println(listMember);

        UtilCollection.sortByMethod(listMember, "getLevel", false);
        System.out.println("Member按字段级别排序后...");
        System.out.println(listMember);

        UtilCollection.sortByMethod(listMember, "getBirthday", true);
        System.out.println("Member按字段出生日期倒序后...");
        System.out.println(listMember);
    }
}
/*
listMemberComparable当前顺序...
[MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01},
MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01}]

listMemberComparable默认排序（用自带的compareTo方法）后...username
[MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01},
MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01}]
listMemberComparable倒序（用自带的compareTo方法）后...
[MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01},
MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01}]


listMember当前顺序...
[MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01},
MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01}]

Member按字段用户名排序后...
[MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01},
MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01}]
Member按字段级别排序后...
[MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01},
MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01}]
Member按字段出生日期倒序后...
[MemberComparable{id=2, username='a234', level=8, birthday=1995-12-01},
MemberComparable{id=1, username='wm123', level=3, birthday=1992-12-01},
MemberComparable{id=3, username='m456', level=12, birthday=1990-12-01}]
*/

