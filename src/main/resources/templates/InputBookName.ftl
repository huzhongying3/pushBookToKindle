<html lang="en">
<head>
<#assign path="${request.contextPath}">
</head>
<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
<#--<script src="${path}/jquery/jquery-3.1.1.min.js"></script>-->
<#--<script src="${path}/jquery/jquery.form.min.js"></script>-->
<script type="text/javascript">
    // wait for the DOM to be loaded
    $(document).ready(function() {
        // bind 'myForm' and provide a simple callback function
        // 为myform绑定ajaxForm异步提交事件，并提供一个简单的回调函数。
        $('#pushBook').click(function () {
            var data = {};
            $("#myForm").serializeArray().map(function(x){data[x.name] = x.value;});
            var url = "${path}/service/askBookUrl";
            $.ajax({
                url: url,
                data: JSON.stringify(data),
                type: 'post',
                dataType: 'json',
                async: false,
                contentType:'application/json',
                success: function (data) {
                    if(data.result==0){
                        alert("查询有此书,开始下载到服务器");
                        var bookURL = data.bookUrl;
                        var kindleMail = data.kindleMail;
                        var bookName = data.bookName;
                        $.ajax({
                            url: "${path}/service/downLoadBook",
                            data: JSON.stringify({"bookURL":bookURL,"kindleMail":kindleMail,"bookName":bookName}),
                            type: 'post',
                            dataType: 'json',
                            async: false,
                            contentType:'application/json',
                            success: function (data) {
                                if(result.result==0){
                                    alert("发送成功");

                                }else {
                                    alert("发送失败");
                                }

                            }
                        });
                    }else {
                        alert("查询无此书");
                    }

                }
            });
        });

        function sendMailFunction(data) {
            alert(data);
        }
    });
</script>
<body>
<div id="div1">
    <form id="myForm" method="post" >
        <p>书名：<input type="text" name="bookName"  /></p>
        <p>亚马逊kindle邮箱名：<input type="text" name="kindleMail"  /></p>
        <p><input id = 'pushBook' type="submit" value="推送" /></p>
    </form>
</div>
</body>

</html>