


###########################   引入命令   ################################

use strict;   ##  要求变量使用前都声明，字符串加引号，引用为实引用

no strict 'refs'; ##  取消引用限制

use 5.012  ## 这个版本之后的perl默认开启strict

use warnings  ##  开启警告 也可以在#!中加入-w来开启

use diagnostics ##  开启诊断 ???????????????

use autodie  ## 遇到系统级错误时（例如输入输出错误），自动die




###########################   perl模块   ################################

安装模块：已下载模块
perl Makefile.PL PREFIX=/Users/home/Ginger  ### 安装目录
或者
perl Makefile.PL INSTALL_BASE=/User/home/Ginger
make
make test
make install


引入模块路径(shell命令)：
export PERL5LIB=/User/home/Ginger
或者
use lib qw(/User/home/Ginger) 使用 /User/home/Ginger路径下的模块    ### 也是在编译时执行
或者
BEGIN { unshift @INC, '/home/gilligan/lib'; }  ###  加搜索模块的路径，必须有BEGIN
放入begin模块里面的是正常的perl语句，会在编译时执行

use  作用的内容都是在编译期执行!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

require List::Util;        ####  require 用于load modules，区别在于其运行于执行期
BEGIN {
	require List::Util;
	List::Util->import(...);
}           ###      等价于  use List::Util;

use constant LIB_DIR => '/home/gilligan/lib';   ### 定义编译期的常量
use lib LIB_DIR;     ###  编译期引入

use FindBin qw($Bin);   ###  此模块将当前目录路径放入$Bin，自带模块，可在编译器之外执行
use lib $Bin;       ###  将库放入@INC
use lib "$Bin/../lib";

### 需要安装的local::lib模块，用于指定CPAN安装模块的位置
% perl -Mlocal::lib  显示该模块的设定变量
% cpan -I Set::Crossproduct    -I标记表示用local::lib指定的路径安装模块


使用Module::Build模块来进行安装工作
perl Build.PL --install_base /Users/home/Ginger
perl Build
perl Build test
perl Build install

未下载模块：
perl -MCPAN -e shell
cpan>install CGI::Prototype
或者
cpan CGI::Prototype Test::Pod

perl -MCPANPLUS -e shell   此CPANPLUS模块在5.010之后自带
CPAN Terminal> i CGI::Prototype          模块安装
或者
cpanp i CGI::Prototype HTTP::Cookies::Safari Test::Pod


perldoc File::Basename  调出模块的说明文档

######       函数式接口    #####
use File::Basename;     ### 引入fileparse, basename, dirname三个子例程，能自动识别所运行的系统
use File::Basename  qw/  basename /;   仅仅引入basename函数
use File::Basename  ('fileparse', 'basename');  仅仅引入fileparse和basename
use File::Basename  qw/ /;   什么也不提取，也可以写成
use File::Basename ();   此时已然可以使用函数，不过要包含完整包名
my  $dirname = File::Basename::dirname($name);    不管用不用use，此调用都成立

use File::Basename;  表示引入默认subroutine
use File::Basename ()； 表示什么subroutine都不引入

#####       面向对象接口   ######    此类接口不引入任何子例程
use  File::Spec;     ### 模块什么都没引入，整个接口使用类方法
###   提供方法catfile（注意与函数的区别，File::Spec是一个对象，每次使用都要完整写出）
use  File::Spec::Functions    ###   变面向对象接口为函数式接口
my $new_name = File::Spec->catfile($dirname1, $dirname2, $dirname3, $basename);  
###  调用类方法catfile合并$dirname和$basename

use File::Path;
rmtree    删除目录里有目录的情况

use File::Find;             ###  查询某目录及其子目录下的某类匹配文件
##  公共参数  $File::Find::name   所有目录和文件的名字，包括完整的相对路径 -d -f测试不成功
##			  $File::Find::dir    所有目录的名字   -d测试不成功
##            $_                  所有文件的名字   -d -f 测试成功
my @path = 'E:\sharedAll\path_reconstruction\delay\TestNetwork\script\test';
sub wanted {
    if ( -f $File::Find::name ) {
        if ( $File::Find::name =~ /\.pl$/ ) {
            print "$File::Find::name\n";
        }
    }
}
find(\&wanted, @path);


use  Math::BigInt;     ###  引入一个对象类
my $value = Math::BigInt->new(2);        ####   对一类实例化，将其引用赋值给$value
$value->bpow(1000);         ###  调用方法bpow()  即求幂
print $value->bstr(), "\n";       ###  调用方法输出


use  Path::Class;
my  $dir  =  dir( qw(Users fred lib) );
my  $subdir  =  $dir->subdir( 'perl5' );  # Users/fred/lib/perl5
my  $parent  =  $dir->parent;             # Users/fred
my $windir = $dir->as_foreign( 'Win32' ); # Users\fred\lib

###  Excel相关

use Spreadsheet::WriteExcel;
my $workbook = Spreadsheet::WriteExcel->new('perl.xls');  ## 创建一个excel文件
my $worksheet = $workbook->add_worksheet();   ### 添加一个sheet作为操作对象

$worksheet->write('A1', 'Hello Excel!');   ###  在A1的位置添加字符串
$worksheet->write(0, 0, 'Hello Excel');    ### 另一种写法，用坐标标示位置A1

my $red_backgroud = $workbook->add_format(   ### 修改excel的格式
	color => 'white',
	bg_color => 'red',
	bold => 1,
);

my $bold = $workbook->add_format(
	bold => 1,
);

$worksheet->write(0, 0, 'Colored cell', $red_backgroud);
$worksheet->write(0, 1, 'bold cell', $bold);

my $product_code = '01234';
$worksheet->write_string(0, 2, $product_code);     ###  加入的是string，所以不会去掉开头的0

$worksheet->write('A2', 37);
$worksheet->write('B2', 42);
$worksheet->write('C2', '=A2 + B2');       ### 将A2 和 B2 的内容相加放入C2，就是excel的用法


use Module::CoreList;
print $Module::CoreList::version{5.01400}{CPAN};      ## 查看CPAN模块的版本号
print Module::CoreList->first_release('Module::Build');     ##  该模块第一次出现是在perl的哪个版本


use  CGI  qw(:all);         ###       all修饰标签，引入模块内所有函数
use  DBI;
use  DateTime; 


use threads;

my $t1 = threads->create(程序名, 参数，参数.....);

my $t2 = async{   say "hello world"; };     ###   创建线程的两种方法


use IPC::System::Simple qw(system systemx capture capturex);
比系统自带的system更具可移植性
systemx保证不使用shell
capture类似反引号

use Try::Tiny;        ###  类似C语言的异常捕获，需安装
try {
	...; # some code that might throw errors
}
catch {
	...; # some code to handle the error
}
finally {
	...;
}

