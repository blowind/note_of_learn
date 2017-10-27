


###########################   ��������   ################################

use strict;   ##  Ҫ�����ʹ��ǰ���������ַ��������ţ�����Ϊʵ����

no strict 'refs'; ##  ȡ����������

use 5.012  ## ����汾֮���perlĬ�Ͽ���strict

use warnings  ##  �������� Ҳ������#!�м���-w������

use diagnostics ##  ������� ???????????????

use autodie  ## ����ϵͳ������ʱ����������������󣩣��Զ�die




###########################   perlģ��   ################################

��װģ�飺������ģ��
perl Makefile.PL PREFIX=/Users/home/Ginger  ### ��װĿ¼
����
perl Makefile.PL INSTALL_BASE=/User/home/Ginger
make
make test
make install


����ģ��·��(shell����)��
export PERL5LIB=/User/home/Ginger
����
use lib qw(/User/home/Ginger) ʹ�� /User/home/Ginger·���µ�ģ��    ### Ҳ���ڱ���ʱִ��
����
BEGIN { unshift @INC, '/home/gilligan/lib'; }  ###  ������ģ���·����������BEGIN
����beginģ���������������perl��䣬���ڱ���ʱִ��

use  ���õ����ݶ����ڱ�����ִ��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

require List::Util;        ####  require ����load modules������������������ִ����
BEGIN {
	require List::Util;
	List::Util->import(...);
}           ###      �ȼ���  use List::Util;

use constant LIB_DIR => '/home/gilligan/lib';   ### ��������ڵĳ���
use lib LIB_DIR;     ###  ����������

use FindBin qw($Bin);   ###  ��ģ�齫��ǰĿ¼·������$Bin���Դ�ģ�飬���ڱ�����֮��ִ��
use lib $Bin;       ###  �������@INC
use lib "$Bin/../lib";

### ��Ҫ��װ��local::libģ�飬����ָ��CPAN��װģ���λ��
% perl -Mlocal::lib  ��ʾ��ģ����趨����
% cpan -I Set::Crossproduct    -I��Ǳ�ʾ��local::libָ����·����װģ��


ʹ��Module::Buildģ�������а�װ����
perl Build.PL --install_base /Users/home/Ginger
perl Build
perl Build test
perl Build install

δ����ģ�飺
perl -MCPAN -e shell
cpan>install CGI::Prototype
����
cpan CGI::Prototype Test::Pod

perl -MCPANPLUS -e shell   ��CPANPLUSģ����5.010֮���Դ�
CPAN Terminal> i CGI::Prototype          ģ�鰲װ
����
cpanp i CGI::Prototype HTTP::Cookies::Safari Test::Pod


perldoc File::Basename  ����ģ���˵���ĵ�

######       ����ʽ�ӿ�    #####
use File::Basename;     ### ����fileparse, basename, dirname���������̣����Զ�ʶ�������е�ϵͳ
use File::Basename  qw/  basename /;   ��������basename����
use File::Basename  ('fileparse', 'basename');  ��������fileparse��basename
use File::Basename  qw/ /;   ʲôҲ����ȡ��Ҳ����д��
use File::Basename ();   ��ʱ��Ȼ����ʹ�ú���������Ҫ������������
my  $dirname = File::Basename::dirname($name);    �����ò���use���˵��ö�����

use File::Basename;  ��ʾ����Ĭ��subroutine
use File::Basename ()�� ��ʾʲôsubroutine��������

#####       �������ӿ�   ######    ����ӿڲ������κ�������
use  File::Spec;     ### ģ��ʲô��û���룬�����ӿ�ʹ���෽��
###   �ṩ����catfile��ע���뺯��������File::Spec��һ������ÿ��ʹ�ö�Ҫ����д����
use  File::Spec::Functions    ###   ���������ӿ�Ϊ����ʽ�ӿ�
my $new_name = File::Spec->catfile($dirname1, $dirname2, $dirname3, $basename);  
###  �����෽��catfile�ϲ�$dirname��$basename

use File::Path;
rmtree    ɾ��Ŀ¼����Ŀ¼�����

use File::Find;             ###  ��ѯĳĿ¼������Ŀ¼�µ�ĳ��ƥ���ļ�
##  ��������  $File::Find::name   ����Ŀ¼���ļ������֣��������������·�� -d -f���Բ��ɹ�
##			  $File::Find::dir    ����Ŀ¼������   -d���Բ��ɹ�
##            $_                  �����ļ�������   -d -f ���Գɹ�
my @path = 'E:\sharedAll\path_reconstruction\delay\TestNetwork\script\test';
sub wanted {
    if ( -f $File::Find::name ) {
        if ( $File::Find::name =~ /\.pl$/ ) {
            print "$File::Find::name\n";
        }
    }
}
find(\&wanted, @path);


use  Math::BigInt;     ###  ����һ��������
my $value = Math::BigInt->new(2);        ####   ��һ��ʵ�������������ø�ֵ��$value
$value->bpow(1000);         ###  ���÷���bpow()  ������
print $value->bstr(), "\n";       ###  ���÷������


use  Path::Class;
my  $dir  =  dir( qw(Users fred lib) );
my  $subdir  =  $dir->subdir( 'perl5' );  # Users/fred/lib/perl5
my  $parent  =  $dir->parent;             # Users/fred
my $windir = $dir->as_foreign( 'Win32' ); # Users\fred\lib

###  Excel���

use Spreadsheet::WriteExcel;
my $workbook = Spreadsheet::WriteExcel->new('perl.xls');  ## ����һ��excel�ļ�
my $worksheet = $workbook->add_worksheet();   ### ���һ��sheet��Ϊ��������

$worksheet->write('A1', 'Hello Excel!');   ###  ��A1��λ������ַ���
$worksheet->write(0, 0, 'Hello Excel');    ### ��һ��д�����������ʾλ��A1

