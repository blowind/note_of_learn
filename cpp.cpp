extern��أ�
extern int i;        //   ����һ����������δ����
extern�����ں����ⲿʱ���ſ��Ժ��г�ʼ��ʽ����ʱ��������������
extern double pi = 3.1415   //  �Ϸ����Ѷ��岢��ʼ��

�ĵã�extern���������ͷ�ļ�a.h�У�Ȼ���ڶ�Ӧ��a.cpp�ļ��ж��壨���԰�����ֵ���ñ�������Ԥ�����ʱ�����а�����ͷ�ļ���a.cpp, b.cpp���ļ������ͷ�ļ����Ƶ���cpp�ļ��У����������ͷ�ļ��ж����������ͷ�ļ�չ��֮�����a.cpp��b.cpp�ж������ñ������壬�ظ����嵼�±�����󡣵����ֻ�Ƿ���������ͷ�ļ�չ��֮���Ƶ�ֻ����������Ϊ�������Զ�Σ���������Զֻ��ԭ����a.cpp�ļ��У����Ա���ɹ������⣬���b.cpp�������ͷ�ļ�a.h������ʹ��a.cpp�ļ��еı��� int i ����ֱ�ӰѸñ�����extern��ʽ���� extern int i; ����b.cpp����ʱ�൱�ڲ�Ԥ����ʱ��չͷ�ļ��Ĳ����ֶ����

extern "C" {   //  ���߱������ڲ�������C���Է�����Σ�������ǰ���_
	int func(int);
	int var;
}
����
extern "C" int var;

#ifdef __cplusplus          //  ͬһ���ļ�����C����������C++������ʱ���������������İ취
extern "C" {
#endif

void *memset (void *, int, size_t);

#ifdef __cplusplus	
}
#endif


register��أ�
register int a;     //  �Ĵ���������û�е�ַ��.��Ϊ�Ĵ��������������ڴ���.������CPU�еļĴ�����.
register�ؼ�����c���Ժ�c++�еĲ��� 
��c++�У�
(1)register �ؼ����޷���ȫ���ж������������ᱻ��ʾΪ����ȷ�Ĵ洢�ࡣ
(2)register �ؼ����ھֲ�������������ʱ�������� & ������ȡ��ַ��һ��ʹ����ȡ��ַ��������������ı�����ǿ�ƴ�����ڴ���
��c��:
(1)register �ؼ��ֿ�����ȫ���ж�����������������ʹ�� & ������ʱ��ֻ�Ǿ��桰�л��Ĵ洢�ࡱ��
(2)register �ؼ��ֿ����ھֲ������������������������޷�����ʹ�� & ��������������벻ͨ����
���鲻Ҫ��register�ؼ��ֶ���ȫ�ֱ�������Ϊȫ�ֱ��������������Ǵ�ִ�г���ʼ��һֱ����������Ż���ֹ����register�������ܻ�����cpu�ļĴ����У�����ڳ�����������������ڶ�ռ���żĴ����Ļ������Ǹ��൱���õľٴ롣
��register�ؼ������εı�������C�������ǲ�������&������ȡ��ַ�ģ����������еľ��顣
��Ϊ��������������˳���Ա�Ľ���ѱ�������Ĵ��������ǲ����������ַ�ġ�����C++�У���register���εı���������&������ȡ��ַ����������һ�δ����з��ֵġ������������ʽȡ��register�����ĵ�ַ��������һ���Ὣ��������������ڴ��У������ᶨ��Ϊ�Ĵ���������


volatile��أ�
volatile int i = 10; 
volatile �ؼ�����һ���������η����������������ͱ�����ʾ���Ա�ĳЩ������δ֪�����ظ��ġ�
���磺����ϵͳ��Ӳ�����������̵߳ȡ���������ؼ��������ı������������Է��ʸñ����Ĵ���Ͳ��ٽ����Ż����Ӷ������ṩ�������ַ���ȶ����ʡ�����ʱ�﷨��int volatile vInt; ��Ҫ��ʹ�� volatile �����ı�����ֵ��ʱ��ϵͳ�������´������ڵ��ڴ��ȡ���ݣ���ʹ��ǰ���ָ��ոմӸô���ȡ�����ݡ����Ҷ�ȡ���������̱����档 
volatileָ��i����ʱ���ܷ����仯�ģ�ÿ��ʹ������ʱ������i�ĵ�ַ�ж�ȡ��������������ɵĻ���������´�i�ĵ�ַ��ȡ���ݷ��� b �С����Ż������ǣ����ڱ������������δ� i�����ݵĴ���֮��Ĵ���û�ж�i���й������������Զ����ϴζ������ݷ��� b �С����������´� i �������������������� i��һ���Ĵ����������߱�ʾһ���˿����ݾ����׳�������˵ volatile ���Ա�֤�������ַ���ȶ����ʡ�
#include <stdio.h>
void main() {    // VS��debugģʽ�¶����10��releaseģʽ�����10��32
	volatile int i = 10;
	int a = i;
	printf("i = %d\n", a);
	// �������������þ��Ǹı��ڴ��� i ��ֵ
	// �����ֲ��ñ�����֪��
	__asm
	{
		MOV DWORD PTR[EBP - 4], 20H
	}
	int b = i;
	printf("i = %d\n", b);
}
��ʵ��ֻ�ǡ���Ƕ������ջ�����ַ�ʽ���ڱ����޷�ʶ��ı����ı䣬�������Ŀ����Ƕ��̲߳������ʹ������ʱ��һ���̸߳ı��˱�����ֵ�������øı���ֵ�������߳� visible��һ��˵����volatile �������µļ����ط���
1) �жϷ���������޸ĵĹ�����������ı�����Ҫ�� volatile��
2) �����񻷾��¸�����乲��ı�־Ӧ�ü� volatile��
3) �洢��ӳ���Ӳ���Ĵ���ͨ��ҲҪ�� volatile ˵������Ϊÿ�ζ����Ķ�д�������ɲ�ͬ���塣
volatile ָ�룺
�� const ���δ����ƣ�const �г���ָ���ָ�볣����˵����volatile Ҳ����Ӧ�ĸ��
������ָ��ָ��Ķ��������� const �� volatile �ģ�
const char* cpch;
volatile char* vpch;
ָ�������ֵ����һ�������ַ�������������� const �� volatile �ģ�
char* const pchc;
char* volatile pchv;
ע�⣺
(1) ���԰�һ���� volatile int ���� volatile int�����ǲ��ܰѷ� volatile ���󸳸�һ�� volatile ����
(2) ���˻��������⣬���û���������Ҳ������ volatile ���ͽ������Ρ�
(3) C++ ��һ���� volatile ��ʶ������ֻ�ܷ������ӿڵ��Ӽ���һ�������ʵ���߿��Ƶ��Ӽ����û�ֻ���� const_cast ����ö����ͽӿڵ���ȫ���ʡ����⣬  volatile �� const һ������ഫ�ݵ����ĳ�Ա��
���߳��µ� volatile ��
��Щ��������volatile�ؼ��������ġ��������̶߳�Ҫ�õ�ĳһ�������Ҹñ�����ֵ�ᱻ�ı�ʱ��Ӧ����volatile�������ùؼ��ֵ������Ƿ�ֹ�Ż��������ѱ������ڴ�װ��CPU�Ĵ����С����������װ��Ĵ�������ô�����߳��п���һ��ʹ���ڴ��еı�����һ��ʹ�üĴ����еı����������ɳ���Ĵ���ִ�С�volatile����˼���ñ�����ÿ�β����ñ���ʱһ��Ҫ���ڴ�������ȡ����������ʹ���Ѿ����ڼĴ����е�ֵ�����£�
volatile BOOL bStop = FALSE; 
(1) ��һ���߳��У�
while( !bStop ) { ... } 
bStop = FALSE; 
return; 
(2) ������һ���߳��У�Ҫ��ֹ������߳�ѭ���� 
bStop = TRUE; 
while( bStop ); //�ȴ�������߳���ֹ
��� bStop ��ʹ�� volatile ��������ô���ѭ������һ����ѭ������Ϊ bStop �Ѿ���ȡ���˼Ĵ����У��Ĵ����� bStop ��ֵ��Զ������ FALSE������ volatile��������ִ��ʱ��ÿ�ξ����ڴ��ж��� bStop ��ֵ���Ͳ�����ѭ���ˡ�
����ؼ����������趨ĳ������Ĵ洢λ�����ڴ��У������ǼĴ����С���Ϊһ��Ķ�����������ܻὫ��Ŀ������ڼĴ��������Լӿ�ָ���ִ���ٶȣ������¶δ����У�        (�򵥵��ٽ���ʵ�֣����������ź������ƣ���������)
... 
int nMyCounter = 0; 
for(; nMyCounter<100;nMyCounter++) 
{ 
... 
} 
... 
�ڴ˶δ����У�nMyCounter �Ŀ������ܴ�ŵ�ĳ���Ĵ����У�ѭ���У��� nMyCounter �Ĳ��Լ��������ǶԴ˼Ĵ����е�ֵ���У��������������жδ���ִ���������Ĳ�����nMyCounter -= 1;��������У��� nMyCounter �ĸı��Ƕ��ڴ��е� nMyCounter ���в��������ǳ���������һ������nMyCounter �ĸı䲻ͬ����


const��أ�
const int bufSize = 512;     //  �Ѷ���ת����һ�������������ʼ����
��ȫ��������������const����(����extern)�Ƕ���ö�����ļ��ľֲ����������ܱ������ļ����ʣ��������ͬ��static
extern const int bufSize = 512;    //  ��������ʼ��һ�����������ļ��з��ʵ�const����
extern const int bufSize;            //  ����bufSize������ʹ������ڵ�ǰ�ļ�������

const������ָ��const���������
const int ival = 1024;
const int &refVal = ival;       //  const���ÿ��Զ�ȡ�������޸�
const int &r = 42;            // �Ϸ���������������ֵ����
int i = 42;
const int &r2 = r + i;       //  �Ϸ�����Ϊ����ʱ���Խ���ֵ��������������ڿ���ȷ����ֵ���Խ�������

int ival = 1024;
int &refVal = ival;         //  ���ã����ñ����ʼ��

typedef int exam_score         //  �������͵�ͬ���

enum open_modes {input, output, append};
enum Points { point2d = 2, point2w,               //�˴�point2w��point3d����3
			  point3d = 3, point3w };
			  
��class�ؼ����������࣬�����ڵ�һ�����ʱ��ǰ���κγ�Ա����ʽָ��Ϊprivate
��struct�ؼ����������࣬�����ڵ�һ�����ʱ��ǰ���κγ�Ա����ʽָ��Ϊpublic


#ifndef SALESITEM_H         //  Ԥ����ΰ���ͬһͷ�ļ�
#define SALESITEM_H
#endif

using std::cin;             // ʹ��cinʱ���ü�std�����ռ�ǰ׺
��ͷ�ļ��У���������ʹ����ȫ�޶��ı�׼�����֣�����������ļ�д

��׼��string����
#include <string>
using std::string;
���ֳ�ʼ����ʽ��
string s1;     // ����Ĭ�Ϲ��캯��
string s2(s1);
string s3("value");
string s4(n, 'c');        //  ��ʼ��Ϊ�ַ�c��n������

sizeof(string);   // sizeof string���ͺ�ʵ���Ĵ�С���ǹ̶���32�ֽڣ����ܸ�ʵ���洢���ַ����ж�󣬲²�����Ӧ����ָ��

string����:
s.empty();    // sΪ�մ�����true
s.size();     //  ����s���ַ��ĸ���
s.c_str();    // ��sת����C����ַ������أ���β����\0��
//      ��������s�Ĳ������ܸı�s��ʹ��������ʧЧ��������ö�ת�����������ǳ����

string::size_type         //  string�������������������������ͣ�������������������������

��׼��vector����
#include <vector>
using std::vector;
���ֳ�ʼ����ʽ��
vector<T> v1;
vector<T> v2(v1);
vector<T> v3(n, i);           //  ����n��ֵΪi��Ԫ��
vector<T> v4(n);              //  n�Ǹ����������庬ֵ��ʼ����Ԫ�ص�n������

vector����:
v.empty();
v.size();
v.push_back(t);              //  ��v��ĩβ���һ��ֵΪt��Ԫ��

