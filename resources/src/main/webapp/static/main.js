/* Author:

 */

$(document).ready(function () {

    var header = ["缩略图", "名称", "文件大小"];

    var options = {
        header:header,
        data:{},
        check_box:true,
        data_view:true,
        data_edit:true,
        data_delete:true,
        page_info:{totalPage:10, pageSize:6},
        page_request_url:"/resources/rs/graphics/page",
        page_buttons:{
            next:"下一页",
            prev:"上一页",
            first:"第一页",
            last:"最后一页",
            operate:"操作",
            data_view:"详情",
            data_delete:"删除",
            data_edit:"编辑"
        },
        detail_show:function (elem, params) {
            var detail_option = {
                width:600,
                height:500,
                title:"详情",
                position:'center',
                buttons:{}
            };
            var dialog = $("#detail_panel").dialog(detail_option);
            dialog.find("img").attr("src","../"+params.originalFilePath);
            var text_length = params.name.length*10;
            if(text_length < 280){
                text_length = 280;
            }
            dialog.find("#name").css("width",text_length).attr("value",params.name);
            dialog.find("#description").addclass("disabled").css("width",text_length).attr("value",params.description);
            console.log();
            dialog.dialog("open");
        },
        edit_view_show:function (elem, params) {
            var detail_option = {
                width:600,
                height:500,
                title:"详情",
                position:'center',
                buttons:{}
            };
            detail_option.title = "编辑";
            var dialog = $("#detail_panel").dialog(detail_option);
            dialog.dialog("open");
        },
        delete_confirm:function (elem, params) {
            var detail_option = {
                width:300,
                height:200,
                position:'center',
                buttons:{
                    "删除":function () {
                        console.log(params);
                        $.publish("/data/delete", {foo:"bar"})
                        $(this).dialog("close");
                    },
                    "取消":function () {
                        $(this).dialog("close");
                    }
                }
            };
            dialog = $('<div/>').append($('<h3/>').text("是否确认删除?")).addClass("container");
            dialog.dialog(detail_option);
            dialog.dialog('open');

        },
        render_data:function (value, tr) {
            var td = $("<td/>").append($("<img/>").css("width",30).css("height",30).attr("src", "../"+value.thumbnailPath));
            tr.append(td);
            td = $("<td/>").append(value.name);
            tr.append(td);
            td = $("<td/>").append(Math.ceil(value.properties.size/1024)+"KB");
            tr.append(td);
        }
    };

    $.ajax(
        {
            url:"/resources/rs/graphics/pageinfo",
            type:"GET"
        }
    ).success(function(data){
            options.page_info = data;
            $.ajax({
                url:"/resources/rs/graphics/page",
                type:"GET",
                data:"requestPage=0&pageSize="+options.page_info.pageSize
            }).success(function(data){
                    console.log(data);
                    options.data = data.dataList;
                    $("#main_panel").myTables(options);
                });
        }).fail(function(data){
            console.log(data);
        });


});