my $red_backgroud = $workbook->add_format(   ### �޸�excel�ĸ�ʽ
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
$worksheet->write_string(0, 2, $product_code);     ###  �������string�����Բ���ȥ����ͷ��0

$worksheet->write('A2', 37);
$worksheet->write('B2', 42);
$worksheet->write('C2', '=A2 + B2');       ### ��A2 �� B2 ��������ӷ���C2������excel���÷�


use Module::CoreList;
print $Module::CoreList::version{5.01400}{CPAN};      ## �鿴CPANģ��İ汾��
print Module::CoreList->first_release('Module::Build');     ##  ��ģ���һ�γ�������perl���ĸ��汾


use  CGI  qw(:all);         ###       all���α�ǩ������ģ�������к���
use  DBI;
use  DateTime; 


use threads;

my $t1 = threads->create(������, ����������.....);

my $t2 = async{   say "hello world"; };     ###   �����̵߳����ַ���


use IPC::System::Simple qw(system systemx capture capturex);
��ϵͳ�Դ���system���߿���ֲ��
systemx��֤��ʹ��shell
capture���Ʒ�����

use Try::Tiny;        ###  ����C���Ե��쳣�����谲װ
try {
	...; # some code that might throw errors
}
catch {
	...; # some code to handle the error
}
finally {
	...;
}

use List::Util;   ###  perl�Դ��⣬C����ʵ�ֵĴ���list�Ĺ���
use List:Util qw(first);
my $first_match = first {/\bPebbles\b/i} @charaters;     ### �ҵ�list�е�һ������Pebbles�ı���
use List::Util qw(sum);
my $total = sum( 1..1000 ); # 500500  ���utility
use List::Util qw(max);
my $max = max( 3, 5, 10, 4, 6 );   ###  ��list�����ֵ ������������
use List::Util qw(maxstr);
my $max = maxstr( @strings );  ###  ����string
use List::Util qw(shuffle);
my @shuffled = shuffle(1..1000); # randomized order of elements  ������˳��


use List:MoreUtils   ###  ��ǿ���谲װ
use List::MoreUtils qw(none any all);
if (none { $_ > 100 } @numbers) {    ##  һ������������Ҳû��
	print "No elements over 100\n"
} elsif (any { $_ > 50 } @numbers) {   ### ֻҪ��һ�����������ͳ���
	print "Some elements over 50\n";
} elsif (all { $_ < 10 } @numbers) {   ###  ���еĶ���������
	print "All elements are less than 10\n";
}

use List::MoreUtils qw(mesh);      ####  ����ϲ�list��һ�δӸ���listȡһ��Ԫ�ط�������list
my @abc = 'a' .. 'z';
my @numbers = 1 .. 20;
my @dinosaurs = qw( dino );
my @large_array = mesh @abc, @numbers, @dinosaurs;
####  �����a 1 dino b 2 c 3 ....
 
use autodie qw (open system: socket)   ###  ��ϵͳ������������ʱ�Զ�die���˴�ֻ��open�ļ�ʱdie

use IO::Handle;        ### 5.14֮ǰҪ��ʾ��������������Ļ�����
open my $fh, '>', $filename or die '..';
$fh->print('Coconut headphones');
$fh->close;

use IO::File;       ### ��׼���а��Դ�
my $fh = IO::File->new( '> castaways.log' ) or die "Could not create filehandle:$!";
my $read_fh = IO::File->new('castaways.log', 'r');
my $write_fh = IO::File->new('castaways.log', 'w');
my $append_fh = IO::File->new( 'castaways.log', O_WRONLY|O_APPEND );

my $temp_fh = IO::File->new_tmpfile;    ## ����ʱ�ļ��洢
$temp_fh->close or die "Could not close file: $!";

use IO::Tee;   ### ͬһ����������������
open my $log_fh, '>>', 'castaways.log';
open my $scalar_fh, '>>', \ my $string;
my $tee_fh = IO::Tee->new($log_fh, $scalar_fh);
print $tee_fh "The radio works in the middle of the ocean!\n";

use IO::Pipe;
my $pipe = IO:Pipe->new;
$pipe->reader("$^X -V");        ### $^X�ǵ�ǰperl�ű�  ���ⲿ�����������
while(<$pipe>) {
	print "Read: $_";
}
$pipe->writer($command);
for(1..10) {
	print $pipe "I can count to $_\n";
}

use IO::Null;
my $null_fh = IO::Null->new;    ## ����Ͱ���޵׶�

my $debug_fh = $Debug ? *STDOUT : IO::Null->new;     ### ͨ������debug flag�������������
$debug_fh->print("Hey, the radio's not working!");

use Regexp::Common qw(URI);    ### �ṩ���ӵ�������ʽģʽ��ʹ�ù�ϣ$REӳ�����regexp������
while(<>) {
	print if /$RE{URL}{HTTP}/;     ### ƥ��http����ַ
}
use Regexp::Common qw(net);
while( <> ) {
	say if /$RE{net}{IPv4}/;      ### ƥ��ipv4��ַ
}
use Regexp::Common qw(number);
while( <> ) {
	say if /$RE{num}{int}/;      ####  ƥ��10�������������ƥ�������
}
while( <> ) {
	say if /$RE{num}{int}{ -base => 16 }/;      ####  ƥ��16�������������ƥ�������
}
while( <> ) {
	say $1 if /$RE{num}{int}{ -base => 16 }{-keep}/; ####  ƥ��10���������������ƥ�������
}

use Bloom::Filter;
 ## capacity��ʾBloom Filter����   error_rate ���õ���������
my $bf = Bloom::Filter->new(capacity=>10, error_rate=>0.001); 
$bf->length  ## �洢Bloom Filter��λ����
$bf->on_bits  ## �洢��λon��bit����Ŀ
$bf->key_count()   ##  �Ѿ��洢��Ԫ����Ŀ
 ##  ���ؼ��ּ���Bloom Filter ��������������ʱ�ᱨ������undef 
$bf->add( @keys );
 ##  ���ؼ���@key�Ƿ���Bloom Filter���棬���ڷ���1�����򷵻�0�����շ���һ��01��list
$bf->check(@key);


###########################   �ؼ���   ################################
my  ����һ�������Ǿֲ�˽�б���

use 5.010;
state  ����һ��������ȫ��˽�б���������C���Ծ�̬�ֲ���������˼��ע��use�����٣���Ȼ����

sub  ����һ��subroutine������
sub marine {}  ���� sub marine() {}
����Բ���ű�ʾ�βθ�������
��Բ���ű�ʾ�������β�





###########################   ϵͳ����   ################################
"
STDIN,  STDOUT,  STDERR,  DATA,  ARGV,  ARGVOUT

$!  ϵͳ������Ϣ

$0  ������  ���ӣ�"$0:Not enough arguments\n"

$| = 1  ʹ��ǰѡ�е�Ĭ�����ÿ������flush

$/  �����н�����

$?  �����ӽ��̵��˳�״̬

$@  eval����Ĵ�����Ϣ

@INC  perl��Ѱģ���·������

__FILE__

__LINE__

__WARN__

__END__        �ļ���β��

%ENV   ���磺$ENV{PATH}
$ENV{'PATH'} = "/home/rootbeer/bin:$ENV{'PATH'}";    ###   ����Լ��Ļ�����������ǰ��
delete $ENV{'IFS'};

$^I = ".bak";   ����Դ�ļ����ݵĺ�׺Ϊ.bak

perl -V  �鿴perl�����Ϣ

"


###########################   �������   ################################


perl  debug���
������% perl -d script.pl
"
s  ���е�������
x  \%total_bytes  ��ʾhashӳ�������
x  sort keys %total_bytes   ��ʾkey��ɵ�һ��list 

"

use Data::Dumper   ###  ���ڽ����ӵ����ݽṹ������ ��perl�������ʽ ���
my %total_bytes;
while(<>) {
	my ($source, $destination, $bytes) = split;
	%total_bytes{$source}{$destination} += $bytes;
}
print Dumper(\%total_bytes);

��ʾ�Ľ����
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

use Data::Dumper;         ###  ��perl��װ�����Դ�
$Data::Dumper::Purity = 1; #declare possibly self-referencing structures������������ʾ������
my @data1 = qw(one won);
my @data2 = qw(two too to);
push @data2, \@data1;
push @data1, \@data2;
print Dumper(\@data1, \@data2);

��ʾ�Ľ����
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

print Data::Dumper->Dump([\@data1, \@data2], [qw(*data1 *data2)]);  ## �ڶ�������˵��������
###  �ο� typeglobs

#######    ������������������ݽṹ��module

use Data::Dump  qw(dump);    ##  ����ⰲװ
dump(\%total_byte);

use Data::Printer;      ##  ����ⰲװ
p(%total_bytes);

use YAML;
print Dump(\%total_bytes);     ## ʹ��ͨ��yaml��ʽ������ӽṹ��ruby��python����ֱ����

use JSON;
print to_json(\%total_bytes, {pretty => 1});   ### ������Ա�ֱ����Ϊjson����ʹ�õĸ��ӽṹ
my $hash_ref = from_json($json_string);

#####    �洢�������ݽṹ��module

use Storable;        ## ���ж��������������
my @data1 = qw(one won);
my @data2 = qw(two too to);
push @data2, \@data1;
push @data1, \@data2;
my $frozen = freeze [\@data1, \@data2]; ### freeze����һ�������Ƶ��ַ���
my $data = thaw($frozen);      ###  ��������洢������
##  ����2
nstore [\@data1, \@data2], $filename;  ### �洢���������ݵ��ļ�
###   nstore = network order���������С�����й�
my $array_ref = retrieve $filename;   ### ���ļ��ж����������ʽ�Ĵ洢�ĸ������ݽṹ


my @a = (a, b ,c, refs);
my @b = @a;   ## �˴�������Ϊshallow copy���������ݶ�����һ���µģ��������õ�ַ����ֻ������һ��
### ������ֻ���Ƶ���һ�㣬����ָ������ݲ�����������@a��@b�ĵ�4��Ԫ��ָ��ͬһ���ڴ��ַ

### ��������Ҫ����ָ�������Ҳ��������������listԪ�ض�ָ��ͬһ������ʱ����Ϊdeep copy
### deep copyҪʹ��Storable

use Data::Dumper;
use Storable qw(freeze thaw);
my @provisions = qw(hat sunscreen);
my @science_kit = qw(microscope radio);
push @provisions, \@science_kit;
my $frozen = freeze \@provisions;
my @packed = @{thaw $frozen};
### ��ʱpacked����ڶ�������Ҳ��һ����ȫ�����Ŀ���������������ͬ������@science_kit�޹�

������̿���ͨ�� Storable �ں��� dclone һ�����
my @packed = @{ dclone \@provisions };


###########################   ��������   ################################

������chop  
ǿ��ȥ����ӱ��������һ���ַ�


������chomp
ȥ���ַ�����β��"\n"��û�еĻ���������
�����б��У�ɾ���б���ÿ��Ԫ�ؽ�β�Ļ��з�
chomp("�ַ���"��������б�);


������scalar
ǿ�ƽ�list����ת��Ϊscalar
scalar @list ���б��������з���list��������


������say
����print�����������ڽ�β�Զ�����"\n"��5.010�汾֮��֧��


������sort
print sort <>  ##  ģ��shell��sort��Դ���룬Ĭ�ϰ�ascii��˳��Ƚϴ�С
my @numbers = sort {$a<=>$b} @some_numbers;   ###  ����С�������������
��subroutine��ʾ�Ƚ�ʱ��-1��ʾ��$a,$b ԭ����˳��0��ʾ˳������ν��1��ʾ�ı�$a,$b ��˳��
����ʽ��{$a<=>$b}��ʾ�����ֵ���������{$b<=>$a}��ʾ�����ֵĽ�������
Ҳ������cmp�滻<=>��cmp���ַ����Ƚ���ʹ��
sub by_score { $score{$b} <=> $score{$a} }  ###  ����hash��values����������keys

###  �õ�������Ԫ����ԭ����list�е�λ��
my @input = qw(Gilligan Skipper Professor Ginger Mary Ann);
my @sorted_positions = sort { $input[$a] cmp $input[$b] } 0..$#input;   ### ��������Ϊ��������
print "@sorted_positions\n";

������reverse
my @a = reverse qw(a b c d e f g);
��������Ĳ���list


������push
push (@array, 0)  or  push (@array, @others)
��ջ����һ������Ϊջlist���ڶ���ΪҪ���Ԫ�أ������Ǳ��������б�
ÿ����ջ��Ԫ����ӵ��б�����ұߣ���������


������pop
pop(@array)
ÿ�γ�ջ��Ԫ��Ϊ�б������ұߵ�Ԫ�أ���������


������unshift
unshift(@array, 0)
���һ��ֵ���б������ߣ�������


������shift
shift(@array)
ɾ���б�����ߵ�һ��ֵ����������  
###   ע�⵽unshift��shift�����������Ƚ��ȳ��Ķ��У�unshift��pop   ����  shift��push  ���ϲ���


������splice
splice(@array, 1, 2, qw(wilma))
����ֵΪ����1�Ͳ���2֮��ɾ����Ԫ��
����1��ΪҪ�������б�
����2��������ʼ�±꣨ɾ��Ԫ�ذ�����ʼԪ�أ�
����3��ɾ�����ȣ���ʡ�ԣ�ʡ�Ա�ʾ����Ԫ��ȫ��ɾ������Ϊ0��ʾ�������
����4���滻�б���ʡ�ԣ�ʡ�Ա�ʾֻɾ�����滻��


������each
while( my ($index, $value) = each (@array))
�����б���±�Ͷ�Ӧֵ
while( my ($key, $value) = each (%hash))


������select
select OUT  ����  select STDOUT
�ı�Ĭ��������


������keys
my @k = keys %hash;
��ȡhash�Ĺؼ������һ���б�

������values
my @v = values %hash
��ȡhash��ֵ���һ���б�


������exists
exists $books{"dino"}
�鿴��ϣ���ǲ����йؼ���dino


������delete
delete $books{$person};
ɾ���ؼ���$person ����ֵ��ע�⵽�븳��ֵ���������


������split
my @fields = split /separater/, $string;
��$string �е��ַ�����  separater������ʽƥ�������  �����������ĸ������ַ����б����@fields ��
��split��˵��ǰ��Ŀշָ�������������Ķ�ʡ�ԣ��ӵ���������-1��������Ŀշָ�


������join
my $result = join $glue, @pieces;
���б����@pieces ������ַ�����$glue ������������ӳ�һ���´�


������localtime
my $date = localtime;    
����perl����localtime��õ�ǰ��ʱ��
my $timestamp  =  1180630098;
my $date = localtime $timestamp; ## localtime�������ؿɶ���ʱ��
my($sec, $min, $hour, $day, $mon, $year, $wday, $yday, $isdst) = localtime $timestamp;
###��list�������з��ص���Ϣ


������gmtime
Greenwich Mean Timeʱ��


�ؼ��֣�unless
if�ķ���ʣ�������ʱִ�У�������else��ע���������������ִֻ��һ��


�ؼ��֣�until
while�ķ���ʣ�������������ʱ��һֱִ��ֱ���������Ƚ��������ж�


������stat
my($dev, $ino, $mode, $nlink, $uid, $gid, $rdev, $size, $atime, $mtime, $ctime, $blksize, $blocks) = stat($filename);
�����ļ�����ϸ��Ϣ


������lstat
��Ҫ�����ӵ���Ϣ��ʹ��lstat������������ʹ��stat�᷵��ָ���ļ�����Ϣ


������chdir
chdir '/etc'  or die "cannot chdir to /etc:$!"; 
���Ĺ���Ŀ¼����������ʱ��homeĿ¼


������mkdir
mkdir  'fred', 0755 or warn "Can't make fred directory: $!";   ###   ����Ŀ¼fred��755ΪȨ��λ
��Ŀ¼��ע���־λ�ð˽��Ʊ�ʾ����ͷҪ��0


������rmdir
rmdir $dir or warn "cannot rmdir $dir: $!\n";
ɾ��Ŀ¼��ÿ��ֻ��ɾ��һ��


������opendir
opendir my $dh, $dir_to_process or die "Cannot open $dir_to_process: $!";  ### ����Ŀ¼���


������readdir
readdir $dh;   ###   ����Ŀ¼���$dhָ���Ŀ¼����ļ�
���������ļ�������.��ͷ�������ļ���..�Ǹ���ʾ�ϼ�Ŀ¼���ļ����������ļ���������·��


������closedir
closedir $dh;   ###   �ر�Ŀ¼���$dh    ���ӣ�
my $dir_to_process = '/etc';
opendir my $dh, $dir_to_process or die "Cannot open $dir_to_process: $!";
foreach $file (readdir $dh) {
	print "one file in $dir_to_process is $file\n";
}
closedir $dh;


������glob
my  @all_files = glob '*';     ###    �õ���ǰĿ¼�������ļ�����������.��ͷ�������ļ�
my @all_files = <*>;   ###  ����д������һ�ֱ��
my @all_files_including_dot = glob '.* *';  
### ���������ļ����㿪ͷ��Ҳ������ע�⵽�м�Ŀո񣬴˴�������ƥ��
### ƥ�䲻ͬ�ļ�ʱ���ÿո������ע�⵽����ƥ�䲿��������������


������readline
my @lines = readline FRED;    ### ���ļ����FRED�ж�����
my @lines = readline $name;   ###  �Ӽ���ļ�����ж�������


������unlink
unlink  'slate',  'bedrock',  'lava';    ###  ɾ���ļ�slate��bedrock��lava
unlink  qw(slate  bedrock  lava);        ###  ͬ��
unlink  glob '*.o';        ### ɾ������.o�ļ�
�ȿ���ɾ��Ӳ���ӣ�Ҳ����ɾ��������


������link
link  'chicken', 'egg'  or warn "can't link chicken to egg: $!";  ### ����chicken����һ������egg
����һ��Ӳ����


������symlink
symlink  'dodgson', 'carroll' or warn "can't symlink dodgson to carroll: $!"; ###����dodgson��������carroll
����һ��������


������readlink
my  $where =  readlink 'carroll';   ###������dodgson
��ȡ������ָ���Ǹ����ļ�


������chmod
chmod 0755,  'fred', 'barney';   ###  ����fred��barney�����ļ���Ȩ��
��Ȩ�ޣ�ֻ֧�����ֱ�ʾ��Ȩ��
��װFile::chmodģ�飬��chmod֧�ַ���Ȩ��


������chown
my  $user  = 1004;
my  $group  =  100;
chown  $user, $group, glob '*.o';
������������ӵ���ߺ��飬ע��ǰ������������Ϊ����


������utime
my  $now  =  time;    ### time�������ص�ǰʱ��
my  $age  = $now - 24*60*60;
utime  $now,  $ago,  glob '*';   ###   ���ĵ�ǰĿ¼�������ļ��ķ���ʱ��Ϊ��ǰ���޸�ʱ��Ϊһ��֮ǰ
������������һ�β��������ļ���������ʱ�䣬�ڶ������������ļ�������޸�ʱ�䣬��������������Ҫ�޸��ļ�


������index
$where = index($big, $small);
��һ���ַ�����Ѱ���Ӵ��� $big Ϊԭ�ַ����� $small Ϊ�Ӵ������ص�һ��ƥ����ַ����±꣬ʧ�ܷ���-1
my $where1 = index($stuff, "w", 3);   ###  ���±�Ϊ3���ַ���ʼ���Ӵ�w
��ѡ������������ʾ��ʼ����λ��


������rindex
my $last_slash = rindex("/etc/passwd", "/");   ###  ����4
�Ӻ���ǰ����Ѱ���Ӵ���ע�⵽���ص�ֵ�����������������±�
my $where2 = rindex($fred, "abba", $where1-1);   ### ��$where1-1��ǰ���Ӵ�abba
��ѡ������������ʾ��ʼ��ǰ���ҵ��±�


������substr
my $part = substr($string, $initial_position, $length);  
����1Ϊԭʼ�ַ���������2Ϊ��ʼ�±�λ�ã�����3Ϊ�Ӵ����ȣ�����һ������Ҫ����Ӵ�
ʡ�Բ���3�õ�����ʼ������β���Ӵ�������2����Ϊ��������ʾ�Ӻ���ǰ����λ��
my $string = "Hello, world!";
substr($string, 0, 5) = "Goodbye"; # $string is now "Goodbye, world!"  �滻�ַ���������
my $previous_value = substr($string, 0 ,5, "Goodbye");  
### $previous_value�õ���ɾ�����Ӵ�Hello
### $sting �еõ��ı��Goodbye,world!
substr($string, �C20) =~ s/fred/barney/g;  ###  �滻$string���20���ַ�������fredΪbarney


������sprintf
my $date_tag = sprintf "%4d/%02d/%02d %2d:%02d:%02d", $yr, $mo, $da, $h, $m, $s; ##����ֵ���ӣ�2038/01/19 3:00:08"
my $money = sprintf "%.2f", 2.49997;  ###  ����2.50
����һ������Ҫ���ʽ���ַ���


������hex
hex('DEADBEEF') # 3_735_928_559 decimal
��16���Ƶõ�10������


������oct
oct('0377') # 255 decimal
�������������16���ƣ�8���ƣ�2���ƣ�ת���õ�10������


������grep
my @a = grep EXPR, @input_list;
grep �����Ǹ���EXPR���жϰ�list�����������ѡ��ķ�����list��
����ӵĵ�һ������EXPRΪһ���������ʽ��true�Ļ�$_ �е����ݻᱻ��ȡ
grep�����һ������Ϊbool���ʽ��ʱ���ö��Ÿ�����Ϊһ��subroutine��ʱ����{}����������ʱ����һ��������subroutine������һ����������block�飬���Բ���ʹ��return
my @odd_numbers = grep { $_ % 2 } 1..1000;  ###  ��1��1000����ȡ����
��һ�������Ǹ�block������һ������ֵ���ڶ��������ǲ���list
my @matching_lines = grep { /\bfred\b/i } <$fh>;   ### Ѱ�Ұ���fred����,subroutine��д��
my @matching_lines = grep /\bfred\b/i, <$fh>; ###  bool���ʽ��д����д����ע�⵽���Ų�����
�б��������з���list�������������з���listԪ�ظ���
my @lunch_choices = grep is_edible($_), @gilligans_possessions;    
### ʹ��������subroutineʱ��Ҫ�Ӷ���

###  ��ø�λ���ֺ���������Ԫ�ص�������
my @input_numbers = (1, 2, 4, 8, 16, 32, 64);
my @indices_of_odd_digit_sums = grep {
	my $number = $input_numbers[$_];
	my $sum;
	$sum += $_ for split //, $number;
	$sum%2;
} 0..$#input_numbers;         ###  $#input_numbers ��ʾ��list�����������

####  ���@x�б�@y��Ӧλ�ô��Ԫ�ص�����
my @bigger_indices grep {
	$_>$#y or $x[$_]>$y[$_];   ## ����@y�����Ĳ���Ĭ�ϸ���
} 0..$#x;


������map
��һ�������Ǹ�block�����ز���������ڶ��������ǲ���list
map �����ڲ���list�е����ݣ�����ÿ�β������(���ܲ�ֻһ��)
print "The money numbers are:\n", map { sprintf("%25s\n", $_) } @formatted_data;  ## list���ݰ�Ҫ�����
print "Some powers of two are:\n", map "\t".( 2**$_ )."\n", 0..15;   ### ��д����ע�ⶺ��
my @result = map { $_, 3*$_ } @numbers;  
###  ���ÿ��Ԫ�أ�������������������list�ĳ�Ա
my @result = map { split //, $_} @numbers;
###  ��@number��ÿ�����ַֿ�������@result��
my @result = map {
			my @digits = split //, $_;
			if($digits[-1] == 4) {
				@digits;
			}else{
				();
			}
} @numbers;
####    ��@number��ÿ�����ַֿ���ֻ������Щ��λ��Ϊ4�����ֵĸ�������
my %skipper = map {$_, 1} qw(blue_shirt hat jacket preserver sunscreen);  ### ����map����hash

####  ���@x�б�@y��Ӧλ�ô��Ԫ��
my @bigger = map {
	if($_>$#y or $x[$_]>$y[$_]) {
		$x[$_];
	}else{
		();
	}
} 0..$#x;

####  ����һ��list��Ԫ����list���ã�list�����һ��Ԫ�������֣��ڶ���������list������
my @remapped_list = map {
	[ $_ => $provision{$_} ];  ### �ȼ��� [ $_, $provision{$_} ];
} keys %provision;






###########################   ע��ϸ��   ################################

printf ����� % ���� %%

ӳ����÷�
my %last_name  =  (
    'fred' => 'flintstone',
    'dino'  => undef,
    'barney' => 'rubble',
    'betty'  =>  'rubble'
  );
  
my %hash = @result;   ###  ֱ��ת�� @result�����Ԫ�ظ���Ҫ��ż��
indexΪż���Ķ���key��Ϊ�����Ķ���value
 
��Ԫ�����?:   ͬC����
my  $size  =
     ($width < 10 ) ? "small"      :
    ($width  <  20) ? "medium" :
    ($width  <  50) ? "large"      :
                      "extra-large";
					  
�߼���&& (����д��and)  �߼���|| (����д��or)      �ж�·(short circuit)�߼�������ִ�в���Ҫ�Ĳ���
���ֵ� and �� or ���ȼ���ͣ� not �� xor Ҳ�Ǵ��ڵ�


//������        defined-or ������  �����߱���û�����ʹ���ұߵĴ��棨���ḳֵ����߱���������Ҫ5.010��
my $value = $try // 'default';
��$try Ϊ undef ʱ��ֵdefault�ַ�����$value �� $try ��ȻΪ undef
Ӧ�ó���Ϊδ��ʼ���͸���ֵ����ʼ���ٽ�һ������

$#names   ��ʾ�б�names���һ��Ԫ�ص�������
foreach my $index (0..$#names)

open my $log_fh, ">", 'castaways.log';       ####  �洢�ļ������scalar����δ��ʼ��������治��ȥ
print {$log_fh} "We have no bananas today\n";  ### ��{}ǿ������ı������ļ����

��������ݴ�����������Ǵ����ļ�ʱ����������open��һ��������Ϊ���
use CGI;
open my $string_fh, '>', \ my $string;  ### �����ݴ�������������ļ���������ݶ����ڳ�����
CGI->save($string_fh);    ### ����CGI״̬

###  ͨ���ھֲ��ѱ�����ΪSTDOUT�������ⲿʱSTDOUT�ֳ�Ϊ��׼����
print "1. This goes to the real standard output\n";
my $string;
{
	local *STDOUT;
	open STDOUT, '>', \ $string;
	print "2. This goes to the string\n";
	$some_obj->noisy_method(); # this STDOUT goes to $string too
}
print "3. This goes to the real standard output\n";



###########################   �������   ################################


my $ref = \@array;  ȡ������
���κα������б�ӳ��ǰ��� \ ����ȡ�øñ���������
!!!�����õĹؼ��ǣ���{}�������������������ò���ԭ�����������֣�����������ط��žͽ�������!!!
@{$ref}   �����õõ�����
${$ref}[1]   �����ò�ȡ�õ�2��Ԫ��

����������򵥵ı�����ʽ���磺@{$items}  ���� ${$items}[1]�����ǿ���ʡ�Ի����ţ�д��
@$items�� $$items[1]

��{}������򵥵ı���ʱ������ʡ��{}
$ref->[1]
�����У�ÿ��д��${DUMMY}[$y]�Ľ����ã�������д��DUMMY->[$y]����list�����ö���->�ļ�д��ʽ
${${$all_with_names[2]}[1]}[0]    ����д��   $all_with_names[2]->[1]->[0]
							��һ������д��   $all_with_names[2][1][0]
							
ע�����������
my $ref = @array;         ${${$ref[2]}[1]}[0]  д�� $ref[2]->[1]->[0]  д��   $ref[2][1][0];
my $ref = \@array;        ${${${$ref}[2]}[1]}[0] д�� $ref->[2]->[1]->[0]  д�� $ref->[2][1][0];

my %gilligan_info = ( name => 'Gilligan',);
my $hash_ref = \%gilligan_info;
my $name = ${$hash_ref}{'name'};
my @keys = keys %$hash_ref{'name'};
my $name = $hash_ref->{'name'};
->�������н�����ʱ��ǰ�涼��һ�����ã�������


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
	printf $format, @$crewmember{qw(name shirt hat position)};  ## ʹ����hash��slicesģʽ
	
	####  ����չ����д����
	printf $format,
	$crewmember->{'name'},
	$crewmember->{'shirt'},
	$crewmember->{'hat'},
	$crewmember->{'position'};
}

�����̵����ã�       ###  ע�⵽���õ�����������һ����û�в�����

sub skipper_greets {
}
my $ref_to_greeter = \&skipper_greets;
&$ref_to_greeter('Gilligan');  ### ����
$ref_to_greeter->('Gilligan');

my $ginger = sub {        ### ��������subroutine����
	my $person = shift;
	print "Ginger: (in a sultry voice) Well hello, $person!\n";
};            ######   ע�⵽����������ֵ��䣬����;������
$ginger->('Skipper');

use File::Find;      ###  ����ֲ���ļ����ļ��в��ң���ݹ����Ŀ¼��������Ŀ¼���ļ�
sub what_to_do {
	print "$File::Find::name found\n";    ### ����$File::Find::name���������ʼλ�õ����·��
}
my @starting_directories = qw(.);
find(\&what_to_do, @starting_directories);  ### �ҵ���ǰĿ¼�������ļ�����Ŀ¼����ӡ

my $total_size = 0;
find(sub {$total_size += -s if -f}, '.');    ### ͳ��Ŀ¼�������ļ��Ĵ�С��
print $total_size, "\n";


###  �հ������൱��ֻ�ܱ�������ʹ�õ�ȫ�־�̬����  ����������
my $callback;  ### �˱������������block���棬��block����Ļ���find���ñ����Ͳ�������
{
	my $count = 0;      ### ���ڱ������������ã���find����ʱ$count��Ȼ���ڣ���Ϊ�հ�
	$callback = sub {print ++$count, ": $File::Find::name\n"};   ##  ;�����٣��˴��Ǹ�ֵ���
}
find($callback, '.');

sub create_find_callback_that_counts {
	my $count = 0;                      ###  ���հ����������ʼ�����������棬����ֻ��ʼ��һ��
	return sub { print ++$count, ": $File::Find::name\n"}
}
my $callback1 = create_find_callback_that_counts();   
find($callback1, 'bin');      ##  ����$callback1�Ĺ���һ��$count
my $callback2 = create_find_callback_that_counts();
find($callback2, 'lib');      ##  ����$callback2�Ĺ�����һ��$count

###  �ֱ��޸ĺͶ����հ�����
sub create_find_callback_that_sum_the_size {
	my $total_size = 0;
	return(sub {$total_size += -s if -f}, sub {return $total_size});
}
my ($count_em, $get_results) = create_find_callback_that_sum_the_size();
find($count_em, 'bin');
my $total_size = &$get_results();

sub print_bigger_than {       ### ��ʼ�������̿��԰������������������̲�����
	my $minimum_size = shift;
	return sub {print "$File::Find::name\n" if -f and -s >= $minimum_size};
}
my $bigger_than_1024 = print_bigger_than(1024);   ### 1024��ɸ����ù̶�ֵ
find($bigger_than_1024, 'bin');

###  �հ�����һ������� state �����ľ�̬�����滻������stateֻ�ܳ�ʼ��scalar
###  state������list��hash���ܳ�ʼ�������������
{
	my $countdown = 10;
	sub count_down { $countdown-- }
	sub count_remaining { $countdown }
}
count_down();
print "we're down to ", count_remaining(), " coconuts!\n";
�ȼ���
sub countdown {
	state $countdown = 10;
	$countdown--;
}
###  state������list��hash���ܳ�ʼ������������󣬵����������ñ���ʵ�ֳ�ʼ��
sub add_to_tab {
	my $castaway = shift;
	state $castaways = qw(Ginger Mary Ann Gilligan); # works!
	state %tab = map { $_, 0 } @$castaways; # works!
	$tab->{$castaway}++;
}
####   ���������̵�������
my $countdown = sub {
	state $n = 5;
	return unless $n > -1;
	say $n--;
	$countdown->();     ###  ��������
};
$countdown->();
����д��
my $sub = sub {
	state $n = 5;
	return unless $n > -1;
	say $n--;
	__SUB__->();    ### v5.16֮�����Ļ�����������ʾ���������̵�����
};
$sub->();

�ļ���������ã�    ### �������ݲ��Ƽ�
log_message( \*LOG_FH, 'An astronaut passes overhead' );
sub log_message {
	local *FH = shift;
	print FH @_, "\n";
}

������ʽ�����ã�
my $pattern = 'coco.*';
if(/$pattern/) {           #### ������ʽ���滻����������˴�������ʽ�������﷨Ҫ��
}                          #### ��ֻ��������ʱ���ܷ���

qr// ������
my $regex = qr/Gilligan|Skipper/;   ### ����һ���������������ʽ������
my $regex = eval { qr/$pattern/ };    ###  ��ȻҪ��eval����������ʽ���Ƿ�����﷨Ҫ��

m/Gilligan$/migc     ##  migc��Ϊ��ǩ
qr/Gilligan$/mi;      ##  qr�ñ�ǩ��д����gc�����ã�ֻ����������pattern�ϵı�ǩ
qr/(?mi:Gilligan$)/;   ## �ڶ���д��  ���õ�ͨ�õ�(?flags:pattern)ģʽ
qr/abc(?i:Gilligan)def/;   ## ֻ�����е���ĸ���Դ�Сд��д��
qr/abc(?x-i:G i l l i g a n)def/i;   ###  �����ڲ���Ӧ��x��ǩ��ȡ��i��ǩ��Ӧ��

my $regex = qr/Gilligan/;
$string =~ m/$regex/;         ### �Ϸ�
$string =~ s/$regex/Skipper/;      ### �Ϸ�
$string =~ $regex;        ### �Ϸ�
$string ~~ $regex;        ### ʹ������ƥ��

my @patterns = (
	qr/(?:Willie )?Gilligan/,
	qr/Mary Ann/,
	qr/Ginger/,
	qr/(?:The )?Professor/,
	qr/Skipper/,
	qr/Mrs?. Howell/,
);
my $name = 'Ginger';
say "Match!" if $name ~~ @patterns;    ###  ����ƥ�䣬��ģʽƥ��

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
my( $match ) = first { $name =~ $patterns{$_} } keys %patterns;  ### ������ƥ��Ľ����ȡ��һ��
say "Matched $match" if $match;

####  ������õ������Ƿ����         
####  ʹ�� ref �ؼ�����ȡ�������ͽ�����ʾ���
use Carp qw(croak);
sub show_hash {
	my $hash_ref = shift;
	my $ref_type = ref $hash_ref;
	croak "I expected a hash reference!" unless $ref_type eq 'HASH';
	###  ����дΪ
	croak "I expected a hash reference!" unless $ref_type eq ref {};
}

###    ����������δͨ����when��Ϊif�����������
my @array = ( \ 'xyz', [qw(a b c)], sub { say STDOUT 'Buster' } );
foreach ( @array ) {
	when( ref eq ref \ '' ) { say STDOUT "Scalar $$_" }
	when( ref eq ref [] ) { say STDOUT "Array @$_" }
	when( ref eq ref sub {} ) { say STDOUT "Sub ???" }
}

����

use Carp qw(croak);
use Scalar::Util qw(reftype);
sub show_hash {
	my $hash_ref = shift;
	my $ref_type = reftype $hash_ref; # works with objects
	croak "I expected a hash reference!" unless $ref_type eq ref {};
}

����

###  ʹ��eval�����ԣ���eval֮�е�����ʹ�ã������������ͱ������򷵻�����1
croak "I expected a hash reference!" unless eval { keys %$ref_type; 1 }  

�������鹹�����������ŵ���һ���÷��������ڲ��б���ʽ�г�����������һ���������飬���ظ����������
my $ref_to_skipper_provisions = [ qw(blue_shirt hat jacket perserver sunscreen) ]
�ȼ���
my $ref_to_skipper_provisions;
{
	my @temporary_name = ( qw(blue_shirt hat jacket preserver sunscreen) );
	$ref_to_skipper_provisions = \@temporary_name;
}

###  Ӧ�þ���������������
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
		###  ����һ�����ã�ͬʱ��ֹ����ʱ����
		$provisions{$person} = [ ] unless $provisions{$person};
	} elsif (/^\s+(\S.*)/) { # a provision
		die 'No person yet!' unless defined $person;
		push @{ $provisions{$person} }, $1;
	} else {
		die "I don't understand: $_";
	}
}


