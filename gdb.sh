
gcc -g -Wall -o insert_sort ins.c             ##  -g ѡ���ʾ�������ű�



��homeĿ¼�� .gdbinit �ļ��м��������䣬���� break g   (��g�����ϼӶϵ�)����ÿ������ʱ���Զ�ִ��


$ gdb insert_sort  ����insert_sort.c�ļ�
$ gdb -tui insert_sort   ## ��-tui����ģʽ��������Դ�룬�����ǵ��Դ��� Ҳ����gdb�ڲ��� ctrl+X+A ��

(gdb) run 12 5           ##  ����gdbģʽ��ʹ��runִ�г��򣬴������12��5

(gdb) help breakpoints   ## �鿴�����ĵ�



(gdb) break 16        ##  ����16��Ϊ�ϵ� ���� b 16
(gdb) tbreak 16       ##  ����16��Ϊ��ʱ�ϵ㣬�ϵ�һ�ξ���ʧ
(gdb) info b          ##  �鿴��ǰ���жϵ���Ϣ
(gdb)


(gdb) next            ##  ����ִ��
(gdb) step            ##  ����ִ�У�����Ǻ�����ȫ��ִ���겢����
(gdb) continue        ##  ִ�е��¸��ϵ�  ���Խ��ctrl+C������ж�/�ָ�ִ��

(gdb) print j         ##  �鿴����j��ֵ
(gdb) print y         ##  ��ӡ�����ֵ  �� $4 = {12, 0, 0, 0, 0, 0, 0}



(gdb) watch z         ##  ������z��ֵ���ʱ��������ִͣ��
(gdb) watch (z > 28)         ##  ������z��ֵ����28ʱ��������ִͣ��
(gdb) frame 1                ##  ���ڴ溯���ĵ���ջ��0������һ����1��������2����������
(gdb) backtrace              ##  �鿴��������ջ


(gdb)b 20                           ##  ���öϵ� ���� b insert  (����insert�Ǻ�����)
(gdb)condition 1 num_y==1           ##  ���öϵ���ж���������һ������1��ʾ�ϵ�1���ڶ���������ʾ������num_y==1

(gdb)b 20 if num_y==1               ##  ������������д����