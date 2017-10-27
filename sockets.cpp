int sockfd;          //  用于记录socket返回的短ID
int listenfd, connfd;      // 同上

//通用套接字地址结构 
struct sockaddr {
	uint8_t sa_len;
	sa_family_t sa_family;   //  AF_XXX 
	char sa_data[14];
};
// 主要用于强制地址转换，例如传给bind函数的第二个参数，就要强制转换成上述类型，否则编译器会警告
int bind(int, struct sockaddr *, socklen_t);

struct in_addr {
	in_addr_t s_addr;
};
struct sockaddr_in {  // ipv4套接字地址结构
	uint8_t sin_len;           // 结构体长度，值为16，参见后面字节大小  1字节
	sa_family_t sin_family;         //  1字节
	in_port_t sin_port;      // 端口号   2字节
	struct in_addr sin_addr;   //  ipv4地址  4字节
	char sin_zero[8];     // 不使用        8字节
};
struct sockaddr_in servaddr;     //  socket地址结构体
servaddr.sin_family = AF_INET;
servaddr.sin_port = htons(13);        // 设置端口号，注意要把端口号转换成socket对应格式

// 在客户端argv[1]中存储的点分十进制数通过inet_pton转换后放入sin_addr，argv[1]里面存储的是服务器地址
inet_pton(AF_INET, argv[1], &servaddr.sin_addr);   
// 在服务器端把自己的接收地址设为任意地址，一般在服务器上有多个IP地址时有用
servaddr.sin_addr.s_addr = htonl(INADDR_ANY);       


///      客户端连接服务器的实例
//  AF_INET指明协议族，SOCK_STREAM指明是字节流
if(sockfd = socket(AF_INET, SOCK_STREAM, 0) < 0) err_sys("socket error");
// 客户端通过servaddr指明的完整IP和端口号进行连接，底层有一个connect阻塞再恢复的过程，恢复后sockfd已经建立连接
connect(sockfd, (SA *)&servaddr, sizeof(servaddr));          
n = read(sockfd, recvline, MAXLINE);    // 将sockfd内的内容读入recvline字符数组，返回值表示读入的字节长度
recvline[0] = 0;          // 等价于设置'\0'数组结束符
fputs(recvline, stdout);   // 在屏幕输出结果


///   服务器端进行初始化等待连接的实例
listenfd = socket(AF_INET, SOCK_STREAM, 0);
bind(listenfd, (SA *)&servaddr, sizeof(servaddr));  
listen(listenfd, LISTENQ);
while(true) {
	connfd = accept(listenfd, (SA *)NULL, NULL);
	ticks = time(NULL);   //  获得以秒为单位的时间累计值
	snprintf(buff, sizeof(buff), "%.24s\r\n", ctime(&ticks));  // 进行一定转换后放入buff数组
	write(connfd, buff, strlen(buff));     // 通过socket进行发送
	close(connfd);
}

///   客户端/服务器模式常用基本函数
#include <sys/socket.h>
int socket(int family, int type, int protocol);    //  成功返回非负描述符，失败返回-1

// TCP套接字中，调用connect会激活TCP的三路握手过程
int connect(int sockfd, const struct sockaddr *servaddr, socklen_t addrlen);

// 把本地协议地址赋予给一个套接字
int bind(int sockfd, const struct sockaddr &myaddr, socklen_t addrlen);

// 变主动套接字为被动套接字，通常的监听套接字描述符
int listen(int sockfd, int backlog);       // backlog表示内核为相应套接字排队的最大连接个数
// 内核给每一个监听的套接字维护两个队列：未完成连接队列(收到客户端SYN) 和 已完成连接队列(完成三次握手)

//  TCP服务器调用，从已完成队列队头返回下一个已完成连接
//  如果成功返回，其值为内核自动生成的一个全新的描述符，代表与所返回客户的TCP连接，成为连接套接字描述符
int accept(int sockfd, struct sockaddr *cliaddr, socklen_t *addrlen);//后两个参数用于得到返回值(即客户端的socket)

//  没有bind或者设置本地端口号时，通过此函数得到内核赋予的本地IP地址和端口号
int getsockname(int sockfd, struct sockaddr *localaddr, socklen_t *addrlen);   //后两个参数用于得到返回值

//  服务器exec的某个新程序，获得客户身份的方式
int getpeername(int sockfd, struct sockaddr *peeraddr, socklen_t *addrlen);   //后两个参数用于得到返回值

//  处理信号的函数，第一个参数是信号，第二个是指向函数的指针，返回一个指向函数的指针
void (*signal(int signo, void (*func)(int)))(int);
//  简化上面函数的声明
typedef void Sigfunc(int);
Sigfunc *signal(int signo, Sigfunc *func);

//  wait用于处理僵尸进程，都返回两个值；如果调用wait时没有已终止的子进程但有运行的子进程，则阻塞等待第一个终止的子进程
#include <sys/wait.h>
// statloc存储返回的子进程终止状态
pid_t wait(int *statloc);
// waitpid的pid参数允许我们指定想等待的进程ID，值-1表示第一个终止的子进程，放在while循环中则循环终止所有的子进程
pid_t waitpid(pid_t pid, int *statloc, int options);

#include <unistd.h>
int close(int sockfd);        //  关闭套接字，成功返回0，出错返回-1


///   字节序 处理函数
#include <netinet/in.h>
uint16_t htons(uint16_t host16bitvalue);
uint32_t htonl(uint32_t host32bitvalue);         //  均返回：网络字节序的值(即大端序列)

uint16_t ntohs(uint16_t net16bitvalue);      
uint32_t ntohl(uint32_t net32bitvalue);          //  均返回：主机字节序的值(视主机使用的字节序而定)


///   ascii字符串和网络字节序的二进制值之间的转换
#include <arpa/inet.h>
//   以下三个都不推荐使用
int inet_aton(const char *strptr, struct in_addr *addptr);   // 将c串转换成一个32位的网络字节序二进制值，放入addptr
in_addr_t inet_addr(const char *strptr);   //  字符串有效则返回32位二进制网络字节序的IPv4地址，否则为INADDR_NONE
char *inet_ntoa(struct in_addr inaddr);    //  返回一个指向点分十进制数串的指针

// 将strptr指向的字符串转换后放入addrptr，成功则返回1，否则返回0，记忆方法： strptr -> addrptr
int inet_pton(int family, const char *strptr, void *addrptr);     // p为presentation，n为numeric
// 从数值格式转换到表达格式，len参数是目标存储单元的大小，调用成功返回strptr
const char *inet_ntop(int family, const void *addrptr, char *strptr, size_t len);

///  内存内容的处理
#include <strings.h>
void bzero(void *dest, size_t nbytes);
void bcopy(const void *src, void *dest, size_t nbytes);
void bcmp(const void *ptr1, const void *ptr2, size_t nbytes);

void *memset(void *dest, int c, size_t len);       // 所有的memXXX函数都需要一个长度参数，这个参数总是最后一个
void *memcpy(void *dest, const void *src, size_t nbytes);  // dest = src 用来记录操作的先后顺序
int memcpy(const void *ptr1, const void *ptr2, size_t nbytes);