use List::Util;   ###  perl自带库，C语言实现的处理list的工具
use List:Util qw(first);
my $first_match = first {/\bPebbles\b/i} @charaters;     ### 找到list中第一个出现Pebbles的标量
use List::Util qw(sum);
my $total = sum( 1..1000 ); # 500500  求和utility
use List::Util qw(max);
my $max = max( 3, 5, 10, 4, 6 );   ###  求list中最大值 ，仅适用数字
use List::Util qw(maxstr);
my $max = maxstr( @strings );  ###  适用string
use List::Util qw(shuffle);
my @shuffled = shuffle(1..1000); # randomized order of elements  ，打乱顺序


use List:MoreUtils   ###  更强大，需安装
use List::MoreUtils qw(none any all);
if (none { $_ > 100 } @numbers) {    ##  一个符合条件的也没有
	print "No elements over 100\n"
} elsif (any { $_ > 50 } @numbers) {   ### 只要有一个符合条件就成立
	print "Some elements over 50\n";
} elsif (all { $_ < 10 } @numbers) {   ###  所有的都符合条件
	print "All elements are less than 10\n";
}

use List::MoreUtils qw(mesh);      ####  交叉合并list，一次从各个list取一个元素放入最终list
my @abc = 'a' .. 'z';
my @numbers = 1 .. 20;
my @dinosaurs = qw( dino );
my @large_array = mesh @abc, @numbers, @dinosaurs;
####  结果：a 1 dino b 2 c 3 ....
 
use autodie qw (open system: socket)   ###  与系统交互产生错误时自动die，此处只在open文件时die

use IO::Handle;        ### 5.14之前要显示声明，输入输出的基本类
open my $fh, '>', $filename or die '..';
$fh->print('Coconut headphones');
$fh->close;

use IO::File;       ### 标准发行版自带
my $fh = IO::File->new( '> castaways.log' ) or die "Could not create filehandle:$!";
my $read_fh = IO::File->new('castaways.log', 'r');
my $write_fh = IO::File->new('castaways.log', 'w');
my $append_fh = IO::File->new( 'castaways.log', O_WRONLY|O_APPEND );

my $temp_fh = IO::File->new_tmpfile;    ## 开临时文件存储
$temp_fh->close or die "Could not close file: $!";

use IO::Tee;   ### 同一个输入流多个输出流
open my $log_fh, '>>', 'castaways.log';
open my $scalar_fh, '>>', \ my $string;
my $tee_fh = IO::Tee->new($log_fh, $scalar_fh);
print $tee_fh "The radio works in the middle of the ocean!\n";

use IO::Pipe;
my $pipe = IO:Pipe->new;
$pipe->reader("$^X -V");        ### $^X是当前perl脚本  从外部命令读入数据
while(<$pipe>) {
	print "Read: $_";
}
$pipe->writer($command);
for(1..10) {
	print $pipe "I can count to $_\n";
}

use IO::Null;
my $null_fh = IO::Null->new;    ## 比特桶，无底洞

my $debug_fh = $Debug ? *STDOUT : IO::Null->new;     ### 通过设置debug flag来控制输出方向
$debug_fh->print("Hey, the radio's not working!");

use Regexp::Common qw(URI);    ### 提供复杂的正则表达式模式，使用哈希$RE映射各个regexp的引用
while(<>) {
	print if /$RE{URL}{HTTP}/;     ### 匹配http的网址
}
use Regexp::Common qw(net);
while( <> ) {
	say if /$RE{net}{IPv4}/;      ### 匹配ipv4地址
}
use Regexp::Common qw(number);
while( <> ) {
	say if /$RE{num}{int}/;      ####  匹配10进制整数，输出匹配的整行
}
while( <> ) {
	say if /$RE{num}{int}{ -base => 16 }/;      ####  匹配16进制整数，输出匹配的整行
}
while( <> ) {
	say $1 if /$RE{num}{int}{ -base => 16 }{-keep}/; ####  匹配10进制整数，仅输出匹配的数字
}

use Bloom::Filter;
 ## capacity表示Bloom Filter容量   error_rate 配置的最大错误率
my $bf = Bloom::Filter->new(capacity=>10, error_rate=>0.001); 
$bf->length  ## 存储Bloom Filter的位长度
$bf->on_bits  ## 存储置位on的bit的数目
$bf->key_count()   ##  已经存储的元素数目
 ##  将关键字加入Bloom Filter 超过其配置容量时会报错，返回undef 
$bf->add( @keys );
 ##  检查关键字@key是否在Bloom Filter里面，存在返回1，否则返回0，最终返回一个01的list
$bf->check(@key);


###########################   关键字   ################################
my  声明一个变量是局部私有变量

use 5.010;
state  声明一个变量是全局私有变量，类似C语言静态局部变量的意思，注意use不可少，不然报错

sub  声明一个subroutine子例程
sub marine {}  或者 sub marine() {}
不带圆括号表示形参个数任意
带圆括号表示不能有形参





###########################   系统参数   ################################
"
STDIN,  STDOUT,  STDERR,  DATA,  ARGV,  ARGVOUT

$!  系统错误消息

$0  程序名  例子："$0:Not enough arguments\n"

$| = 1  使当前选中的默认输出每次立马flush

$/  设置行结束符

$?  保存子进程的退出状态

$@  eval捕获的错误信息

@INC  perl搜寻模块的路径集合

__FILE__

__LINE__

__WARN__

__END__        文件结尾宏

%ENV   例如：$ENV{PATH}
$ENV{'PATH'} = "/home/rootbeer/bin:$ENV{'PATH'}";    ###   添加自己的环境变量到最前面
delete $ENV{'IFS'};

$^I = ".bak";   设置源文件备份的后缀为.bak

perl -V  查看perl相关信息

"


###########################   调试相关   ################################


perl  debug相关
命令行% perl -d script.pl
"
s  进行单步跟踪
x  \%total_bytes  显示hash映射的内容
x  sort keys %total_bytes   显示key组成的一个list 

"

use Data::Dumper   ###  用于将复杂的数据结构的内容 以perl代码的形式 输出
my %total_bytes;
while(<>) {
	my ($source, $destination, $bytes) = split;
	%total_bytes{$source}{$destination} += $bytes;
}
print Dumper(\%total_bytes);

显示的结果：
$VAR1 = {
		'thurston.howell.hut' => {
			'lovey.howell.hut' => 1250
		},
		'ginger.girl.hut' => {
			'maryann.girl.hut' => 199,
			'professor.hut' => 1218
		},
		'professor.hut' => {
			'gilligan.crew.hut' => 1250,
			'lovey.howell.hut' => 1360
		}
};

use Data::Dumper;         ###  随perl安装程序自带
$Data::Dumper::Purity = 1; #declare possibly self-referencing structures，这样才能显示自引用
my @data1 = qw(one won);
my @data2 = qw(two too to);
push @data2, \@data1;
push @data1, \@data2;
print Dumper(\@data1, \@data2);

显示的结果：
$VAR1 = [
	'one',
	'won',
	[
		'two',
		'too',
		'to',
		[ ]
	]
];
$VAR1->[2][3] = $VAR1;
$VAR2 = $VAR1->[2];

