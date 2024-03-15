package com.xama.backend.infrastructure.NLP.coreNLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;

public class PosTag {
    private String postext = "";

    public String getPostext() {
        return postext;
    }
    public PosTag(String text){

        CoreNLPHel coreNLPHel = CoreNLPHel.getInstance();
        StanfordCoreNLP pipeline = coreNLPHel.getPipeline();
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuffer sb = new StringBuffer();
        for (CoreMap sentence:sentences){
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                sb.append(word);
                sb.append("/");
                sb.append(pos);
                sb.append(" ");
            }
        }
        postext = sb.toString().trim();
    }
}
