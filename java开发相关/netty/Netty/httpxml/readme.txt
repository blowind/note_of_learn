����Ŀ��Ҫʹ��JiBX����pojo��xml�İ󶨹�ϵ�ļ�����Ҫ��bind.xml��pojo.xsd��Ȼ���ڴ˻�����compile���а󶨹�ϵ��class�ļ�������������£�

1��������Ҫbuild��Ŀ����POJO���class�ļ�

2������Ŀ�� target\classes Ŀ¼�������������
java -cp bin;D:\jibx\lib\jibx-tools.jar org.jibx.binding.generator.BindGen -b bind.xml com.zxf.nio.httpxml.pojo.Order

3��Ȼ����ͬһ��Ŀ¼�����У����������ڸ��ݰ��ļ�bind.xml��POJO�����ӳ���ϵ�͹���̬�޸�POJO�࣬�õ����POJO��.class�ļ�(���޸�POJO.java�ļ�)����ִ�б������ᱨUnable to access binding information for class����
java -cp bin;D:\jibx\lib\jibx-bind.jar org.jibx.binding.Compile -v bind.xml

4���鿴������bind��schema�ļ���Ϣ�������ѡ��Ҳ����target\classesĿ¼��ִ�У���
java -cp bin;D:\jibx\lib\jibx-run.jar org.jibx.runtime.PrintInfo -c com.zxf.nio.httpxml.pojo.Order



ע�⣺�����Ŀ����rebuild�ˣ�ʹ��JiBX compile������class�ļ��ᱻ������Ҫ���������������������