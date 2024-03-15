package com.xama.backend.infrastructure.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.xama.backend.infrastructure.NLP.jieba.Keyword;
import com.xama.backend.infrastructure.NLP.jieba.TfIdfAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JiebaUtil {

    public static List<String> segment(String s){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> index = segmenter.process(s, JiebaSegmenter.SegMode.INDEX);
        List<SegToken> search = segmenter.process(s, JiebaSegmenter.SegMode.SEARCH);
        System.out.println("结巴分词：精确模式" + index.stream().map(e -> e.word).collect(Collectors.toList()));
        System.out.println("结巴分词：搜索模式" + search.stream().map(e -> e.word).collect(Collectors.toList()));
        return segmenter.sentenceProcess(s);
    }

    public static List<String> keywords(String content,int top) {
        //去除空格和特殊字符
        String regEx = "[\n\r\t`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？^p]";
        String aa = "";
        content = content.replaceAll(regEx, aa).replaceAll("　", "").replaceAll(" ", "");
        List<String> keywords = new ArrayList<>();
        //提取10个关键词
        TfIdfAnalyzer tfIdfAnalyzer = new TfIdfAnalyzer();
        List<Keyword> list = tfIdfAnalyzer.analyze(content, top);
        for (Keyword word : list) {
            keywords.add(word.getName());
        }
        return keywords;
    }
}
