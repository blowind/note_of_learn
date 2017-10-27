linux下新命令窗口中打开R的界面，多个菜单栏：
$ R -g Tk &           ###  $ 是命令提示符
$ R CMD BATCH generate_graphs.R  
###  在命令提示符下运行R批处理文件
###  generate_graphs.R是脚本，结果输出到generate_graphs.Rout
$ RScript generate_graphs.R      ###   同上
$ R CMD BATCH a.R a_`date "+%y%m%d"`.log    ### 同上，结果输出到带时间的log文件里
$ R CMD INSTALL aplpack_1.1.1.tgz         ##  从shell窗口安装包
$ R CMD check nutshell       ###  检查要生成的包是否符合要求
$ R CMD CHECK --help         ###  获得check的帮助
$ R CMD build nutshell       ###  生成包


两个集成IDE：
R Productivity Environment
RStudio


打开某个函数的手册页：
help(solve)    
?solve
?`+`          ###  打开操作符的帮助文件时，操作符要用反引号框起来
help.start()   ### 打开帮助页  (html格式)
help.search("regression")     ###   寻找某个话题的帮助，忘掉某个函数的名字时有用
??regresstion                 ###   同上
example(topic)                ###   打开某个话题的例子页
library(help="grDevices")     ###   获得某个库的帮助信息
vignette("affy")              ###   查看包的专门的帮助文档
vignette(all=TRUE)            ###   查看所有有专门帮助文档的包

source("commands.R")  ###  调用已有的命令脚本，必须在工作目录中
sink("record.lis")   ### 保存结果到record.lis文件
sink()   ###  结果直接输出到控制台

ls()   ###  显示R当前存储的所有对象的名字

rm(x, y, z, ink, junk, temp, foo, bar)  ###  删除对象x, y, z......



 					########       包相关         ########
					
install.packages("RExcelInstaller", "rcom", "rsproxy")    ##  安装包
install.packages(c("tree", "maptree"))     ##  安装 tree  和 maptree 两个包
remove.packages(c("tree", "maptree"), .Library);  ###   删除安装在默认目录的两个包

installed.packages              ##  返回当前安装的所有包的包名数组
available.packages              ##  返回可用包的包名数组
update.packages                 ##  更新所有可更新的包


####   制作包
package.skeleton(name = "anRpackage", list,
				 environment = .GlobalEnv,
				 path = ".", force = FALSE, namespace = FALSE,
				 code_files = charactor())
##  name说明包名，list表示一个包含加入此包中R对象名字的字符向量
package.skeleton(name="nutshell", path="~/Documents/book/current/")


.Library          ##   查看当前会话包的默认安装位置
getOption("defaultPackages")       ###    查看当前会话导入的包
(.packages())                      ###    同上
(.packages(all.available=TRUE))    ###    查看所有安装了的可用的包
library()                          ###    同上


三个用于可视化的包： graphics, grid, lattice
library(nutshell)    ##  载入某个包
data(field.goals)    ##  读入某个包里的数据

dim(cars)   ##  得到cars数据包里面的数据量个数，维度个数
summary(cars)  ##  查看cars数据包的各个维度的统计信息
names(cars)    ##  查看cars数据包里面所有维度的名字



 					########       画图相关         ########


hist(field.goals$yards)   ## 画柱状图
hist(field.goals$yards, breaks=35)  ## 画柱状图，设定每个柱子的宽度
table(field.goals$play.type)
stripchart(field.goals[field.goals$play.type=="FG blocked",]$yards, pch=19, method="jitter")
###  不详
plot(cars, xlab = "Speed (mph)", ylab = "Stopping distance (ft)", las = 1, xlim = c(0,25))
###  指定 xlabel ylabel  xlim -> x轴区间

library(nutshell)
library(lattice)    ###  lattice画图库默认不载入
data(consumption)  ##  载入数据包
dotplot(Amount ~ Year | Food, data=consumption, aspect="xy", scales=list(relation="sliced", cex=.4))        
###   根据公式显示图(因变量：Amount  自变量：Year Food) aspect保持x,y轴单位刻度大小一致




 					########       基础相关         ########　          


x <- 1   ##  基本赋值
x <- c(10.4, 5.6, 3.1, 6.4, 21.7)   ###  调用函数c() 生成一个 向量vector，函数接收任意数目参数
assign("x", c(10.4, 5.6, 3.1, 6.4, 21.7))   ## 同上，调用赋值函数进行赋值
y <- c(x, 0, x)   ###   创建一个含有11个元素的向量，元素0在正中间
x[3]    ###  取出x里面第3个元素
x[1:3]    ###  取出第1到3个元素
x[a %% 3 == 0]  ###  a=c(1,2,3,4,5,6), 取出编号是3的倍数的元素   
y[c(1,6,11)]   ###  取出第1, 6, 11号元素，按指示的顺序
y[c(8,4,9)]    ###  同上，结果按8,4,9的顺序排列

绝大多数对象的赋值都是内容拷贝而不是地址拷贝，包括vector, list, 函数的形参实参

NA 表示缺失值 not available
Inf(-Inf)  表示计算机无法表示的大数(小数)  positive infinity(negative infinity)
1/0   也得到 Inf
NaN   不能计算的值  not a number    ###  Inf - Inf  和  0/0  都得到NaN
NULL  空值



基本操作符   + - * / ^(指数运算符) %%(取余运算符) %/%(整除运算符)
log exp sin cos tan sqrt
41 %% 21   # 得到余数 20
20 ^ 1     # 得到20的一次方20
21 %/% 2   # 整除运算，得到10
exp(1) e的一次幂
log2(1)  对数，得到0
log(x=64, base=4)