����ӳ�乹��������
my $ref_to_gilligan_info = {
	name => 'Gilligan',
	hat => 'White',
	shirt => 'Red',
	position => 'First Mate',
};

����ӳ�����úʹ���block������ʽ���ƣ�Ϊ��ʾ���𣬿ɲ������·���
my $hash_ref = +{...};      ####  �������ԼӺſ�ͷ

code block:
{; ... }          ###  ���������;��ͷ



###########################   �����÷�   ################################

1������printf���������С��̬�������

my @iterms = qw( wilma dino pebbles );
my $format = "the item are:\n". ("%10s\n" x @iterms);
printf $format, @items;

��д��
printf "The items are:\n".("%10s\n" x @items), @items;


2��������ļ����

print $rock;    �������������
print { $rock_fh };   ���$_ ���ļ���� $rock_fh        �ô����ŰѾ����������������perl֪�����Ǹ��������������perl���䵱��һ����ͨ����


3��һ�仰perl����

perl -p -i.bak -w -e 's/Randall/Randal/g' fred*.dat

���ͣ�
	-p  ��perl�Լ����Ƴ�Ϊһ��С���򣬴����൱��
		while(<>) {
			print ;
		}

	-n  ͬ -p������û��print���⼴�����

	-i.bak   ����$^I Ϊ.bak

	-w   ��warnings
	
	-e  ��ʾִ�д������£����ִ�д��룬ִ�д����õ�������������unix������ϰ�ߣ�
	
