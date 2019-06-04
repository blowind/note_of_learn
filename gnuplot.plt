set terminal postscript eps enhanced color   
set termimal postscript eps color solid linewidth 2 "Helvetica" 20 
###　color 参数表示我们要彩色图，solid 表示我们要实线不要虚线
###  linewidth 参数指定2倍线宽，而最后指定使用 Helvetica 20 号字体
###  输出终端，表示你打算用什么方式输出图片 还有诸如 jpeg  png  
###  默认是wxt，表示直接输出到屏幕

###  此处的enhanced表示title，xlabel标签表示的内容除了用纯文本模式，还可以使用诸如字母加角标的复杂标签
###  很多terminal都支持enhanced模式，使用方法就是在set terminal的时候，在后面加上enhanced参数
###  enhanced模式里有一些表达特殊含义的字符，利用这些字符可以构成一些比较复杂的文字输出。这些特殊字符主要包括：

1.   ^：表示后面的字符为上角标  ###  例如："a^x"表示a的x次方形式
2.  _：表示后面的字符为下角标   ###  例如："a_x"表示下标为x的a的形式
3.  @：表示后面的字符不占任何宽度
	###  "a^b_{cd}"  中，上标b和下标cd不在一列
	###  "a^@b_{cd}"  中，上标b和下标cd中的c在一列
	
4.  &{"string"}：表示一段空白，空白的长度等于花括号内那段字符串所占宽度
	###  "abc&{de}fg" 中，de出现的地方显示空白
	
	
5. ~：表示后面的两个字符重叠打印（相当于打字机在同一位置打印两个字符）；
      也可以在第二个字符前加上一个数字，表示第二个字符相对于第一个字符有一个竖直方向的移动，
	  移动距离等于该数字乘以字符尺寸。
	  
	  ###　"~a{1.2\\_}"  表示a上面加个横杠
	  ###  注意这里的两个反斜杠。因为下横杠（_）是一个特殊字符，需要在前面加反斜杠 \ 来表示它本来的意义，而反斜杠本身也是一个特殊字符，需要在它前面再加一个反斜杠。如果我们在这里用单引号而不是双引号，那么只需要一个反斜杠就可以了。这里的 1.2 表示后面的字符（_）向上移动 1.2 个字符大小的距离。
	  
	  ### 希腊字母 αβχ，对应的16进制代码分别为 61、62、63，转换为8进制代码就是 141、142、143。在 gnuplot 里，我们可以直接用字符的8进制代码表示这个字符，所以我们也可以用 {/Symbol \141\142\143} 来表示 αβχ。
	  
	  
	  
##################### 虽然利用 enhanced 模式也能显示一些简单的数学表达式，
#########   但是对于稍微复杂一点的公式来说，显示效果无法令人满意。要在gnuplot里显示数学公式，终极方案还是要用 LaTeX
	  
	  
set term epslatex standalone lw 2 color 11
set output "erf.tex"

### epslatex 和我们之前介绍过的 postscript eps 输出方式非常接近，因此它们很多参数都是相同的。
### 区别在于，epslatex 使用 postscript eps 仅生成图形存于 eps 文件，而所有文字标签包含在另外一个 LaTeX 文件中。
### 在 gnuplot 完成输出之后，使用 LaTeX 命令最终生成完整的图片。

### 在 set term 命令里，standalone 是一个新的参数，它表示生成完整的 LaTeX 文件。
### 如果没有这个参数，生成的 LaTeX 文件将不能单独编译，必须把代码插入其它的 LaTeX 文件中编译。
### set term 最后的数字 11 代表字体大小。

### set output 只需指定 LaTeX 文件名，而不需要指定 eps 文件名。
	  

	  
###  上面提到的“字符”，也可以是包含在花括号 {}内的“字符串”。
###  除了上面这些特殊标志，还可以临时改变字符（或字符串）的字体，
###  方法是：{/字体名=字体大小 字符}

	  ###  {/Symbol abc}abc   显示为： αβχabc
	  ###  前三个字母为alpha，beta等希腊字母，后面为abc三个英文字母
	  ###  这里第一个花括号里的 abc 使用了 Symbol 字体，而后面括号外的 abc 使用的是默认字体。
	  ###  注意字体名称前的斜杠方向。Symbol 字体里的英文字母对应显示的是希腊字母。

