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

:: �����������
ipconfig /flushdns

:: �ر�pidΪ4�Ľ���
tskill 4

:: Apache�޷�������ԭ��80�˿ڱ�PIDΪ4��system����ռ�ã��ý��̻�����httpϵͳ���񣬽���ռ��80�˿�
net stop http 