perl -le "print for @INC"  һ�仰perl���鿴@INC ���������
	
	
4�����ʽ���η�

�����ں���Ĳ���Բ���ŵ� if  while  unless until  foreach
�ʺ�һ�仰ģ��
$sum += $_ for @digits;


5��perl�е����ѭ���飺
for,   foreach,  while,   until,   ������{}
last;    ����ѭ��

next;    ��������ѭ��
next���������з���ֵ���� eval {} , sub {} or do {}�����Ĵ�����У�Ҳ�������� grep() or map()����

redo;   ����ִ�п��ڴ��룬�����������жϺ͵����������


6����ǣ�label����:
LINE:   while(<>) {
			......
			last   LINE  if  /__END__/;   ###  �����ļ�βʱ����while��
	    }
  
  ������ last   next   redo ����֮һ����LINE��ǵ������飬ע�⵽label��goto�����岻ͬ��label��������飬����������loop��
  
  
7��ִ���ⲿ����

``ִ�з�����������ⲿ������ڲ����ⲿ��������н��
�������ڳ���ķ��ؽ���ᱻperl��װ��һ��string���أ�ע�⵽���string�Դ����з�\n
������ص�string�����ж�����з��������б��������еõ����з��ָ��һ���б�
�����������������ݵĴ�������˫���ŵ�Ч��
chomp(my $no_newline_now = `date`);
qx/ /  ����ͬ�ϣ��⼴������ŵ����ֱ�ʾ���������������ݵķ������Ƶ����ţ�delimiter��ѡ������qw//
�����Ų�Ҫʹ������Ҫ������������ϣ������ȷ�������Ƿ���Ҫ���룬�����·�ʽ��
my $result = `some_questionable_command arg arg argh </dev/null`;   ### ��bitͰ��Ϊ����



