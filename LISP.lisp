windows��lisp�������ã�
http://www.mohiji.org/2011/01/31/modern-common-lisp-on-windows/

(load "C:\\path\\to\\quicklisp.lisp")      
(quicklisp-quickstart:install :path "C:\\quicklisp\\")  ;;;Ĭ�ϰ�װ�� $HOME/quicklisp ��:path�޸İ�װĿ¼ ֻ�谲װһ��
(load "~/quicklisp/setup.lisp")
(ql:add-to-init-file)         ;;;  ��quicklisp����Ĭ�ϼ�����

To load a system, use: (ql:quickload "system-name")     ;;; ��� :verbose t ��ǩ������ʾ��ϸ�ļ�����Ϣ
���磺   (ql:quickload "ieee-floats")  
To find systems, use: (ql:system-apropos "term")
���磺   (ql:system-apropos "xml")
To load Quicklisp every time you start Lisp, use: (ql:add-to-init-file)



ԭ�
() ���� nil  ;;; ��գ�Ҳ���Ա�ʾfalse

t  ��ʾtrue

�ؼ��֣�
(quote x)    ;;; ������ ��дΪ  'x


�ؼ��֣�
(atom x)     ;;; x�Ƿ���ԭ�ӻ��߿��б�  �Ƿ���t �񷵻�()


�ؼ��֣�
(eq x y)     ;;; x,y�Ƿ����  ���� t ���� ()


�ؼ��֣�
(car x)      ;;; �ڴ�x�Ǹ��б��������һ��Ԫ��


