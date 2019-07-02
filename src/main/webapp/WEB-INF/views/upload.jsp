<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>模板上传</title>
    <meta name="decorator" content="default"/>
    <style>
        html, body {
            width: 100%;
            height: 100%
        }

        body {
            background: url('http://image.wuweifeng.top/vprBackgroundImage.jpg') no-repeat center center;
            background-size: cover;
            background-attachment: fixed;
            margin: 0px;
        }

        .flex {
            /*flex 布局*/
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .title-div {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 20%;
        }

        .content-div {
            display: flex;
            flex-direction: column;
            align-items: center;
            /*justify-content: center;*/
            height: 80%;
        }

        .upload-button { /* 按钮美化 */
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
            background-color: #d4d4d4; /* 设置按钮背景颜色为灰色 */
            /*border-color:#d4d4d4;!* 边框线颜色应与背景颜色一致 *!*/
            color: #fff; /* 设置字体颜色为白色 */
        }

        .upload-button:hover { /* 鼠标移入按钮范围时改变颜色 */
            background: #5599FF;
        }

        .info-table {
            table-layout: fixed;
            border-collapse: collapse;
            height: 60px;
            width: 500px;
            margin-top: 18px;
            word-break: break-all;
            vertical-align: middle;
            text-align: center;
            background-color: rgba(92, 173, 255, 0.5);
        }

        th {
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
        a[class="button-selectimg"]{color:#1E90FF;padding:4px 6px;border:1px dashed #1E90FF;border-radius:2px;}
        .input-file{
            display: flex;
            align-items: center;
            justify-content: center;
            height: 32px;
            width: 500px;
        }
        input[id="avatval"]{padding:3px 6px;padding-left:10px;border:1px solid #E7EAEC;width:230px;height:32px;line-height:30px;border-left:3px solid #1E90FF;background:#FAFAFB;border-radius:2px;}
        input[type='file']{border:0px;display:none;}
    </style>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script>
        $(function () {
            // 绑定按钮点击事件
            $("#bt-upload").click(function () {
                var data = new FormData();
                data.append("uploadFile", document.getElementById('chooseFile').files[0]);
                data.append("uploadCheckCode", $.getUrlParam('uploadCheckCode'));
                // 设置禁用效果，不再响应点击事件
                $(this).attr({"disabled": "disabled"});
                // 设置样式
                $(this).addClass("btn-disable");
                axios.post('/vpr-electronic-contract/uploadFile', data, {headers: {"Content-Type": "multipart/form-data"}})
                    .then(function (res) {
                        if (res.data.code === 200) {
                            alert("上传成功")
                        } else {
                            alert(res.data.message)
                        }
                        $("#bt-upload").removeAttr("disabled");
                        $("#bt-upload").removeClass("btn-disable");
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

                // alert($.getUrlParam('checkId'));
            });
            // $("#id").removeAttr("disabled");
            // $("#id").removeClass("btn_disable");
        });
        (function ($) {
            $.getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            }
        })(jQuery);

        $(function(){
            $("#avatsel1").click(function(){
                $("input[type='file']").trigger('click');
            });
            $("#avatval").click(function(){
                $("input[type='file']").trigger('click');
            });
            $("input[type='file']").change(function(){
                $("#avatval").val($(this).val());
            });
        });
    </script>
</head>
<body class="flex">
<div class="title-div">
    <h1 style="color: white;">VPR电子签约，守护您的权益</h1>
</div>
<div class="content-div">
    <%--<div class="filebox">--%>
        <%--<input type="file" id="chooseFile" accept=".docx"/>--%>
    <%--</div>--%>
    <div class="input-file">
        <input type="text" id="avatval" placeholder="请选择文件···" readonly="readonly" style="vertical-align: middle;"/>
        <input type="file" id="chooseFile" accept=".docx"/>
        <a href="javascript:void(0);" class="button-selectimg" id="avatsel1">选择文件</a>   
    </div>

    <table border="1" class="info-table">
        <tr>
            <th>上传目的：</th>
            <th>${purpose}</th>
        </tr>
    </table>
    <button id="bt-upload" class="upload-button">上传</button>
</div>
</body>
</html>