R中所有操作都有对应的函数形式，函数名的基本形式是用反引号括起来的操作符表示
apples <- 3   对应    `<-`(apples, 3)
animals <- c("cow", "chicken", "pig", "tuba")
animals[2]               对应     `[`(animals, 2)
animals[4] <- "duck"     对应     `[<-`(animals,4,"duck")
apples + oranges         对应     `+`(apples, oranges)
if (apples > oranges) "apples are better" else "oranges are better"
对应     `if`(apples > oranges,"apples are better","oranges are better")


					########       函数相关         ########　　　

f <- function(x,y) {c(x+1, y+1)}   ###  函数定义，命名
`%myop%` <- function(a, b) {2*a + 2*b}  ###  定义一个二元操作符
1 %myop% 1     ###  得到4



					########       矩阵相关         ########　

a <- array(c(1,2,3,4,5,6,7,8,9,10,11,12), dim=c(3,4))   ###  矩阵定义，命名，按列填充
a[2,2]   ###  矩阵元素读取，得到5        ###   注意到维度以逗号分开
a[1:2, 1:2]   ###   读取矩阵内部的小矩阵
a[1,]    ###  得到一行数据
a[,1]    ###  得到一列数据
a[1:2,]    ###   得到1,2行所有数据
a[c(1,3),]  ###     得到第1,3行数据
w <- array(c(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18),dim=c(3,3,2))
w[1,1,1]

m <- matrix(data=c(1,2,3,4,5,6,7,8,9,10,11,12), nrow=3, ncol=4)   ### 定义二维矩阵


					########       list相关         ########　
list对每个元素的内容没要求，成员可以是不同类型的东西
数字向量 numerical vector 和 字符向量 charactor vector 成员都是单一类型
					
e <- list(thing="hat", size="8.25")  ###  定义一个list，并给list中的元素命名
e$thing      ##  通过e和名字读取list中的元素内容，得到"hat"
e[1]         ##  得到  $thing   [1] "hat"
e[[1]]       ##  得到 "hat"

teams <- c("PHI", "NYM", "FLA", "ATL", "WSN")
w <- c(92, 89, 94, 72, 59)
l <- c(70, 73, 77, 90, 102)
nleast <- data.frame(teams, w, l)   ###  声明一个data frame，每个元素为大小相同的向量
nleast$w   ##  通过名字来访问内部元素向量
nleast$l[nleast$teams == "FLA"]   ###  通过生成一个新布尔值的vector来索取 l 中的FLA值


class(teams)  ## 得到 character      ###  获得某个对象对应的类
class(nleast)  ##  得到  data.frame
class(class)  ##  得到 function

y ~ x1 + x2 + ... + xn    ###  定义一个公式对象



max/min 选出 向量中最大/最小的元素
range 得到向量中最小和最大的元素，结果是个新向量   等同于  c(min(x), max(x))
length(x)  向量x中元素个数
sum(x)     向量x中元素的和
prod(x)    向量x中元素的积
mean(x)    向量x的均值    等价于  sum(x)/length(x)
var(x)     向量x的方差    等价于  sum((x-mean(x))^2) / (length(x)-1)
sort(x)    得到x的升序的拷贝(新向量，原x不变)
pmax(x, y, z)  以最大向量为准，其他向量平移扩张到一样大，取三个向量每一维的最大值组成新向量
例如：
pmax(c(1,2,3,4,5), c(5,4,3,2,1))  得到  c(5,4,3,4,5)
pmin(x, y, z)  同上，取各个向量每一维的最小值组成新向量

默认不用复数，要使用的显式声明
sqrt(-17)  会报错
sqrt(-17+0i)  不会报错，当做复数执行


使用a:b模式得到a和b之间所有的整数的向量vector
使用c(arg)模式可以得到混合浮点，整型，字符的向量
1:30  得到  c(1,2,3,...,29,30)    : 运算符在表达式中优先级最高
2*1:15   得到  c(2, 4, 6, ..., 28, 30)
30:1   得到递减顺序的vector
seq(1, 30)   效果同  1:30
R支持参数变量  例如：  seq(from, to, by, length)
seq(to=30, from=1)    等同于  1:30   参数变量的位置可以任意放置  by说明步长  length说明序列长度
seq的第五个参数是along，只能单独使用  along = vector  创建一个 1,2,3,....,length(vector)的普通序列

rep(x, times=5)   创建一个向量，内容由5个x顺序连起来
rep(x, each=5)    创建一个向量，从左到右依次重复x里的每个元素5次

TRUE FALSE   注意到简写 T/F 是两个自带赋值为TRUE/FALSE 的变量，所以其值可以被人改变，慎用
temp <- x > 13   将判断值赋给temp
注意到x为向量时，temp也是一个大小相同的向量，值为各个元素的比较结果

quote()    ###  分析参数，但是不执行
quote(if (x > 1) "orange" else "apple")   ###   识别出是R语句
typeof()    ###   识别参数类型
typeof(quote(if (x > 1) "orange" else "apple"))    ##  得到language，说明参数是R语言
as(quote(if (x > 1) "orange" else "apple"),"list")
###   得到  if   x>1  orange  apple   四个list元素
lapply(as(quote(if (x > 1) "orange" else "apple"), "list"),typeof) ###对每个元素使用typeof
###   得到  symbol  language  charater  charater  