system('$cmd')
system���س�������״̬��system����һ��fork��exec�������ӽ�����������$cmd ����
@args = ("command", "arg1", "arg2")
system(@args) == 0 or die "system @args failed:$?"
system "long_running-command with parameters &";
shell���յ�long_running-command��ź�̨���У�ʵ����long_running-command�൱������̣���perl�ű�ûֱ�ӽ���
my $tarfile = 'something*wicked.tar';
my @dirs = qw(fred|flintstone <barney&rubble> betty );
system 'tar', 'cvf', $tarfile, @dirs;
�����ֲ����ֿ��ķ������ã���������shell������shell��*��|��>����Ԫ�ַ���ʧȥ�����壬ͬʱ��ȥshell���õ�IO�ض��򣬺�̨����ȹ���
!system 'rm -rf files_to_delete' or die 'something went wrong';
�����÷�����Ϊshell�ڳɹ��󷵻�0

exec ����������ǰ��������в���ִ��һ���ⲿ����Ҿ������أ����ֵ����ⲿ�����İ취�õĲ��ࣩ
��ǰ���е�perl����ִ��exec����������ʱ�Ѿ��������е�perl�ű�������û��perl�ķ�����
���ڸ�Ҫִ�е������������û���������IO�ӿڵ�
exec 'bedrock', '-o', 'args1', @ARGV;