set term pdfcairo lw 2 font "Times New Roman,8"
###  输出到pdf文件，相比简单的pdf，pdfcairo使用了cairo一个2D图形程序库和pango（一个字体渲染程序库）来生成pdf文件
###  此处字体选择的是系统内的字体

set term pngcairo lw 2 font "AR PL UKai CN,14"
###  输出到png文件，其他同上

## 加、减、乘、除、乘方
## 分别用+，-，*，/，**表示

## 整数和浮点数和C语言类似，gnuplot对整数和浮点数（实数）区别对待，整数的运算结果还是整数。
## 所以在处理整数除法时要尤其小心，例如7/2的结果是3而不是3.5

## 复数: gnuplot支持复数运算，复数用包含在花括号内的一对实数表示，例如{3,5}表示 3+5i

## 圆周率在gnuplot里用 pi 表示。

## 在gnuplot命令行里使用 help set xtics 就能得到完整的 set xtics 用法及实例

set output "outfile.ps"    ###  输出到文件，默认为屏幕

set size 0.6,0.6   ###　设置图像的长度为0.6，宽度为0.6

set title "name"  ###  给整幅图取个名字

###################################################################################################################

 
set xlabel "X-AXIS"           ###   用于x轴下面的标识
set ylabel "Y-AXIS"           ###    用于y轴左边或者上面的标识


set xrange [-2*pi:2*pi]
set yrange [-2:2]

unset tics  ### 关掉主刻度

set tics {front | back}  ###  作用于2D图，表示与画图内容重叠是刻度显示在上层还是下层

set xtics 10 ##  设置x轴主刻度（一般为数字）之间的间隔大小为10，类似尺子上的  1 厘米 到 2厘米 之间的大小为10毫米
set xtics 1 10   ###  设置x轴主刻度标注（一般为数字）之间的间隔大小为10，最左边起始第一个数字为1
set xtics -1,0.5,1   ### 设置x轴主刻度标注，这三个参数分别表示：最小主刻度、主刻度步长、最大主刻度。
set xtics rotate by -45   ####   x轴标注顺时针旋转45度
set xtics ("-2π" -2*pi, "" -1.5*pi 1, "-π" -pi, "" -0.5*pi 1, "0" 0, "" 0.5*pi 1, "π" pi, "" 1.5*pi 1, "2π" 2*pi)
### 人工设置对应主刻度显示的值，引号内为实际显示的符号，没引号的为刻度上实际的值，第三个参数表示刻度等级
### 这里 set xtics命令直接规定了每个刻度的位置和显示的字符。
### 每一个刻度对应三个参数：显示字符、刻度位置、刻度等级。刻度等级为 0 时表示主刻度，等级为 1 时表示分刻度。
### 对于主刻度（等级为 0 时），表示等级的参数也可以省略不写。
### 各个刻度的参数之间用逗号隔开。从上面的例子我们还看出，显示字符可以为空，也就是只标刻度，不显示字符。
####    y轴同上

### 小技巧
### 通过设置xrange比要显示数据稍大，然后设置xtics来更好的显示，防止数据紧靠两边显示
### 例如：显示数据范围在1到12
### set xrange [0.5:12.5]  扩大范围
### set xtics 1,1,12       显示主刻度的上下限



set mxtics 10  ###  设置x轴主刻度之间的分刻度数，m为minor，类似尺子上的  1 厘米 到 2厘米 之间的 最小刻度线，这些刻度上没数字
####   y轴同上


#############   我们之前讲过的所有有关坐标的参数，在第二坐标轴上均适用，只不过相应的名字起始字母改为 x2(右边) 或者 y2(顶上)
#############   例如 ylabel 改为 y2label。
#############   另外，plot 命令有一个新的参数 axis，用来控制使用哪个坐标轴，例如 axis x1y2 就表示使用第一横轴和第二纵轴。

set ytics nomirror  ### 取消y1轴在y2轴上的刻度镜像，需显示说明，否则默认镜像，y1与y2轴刻度一致
set y2tics          ### 表示y2轴刻度按自己要求定制，需显示说明 

