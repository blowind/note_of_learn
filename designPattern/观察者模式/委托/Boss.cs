interface Subject
{
	void Notify();
	
	string SubjectState
	{
		get;
		set;
	}
}

delegate void EventHandler();   // 声明一个委托，名称叫“EventHandler（事件处理程序）”，无参数，无返回值

class Boss: Subject
{
	// 声明“EventHandler（事件处理程序）”的委托事件，名称叫“Update”
	public event EventHandler Update;
	
	private string action;
	
	public void Notify()
	{
		Update();  //  
	}
	
	public string SubjectState
	{
		get { return action; }
		set { action = value; }
	}
}
