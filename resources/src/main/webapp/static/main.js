/* Author:

 */

$(document).ready(function () {

    var header = ["缩略图", "名称", "文件大小"];

    function update_detail_panel(value) {
        if (value == 1) {
            $("#main_info").fadeOut();
            $("#main_info").css("display", "none");
            $("#properties_info").fadeIn();
            $("#properties_info").css("display", "");
        } else if (value == 2) {
            $("#main_info").css("display", "");
            $("#properties_info").css("display", "none");
        }

    }

    $("a[id='properties']").live("click", function () {
        update_detail_panel(1);
    });
    $("a[id='back']").live("click", function () {
        update_detail_panel(2);
    });

    function populateDetailPanelData(dialog,detail_option, graphic) {
        dialog.find("img").attr("src", "../" + graphic.originalFilePath);
        var text_length = graphic.name.length * 10;
        if (text_length < 280) {
            text_length = 280;
        }
        update_detail_panel(2);

        dialog.find("#name").addClass("disabled").css("width", text_length).val("");
        dialog.find("#name").addClass("disabled").css("width", text_length).attr("value", graphic.name);

        dialog.find("#description").addClass("disabled").css("width", text_length).val();
        dialog.find("#description").addClass("disabled").css("width", text_length).val(graphic.description);


        if(graphic.tag){
            dialog.find("#tag").addClass("disabled").css("width", text_length).text();
            dialog.find("#tag").addClass("disabled").css("width", text_length).text(graphic.tag.tag);
        }
        if(graphic.workSpace){
            dialog.find("#workSpace").addClass("disabled").css("width", text_length).text();
            dialog.find("#workSpace").addClass("disabled").css("width", text_length).text(graphic.workSpace.name);
        }

        dialog.find("#uuid").text(graphic.properties.uuid);
        dialog.find("#path").text(graphic.properties.path);
        dialog.find("#size").text(graphic.properties.size + "B");
        dialog.find("#createTime").text(new Date(graphic.properties.updateDate));
        if(graphic.checkStatus == "UNCHECKED"){
            dialog.find("#checkStatus").text("未审核");
            dialog.find("#checkResult").css("display","none");
        }
        if(graphic.checkStatus == "CHECKED"){
            dialog.find("#checkStatus").text("已审核");
            dialog.find("#checkResult").css("display","block");
            if(graphic.checkResult == "PASS"){
                dialog.find("#checkResult").text("通过");
            }
            if(graphic.checkResult == "UNPASS"){
                dialog.find("#checkResult").text("未通过");
            }
        }
        dialog.find("#save_button").css("display", "none");
    }

    $.ajax({
        url:"../rs/tag/all",
        type:'GET'
    }).success(function(data){
            $.each(data,function(index,value){
                var option = $("<option/>").val(value.id).text(value.tag);
                $("#tag_selector").append(option);
            });
        });

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
        detail_show:function (elem, graphic) {
            var detail_option = {
                width:600,
                height:500,
                title:"详情",
                position:'center',
                buttons:{}
            };
            var dialog = $("#detail_panel").dialog(detail_option);
            dialog.dialog("close");
            dialog.find("#tag").css("display","block");
            dialog.find("#tag_selector").css("display","none");
            populateDetailPanelData(dialog,detail_option, graphic);
            dialog.dialog("open");
        },
        edit_view_show:function (elem, graphic) {
            var detail_option = {
                width:600,
                height:500,
                title:"详情",
                position:'center',
                buttons:{}
            };
            detail_option.title = "编辑";
            var dialog = $("#detail_panel").dialog(detail_option);
            dialog.dialog("close");
            populateDetailPanelData(dialog,detail_option, graphic);
            dialog.find("#tag").css("display","none");
            dialog.find("#tag_selector").css("display","block");
            dialog.find("#save_button").css("display","block");
            if(graphic.tag){
                $("#tag_selector option[value='"+graphic.tag.idString+"']").attr("selected","selected");
            }

            dialog.dialog("open");
            var tagId;
            var context = $(this);
            $("#save_button").click(function(){
                graphic.name =   dialog.find("#name").val();
                graphic.description =  dialog.find("#description").val();
                tagId = $("#tag_selector option:selected").attr("value");

                $.ajax({
                    type:"POST",
                    url:"../rs/graphics/update",
                    data:JSON.stringify({id:graphic.idString,name:graphic.name,description:graphic.description,tagId:tagId}),
                    dataType:"json",
                    contentType:"application/json; charset=UTF-8",
                    beforeSend: function(x) {
                        if (x && x.overrideMimeType) {
                            x.overrideMimeType("application/j-son;charset=UTF-8");
                        }
                    },
                    success:function(){
                        console.log("success");
                        $.publish("/data/saved", {message:"success"});

                    }
                }).fail(function(data){
                        console.log("fail");
                    });
                dialog.dialog("close");
            });
        },
        delete_confirm:function (elem, params) {
            var detail_option = {
                width:300,
                height:200,
                position:'center',
                buttons:{
                    "删除":function () {
                        var context = $(this);
                        $.ajax({
                            context:context,
                            type:"POST",
                            url:"../rs/graphics/delete",
                            data:{id:params},
                            success:function(){
                                console.log("success");
                                $.publish("/data/delete", {message:"success"});
                                $(this).dialog("close");
                            }
                        }).fail(function(data){
                                console.log("done");
                            });
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
        batch_delete_confirm:function (elem, idList) {
            var detail_option = {
                width:300,
                height:200,
                position:'center',
                buttons:{
                    "删除":function () {
                        var context = $(this);
                        $.ajax({
                            context:context,
                            type:"POST",
                            url:"../rs/graphics/batch_delete",
                            data:JSON.stringify(idList),
                            dataType:"json",
                            contentType:"application/json; charset=UTF-8",
                            success:function(){
                                console.log("success");
                                $.publish("/data/delete", {message:"success"});
                                $(this).dialog("close");
                            }
                        }).fail(function(data){
                                console.log("done");
                            });
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
        batch_check_confirm:function (parent, graphic_id_array) {
            var form = $("<form/>").append($("<label/>").text("审核结果:"));
            var checkResult = $("<select>" +
                "<option value='pass'>审核通过</option>" +
                "<option value='unpass'>审核不通过</option>" +
                "</select>");
            form.append(checkResult);
            form.css("display","block");
            var flashMessage = $("<div/>").css("display","none");

            var detail_option = {
                width:300,
                height:200,
                position:'center',
                title:"审核",
                buttons:{
                    "确定":function () {

                      if(graphic_id_array.length == 0){
                          form.css("display","none");
                          flashMessage.css("display","block").addClass("alert").html("<span>未选择审核条目</span>");
                      }else{
                          var context = $(this);
                          console.log(checkResult.find("option:selected").attr("value"));
                          var checkResultValue = checkResult.find("option:selected").attr("value");
                          var data = {};
                          data.checkResult=checkResultValue;
                          data.graphicIds = graphic_id_array;

                          //TODO 提交服务器审核.
                          $.ajax({
                              context:context,
                              type:"POST",
                              url:"../rs/graphics/checks",
                              data:JSON.stringify(data),
                              dataType:"json",
                              contentType:"application/json; charset=UTF-8",
                              beforeSend: function(x) {
                                  if (x && x.overrideMimeType) {
                                      x.overrideMimeType("application/j-son;charset=UTF-8");
                                  }
                              },
                              success:function(){
                                  console.log("data check success");
                                  $.publish("/data/saved", {message:"success"});
                                  $(this).dialog("close");
                              }

                          });
                      }

                    },
                    "取消":function () {
                        $(this).dialog("close");
                    }
                }
            };

            dialog = $('<div/>').append(form).addClass("container").append(flashMessage);

            dialog.dialog(detail_option);
            dialog.dialog('open');

        },
        render_data:function (value, tr) {
            var td = $("<td/>").append($("<img/>").css("width", 30).css("height", 30).attr("src", "../" + value.thumbnailPath));
            tr.append(td);
            td = $("<td/>").append(value.name);
            tr.append(td);
            td = $("<td/>").append(Math.ceil(value.properties.size / 1024) + "KB");
            tr.append(td);
        }
    };

    var queryType = $("#queryType").val();
    $.ajax(
        {
            url:"/resources/rs/graphics/pageinfo",
            type:"GET"
        }
    ).success(function (data) {
            options.page_info = data;
            options.queryType=queryType;
            $.ajax({
                url:"/resources/rs/graphics/page",
                type:"GET",
                data:"requestPage=0&pageSize=" + options.page_info.pageSize+"&queryType="+queryType
            }).success(function (data) {
                    console.log(data);
                    options.data = data.dataList;
                    $("#main_panel").myTables(options);
                });
        }).fail(function (data) {
            console.log(data);
        });


});