vector<int>::iterator iter = ivec.begin();   // ����һ�����������ͣ��������������ʼ��
*iter = 0;                       //  ʹ�ý����ò����������ʵ�������ָ���Ԫ��
iter++;                          //  ����������
vector<string>::const_iterator iter = text.begin();    // ����һ��const_iterator��ֻ��
iter++;                          //    �Ϸ�
////      const_iterator �����ֵ���Ըı䣬�����ܸı�����ָ���Ԫ�ص�ֵ������ֻ������ʱ
const vector<int>::iterator cit = nums.begin(); // citָ������ݿ��Ըı䣬���������ɸ���
//  ���const ���͵ĵ�����û�����壬��Ϊ������������
*cit = 1;                        //    �Ϸ�

const size_t arr_size = 6;
int int_arr[arr_size] = {0, 1, 2, 3, 4, 5};
vector<int> ivec(int_arr, int_arr + arr_size); //ʹ�������ʼ��vector��Ҫ����������β��ַ

vector<int> array;
array.push_back(1);
array.push_back(6);
array.push_back(2);
array.push_back(6);
array.erase( remove( array.begin(), array.end(), 6), array.end());     //  ɾ�������������е�6


��׼��bitset����
#include <bitset>
using std::bitset;
���ֳ�ʼ����ʽ��
bitset<n> b;           //  nλb��ÿλΪ0
bitset<n> b(u);        //  unsigned long��u��һ������
bitset<n> b(s);        //   string����s���е�λ���ĸ���
bitset<n> b(s, pos, n);   //  b��s�д�λ��pos��ʼ��n��λ�ĸ���

bitset<128> bitvec2(0xffff);            // ���ȣ���λ32λΪ1����λ��0
string strval("1100");
bitset<32> bitvec4(strval);             //  0,1λ��Ϊ0��2,3λ��Ϊ1��������λ��0

bitset����:
b.any()        //  b���Ƿ������Ϊ1�Ķ�����λ
b.none()       //  b�в�������Ϊ1�Ķ�����λ
b.count()       //  b����Ϊ1�Ķ�����λ�ĸ���
b.size()            //  b�ж�����λ�ĸ���
b.test[pos]      //  b����pos���Ķ�����λ�Ƿ�Ϊ1
b.set()        //  b�����ж�����λ����Ϊ1
b.set(pos)        //  b��pos���Ķ�����λ��Ϊ1
b.reset()          // b�����ж�����λ��Ϊ0
b.flip()            //  b�����ж�����λȡ��
b.to_ulong()          // b��ͬ���Ķ�����λ����һ��unsigned longֵ
os << b                     //  b��λ�������os��

const unsigned array_size = 3;
int ia[array_size] = {0, 1, 2};          // �Ϸ�

int *pi = 0;            //  ��ʼ��һ����ָ���κζ����ָ��

void *pi  //  ���Ա����κ����Ͷ���ĵ�ַ��������ָ����һ��ֵַ��أ��������
double obj = 3.14;
void *pv = &obj;        //  �Ϸ�

**������ָ��һ��ָ��ָ����һ��ָ��
int ival = 1024;
int *pi = &ival;
int **ppi = &pi;       //  ָ��ָ���ָ��
cout << ival << endl;   // ���1024
cout << *pi << endl;   //  ���1024
cout << **ppi << endl;    // ���ν����ã����1024

ptrdiff_t n = ip2 - ip;  //  ָ���ľ��룬��׼�����ͣ�������ص����ͣ�cstddef�ж���,signed����

// ָ��const���͵�ָ��          !!!!!!!!!!!!!!!!!!!!!
const double *cptr;      // cptrֻ��ָ��const double���ͣ���cptr�����ǿ��޸ĵ�
//   const�޶���cptrָ����ָ��Ķ������ͣ�����cptr������ָ�������Ϊconst�������޸�
//   ���Ը�cptr���¸�ֵ��ʹ��ָ����һ��const����
//   ���������ݲ����޸ĵ��β�
const double pi = 3.14;
const double *cptr = &pi;     //  �Ϸ�
const int universe = 42;
const void *cpv = &universe;    // �Ϸ�������ʹ��const void*����ָ�뱣��const�����ַ

���ܰ�const����ĵ�ַ������ͨ��ָ�룬���ǿ��԰ѷ�const����ĵ�ַ��ֵ��ָ��const�����ָ��
��ʱ����ͨ�����ָ���޸�ָ��ĵ�ַ������

���ܱ�ָ֤��const��ָ����ָ�����ֵһ�������޸ģ���Ϊָ��const��ָ�����ָ���const����

//  constָ��
int errNumb = 0;
int *const curErr = &errNumb;    // �����Ҷ�����ָ��int�Ͷ����constָ��
//  constָ���ֵ�����޸ģ����Ա����ڶ���ʱ��ʼ��
//  constָ��ָ��������Ƿ���޸���ȫ��ָ��Ķ����Ƿ���const

const double pi = 3.1415;
const double *const pi_ptr = &pi;     //  ָ��const�����constָ��

string s;
typedef string *pstring;        //   pstring ��ָ��string��ָ��
const pstring cstr1 = &s;  //const���ε���pstring���ͣ����Ǹ�ָ�룬������ָ��string���͵�constָ��
//       ��ʽ�ȼ��� string *const cstr1;
pstring const cstr2 = &s; // ����ͬ�ϣ���pstring������ͨ��int��⣬������ͨ��const������const����int֮ǰ����֮��û����
string *const cstr3 = &s;   //  ������д�� 

const char *cp = "some value";     // cpָ��C����ַ���
while(*cp) {
	++cp;
}
C����ַ����ı�׼�⺯��      ��Щ����������ַ���������'\0'����
#include <cstring>
strlen(s)   // ����s���ȣ�������������null
strcmp(s1, s2)    //  �Ƚ������ַ�����s1=s2����0 s1>s2�������� s1<s2���ظ���
strcat(s1, s2)    //  s2���ӵ�s1�󣬷���s1
strcpy(s1, s2)    //  s2���Ƹ�s1������s1
strncat(s1, s2, n)   // s2ǰn���ַ����ӵ�s1�󣬷���s1
strncpy(s1, s2, n)   // s2ǰn���ַ����Ƹ�s1������s1
ʹ�ú�����������ȫ
char largeStr[16 + 18 + 2];
strncpy(largeStr, cp1, 17);    //  ע����㸴��
strncat(largeStr, " ", 2);
strncat(largeStr, cp2, 19);

new ��̬��������   delete  ɾ����̬�����Ķ���
int *pi = new int;   // ��̬��������ʱ��ֻ��ָ���������ͣ�newֻ����ָ��̬�����Ķ����ָ��
int *pi = new int(1024);    // ��ʼ��
int *pi = new int();         // ǿ�ƶԶ�̬�����Ķ�����ֵ��ʼ��
new���ط���Ķ���ĵ�ַ�����Զ�����ָ��洢�ķ���ֵ��ͬʱ������صĵ�ַ�������͵ģ�����
����Ա������ʾ�Ľ���̬�����Ķ���ռ�õ��ڴ淵�ظ��Լ��洢����ʹ��delete
���ָ��ָ������new������ڴ��ַ�����ڸ�ָ����ʹ��delete�ǲ��Ϸ���  
ÿ����new�����ڴ�ʱ��ϵͳ�ӿ������������ϻ��һ�����п飬���˿�����new�����Ķ���ͬʱ������Ĵ�С����ÿ���׵�ַ�ڣ�������Ĳ�����Ϊһ������鷵�ظ�ϵͳ��������deleteɾ����new�����ڴ�ĵط���ʱ���׵�ַ����Ҫ�ͷŵ����ݵĴ�С����������new������ڴ��ַ��Ȼû�������С��Ϣ������delete���Ϸ�

delete p;
ʹ��delete֮��ָ��p���δ���壬һ����ָ��֮ǰ�������ĵ�ַ��Ȼ��p��ָ���ڴ����ͷţ���ʱָ��p�������ָ��

const int *pci = new const int(1024);   //  �����ʼ�� ����ַֻ�ܸ���ָ��const��ָ��
delete pci;            //  ɾ��const����

��̬���飺 ��new���ٵĶ�����ֱ�������ģ��ڶ��Ϸ���ռ�
int *pia = new int[10];   // ��������new������ڴ棬�����ڶ��ϣ�new����ָ�������һ��Ԫ�ص�ָ��
//  �����ɴ洢��(��)�ϴ��������������û�����ֵģ�ֻ��ͨ����ַ��ӷ���
string *psa = new string[10];     //  ��������Ĭ�Ϲ��캯����ʼ��
int *pia = new int[10];          //  ��������Ĭ�ϲ���ʼ��
int *pia2 = new int[10]();        // ǿ��Ҫ�����ֵ��ʼ��


size_t n = get_size();         //  ͨ������õ�nֵ
int* p = new int[n];    //  ��̬�������鳤��  ������new����ʱn�ڱ���ʱ��δ֪��
for(int* q = p; q != p+n; ++q)

char arr[0];     //  ���Ϸ���������������Ϊ0������
char *cp = new char[0];  //�Ϸ�������new��̬��������Ϊ0������Ϸ���ֻ��cp���ܽ��н����ò���
//   ��ʱcpû��ָ���κ�ֵ
delete [] cp;    //  �ջ�cp��ָ������飬���ڶ�̬�������������  []������
��Ϊnew�������ڴ�ռ��׵�ַ������ʹ�С��������[]����delete�ж��Ԫ�أ��˴�����ж������С��δ֪���˴������²⣬ ������֤

//   ʹ��new��̬���������С����̬�Ĵ���ͬ���ȵ��ַ���
const char *noerr = "success";
const char *err189 = "Error: a function declaration must specify a function return type";
const char *errorTxt;
if(errorFound)
	errorTxt = err189;
else
	errorTxt = noerr;
int dimension = strlen(errorTxt) + 1;         //  ������C����ַ�����β��'\0'
char *errMsg = new char[dimension];               // �ؼ�����
strncpy(errMsg, errorTxt, dimension);


��ά���飺
int ia[3][4] = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9 ,10 ,11}}


��������/��(++, --)�����������ǰ�õ��ж��⿪��������ʹ��ǰ�ò����ĺ�ϰ��
++i  ��  i++  ǰ��++�����++���ص�д��
class Integer {
public:
	Integer(long data): m_data(data) {}         //  ���캯��
	Integer& operator++() {
		m_data++;
		return *this;
	}
	Integer operator++(int) {
		Integer temp = *this;
		m_data++;
		return temp;       //   �����������ľ�ֵ
	}
}
++i  ��  i++   Ч���ϵ�����
�ڽ��������͵������ת���ɻ�����֮��ǰ��++ֻ��һ������룬����++����������루����֤??????????��
�Զ����������͵������++iЧ�ʽϸߡ�
�����ڽ���������ʱ�����ǵ�Ч�ʲ�𲻴�ȥ���������Ż���Ӱ�죩��������������������Ǵ�ɲ��ع��ġ�
�����������ٿ����Զ����������ͣ���Ҫ��ָ�ࣩ���������ʱ���ǲ���Ҫ�����ܶ������ķ����ˣ���Ϊǰ׺ʽ��++i�����Է��ض�������ã�����׺ʽ��i++���������һ����ʱ���󱣴����ǰ�����ֵ������(ʵ�ֹ��Զ�������++���������ľ�֪��)�����Ե����ڴ�����ʱ������˽ϴ�ĸ��ƿ���������Ч�ʽ��ͣ���˴���ʹ�����Զ������ͣ�ע�ⲻ��ָ�ڽ����ͣ���ʱ��Ӧ�þ����ܵ�ʹ��ǰ׺ʽ����/�ݼ�����Ϊ���������ʽϼѡ�

*iter++  �ȼ���  *(iter++)   *��������δ����ǰ������

Sales_item *sp = &item1;
(*sp).same_isbn(item2);       //  ���Ų����٣���Ϊ.�����ȼ���

sizeof  ����һ����������������ĳ��ȣ�����ֵ����size_t�����ȵ�λ���ֽ�

sizeof(ia)/sizeof(*ia)    ia�����������˲����õ�����Ԫ�صĸ���
sizeof(ia)/sizeof(ia[0])   ��˼ͬ�ϣ���һ��д��

throw  �����ⲿ��
try   ��������

try {
	if(!item1.same_isbn(item2))
		throw runtime_error("Data must refer to same ISBN");   // �׳��쳣
} catch (runtime_error err)  {
	cout << err.what();
}

ʹ��Ԥ���������е���
int main() {
	#ifndef NDEBUG
	cerr << "starting main" << endl;
	#endif
}
�����б���ʱ��
$ CC -DNDEBUG main.c

