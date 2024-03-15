package com.xama.backend.api;

import com.xama.backend.infrastructure.NLP.coreNLP.NamedEntity;
import com.xama.backend.infrastructure.NLP.coreNLP.PosTag;
import com.xama.backend.infrastructure.NLP.coreNLP.SPTree;
import com.xama.backend.infrastructure.NLP.coreNLP.SenSplit;
import com.xama.backend.infrastructure.utils.HanLPUtil;
import com.xama.backend.infrastructure.utils.JiebaUtil;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

@RunWith(SpringRunner.class)
public class NLPTest {

    @Test
    public void hanLPTest(){
        String s = "识别分析绩效管控要素确定管理改进方向";
        List<String> a = HanLPUtil.segment_Default(s);
        List<String> b = HanLPUtil.segment_Standard(s);
        List<String> c = HanLPUtil.segment_NLP(s);
        List<String> d = HanLPUtil.segment_Precise(s);
        List<String> e = HanLPUtil.segment_CRF(s);
        List<String> f = HanLPUtil.segment_Short(s);



        System.out.println(s);
    }

    @Test
    public void stanfordCoreNLPTest(){
        String text = "识别分析绩效管控要素确定管理改进方向";

        try(InputStream inputStream = new FileInputStream("C:\\MyProject\\xama-admin-backend\\backend-api\\src\\main\\resources\\CoreNLP-chinese.properties")){
            Properties props = new Properties();
//            props.load(inputStream);
            props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
            // 初始化pipeline
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            // 创建文本注解
            Annotation annotation = new Annotation(text);

            // 运行NLP管道
            pipeline.annotate(annotation);

            // 从注解中提取关键词
            List<String> keywords = new ArrayList<>();
            List<CoreLabel> tokens = annotation.get(CoreAnnotations.TokensAnnotation.class);

            for (CoreLabel token : tokens) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                // 自定义过滤条件，例如只提取名词等
                if (pos.startsWith("N")) {
                    keywords.add(word);
                }
            }

            // 输出关键词
            System.out.println("Keywords: " + keywords);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void stanfordCordNLP_42Test(){
        //2.测试分句
        System.out.println("---2.中文分句---");
        String text = "践行兴装强军战略，支撑建军百年奋斗目标的重要举措";
        ArrayList<String>sensRes = new SenSplit(text).getSensRes();
        for(String str:sensRes){
            System.out.println(str);
        }
        //3.词性标注
        System.out.println("---3.词性标注---");
        System.out.println(new PosTag(text).getPostext());
        //4.命名实体识别
        System.out.println("---4.命名实体识别---");
        System.out.println(new NamedEntity(text).getNertext());
        //5.句子的解析树&句子依存分析
        System.out.println("---5-1.句子解析树---");
        SPTree spTree = new SPTree(text);
        System.out.println(spTree.getPrasetext());
        System.out.println("---5-2.句子依存---");
        System.out.println(spTree.getDepprasetext());
    }

    @Test
    public void jiebaNLP_test(){
        String text = "践行兴装强军战略，支撑建军百年奋斗目标的重要举措";

        List<String> segment = JiebaUtil.segment(text);
        System.out.println("结巴分词：默认模式" + segment);

        List<String> keywords = JiebaUtil.keywords(text,0);
        keywords.sort(Comparator.comparing(text::indexOf));
        System.out.println("结巴分词：提取关键词" + keywords);
        List<String> nlp = HanLPUtil.segment_NLP(text);
        System.out.println("HanLP分词：NLP模式" + nlp);
        List<String> def = HanLPUtil.segment_Default(text);
        System.out.println("HanLP分词：默认模式" + def);
        List<String> standard = HanLPUtil.segment_Standard(text);
        System.out.println("HanLP分词：标准模式" + standard);
        List<String> index = HanLPUtil.segment_Index(text);
        System.out.println("HanLP分词：索引模式" + index);
        List<String> nshort = HanLPUtil.segment_NShort(text);
        System.out.println("HanLP分词：NShort模式" + nshort);
        List<String> short_ = HanLPUtil.segment_Short(text);
        System.out.println("HanLP分词：Short模式" + short_);
        List<String> crf = HanLPUtil.segment_CRF(text);
        System.out.println("HanLP分词：CRF模式" + crf);
    }
}
