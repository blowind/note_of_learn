:: ��ʾ�����û����ҳ��� 
:: �������룺mode con cp select=936�����ʾ��ʾ�������ġ��������mode con cp select=437�����ʾ��ʾMS-DOS ����Ӣ���������ʾ������?��
:: ��cmd�����в�����ʾ����ʱ�����������chcp 936  ������ʾ������
chcp 437


:: �鿴��ǰʹ�õĶ˿�
netstat -an

:: �鿴�����˶˿�
netstat -ano | findstr "8080"

:: ���ݽ��̺Ų鿴������ϸ��Ϣ
tasklist | findstr "11234"

:: �ر�pidΪ4�Ľ���
tskill 4

:: �����������
ipconfig /flushdns

:: �鿴����ip�������Ϣ
ipconfig /all

:: �鿴����������������������������Ľ�����������ַ
nslookup

:: Apache�޷�������ԭ��80�˿ڱ�PIDΪ4��system����ռ�ã��ý��̻�����httpϵͳ���񣬽���ռ��80�˿�
net stop http 

:: ��ȡSID����ʾ��ǰ���û�����SIDֵ
whoami /user

:: ɾ��ע�����Ϣ������<SID>��ǰ�������ȡ
reg delete "HKEY_USERS\<SID>\Software\Scooter Software\Beyond Compare 4" /v CacheId /f


:: �������д�ӡ�ű�
REM ����
REM �����򿪡�����ƻ����򡱣�����������ƻ�����⣬���м������б����һ�ѡ���½�����
REM �����ǩ���������ƣ�
REM ������ѡ��е���½���������ʱ�䣬
REM ����ѡ��е���½�ָ��Ҫ�����ĳ���(�˴�Ϊ����bat�ű�)��
REM ����ѡ��п����Ҫ��Ҫ��ѡ��ֻ���ڼ����ʹ�ý������Դʱ������������
REM �ű�����
:one
echo %date:~0,10% %time:~0,-3% ��鵱ǰ����...
ping 127.0.0.1 -n 10>nul
goto one
