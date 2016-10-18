<html lang="en">
<head>

</head>
<script src="/jquery/jquery-3.1.1.min.js"></script>
<script src="/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
    // wait for the DOM to be loaded
    $(document).ready(function() {
        // bind 'myForm' and provide a simple callback function
        // 为myform绑定ajaxForm异步提交事件，并提供一个简单的回调函数。
        $('#myForm').ajaxForm(function() {
            alert("Thank you for your comment!");
        });
    });
</script>
<body>
<div id="div1">
    <form id="myForm" method="get" action="#">
        <p>书名：<input type="text" name="bookNmae"  /></p>
        <p>亚马逊kindle邮箱名：<input type="text" name="mailAddress"  /></p>
        <p><input type="submit" value="推送" /></p>
    </form>
</div>
</body>

</html>