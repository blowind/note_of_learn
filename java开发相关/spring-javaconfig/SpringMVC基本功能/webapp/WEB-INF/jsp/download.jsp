<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>通过JSON转换并下载二进制文件</title>
</head>
<body>

<button onclick="javascript:getPicture()">获得json返回的图片</button>
<button onclick="javascript:getTxtFile()">获得json返回的txt文档</button>
<button onclick="javascript:getTextAndShow()">字符串解压缩并BASE64解码</button>
<label id="labelId">原有内容</label>
<button onclick="javascript:sendGzipData()">发送gzip压缩内容</button>
<img id="imgId" src="" alt="">

<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/pako/1.0.10/pako.min.js"></script>
<script type="text/javascript" src="/jquery.base64.js"></script>
<script type="text/javascript">

/*  Data URI格式规范
    data:[<mime type>][;charset=<charset>][;<encoding>],<encoded data>
    1.  data ：协议名称；
    2.  [<mime type>] ：可选项，数据类型（image/png、text/plain等）
    3.  [;charset=<charset>] ：可选项，源文本的字符集编码方式
    4.  [;<encoding>] ：数据编码方式（默认US-ASCII，BASE64两种）
    5.  ,<encoded data> ：  数据内容
*/

/*  当前Data URI scheme支持的类型
    data:,                            文本数据
    data:text/plain,                    文本数据
    data:text/html,                  HTML代码
    data:text/html;base64,            base64编码的HTML代码
    data:text/css,                    CSS代码
    data:text/css;base64,              base64编码的CSS代码
    data:text/javascript,              Javascript代码
    data:text/javascript;base64,        base64编码的Javascript代码
    data:image/gif;base64,            base64编码的gif图片数据
    data:image/png;base64,            base64编码的png图片数据
    data:image/jpeg;base64,          base64编码的jpeg图片数据
    data:image/x-icon;base64,          base64编码的icon图片数据
*/

    function getPicture() {
        $.ajax({
            url: "/file/picture",
            data: {},
            type: "GET",
            dataType: "json",
            success: function(result) {
                if(result.code == 0) {
                    /*获取图片*/
                    var imgFilename = result.filename;
                    var imgInBase64 = "data:image/jpg;base64," + result.fileInBase64;
                    $('#imgId').attr("src" , imgInBase64);

                    /* IE浏览器的下载需要特殊处理 */
                    if (window.navigator.msSaveOrOpenBlob) {
                        /* atob是浏览器原生的base64转ascii码的函数，相应的还设有atob，约束是只支持ASCII码（即英文字符），不支持中文和其他字符*/
                        // var bstr = decodeURIComponent(atob(result.fileInBase64));

                        $.base64.utf8encode = true;
                        var bstr = $.base64.atob(result.fileInBase64);
                        var n = bstr.length;
                        var u8arr = new Uint8Array(n);
                        while (n--) {
                            u8arr[n] = bstr.charCodeAt(n);
                        }
                        var blob = new Blob([u8arr]);
                        window.navigator.msSaveOrOpenBlob(blob, imgFilename);
                    } else {

                        picLink = document.createElement('a');
                        picLink.href = imgInBase64;
                        picLink.setAttribute('download', imgFilename);
                        picLink.click();
                    }
                }else{
                    alert("获取图片失败");
                }
            }
        });
    }

    function getTxtFile() {
        $.ajax({
            url: "/file/txt",
            data: {},
            type: "GET",
            dataType: "json",
            success: function(result) {
                if(result.code == 0) {
                    /*获取纯文本文件*/
                    var txtDesc = "data:text/plain;base64," + result.fileInBase64;
                    txtLink = document.createElement('a');
                    txtLink.href = txtDesc;
                    txtLink.setAttribute('download', result.filename);
                    txtLink.click();
                }else{
                    alert("获取txt文件失败")
                }
            }
        });
    }

    function getTextAndShow() {
        $.ajax({
            url: "/file/string",
            data: {},
            type: "GET",
            dataType: "json",
            success: function(result) {
                if(result.code == 0) {
                    /* base64解码程序，浏览器自带的atob不支持ASCII英文之外的其他字符 */
                    var gzipStr = $.base64.atob(result.text);
                    var charData = gzipStr.split('').map(function(x) {
                        return x.charCodeAt(0);
                    });
                    /*将字符转为byte[]数组*/
                    var binData = new Uint8Array(charData);
                    /* inflate解压缩字符串，入参是Uint8Array[]数组类型，对应的有pako.gzip()进行压缩的功能
                     * pako.inflate(binData)默认也是返回一个 Uint8Array 对象 */
                    var unzipStr = pako.inflate(binData, { to : "string"});

                    $('#labelId').text(unzipStr);

                    /* js里面进行gzip压缩并Base64编码的代码
                     * pako.gzip(params) 默认返回一个 Uint8Array 对象,如果此时使用 Ajax 进行请求,参数会以数组的形式进行发送
                     * 为了解决该问题,添加 {to: "string"} 参数,返回一个二进制的字符串 */
                    /* var str = $.base64.btoa(pako.gzip(encodeURIComponent(originStr), {to: "string"})); */

                }else{
                    alert("获取txt文件失败")
                }
            }
        });
    }

    function sendGzipData() {
        var params = encodeURIComponent(JSON.stringify(
            {
                title : "title",
                content: "there is the bunk data"
            }
        ));
        /* 内容太少的gzip压缩反而会增加数据量 */
        // params = pako.gzip(params, {to : "string"});
        $.ajax({
            url: "/file/gzipData",
            data: params,
            dataType: "json",
            type: "POST",
            // headers: {
            //     "Content-Encoding": "customedGzip"
            // },
            success: function(ret) {
                alert(ret);
            }
        });
    }
</script>
</body>
</html>