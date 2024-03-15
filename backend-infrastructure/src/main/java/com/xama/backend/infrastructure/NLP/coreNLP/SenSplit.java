package com.xama.backend.infrastructure.NLP.coreNLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;

public class SenSplit {
    private ArrayList<String> sensRes = new ArrayList<String>();

    public ArrayList<String> getSensRes() {
        return sensRes;   //返回存储句子的数组(ArrayList类型)
    }
    public SenSplit(String text){
        CoreNLPHel coreNLPHel = CoreNLPHel.getInstance();
        StanfordCoreNLP pipeline = coreNLPHel.getPipeline();
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap setence:sentences){
            sensRes.add(setence.get(CoreAnnotations.TextAnnotation.class));
        }
    }
}
