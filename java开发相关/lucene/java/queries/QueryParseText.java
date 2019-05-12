package tup.lucene.queries;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import tup.lucene.ik.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName: QueryParseText
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/13 0:36
 * @Version: 1.0
 **/
public class QueryParseText {
    public static void main(String[] args) throws ParseException, IOException {
        String field = "title";
        Path path = Paths.get("indexdir");
        Directory dir = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer();
        QueryParser parser = new QueryParser(field, analyzer);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse("农村学生");
        System.out.println("Query: " + query.toString());

        TopDocs tds = searcher.search(query, 10);
        for(ScoreDoc sd : tds.scoreDocs) {
            Document doc = searcher.doc(sd.doc);
            System.out.println("DocId: " + sd.doc);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
            System.out.println("文档评分: " + sd.score);
        }
    }
}
