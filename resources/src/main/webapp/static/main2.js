/**
 * Created with IntelliJ IDEA.
 * User: ZouYanjian
 * Date: 12-6-1
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    $("#resources_menu>ul li a").live("click", function (event) {
        console.log(event);
    });

    $("#addNewResourceBtn").live("click", function (event) {
        event.preventDefault();
        console.log();
        $.ajax(
            {
                type:"POST",
                url:"rs/graphics/add/",
                dataType:'json',
                contentType:"application/json;charset=UTF-8",
                data:{adb:"adfad", asdfa:"adfasdf"}
            }
        ).done(function (data) {
                console.log(data);
            });

    });

    var pagination
    /*分页*/
    if ($('#pagination')) {
        var total_record = $("#record_size").attr("value");
        console.log(total_record);
        pagination = $('#pagination').smartpaginator({ totalrecords:total_record,
            recordsperpage:9,
            controlsalways:true,
            display:'single',
            datacontainer:'dataTable',
            dataelement:'tr',
            prev:'上一页',
            next:'下一页',
            first:'第一页',
            last:'最后一页',
            onchange:function (newPage) {
//                $('#r').html('Page # ' + newPage);
                console.log(pagination);
            }
        });
    }

    /*处理graphic 操作*/
    if($('#dataTable')){
        var id ;

        $('a.delete_link').click(function(){
            var options = {
                autoOpen: false,
                width: 'auto',
                modal: true,
                width:200,
                height:150,
                maxWidth:200,
                minWidth:200,
                maxHeight:150,
                minHeight:150,
                buttons:{
                    "删除":function(){
                        $(this).dialog("close");
                        console.log($('#'+id+'').parent().parent());
                        $('#'+id+'').parent().parent().css('display','none');
                        pagination.triggerHandler('update');
                    },
                    "取消":function(){
                        $(this).dialog("close");
                    }
                }
            };
            id = '';
            id = $(this).attr('id');
            console.log(id);
            var dialog = $('<div/>').append($('<h3/>').text("确认删除?")).addClass("container");
            dialog.dialog(options);
            dialog.dialog('open');
        });
        /*隐藏Detail 面板*/
        $('#detail_panel').css('display','none');
        $('a.detail_link').click(function(){
            var options = {
                autoOpen: false,
                width: 'auto',
                modal: true,
                width:700,
                height:500
            };
            var base_url = '../rs/graphics?id=';
            $('#properties_info').css('display','none');
            $('#main_info').css('display','');
            id = '';
            id = $(this).attr('id');
            console.log(id);
            var dialog = $("#detail_panel").dialog(options);
            console.log(base_url);
            $("#detail_panel img").attr('src','');
            $("#detail_panel img").attr('src',base_url+id);
            dialog.dialog('open');
        });

        $('a.edit_link').click(function(){
            var options = {
                autoOpen: false,
                width: 'auto',
                modal: true,
                width:700,
                height:500
            };
            var base_url = '../rs/graphics?id=';
            $('#main_info').css('display','none');
            $('#properties_info').css('display','');
            id = '';
            id = $(this).attr('id');
            console.log(id);
            var dialog = $("#detail_panel").dialog(options);
            console.log(base_url);
            $("#detail_panel img").attr('src','');
            $("#detail_panel img").attr('src',base_url+id);
            dialog.dialog('open');
        });
    }
});