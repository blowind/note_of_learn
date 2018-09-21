public class IDCardParity {

	private Map<Integer, String> map = new HashMap<Integer, String>() {
		{
			put(0, "1");
			put(1, "0");
			put(2, "X");
			put(3, "9");
			put(4, "8");
			put(5, "7");
			put(6, "6");
			put(7, "5");
			put(8, "4");
			put(9, "3");
			put(10, "2");
		}
	};
	
	public static void main(String[] argv) {
		String numberBeforeParity = "35020119781102313";
		List<String> tmp = Arrays.asList(numberBeforeParity.split(""));
		List<Integer> cc = tmp.stream().map(Integer::valueOf).collect(Collectors.toList());
		List<Integer> weightPerDigit = Arrays.asList(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2);
		int sum = 0;
		for(int i=0; i< cc.size(); i++) {
			sum += cc.get(i)* weightPerDigit.get(i);
		}
		System.out.println(sum);

		int mod = sum % 11;
		System.out.println(mod);
		System.out.println("result: " + map.get(mod));

	}
}