print Data::Dumper->Dump([\@data1, \@data2], [qw(*data1 *data2)]);  ## 第二个引用说明变量名
###  参考 typeglobs

#######    其他可以输出复杂数据结构的module

use Data::Dump  qw(dump);    ##  需额外安装
dump(\%total_byte);

use Data::Printer;      ##  需额外安装
p(%total_bytes);

use YAML;
print Dump(\%total_bytes);     ## 使用通过yaml格式输出复杂结构，ruby和python可以直接用

use JSON;
print to_json(\%total_bytes, {pretty => 1});   ### 输出可以被直接作为json对象使用的复杂结构
my $hash_ref = from_json($json_string);

#####    存储复杂数据结构的module

use Storable;        ## 所有东西必须放入引用
my @data1 = qw(one won);
my @data2 = qw(two too to);
push @data2, \@data1;
push @data1, \@data2;
my $frozen = freeze [\@data1, \@data2]; ### freeze返回一个二进制的字符串
my $data = thaw($frozen);      ###  读入上面存储的数据
##  方法2
nstore [\@data1, \@data2], $filename;  ### 存储二进制内容到文件
###   nstore = network order，跟大端序小端序有关
my $array_ref = retrieve $filename;   ### 从文件中读入二进制形式的存储的复杂数据结构


my @a = (a, b ,c, refs);
my @b = @a;   ## 此处拷贝称为shallow copy，所有内容都复制一份新的，包括引用地址，但只拷贝第一层
### 但以上只复制到这一层，引用指向的内容不拷贝，所以@a和@b的第4个元素指向同一个内存地址

### 当我们需要引用指向的内容也拷贝而不是两个list元素都指向同一个内容时，称为deep copy
### deep copy要使用Storable

use Data::Dumper;
use Storable qw(freeze thaw);
my @provisions = qw(hat sunscreen);
my @science_kit = qw(microscope radio);
push @provisions, \@science_kit;
my $frozen = freeze \@provisions;
my @packed = @{thaw $frozen};
### 此时packed里面第二层引用也是一份完全独立的拷贝，除了内容相同其他与@science_kit无关

上面过程可以通过 Storable 内函数 dclone 一步完成
my @packed = @{ dclone \@provisions };


###########################   基本函数   ################################

函数：chop  
强制去掉后接变量的最后一个字符


函数：chomp
去掉字符串结尾的"\n"，没有的话不做处理
用在列表中，删除列表中每个元素结尾的换行符
chomp("字符串"或变量或列表);


函数：scalar
强制将list变量转换为scalar
scalar @list 在列表上下文中返回list参数个数


函数：say
类似print，区别在于在结尾自动加上"\n"，5.010版本之后支持


函数：sort
print sort <>  ##  模拟shell中sort的源代码，默认按ascii码顺序比较大小
my @numbers = sort {$a<=>$b} @some_numbers;   ###  按从小到大的升序排列
用subroutine显示比较时，-1表示按$a,$b 原来的顺序，0表示顺序无所谓，1表示改变$a,$b 的顺序
简单形式中{$a<=>$b}表示按数字的升序排序，{$b<=>$a}表示按数字的降序排序
也可以用cmp替换<=>，cmp在字符串比较中使用
sub by_score { $score{$b} <=> $score{$a} }  ###  根据hash的values来降序排序keys

###  得到排序后的元素在原来的list中的位置
my @input = qw(Gilligan Skipper Professor Ginger Mary Ann);
my @sorted_positions = sort { $input[$a] cmp $input[$b] } 0..$#input;   ### 拿索引作为排序内容
print "@sorted_positions\n";

函数：reverse
my @a = reverse qw(a b c d e f g);
返回逆序的操作list


函数：push
push (@array, 0)  or  push (@array, @others)
入栈，第一个参数为栈list，第二个为要入的元素，可以是标量或者列表
每次入栈的元素添加到列表的最右边！！！！！


函数：pop
pop(@array)
每次出栈的元素为列表中最右边的元素！！！！！


函数：unshift
unshift(@array, 0)
添加一个值到列表的最左边！！！！


函数：shift
shift(@array)
删除列表最左边的一个值！！！！！  
###   注意到unshift和shift合起来不是先进先出的队列，unshift和pop   或者  shift和push  联合才是


函数：splice
splice(@array, 1, 2, qw(wilma))
返回值为参数1和参数2之间删掉的元素
参数1：为要操作的列表
参数2：操作起始下标（删除元素包含起始元素）
参数3：删除长度（可省略，省略表示后面元素全部删除），为0表示插入操作
参数4：替换列表（可省略，省略表示只删除不替换）


函数：each
while( my ($index, $value) = each (@array))
返回列表的下标和对应值
while( my ($key, $value) = each (%hash))


函数：select
select OUT  或者  select STDOUT
改变默认输出句柄


函数：keys
my @k = keys %hash;
获取hash的关键字组成一个列表

函数：values
my @v = values %hash
获取hash的值组成一个列表


函数：exists
exists $books{"dino"}
查看哈希里是不是有关键字dino


函数：delete
delete $books{$person};
删除关键字$person 及其值，注意到与赋空值是有区别的


函数：split
my @fields = split /separater/, $string;
把$string 中的字符串按  separater正则表达式匹配的内容  隔开，隔开的各个部分放入列表变量@fields 中
对split来说，前面的空分隔都保留，后面的都省略，加第三个参数-1保留后面的空分隔


函数：join
my $result = join $glue, @pieces;
把列表变量@pieces 里面的字符串用$glue 里面的内容连接成一个新串


函数：localtime
my $date = localtime;    
调用perl函数localtime获得当前的时间
my $timestamp  =  1180630098;
my $date = localtime $timestamp; ## localtime函数返回可读的时间
my($sec, $min, $hour, $day, $mon, $year, $wday, $yday, $isdst) = localtime $timestamp;
###在list上下文中返回的信息


函数：gmtime
Greenwich Mean Time时间


关键字：unless
if的反义词，不成立时执行，可以配else，注意后面括号内内容只执行一次


关键字：until
while的反义词，条件不成立的时候一直执行直到成立，先进行条件判断


函数：stat
my($dev, $ino, $mode, $nlink, $uid, $gid, $rdev, $size, $atime, $mtime, $ctime, $blksize, $blocks) = stat($filename);
返回文件的详细信息


函数：lstat
需要软链接的信息，使用lstat，在软链接上使用stat会返回指向文件的信息


函数：chdir
chdir '/etc'  or die "cannot chdir to /etc:$!"; 
更改工作目录，不带参数时打开home目录


函数：mkdir
mkdir  'fred', 0755 or warn "Can't make fred directory: $!";   ###   生成目录fred，755为权限位
成目录，注意标志位用八进制表示，开头要是0


函数：rmdir
rmdir $dir or warn "cannot rmdir $dir: $!\n";
删除目录，每次只能删除一个


函数：opendir
opendir my $dh, $dir_to_process or die "Cannot open $dir_to_process: $!";  ### 读入目录句柄