plot "weather_beijing.dat" u 1:2 w lp pt 5 lc rgbcolor "#2B60DE" axis x1y1 t "降水量", \    ###  使用x1,y1轴
     "weather_beijing.dat" u 1:3 w lp pt 7 lc rgbcolor "#F62817" axis x1y2 t "气温"         ###  使用x1,y2轴
	 
	 ### 此处使用 rgbcolor 来定义颜色，这很大程度上增加了颜色选择范围，允许更好的显示效果

set grid   ## 图上加上栅格（grid），作用在主刻度上
set grid xtics y2tics  ### 会开启 x1 和 y2 的栅格
####  此时如果不能兼顾两组数据。最好的解决方案是，让两个纵轴有相同数目的分格，这样两套 grid 也就重合了，开启任何一个就可以了


            ###############   gnuplot 有第一（first）和第二（second）两套坐标系统  ###############
            ###############   除此之外，它还有 graph，screen 和 character 三套坐标系统  ###############
			
### first 和 second 定位都是根据坐标轴内对应的位置数据，以坐标轴包围区域为界
			
### graph 和 screen 都是归一化的坐标系统。graph 以坐标轴包围区域为界，左下角为 0,0，右上角为 1,1；screen 以整个图片区域为界，左下角为 0,0，右上角为 1,1。
### 简而言之，screen包括的区域比graph要大

### character 顾名思义，是以字符大小为单位长度的坐标系统，因此它的单位长度依赖于字体大小。它的原点位置和 screen 相同。

set label 1 "Hello first" at first 2,0.5  ### 在第一坐标轴(2,0.5)的位置开始横向显示Hello first
###  label后面的1是标识符，用来区别各个label，可以随便选个整数。默认可省略first

set label 2 "Hello second" at second 2,0.5  ### 在第二坐标轴(2,0.5)的位置开始横向显示Hello second

set label 3 "Hello graph" at graph 0.2,0.5  ###  在坐标轴内(0.2,0.5)的位置显示Hello graph，注意坐标轴位置取值区间

set label 4 "Hello screen" at screen 0.2,0.5  ### 在整幅图的(0.2,0.5)的位置显示Hello screen，注意坐标轴位置取值区间

set label 5 "Hello character" at character 10,5  ### 在字符大小(10,5)的位置显示Hello character

### 标签文字的默认对齐方式为居左，也就是指定的坐标位置在文字的左边。我们也可以在 label 命令里选择其他对齐方式。
### 除此之外，我们还可以在 label 命令里指定文字颜色，旋转文字，或者在指定坐标位置处加一个点。

set label 1 "Hello red left" at 2,0.4 left textcolor rgb "#FF0000"  ### 左对齐，红色

set label 2 "Hello green center" at 2,0.5 center textcolor rgb "#00FF00" ### 中间对齐，绿色

set label 3 "Hello blue right" at 2,0.6 right textcolor rgb "#0000FF"  ### 右对齐，蓝色

set label 4 "Hello rotate" at -2,0.4 rotate by 45  ### 标签逆时针旋转45度

set label 5 "Hello point" at -3,0.2 point pt 7 lc rgb "#FF9900"  ###  在坐标位置加一个点

set label "S" at graph 0.5,0.5 center font "Symbol,24"        ###  设置字体类型和大小


            ###############                        箭头                       ###############

set arrow

### 包括以下常用参数：
1.  from ... to ...
    ### 箭头的起点和终点坐标。如果把 to 换成 rto，第二个坐标就表示相对位置而不是绝对坐标。
	
2.  nohead, head, backhead, heads
	### 分别表示：没有箭头（其实就是线段），箭头在终点，箭头在起点，双向都有箭头。
	
3.  size <length>,<angle>,<backangle>
	### 箭头尺寸，默认长度单位为 first 坐标单位长度。
	### length指箭头等腰三角形的等腰边长度，angle指顶角的二分之一，backangle指顶角一半及底角对应的外角大小
	
4.  filled, empty, nofilled
	### 箭头的三种填充风格：
	### 1. 填充，2.中空， 3.无底边中空
	
	
set arrow 1 from 2,1.05 to 0.3,1 filled size 0.5,15,60 lw 2  ### 分别为坐标，填充，尺寸三部分加上连线粗细四部分

