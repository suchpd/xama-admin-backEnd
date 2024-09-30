package com.xama.backend.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class BackendApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test(){
        ListNode l1 = new ListNode(2).next(new ListNode(4).next(new ListNode(3)));
        ListNode l2 = new ListNode(5).next(new ListNode(6).next(new ListNode(4)));

        ListNode res = addTwoNumbers(l1,l2);
        System.out.println(res);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1), pre = dummyHead;
        int t = 0;
        while (l1 != null || l2 != null || t != 0) {
            if (l1 != null) {
                t += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                t += l2.val;
                l2 = l2.next;
            }
            pre.next = new ListNode(t % 10);
            pre = pre.next;
            t /= 10;
        }

        return dummyHead.next;
    }

    @Test
    void dateTest(){
        String[] timeContent = extractTimeContent("年至2023年，共年");
    }

    public static String[] extractTimeContent(String input) {
        // 定义正则表达式
        String regex = "(\\d+年)?至(\\d+年)?，共(\\d+(\\.\\d+)?)?年";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] content = new String[3];
//        if (matcher.find()) {
            if(matcher.groupCount() < 3){
                throw new RuntimeException("经济效益测算表,时间格式错误！");
            }
            content[0] = matcher.group(1).replace("年","");
            content[1] = matcher.group(2).replace("年","");
            content[2] = matcher.group(3).replace("年","");
//        }
        return content;
    }
}
