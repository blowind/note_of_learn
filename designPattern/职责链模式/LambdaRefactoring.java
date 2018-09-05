
public class LambdaRefactoring {
	public static void main(String[] argv) {
		UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
		
		UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
		
		Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
		
		String result = pipeline.apply("Aren't labdas really sexy?!!")
	}
}