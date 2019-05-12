package tup.lucene.analyzer;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import tup.lucene.ik.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * @ClassName: VariousAnalyzers
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/11 18:58
 * @Version: 1.0
 **/
public class VariousAnalyzers {
//    private static final String str = "中华人民共和国简称中国， 是一个有13亿人口的国家";
    private static final String str = "公路局正在治理解放大道路面积水问题";

    public static void printAnalyzer(Analyzer analyzer) throws IOException {
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        // 清空流
        tokenStream.reset();
        CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(termAttribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }

    public static void wrapper(Analyzer analyzer, String description) throws IOException {
        System.out.println(description + analyzer.getClass());
        printAnalyzer(analyzer);
    }

    public static void main(String[] args) throws IOException {
        // 标准分词
        // 中|华|人|民|共|和|国|简|称|中|国|是|一|个|有|13|亿|人|口|的|国|家|
        wrapper(new StandardAnalyzer(), "标准分词: ");

        // 空格分词
        // 中华人民共和国简称中国，|是一个有13亿人口的国家|
        wrapper(new WhitespaceAnalyzer(), "空格分词: ");

        // 简单分词
        // 中华人民共和国简称中国|是一个有|亿人口的国家|
        wrapper(new SimpleAnalyzer(), "简单分词: ");

        // 二分法分词
        // 中华|华人|人民|民共|共和|和国|国简|简称|称中|中国|是一|一个|个有|13|亿人|人口|口的|的国|国家|
        wrapper(new CJKAnalyzer(), "二分法分词: ");

        // 关键字分词
        // 中华人民共和国简称中国， 是一个有13亿人口的国家|
        wrapper(new KeywordAnalyzer(), "关键字分词: ");

        // 停用词分词
//        wrapper(new StopAnalyzer(), "停用词分词: ");

        // 中文智能分词
        // 中华人民共和国|简称|中国|是|一个|有|13|亿|人口|的|国家|
        wrapper(new SmartChineseAnalyzer(), "中文智能分词: ");

        wrapper(new IKAnalyzer(true), "自定义IKAnalyzer分词: ");
    }
}
