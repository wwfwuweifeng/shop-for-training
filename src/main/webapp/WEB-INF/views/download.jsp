<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>模板下载</title>
    <meta name="decorator" content="default"/>
    <style>
        html,body{
            width:100%;
            height:100%
        }
        body{
            background:url('http://image.wuweifeng.top/vprBackgroundImage.jpg')  no-repeat center center;
            background-size:cover;
            background-attachment:fixed;
            margin: 0px;
        }
        .flex{
            /*flex 布局*/
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .title-div{
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 20%;
        }
        .content-div{
            display: flex;
            flex-direction: column;
            align-items: center;
            /*justify-content: center;*/
            height: 80%;
        }
        .download-button { /* 按钮美化 */
            width: 180px; /* 宽度 */
            height: 35px; /* 高度 */
            border-width: 0px; /* 边框宽度 */
            border-radius: 3px; /* 边框半径 */
            background: #1E90FF; /* 背景颜色 */
            cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
            outline: none; /* 不显示轮廓线 */
            color: white; /* 字体颜色 */
            font-size: 17px; /* 字体大小 */
            margin-top: 18px;
        }
        .btn-disable {
            background-color:#d4d4d4;/* 设置按钮背景颜色为灰色 */
            /*border-color:#d4d4d4;!* 边框线颜色应与背景颜色一致 *!*/
            color:#fff;/* 设置字体颜色为白色 */
        }
        .download-button:hover { /* 鼠标移入按钮范围时改变颜色 */
            background: #5599FF;
        }
        .info-table{
            table-layout: fixed;
            border-collapse: collapse;
            height: 120px;
            width: 500px;
            word-break: break-all;
            vertical-align: middle;
            text-align: center;
            background-color:rgba(92,173,255,0.5);
        }
        th{
            text-align: center;
            height: 60px;
            color: #E8E8E8;
        }
        tbody {
            height: auto;
            color: #080808;
            /*background-color: #e6e6e6;*/
            text-align: center;
        }
    </style>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
    </script>
    <script>
        $(function(){
            // 绑定按钮点击事件
            $("#bt-download").click(function(){

                // axios({ // 用axios发送post请求
                //     method: 'get',
                //     url: '/web/downStartTemplate', // 请求地址
                //     responseType: 'blob'
                // }).then(function (response) {
                //         console.log(response.data);
                //         var fileReader = new FileReader();
                //         fileReader.onload = function(e) {
                //             var anchor = document.createElement('a');
                //             anchor.style.display = 'none';
                //             anchor.href = e.target.result;
                //             anchor.download = 'test.docx';
                //             console.log(1111);
                //             anchor.click();
                //             document.removeChild(anchor)
                //         };
                //         fileReader.readAsDataURL(response.data);
                //     })
                //     .catch(function (error) {
                //         console.log("error");
                //     });
                var downloadCheckCode=$.getUrlParam('downloadCheckCode');
                if (null==downloadCheckCode){
                    window.location.href="/vpr-electronic-contract/downStartTemplate";
                } else {
                    window.location.href="/vpr-electronic-contract/downloadFile?downloadCheckCode="+downloadCheckCode;
                }
                // 设置禁用效果，不再响应点击事件
                $(this).attr({"disabled":"disabled"});
                // 设置样式
                $(this).addClass("btn-disable");
                // alert($.getUrlParam('checkId'));
            });
            // $("#id").removeAttr("disabled");
            // $("#id").removeClass("btn_disable");
        });
        (function ($) {
            $.getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            }
        })(jQuery);

        jQuery.download = function(url, data, method){ // 获得url和data
            if( url && data ){
                // data 是 string 或者 array/object
                data = typeof data == 'string' ? data : jQuery.param(data); // 把参数组装成 form的 input
                var inputs = '';
                jQuery.each(data.split('&'), function(){
                    var pair = this.split('=');
                    inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
                }); // request发送请求
                jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
            }
        }
    </script>
</head>
<body class="flex">
    <div class="title-div">
        <h1 style="color: white;">VPR电子签约，守护您的权益</h1>
    </div>
    <div class="content-div">
        <h2 style="color: white;">模板信息</h2>

        <table border="1" class="info-table">
            <tr>
                <th>模板名：</th>
                <th>${template.templateName}</th>
            </tr>
            <tr>
                <th>模板编号：</th>
                <th>${template.templateId}</th>
            </tr>
        </table>

        <button id="bt-download" class="download-button">点击下载</button>
    </div>
</body>
</html>
