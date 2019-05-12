package tup.lucene.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * @ClassName: IKTokenizer
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/5/11 18:59
 * @Version: 1.0
 **/
public class IKTokenizer extends Tokenizer {
    // IK分词器实现
    private IKSegmenter _IKImplement;
    // 词元文本属性
    private final CharTermAttribute termAttribute;
    // 词元位移属性
    private final OffsetAttribute offsetAttribute;
    // 词元分类属性
    private final TypeAttribute typeAttribute;
    // 记录最后一个词元的结束位置
    private int endPosition;

    public IKTokenizer(boolean useSmart) {
        super();
        offsetAttribute = addAttribute(OffsetAttribute.class);
        termAttribute = addAttribute(CharTermAttribute.class);
        typeAttribute = addAttribute(TypeAttribute.class);
        _IKImplement = new IKSegmenter(input, useSmart);
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();  // 清除所有词元属性
        Lexeme nextLexeme = _IKImplement.next();
        if(nextLexeme != null) {
            termAttribute.append(nextLexeme.getLexemeText());
            termAttribute.setLength(nextLexeme.getLength());
            offsetAttribute.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            endPosition = nextLexeme.getEndPosition();
            typeAttribute.setType(nextLexeme.getLexemeText());
            return true;  // 返回true表明还有下一个词元
        }
        return false;  // 表明词元输出完毕
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        _IKImplement.reset(input);
    }

    @Override
    public final void end() {
        int finalOffset = correctOffset(this.endPosition);
        offsetAttribute.setOffset(finalOffset, finalOffset);
    }
}
