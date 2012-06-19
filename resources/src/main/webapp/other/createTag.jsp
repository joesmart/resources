<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title></title>
</head>
<body>
<form class="well form-horizontal">
    <fieldset>
        <div>
            <label class="control-label" for="input01">名称</label>
            <div class="controls">
                <input type="text" class="input-xlarge" id="input01">
                <p class="help-block">输入标签名称</p>
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">提交</button>
            <button class="btn">取消</button>
        </div>
    </fieldset>

</form>

<script type="text/javascript">
    $(function(){
        $('button[type="submit"]').live("click",function(event){
            event.preventDefault();
            console.log($("form input[type='text']").val());
            var data = {tag:$("form input[type='text']").val()}
            $.ajax({
                url:"../rs/tag/add",
                type:"POST",
                data:JSON.stringify(data),
                dataType:"json",
                contentType:"application/json;charset=UTF-8",
                beforeSend: function(x) {
                    if (x && x.overrideMimeType) {
                        x.overrideMimeType("application/j-son;charset=UTF-8");
                    }
                }
            }).success(function(event){
                        console.log(event);
                        alert("数据添加成功");
                        $("form input[type='text']").val("");
                    }).fail(function(event){
                        alert("数据添加失败");
                    });
        });
    });
</script>
</body>
</html>