8�������Ÿ�ʽ��ʾǮ��

sub big_money {
	my $number = sprintf "%.2f", shift @_;  ### ֻ����һ����������ȷ���֣�С�����ڶ�λ��
	# Add one comma each time through the do-nothing loop
	1 while $number =~ s/^(-?\d+)(\d\d\d)/$1,$2/;  ###  ÿ�δӺ���ȡ�������֣�����һ������
	# Put the dollar sign in the right place
	$number =~ s/^(-?)/$1\$/;       ####  �ڷ���֮���һ��$
	$number;
}
###  ��Ϊ�ǴӺ���ǰ�ң�������while��������/g����ȫ���滻

my @a = $str =~ /\w{2}/g;   ### ��$str�ֲ��������ĸΪһ����λ��list




9������ƥ��:   ~~
use 5.010001;  ### �汾����

say "I found a key matching 'Fred'" if %names ~~ /Fred/;   ####  Ѱ��hash���Ƿ���keyΪFred
say "I found a key matching 'Fred'" if /Fred/ ~~ %names;   ###  ��һ��д��
��ͬ��
my $flag = 0;
foreach my $key ( keys %names ) {
	next unless $key =~ /Fred/;
	$flag = $key;
	last;
}
print "I found a key matching 'Fred'. It was $flag\n" if $flag;

