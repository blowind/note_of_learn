class Program
{
	static void Main(string[] args)
	{
		Boss huhansan = new Boss();
		
		StockObserver employer1 = new StockObserver("zxf", huhansan);
		NBAObserver employer2 = new NBAObserver("ssy", huhansan);
		
		huhansan.Update += new EventHandler(employer1.CloseStockMarket);
		huhansan.Update += new EventHandler(employer2.CloseNBADirectSeeding);
		
		huhansan.SubjectState = "huhansan is back!";
		huhansan.Notify();
	}
}
	