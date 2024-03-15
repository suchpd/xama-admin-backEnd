package com.xama.backend.api;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    ListNode next(ListNode next) {
        this.next = next;

        return this;
    }
}
