interface Subject
{
	void Notify();
	
	string SubjectState
	{
		get;
		set;
	}
}

delegate void EventHandler();   // ����һ��ί�У����ƽС�EventHandler���¼�������򣩡����޲������޷���ֵ

class Boss: Subject
{
	// ������EventHandler���¼�������򣩡���ί���¼������ƽС�Update��
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