set label 1 at 0,1 point pt 7 ps 1.5 lc rgb "#F87217"  ###  在上面箭头指向的最大值处打点

set label 2 "最大值在（0, 1）" at 2.1,1.05  ### 箭头后面的文字说明



            ###############                   边框和坐标轴                      ###############


unset border  ##  把图四周的边框去掉

set zeroaxis  ##  画出正交于原点的坐标轴

####  在设定坐标刻度时加上 axis 参数，这样刻度会出现在坐标轴上面，而不是边框上

set zeroaxis lt -1 lw 2   ###  设置相交于原点的标准坐标轴，黑色类型，线条宽度为2，此时x轴和y轴上没有箭头

set xtics axis -2,1,2     ###  设置上诉标准坐标轴的主刻度为-2到2，刻度间隔为1

set ytics axis 0,1,1 offset 0,0.4  

### offset的作用就是把命令里提到的标签文字平移一段距离。在这里，offset 默认的坐标系统是 character。
### 它使得我们很多时候改变字体大小，而不必重新设置 offset。

set rmargin 4  ###  设置图像右边空白宽度为4，单位也是 character
### 相应的，上、左、下边的空白宽度，分别由 tmargin，lmargin，bmargin 参数控制


            ###############                   图例（key）                      ###############
			
unset key  ### 取消显示图例
			
set key box  ###  为图例加上边框

set key center at 10,0.7   ###  改变图例显示位置

set key reverse ###  把图例的 title 和图线示例调换位置

set key width 1  ###  调整图例边框宽度 width（或高度 height）

set key Left   ###  调整 title 文字对齐方式（Left 或者 Right，注意首字母大写，小写的设置位置）

set key spacing 1.2  ###  调整图例行间隔

set key samplen 2  ###  调整图线示例长度



            ###############                   对数坐标                      ###############

set logscale x  ###  x轴每一大格用对数表示，比如0.01, 0.1, 1

set logscale xy  ###  使用x轴y轴双对数坐标

set format y "%.0e"  ###  指定y轴数字显示格式，再加上表示格式的字符串，此处所有刻度都用科学计数法显示

1.  %f    小数格式
	
2.  %e    指数格式
	
3.  %g    根据长度自动选择 %f 或者 %e
	
4.  %t    指数格式的有效数字部分
	
5.  %T    指数格式的指数部分


#### 各特殊字符之前的数字可以用于表示有效数字的精度



            ###############                   图像尺寸                      ###############

set term pngcairo size 800,600   ### 输出默认大小是 640x480 像素的 png 图片

###  对于 eps 和 pdf 输出，默认的 size 单位是英寸，而不是像素。这是因为 eps 和 pdf 
###  均是矢量图片，像素值没什么意义


###  除了在设置 terminal 的时候可以指定 size 参数，gnuplot 里面还有一个单独的 set size 命令。区别在于：
###  1.  set terminal 的时候 size 参数指定的是整个图片的尺寸，包括标签、标题、四边空白等等，而 set size 
###      命令指定的仅仅是绘图区域的尺寸；
###  2.  set terminal 的时候 size 参数指定的是绝对尺寸，例如像素、英寸等，而 set size 命令指定的是
###      相对尺寸，也就是绘图区域相对于整个图片大小的比例。例如 set size 0.5,0.5 时，绘图区域仅占
###      整个图片大小的四分之一。


### 由于上述第二点，set size 命令更常用的形式是 set size ratio，这时只需给出绘图区域高和宽的比例

set size ratio 0.5   ###  画出高宽比为 1:2 的图像

set size square      ###  画出高宽比为 1 的图像（也就是正方形）



            ###############                   极坐标                      ###############
			
set polar   ### 设置使用极坐标绘图，此时自变量是t，t本身代表角度，默认单位是弧度，默认取值范围是[0:2*pi]

set angles degrees   ###  将t的默认单位从弧度改成角度

set trange [0:10*pi]    ###  改变t的取值范围

####  极坐标下的栅格和直角坐标不同，应该是按一定角度分隔的扇区，
####  所以 set grid 命令需要加上 polar 参数。默认扇区分隔角度是 30 度，
####  该角度可以作为 set grid polar 的参数进行调整