���õ��Ժ꣺
__FILE__  �ļ���
__LINE__  ��ǰ�к�
__TIME__  �ļ��������ʱ��
__DATA__  �ļ������������


������أ�
Date &calendar(const char*);    //  ����һ��ָ��Date���͵����ã��β�����������ֵ������const����
int *foo_bar(int *ip);        // ����ָ��

void use_ptr(const int *p) {}    //  ����ָ��ָ���ֵ

void swap(int &v1, int &v2) {}    // �����������͵��β�

            //////          C++11��������                   ///////////
			
C++��������ת���ṩ��4������ת����������Ӧ�Բ�ͬ���ϵ�Ӧ�á���ʽ�磺TYPE B = static_cast(TYPE)(a)
const_cast��������������ȥconst���ԣ�ȥ�����͵�const��volatile���ԡ�

static_cast������������Ǿ�̬����ת������intת����char��
������C����ǿ��ת����������ת������̬����ת�������ڣ�
����1. ���������֮��ת������������ָ��ת���ɸ���ָ���ǰ�ȫ��;������ָ��ת��������ָ���ǲ���ȫ�ġ�(���������֮��Ķ�̬����ת��������dynamic_cast)
����2. ������������ת����enum, struct, int, char, float�ȡ�static_cast���ܽ����޹�����(��ǻ��������)ָ��֮���ת����
����3. �ѿ�ָ��ת����Ŀ�����͵Ŀ�ָ�롣
����4. ���κ����͵ı��ʽת����void���͡�
����5. static_cast����ȥ�����͵�const��volitale����(��const_cast)��
int n = 6;
double d = static_cast<double>(n); // ��������ת��
double *d = static_cast<double *>(&n) //�޹�����ָ��ת�����������
void *p = static_cast<void *>(pn); //��������ת����void����

dynamic_cast������������Ƕ�̬����ת����������͸���֮��Ķ�̬����ת����
��������ת������̬����ת��������ʱ���Ͱ�ȫ���(ת��ʧ�ܷ���NULL)��
����1. ��ȫ�Ļ��������֮��ת����
����2. ����Ҫ���麯��������������麯�������ֶ�̬���Բ���ʹ��dynamic_cast
����3. ��ͬ���಻ͬ����֮��Ľ���ת�����������NULL��

class BaseClass {
����public:
����int m_iNum;
����virtual void foo(){};
����//����������麯�������ֶ�̬���Բ���ʹ��dynamic_cast
};
class DerivedClass: public BaseClass {
����public:
����char *m_szName[100];
����void bar(){};
};
BaseClass* pb = new DerivedClass();
//   ָ�������ָ��ת����û������
DerivedClass *pd1 = static_cast<DerivedClass *>(pb);    //����->���࣬��̬����ת������ȷ�����Ƽ�
DerivedClass *pd2 = dynamic_cast<DerivedClass *>(pb);    //����->���࣬��̬����ת������ȷ

BaseClass* pb2 = new BaseClass();
//   ָ�����ָ��ת�����кܴ����⣬������̬ת����֤��һ����Ч�Ľ��
DerivedClass *pd21 = static_cast<DerivedClass *>(pb2);     //����->���࣬��̬����ת����Σ�գ���������m_szName��ԱԽ��
DerivedClass *pd22 = dynamic_cast<DerivedClass *>(pb2);     //����->���࣬��̬����ת������ȫ�ġ������NULL


reinterpreter_cast���������½������ͣ���û�н��ж����Ƶ�ת����
	1.ת�������ͱ�����һ��ָ�롢���á��������͡�����ָ����߳�Աָ�롣
����2. �ڱ���λ�����Ͻ���ת���������԰�һ��ָ��ת����һ��������Ҳ���԰�һ������ת����һ��ָ��(�Ȱ�һ��ָ��ת����һ���������ڰѸ�����ת����ԭ���͵�ָ�룬�����Եõ�ԭ�ȵ�ָ��ֵ)�������ܽ���32bit��ʵ��ת��ָ�롣
����3. ����ͨ����;�����ں���ָ������֮�����ת����
����4. ���ѱ�֤��ֲ�ԡ�


������ʹ�ã�
int cmp(const void* a, const void *b)
qsort(arraypoint, arraysize, sizeof(element), cmp);

��C++ģ��ʵ�־�̬���캯����

template <typename T>
class static_constructable {
	private:
		struct helper{          //   ˽���ֻ࣬������һ��
			helper() {
				std::cout << "2s" << std::endl;
				T::static_constructor();
			}
		}
	protected:
		static_constructable() {            //  ���๹�캯����ÿ������һ����ʵ����ʱ�򶼵���
			std::cout << "1s" << std::endl;
			static helper placeholder;           //  ��̬������ֻʵ����һ��
		}
}

class A :static_constructable<A> {
	public:
		static void static_constructor() {
			std::cout << "3s" << std::endl;
			std::cout << "static constructor a" << std::endl;
			s_string = "abc";
		}
		static std::string s_string;
		A(std::string a1) {
			std::cout << "constructor " << a1 << std::endl;
		}
	private:
		int m_i;
}

std::string A::s_string;         // ��������٣���Ȼ���ӳ���ԭ��Ϊ����ĳ�Ա��̬�����������ⲿ���г�ʼ��
int _tmain(int argc, _TCHAR* argv[])
{
	std::cout << "begining of main" << std::endl;
	assert(sizeof(A) == sizeof(int));       //  ��ʱAֻ��4�ֽڴ�С���Ʋ�Ϊһ��ָ���С
	assert(A::s_string == "");              //  ��ʱδ���þ�̬���캯��������s_stringΪ��
	A a1("a1");
	assert(A::s_string == "abc");            //  ��ʱ�ѵ��þ�̬���캯��������s_stringΪabc
	A a2("a2");
	std::cout << "end of main" << std::endl;
	int abc;
	std::cin >> abc;
	return 0;
}

�����
beginning of main  
1s
2s
3s
static constructor a //����A����ǰ�Զ����þ�̬���췽����һ���ҽ�һ��  
constructor a1  
1s
constructor a2  
end of main 

����ģʽ��  ����˽�л����캯������������
class OnlyHeapClass {
	public:
		static OnlyHeapClass* GetInstance() {
			//     �����û���ָ�����ʽ���䷵�أ����ﲻ�Զ��󷵻أ���Ҫ�ǹ��캯����˽�еģ��ⲿ���ܴ�����ʱ����
			return (new OnlyHeapClass); 
		}
	void Destory();      //   ר��������������Ϊdeleteֻ��������ջ�ϴ����Ķ��󣬶��˴��Ƕ��϶���Ҫ��ʾ����������������������private�ģ�������ʾ���ã�������Ҫ�����Ա������������(��ֻ����������delete��)
	private:
		OnlyHeapClass();
		~OnlyHeapClass();
};

int main()
{
	OnlyHeapClass *p = OnlyHeapClass::GetInstance();
	delete p;
	return 0;
}

//   �������ʹ����˽�й��캯����GetInstance()��ΪOnlyHeapClass�ľ�̬��Ա���������ڴ��д�������
//   ����Ҫ�纯�����ݲ��Ҳ���ʹ��ֵ���ݷ�ʽ����������ѡ���ڶ��ϴ�������
//   ������ʹgetInstance()�˳�������Ҳ������֮�ͷţ������ֶ��ͷš�

//////���캯��˽�л��������Ʊ�֤�������಻�ܴ�������������ߴ������ʵ����������������;�����磬ʵ������һ��class�������ڴ����������һ��������ָ���������Ķ��󣨿�����class��˽���������һ��static���͵ļ����������ĳ�ֵ��Ϊ0��Ȼ����GetInstance()����Щ���ƣ�ÿ�ε�����ʱ�ȼ���������ֵ�Ƿ��Ѿ��ﵽ�������������ֵ���������������󣬷����new���µĶ���ͬʱ����������ֵ��1.���Ϊ�˱���ֵ����ʱ�����µĶ��󸱱������˽����캯����Ϊ˽���⣬���ƹ��캯��ҲҪ�ر���������Ϊ˽�С�

��������private:
������α�ֻ֤���ڶ���newһ���µ�������أ�ֻ���������������Ϊ˽�г�Ա��ԭ����C++��һ����̬�󶨵����ԡ��ڱ�������У����еķ��麯�����ö����������ɡ���ʹ���麯����Ҳ����ɷ����ԡ���Щ������ջ�����ɶ���ʱ��������Զ�������Ҳ��˵��������������Է��ʡ����������ɶ�����������ʱ���ɳ���Ա���ƣ����Բ�һ����Ҫ������������֤�˲�����ջ�����ɶ������Ҫ֤�����ڶ���������������OnlyHeapClass��һ�����Ψһ����������������������Ϊ˽�С�delete����������������������Բ��ܱ��롣
��ô����ͷ����أ���Ҳ�ܼ򵥣��ṩһ����Ա���������delete�������ڳ�Ա�����У����������ǿ��Է��ʵġ���Ȼdetele����Ҳ�ǿ��Ա���ͨ���� 
void OnlyHeapClass::Destroy() { 
        delete this; 
} 
���캯��˽�л��������ƿ��Ա�ֻ֤����new�����ڶ��������ɶ���ֻ�ܶ�̬��ȥ�������������������ɵĿ��ƶ�����������ڡ����ǣ�����������Ҫ�ṩ�����ͳ����Ĺ����ӿڡ�
��������delete��newΪ˽�п��ԴﵽҪ����󴴽���ջ�ϵ�Ŀ�ģ���placement newҲ���Դ�����ջ�ϡ�

 1.ΪʲôҪ�Լ������أ��������������ʱ�����Զ�����������������ʲô�������Ҫ�Լ��������������أ�   
  ������������һ���������ϣ��������֮ǰ������һЩ���飬������������˲���֪����   
  ��ô��Ϳ�������дһ�������������Ҫ��������ȫ���������ٵ�������������   
  �����˼�ֻ�ܵ�������������������󣬴Ӷ���֤������ǰһ��������Ҫ��Ķ�����   
    
  2.ʲô����²��õ���ֻ���ɶѶ����أ�   
  �����Ѷ������new�����ģ������ջ������ԡ�ʲô�����Ҫnew��ʲô�������ջ����   
  ��ǰ���䣬�޷Ǿ��Ǻ�ʱ���ö�̬����ʱ���þ�̬���ɵ����⡣���Ҫ���ݾ������   
  �����������������һ��������������֪��ĳ���������ֻ����10������ô��Ϳ���   
  ������������һ�����顣10��Ԫ�أ�ÿ��Ԫ�ض���һ��ջ����������޷�ȷ����   
  �֣���ô��Ϳ��Զ���һ����������ָ�룬��Ҫ������ʱ���new������������list   
  ����vector����������
  
   ��������������Ϊ˽�еģ�����ֹ���û��������������������ʹ�á�������������������棺
  1.   ��ֹ�û��Դ����͵ı������ж��壬����ֹ��ջ�ڴ�ռ��ڴ��������͵Ķ���Ҫ��������ֻ����   new   �ڶ��Ͻ��С�
  2.   ��ֹ�û��ڳ�����ʹ��   delete   ɾ�������Ͷ��󡣶����ɾ��ֻ��������ʵ�֣�Ҳ����˵ֻ�����ʵ���߲��п���ʵ�ֶԶ����delete���û��������ɾ����������û���ɾ������Ļ���ֻ�ܰ������ʵ�����ṩ�ķ������С�   
  �ɼ���������֮�����������û��Դ����ʹ�á�һ����˵��Ҫ��������ͨ���������������ﵽ�����Ŀ�ģ�������   singleton��ʵ���ϡ�
  
malloc��free��C/C++���Եı�׼�⺯����new/delete��C++������������ɶ�̬�����ڴ���ͷ��ڴ�
���ڷ��ڲ��������͵Ķ���malloc/free�޷����㶯̬�����Ҫ��
����malloc/free�ǿ⺯������������������ڱ���������Ȩ��֮�ڣ����ܹ���ִ�й��캯������������������ǿ����malloc/free

  
ָ���βΣ�
void reset(int *ip)               // ������int *ʵ�ε���
void use_ptr(const int *p)         // ������int *Ҳ������const int *ʵ�ε���

