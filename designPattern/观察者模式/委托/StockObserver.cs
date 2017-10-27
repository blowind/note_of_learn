class StockObserver
{
	private string name;
	private Subject sub;
	
	public StockObserver(string name, Subject sub)
	{
		this.name = name;
		this.sub = sub;
	}
	
	public void CloseStockMarket()
	{
		Console.WriteLine("{0} {1} �رչ�Ʊ���飬����������", sub.SubjectState, name);
	}
}