say "The arrays have the same elements!" if @names1 ~~ @names2;   ###  �Ա������б��Ƿ���ȫ��ͬ
��ͬ��
my $equal = 0;
foreach my $index ( 0 .. $#names1 ) {
	last unless $names1[$index] eq $names2[$index];
	$equal++;
}
print "The arrays have the same elements!\n"
if $equal == @names1;

say "The result [$result] is one of the input values (@nums)" if $result ~~ @nums;  
###  ����Ƿ�Ϊ�б���һ��Ԫ��
����ƥ��ĳ����÷�
Example							Type of match
%a ~~ %b			 			hash keys identical
%a ~~ @b or @a ~~ %b 			at least one key in %a is in @b
%a ~~ /Fred/ or /Fred/ ~~ %b 	at least one key matches pattern  ### �����ַ���ƥ��ʱ���ַ���������ǰ��
'Fred' ~~ %a 					exists $a{Fred}
@a ~~ @b 						arrays are the same
@a ~~ /Fred/ 					at least one element in @a matches pattern  
### �ڶ����Ϊ�����ַ����򲻳ɹ�ƥ��
$name ~~ undef 					$name is not defined
$name ~~ /Fred/ 				pattern match
123 ~~ ��123.0��					numeric equality with ��numish�� string ###  ������Ƚϣ����ֱ�����ǰ
��Fred�� ~~ ��Fred�� 				string equality
123 ~~ 456 						numeric equality


10��given���
use 5.010001;
given ( $ARGV[0] ) {
	when ( 'Fred' ) { say 'Name is Fred' }
	when ( /fred/i ) { say 'Name has fred in it' }
	when ( /\AFred/ ) { say 'Name starts with Fred' }
	default { say "I don't see a Fred" }
}
����C���Ե�switch��䣬�����м��Ĭ��ƥ��������ƥ�䣬Ĭ���Դ�break��ƥ��һ���Ͳ�ִ��ʣ�µ�ƥ��
when ( $_ ~~ /fred/i ) { say 'Name has fred in it'; continue }
��һ��continue������ƥ��ɹ������ƥ��ִ�н�������when
when���������dumb comparison����������==��>��<��!����=~��Ҳ���Է���subroutine�ķ��ؽ��

