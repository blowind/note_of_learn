1����https://github.com/protocolbuffers/protobuf/releases��վ����protoc-3.7.1-win64.zip����
   ���ڸ���.proto�ļ��������л����.java�ļ�

2��ʹ������
protoc.exe --java_out=. SubscribeReq.proto
protoc.exe --java_out=. SubscribeResp.proto
�ֱ�����common�����
SubscribeReqProto.java��
SubscribeRespProto.java�ļ�

ע�⣺�����޸�.proto����java_package���ݷֱ�����client��server·���¸��Ե�java�ļ���
      Ҳ�������ɹ�����class�ļ������jar�ļ��ֱ���client��server��������