函数：readdir
readdir $dh;   ###   读入目录句柄$dh指向的目录里的文件
读入所有文件，包括.开头的隐藏文件和..那个表示上级目录的文件，仅返回文件名，不带路径


函数：closedir
closedir $dh;   ###   关闭目录句柄$dh    例子：
my $dir_to_process = '/etc';
opendir my $dh, $dir_to_process or die "Cannot open $dir_to_process: $!";
foreach $file (readdir $dh) {
	print "one file in $dir_to_process is $file\n";
}
closedir $dh;


函数：glob
my  @all_files = glob '*';     ###    得到当前目录下所有文件名，不包括.开头的隐藏文件
my @all_files = <*>;   ###  上面写法的另一种表达
my @all_files_including_dot = glob '.* *';  
### 包括所有文件，点开头的也包括，注意到中间的空格，此处是两个匹配
### 匹配不同文件时，用空格隔开，注意到整个匹配部分用引号引起来


函数：readline
my @lines = readline FRED;    ### 从文件句柄FRED中读数据
my @lines = readline $name;   ###  从间接文件句柄中读入数据


函数：unlink
unlink  'slate',  'bedrock',  'lava';    ###  删除文件slate，bedrock，lava
unlink  qw(slate  bedrock  lava);        ###  同上
unlink  glob '*.o';        ### 删除所有.o文件
既可以删除硬链接，也可以删除软链接


函数：link
link  'chicken', 'egg'  or warn "can't link chicken to egg: $!";  ### 创建chicken的另一个别名egg
创建一个硬链接


函数：symlink
symlink  'dodgson', 'carroll' or warn "can't symlink dodgson to carroll: $!"; ###创建dodgson的软链接carroll
创建一个软链接


函数：readlink
my  $where =  readlink 'carroll';   ###　返回dodgson
读取软链接指向那个的文件


函数：chmod
chmod 0755,  'fred', 'barney';   ###  更改fred，barney两个文件的权限
改权限，只支持数字表示的权限
安装File::chmod模块，让chmod支持符号权限


函数：chown
my  $user  = 1004;
my  $group  =  100;
chown  $user, $group, glob '*.o';
三个参数，改拥有者和组，注意前两个参数必须为数字


函数：utime
my  $now  =  time;    ### time函数返回当前时间
my  $age  = $now - 24*60*60;
utime  $now,  $ago,  glob '*';   ###   更改当前目录下所有文件的访问时间为当前，修改时间为一天之前
三个参数，第一次参数设置文件的最后访问时间，第二个参数设置文件的最后修改时间，第三个参数设置要修改文件


函数：index
$where = index($big, $small);
在一个字符串中寻找子串， $big 为原字符串， $small 为子串，返回第一个匹配的字符的下标，失败返回-1
my $where1 = index($stuff, "w", 3);   ###  从下标为3的字符开始找子串w
可选第三个参数表示开始查找位置


函数：rindex
my $last_slash = rindex("/etc/passwd", "/");   ###  返回4
从后往前逆向寻找子串，注意到返回的值还是正向数过来的下标
my $where2 = rindex($fred, "abba", $where1-1);   ### 从$where1-1向前找子串abba
可选第三个参数表示开始向前查找的下标


函数：substr
my $part = substr($string, $initial_position, $length);  
参数1为原始字符串，参数2为起始下标位置，参数3为子串长度，返回一个符合要求的子串
省略参数3得到从起始处到结尾的子串，参数2可以为负数，表示从后往前数的位置
my $string = "Hello, world!";
substr($string, 0, 5) = "Goodbye"; # $string is now "Goodbye, world!"  替换字符串的内容
my $previous_value = substr($string, 0 ,5, "Goodbye");  
### $previous_value得到被删除的子串Hello
### $sting 中得到改变的Goodbye,world!
substr($string, –20) =~ s/fred/barney/g;  ###  替换$string最后20个字符的所有fred为barney


函数：sprintf
my $date_tag = sprintf "%4d/%02d/%02d %2d:%02d:%02d", $yr, $mo, $da, $h, $m, $s; ##返回值例子：2038/01/19 3:00:08"
my $money = sprintf "%.2f", 2.49997;  ###  返回2.50
返回一个符合要求格式的字符串


函数：hex
hex('DEADBEEF') # 3_735_928_559 decimal
从16进制得到10进制数


函数：oct
oct('0377') # 255 decimal
里面参数可以是16进制，8进制，2进制，转化得到10进制数


函数：grep
my @a = grep EXPR, @input_list;
grep 仅仅是根据EXPR的判断把list里面的内容有选择的放入结果list中
后面接的第一个参数EXPR为一个布尔表达式，true的话$_ 中的内容会被提取
grep后面第一个参数为bool表达式的时候用逗号隔开，为一个subroutine的时候用{}括起来，此时不是一个真正的subroutine，而是一个括起来的block块，所以不能使用return
my @odd_numbers = grep { $_ % 2 } 1..1000;  ###  从1到1000中提取奇数
第一个参数是个block，返回一个布尔值，第二个参数是操作list
my @matching_lines = grep { /\bfred\b/i } <$fh>;   ### 寻找包含fred的行,subroutine的写法
my @matching_lines = grep /\bfred\b/i, <$fh>; ###  bool表达式的写法的写法，注意到逗号不能少
列表上下文中返回list，标量上下文中返回list元素个数
my @lunch_choices = grep is_edible($_), @gilligans_possessions;    
### 使用真正的subroutine时，要加逗号

###  获得各位数字和是奇数的元素的索引号
my @input_numbers = (1, 2, 4, 8, 16, 32, 64);
my @indices_of_odd_digit_sums = grep {
	my $number = $input_numbers[$_];
	my $sum;
	$sum += $_ for split //, $number;
	$sum%2;
} 0..$#input_numbers;         ###  $#input_numbers 表示该list的最大索引号

####  获得@x中比@y对应位置大的元素的索引
my @bigger_indices grep {
	$_>$#y or $x[$_]>$y[$_];   ## 超过@y索引的部分默认更大
} 0..$#x;