set grid polar pi/5   ###  每个栅格的角度弧度为pi/5

例子：  
set polar                             ###  使用极坐标
unset key                             ###  不使用图例
set samples 1000                      ###   采样率为1000
set xrange [-40:40]                   ###   虽然使用极坐标，整个图还是会用方框框起来，此处设置方框x轴的坐标
                                      ###   可以使用unset border去掉边框
set yrange [-40:40]                   ###   同上，作用于y轴
set size square                       ###   画出的图为方形，即高宽比为1:1
plot 5+25*cos(5*t/2) lw 2



            ###############                   参数方程                      ###############
			
set parametric    ###  设置参数方程环境，此时自变量为t，3D绘图中自变量为u/v

例子：
set parametric                   ###  使用参数方程
set xrange [-1.2:1.2]            ###  边框刻度范围
set yrange [-1.2:1.2]
set trange [0:2*pi]              ###  参数自变量取值范围
set samples 1000                 ###  取样点数
set size square                  ###  方形图
unset key                        ###   不使用图例
plot sin(3*t), sin(4*t) lw 2     ###   参数方程分别为x=f(t)=sin(3*t), y=g(t)=sin(4*t)



            ###############                   误差条（error bar）                   ###############
###   如果误差用标准差来表示，这时候只要增加一列误差项就行了，所以一共需要 3 列数据。
###   如果误差用最小值 和最大值来表示，这时候需要增加两列误差项，所以一共需要 4 列数据
			
			
plot "数据文件名" using <using参数> with <xerrorbars | yerrorbars | xyerrorbars>	

xerrorbars   
3 列：x   y   σ_x
4 列：x   y   x_min   x_max
yerrorbars
3 列：x   y   σ_y
4 列：x   y   y_min   y_max
xyerrorbars
4 列：x   y   σ_x   σ_y
6 列：x   y   x_min   x_max   y_min   y_max

例子：
set xrange [8:16]
set yrange [-5:105]
unset key
set xlabel "Laser Pulse Energy"
set ylabel "Bubble Formation Probability(%)"
plot "probability.dat" u 1:2:3:4 with xerrorbars

###  如果既要画 error bar，又要连线，可以把上述命令中的 errorbars 换为 errorlines：
plot "probability.dat" u 1:2:3:4 with xerrorlines
			
			
################################################################################################################



###  with 命令后面跟画图方式，默认为 points 方式，除此之外还有lines（连线），linespoints（连线的点）等30种方式


###  控制点和线画法风格的参数
###  要使用的时候在终端中输入test，根据右边的点，线，颜色类型对应选就行了
###  注意test列表里结果的类型和颜色不是绑定关系，选颜色的时候只是选颜色，选类型的时候只是选类型
###  不设置的时候，按需要从类型1的颜色类型开始依次使用

## linestyle   连线风格（包括linetype，linewidth等）

## linetype     连线种类

## linewidth   连线粗细

## linecolor   连线颜色

## pointtype   点的种类

## pointsize   点的大小

## 例如：
plot "datafile.dat" with linespoints linecolor 3 linewidth 2 pointtype 7 pointsize 2

set style line 1 lt 1 lw 3 lc rgb "#F62217"   第一个linestyle为实线，宽度为3，设置rgb颜色F62217
set style line 2 lt 1 pt 7 lc rgb "#D4A017"  实线，使用7号 symbol
set style line 3 lt 1 pt 8 lc rgb "#2B60DE"  实线，使用8号 symbol

##  lt设置线类型（实线虚线，1为实线，2为虚线）
##  lw设置宽度
##  pt设置点的表现方式，主要有
    1：加号
	2：十字
	3：*
	4：空心方块
	5：实心方块
	6：空心圆
	7：实心圆
	8：空心三角
	9：实心三角
	10：空心倒三角
	11：实心倒三角
	12：空心钻石
	13：实心钻石
	
	
    ################################     3D绘图     #########################################
	

set xyplane at <z坐标>    ###   用参数xyplane参数设置底部平面的绝对位置
set xyplane <相对比例>    ###   设定空白部分相对于 zrange 的比例

set isosamples 50         ###   调节网络大小，即采样点的多少

set hidden3d      ###    隐藏3D显示过程中部分重叠部分