use 5.010001;
foreach ( @names ) { # don't use a named variable!
	say "\nProcessing $_";
	when ( /fred/i ) { say 'Name has fred in it'; continue }
	when ( /\AFred/ ) { say 'Name starts with Fred'; continue }
	when ( 'Fred' ) { say 'Name is Fred'; }
	default { say "I don't see a Fred" }
}
���ڶ���������жϣ�ʡ�Ե�given��ֱ����foreach���жϱ�������$_


11��perl����Ķ���̲�����ʹ��open�������ļ���ǰ����ߺ���ӹܵ���|
open DATE, 'date|' or die "cannot pipe from date: $!";       ####    date�Ľ�����ӵ�perl�ı�׼����
open MAIL, '|mail merlyn' or die "cannot pipe to mail: $!";   ####   perl�����������mail
ռλ�����������������ڵ�λ��
open my $date_fh, '-|', 'date' or die "cannot pipe from date: $!";    
###  ������ļ����������date�Ľ����Ϊ����
open my $mail_fh, '|-', 'mail merlyn' or die "cannot pipe to mail: $!"; 
 ### ��д���ļ����������perl�����������mail
 
 open my $pipe, '-|', $command or die "cannot open filehandle: $!";  ### ��һ���ⲿ����������
 while(<$pipe>) {
	print "Read: $_";
 }
 ��open�����ĺô����ӽ���Ҫ����ܾ�ʱ�����Եõ�һ�������������ʣ�µ�perl�ű��������뷴��������ֻ�ܵ��ӽ��̽��������������ʣ�µ�perl�ű�
 
 
 
12��slices����õ��б��м�����Ԫ�أ�����Ԫ�ض���ʹ�õ�ʱ��
ʹ�÷��������б���Χ����С���ţ������±�����
my $mtime = (stat $some_file)[9];    
###  �õ�stat���صĹ���ĳ���ļ����޸�ʱ������ݣ��޸�ʱ���ڷ��ؽ��list�ĵھ���
while(<IN>) {
	my $num_5 = (split /:/)[4];    ###  ���split����Ԫ�صĵ�����
}
my ($num_3, $num5) = (split /:/)[2, 4];       ### �б��������л������Ԫ��
my($first, $last) = (sort @names)[0, -1];     ###  ��õ�һ�������һ��Ԫ��
my @numbers = ( @names )[ 9, 0, 2, 1, 0 ];   ###  �±����������˳��Ҳ�����ظ�����array slice������

array slice���������л�ø���Ԫ�أ��������Ǹ����б��л���������﷨��˼���б�������ʵ�ʽ��û����
my @names = qw{ zero one two three four five six seven eight nine };
print "Bedrock @names[ 9, 0, 2, 1, 0 ]\n";    ####  ����list���Ӧ��Ӣ���ַ������ո�ֿ�
@items[2, 3] = ($new_address, $new_home_phone);   ###   �ı�list���������Ԫ��

hash slice���õ�hash�и���Ԫ�ص�ֵ������������Ҫ
my @three_scores = ($score{"barney"}, $score{"fred"}, $score{"dino"});  ####  ֱ������key�õ�ֵ
my @three_scores = @score{ qw/ barney fred dino/ };   ###  hash slice ע�⵽ǰ�滹��@����
my @players = qw/ barney fred dino /;
my @bowling_scores = (195, 205, 30);
@score{ @players } = @bowling_scores;        ###   ����hash����ļ��ı����м���Ԫ�ص�ֵ
print "Tonight's players were: @players\n";
print "Their scores were: @score{@players}\n";   ###  ÿ��ֵ�ÿո�ֿ�


13���쳣����eval������һ��fatal errorʱ����undef����$@ �����ô�����Ϣ��û�д���ʱ$@ Ϊundef��eval��������ִ�е����һ�����������û���������������Ϊ��һ��������expression�������ڵ����ɾ��ʱ��Ҫ�ӷֺ�;
eval { $barney = $fred / $dino };   ###  ������$dinoΪ0���쳣ʱ������eval��Χ�Ĵ���飬����ִ�к���Ĵ���
my $barney = eval { $fred / $dino } // 'NaN';  ###  ����Ч��д������$dinoΪ0ʱ��eval����undef
print "I couldn't divide by \$dino: $@" if $@;
unless( eval { $fred / $dino } ) {         ###  ��ʵ�и�һ����÷�
	print "I couldn't divide by \$dino: $@" if $@;
}

eval���ܴ������ִ���
1��perl�����﷨����
2���ڴ�й¶�ȴ������ִ���ֱ�ӹص�perl������
3�����棬-w����use warnings���õ�
4��exit�˳�����perl����

{
local $@; # don't stomp on higher level errors  ����$@�ķ�Χ�����block�ڣ���Ӱ���ϲ��eval
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

###    ���� eval �����ֵĲ����һ����{}block�飬һ�����ַ������ʽ


14�� eval ��ִ��һ��string expression���ݣ�������һ��{}block������ľ��ӣ��������һ��ֵ
��eval�Ĳ�����һ���ַ������ʽ�����ʽ����������ʱ�ȱ�����ִ��
eval '$sum = 2 + 2';
print "The sum is $sum\n";

for my $operator ( qw(+ - * /)) {
	my $result = eval "2 $operator 2";
	print "2 $operator 2 is $result\n";
}
####  ����ǰ׺���ĸ�����Ľ��
��������Ǹ����Ӳ���ִ�У���$@ ����������Ϣ

print 'The quotient is ', eval '5 /', "\n";
warn $@ if $@;
eval�Ľ���д��������������

15�� do ���÷�: �������жϷŵ�һ��
my $bowler = do {
	if(... some condition ...) {'Mary Ann'}
	elsif(... some condition ...) {'Ginger'}
	else {'The Professor'}
};              ###  ע��˴��ķֺţ�����ͬeval
�ȼ���
my $bowler;
if(...some condition...) {
	$bowler = 'Mary Ann';
}elsif(...some condition...) {
	$bowler = 'Ginger';
}else{
	$bowler = 'The Professor';
}

my $file_contents = do {            ####  ��һ���ļ����������ݶ��뵽һ��������
	local $/;
	local @ARGV = ($filename);
	<>
};


16��The Schwartzian Transform
my @output_data =
	map { EXTRACTION },      ###  ��ȡ�ȽϽ������Ҫ�Ĳ���
	sort { COMPARISON }      ###  ��ȡ�����еıȽϲ��ֽ��бȽ�
	map [ CONSTRUCTION ],     ### ����������
	@input_data;
	
####  ���ӣ������ִ�Сд���ַ�������
my @output_data =
	map $_->[0],
	sort { $a->[1] cmp $b->[1] }
	map [ $_, "\F$_" ], ### \F ���ڰѺ��������ַ�ȫ����д
	@input_data;

###  ��hash����list	
my @output_data =
	map $_->{ORGINAL},
	sort { $a->{FOLDED} cmp $b->{FOLDED} }
	map { ORIGINAL => $_, FOLDED => "\F$_" },      ### ʹ��hash���棬�ؼ��ִ����±��������
	@input_data;

###  ���Ԫ������
my @output_data =
	map $_->[0],
	sort {
		$a->[1] cmp $b->[1] or  
		$a->[2] <=> $b->[2] or
		$a->[3] cmp $b->[3] }
	map [ $_, lc, get_id($_), get_name($_) ],
	@input_data;
	
map $_->{VALUE},      ### ʹ��hash��д��
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