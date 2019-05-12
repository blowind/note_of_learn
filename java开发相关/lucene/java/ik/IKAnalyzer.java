package tup.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @ClassName: IKAnalyzer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/11 18:58
 * @Version: 1.0
 **/
public class IKAnalyzer extends Analyzer {
    private boolean useSmart;
    private boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer() {
        // IK分词器接口实现，false表示使用细粒度切分算法
        this(false);
    }

    public IKAnalyzer(boolean useSmart) {
        super();
        // useSmart为false表示使用细粒度切分算法，true表示使用智能切分算法
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer _IKTokenizer = new IKTokenizer(this.useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }
}