set pm3d          ###    用色彩显示不同的z值  pm3d(palette-mapped 3d：色板映射)

###   色彩图除了画在曲面上，还可以画在底部或顶部
set pm3d at bst     ###   b,s,t 三个字母分别代表底部、曲面和顶部，
                    ###   at 之后可以是任一个字母，也可是三个字母的任意组合
					
set pm3d map        ###   从上往下看数据在取值范围内的全貌，此时是个从上到下的二维图

set cbrange [0:1]   ###  设置色板Color Box range对应的取值范围

set pm3d interpolate N,M   ###  变马赛克图为比较平滑的彩色图，使用插值的方法，
						   ###    M 和 N 分别代表 x 和 y 方向插值的数目。如果希望 gnuplot 自动优化选择，就让 M=N=0

set view 45,20          ###    设置3D视角，默认视角是60,30，分别表示绕x轴和z轴的旋转角度


#####      色板(palette)设置
自定义色板的方式有好多种，我们这里只谈一下比较方便常用的方式：用 rgbformulae 定义 RGB 色彩。
RGB 是电脑中最常用的色彩空间表示方式，而 rgbformulae 是一系列从数值到色阶的数学映射公式，共有 37 个。如果想知道 rgbformulae 到底包含哪些公式，可以使用 gnuplot 命令：show palette rgbformulae

由于 RGB 有三个颜色通道，所以每一个色板需要三个公式，分别表示 R（Red）、G（Green）、B（Blue）。色板的设置方法为：
set palette rgbformulae r,g,b
其中 r, g, b 分别表示 R, G, B 通道所用公式代码（0 到 36，允许用负值）。gnuplot 默认色板的公式代码为 7，5，15。

常用的组合:
7,5,15（pm3d 默认）
3,11,6（绿—红—紫）
23,28,3（绿—蓝—白）
21,22,23（黒—红—黄—白）
30,31,32（黑—蓝—紫—黄—白，可以用于黑白打印）
33,13,10（彩虹色）
22,13,-31（另一种彩虹色）
34,35,36（黒—红—黄—白）

set palette gray    ###   不用彩色，而只用黑白灰度

	
例子：
f(x,y) = sin(sqrt(x*x+y*y))/sqrt(x*x+y*y)
set xlabel "X"
set ylabel "Y"
set zlabel "Z"
unset key
splot f(x,y)

3D数据的存储：
(1)	纯文本格式，先让x轴数据不变，递增y轴数据，完事后空一行，x轴选取下一个数据，y轴同样在此递增，以此类推
(2) 二进制文件，省略

set border   设置边框

3D情况下总共有一个立方体的12个边框，由12bit的整数控制，例如：
0000 0000 1111: 只显示底部 4 个边框
0000 1111 0000: 只显示竖直 4 个边框
1111 0000 0000: 只显示顶部 4 个边框

set border 4095 lc rgb "#2554c7"  ###  12个边框的1111 1111 1111对应4095，显示12个边框，改边框颜色为蓝色


    ################################     image绘图     #########################################
	
 pm3d 绘图时我们说过，NxM 的数据只能画出 (N-1)x(M-1) 的图像。有没有这样一种方式，让我们能从 NxM 的数据画出 NxM 的图像呢？这次我们介绍一种新的画图风格：image

例子：
unset key
set xlabel "X"
set ylabel "Y"
set size square
plot "data3d.dat" with image


色块数目等于数据点数目，每一个色块中心位于相应数据点，而色块色彩对应的就是该数据点的值。这和 pm3d 不同，因为 pm3d 绘图时数据点位于色块顶点位置。另外，这里虽然用的是 plot 命令，但是图像反应了 3D 的信息，所以我们把它放在 3D 作图里介绍。
	
	################################     画柱状图     #########################################
	
	

set style data histogram   ###  告诉gnuplot所有数据都使用histogram风格(直方图/柱状图)
set style histogram  ####  设置histogram柱状图的作图参数
例如:
set style histogram clustered gap 1  
###  clustered表示对比数据并排放     
###  gap 1表示各簇数据之间空白的宽度等于数据柱宽度的 1 倍（同一簇的数据是紧挨着的）

