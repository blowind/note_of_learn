windows下lisp环境配置：
http://www.mohiji.org/2011/01/31/modern-common-lisp-on-windows/

(load "C:\\path\\to\\quicklisp.lisp")      
(quicklisp-quickstart:install :path "C:\\quicklisp\\")  ;;;默认安装到 $HOME/quicklisp 用:path修改安装目录 只需安装一次
(load "~/quicklisp/setup.lisp")
(ql:add-to-init-file)         ;;;  将quicklisp加入默认加载项

To load a system, use: (ql:quickload "system-name")     ;;; 添加 :verbose t 标签可以显示详细的加载信息
例如：   (ql:quickload "ieee-floats")  
To find systems, use: (ql:system-apropos "term")
例如：   (ql:system-apropos "xml")
To load Quicklisp every time you start Lisp, use: (ql:add-to-init-file)



原语：
() 或者 nil  ;;; 表空，也可以表示false

t  表示true

关键字：
(quote x)    ;;; 括起来 简写为  'x


关键字：
(atom x)     ;;; x是否是原子或者空列表  是返回t 否返回()


关键字：
(eq x y)     ;;; x,y是否相等  返回 t 或者 ()


关键字：
(car x)      ;;; 期待x是个列表，返回其第一个元素


关键字：
(cdr x)      ;;; 期待x是个列表，返回除第一个元素之外的所有元素
(car (cdr (cdr '(a b c d))))       ;;; 取得第三个元素
(third '(a b c d))                 ;;; 同上


关键字：
(cons x y)   ;;; 期待y是个列表，返回一个包含x,y的列表


关键字：
(cond (p1 e1) ... (pn en))   ;;;  一次评估p1到pn，若某个pi返回t, 返回ei
(cond (x y) ('t z))   ;;;  等价于  if x then y else z


关键字：
((lambda (p1 ... pn) e) a1 ... an)   ;;; 函数调用，先计算a1，代入e，对应p1是返回结果

(label f (lambda (p1 ... pn) e))     ;;; 递归定义，e中可以出现f
(label subst (lambda (x y z)      ;;; 递归实现 subst(x y z)，其中z是list，替换z中所有为z
			   ( cond ((atom z)     ;;; 如果z已经是个原子
			           (cond ((eq z y) x))    ;;; z等于y 则替换
							 ('t z)))         ;;; 不等的话保持z不变
					  ('t (cons (subst x y (car z))   ;;;  如果z不是原子，切割z的头并递归
								(subst x y (cdr z))))))   ;;;z不是原子，切割剩下的部分递归

等价于
(defun subst (x y z)
	( cond ((atom z)
		   (cond ((eq z y) x)
				 ('t z)))
		  ('t (cons (subst(x y (car z))
			        (subst(x y (cdr z))))))


关键字：					
(cadr e)   ;;; 等价于  (car (cdr e))  取e中第二个元素，连续的ad形式最多四个
(third '(a b c))  ;; 得到第三个元素c  等价于 (caddr '(a b c))


关键字：
(list e1 ... en)  ;;;  等价于 (cons e1 ...(cons en '()) ...)
(list '(+ 2 1) (+ 2 1))   ;; 得到((+ 2 1) 3)  list是函数，会计算其每个参数，除非用'标记


关键字：
(listp '(a b c))  ;;; 参数是list时，listp返回t
(typep 27 'integer)   ;;; 判断给定参数的类型


关键字：
(null nil)        ;;; 参数是nil或者()时，null返回t


关键字：
(not nil)         ;;; 同上


关键字：
(if (listp '(a b c)) (+ 1 2) (+ 5 6))   ;;; LISP的条件表达式，得到3
(if (listp '(a b c))                    ;;; 同上，正常写法
	(+ 1 2)
	(+ 5 6))
(if (listp 27) (+ 2 3))   ;;;  只有if语句，没有else语句的写法

(and t (+ 1 2))    
;;; 除了最后一个参数，其他都是形参，最后参数是返回值，上述表达式返回3，但是所有形参都必须是非nil
;;; 形参计算时有短路逻辑，所以可能返回的不是最后一个表达式

(format t "~A plus ~A equals ~A.~%" 2 3 (+ 2 3))      
;;;  输出 format是输出函数名 t表示默认输出位置  ~A表示要填充的地方  ~%表示换行
(read)    ;;;  默认输入

(let ((x 1) (y 1)) (+ x y))        ;;;  let 引入局部变量
(defun ask-number ()               ;;;  新函数无参数，用于读入数字
	(format t "Please enter a number. ")
	(let ((val (read)))
		(if (numberp val)
			val
			(ask-number))))

(defparameter *glob* 99)       ;;;  定义全局变量，变量名前后加*是国际惯例
(defconstant limit (+ *glob* 1))   ;;;  定义全局常量
(boundp '*glob*)               ;;;  查看*glob* 是否为全局变量


(setf *glob* 98)               ;;; 赋值运算 给变量*glob*赋值
(let ((n 10)) (setf n 2) n)    ;;;  let返回表达式最后一个结果，得到2
(setf x (list 'a 'b 'c))       ;;;  x不存在时，自动创建全局变量x
(setf (car x) n)               ;;;  替换x中第一个元素，得到(n b c)
(setf a b c d e f)  ;;;  等价于 (setf a b) (setf c d) (setf e f)


(remove 'a lst)         ;;;  拷贝一个lst副本，移除其中的所有a并返回这个副本

(defun show-squares (start end)         ;;;  迭代的实现
	(do ((i start (+ i 1)))             ;;; do的第一个参数是(varialbe initial update)形式
		((> i end) 'done)               
	;;; 第二个参数首先包含结束判断expression，剩下的部分依次计算，最后一个为整个迭代返回值
		(format t "~A ~A~%" i (* i i))))

(defun show-squares (i end)            ;;;  上述迭代的递归实现
	(if (> i end)
		'done
		(progn                ;;; progn 接收任何数目表达式，依次计算，返回最后一个的值
			(format t "~A ~A~%" i (* i i))
			(show-squares (+ i 1) end))))
			
			
(defun our-length (lst)           ;;; 计算lst长度
	(let ((len 0))
		(dolist (obj lst)         ;;;  dolist迭代lst中的元素，每次把lst中的值放入obj
		;;;  dolist (variable expression) expression
			(setf len (+ len 1)))
		len))
		
(defun our-length (lst)           ;;;  上述迭代的递归实现
	(if (null lst)
		0
		(+ 1 (our-length (cdr lst)))))

(apply #'+ '(1 2 3))     ;;; apply 第一个参数是函数名，把函数作用于后面的参数
(apply #'+ 1 2 '(3 4 5))  ;;;  得到15
(funcall #'+ 1 2 3)      ;;; 同上，但是参数不用当做list组装起来		


(eql (cons 'a nil) (cons 'a nil))   ;;; eql仅在参数是同一个对象是返回true，所以此处得到nil
(equal (cons 'a nil) (cons 'a nil)) ;;; equal在值相同时返回T，所以此处是T

(setf x '(a b c))  ;;; 得到(a b c)
(setf y x)      ;;;  得到一个指针，y,x指向同样的内容
(setf x '(a b c) y (copy-list x))  ;;; 拷贝(a b c)，y指向新的拷贝


(cons 'a 'b)  ;;; cons只接收两个参数，合并成一个list
(append '(a b) '(c d) 'e)  ;;; append接收多个参数，把他们合并成一个list


(nth 0 '(a b c))    ;;; 获得list中第0个元素，得到a
(nthcdr 2 '(a b c d e))   ;;;  获得第2个cdr，得到(c d e)
(last '(a b (c d)))    ;;;返回外层list中的最后一个元素((c d))，注意到这个list当做一个元素


(mapcar #'(lambda (x) (+ x 10)) '(1 2 3))    ;;;  得到(11 12 13)
;; (mapcar function    one-or-more-list)形式，每次对各个list中元素一起调用function
(mapcar #'list '(a b c) '(1 2 3 4))       ;;;  得到((a 1) (b 2) (c 3))
(maplist #'(lambda (x) x) '(a b c))   ;;;  调用形式同上，不过作用于cdr部分
;;;   得到((a b c) (b c) (c))


(substitute 'y 'x '(a b c d x x x)) ;;;  (substitute new old list) 得到(a b c d y y y)
;;;   只能替换list这一层的元素
(subst 'y 'x '(and (integerp x) (zerop (mod x 2))))   ;;;  深层替换
;;;   得到(and (integerp y ) (zerop (mod y 2)))


(member 'b '(a b c))  ;;;  返回匹配开始的list，默认比较函数是eql  此处得到(b c)
(member '(a) '((a) (z)) :test #'equal)   ;;; 同上，但是替换比较函数为equal
;;;;  :symbal 是关键字参数    关键字参数必须放在最后，多个关键字参数之间的位置没影响
(member 'a '((a b) (c d)) :key #'car)     ;;;   得到((a b) (c d))
;;;;   :key 指定在比较之前作用于参数的函数
(member 2 '((1) (2)) :key #'car :test #'equal)  ;;;   得到((2))



新函数的定义：
#'+      ;;;; #'作为定义新函数的简写

lambda 表达式是为了区分list和函数，因为他们在LISP中是一样的存储结构
(lambda (parameters) (zero or more expressions))
(lambda (x y) (+ x y))          ;;;  定义了一个 lambda表达式 或者说 函数
((lambda (x) (+ x 100)) 1)      ;;;  整个lambda表达式本身可以当做函数名使用
(funcall #'(lambda (x) (+ x 100)) 1)   ;; 同上，另一种调用法

(defun our-third (x) (car (cdr (cdr x))) )
;;;   defun函数名  自定义函数名  形参列表  函数实现  构成整个函数的定义

(defun our-member (obj lst)       ;;;  递归判断一个元素是否是一个list的成员，返回值是以obj开头的剩余list
	(if (null lst)
		nil
		(if (eql (car lst) obj)
			lst
			(our-member obj (cdr lst)))))

(defun null. (x)               ;;;  判断x是否为空
	(eq x '()))
(null. 'a)      ;;;  得到()
(null. '())     ;;;  得到t

(defun and. (x y)             ;;;   x,y都非空时返回t
	(cond (x (cond (y 't) ('t '())))
		  ('t '())))
		  
(and. (atom 'a) (eq 'a 'a))   ;;;  得到t
(and. (atom 'a) (eq 'a 'b))   ;;;  得到()


(defun append. (x y)          ;;;   拼接两个list x 和 y
	(cond ((null. x) y)
	      ('t (cons (car x) (append. (cdr x) y)))))
		  
