package com.xama.backend.infrastructure.NLP.coreNLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;

public class SPTree {
    List<CoreMap> sentences;

    public SPTree(String text){
        CoreNLPHel coreNLPHel = CoreNLPHel.getInstance();
        StanfordCoreNLP pipeline = coreNLPHel.getPipeline();
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    }

    //句子的依赖图(依存分析)
    public String getDepprasetext() {
        StringBuffer sb2 = new StringBuffer();
        for (CoreMap sentence:sentences){
            String sentext = sentence.get(CoreAnnotations.TextAnnotation.class);
            SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            //System.out.println("句子的依赖图");
            sb2.append(sentext);
            sb2.append("\n");
            sb2.append(graph.toString(SemanticGraph.OutputFormat.LIST));
            sb2.append("\n");
        }
        return sb2.toString().trim();
    }
    // 句子的解析树
    public String getPrasetext() {
        StringBuffer sb1 = new StringBuffer();
        for (CoreMap sentence:sentences){
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            String sentext = sentence.get(CoreAnnotations.TextAnnotation.class);
            sb1.append(sentext);
            sb1.append("/");
            sb1.append(tree.toString());
            sb1.append("\n");
        }
        return sb1.toString().trim();
    }
}
