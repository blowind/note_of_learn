///     ����ģʽ(Singleton Pattern)������ģʽȷ��������һ�������ֻ��һ��ʵ����
//      �����ڳ����л�������������磺�̳߳أ����棬�Ի��򣬴�ӡ�����Կ����豸��������
//      ��Щ�����ֻ����һ��ʵ�������������ʵ�����ͻᵼ��������������
class Singleton {
private:
	static Singleton *unique;      //  ���Ա�����Գ�ʼ��
	Singleton() {                   //  ���캯��˽�л��������ⲿ�Ͳ�����newһ����ʵ����ֻ���ڲ�����
		base = 1;                           //  �ڹ��캯�����ʼ��������ʻ�߼�����Ĺ��ܱ���
		cout << "consturct singleton" << endl;
	} 
	~Singleton() {                     //   ��ֹ���ɾ������ʵ�������Լ�����ɾ��
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);       //  ���ƹ��캯��������������ֹ�ⲿ���и���
	Singleton& operator=(const Singleton&);   //  =���غ���������������ֹ�ⲿ��ֵ  singleton *p = ��д�������Ϸ���

	int base;                          //  �ڲ����������߼�����Ĺ��ܱ���

public:
	static Singleton& getInstance() {     //  Ψһ���ʵ���ķ�����ÿ��Ҫȡ��ʵ������ͨ
		cout << "new singleton" << endl;
		if(unique == NULL) {              //  �˷���ͨ���жϱ�֤��һ��ʵ��
			unique = new Singleton();
		}
		return *unique;         //  ���뷵��һ��ʵ�������ã���Ȼ�ⲿ�޷�������ʵ�����Ӷ��޷�ʹ�����ʵ��
	}
	static void release() {            //   ��Ӧ���ɺ�������Ȼ�������������Ȼ�ɶԳ��֣�ͨ������ӵ�����������
		cout << "release singleton" << endl;
		if(unique != NULL) {
			delete unique;        //  ������������
			unique = NULL;        //   �������¸��գ������������ָ�룬�ⲿ��ε���release����ʱ��������Ԥ�ϴ���
		}
	}
	int workingFunc(int i) {       //  ���ܺ��������ڴ����ڲ��������߼�
		return base += i;
	}
};

Singleton* Singleton::unique = NULL;    //  ������ȫ�ֳ�ʼ����˽�С���̬����������getInstance�ڲ��߼��޷��жϣ���������
int main() {
	Singleton::getInstance();        //  ���ù��캯��ʵ����Ψһһ������
	Singleton::getInstance();        //  ֻ�����жϣ������ù��캯��
	cout << Singleton::getInstance().workingFunc(3) << endl;    //  ͨ��getInstance��ö�����������߼�����
	cout << Singleton::getInstance().workingFunc(8) << endl;
	Singleton::release();           //  ��ӵ�����������
	Singleton::release();           //  ��Ϊ���������ָ�����⣬��ε����޺�
	getchar();
}
//  ����ÿ�ζ���ͨ���ж�unique == NULL��ȷʵ�Ƿ�Ҫʵ�������ⱻ��Ϊ�ӳ�ʵ��������Ӧ����ģʽ��
//  "����ģʽ"�޷��ڶ��̻߳��������У��������߳�ǰ����Ⱥ����е�unique == NULLʱ�������ж϶�Ϊtrue���Ӷ�new������
//  �������"����ģʽ"������ģʽ�����Ƿ���ø����ʵ�����ڳ���ʼʱ�ͻ����һ�������ʵ���������Ժ󷵻ش�ʵ��
//  ͨ�������ڲ���̬���������Ǿ�̬����ָ����ʵ�֣��ɾ�̬��ʼ��ʵ����֤���̰߳�ȫ��
//  ��Ϊ��̬ʵ����ʼ���ڳ���ʼʱ����������֮ǰ�������߳��Ե��̷߳�ʽ����˳�ʼ�������ص��Ĺ�����߳�����
//  ע�⵽����ģʽû�о�̬�����ͷţ��ͷ��ǿ�����������ϵͳ���ͷ�

class Singleton {
private:
	static Singleton m_Instance;      //  �ؼ����ڣ�����Ϊһ����̬��Ա������������һ��ָ��

	Singleton() {
		cout << "construct singleton" << endl;
		base = 1;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);
	Singleton& operator=(const Singleton&);

	int base;

public:
	static Singleton* getInstance() {
		return &m_Instance;
	}
	int workingFunc(int i) {
		return base += i;
	}
};
Singleton Singleton::m_Instance;  //  �ڳ���ʼǰʵ������̬��������ʱ�Ǳ���������ָ��
int main() {
	Singleton::getInstance();     //  ��ʱ��ʵ������ֱ�ӷ���ָ��
	Singleton::getInstance();     //  ��ʱ��ʵ������ֱ�ӷ���ָ��
	cout << Singleton::getInstance()->workingFunc(3) << endl;
	cout << Singleton::getInstance()->workingFunc(8) << endl;
	getchar();
}
//   ����ģʽ������Ҳ������, �������Ƕ�̬��, ����̬��Ա������ʼ��˳����û��֤�����磺
//   ����������ģʽ���� ASingleton �� BSingleton, ĳ�������� BSingleton �Ĺ��캯����ʹ�� ASingleton ʵ��
//   ��Ϊ BSingleton m_pInstance ��̬��������� ASingleton һ�����ó�ʼ�����캯��
//   ��� ASingleton::Instance() ���صľ���һ��δ��ʼ�����ڴ�����, ����û�ܾ�ֱ�ӱ���.
class Singleton {
private:
	static Singleton* p_Instance;      //  ����һ��ָ�룬����Ҫʱʵ����

