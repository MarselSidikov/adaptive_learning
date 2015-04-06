package ru.kpfu.ivmiit.learning.tools.models;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.*;
/**
 * @author Miftakhutdinov Zulfat (Kazan Federal University)
 *
 */
public class Result {
    private String Res;
    private int studentId;
    private int lessonId;
    private double absComplexity;
    private double relComplexity;
    private double totalComplexity;
    public int getStudentId (){return studentId;}
    public int getLessonId () {return  lessonId;}


    public Map<Integer,List<String>> getResults() {
        Map<Integer,List<String>> resultsDictionary = new HashMap<Integer, List<String>>();
        String [] answersArray = Res.split(";");
        int questionId;
        String isCorrect;
        String questionComplexity;
        List<String> arr;
        for (String ans:answersArray) {
            questionId = Integer.parseInt(ans.split(":")[0]);
            isCorrect = ans.split(":")[1];
            questionComplexity = ans.split(":")[2];
            arr = new LinkedList<String>();
            arr.add(isCorrect);
            arr.add(questionComplexity);
            resultsDictionary.put(questionId,arr);
        }
        return resultsDictionary;
    }
    public Result(String res,int studentId,int lessonId) {
        boolean isCorrect;
        double complexity=0;
        this.Res = res;
        this.studentId = studentId;
        this.lessonId = lessonId;
        Map<Integer, List<String>> r = this.getResults();
        for (Map.Entry<Integer, List<String>> questionID : r.entrySet()) {
            isCorrect = Boolean.parseBoolean(questionID.getValue().get(0));
            complexity = Double.parseDouble(questionID.getValue().get(1));
            totalComplexity += complexity;
            if (isCorrect)
                absComplexity += complexity;
        }
        relComplexity = absComplexity/totalComplexity;
    }
    public double getAbsComplexity() {
        return absComplexity;
    }
    public double getRelComplexity () {
        return relComplexity;
    }
}