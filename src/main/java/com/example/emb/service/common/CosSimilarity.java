package com.example.emb.service.common;

import org.apache.catalina.LifecycleState;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 余弦算法计算作业的重复率
 */

@Service
public class CosSimilarity {

    double calculateCosineSimilarity(String text1, List<Map<CharSequence,Integer>> mapList){
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        Double rate=0.0;
        Map<CharSequence,Integer> map0=getTermFrequencyMap(text1);
        //对所有的取平均值
        for(Map<CharSequence,Integer> mapX : mapList){
            rate+=cosineSimilarity.cosineSimilarity(map0,mapX);
        }

        //除去自己的那部分
        return (rate-1)/(mapList.size()-1);
    }


    private static List<Map<CharSequence,Integer>> getAllTermFrequencyMap(List<String> list){
        List<Map<CharSequence,Integer>> mapList=new ArrayList<>();
        for(String test:list)
            mapList.add(getTermFrequencyMap(test));
        return mapList;
    }

    //todo
    //这个函数需要做修改，我需要改掉他的单词划分
    //去掉http的头，还有md的格式用语，不然的话问题很大
    private static Map<CharSequence, Integer> getTermFrequencyMap(String text) {
        Map<CharSequence, Integer> frequencyMap = new HashMap<>();
        String[] terms = text.split("\\s+");

        for (String term : terms) {
            frequencyMap.put(term, frequencyMap.getOrDefault(term, 0) + 1);
        }

        return frequencyMap;
    }
}
