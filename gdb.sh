
gcc -g -Wall -o insert_sort ins.c             ##  -g 选项表示保留符号表



在home目录下 .gdbinit 文件中加入调试语句，诸如 break g   (在g函数上加断点)，则每次启动时会自动执行


$ gdb insert_sort  调试insert_sort.c文件
$ gdb -tui insert_sort   ## 打开-tui分屏模式，上屏是源码，下屏是调试窗口 也可以gdb内部用 ctrl+X+A 打开

(gdb) run 12 5           ##  进入gdb模式后，使用run执行程序，传入参数12和5

(gdb) help breakpoints   ## 查看帮助文档



(gdb) break 16        ##  设置16行为断点 或者 b 16
(gdb) tbreak 16       ##  设置16行为临时断点，断点一次就消失
(gdb) info b          ##  查看当前所有断点信息
(gdb)


(gdb) next            ##  单步执行
(gdb) step            ##  单步执行，如果是函数则全部执行完并返回
(gdb) continue        ##  执行到下个断点  可以结合ctrl+C来多次中断/恢复执行

(gdb) print j         ##  查看变量j的值
(gdb) print y         ##  打印数组的值  如 $4 = {12, 0, 0, 0, 0, 0, 0}



(gdb) watch z         ##  当变量z的值变更时，程序暂停执行
(gdb) watch (z > 28)         ##  当变量z的值大于28时，程序暂停执行
(gdb) frame 1                ##  最内存函数的调用栈是0，其上一层是1，再上是2，依次类推
(gdb) backtrace              ##  查看函数调用栈


(gdb)b 20                           ##  设置断点 或者 b insert  (其中insert是函数名)
(gdb)condition 1 num_y==1           ##  设置断点的中断条件，第一个参数1表示断点1，第二个参数表示条件是num_y==1

(gdb)b 20 if num_y==1               ##  结合上面两句的写法，