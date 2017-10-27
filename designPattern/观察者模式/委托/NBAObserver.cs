class NBAObserver
{
	private string name;
	private Subject sub;
	
	public NBAObserver(string name, Subject sub)
	{
		this.name = name;
		this.sub = sub;
	}
	
	public void CloseNBADirectSeeding()
	{
		Console.WriteLine("{0} {1} �ر�NBAֱ��������������", sub.SubjectState, name);
	}
}