函数：map
第一个参数是个block，返回操作结果，第二个参数是操作list
map 是用于操作list中的内容，返回每次操作结果(可能不只一个)
print "The money numbers are:\n", map { sprintf("%25s\n", $_) } @formatted_data;  ## list内容按要求输出
print "Some powers of two are:\n", map "\t".( 2**$_ )."\n", 0..15;   ### 简单写法，注意逗号
my @result = map { $_, 3*$_ } @numbers;  
###  针对每个元素，输出两个结果，都是新list的成员
my @result = map { split //, $_} @numbers;
###  把@number的每个数字分开，存入@result中
my @result = map {
			my @digits = split //, $_;
			if($digits[-1] == 4) {
				@digits;
			}else{
				();
			}
} @numbers;
####    把@number的每个数字分开，只返回那些个位数为4的数字的各个部分
my %skipper = map {$_, 1} qw(blue_shirt hat jacket preserver sunscreen);  ### 利用map生成hash

####  获得@x中比@y对应位置大的元素
my @bigger = map {
	if($_>$#y or $x[$_]>$y[$_]) {
		$x[$_];
	}else{
		();
	}
} 0..$#x;

####  生成一个list，元素是list引用，list里面第一个元素是名字，第二个是内容list的引用
my @remapped_list = map {
	[ $_ => $provision{$_} ];  ### 等价于 [ $_, $provision{$_} ];
} keys %provision;






###########################   注意细节   ################################

printf 中输出 % 号用 %%

映射的用法
my %last_name  =  (
    'fred' => 'flintstone',
    'dino'  => undef,
    'barney' => 'rubble',
    'betty'  =>  'rubble'
  );
  
my %hash = @result;   ###  直接转换 @result里面的元素个数要是偶数
index为偶数的都是key，为奇数的都是value
 
三元运算符?:   同C语言
my  $size  =
     ($width < 10 ) ? "small"      :
    ($width  <  20) ? "medium" :
    ($width  <  50) ? "large"      :
                      "extra-large";
					  
逻辑与&& (或者写成and)  逻辑或|| (或者写成or)      有短路(short circuit)逻辑，即不执行不必要的操作
文字的 and 和 or 优先级最低， not 和 xor 也是存在的


//操作符        defined-or 操作符  如果左边变量没定义就使用右边的代替（不会赋值给左边变量）（需要5.010）
my $value = $try // 'default';
当$try 为 undef 时赋值default字符串给$value ， $try 仍然为 undef
应用场景为未初始化就赋初值，初始化再进一步处理

$#names   表示列表names最后一个元素的索引号
foreach my $index (0..$#names)

open my $log_fh, ">", 'castaways.log';       ####  存储文件句柄的scalar必须未初始化，否则存不进去
print {$log_fh} "We have no bananas today\n";  ### 用{}强调里面的变量是文件句柄

当想把内容存入变量而不是存入文件时，可以利用open打开一个变量作为句柄
use CGI;
open my $string_fh, '>', \ my $string;  ### 将内容存入变量而不是文件句柄，内容都存在程序内
CGI->save($string_fh);    ### 存入CGI状态

###  通过在局部把变量作为STDOUT处理，在外部时STDOUT又称为标准变量
print "1. This goes to the real standard output\n";
my $string;
{
	local *STDOUT;
	open STDOUT, '>', \ $string;
	print "2. This goes to the string\n";
	$some_obj->noisy_method(); # this STDOUT goes to $string too
}
print "3. This goes to the real standard output\n";



###########################   引用相关   ################################


my $ref = \@array;  取得引用
在任何标量，列表，映射前面加 \ 都能取得该变量的引用
!!!解引用的关键是：用{}括起来的整个代表引用部分原来的字面名字，加上其他相关符号就解引用了!!!
@{$ref}   解引用得到数组
${$ref}[1]   解引用并取得第2个元素

当引用是最简单的标量形式，如：@{$items}  或者 ${$items}[1]，我们可以省略花括号，写成
@$items， $$items[1]

当{}中是最简单的标量时，可以省略{}
$ref->[1]
数组中，每个写成${DUMMY}[$y]的解引用，都可以写成DUMMY->[$y]，即list的引用都有->的简写形式
${${$all_with_names[2]}[1]}[0]    可以写成   $all_with_names[2]->[1]->[0]
							进一步可以写成   $all_with_names[2][1][0]
							
注意下面的区别：
my $ref = @array;         ${${$ref[2]}[1]}[0]  写成 $ref[2]->[1]->[0]  写成   $ref[2][1][0];
my $ref = \@array;        ${${${$ref}[2]}[1]}[0] 写成 $ref->[2]->[1]->[0]  写成 $ref->[2][1][0];

my %gilligan_info = ( name => 'Gilligan',);
my $hash_ref = \%gilligan_info;
my $name = ${$hash_ref}{'name'};
my @keys = keys %$hash_ref{'name'};
my $name = $hash_ref->{'name'};
->用来进行解引用时，前面都是一个引用（标量）


my %gilligan_info = (
	name => 'Gilligan',
	hat => 'White',
	shirt => 'Red',
	position => 'First Mate',
);
my %skipper_info = (
	name => 'Skipper',
	hat => 'Black',
	shirt => 'Blue',
	position => 'Captain',
);
my @crew = (\%gilligan_info, \%skipper_info);
my $format = "%?15s %?7s %?7s %?15s\n";
printf $format, qw(Name Shirt Hat Position);

for my $crewmember (@crew) {
	printf $format, @$crewmember{qw(name shirt hat position)};  ## 使用了hash的slices模式
	
	####  或者展开的写法：
	printf $format,
	$crewmember->{'name'},
	$crewmember->{'shirt'},
	$crewmember->{'hat'},
	$crewmember->{'position'};
}

子例程的引用：       ###  注意到引用的匿名子例程一般是没有参数的

sub skipper_greets {
}
my $ref_to_greeter = \&skipper_greets;
&$ref_to_greeter('Gilligan');  ### 或者
$ref_to_greeter->('Gilligan');

my $ginger = sub {        ### 生成匿名subroutine引用
	my $person = shift;
	print "Ginger: (in a sultry voice) Well hello, $person!\n";
};            ######   注意到这是正常赋值语句，所以;不能少
$ginger->('Skipper');

use File::Find;      ###  可移植性文件和文件夹查找，会递归查找目录下所有子目录和文件
sub what_to_do {
	print "$File::Find::name found\n";    ### 变量$File::Find::name是相对于起始位置的相对路径
}
my @starting_directories = qw(.);
find(\&what_to_do, @starting_directories);  ### 找到当前目录下所有文件和子目录并打印

my $total_size = 0;
find(sub {$total_size += -s if -f}, '.');    ### 统计目录下所有文件的大小和
print $total_size, "\n";


###  闭包变量相当于只能被子例程使用的全局静态变量  ！！！！！
my $callback;  ### 此变量声明必须放block外面，放block里面的话在find处该变量就不存在了
{
	my $count = 0;      ### 由于被匿名变量引用，在find调用时$count依然存在，成为闭包
	$callback = sub {print ++$count, ": $File::Find::name\n"};   ##  ;不能少，此处是赋值语句
}
find($callback, '.');

sub create_find_callback_that_counts {
	my $count = 0;                      ###  将闭包变量放入初始化子例程里面，这样只初始化一次
	return sub { print ++$count, ": $File::Find::name\n"}
}
my $callback1 = create_find_callback_that_counts();   
find($callback1, 'bin');      ##  调用$callback1的共用一个$count
my $callback2 = create_find_callback_that_counts();
find($callback2, 'lib');      ##  调用$callback2的共用另一个$count

###  分别修改和读出闭包变量
sub create_find_callback_that_sum_the_size {
	my $total_size = 0;
	return(sub {$total_size += -s if -f}, sub {return $total_size});
}
my ($count_em, $get_results) = create_find_callback_that_sum_the_size();
find($count_em, 'bin');
my $total_size = &$get_results();

sub print_bigger_than {       ### 初始化子例程可以包含参数，匿名子例程不可以
	my $minimum_size = shift;
	return sub {print "$File::Find::name\n" if -f and -s >= $minimum_size};
}
my $bigger_than_1024 = print_bigger_than(1024);   ### 1024变成该引用固定值
find($bigger_than_1024, 'bin');

###  闭包变量一般可以用 state 声明的静态变量替换，不过state只能初始化scalar
###  state声明的list和hash不能初始化，报编译错误
{
	my $countdown = 10;
	sub count_down { $countdown-- }
	sub count_remaining { $countdown }
}
count_down();
print "we're down to ", count_remaining(), " coconuts!\n";
等价于
sub countdown {
	state $countdown = 10;
	$countdown--;
}
###  state声明的list和hash不能初始化，报编译错误，但可以用引用变相实现初始化
sub add_to_tab {
	my $castaway = shift;
	state $castaways = qw(Ginger Mary Ann Gilligan); # works!
	state %tab = map { $_, 0 } @$castaways; # works!
	$tab->{$castaway}++;
}
####   匿名子例程的自引用
my $countdown = sub {
	state $n = 5;
	return unless $n > -1;
	say $n--;
	$countdown->();     ###  调用自身
};
$countdown->();
或者写成
my $sub = sub {
	state $n = 5;
	return unless $n > -1;
	say $n--;
	__SUB__->();    ### v5.16之后加入的环境变量，表示所在子例程的引用
};
$sub->();

文件句柄的引用：    ### 以下内容不推荐
log_message( \*LOG_FH, 'An astronaut passes overhead' );
sub log_message {
	local *FH = shift;
	print FH @_, "\n";
}

正则表达式的引用：
my $pattern = 'coco.*';
if(/$pattern/) {           #### 正则表达式的替换，但是如果此处正则表达式不符合语法要求
}                          #### 则只能在运行时才能发现

qr// 操作符
my $regex = qr/Gilligan|Skipper/;   ### 生成一个编译过的正则表达式的引用
my $regex = eval { qr/$pattern/ };    ###  依然要用eval评估正则表达式的是否符合语法要求

m/Gilligan$/migc     ##  migc作为标签
qr/Gilligan$/mi;      ##  qr用标签的写法，gc不可用，只可用作用在pattern上的标签
qr/(?mi:Gilligan$)/;   ## 第二种写法  采用的通用的(?flags:pattern)模式
qr/abc(?i:Gilligan)def/;   ## 只括号中的字母忽略大小写的写法
qr/abc(?x-i:G i l l i g a n)def/i;   ###  括号内部分应用x标签，取消i标签的应用

my $regex = qr/Gilligan/;
$string =~ m/$regex/;         ### 合法
$string =~ s/$regex/Skipper/;      ### 合法
$string =~ $regex;        ### 合法
$string ~~ $regex;        ### 使用智能匹配

my @patterns = (
	qr/(?:Willie )?Gilligan/,
	qr/Mary Ann/,
	qr/Ginger/,
	qr/(?:The )?Professor/,
	qr/Skipper/,
	qr/Mrs?. Howell/,
);
my $name = 'Ginger';
say "Match!" if $name ~~ @patterns;    ###  智能匹配，多模式匹配

use List::Util qw(first);
my %patterns = (
	Gilligan => qr/(?:Willie )?Gilligan/,
	'Mary Ann' => qr/Mary Ann/,
	Ginger => qr/Ginger/,
	Professor => qr/(?:The )?Professor/,
	Skipper => qr/Skipper/,
	'A Howell' => qr/Mrs?. Howell/,
);
my $name = 'Ginger';
my( $match ) = first { $name =~ $patterns{$_} } keys %patterns;  ### 从所有匹配的结果里取第一个
say "Matched $match" if $match;

####  检查引用的类型是否相符         
####  使用 ref 关键字提取引用类型进行显示检查
use Carp qw(croak);
sub show_hash {
	my $hash_ref = shift;
	my $ref_type = ref $hash_ref;
	croak "I expected a hash reference!" unless $ref_type eq 'HASH';
	###  或者写为
	croak "I expected a hash reference!" unless $ref_type eq ref {};
}

###    下面代码调试未通过，when改为if后可正常运行
my @array = ( \ 'xyz', [qw(a b c)], sub { say STDOUT 'Buster' } );
foreach ( @array ) {
	when( ref eq ref \ '' ) { say STDOUT "Scalar $$_" }
	when( ref eq ref [] ) { say STDOUT "Array @$_" }
	when( ref eq ref sub {} ) { say STDOUT "Sub ???" }
}

或者

use Carp qw(croak);
use Scalar::Util qw(reftype);
sub show_hash {
	my $hash_ref = shift;
	my $ref_type = reftype $hash_ref; # works with objects
	croak "I expected a hash reference!" unless $ref_type eq ref {};
}

或者

###  使用eval来测试，在eval之中当引用使用，如果产生错误就报错，否则返回最后的1
croak "I expected a hash reference!" unless eval { keys %$ref_type; 1 }  

匿名数组构造器，方括号的另一种用法，利用内部列表形式列出的内容生成一个匿名数组，返回该数组的引用
my $ref_to_skipper_provisions = [ qw(blue_shirt hat jacket perserver sunscreen) ]
等价于
my $ref_to_skipper_provisions;
{
	my @temporary_name = ( qw(blue_shirt hat jacket preserver sunscreen) );
	$ref_to_skipper_provisions = \@temporary_name;
}

###  应用举例：下面是输入
The Skipper
	blue_shirt
	hat
	jacket
	preserver
	sunscreen
Professor
	sunscreen
	water_bottle
	slide_rule
Gilligan
	red_shirt
	hat
	lucky_socks
	water_bottle

my %provisions;
my $person;
while (<>) {
	if (/^(\S.*)/) { # a person's name (no leading whitespace)
		$person = $1;
		###  生成一个引用，同时防止存在时覆盖
		$provisions{$person} = [ ] unless $provisions{$person};
	} elsif (/^\s+(\S.*)/) { # a provision
		die 'No person yet!' unless defined $person;
		push @{ $provisions{$person} }, $1;
	} else {
		die "I don't understand: $_";
	}
}


匿名映射构造器类似
my $ref_to_gilligan_info = {
	name => 'Gilligan',
	hat => 'White',
	shirt => 'Red',
	position => 'First Mate',
};

匿名映射引用和代码block表现形式类似，为以示区别，可采用如下方法
my $hash_ref = +{...};      ####  花括号以加号开头

code block:
{; ... }          ###  代码块内以;开头



###########################   巧妙用法   ################################

1、利用printf根据数组大小动态输出内容

my @iterms = qw( wilma dino pebbles );
my $format = "the item are:\n". ("%10s\n" x @iterms);
printf $format, @items;

缩写：
printf "The items are:\n".("%10s\n" x @items), @items;


2、输出到文件句柄

print $rock;    输出变量的内容
print { $rock_fh };   输出$_ 到文件句柄 $rock_fh        用大括号把句柄变量括起来以让perl知道这是个句柄变量，否则perl将其当做一个普通变量


3、一句话perl程序

perl -p -i.bak -w -e 's/Randall/Randal/g' fred*.dat

解释：
	-p  让perl自己完善成为一个小程序，大致相当于
		while(<>) {
			print ;
		}

	-n  同 -p，但是没有print，意即不输出

	-i.bak   设置$^I 为.bak

	-w   打开warnings
	
	-e  表示执行代码如下，后接执行代码，执行代码用单引号引起来（unix命令行习惯）
	
perl -le "print for @INC"  一句话perl，查看@INC 里面的内容
	
	
4、表达式修饰符

即放在后面的不带圆括号的 if  while  unless until  foreach
适合一句话模块
$sum += $_ for @digits;


5、perl中的五个循环块：
for,   foreach,  while,   until,   裸括号{}
last;    跳出循环

next;    跳出本次循环
next不能用于有返回值的如 eval {} , sub {} or do {}这样的代码块中，也不能用于 grep() or map()迭代

redo;   重新执行块内代码，不进行条件判断和迭代处理操作


6、标记（label）块:
LINE:   while(<>) {
			......
			last   LINE  if  /__END__/;   ###  到达文件尾时跳出while块
	    }
  
  可以用 last   next   redo 三者之一跳出LINE标记的整个块，注意到label与goto的意义不同，label标记整个块，是用来跳出loop的
  
  
7、执行外部程序

``执行反引号里面的外部命令，用于捕获外部命令的运行结果
反引号内程序的返回结果会被perl包装成一个string返回，注意到这个string自带换行符\n
如果返回的string里面有多个换行符，则在列表上下文中得到换行符分割的一个列表
反引号括起来的内容的处理类似双引号的效果
chomp(my $no_newline_now = `date`);
qx/ /  功能同上，意即上面符号的文字表示，区别是引用内容的翻译类似单引号，delimiter的选择类似qw//
反引号不要使用在需要有输入的命令上，如果不确定命令是否需要输入，用如下方式：
my $result = `some_questionable_command arg arg argh </dev/null`;   ### 用bit桶作为输入



system('$cmd')
system返回程序运行状态，system先做一个fork在exec，即在子进程里面运行$cmd 程序
@args = ("command", "arg1", "arg2")
system(@args) == 0 or die "system @args failed:$?"
system "long_running-command with parameters &";
shell接收到long_running-command后放后台运行，实际上long_running-command相当于孙进程，跟perl脚本没直接交互
my $tarfile = 'something*wicked.tar';
my @dirs = qw(fred|flintstone <barney&rubble> betty );
system 'tar', 'cvf', $tarfile, @dirs;
用这种参数分开的方法调用，不会启动shell，传给shell的*，|和>这类元字符就失去了意义，同时是去shell设置的IO重定向，后台程序等功能
!system 'rm -rf files_to_delete' or die 'something went wrong';
常见用法，因为shell在成功后返回0

exec 函数结束当前程序的运行并且执行一条外部命令并且决不返回（这种调用外部函数的办法用的不多）
当前运行的perl进程执行exec后面的命令，此时已经跳出运行的perl脚本，所以没有perl的返回了
用于给要执行的真正程序设置环境变量，IO接口等
exec 'bedrock', '-o', 'args1', @ARGV;

8、按逗号格式显示钱数

sub big_money {
	my $number = sprintf "%.2f", shift @_;  ### 只接受一个参数，精确到分（小数点后第二位）
	# Add one comma each time through the do-nothing loop
	1 while $number =~ s/^(-?\d+)(\d\d\d)/$1,$2/;  ###  每次从后面取三个数字，加上一个逗号
	# Put the dollar sign in the right place
	$number =~ s/^(-?)/$1\$/;       ####  在符号之后加一个$
	$number;
}
###  因为是从后往前找，所以用while而不能用/g进行全局替换

my @a = $str =~ /\w{2}/g;   ### 将$str分拆成两个字母为一个单位的list




9、智能匹配:   ~~
use 5.010001;  ### 版本以上

say "I found a key matching 'Fred'" if %names ~~ /Fred/;   ####  寻找hash中是否有key为Fred
say "I found a key matching 'Fred'" if /Fred/ ~~ %names;   ###  另一种写法
等同于
my $flag = 0;
foreach my $key ( keys %names ) {
	next unless $key =~ /Fred/;
	$flag = $key;
	last;
}
print "I found a key matching 'Fred'. It was $flag\n" if $flag;

say "The arrays have the same elements!" if @names1 ~~ @names2;   ###  对比两个列表是否完全相同
等同于
my $equal = 0;
foreach my $index ( 0 .. $#names1 ) {
	last unless $names1[$index] eq $names2[$index];
	$equal++;
}
print "The arrays have the same elements!\n"
if $equal == @names1;

say "The result [$result] is one of the input values (@nums)" if $result ~~ @nums;  
###  检查是否为列表中一个元素
智能匹配的常见用法
Example							Type of match
%a ~~ %b			 			hash keys identical
%a ~~ @b or @a ~~ %b 			at least one key in %a is in @b
%a ~~ /Fred/ or /Fred/ ~~ %b 	at least one key matches pattern  ### 引号字符串匹配时，字符串必须在前面
'Fred' ~~ %a 					exists $a{Fred}
@a ~~ @b 						arrays are the same
@a ~~ /Fred/ 					at least one element in @a matches pattern  
### 第二项改为引号字符串则不成功匹配
$name ~~ undef 					$name is not defined
$name ~~ /Fred/ 				pattern match
123 ~~ ’123.0’					numeric equality with “numish” string ###  数字类比较，数字必须在前
’Fred’ ~~ ’Fred’ 				string equality
123 ~~ 456 						numeric equality


10、given语句
use 5.010001;
given ( $ARGV[0] ) {
	when ( 'Fred' ) { say 'Name is Fred' }
	when ( /fred/i ) { say 'Name has fred in it' }
	when ( /\AFred/ ) { say 'Name starts with Fred' }
	default { say "I don't see a Fred" }
}
类似C语言的switch语句，不过中间的默认匹配是智能匹配，默认自带break，匹配一个就不执行剩下的匹配
when ( $_ ~~ /fred/i ) { say 'Name has fred in it'; continue }
加一个continue可以在匹配成功后继续匹配执行接下来的when
when里面可以用dumb comparison，即正常的==，>，<，!或者=~，也可以放入subroutine的返回结果

use 5.010001;
foreach ( @names ) { # don't use a named variable!
	say "\nProcessing $_";
	when ( /fred/i ) { say 'Name has fred in it'; continue }
	when ( /\AFred/ ) { say 'Name starts with Fred'; continue }
	when ( 'Fred' ) { say 'Name is Fred'; }
	default { say "I don't see a Fred" }
}
对于多个变量的判断，省略掉given，直接用foreach将判断变量放入$_


11、perl里面的多进程并发，使用open，并在文件名前面或者后面加管道符|
open DATE, 'date|' or die "cannot pipe from date: $!";       ####    date的结果链接到perl的标准输入
open MAIL, '|mail merlyn' or die "cannot pipe to mail: $!";   ####   perl的内容输出到mail
占位符表明后面命令所在的位置
open my $date_fh, '-|', 'date' or die "cannot pipe from date: $!";    
###  想读入文件句柄，即把date的结果作为输入
open my $mail_fh, '|-', 'mail merlyn' or die "cannot pipe to mail: $!"; 
 ### 想写入文件句柄，即把perl的内容输出到mail
 
 open my $pipe, '-|', $command or die "cannot open filehandle: $!";  ### 从一个外部命令里输入
 while(<$pipe>) {
	print "Read: $_";
 }
 用open并发的好处是子进程要处理很久时，可以得到一个结果就立即用剩下的perl脚本处理，而想反引号那种只能等子进程结束处理才能运行剩下的perl脚本
 
 
 
12、slices：想得到列表中极个别元素，其他元素都不使用的时候
使用方法：在列表外围加上小括号，并用下标索引
my $mtime = (stat $some_file)[9];    
###  得到stat返回的关于某个文件的修改时间的内容，修改时间在返回结果list的第九项
while(<IN>) {
	my $num_5 = (split /:/)[4];    ###  获得split出来元素的第四项
}
my ($num_3, $num5) = (split /:/)[2, 4];       ### 列表上下文中获得两个元素
my($first, $last) = (sort @names)[0, -1];     ###  获得第一个和最后一个元素
my @numbers = ( @names )[ 9, 0, 2, 1, 0 ];   ###  下标可以是任意顺序，也可以重复，用array slice更方便

array slice：与数组中获得个别元素，与上面那个从列表中获得内容在语法意思上有本质区别，实际结果没区别
my @names = qw{ zero one two three four five six seven eight nine };
print "Bedrock @names[ 9, 0, 2, 1, 0 ]\n";    ####  返回list里对应的英文字符串，空格分开
@items[2, 3] = ($new_address, $new_home_phone);   ###   改变list里面的两个元素

hash slice：得到hash中个别元素的值，其他都不需要
my @three_scores = ($score{"barney"}, $score{"fred"}, $score{"dino"});  ####  直接利用key得到值
my @three_scores = @score{ qw/ barney fred dino/ };   ###  hash slice 注意到前面还是@符号
my @players = qw/ barney fred dino /;
my @bowling_scores = (195, 205, 30);
@score{ @players } = @bowling_scores;        ###   根据hash里面的键改变其中几个元素的值
print "Tonight's players were: @players\n";
print "Their scores were: @score{@players}\n";   ###  每个值用空格分开


13、异常处理eval：发现一个fatal error时返回undef并在$@ 中设置错误信息，没有错误时$@ 为undef，eval返回正常执行的最后一句结果。必须用花括号括起来，因为是一个完整的expression，所以在单独成句的时候要加分号;
eval { $barney = $fred / $dino };   ###  当遇到$dino为0的异常时，跳过eval包围的代码块，继续执行后面的代码
my $barney = eval { $fred / $dino } // 'NaN';  ###  更有效的写法，当$dino为0时，eval返回undef
print "I couldn't divide by \$dino: $@" if $@;
unless( eval { $fred / $dino } ) {         ###  现实中更一般的用法
	print "I couldn't divide by \$dino: $@" if $@;
}

eval不能处理四种错误：
1、perl代码语法错误
2、内存泄露等错误，这种错误直接关掉perl解释器
3、警告，-w或者use warnings设置的
4、exit退出整个perl程序

{
local $@; # don't stomp on higher level errors  限制$@的范围在这个block内，不影响上层的eval
	eval {
		...;
		die "An unexpected exception message" if $unexpected;
		die "Bad denominator" if $dino == 0;
		$barney = $fred / $dino;
	}
	if ( $@ =~ /unexpected/ ) {
		...;
	}
	elsif( $@ =~ /denominator/ ) {
		...;
	}
}

###    两个 eval 好区分的差别是一个接{}block块，一个接字符串表达式


14、 eval ：执行一个string expression内容，而不是一个{}block块里面的句子，返回最后一个值
此eval的参数是一个字符串表达式，表达式内容在运行时先编译再执行
eval '$sum = 2 + 2';
print "The sum is $sum\n";

for my $operator ( qw(+ - * /)) {
	my $result = eval "2 $operator 2";
	print "2 $operator 2 is $result\n";
}
####  返回前缀加四个运算的结果
如果给的那个句子不能执行，在$@ 中填充错误信息

print 'The quotient is ', eval '5 /', "\n";
warn $@ if $@;
eval的结果有错误，马上输出警告

15、 do 的用法: 把所有判断放到一起
my $bowler = do {
	if(... some condition ...) {'Mary Ann'}
	elsif(... some condition ...) {'Ginger'}
	else {'The Professor'}
};              ###  注意此处的分号，理由同eval
等价于
my $bowler;
if(...some condition...) {
	$bowler = 'Mary Ann';
}elsif(...some condition...) {
	$bowler = 'Ginger';
}else{
	$bowler = 'The Professor';
}

my $file_contents = do {            ####  把一个文件的所有内容读入到一个变量内
	local $/;
	local @ARGV = ($filename);
	<>
};


16、The Schwartzian Transform
my @output_data =
	map { EXTRACTION },      ###  抽取比较结果中需要的部分
	sort { COMPARISON }      ###  提取引用中的比较部分进行比较
	map [ CONSTRUCTION ],     ### 构造新引用
	@input_data;
	
####  例子：不区分大小写的字符串排序
my @output_data =
	map $_->[0],
	sort { $a->[1] cmp $b->[1] }
	map [ $_, "\F$_" ], ### \F 用于把后面所有字符全部大写
	@input_data;

###  用hash代替list	
my @output_data =
	map $_->{ORGINAL},
	sort { $a->{FOLDED} cmp $b->{FOLDED} }
	map { ORIGINAL => $_, FOLDED => "\F$_" },      ### 使用hash代替，关键字代替下标进行索引
	@input_data;

###  多个元素排序
my @output_data =
	map $_->[0],
	sort {
		$a->[1] cmp $b->[1] or  
		$a->[2] <=> $b->[2] or
		$a->[3] cmp $b->[3] }
	map [ $_, lc, get_id($_), get_name($_) ],
	@input_data;
	
map $_->{VALUE},      ### 使用hash的写法
	sort {
		$a->{LOWER} cmp $b->{LOWER} or
		$a->{ID} <=> $b->{ID} or
		$a->{NAME} AND $b->{NAME} }
	map {
		VALUE => $_,
		LOWER => lc,
		ID => get_id($_),
		NAME => get_name($_),
	},
@input_data;