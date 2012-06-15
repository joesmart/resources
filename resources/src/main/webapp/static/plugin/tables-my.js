/*!
 * jQuery 'best options' plugin boilerplate
 * Author: @cowboy
 * Further changes: @addyosmani
 * Licensed under the MIT license
 */

;
(function ($, window, document, undefined) {

    function createCheckboxHeade(options, _tr) {
        var th;
        if (options.check_box) {
            th = $("<th/>").css("width", "3%").append($("<input type='checkbox'/>").click(function (event) {
                var id_array = [];
                if ($(event.target).prop("checked") == true) {
                    $("table input:checkbox").prop("checked", true);
                    /* console.log($("table input:checkbox").length);
                     var checkboxes = $("table input:checkbox");
                     if(checkboxes.length > 0){
                     checkboxes.each(function(index){
                     if($(this).attr("id")){
                     id_array.push($(this).attr("id"));
                     }
                     })
                     }
                     console.log();
                     options.myMethod(checkboxes,id_array.join(","));*/
                } else {
                    $("table input:checkbox").prop("checked", false);
                }
            }));
            _tr.append(th);
        }
        return th;
    }

    /*
     * 创建数据操作列
     * */
    function createOperateHeader(options, _tr) {
        if (options.data_view || options.data_edit || options.data_delete) {
            var th = $("<th/>").css("width", "20%").text(options.page_buttons.operate);
            _tr.append(th)
        }
    }

    /*
     * 创建数据表表头.
     * */
    function createTableHeader(options, _table_header) {
        var tr = $("<tr/>")
        createCheckboxHeade(options, tr);
        var th
        $.each(options.header, function (index, value) {
            if(index == 0){
                th = $("<th/>").css("width","15%").text(value);
            }else if(index == 2){
                th = $("<th/>").css("width","15%").text(value);
            }else{
                th = $("<th/>").text(value);
            }
            tr.append(th);
        });
        createOperateHeader(options, tr);
        _table_header.append(tr);
    }

    function generateDataOperator(options, value, tr) {
        var td ;
        if (options.data_view || options.data_edit || options.data_delete) {
            td = $("<td/>");

            if (options.data_view) {
                var a = $("<a/> ");
                a.attr("id", value.idString).attr('href', 'javascript:void(0)');
                a.append($("<i class='icon-list-alt'></i>")).append(options.page_buttons.data_view);
                a.click(function (event) {
                    console.log(options);
                    var obj = options.map[value.idString];
                    options.detail_show(this,obj);
                });
                td.append(a).append("&nbsp;");
            }

            if (options.data_edit) {
                a = $("<a/> ").append($("<i class='icon-wrench'></i>")).append(options.page_buttons.data_edit);
                a.attr("id", value.idString).attr('href', 'javascript:void(0)');
                a.click(function () {
                    options.edit_view_show();
                });
                td.append(a).append("&nbsp;");
            }

            if (options.data_delete) {
                a = $("<a/> ").append($("<i class='icon-trash'></i>")).append(options.page_buttons.data_delete);
                a.attr("id", value.idString).attr('href', 'javascript:void(0)');
                a.click(function () {
                    options.delete_confirm($(this), a.attr('id'));
                });
                td.append(a).append("&nbsp;");
            }
            tr.append(td);
        }
        return td;
    }

    function render_data(options,value, tr) {
        var td ;
        /*$.each(value, function (index, value) {
            td = $("<td/>").append(value);
            tr.append(td);
        });*/
        options.render_data(value,tr);
    }

    /**
     * 创建数据表表体
     */
    function crateTableBody(options, _table_body) {
        var tr;
        var td;
        $.each(options.data, function (index, value) {
            tr = $("<tr/>");
            if (options.check_box) {
                td = $("<td>").append($("<input type='checkbox'/>").attr("id", value.idString));
                tr.append(td);
            }
            render_data(options,value, tr);
            generateDataOperator(options, value, tr);
            _table_body.append(tr);
        });
    }

    /*
     * 创建数据表
     * */
    function createTable(options) {
        var _table_header = $("<thead/>");
        var _table_body = $("<tbody/>");
        createTableHeader(options, _table_header);
        crateTableBody(options, _table_body);
        //数据表格
        var tables = $("<table/>").attr("id", "dataContainer")
            .addClass("table table-striped table-bordered table-condensed")
            .append(_table_header)
            .append(_table_body);
        return tables;
    }

    //创建分页
    function createPagination(options,current_page) {
        //分页
        var li;
        var pagination = $("<ul/>");

        li = $("<li/>").append($("<a/>").attr("id","first").text(options.page_buttons.first).attr('href', 'javascript:void(0)'));
        pagination.append(li);
        li = $("<li/>").append($("<a/>").attr("id","prev").text(options.page_buttons.prev).attr('href', 'javascript:void(0)'));
        pagination.append(li);

        for (var i = 1; i <= options.page_info.totalPage; i++) {
            li = $("<li/>").append($("<a/>").attr("id",i).text(i).attr('href', 'javascript:void(0)'));
            pagination.append(li);
        }
        li = $("<li/>").append($("<a/>").attr("id","next").text(options.page_buttons.next).attr('href', 'javascript:void(0)'));
        pagination.append(li);
        li = $("<li/>").append($("<a/>").attr("id","last").text(options.page_buttons.last).attr('href', 'javascript:void(0)'));
        pagination.append(li);


        return pagination;
    }

    function updatePaginationToolbarStatus(options,current_page){
        var first = $('a[id="first"]').parent();
        var next = $('a[id="next"]').parent();
        var prev = $('a[id="prev"]').parent();
        var last = $('a[id="last"]').parent();
         $('a[id=\"'+current_page+'\"]').parent().addClass("active");
        console.log("current_page:"+current_page);
        var total_page = options.page_info.totalPage;
//        current_page = parseInt(current_page);
        if(current_page <= 1){
            first.addClass("disabled");
            prev.addClass("disabled");
            last.removeClass("disabled");
            last.removeClass("disabled");
        } else if(current_page >= total_page){
            first.removeClass("disabled");
            prev.removeClass("disabled");
            next.addClass("disabled");
            last.addClass("disabled");
        }else{
            first.removeClass("disabled");
            prev.removeClass("disabled");
            next.removeClass("disabled");
            last.removeClass("disabled");
        }
    }

    function paginationItemClick(current_page, options) {
        $(this).parent().siblings().removeClass("active");
        $(this).parent().addClass("active");
        var id_value = $(this).attr("id");
        if (id_value == "prev") {
            current_page = current_page - 1;
            if (current_page < 1) current_page = 1;
        } else if (id_value == "next") {
            current_page = current_page + 1;
            if (current_page > options.totalPage) current_page = options.totalPage;
        } else if (id_value == "first") {
            current_page = 1;
        } else if (id_value == "last") {
            current_page = options.totalPage;
        } else {
            current_page = id_value;
        }
        return current_page;
    }

    function updateTable(options, data) {
        options.data = data;
        generateClientCache(options);
        $('#dataContainer tbody tr').remove();
        var _table_body = $('#dataContainer tbody ');

        if (_table_body) {
            crateTableBody(options, _table_body);
        }
    }

    function generateClientCache(options) {
        var map = new Object();
        $.each(options.data, function (index, value) {
            map[value.idString] = value;
        });
        options.map = map;
    }

    $.fn.myTables = function (options) {
        // Here's a best practice for overriding 'defaults'
        // with specified options. Note how, rather than a
        // regular defaults object being passed as the second
        // parameter, we instead refer to $.fn.pluginName.options
        // explicitly, merging it with the options passed directly
        // to the plugin. This allows us to override options both
        // globally and on a per-call level.

        var tables;
        $.subscribe("/data/delete",function(topic,data){
            console.log(topic);
            console.log(data);

            $('#dataContainer tbody tr').remove();
            var _table_body = $('#dataContainer tbody ');
            if(_table_body){
                crateTableBody(options,_table_body);
            }
        });

        $.subscribe("/data/initialize",function(topic,data){
            console.log(topic);
            console.log(data);
            updateTable(options, data);
        });

        options = $.extend({}, $.fn.myTables.options, options);

        generateClientCache(options);

        return this.each(function () {
            var current_page = 1;
            var filter_array = ["first","next","prev","last"];
            console.log("xxx");
            tables = createTable(options);
            //把table 放到 Div 里.
            var pagination = createPagination(options,current_page);
            //把pagination 放到div 中去.
            var _div_pagination = $("<div/>").addClass("pagination").append(pagination);
            //添加 table 和 pagination
            $(this).addClass("well container well-large").append(tables).append(_div_pagination);

            updatePaginationToolbarStatus(options,current_page);
            $(".pagination ul li a").live("click",function(){
                var urls = ["data1.json","data2.json"];
                var randomNum = Math.ceil(Math.random()*2);
                console.log("randomNum:"+randomNum);
                current_page = paginationItemClick.call(this, current_page, options);
                $.ajax(
                    {url:options.page_request_url,
                    type:'GET',
                    data:"requestPage="+current_page+"&pageSize="+options.page_info.pageSize
                    }).success(
                    function(data){
                        console.log("data_update");
                        updateTable(options,data.dataList);
                    }
                );

                updatePaginationToolbarStatus(options,current_page);
            });
        });
    };

    // Globally overriding options
    // Here are our publicly accessible default plugin options
    // that are available in case the user doesn't pass in all
    // of the values expected. The user is given a default
    // experience but can also override the values as necessary.
    // eg. $fn.pluginName.key ='otherval';

    $.fn.myTables.options = {
        key:"value",
        header:[],
        data:[],
        map:{},
        check_box:false,
        page_info:{},
        page_buttons:{
            next:"Next", prev:"Prev", first:"First", last:"Last",
            operate:"Operate", data_view:"view", data_delete:"Delete",
            data_edit:"Edit"
        },
        myMethod:function (elem, param) {
            console.log("myMethod");
            console.log("myMethod:" + elem.length);
            console.log("myMethod:" + param);
        },
        detail_show:function (elem, param) {

        },
        edit_view_show:function(elem,param){

        },
        render_data:function(vale,tr){

        }
    };

})(jQuery, window, document);
