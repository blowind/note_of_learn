import java.util.Arrays;
import java.util.Collections;
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
 * @description  实现Collector.joining功能，注意两个类不能合并，否则报错
 * @date 2018/06/11 17:30
 */
public class StringCollector  implements Collector<String, StringCombiner, String> {

	private static final Set<Characteristics> characteristics = Collections.emptySet();

	private final String delim;
	private final String prefix;
	private final String suffix;

	public StringCollector(String delim, String prefix, String suffix) {
		this.delim = delim;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public Supplier<StringCombiner> supplier() {
		return () -> new StringCombiner(delim, prefix, suffix);
	}

	@Override
	public BiConsumer<StringCombiner, String> accumulator() {
		return StringCombiner::add;
	}

	@Override
	public BinaryOperator<StringCombiner> combiner() {
		return StringCombiner::merge;
	}

	@Override
	public Function<StringCombiner, String> finisher() {
		return StringCombiner::toString;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return characteristics;
	}

	public static void main(String[] argv) {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		String ret = list.stream().collect(new StringCollector(", ", "[", "]"));
		System.out.println(ret);
	}
}
