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
		Console.WriteLine("{0} {1} 关闭股票行情，继续工作！", sub.SubjectState, name);
	}
}