const�βΣ�
void fcn(const int i)   // ��Ϊ  void fcn(int i) Ϊ�˼���C���ԣ���ʱ�ٶ���ǰ�������ᱨ�ظ��������
const�βεĵ��÷�Χ����ͨ�ķ�const�βε��÷�Χ�㷺������
string::size_type find_char(string &s, char c)
find_char("hello world", 'o');  // ���ò��Ϸ�����֧������ֵ�ַ���
//   ���βζ���Ϊconst�����������ַ�������ֵ��������µ��ã��ȷ�const�βε�ʹ�÷�Χ���㷺������������
���ʹ�������βε�ΨһĿ���Ǳ��⸴��ʵ�Σ���Ӧ�������βζ���Ϊconst����
�������Ҫconst����ʱ�����βζ���Ϊ��ͨ���ã���ᵼ�²���ʹ����ֵ��const�����Լ���Ҫ��������ת���Ķ��������øú������Ӷ�����Ҫ�������˸ú�����ʹ��

�����βΣ�
void swap(int &v1, int &v2)
bool isShorter(const string &s1, const string &s2)
��const�����β�ֻ������ȫͬ���͵ķ�const�������
int incr(int &val)  ���µ��ýԴ�����
short v1 = 0;
const int v2 = 42;
incr(v1);   // ���Ͳ��ԣ���Ϊ�������βΣ�����ǿ��������ȫһ��
incr(v2);
incr(0);      // ����ֵ������ֵ������Ҳ����(�����޸�)
incr(v1+v2);   // �ӷ��������һ����ֵ����

ָ��ָ������ã�
void ptrswap(int *&v1, int *&v2)  // v1��һ�����ã���ָ��int�Ͷ����ָ�������

�����βζ��壺
void printValues(int *) {}
void printValues(int[]) {}
void printValues(int[10]) {}    //  ����������ȫ�ȼۣ��������һ��10�����󵼺�������

��������Ҫ���������Һ����岻����������ĳ���ʱӦʹ��ָ���βΣ����������Ӧʹ�������β�

ͨ�����ô������飺
void printValues(int (&arr)[10])   // ���������Ὣ����ʵ��ת��Ϊָ�룬���Ǵ�����������ñ���
//   �����С��Ϊ�βκ�ʵ�����͵�һ���֣�Բ���Ų�����
�����βε��ŵ����ں���������������ĳ����ǰ�ȫ�ģ�ȱ���������˿��Դ��ݵ�ʵ�����飬ֻ��ʹ�ó���ƥ���ʵ�����������ú���

��ά����Ĵ��ݣ�  //  Ԫ�ر��������飬����һά������ά�ĳ��ȶ���Ԫ�����͵�һ���֣���ָ��
void printValues(int (matrix*)[10], int rowSize)  // matrix��ָ����10��intԪ�ص������ָ��
void printValues(int matrix[][10], int rowSize)   //  ����Ϊ��ά�������ʽ�����ʻ���ָ��
ָ���βε��ŵ��ǿ�����ȷ�ı�ʾ���������ݵ���ָ������Ԫ�ص�ָ�룬���������鱾�����ҿ���ʹ�����ⳤ�ȵ�ʵ�����������ú�����ȱ���Ǻ����岻������������ĳ��ȣ�����������������ڴ��Խ����ʣ��Ӷ���������Ľ�����ߵ��³��������

ʡ�Է��βΣ�
void foo(parm_list, ...); //��ʾ�������β��ö�Ӧ��ʵ�ν������ͼ�飬��ʡ�Է���Ӧ��ʵ����ͣ���ͼ��

��������ֵ��ǧ��Ҫ���ؾֲ����������ã���Ϊ�ֲ������ں������н�����ᱻ�ͷŵ�
���÷�����ֵ��
char &get_val(string &str, string::size_type ix) {
	return str[ix];
}
const char &get_val()   // �˺������Ʒ���ֵ���ɱ��޸�
int main() {
	string s("a value");
	get_val(s, 0) = 'A';  // �ı��ַ�������ĸΪA
}

Ĭ��ʵ�Σ�
//  ���һ���βξ���Ĭ��ʵ�Σ����������е��βζ�������Ĭ��ʵ��, Ĭ��ʵ��ֻ���滻��������ȱ�ٵ�β��ʵ��
string screenInit(string:size_type height = 24, string::size_type width = 80, char background = ' ')
screenInit();   // �ȼ���  screenInit(24, 80, �� ��);
screenInit(66);  // �ȼ���  screenInit(66, 80, �� ��);
screenInit(66, 256); // �ȼ���  screenInit(66, 256, ' ');
screenInit(, , '?');   // error
//  ���ʱע�⣺ʹ����ʹ��Ĭ��ʵ�ε��β�������ǰ��
//   ������������Ҳ�����ڶ�����ָ��Ĭ���βΣ�����ֻ��ѡ��һָ��һ�Σ�ͨ����ͷ�ļ���������ָ��

����������
inline const string& shorterString(const string& s1, const string& s2)
//   ��������Ӧ����ͷ�ļ��ж���

��ĳ�Ա������
��������ʽ�Ľ������ڶ���ĳ�Ա����������������
ÿ����Ա��������һ������ġ��������βν��ó�Ա��������øú�������������һ��
const����ָ��const�����ָ�������ֻ�ܵ�����const��Ա���������÷�const��Ա��������
class Sales_item {
	public:
		double avg_price() const;     //   �˴���const�ı�������this�βε����ͣ������Ϊconst Sales_item* ����
		bool same_isbn(const Sales_item &rhs) const  // ��const�ĳ�Ա������Ϊ������Ա����
		{ return isbn = rhs.isbn; }
		Sales_item(): units_sold(0), revenue(0.0) {}  // Ĭ�Ϲ��캯��
		// ð�źͻ�����֮��Ĵ����Ϊ���캯���ĳ�ʼ���б�Ϊ������ݳ�Աָ����ֵ
	private:
		std::string isbn;
		unsigned units_sold;
		double revenue;
}
���ⶨ���Ա������
//   ԭ����Ա������const֮���ǲ����޸������������ݵ�
 //  �ڶ�const��Ա����ʹ�õĳ�Ա������mutable���η��󣬾Ϳ���������ĺ������޸������Ա������
 //  ����ǰ�����������Ϊ mutable double revenue; 
double Sales_item::avg_price() const         
{
	if (units_sold)
		return revenue/units_sold;
	else
		return 0;
}

���غ�����ͬ����ͬ�βεĺ���
Record lookup(Phone);
Record lookup(const Phone);   // �����������ظ�����Ϊ�����β�ʱ���������β��Ƿ�Ϊconst�������������֪�������ĸ�����
//  ��������������ڷ������β�


ָ������ָ�룺
bool (*pf)(const string&, const string&)  //  ����һ������ָ�������ע�⵽pf�Ǹ����������Ǹ�������
//   ���¶����ʾcmpFcn��һ��ָ������ָ�����͵����֣���ָ������Ϊ��ָ�򷵻�bool���Ͳ���������const string�����βεĺ�����ָ�롱������Ҫʹ�����ֺ���ָ������ʱ��ֻ��ֱ��ʹ��cmpFcn����
typedef bool (*cmpFcn)(const string&, const string&);  //  ���������ļ򻯣�ͨ������
bool lengthCompare(const string&, const string&);
cmpFcn pf = lengthCompare;
pf("hi", "bye");  // ���Բ���Ҫ�����ò�������ֱ��ͨ��ָ����ú���
(*pf)("hi", "bye");   // ͬ����ȷ

����ָ���βΣ�
void useBigger(const string&, const string&, bool(const string&, const string&));    //  �����������Ǻ���ָ��
void useBigger(const string&, const string&, bool(*)(const string&, const string&));    // ͬ��

����ָ������ָ�룺
int (*ff(int))(int*, int);   //  �����������ֿ�ʼ�����������
ff(int)  ff����Ϊһ������������һ��int�͵��βΣ��ú�������int (*)(int*, int);
//  ������ȷ��д��
typedef int (*PF)(int*, int);
PF ff(int);

ָ�����غ�����ָ�룺
ָ�����ͱ��������غ���ĳ���汾��ȷƥ�䣬���򱨱������
extern void ff(vector<double>);
extern void ff(unsigned int);
void (*pf1)(unsigned int) = &ff;   // ƥ��ff(unsigned int)


//  ָ������
int *ptr[5];
//  ����ָ��
int (*ptr)[];
int Test[2][3] = {{1,2,3}, {4,5,6}};
int (*A)[3] = &Test[1];
cout << (*A)[0] << (*A)[1] << endl;

����һ����ά��ָ��
int **p = new int*[50];    // һ��ָ��ָ�������ָ��
for(int i=0; i<50; i++)     // ��ָ�������ÿ��Ԫ�أ�ָ��ĳ������
	p[i] = new int[100];


��ĳ�Ա�����ĳ�ʼ���б�ĳ�ʼ��˳���Ǹ��ݳ�Ա����������˳����ִ�еģ��������Գ�ʼ��ǰ�����ʱ��Ҫע������˳�򣡣���

�����������������Ϊvirtualʱ�����е���������������������Զ���Ϊvirtual�ͣ�

��C++�У�ֻҪԭ���ķ���������ָ�����ָ������ã��µķ���������ָ���������ָ������ã����ǵķ����Ϳ��Ըı䷵�����͡�
���������ͳ�ΪЭ�䷵�����ͣ�Covariant returns type).
����Ҫ����  """������ȫ��ͬ���β�"""��
һ�㸲�Ǿ�����ͬ�ķ���ֵ���������ʾ�����������Է�������Э����ԣ����������ɡ����ǵķ���ֵ�����ֻ���������ࡣ����������⣬һ��������Ҳ��һ�����ࡣ
Class ShapeEditor ...{����};
Class CircleEditor : public ShapeEditor...{ �� };
Class Shape {
public:
virtual const ShapeEditor& getEditor () const = 0; //Factory Method  ���麯��
};
Class Circle : Public Shape {
public:
const CircleEditor& getEditor ()const ;
};
����������У�ע��CircleEditor������Circle::getEditor������֮ǰ�������ض��壨�����ܽ�����������
��Ϊ����������֪��CircleEditor����Ĳ��֣�����ִ���ʵ��ĵ�ַ���ݣ��Ӷ���һ��CircleEditor����
����ָ�룩ת��Ϊһ��ShapeEditor���ã���ָ�룩
Э�䷵�����͵��������ڣ����ǿ������ʵ��̶ȵĳ�����湤�����������Ǵ���Shape�������һ�������ShapeEditor�������ڴ���ĳ�־������״���ͣ�����Circle,���ǾͿ���ֱ�ӻ��CiecleEditor.Э�䷵�ػ��ƽ����Ǵ�������һ�ִ������ѳ��������ò�ʹ�����ڳ����ת�������������¡��ṩ������Ϣ����������Ϣ��һ��ʼ�Ͳ�Ӧ�ö����ģ�����ô��������Ԫ��������operator+����ô�����û����operator+�أ���


