package tup.lucene.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import tup.lucene.ik.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName: UpdateIndex
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/12 23:21
 * @Version: 1.0
 **/
public class UpdateIndex {
    public static void main(String[] args) {
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        Path path = Paths.get("indexdir");
        Directory dir;
        try{
            dir = FSDirectory.open(path);
            IndexWriter writer = new IndexWriter(dir, config);
            Document doc = new Document();
            doc.add(new TextField("id", "2", Field.Store.YES));
            doc.add(new TextField("title", "北京大学开学迎来4380名新生", Field.Store.YES));
            doc.add(new TextField("content", "昨天，北京大学迎来4380名来自全国各地及数十个国家的本科新生。其中，农村学生共700余名，为及呢喃来最多...", Field.Store.YES));
            writer.updateDocument(new Term("title", "北大"), doc);
            writer.commit();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
