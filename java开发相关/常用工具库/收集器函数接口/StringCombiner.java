import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author zhangxuefeng
 * @version 1.0.0
 * @description
 * @date 2018/06/11 16:39
 */
public class StringCombiner {


	private String prefix;
	private String delim;
	private String suffix;
	private StringBuilder builder;

	public StringCombiner(String delim, String prefix, String suffix) {
		this.prefix = prefix;
		this.delim = delim;
		this.suffix = suffix;
		this.builder = new StringBuilder();
	}


	public StringCombiner add(String element) {
		if(!areAtStart()) {
			builder.append(delim);
		}
		builder.append(element);
		return this;
	}

	public StringCombiner merge(StringCombiner other) {
		if(!other.equals(this)) {
			if(!other.areAtStart() && this.areAtStart()) {
				other.builder.insert(0, this.delim);
			}
			this.builder.append(other.builder);
		}
		return this;
	}

	@Override
	public String toString() {
		return prefix + builder.toString() + suffix;
	}

	private boolean areAtStart() {
		return builder.length() == 0;
	}


}