��Щ��Ա�������������ͱȽ��ر����ǵĳ�ʼ����ʽҲ����ͨ�������͵ĳ�Ա����������ͬ����Щ��������͵ĳ�Ա����������
a.����      b.����       c.��̬       d.��̬����(����)         e.��̬����(������)
���������ã�����ͨ�������б���г�ʼ����
��̬��Ա�����ĳ�ʼ��Ҳ���е��ر����������ʼ���Ҳ����ٴ���static�ؼ��֣��䱾�ʼ���ĩ��
�ο�����Ĵ����Լ�����ע�ͣ�
#include <iostream>
using namespace std;
class BClass
{
public:
 BClass() : i(1), ci(2), ri(i){} // ���ڳ����ͳ�Ա�����������ͳ�Ա����������ͨ���������б�ķ�ʽ���г�ʼ��
//��ͨ��Ա����Ҳ���Է��ں���������Ǳ�����ʵ�Ѳ��ǳ�ʼ��������һ����ͨ���������-->��ֵ���㣬Ч��Ҳ��
private:
 int i;                                  // ��ͨ��Ա����
 const int ci;                           // ������Ա����
 int &ri;                                // ���ó�Ա����
 static int si;                          // ��̬��Ա����
 //static int si2 = 100;                 // error: ֻ�о�̬������Ա�������ſ���������ʼ��
 static const int csi;                   // ��̬������Ա����
 static const int csi2 = 100;            // ��̬������Ա�����ĳ�ʼ��(Integral type)    (1)
 static const double csd;                // ��̬������Ա����(non-Integral type)
 //static const double csd2 = 99.9;      // error: ֻ�о�̬�����������ݳ�Ա�ſ��������г�ʼ��
};
//ע���������У������ٴ���static
int BClass::si = 0; // ��̬��Ա�����ĳ�ʼ��(Integral type)
const int BClass::csi = 1; // ��̬������Ա�����ĳ�ʼ��(Integral type)
const double BClass::csd = 99.9; // ��̬������Ա�����ĳ�ʼ��(non-Integral type)
// �ڳ�ʼ��(1)�е�csi2ʱ������������ʦStanley B.Lippman��˵�����������Ǳ���ġ�
// ����VC2003�����������һ�н���������󣬶���VC2005�У�������������п��ޣ�����ͱ������йء�
const int BClass::csi2;
---------------------------------------------------------------------------------------------
��̬��Ա�����������򣬵�����������󣬺���ͨ��static����һ��������һ���оͷ����ڴ沢��ʼ�����������ںͳ���һ�¡�
���ԣ�����Ĺ��캯�����ʼ��static������Ȼ�ǲ�����ġ�
��̬��Ա��ʵ��ȫ�ֱ�����λ��һ���ģ�ֻ����������������ʹ�����������������ڣ������������������������Ա����Ҫ����Ķ����⣨�������������⣩��ʼ����
1.static��Ա�����������౾��Ͷ��󣬵��Ƕ��ж���ӵ��һ���ľ�̬��Ա���Ӷ��ڶ������ʱ����ͨ�����캯��������г�ʼ����
2.��̬��Ա�������ඨ����߳�ʼ����ֻ����class body���ʼ����
3.��̬��Ա��Ȼ��ѭpublic��private��protected����׼��
4.��̬��Ա����û��thisָ�룬�����ܷ��طǾ�̬��Ա����Ϊ���˶����������⣬�౾��Ҳ���Ե��ã����Բ�����ʹ�÷Ǿ�̬��Ա������
C++��̬��Ա��������static���εĳ�Ա�����������ڶ����һ���֣��������һ���֡�
��˿�����û��ʵ�����κζ����ʱ��ʹ�þ�̬��Ա���������Ǳ����ʼ������������
���ھ�̬����ֻ�ܱ���ʼ��һ�Σ����Գ�ʼ����Ա������Ҫ�������µط���1.��Ĺ��캯�������캯�����ܶ�α����ã���2.ͷ�ļ��У�ͷ�ļ����ܱ����������ط���Ҳ���ܱ�ִ�ж�Σ���Ӧ�÷���Ӧ�ó����У���������κεط���ʼ�������磺��main�У���ȫ�ֺ����У����κκ���֮�⡣
static��Ա�����Ƿ���private���ڳ�ʼ����û��Ӱ�죬��Ϊ�趨static��Ա������ֵʱ�������κδ�ȡȨ�޵�����������������Ҫע����ǣ�static��Ա����������Ҫ�����ڳ�ʼ������У���Ϊ���ǳ�ʼ�����������Ǹ�ֵ������static��Ա�������ڳ�ʼ������������������ʱ�򣩲Ŷ�������ġ����û�г�ʼ����������������Ӵ��󡣳�ʼ����ʽ���£�
����  ����::��̬��Ա������ = ��ʼ��ֵ;










   ///////////////////////////////            �������             ////////////////////////////
˳������
#include <vector>   // �����������
#include <list>     //  ���ٲ���/ɾ��
#include <deque>   //  ˫�˶���


C c(c2);  // ��������c2�ĸ���c    vector<int> c(c1)   c1Ҫ��vector<int>����
C c(b, e);       // ����c��Ԫ���ǵ�����b��e��ʾ�ķ�Χ�ڵ�Ԫ�صĸ���   vector<int> c(a.begin(), a.end())
C c(n, e);    // ����c��Ԫ��Ϊn��t   vector<int> c(3, 10)    ֻ������˳������  
C c(n);       // ����c����n��Ԫ�أ�����ֵ��ʼ���� ֻ������˳������  
vector<vector<string> > lines;  // �ո񲻿�ʡ�������>>������
vector<string>::iterator mid = svec.begin() + svec.size()/2;
������������ͱ�����
size_type
iterator
const_iterator
reverse_iterator
const_reverse_iterator
difference_type
value_type
reference
const_reference
//  ͨ�õ���������
c.begin()  c.end()  c.rbegin()  c.rend()  
c.push_back(a)  
c.insert(p, t)     // �ڵ�����pָ���Ԫ��ǰ�����t������ָ����Ԫ�صĵ�����
c.insert(p, n, t)   //  �ڵ�����pָ���Ԫ��ǰ����n��t������void
c.insert(p, b, e)  //  �ڵ�����pָ���Ԫ��ǰ���������b��e��ǵ�Ԫ��
c.size()       //  ����Ԫ�ظ���  c::size_type����
c.max_size()
c.empty()
c.resize(n)     // ��������c�ĳ��ȴ�С��ʹ��������n��Ԫ��
c.resize(n, t)   //  ��������c�ĳ��ȴ�С��ʹ��������n��Ԫ�أ��¼�Ԫ��ֵΪt
c.back()       //  ��������c�����һ��Ԫ�ص�����
//  list<int>::reference last = *--ilist.end();
//  list<int>::reference last2 = ilist.back();
c.front()     //  ��������c�ĵ�һ��Ԫ�ص�����
//  list<int>::reference val = *ilist.begin();
//  list<int>::reference val2 = ilist.front();
c.erase(p)   //  ɾ��������pָ���Ԫ��
c.erase(b,e)  //  ɾ��������b��e֮���Ԫ��
c.clear()   // ɾ������Ԫ��
c.pop_back()  // ɾ�����һ��Ԫ�أ�����void
c1.swap(c2)   //  ��������c1��c2������
c.assign(b, e)   // ����c��Ԫ�أ���������b��e������Ԫ�ظ��Ƶ�c�У�b��e����ָ��c��Ԫ��
c.assign(n, t)   // ����c��Ԫ��Ϊn��t

//  vector
c.capacity()   //  vector��ǰ���������һ���c.size()��
c.reserve(n)   //  ǿ������vector�ܿռ�Ϊn������ʹ�ÿռ��Ԥ���ռ�


//  ֻ����list deque 
c.push_front(a)
c.pop_front()   //  ɾ����һ��Ԫ�أ�����void


string���ͣ�
#include <string>


sting s(cp);   // ��cpָ���c����ַ�����ʼ������
getline(is, s)  // ��������is�ж�ȡһ���ַ���д��s
s.insert(p, t)
s.assign(b, e)
s.erase(p)
s.substr(pos, n)
s.append(args)
s.replace(pos, len, args)

s.find(args)       //  args�������ͣ�c,pos    s2,pos   cp,pos   cp,pos,n
s.rfind(args)
s.find_first_of(args)
s.find_last_of(args)
s.find_first_not_of(args)
s.find_last_not_of(args)



������������
#include <stack>     //  ջ
#include <queue>     //  ����
Ĭ��stack��queue����deque����ʵ�֣�priority_queue����vectorʵ��

ջ������ stack<int> s;
s.empty()
s.size()
s.pop()       //  ɾ��ջ��Ԫ�أ���������
s.top()        //  ����ջ��Ԫ�أ�����ɾ��
s.push(item)

deque<int> deq;
stack<int> stk(deq);
stack< string, vector<string> > str_stk;  // ����vectorʵ��stack
stack< string, vector<string> > str_stk2(svec);  // ͬ��

����������
q.empty()
q.size()
q.pop()
q.front()
q.back()
q.top()
q.push(item)

����������
pair<T1, T2> p1;  //  pair<string, int>   //  pair<string, string> author("James", "Joyce")
author.first == "James";   author.second == "Joyce";

make_pair(v1, v2);
pair<string, string> next_auth;
while(cin >> first >> second)
	next_auth = make_pair(first, second);  // �ȼ���  next_auth = pair<string, string>(first, second);
	
map�������飺
map<k, v> m;   //  ��Ϊk��ֵΪv
map<K, V>::key_type  //  map�����У����������Ľ�������
map<K, V>::mapped_type  // map�����У�����������ֵ������
map<K, V>::value_type //һ��pair���ͣ�firstԪ�ؾ��� const map<K,V>::key_type���ͣ�secondԪ����Ϊmap<K,V>::mapped_type

map<string, int>::iterator map_it = word_count.begin();
map_it->first; map_it->second;
word_count["Anna"] = 1;        //  ������ʱ����  Anna

map<string, int> word_count;
string word;
while(cin >> word)
	++word_count[word];            //  ���ʼ�������û����룬����Ӽ�

while(cin >> word) {
	pair<map<string, int>::iterator, bool> ret = word_count.insert(make_pair(word, 1));
	if(!ret.second)                    //  �ò��뷽ʽ��д���ʼ�����
		++ret.first->second;
}
	
word_count.insert(map<string, int>::value_type("Anna", 1));
word_count.insert(make_pair("Anna", 1));

m.count(k)        //  ����m�н�k���ִ������������Ԫ�أ����±����ʱ�����ڵĻ������Ԫ��
m.find(k)           //  ����k�ĵ������������ڽ�Kʱ����ĩ�˵�����
m.erase(k)         //  kΪ��
m.erase(p)        //  pΪ������
m.erase(b, e)     //   ��Χɾ��

����ʾ����
int main() {
	map<int, string> mapStudent;
	mapStudent.insert(pair<int, string>(1, "student_one"));             //  ͨ��ʵ����һ��pair���
	mapStudent.insert(pair<int, string>(2, "student_two"));             //  ����Ѵ������ʧ��
	mapStudent.insert(pair<int, string>(3, "student_three"));
	mapStudent.insert(map<int, string>::value_type(4, "student_four"));          // ͨ��value_type���
	mapStudent.insert(map<int, string>::value_type(5, "student_five"));          // ����Ѵ������ʧ��
	mapStudent.insert(map<int, string>::value_type(6, "student_six"));
	// ������ķ�������ʱ��ֵ��������Ϊȱʡֵ���ٸ�ֵΪ��ʾ��ֵ�����ֵԪ���Ǹ����������Ƚϴ󣬽��������Ϸ���
	mapStudent[7] = "student_seven";    //  ͨ���±���ʽ��ӣ���Ҫ���±��ǹؼ��ֶ����������±꣬����û��0
	mapStudent[8] = "student_eight";    //  �˷�ʽ�Ǹ�����ӣ���������򸲸�
	
	pair<map<int, string>::iterator, bool> insertValue;
	insertValue = mapStudent.insert(pair<int, string>(3, "student_5"));  // ��Ӻ󷵻�һ����������bool���͵�pair
	if(insertValue.second) {
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}
	
	
	cout << mapStudent.size() << endl;
	for(map<int, string>::const_iterator it = mapStudent.begin(); it!=mapStudent.end(); it++) {   // ˳�����
		cout << it->first << " " << it->second << endl;
	}
	//  �������
	for(map<int, string>::const_reverse_iterator cit = mapStudent.rbegin(); cit!=mapStudent.rend(); cit++) {
		cout << cit->first << " " << cit->second << endl;
	}
	
	cout << "count: " << mapStudent.count(3) << endl;     // ���ִ�����map�п������ж��Ƿ���ĳ��Ԫ��
	
	//  ͨ��map����ķ�����ȡ��iterator����������һ��std::pair����
	//  ������������ iterator->first �� iterator->second �ֱ����ؼ��ֺʹ洢������
	
	map<int, string>::iterator it = mapStudent.find(3);     // ����ĳ��Ԫ�أ�������Ӧ������
	if(it == mapStudent.end()) {
		cout << "don't find" << endl;
	}else{
		cout << "find " << it->first << "->" << it->second << endl; 
	}
	
	string elem = mapStudent[1];     //  ���������Ҳ�ܷ���һ��ֵ�������������������������ؼ���1
	
	map<int,string>::iterator it1, it2;
	//  �½磬map���������Ԫ���򷵻ظ�Ԫ�صĵ������������ڷ��ظպñȸ�ֵ���Ԫ�ص� ����������mapStudent.end()
	it1 = mapStudent.lower_bound(3); 
	cout << it1->first << " " << it1->second << endl;
	//  �Ͻ죬���ڲ����ڶ����ظպñȸ�ֵ���Ԫ�ص� ����������mapStudent.end()
	it2 = mapStudent.upper_bound(5);
	cout << it2->first << " " << it2->second << endl;
	
	pair<map<int, string>::iterator, map<int, string>::iterator> mapPair;
	mapPair = mapStudent.equal_range(3);           //  ����һ�Ե��������ֱ�Ϊ lower_bound �� upper_bound
	if(mapPair.first == mapPair.second) {          //����Ⱦ�û�ҵ�
		cout << "don't find element" << endl;
	}else{
		cout << "find 3" << endl;
	}
	
	map<int, string>::iterator it3;
	it3 = mapStudent.find(3);
	mapStudent.erase(it3);            //  ͨ��������ɾ��
	mapStudent.erase(4);              //  ͨ���ؼ���ɾ��
	mapStudent.erase(mapStudent.begin(), mapStudent.end());      //  ͨ����Χ������ɾ��
	mapStudent.clear();   //  �ȼ�������ĵ�������Χɾ��
}

