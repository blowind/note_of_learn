int sockfd;          //  ���ڼ�¼socket���صĶ�ID
int listenfd, connfd;      // ͬ��

//ͨ���׽��ֵ�ַ�ṹ 
struct sockaddr {
	uint8_t sa_len;
	sa_family_t sa_family;   //  AF_XXX 
	char sa_data[14];
};
// ��Ҫ����ǿ�Ƶ�ַת�������紫��bind�����ĵڶ�����������Ҫǿ��ת�����������ͣ�����������ᾯ��
int bind(int, struct sockaddr *, socklen_t);

struct in_addr {
	in_addr_t s_addr;
};
struct sockaddr_in {  // ipv4�׽��ֵ�ַ�ṹ
	uint8_t sin_len;           // �ṹ�峤�ȣ�ֵΪ16���μ������ֽڴ�С  1�ֽ�
	sa_family_t sin_family;         //  1�ֽ�
	in_port_t sin_port;      // �˿ں�   2�ֽ�
	struct in_addr sin_addr;   //  ipv4��ַ  4�ֽ�
	char sin_zero[8];     // ��ʹ��        8�ֽ�
};
struct sockaddr_in servaddr;     //  socket��ַ�ṹ��
servaddr.sin_family = AF_INET;
servaddr.sin_port = htons(13);        // ���ö˿ںţ�ע��Ҫ�Ѷ˿ں�ת����socket��Ӧ��ʽ

// �ڿͻ���argv[1]�д洢�ĵ��ʮ������ͨ��inet_ptonת�������sin_addr��argv[1]����洢���Ƿ�������ַ
inet_pton(AF_INET, argv[1], &servaddr.sin_addr);   
// �ڷ������˰��Լ��Ľ��յ�ַ��Ϊ�����ַ��һ���ڷ��������ж��IP��ַʱ����
servaddr.sin_addr.s_addr = htonl(INADDR_ANY);       


///      �ͻ������ӷ�������ʵ��
//  AF_INETָ��Э���壬SOCK_STREAMָ�����ֽ���
if(sockfd = socket(AF_INET, SOCK_STREAM, 0) < 0) err_sys("socket error");
// �ͻ���ͨ��servaddrָ��������IP�Ͷ˿ںŽ������ӣ��ײ���һ��connect�����ٻָ��Ĺ��̣��ָ���sockfd�Ѿ���������
connect(sockfd, (SA *)&servaddr, sizeof(servaddr));          
n = read(sockfd, recvline, MAXLINE);    // ��sockfd�ڵ����ݶ���recvline�ַ����飬����ֵ��ʾ������ֽڳ���
recvline[0] = 0;          // �ȼ�������'\0'���������
fputs(recvline, stdout);   // ����Ļ������


///   �������˽��г�ʼ���ȴ����ӵ�ʵ��
listenfd = socket(AF_INET, SOCK_STREAM, 0);
bind(listenfd, (SA *)&servaddr, sizeof(servaddr));  
listen(listenfd, LISTENQ);
while(true) {
	connfd = accept(listenfd, (SA *)NULL, NULL);
	ticks = time(NULL);   //  �������Ϊ��λ��ʱ���ۼ�ֵ
	snprintf(buff, sizeof(buff), "%.24s\r\n", ctime(&ticks));  // ����һ��ת�������buff����
	write(connfd, buff, strlen(buff));     // ͨ��socket���з���
	close(connfd);
}

///   �ͻ���/������ģʽ���û�������
#include <sys/socket.h>
int socket(int family, int type, int protocol);    //  �ɹ����طǸ���������ʧ�ܷ���-1

// TCP�׽����У�����connect�ἤ��TCP����·���ֹ���
int connect(int sockfd, const struct sockaddr *servaddr, socklen_t addrlen);

// �ѱ���Э���ַ�����һ���׽���
int bind(int sockfd, const struct sockaddr &myaddr, socklen_t addrlen);

// �������׽���Ϊ�����׽��֣�ͨ���ļ����׽���������
int listen(int sockfd, int backlog);       // backlog��ʾ�ں�Ϊ��Ӧ�׽����Ŷӵ�������Ӹ���
// �ں˸�ÿһ���������׽���ά���������У�δ������Ӷ���(�յ��ͻ���SYN) �� ��������Ӷ���(�����������)

