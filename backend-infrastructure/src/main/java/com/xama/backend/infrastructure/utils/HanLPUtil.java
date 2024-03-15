package com.xama.backend.infrastructure.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HanLPUtil {

    public static List<Nature> EXCLUDE_NATURE = Arrays.asList(Nature.w,Nature.p,Nature.pba,Nature.pbei,Nature.q,Nature.qg,Nature.qt,
            Nature.qv,Nature.r,Nature.rg,Nature.rr,Nature.ry,Nature.rys,Nature.ryt,Nature.ryv,Nature.u,Nature.ud,Nature.ude1,Nature.ude2,Nature.ude3,
            Nature.udeng,Nature.udh,Nature.uj,Nature.ul,Nature.ule,Nature.ulian,Nature.uls,Nature.usuo,Nature.uv,Nature.uyy,Nature.uz,Nature.uzhe,Nature.uzhi,
            Nature.vshi,Nature.vyou);

    public static List<String> segment_Default(String s){
        List<Term> segment = HanLP.segment(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_Precise(String s){
        Segment segment = HanLP.newSegment();
        List<Term> terms = segment.seg(s);
        return terms.stream().filter(e -> !EXCLUDE_NATURE.contains(e.nature)
                        && !e.nature.startsWith("w")
                        && !e.nature.startsWith("x")
                        && !e.nature.startsWith("y")
                        && !e.nature.startsWith("z"))
                .map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_Standard(String s){
        List<Term> segment = StandardTokenizer.segment(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_NLP(String s){
        List<Term> segment = NLPTokenizer.segment(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_Index(String s){
        List<Term> segment = IndexTokenizer.segment(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_NShort(String s){
        Segment nshortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        List<Term> segment = nshortSegment.seg(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_Short(String s){
        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        List<Term> segment = shortestSegment.seg(s);
        return segment.stream().map(e -> e.word).collect(Collectors.toList());
    }
    public static List<String> segment_CRF(String s){
        List<Term> terms;
        try {
            Segment crfLexicalAnalyzer = new CRFLexicalAnalyzer();//旧版本使用的是CRFSegment，已被遗弃
            terms = crfLexicalAnalyzer.seg(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return terms.stream().map(e -> e.word).collect(Collectors.toList());
    }

    /**
     * 提取关键词
     * @param s 语句
     * @param size  关键词数量
     * @return  关键词集合
     */
    public static List<String> extractKeyword(String s,int size){
        List<String> strings = HanLP.extractKeyword(s,size);
        return strings;
    }

    /**
     * 计算字符出现次数
     * @param pattern   匹配的字符
     * @param text  字符集
     * @return  出现次数
     */
    public static int countOccurrences(String pattern, String text) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
