1、在https://github.com/protocolbuffers/protobuf/releases网站下载protoc-3.7.1-win64.zip包，
   用于根据.proto文件生成序列化类的.java文件

2、使用命令
protoc.exe --java_out=. SubscribeReq.proto
protoc.exe --java_out=. SubscribeResp.proto
分别生成common里面的
SubscribeReqProto.java和
SubscribeRespProto.java文件

注意：可以修改.proto里面java_package内容分别生成client和server路径下各自的java文件，
      也可以生成公共的class文件打包成jar文件分别由client和server各自引入