//  TCP���������ã�������ɶ��ж�ͷ������һ�����������
//  ����ɹ����أ���ֵΪ�ں��Զ����ɵ�һ��ȫ�µ��������������������ؿͻ���TCP���ӣ���Ϊ�����׽���������
int accept(int sockfd, struct sockaddr *cliaddr, socklen_t *addrlen);//�������������ڵõ�����ֵ(���ͻ��˵�socket)

//  û��bind�������ñ��ض˿ں�ʱ��ͨ���˺����õ��ں˸���ı���IP��ַ�Ͷ˿ں�
int getsockname(int sockfd, struct sockaddr *localaddr, socklen_t *addrlen);   //�������������ڵõ�����ֵ

//  ������exec��ĳ���³��򣬻�ÿͻ���ݵķ�ʽ
int getpeername(int sockfd, struct sockaddr *peeraddr, socklen_t *addrlen);   //�������������ڵõ�����ֵ

//  �����źŵĺ�������һ���������źţ��ڶ�����ָ������ָ�룬����һ��ָ������ָ��
void (*signal(int signo, void (*func)(int)))(int);
//  �����溯��������
typedef void Sigfunc(int);
Sigfunc *signal(int signo, Sigfunc *func);

//  wait���ڴ���ʬ���̣�����������ֵ���������waitʱû������ֹ���ӽ��̵������е��ӽ��̣��������ȴ���һ����ֹ���ӽ���
#include <sys/wait.h>
// statloc�洢���ص��ӽ�����ֹ״̬
pid_t wait(int *statloc);
// waitpid��pid������������ָ����ȴ��Ľ���ID��ֵ-1��ʾ��һ����ֹ���ӽ��̣�����whileѭ������ѭ����ֹ���е��ӽ���
pid_t waitpid(pid_t pid, int *statloc, int options);

#include <unistd.h>
int close(int sockfd);        //  �ر��׽��֣��ɹ�����0��������-1


///   �ֽ��� ������
#include <netinet/in.h>
uint16_t htons(uint16_t host16bitvalue);
uint32_t htonl(uint32_t host32bitvalue);         //  �����أ������ֽ����ֵ(���������)

uint16_t ntohs(uint16_t net16bitvalue);      
uint32_t ntohl(uint32_t net32bitvalue);          //  �����أ������ֽ����ֵ(������ʹ�õ��ֽ������)


///   ascii�ַ����������ֽ���Ķ�����ֵ֮���ת��
#include <arpa/inet.h>
//   �������������Ƽ�ʹ��
int inet_aton(const char *strptr, struct in_addr *addptr);   // ��c��ת����һ��32λ�������ֽ��������ֵ������addptr
in_addr_t inet_addr(const char *strptr);   //  �ַ�����Ч�򷵻�32λ�����������ֽ����IPv4��ַ������ΪINADDR_NONE
char *inet_ntoa(struct in_addr inaddr);    //  ����һ��ָ����ʮ����������ָ��

// ��strptrָ����ַ���ת�������addrptr���ɹ��򷵻�1�����򷵻�0�����䷽���� strptr -> addrptr
int inet_pton(int family, const char *strptr, void *addrptr);     // pΪpresentation��nΪnumeric
// ����ֵ��ʽת��������ʽ��len������Ŀ��洢��Ԫ�Ĵ�С�����óɹ�����strptr
const char *inet_ntop(int family, const void *addrptr, char *strptr, size_t len);

///  �ڴ����ݵĴ���
#include <strings.h>
void bzero(void *dest, size_t nbytes);
void bcopy(const void *src, void *dest, size_t nbytes);
void bcmp(const void *ptr1, const void *ptr2, size_t nbytes);

void *memset(void *dest, int c, size_t len);       // ���е�memXXX��������Ҫһ�����Ȳ�������������������һ��
void *memcpy(void *dest, const void *src, size_t nbytes);  // dest = src ������¼�������Ⱥ�˳��
int memcpy(const void *ptr1, const void *ptr2, size_t nbytes);