//   http://blog.sina.com.cn/s/blog_4b3c1f950100kgps.html  �������map��QA���Կ��������������˷�ʱ��

set������
set<int> iset(ivec.begin(), ivec.end());
set<string> set1;
set1.insert("the");

����ʾ����
int main() {
	set<int> setNum;
	for(int i=9; i>0; i--) {
		setNum.insert(i);       //  ����Ԫ��
	}

	set<int>::iterator it = setNum.find(5);    // Ѱ��Ԫ��5���ɹ�������Ч��������ʧ�ܷ��� end()
	if(it != setNum.end()) {
		cout << "find element\n";
	}else{
		cout << "don't find\n";
	}

	pair<set<int>::iterator, bool> vset = setNum.insert(5);    // �����ظ�Ԫ�أ�ʧ�ܷ���һ��pair
	if(vset.second) { // ע�ⲻ��ͨ���ж�pair�ĵ�һ��Ԫ���ǲ���end()���жϲ���ɹ���񣬲²ⲻ�ɹ������õ�һ��ֵ
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}

	cout << setNum.size() << endl;         //   ����С
	setNum.erase(5);                       //   ɾ��setԪ��
	cout << setNum.size() << endl;
	
	vset = setNum.insert(5);               //  ɾ�������ɹ�
	if(vset.second) {
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}
	
	cout << setNum.count(6) << endl;      //  �鿴ĳ��Ԫ���ڲ���set�У���Ϊֻ���ܳ���һ�Σ��������ֻ��1����0

	for(set<int>::iterator cit=setNum.begin(); cit!=setNum.end(); cit++) {   //��set���ݵı��� 
		cout << *cit << " ";
	}

}


multimap������
m.lower_bound(k)    // ����һ����������ָ�򽡲�С��k�ĵ�һ��Ԫ��
m.upper_bound(k)     // ����һ����������ָ�򽡴���k�ĵ�һ��Ԫ��
m.equal_range(k)     //  ����һ��������pair������first�ȼ���m.lower_bound(k), second�ȼ���m.upper_bound(k)

�����㷨��
#include <algorithm>
#include <numeric>

vector<int>::const_iterator result = find(vec.begin(), vec>end(), search_value);    // ���ص�����
int *result = find(ia, ia+6, search_value);             //  ����ָ��

int sum = accumulate(vec.begin(), vec.end(), 42);        //  �ۼ���ͣ�42���ۼӳ�ʼֵ

list<string>::iterator it = find_first_of(roster1.begin(), roster1.end(), roster2.begin(), roster2.end());

fill(vec.begin(), vec.end(), 0);     //  ����������Χ�ڵ�ֵ�޸�Ϊ0(����������)
fill_n(vec.begin(), 10, 0);       //  begin��ʼ��ʮ��Ԫ����Ϊ0�����뱣֤��ʮ��Ԫ��

#include <iterator>        //  ��������صĿ�
vector<int> vec;
fill_n(back_inserter(vec), 10, 0); // back_inserter�ǵ�����������(���������)���˴�������vec�ϸ���10��0

copy(ilst.begin(), ilst.end(), back_inserter(ivec));      // ����������ָ��Ŀ�����е�һ��Ԫ�أ���������ĵط�
replace(ilst.begin(), ilst.end(), 0, 42);             //   ����Χ������0�滻��42
replace_copy(ilst.begin(), ilst.end(), back_inserter(ivec), 0, 42);    
//  ����������ָ�������󱣴��λ�ã������������ilst��Χ�ڵ�ֵ������0���滻��42

sort(words.begin(), words.end());   // ���򣬵��ʰ��ֵ�������

// �������ڡ����ظ�Ԫ���ƶ�������β��������ָ���һ���ظ�ֵ�ĵ�����
vector<string>::iterator end_unique = unique(words.begin(), words.end());  

bool isShorter(const string &s1, const string &s2) {
	return s1.size() < s2.size();
}
stable_sort(words.begin(), words.end(), isShorter);       //  �������Ԫ�ص�ԭʼλ��

bool GT6(const string &s) {
	return s.size()>=6;
}
vector<string>::size_type wc = count_if(words.begin(), words.end(), GT6);

�����������
back_inserter   //  ����ʹ��push_backʵ�ֲ���ĵ�����
front_inserter    //   ʹ��push_frontʵ�ֲ���
inserter         //  ʹ��insertʵ�ֲ���

list<int>::iterator it = find(ilst.begin(), ilst.end(), 42);
replace_copy(ivec.begin(), ivec.end(), inserter(ilst, it), 100, 0)

����������
istream_iterator<int> cin_it(cin);    //  ��cin�ж���int
istream_iterator<int> end_of_stream;
while(cin_it != end_of_stream)       // �յ�������Ϊ������־
	vec.push_back(*cin_it++);         //  ��������vec
//  д��2
vector<int> vec(cin_it, end_of_stream);     //  ���õ�������ʼ��vec��������β���߷�int������ʱ����

ofstream outfile;       //  ����ļ���
ostream_iteraotr<Sales_item> output(outfile, " ");       //  ÿ�����Ԫ���ÿո���� 


�����������
vector<int>::reverse_iterator r_iter;
for(r_iter = vec.rbegin()); r_iter != vec.rend(); ++r_iter)
	cout << *r_iter << endl;



   ///////////////////////////////            ���С����             ////////////////////////////
   //       ���ڼ���ĳ���ֶ������м���1�Ĵ���
   int func(int x) {
		int count = 0;
		while(x) {
			count++;
			x = x&(x-1);       //  ע�⵽������ÿ����һ��1
		}
		return count;
   }
   
//   ��������͵�һ�룬������ķ���
int x,y;    //  ֻ����int����������ʾ������һ��
halfsum = (x&y)+((x^y)>>1);        //  x&y�õ���ͬ��λ������ͬλ�ĺ͵�һ�룻 x^y�õ����в�ͬλ��Ȼ�����2

//   ���ú궨����ȡ�ṹ����ĳ�����������ƫ����
#define FIND(struc, e) (size_t)&(((struc *)0)->e)  // ��0��ַת��stuc *���ͣ���ȡ��e�ĵ�ַ���õ����ƫ��
//   ע�⵽���ֺ궨��ʹ�õ�ʱ������������������������׳������Ժ궨�������ʹ�÷�������!!!!!!!!

//  �궨������Сֵ
#define MIN(A,B) ((A)<=(B)?(A):(B))     // ע��������в�����Ҫ������������

//  VC����:  ʹ��pack��ǿ�ƽṹ�����ݲ�����
#pragma pack(1)
struct aStruct {
	char cValue;
	int ivaule;
};
#pragma pack()

#pragma warning(disable: 4786)  //  ȥ����������е�warning�������warning���Կ⣬����������ͷ�ļ���include֮ǰ


        ///////////////////////////////            ���С����             ////////////////////////////
һ�㺯����ͷ�ļ���
#include <fstream>
ifstream map_file(argv[1]);         // �ò���1�е��ļ�����ʼ��map_file������
map_file >> key >> value;
string fileName;
ifstream infile(fileName.c_str());     //  c�ַ���
map_file.getline(a, 100);        //  �������ļ���һ���ַ����ַ�����a������100��

infile.good();      //  ����һ��boolֵ����ʾ�ļ����Ƿ���ȷ
infile.bad();       //  ����һ��boolֵ����ʾ�ļ����Ƿ����
infile.fail();      //  ��bad���ƣ���û��ô����
infile.get();       //  ÿ�η���һ���ַ�
infile.peek();       //  �����ļ�����һ���ַ���������ʵ�ʶ�ȡ������get()�������û���ͬһ���ַ�
infile.ignore(int, char);    //   ��һ������Ҫ�������ַ������ڶ�����һ���ַ���������ֹͣ
infile.eof();       //  �ж��Ƿ��ļ���β

�򿪱�־��
ios::app  ��ӵ��ļ�β
ios::ate  ���ļ���־����ĩβ������ʼ
ios::trunc  Ĭ�ϣ��ضϲ���д�ļ�
ios::nocreate  �ļ�������Ҳ������
ios::noreplace  �ļ�������ʧ��

// ʹ�ö�������ʽ�����ļ���д
ofstream fout("file.dat", ios::binary);     // �Զ����Ƶķ�ʽ���ļ�
int number = 30;
fout.write((char *)(&number), sizeof(number));  // ��һ������������char����ָ�룬�ڶ��������Ǵ��������ֽڴ�С
//  ������д��ṹ���ascii������
struct OBJECT { int number; char letter;} obj;
obj.number = 15;
obj.char = 'M';
fout.write((char *)(&obj), sizeof(obj));
ifstream fin("file.dat", ios::binary);       //  ��ֻ���ķ�ʽ�򿪶������ļ�
fin.read((char *)(&obj), sizeof(obj));


#include <string>
ifstream input(argv[2]);
string line;
getline(input, line);            //  ����string���е�getlineÿ�ζ���һ��
input >> line;                   //  ÿ��ֻ�ܶ���ո���з��ָ���һ����

#include <sstream>           //  ���ڴ����ַ����ķֶ�����
istringstream stream(line);   // ��������а��ַ����ֶ�
string word;
stream >> word;

����stringstream�����ַ�����������ת������Ϊÿ��ת����Ҫ�漰��������������
stringstream ss;
string result = "10000";
int n = 0;
ss << result;
stream >> n;      // �õ�n����10000

//      ÿ�ζ���һ�У����ڰ��ո���зָһ������ָ��ÿһ������
void getNeighborMatrix(const string &filename, int size) {
	ifstream map_file(filename);        // �������ļ�      ifstream map_file; map_file.open(filename); Ҳ����
	string in_line;
	
	while(map_file >> in_line) {
		istringstream line_stream(in_line);
		string word;
		while(line_stream >> word) {
			stringstream ss;
			int i;
			ss << word;
			ss >> i;
			cout << i << endl;
		}
	}
}




//  ���ת��ʹ��ͬһ��stringstreamʱ����Ҫ����clear()���� ss.clear();
//  ʹ��ss.str("");���ã���Ϊ�������һ��1.2234ʱ��һ�����int�ͣ���С�������������ڣ��������floatʱ���С���������
template<class out_type, class in_type>       // ���������ģ�廯��
out_type convert(const in_type &t) {
	stringstream ss;
	ss << t;                      //      �ؼ��������������
	out_type result;
	ss >> result;
	return result;
}
convert<string>(2.345);      // �õ�2.345���ַ�����ʽ     <>���������ʽ�������ʽ���β��������
convert<double>("2.345");     //   �õ�2.345�ĸ�������ʽ


//   ָ�����������ĶΣ�����GCC�ṩ��һ����չ���ƣ���ʽ��__attribute__((section("name"))) nameΪ.text .data�����Ķ���
__attribute__((section("FOO"))) int global = 42;    // ������global������ΪFOO�Ķ���
__attribute__((section("BAR"))) void foo() {}        //  ������foo������ΪBAR�Ķ���

#pragma data_seg("FOO")           //  windowsƽ̨�½�����global����FOO�Σ��ǵ�Ҫ�ָ���ȥ
int global = 1;
#pragma data_seg(".data")

__attribute__((weak))  weak2 = 2;           //  ����weak2Ϊ������
__attrubute__((weakref)) void foo();        //   ����fooΪ������

int global __attribute__((nocommon));       //  ��δ��ʼ����ȫ�ֱ���global����common����ʽ������ʱglobal��ǿ����

#include <stdio.h>
#include <pthread.h>
int pthread_create(pthread_t* const pthread_attr_t*, void* (*)(void*), void*) __attribute__((weak));
int main() {
	if(pthread_create) {
		printf("this is multi-thread version!\n");
	}else{
		printf("this is single-thread version!\n");
	}
}
��linux�²�ͬ�ı��������̬���ɶ�Ӧ�汾��
gcc pthread.c -o pt    ���к����  this is single-thread version!
gcc pthread.c -lpthread -o pt  ���к����  this is multi-thread version!