	Singleton() {
		cout << "construct singleton" << endl;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);            //  ȥ���ⲿ���ƹ���
	Singleton& operator=(const Singleton&);      //  ȥ���ⲿ�� = ����

public:

	Singleton& getInstance() {
		if(NULL == p_Instance) {        //  �ж��費��Ҫ��������Ϊ�����Ǹ����ĺܴ�Ĳ���
			Lock();                     //  ��������������ʵ�֣�����boost
			if(NULL == p_Instance) {    //     �˴��жϲ����٣���Ϊ�����߳̿���ͬʱ�����һ���жϣ����˵û�����new����
				p_Instance = new Singleton();
				//  �˴�ʵ�ʻ��ǿ��ܷ����̰߳�ȫ���⣬ԭ������ʵ������仰ִ������������
				//  1�������ڴ�   2�����ڴ�λ�õ��ù��캯��   3�����ڴ�ĵ�ַ��ֵ��p_Instance
				// ����2��3��˳����Եߵ����������CPU������ִ�н����˵ߵ����������ж�ʱ�Ѿ��ǿգ���ʵ�ʹ��캯��δִ��
				//  ��ʱʹ�÷��صĹ���ʵ�����Ͳ�֪���ᷢ��ʲô��
				//  �Ľ�����������һ��CPU�ṩ��barrierָ���ָ����֯CPU��֮ǰ��ָ�����barrier֮��
				//  Singleton* temp = new Singleton();
				//  barrier();
				//  p_Instance = temp;
			}
			Unlock();                   //   ����������ʵ��
		}
		return *p_Instance;
	}
	//     ����������������Ϊ�麯������������������಻���ø�������������������¸����ڴ�й¶
	//     ��һ���ڴ�й¶����ǣ�����ָ��ָ�������ʵ����delete��ָ���ʱ�򣬲������������������������й¶
	virtual void release() {       //  ��дһ�������Ҳ���ԣ��õ��ϵͳ���ڳ����˳�ʱ�Զ��ͷ�ռ�õ�ϵͳ��Դ
		if(NULL != p_Instance) {            
			Lock();
			if(NULL != p_Instance) {
				delete p_Instance;     //  ������ʱ��Ҳ��Ҫ����������ǰ���ִ����һ����ʱ�򣬺�һ��ִ���ͷ�����ָ��
				p_Instance = NULL;
			}
			Unlock();
		}
	}
};
Singleton* Singleton::p_Instance = NULL;
//   �����������ʱ�����ϵļ�������������Ϊ����ƿ��
//   C++ 0x �Ժ�Ҫ���������֤�ڲ���̬�������̰߳�ȫ�ԣ����Բ���������C++ 0x ��ǰ���������
class Singleton {
private:

	Singleton() {
		cout << "construct singleton" << endl;
		base = 1;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);
	Singleton& operator=(const Singleton&);

	int base;

public:
	static Singleton& getInstance() {
		//	 ��̬���������Ա�֤��ֻ�ڵ�һ�ε���ʱ����ʼ��
		//   �ﵽ��̬��ʼ��Ч����ͬʱ��֤�˳�Ա������singleton����ĳ�ʼ��˳��
		static Singleton m_Instance;      //  �ؼ����ڣ��ھ�̬�����������ľ�̬������������֤���̰߳�ȫ��
		return m_Instance;      //  �������ñ�֤�˵����߲���Ҫ��鷵��ֵ����Ч��
	}
	int workingFunc(int i) {
		return base += i;
	}
};
int main() {
	//Singleton s = Singleton::getInstance();    // ��������Ҫ���ÿ������캯����
	Singleton& s = Singleton::getInstance();     //   ʹ�����õĻ�����û������
	Singleton::getInstance();
}


///  ���̵߳��µĲ�����ִ��
//   �����뺯����һ����������Ϊ������ģ������ú���������֮�󲻻�����κβ������
//   һ������Ҫ����������������1������߳�ͬʱִ���������   2����������ֱ�ӻ��߼�ӵ�������
int sqr(int x) {        //  �����뺯��������
	return x*x;
}
�����뺯�����ص㣺
1����ʹ���κξ�̬��ȫ�ֵķ�const����
2���������κξ�̬��ȫ�ֵķ�const����
3�����������÷��ṩ�Ĳ���
4���������κε�����Դ����(mutex��)
5���������κβ�������ĺ���

�����������Ż����µ��̲߳���ȫ�ԣ����磺
 x = 0;
 Thread1   Thread2
 lock();   lock();
 x++;      x++;
 unlock(); unlock();
 ò�������̼߳���֮��x++�Ƕ����ģ�ʵ���Ͽ�����Ϊ������Ϊ�����x�����ٶȣ���x����ĳ���Ĵ�����ÿ�ζ���ӼĴ�������룬
 ��Thread2����ִ�У�д��x[2]=1 ֮��ܾã�Thread1���Ĵ�����洢��x[1]=1 ֵд��(�̵߳ļĴ������໥������)
  �����������ʹ��volatile�ؼ�����ֹ�����Ż�  
 volatileЧ����1����ֹ������������������ӼĴ���д��  2����ֹ����������volatile������ָ��˳��
 ����2��   
 x=y=0;
 Thread1   Thread2
 x=1;      y=1;
 r1=y;     r2=x;
 �������r1��r2������ͬʱΪ0��������CPU�Ķ�̬���ȣ�Ϊ�����Ч�ʽ�����������ص�����ָ���ָ����ܱ�������
 x=y=0;
 Thread1   Thread2
 r1=y;     y=1;
 x=1;      r2=x;
�˴�������CPU���Ż������Ա�������ȫ����Ϊ����ֻ�ܿ����Ƿ����ֶ�����CPU

��һ�����Ӳο�singleton��˫�ж�ģʽ