�ؼ��֣�
(cdr x)      ;;; �ڴ�x�Ǹ��б����س���һ��Ԫ��֮�������Ԫ��
(car (cdr (cdr '(a b c d))))       ;;; ȡ�õ�����Ԫ��
(third '(a b c d))                 ;;; ͬ��


�ؼ��֣�
(cons x y)   ;;; �ڴ�y�Ǹ��б�����һ������x,y���б�


�ؼ��֣�
(cond (p1 e1) ... (pn en))   ;;;  һ������p1��pn����ĳ��pi����t, ����ei
(cond (x y) ('t z))   ;;;  �ȼ���  if x then y else z


�ؼ��֣�
((lambda (p1 ... pn) e) a1 ... an)   ;;; �������ã��ȼ���a1������e����Ӧp1�Ƿ��ؽ��

(label f (lambda (p1 ... pn) e))     ;;; �ݹ鶨�壬e�п��Գ���f
(label subst (lambda (x y z)      ;;; �ݹ�ʵ�� subst(x y z)������z��list���滻z������Ϊz
			   ( cond ((atom z)     ;;; ���z�Ѿ��Ǹ�ԭ��
			           (cond ((eq z y) x))    ;;; z����y ���滻
							 ('t z)))         ;;; ���ȵĻ�����z����
					  ('t (cons (subst x y (car z))   ;;;  ���z����ԭ�ӣ��и�z��ͷ���ݹ�
								(subst x y (cdr z))))))   ;;;z����ԭ�ӣ��и�ʣ�µĲ��ֵݹ�

�ȼ���
(defun subst (x y z)
	( cond ((atom z)
		   (cond ((eq z y) x)
				 ('t z)))
		  ('t (cons (subst(x y (car z))
			        (subst(x y (cdr z))))))


�ؼ��֣�					
(cadr e)   ;;; �ȼ���  (car (cdr e))  ȡe�еڶ���Ԫ�أ�������ad��ʽ����ĸ�
(third '(a b c))  ;; �õ�������Ԫ��c  �ȼ��� (caddr '(a b c))


�ؼ��֣�
(list e1 ... en)  ;;;  �ȼ��� (cons e1 ...(cons en '()) ...)
(list '(+ 2 1) (+ 2 1))   ;; �õ�((+ 2 1) 3)  list�Ǻ������������ÿ��������������'���


�ؼ��֣�
(listp '(a b c))  ;;; ������listʱ��listp����t
(typep 27 'integer)   ;;; �жϸ�������������


�ؼ��֣�
(null nil)        ;;; ������nil����()ʱ��null����t


�ؼ��֣�
(not nil)         ;;; ͬ��


�ؼ��֣�
(if (listp '(a b c)) (+ 1 2) (+ 5 6))   ;;; LISP���������ʽ���õ�3
(if (listp '(a b c))                    ;;; ͬ�ϣ�����д��
	(+ 1 2)
	(+ 5 6))
(if (listp 27) (+ 2 3))   ;;;  ֻ��if��䣬û��else����д��

(and t (+ 1 2))    
;;; �������һ�����������������βΣ��������Ƿ���ֵ���������ʽ����3�����������βζ������Ƿ�nil
;;; �βμ���ʱ�ж�·�߼������Կ��ܷ��صĲ������һ�����ʽ

(format t "~A plus ~A equals ~A.~%" 2 3 (+ 2 3))      
;;;  ��� format����������� t��ʾĬ�����λ��  ~A��ʾҪ���ĵط�  ~%��ʾ����
(read)    ;;;  Ĭ������

(let ((x 1) (y 1)) (+ x y))        ;;;  let ����ֲ�����
(defun ask-number ()               ;;;  �º����޲��������ڶ�������
	(format t "Please enter a number. ")
	(let ((val (read)))
		(if (numberp val)
			val
			(ask-number))))

(defparameter *glob* 99)       ;;;  ����ȫ�ֱ�����������ǰ���*�ǹ��ʹ���
(defconstant limit (+ *glob* 1))   ;;;  ����ȫ�ֳ���
(boundp '*glob*)               ;;;  �鿴*glob* �Ƿ�Ϊȫ�ֱ���


(setf *glob* 98)               ;;; ��ֵ���� ������*glob*��ֵ
(let ((n 10)) (setf n 2) n)    ;;;  let���ر��ʽ���һ��������õ�2
(setf x (list 'a 'b 'c))       ;;;  x������ʱ���Զ�����ȫ�ֱ���x
(setf (car x) n)               ;;;  �滻x�е�һ��Ԫ�أ��õ�(n b c)
(setf a b c d e f)  ;;;  �ȼ��� (setf a b) (setf c d) (setf e f)


(remove 'a lst)         ;;;  ����һ��lst�������Ƴ����е�����a�������������

(defun show-squares (start end)         ;;;  ������ʵ��
	(do ((i start (+ i 1)))             ;;; do�ĵ�һ��������(varialbe initial update)��ʽ
		((> i end) 'done)               
	;;; �ڶ����������Ȱ��������ж�expression��ʣ�µĲ������μ��㣬���һ��Ϊ������������ֵ
		(format t "~A ~A~%" i (* i i))))

(defun show-squares (i end)            ;;;  ���������ĵݹ�ʵ��
	(if (> i end)
		'done
		(progn                ;;; progn �����κ���Ŀ���ʽ�����μ��㣬�������һ����ֵ
			(format t "~A ~A~%" i (* i i))
			(show-squares (+ i 1) end))))
			
			
(defun our-length (lst)           ;;; ����lst����
	(let ((len 0))
		(dolist (obj lst)         ;;;  dolist����lst�е�Ԫ�أ�ÿ�ΰ�lst�е�ֵ����obj
		;;;  dolist (variable expression) expression
			(setf len (+ len 1)))
		len))
		
(defun our-length (lst)           ;;;  ���������ĵݹ�ʵ��
	(if (null lst)
		0
		(+ 1 (our-length (cdr lst)))))

(apply #'+ '(1 2 3))     ;;; apply ��һ�������Ǻ��������Ѻ��������ں���Ĳ���
(apply #'+ 1 2 '(3 4 5))  ;;;  �õ�15
(funcall #'+ 1 2 3)      ;;; ͬ�ϣ����ǲ������õ���list��װ����		


(eql (cons 'a nil) (cons 'a nil))   ;;; eql���ڲ�����ͬһ�������Ƿ���true�����Դ˴��õ�nil
(equal (cons 'a nil) (cons 'a nil)) ;;; equal��ֵ��ͬʱ����T�����Դ˴���T

(setf x '(a b c))  ;;; �õ�(a b c)
(setf y x)      ;;;  �õ�һ��ָ�룬y,xָ��ͬ��������
(setf x '(a b c) y (copy-list x))  ;;; ����(a b c)��yָ���µĿ���


(cons 'a 'b)  ;;; consֻ���������������ϲ���һ��list
(append '(a b) '(c d) 'e)  ;;; append���ն�������������Ǻϲ���һ��list


(nth 0 '(a b c))    ;;; ���list�е�0��Ԫ�أ��õ�a
(nthcdr 2 '(a b c d e))   ;;;  ��õ�2��cdr���õ�(c d e)
(last '(a b (c d)))    ;;;�������list�е����һ��Ԫ��((c d))��ע�⵽���list����һ��Ԫ��


(mapcar #'(lambda (x) (+ x 10)) '(1 2 3))    ;;;  �õ�(11 12 13)
;; (mapcar function    one-or-more-list)��ʽ��ÿ�ζԸ���list��Ԫ��һ�����function
(mapcar #'list '(a b c) '(1 2 3 4))       ;;;  �õ�((a 1) (b 2) (c 3))
(maplist #'(lambda (x) x) '(a b c))   ;;;  ������ʽͬ�ϣ�����������cdr����
;;;   �õ�((a b c) (b c) (c))


(substitute 'y 'x '(a b c d x x x)) ;;;  (substitute new old list) �õ�(a b c d y y y)
;;;   ֻ���滻list��һ���Ԫ��
(subst 'y 'x '(and (integerp x) (zerop (mod x 2))))   ;;;  ����滻
;;;   �õ�(and (integerp y ) (zerop (mod y 2)))


(member 'b '(a b c))  ;;;  ����ƥ�俪ʼ��list��Ĭ�ϱȽϺ�����eql  �˴��õ�(b c)
(member '(a) '((a) (z)) :test #'equal)   ;;; ͬ�ϣ������滻�ȽϺ���Ϊequal
;;;;  :symbal �ǹؼ��ֲ���    �ؼ��ֲ������������󣬶���ؼ��ֲ���֮���λ��ûӰ��
(member 'a '((a b) (c d)) :key #'car)     ;;;   �õ�((a b) (c d))
;;;;   :key ָ���ڱȽ�֮ǰ�����ڲ����ĺ���
(member 2 '((1) (2)) :key #'car :test #'equal)  ;;;   �õ�((2))



�º����Ķ��壺
#'+      ;;;; #'��Ϊ�����º����ļ�д

lambda ���ʽ��Ϊ������list�ͺ�������Ϊ������LISP����һ���Ĵ洢�ṹ
(lambda (parameters) (zero or more expressions))
(lambda (x y) (+ x y))          ;;;  ������һ�� lambda���ʽ ����˵ ����
((lambda (x) (+ x 100)) 1)      ;;;  ����lambda���ʽ������Ե���������ʹ��
(funcall #'(lambda (x) (+ x 100)) 1)   ;; ͬ�ϣ���һ�ֵ��÷�

(defun our-third (x) (car (cdr (cdr x))) )
;;;   defun������  �Զ��庯����  �β��б�  ����ʵ��  �������������Ķ���

(defun our-member (obj lst)       ;;;  �ݹ��ж�һ��Ԫ���Ƿ���һ��list�ĳ�Ա������ֵ����obj��ͷ��ʣ��list
	(if (null lst)
		nil
		(if (eql (car lst) obj)
			lst
			(our-member obj (cdr lst)))))

(defun null. (x)               ;;;  �ж�x�Ƿ�Ϊ��
	(eq x '()))
(null. 'a)      ;;;  �õ�()
(null. '())     ;;;  �õ�t

(defun and. (x y)             ;;;   x,y���ǿ�ʱ����t
	(cond (x (cond (y 't) ('t '())))
		  ('t '())))
		  
(and. (atom 'a) (eq 'a 'a))   ;;;  �õ�t
(and. (atom 'a) (eq 'a 'b))   ;;;  �õ�()


(defun append. (x y)          ;;;   ƴ������list x �� y
	(cond ((null. x) y)
	      ('t (cons (car x) (append. (cdr x) y)))))
		  
