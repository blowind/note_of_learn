package tup.lucene.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import tup.lucene.ik.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName: DeleteIndex
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/12 23:16
 * @Version: 1.0
 **/
public class DeleteIndex {
    public static void main(String[] args) {
        deleteDoc("title", "美国");
    }

    public static void deleteDoc(String field, String key) {
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        Path path = Paths.get("indexdir");
        Directory dir;
        try{
            dir = FSDirectory.open(path);
            IndexWriter writer = new IndexWriter(dir, config);
            writer.deleteDocuments(new Term(field, key));
            writer.commit();
            writer.close();
            System.out.println("删除成功");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