set style histogram rowstacked
###  rowstacked表示把数据竖着垒起来，此模式没有gap参数，可以使用set boxwidth命令设置数据柱相对宽度
set boxwidth 0.8 relative

###  除了rowstacked模式，还有columnstacked模式，rowstacked逐行把数据叠加显示，而columnstacked逐列把数据叠加
###  另外还有errorbars模式，在clustered基础上增加误差条

	####  histogram和一般点线图的不同：点线图需要提供x,y两组数据，
	####  而histogram图只需一组数据，每个数据自动画在x轴的非负整数位置，此时x轴上标注与我们的要求不符
	####  改变标注的另一种方法是使用   xticlabels(列指引号)   的方法
	####  具体事例如下：
	
plot "datafile" using 2:xticlabels(1) title columnheader(2), \  
	 ### 画柱状图，用第2列做y轴，第1列内容作为x轴各个柱状数据对应的标注，使用第2列的第一行内容作为对应柱状数据的名字
	 '' using 3:xticalabels(1) title columnheader(3)
     ### 画柱状图，用第3列做y轴，同样也是第1列内容做标注，使用第3列第一行的内容作对应柱状数据的名字
	 
	 用法解析：
	 xticlabels(n)表示使用第n列的内容作为每行数据对应的x轴标注
	 columnheader(3)表示使用第n列的第一行内容作为这列数据表示的内容的title



set style fill ####   填充风格命令




	
	####################################   画实验数据  #######################################


plot "output.dat" u 1:2 t "Analytical" w lines, \     ### 用连线画图，使用第一列作为x，第二列作为y，图例标题为Analytical
	 "output.dat" u 1:3 t "second" w lines, \         ###   同上，用同一个文件的第一列为x，第三列为y
	 "output.dat" u 1:4 t "third" w lines             ###   同上，用同一个文件的第一列为x，第四列为y
	 
	 

f(x) = -0.01687*x + 1.3512	 
	 

plot f(x) notitle with lines linestyle 1, \                    ### 画出前面的那个函数，线无标题描述，使用线条1风格
	 "plotexp.dat" index 0:0 using 1:2:3 title "data1" with yerrorbars linestyle 2, \  
               ##  索引第一个文件块，使用第一列作为x轴，第二列作为y轴，第三列作为偏差数据，使用线条2风格
			   ##  此处的title设置的是图例的title，而不是整幅图的title
	 "" ind 1:1 u 1:2:3 ti "data2" w yerr linestyle 3, \
	           ##  索引第二个文件块，，使用线条3风格，其他同上
	 "" ind 2:2 u 1:2:($2*$3/100.0) ti "data3" w yerr
	           ##  索引第三个文件块，第三列用偏差占y值的百分比给出，所以换算成实际偏差值
			   
plot "precipitation.dat" u 1:(2/25.4) w lp pt 5 title "北京", \  ### 用第二列的数据除以25.4，得到的一个实数作为y轴数据
     "precipitation.dat" u 1:(3/25.4) w lp pt 7 title "上海"     ### 用第三列数据，其他同上
			   
########   在对特定列的数据进行运算操作时，我们需要在列号之前加上 $ 符号，这样表示该数据的值。
			   
			   
	################################     画函数（普通函数）  #########################
	
plot sin(5*x)   ###  画一个简单的正弦函数

set samples 500  ###  设置函数取样数目为500，默认取样数目为100，对于快速振荡函数取样率偏低，可能导致函数看起来不圆滑
			   
	
	################################     画函数（最小二乘拟合）  #########################

a=0.25
b=0.02
c=0.05
d=0.1
			   
f(x) = c/((x-a)**2+b) + d/sqrt(x)       ### 关于x，y的函数，前面分母里面是(x-a)的平方加b

###  输入以上函数的结果成数据文件
set term table   ### 测试不可用

gnuplot命令：
gnuplot> fit f(x) "exp.dat" using 1:2:3 via a,b,c,d
###   此命令会找出合适的a,b,c,d值

   
	################################     画CDF图  #########################
	
plot 'cdf.dat' using 3:(1./100510.) t "CDF"  lw 5 lt -1 smooth cumulative
###  此命令取数据列中第三列数据画pdf图，其中100510是总行数  线条宽度为5







	 