TinysHelloWorld.lds      // �򵥵����ӽű�������  ��������a.o  b.o���ļ�
ENTRY(nomain)        //  ָ���������
SECTIONS
{
	. = 0x08048000 + SIZEOF_HEADERS;          //�����õ�ǰ�����ַ
	tinytext : { *(.text) *(.data) *(.rodata) }      // ��ԭ������Ŀ���ļ�����������������κϳ�һ���µ�tinytext��
	/DISCARD/ : { *(.comment) }       //�����������ļ���.comment�ζ���
}
//  ��Ŀ���ļ�����TinysHelloWorld.lds�ű���̬���ӳ�һ����ִ���ļ�
ld -static -T TinysHelloWorld.lds -o TinysHelloWorld TinysHelloWorld.o


gcc ��  -ffunction-sections �� -fdata-sections ѡ�ÿ���������߱������浽�����Ķ���

gcc -fPIC -shared -o Lib.so Lib.c // -shared��ʾ�����������  -fPIC��ʾʹ�õ�ַ�޹ش���PIC(Position-independent Code)
gcc -o Program1 Program1.c ./Lib.so      //  �������붯̬���������


gcc -E hello.c -o hello.i           //  ֻ����Ԥ����
gcc -S hello.c -o hello.s          //   ����
as hello.s -o hello.o       //  ��࣬��������
gcc -c SimpleSection.c -o SimpleSection.o //  ֻ���벻����
ld -static /usr/lib/crt1.o  hello.o    // ����
ld a.o b.o -e main -o ab  // ��main��Ϊ������ڣ��ϲ�����Ŀ���ļ�Ϊһ��
linux�£�ELF��ִ���ļ�Ĭ�ϴӵ�ַ0x08048000��ʼ����
gcc main.c b1.so b2.so -o main -XLinker -rpath ./     // -XLinker -rpath ./  �������ӵ�ǰ·��Ѱ�ҹ������
gcc -o RunSoSimple RunSoSimple.c -ldl            //  ʹ��Dynamical Loading��

file foobar.o          //  �鿴�ļ��ĸ�ʽ��Ϣ
size SimpleSection.o    //  �鿴���������εĴ�С
nm SimpleSection.o        //  �鿴ELF�ļ��ķ��ű�
c++filt _ZN1N1C4funcEi     //  ����C++����֮������� �ָ�Ϊ��_Z N 1N 1C 4func E i
ar -t libc.a                 //  �鿴���ļ���������Щ�ļ�s       windows����lib /LIST libcmt.lib
ldd Program1              // �鿴������ģ����߹������������Щ�����

objdump -d a.o                //   �鿴.text�εĶ����ƺͷ�������
objdump -r a.o                // �鿴Ŀ���ļ����ض�λ��
objdump -h SimpleSection.o   // �鿴����Ŀ���ļ��Ľṹ������
readelf -S SimpleSection.o   //  ��α�ȸ��������Ϣ
readelf -sD Lib.so              //  �鿴elf�ļ��Ķ�̬���ű�����Ĺ�ϣ��


       ///////////////////////////////            �ඨ�����             ////////////////////////////
	   
	   
//  ��һ��������Դ�ļ��У�һ����ֻ�ܱ�����һ�Ρ�����ڶ���ļ��ж���һ���࣬ÿ���ļ��е��ඨ���������ȫ��ͬ��
//  ���Խ��ඨ�����ͷ�ļ��У�����ÿ��ʹ������ļ��ж���ͬ���ķ�ʽ��������
	   
class AllInfo;   // �������	   
//  ֻ�е��ඨ�������֮����ܶ����࣬����಻�ܾ����������͵����ݳ�Ա��Ȼ����ֻҪ����һ���־Ϳ�����Ϊ��������
//  ��ʱ�����Զ���ָ���������͵�ָ���������

//  �������������������Ա�����ǺϷ��ģ�ʹ��δ�����Ա���κγ��Խ���������ʧ�� LINKXXX֮��Ĵ���
//������������private���ƹ��캯�����û������еĸ��Ƴ��Խ��ڱ���ʱ���Ϊ���󣬳�Ա��������Ԫ�еĸ��Ƴ��Խ�������ʱ����
	   
class AllInfo {
private:
	int base;
	int ano_base;
	
	mutable int changable_base;    //  mutableʹ�ñ����������ڳ�����Ա������ʹ�ã����������constʱҲ���Ըı����ֵ
	
	const int const_base;         //  ����ͨ�������б���г�ʼ��
	//   ֮�����������Ϊ��Щ����Ҫ�ڶ����ͬʱ��ʼ��
	//   ��һ����Ĺ��캯������ʱ��ʼ���б��ʱ����Ǹ������г�Ա�������ɵ�ʱ��
	int &ref_base;                //  ����ͨ�������б���г�ʼ�� 
	
	//  ���static�����ݳ�Ա���������ඨ������ⲿ���壬�˴����Ƕ���ֻ������
	//  static��Ա������ΪĬ��ʵ�Σ���Ϊ�ڳ�ʼ���¶���ʱ����ľ�̬��Ա������ֵ���Ѿ�֪����
	static int static_base;          //  ��̬��Ա����ֻ�������ⲿ��ʼ��
	static const int static_const_int_base = 100;     //  ��̬������Ա"����"�����ĳ�ʼ������ֱ�ӳ�ʼ����Ҳ�������ⲿ
	static const double static_const_double_base;      //  ��̬������Ա"������"����ֻ�������ⲿ��ʼ��
	
	//  ����������������һ��������ľ�̬��Ա
	//  ���������������һ����ͨ��Ա��ֻ������ָ���������ָ��
	//  ��Ϊ��ʱ�໹δ������ɣ����ɶ���ʱ��֪��������ٴ�С�ռ䣬�����γɿռ������ѭ��
	//  ���ھ�̬��Ա�����Ͳ����ڶ���ռ������������ͨ��̬��Ա�����ɶ���ʱ�ռ�ķ���
	static AllInfo self_contain;
	
	//  ��һ��������и���ʱ����ָ���Ա����ִ����ͨ��ֵ���ƣ���ָ��Ķ���������ͬһ������
	AllInfo *pAllInfo;              
	
	void do_display(std::ostream &os) const {
		os << base;
	}
public:

	//  ���ڶ���������ⲿ����ʹ�������������Ϊ����public��
	//  �����������ͣ��ⲿ������  AllInfo::index ������
	typedef std::string::size_type index;

     //��ʾ����Ĭ�Ϲ��캯����Ĭ�Ϲ��캯����Ҫ��ʼ���������ͳ�Ա�����������캯���г�ʼ���б����ڶ����ж�����������
	 // ���캯����ʼ��ʽҪ�ȹ��캯���ڲ������ִ�е���
	 // ��ִ���ڲ����֮ǰ��������������ݳ�Ա�Ѿ��ó�ʼ��ʽ���߳�ԱĬ�Ϲ��캯����ʼ����һ����
	 // û��Ĭ�Ϲ��캯���������ͳ�Ա��const�����������͵ĳ�Ա���������ڳ�ʼ���б����ʼ����
	 // ���ں����ߣ������ڹ��캯��������Ǹ�ֵ
	 // ��ʼ���б��г�Ա��ʼ����˳�����ݳ�Ա�����˳�����ʼ���б��е�˳���޹�
	 // �����ͳ�Ա�ĳ�ʼ��ʽ���ǵ��ø����ĳһ�ֿ��õĹ��캯��
	 // û��Ĭ�Ϲ��캯�����಻��������̬���������Ԫ������
	AllInfo(): base(1), ano_base(2), changable_base(3)�� const_base(4), ref_base(base)
	{}
	
	// ��ĳ�Ա�����ĳ�ʼ���б�ĳ�ʼ��˳���Ǹ��ݳ�Ա����������˳����ִ�еģ������ȳ�ʼ��base���ٳ�ʼ��ano_base
	// ��������ֻ��ע�������������������������ͣ������ķ�������
	// �˴����캯������Ĭ��ʵ��֮�󣬻������Ĺ��캯����ͻ�����ɶ���ʱ��֪�������ĸ����캯��
	// �����ʹ�ô�Ĭ��ʵ�εĹ��캯�������������������Ĺ��캯�����Ӷ������˴����ظ�
	AllInfo(int i=1): ano_base(3), base(2)��const_base(4), ref_base(base) {}//  �������Ĺ��캯���������˹��캯��
	
	// �����õ���ʵ�������õĹ��캯�������˴��β����͵������͵�һ����ʽת��
	//���磺AllInfo.issame("abc");  �β�ΪAllInfo���ͣ�������abc����һ����ʱ��AllInfo�����ٰ������ʱ���󴫸�issame
	// AllInfo(const string s): ano_base(3), base(2)��const_base(4), ref_base(base) {}
	// ʹ��explicit�ؼ��ַ�ֹ����Ҫ��ʽת������������ʹ�ù��캯����explicitֻ���������ڲ��Ĺ��캯�������ϣ�����\
	// �����캯��������Ϊexplicitʱ������������ʹ������Ϊת��������
	explicit AllInfo(const string &s): ano_base(3), base(2)��const_base(4), ref_base(base) {}
	
	
	// C++֧�����ֳ�ʼ����ʽ��ֱ�ӳ�ʼ���͸��Ƴ�ʼ����ֱ�ӳ�ʼ������ʼ��ʽ����Բ�����У����Ƴ�ʼ��ʹ��=����
	// ���������Ͷ���ֱ�ӳ�ʼ��ֱ�ӵ�����ʵ��ƥ��Ĺ��캯�������Ƴ�ʼ�����ǵ��ø��ƹ��캯��
	// ���Ƴ�ʼ������ʹ��ָ�����캯������һ��"""��ʱ����"""��Ȼ���ø��ƹ��콫�����ʱ�����Ƶ����ڴ����Ķ���
	// vector<string> svec(5);  ���������ֹ��췽ʽʹ����Ĭ�Ϲ��캯���͸��ƹ��캯�����ȴ�����ʱ�������ٸ��Ƶ�ÿ��λ��
	// AllInfo(const AllInfo&);       �����������壬�����ڲ�����ĺ���Ĭ�϶���inline��
	// ������ݳ�Ա����ָ�� ���� �ڹ��캯���з�����������Դ ʱ������Ը��ƶ���ʱ������������п���
	AllInfo(const AllInfo& a) {}         //  ����(����)���캯��
	
	// ��һ����������ʽ�İ󶨵�thisָ��
	// ��ֵ��������ʵ���У�Ҫ���Ƕ�ԭ����ֵ���ݵ��ͷţ��ر��������������Դ�� ���� ָ�������ָ�� ��ʱ��
	// ��ֵ������ͨ��Ҫ�����ƹ��캯������������ҲҪ��ɵĹ�����һ��ͨ�ù���Ӧ����privateʵ�ú�����
	AllInfo& operator=(const AllInfo& a) {       //  ��ֵ��������  =���غ���
		if(&a != this) {    // ��һ���ж������Ǳ�Ҫ�ģ���Ϊ�Լ�����ĸ�ֵ������һ�㶼Ҫ��ɾ���ٸ���
			AllInfo tmp(a);     //  ����AllInfo�ĸ��ƹ��캯����ʼ����ʱ������ֻ����ָ����Ҫ�����ڴ��ʱ����Ҫ��ô��
			AllInfo* pTmp = tmp.pAllInfo;          //  ����һ��ָ�����ʱ����
			tmp.pAllInfo = pAllInfo;           //  ��ô���������������쳣��ȫ��
			pAllInfo = pTmp;               //  �������ָ�����Ҫͨ�����ַ�ʽ����ֵ
			....                 //  �������ڲ������ĸ�ֵ
		} 
		return *this;         //  ��Ϊtmp�Ǻ����ڲ���ʱ������������������������������ͷ�ָ��ָ����ڴ�ռ�
	}        
	
