package com.xama.backend.infrastructure.NLP.coreNLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;

public class NamedEntity {
    private String nertext = "";

    public String getNertext() {
        return nertext;
    }

    public NamedEntity(String text){
        CoreNLPHel coreNLPHel = CoreNLPHel.getInstance();
        StanfordCoreNLP pipeline = coreNLPHel.getPipeline();
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuffer sb = new StringBuffer();
        for (CoreMap sentence:sentences){
            // 获取句子的token（可以是作为分词后的词语）
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                //String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                //String ne = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                //System.out.println(word + "\t" + pos + " | analysis : {  original : " + ner + "," + " normalized : " + ne + "}");
                sb.append(word);
                sb.append("/");
                sb.append(ner);
                sb.append(" ");
            }
        }
        nertext = sb.toString().trim();
    }
}