	// �ϳ��������������󴴽�ʱ��������ÿһ����static��Ա��������Ա�����������������������Ա
	// û�з���ֵ��û���β�
	~AllInfo() {}        //  ��������
	
	
	// ���˺������ò����������ز��������β���Ŀ(������ʽ��thisָ��)��������Ĳ�������Ŀ��ͬ
	// ��Ϊ���Ա�����غ��������βο������Ȳ�������Ŀ��1����Ϊ�Ѿ������޶�Ϊ��һ����������this�β�
	// �������ò��������Խ���������Ŀ�Ĳ�����
	// �ĸ��������صĲ�������    ::       .*       .       ?:
	// + - * & ������һԪ�������ֿ�����Ԫ�������ģ��в�������Ŀ����������Ǽ�Ԫ��
	//���ز����������ȼ�������ԺͲ�������Ŀ�ǹ̶��ġ��ĵã���Ϊ�﷨�����������ģ��������Ƚ����﷨�����ٽ��дʷ�����
	// ���˺������������⣬���ز�����ʹ��Ĭ��ʵ���ǷǷ���
	// = [] () -> �ĸ����������ر��붨��Ϊ��Ա�������������Ϊ�ǳ�Ա�������ڱ���ʱ����
	// ���ϸ�ֵ������ͨ������Ϊ���Ա���� += -= *= /=
	//  �ı����״̬ �� ��������ͽ�����ϵ�� �� ++ -- �����ã�ͨ������Ϊ���Ա����
	//  �ԳƲ�����������������������Ȳ���������ϵ��������λ����������ö���Ϊ��ͨ�ǳ�Ա����
	//  �Ӻſ�����Ϊ�ǳ�Ա���������أ���Ϊ��������ߵĲ��������κ��޸ģ����Ƿ���һ���¶���
	//  AllInfo operator+(const AllInfo&, const AllInfo&);     //     ������������ʽ����������
	//  AllInfo operator+(const AllInfo&);   //  ���ؼӺţ�����һ����ֵ�����Ƽ�����ö���Ϊ��ͨ�ǳ�Ա����
	AllInfo& operator+=(const AllInfo&);  //  ���ͬʱ����+=���أ�����һ����ֵ������+=��ʵ�ֿ�������+���ڲ�ʵ��
	//  ���� a += b;���������ã���������ʾ��  a.operator+=(b);   ���е���
	
	//  ������һ��ɶԶ��壬������֮�����ʹ�÷����㷨�磺find
	inline bool operator==(const AllInfo &lhs, const AllInfo &rhs) {}
	inline bool operator!=(const AllInfo &lhs, const AllInfo &rhs) { return !(lhs==rhs); }
	
	
	//  ��ĳЩ�����������ض���Ϊ��Ԫ����Ϊ��ʱ�������ܿ�����Ҫ�����ڲ�˽�б���
	//  ���ܽ�IO����������Ϊ��ĳ�Ա�������ǣ������ô���ˣ����������ֻ���Ǹ������͵Ķ�����Ϊ���������this
	//  IOstream��û�и��ƹ��캯���ģ����Բ��ܸ��ƣ��������������һ������Ϊ��Ԫ
	friend std::istream& operator>>(std::istream&, AllInfo&);    //  ע���һ���βκͷ������ͣ����ǹ�������
	friend std::ostream& operator<<(std::ostream&, const AllInfo&);
	
	
	//  const����ָ��const�����ָ�������ֻ�ܵ�����const��Ա���������÷�const��Ա��������
	//  �βα�֮���const����Ա��������Ϊ������const����ͬʱ�����������Ͷ�����
	//  �˴�this�������ǣ�  const AllInfo& const this;   this�������ɸģ�������ر����������const��
	//  ���ԶԱȷ������õĺ���
	const AllInfo& workFunc() const {    
		//  ���峣����Ա���������øú����Ķ�������ݲ����޸ģ�ֻ�ɶ����˴�const����������this
		changable_base = 1;            //  const��Ա����ʹ�õĳ�Ա������mutable���η�֮��
		return *this;
	}   
	
	// ����const�����أ��������صĺô��ǣ� a.display()����ʱ����a�Ƿ���const����ѡ��
	// ��a.get().display()������get()�ķ�������ѡ��  a.get().display().set()����Ȼ��ѡ���const�汾
	const AllInfo& display(std::ostream &os) {
		do_display(os);   return *this;     // �ڲ�ͳһ����һ�µ�˽�к�������׼������
	}
	const AllInfo& display(std::ostream &os) const {   // ֻ����һ�ֶ���ʱa.get().display().set()���Ϸ�
		do_display(os);   return *this;
	}
	
	
	
	//  ����this�������ǣ�  AllInfo* const this;   �����Ըı�this����ĵ�ַ�����ǿ��Ըı�thisָ�������
	//  �ӷ���ֵҲ���Կ��������ص�����ͨ����
	AllInfo& get() {   //  �������õĺ����������γ��������õĸ�ʽ������  a.get().get().get();
		return *this;
	} 
	
	//  ����������inline���Ƿ������в�����ǰ��
	//  �����ڲ�����ĳ�Ա���������Զ���Ϊinline����
	//  int getBase();     ֻ����������
	//  ����Ϊinline�ĺ������ⲿ����ʱ�ɼӿɲ���inline��������ʱ����inline���ڶ���ʱ��inlineҲ�ǺϷ���
	inline int getBase() {       
		return base;
	}
	
	
	// ��̬��Ա����û��this�βΣ�����ֱ�ӷ�λ���static��Ա��������ֱ��ʹ�÷�static��Ա����Ȼ��̬��Ա����û��const
	// ��̬��Ա�����ĵ��ã�����ͨ���������������ֱ�ӵ��ã�Ҳ����ͨ���������û���ָ��������͵�ָ���ӵ���
	static void workFunc() {}       //  ��̬������ֻ�ܵ��ó�Ա�ľ�̬����
	
	//  ��Ԫ��������һ���ཫ����ǹ��г�Ա�ķ���Ȩ����ָ���ĺ������࣬�����Թؼ���friend��ʼ��ֻ�ܳ������ඨ���ڲ�
	//  ��Ԫ��������ͨ�ķǳ�Ա��������ǰ�涨���������ĳ�Ա����������������
	//  �����Ԫ��ĳ�Ա�����ж�����أ��������������Ϊ��Ԫ���Ա�����Ķ���Ҫ��friend����
	friend outFunc(AllInfo&);
	friend NewInfoClass& NewInfoClass::get(AllInfo&);     //  ������ĳ�Ա����
	friend NewInfoClass& NewInfoClass::get(int, AllInfo&);    // ��ĳ�Ա������������Ϊ��Ԫ��ͬ��Ҫ������������
	friend class NewInfoClass;         //  �����࣬���Ϊ��Ԫʱ������������г�Ա���������Է���������Ԫ��ϵ�����Ա
};           //  ע��˴������ķֺ��Ǳ���ģ�������������������


//  �˴�����ľ�̬��Ա�����Ķ��壬����Ҫ�ڶ����ͬʱ��ʼ��
//  �˴�������������������������ʱ��̬�������������У�����ֱ��ʹ���������˽�к�����˽�б���
//  ���磺  int AllInfo::static_base = initialBase();  Ҳ�ǺϷ���
int AllInfo::static_base = 1;       //  ��̬��Ա�����ĳ�ʼ��
const double AllInfo::static_const_double_base = 1.1;     //  ͬ��
AllInfo AllInfo::self_contain;       // ����Ĭ�Ϲ��캯��

AllInfo operator+(const AllInfo &lhs, const AllInfo &rhs) {         //  ����
	AllInfo ret(lhs);            //  ���ƹ��캯�����и�ֵ
	ret += rhs;                  //  ���ø��ϲ���������ʵ�ʴ���
	return ret;
}



//  ����Ĺ��캯��������󣬲������ڵĶ����ⲿʹ��explicit����inline��ͬ����static���Ǻ���
//  explicit AllInfo(const string &s = ""): ano_base(3), base(2)��const_base(4), ref_base(base) {}

//  ����C++����Ա�׷�����
AllInfo myinfo();       //  ��ʱ���Ա���ͨ������ʵ���ϴ˾䱻����������Ϊһ��������������Ϊ���� ()
info.get();        //  �˴������ڱ���ʱ����������Ϊ��Ȼ���ܽ�һ����Ա���ʷ���������һ������
��ȷ��������
AllInfo myinfo;           //  Ĭ�Ϲ��캯����ʼ��
AllInfo myinfo = new AllInfo();   //  �ȳ�ʼ��һ��AllInfo��������������ֵ��ʼ��myinfo


//  ����û�ж��幹�캯������ȫ�����ݳ�Ա��Ϊpublic���࣬���Բ������ʼ������Ԫ����ͬ�ķ�ʽ��ʼ�����Ա
struct data {
	int val;
	char *ptr;
};
Data val1 = {0, "abcdefg"}    // �Ϸ���ע�⵽����������ݳ�Ա�����Ĵ�������ʼ��


///         ����ָ����أ�   ����Ԫʵ������ָ��ķ���
//     ��һ�ַ������õĵط����ڣ�����ÿһ����Ҫʹ������ָ����࣬����Ҫר��Ϊ����������ָ�룬��ר������ָ����������Ԫ
// ר�����������㣺1������ָ���������ݶ���˽�еģ�ֻ����Ԫ��ʹ�� 2��ÿ��Ҫʹ������ָ����඼��Ҫ������ָ������ʾ����
//       Smart_Pointer  ��ΪӦ����ĳ�Ա������������ʵ������ָ��
class Smart_Pointer {           //  Smart_Pointer�����г�Ա����˽�е�
	friend class HasPtr;        // HasPtrΪʵ�ʵ�Ӧ���࣬���Ӧ������Ϊ��Ԫ����ֱ�Ӵ���Smart_Pointer�����г�Ա
	int *ip;                    //  ָ��ͬһ�������ָ��
	size_t use;                 //��������
	Smart_Pointer(int *p):ip(p), use(1) {}
	~Smart_Pointer() { delete ip; }
};

class HasPtr {           //  ���Ӧ������ָ�룬����Ҫ������ָ��
public:
	//	 ��������ʼ����һ������ָ�룬��Ϊ���ǵ�һ��ָ��ʵ�εģ�����������һ������ָ�룬�����ַ��������ָ������
	HasPtr(int *p, int i): ptr(new Smart_Pointer(p)), val(i) {}
	//   ���ƹ��캯����ʵ�֣�ע���ʼ���б�ֵ��ͬʱ�����ڲ�������ָ�����++
	HasPtr(const HasPtr &orig): ptr(orig.ptr), val(orig.val) {
		++ptr->use;
	}
	//   ��ֵ���캯��
	HasPtr& operator=(const HasPtr &rhs) {
		++rhs.ptr->use;        //  ��ʹ�µļ�����һ������������֮�⣬����Ҫ�������������ֵ�������������޷�++��--
		if(--ptr->use == 0) {    	//  ����ϵļ�����һ֮���Ƿ���Ҫɾ��
			delete ptr;
		}
		ptr = rhs.ptr;        //  ���Լӹ��������ټ�
		val = rhs.val;
		return *this;
	}
	//   ����������������Ϊ0ʱ��ɾ������ָ�룬����ָ��������ʱ�򣬽��洢��ָ��ָ��Ķ���һ��delete����
	~HasPtr() {
		if(--ptr->use == 0) delete ptr;
	}
	
	int *get_ptr() { return ptr->ip; }
	void set_ptr(int *p) { ptr->ip = p; }
	
	int get_ptr_val() { return *(ptr->ip); }
	void set_ptr_val(int i) { *(ptr-ip) = i; }
	
private:
	Smart_Pointer *ptr;
	int val;
};

//   Ӳ�Ե�ǳ���Ƶ�����ָ�루ʵ�����Ѿ�û������ָ���ˣ����Ƕ����ٿռ������ģ�������ɣ���string�������ôʵ�ֵ�
class HasPtr {
public:
	HasPtr(const int &p, int i): ptr(new int(p)), val(i) {}    //  �˴��ǹؼ���ͬ��ֱ��newһ���¿ռ����洢ֵ
	
	HasPtr(const HasPtr &orig): ptr(new int(*origin.ptr)), val(orig.val) {}  // �ؼ���֮ͬ��������ʱҲ���ռ�
	
	HasPtr& operator=(const HasPtr &rhs) {        //  ������ֵʱ��Ҳ����ȷ��
		*ptr = *rhs.ptr;             //  �ܱ���ֵ��ʱ���ڴ�ռ�϶��Ѿ����٣�ֱ�Ӹ�ֵ�ͺ�
		val = rhs.val;
		return *this;
	}
	
	~HasPtr() { delete ptr; }
private:
	int *ptr;
	int val;
};


��C/C++�У���������Ϊ�����Ĳ������д���ʱ��������Զ��˻�Ϊͬ���͵�ָ��



//     Linux����C++����Shell���н��� 
#include <stdio.h>  
#include <cstdlib>  
int main()  
{  
 printf("I am a picture capture\n");  
//����import����ص�ǰȫ��  
//�ļ����ǡ�1.jpg��  
 system("import -window root 1.jpg");  
 printf("Done!\n");  
 return 0;  
} 