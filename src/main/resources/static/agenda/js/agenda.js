var agenda = new function ($) {

    var layer;
    var form;

    return {
        init: function () {
            layui.use(['layer', 'form'], function () {
                layer = layui.layer;
                form = layui.form;
            })
            //国际化默认值为'en'，代表使用英文
            var initialLocaleCode = 'zh-cn';
            //初始化FullCalendar 
            $('#calendar').fullCalendar({
                //设置头部信息，如果不想显示，可以设置header为false
                header: {
                    //日历头部左边：初始化切换按钮
                    left: 'prev,next today',
                    //日历头部中间：显示当前日期信息
                    center: 'title',
                    //日历头部右边：初始化视图
                    right: 'month,agendaWeek,agendaDay'
                },
                locale: initialLocaleCode,
                buttonIcons: true, // show the prev/next text
                weekNumbers: true,
                navLinks: true, // can click day/week names to navigate views
                selectable: true,
                selectHelper: true,
                eventTimeFormat:"HH:mm",
                displayEventEnd:true,
                select: function (start, end, jsEvent, view) {
                    agenda.select(start, end, view);
                },
                /* dayClick: function(date){
					console.log('dayClick触发的时间为：', date.format());
                },*/
                eventClick: function (event) {
                    agenda.edit(event);
                },
                eventResize: function (event, dayDelta, revertFunc) {
                    //do something here...
                    agenda.moveEvent(event);
                   /* console.log(' --- start --- eventResize');
                    console.log('eventResize被执行，Event的title属性值为：', event.title);
                    if (dayDelta._days != 0) {
                        console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
                    } else if (dayDelta._milliseconds != 0) {
                        console.log(event.end.format());
                        console.log('eventResize被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
                    } else {
                        console.log('eventResize被执行，Event的start和end时间没有改变！');
                    }
                    //revertFunc();
                    console.log('--- end --- eventResize');*/
                    // ...
                },
                eventDrop: function (event, dayDelta, revertFunc) {
                    //do something here...
                    agenda.moveEvent(event);
                    /*console.log('eventDrop --- start ---');
                    console.log('eventDrop被执行，Event的title属性值为：', event.title);
                    if (dayDelta._days != 0) {
                        console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._days + '天！');
                    } else if (dayDelta._milliseconds != 0) {
                        console.log('eventDrop被执行，Event的start和end时间改变了：', dayDelta._milliseconds / 1000 + '秒！');
                    } else {
                        console.log('eventDrop被执行，Event的start和end时间没有改变！');
                    }
                    //revertFunc();
                    console.log('eventDrop --- end ---');
                    // ...*/
                },
                editable: true,
                eventLimit: true, //
                //设置是否显示周六和周日，设为false则不显示  
                weekends: true,
                //日历初始化时显示的日期，月视图显示该月，周视图显示该周，日视图显示该天，和当前日期没有关系
                //defaultDate: '2016-06-06',
                //日程数据
                events: function (start, end, timezone, callback) {
                    var beginDay = start.format("YYYY-MM-DD")
                    var endDay = end.format("YYYY-MM-DD")
                    agenda.loadEvent(beginDay, endDay, callback)
                },

            });

            $.each($.fullCalendar.locales, function (localeCode) {
                $('#locale-selector').append(
                    $('<option/>')
                        .attr('value', localeCode)
                        .prop('selected', localeCode == initialLocaleCode)
                        .text(localeCode)
                );
            });

            // when the selected option changes, dynamically change the calendar option
            $('#locale-selector').on('change', function () {
                if (this.value) {
                    $('#calendar').fullCalendar('option', 'locale', this.value);
                }
            });
        },


        //加载数据
        loadEvent: function (beginDay, endDay, callback) {
            jQuery.get("/admin/agenda/list",
                {
                    beginDay: beginDay,
                    endDay: endDay
                },
                function (data) {
                    var events = [];
                    jQuery.each(data, function (k,v) {
                        events.push(v)
                    })
                    callback(events);
                })
        },

        //选择触发
        select: function (start, end, view) {
            var className = '';
            if(view.name == 'month'){
                className = 'allDay';
            }
            var beginTime = start.format("YYYY-MM-DD HH:mm:ss")
            var endTime = end.format("YYYY-MM-DD HH:mm:ss")
            jQuery("#content").val("");
            jQuery("#viewtime").html(beginTime + "至" + endTime);
            layer.open({
                title: '日程编辑',
                type: 1,
                skin: 'layui-layer-lan', //加上边框
                area: ['600px', '300px'], //宽高
                content: jQuery("#viewDiv"),
                btn: ['保存', '取消'],
                'yes': function (index) {
                    var content = jQuery("#content").val();
                    if (content == "") {
                        layer.msg("请输入内容", {icon: 2});
                        return false;
                    }
                    jQuery.post("/admin/agenda/create",
                        {
                            'startTime': beginTime,
                            'endTime':endTime,
                            'content':content,
                            'name':view.name,
                        }, function (data) {
                        if(data.code == 200){
                            layer.close(index);
                            layer.msg("保存成功",{icon:1});
                            eventData = {
                                id:data.id,
                                title: content,
                                start: start,
                                end: end,
                                className:className
                            };
                            $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                            $('#calendar').fullCalendar('unselect');
                        }else{
                            layer.msg("保存失败", {icon:2});
                        }
                    })
                },
                'btn2': function (index) {
                    layer.close(index);
                }
            });
        },
        
        edit : function (event) {
            var beginTime = event.start.format("YYYY-MM-DD HH:mm:ss")
            var endTime = event.end.format("YYYY-MM-DD HH:mm:ss")
            jQuery("#content").html('');
            form.render(null,'agendaForm');
            jQuery("#id").val(event.id)
            jQuery("#viewtime").html(beginTime + "至" + endTime);
            jQuery("#content").val(event.title);
            layer.open({
                title: '日程编辑',
                type: 1,
                skin: 'layui-layer-lan', //加上边框
                area: ['600px', '300px'], //宽高
                content: jQuery("#viewDiv"),
                skin: 'layui-layer-molv',
                btn: ['删除','保存', '取消'],
                'yes':function (index) {
                    layer.confirm("确认删除日程吗？", function () {
                        jQuery.post("/admin/agenda/delete", {
                            id:event.id
                        },function (data) {
                            if(data.code == 200){
                                layer.msg("删除成功",{icon:1});
                                layer.close(index);
                                $('#calendar').fullCalendar('removeEvents', event.id);
                            }else{
                                layer.msg("删除失败",{icon:2});
                                layer.close(index);
                            }
                        })
                    })
                },
                'btn2': function (index) {
                    var content = jQuery("#content").val();
                    if (content == "") {
                        layer.msg("请输入内容", {icon: 2});
                        return false;
                    }
                    jQuery.post("/admin/agenda/create",
                        {
                            startTime: beginTime,
                            endTime:endTime,
                            content:content,
                            id:event.id
                        }, function (data) {
                            if(data.code == 200){
                                layer.close(index);
                                eventData = {
                                    id:event.id,
                                    title: content,
                                    start: beginTime,
                                    end: endTime
                                };
                                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                                $('#calendar').fullCalendar('unselect');
                            }else{
                                layer.msg("保存失败", {icon:2});
                            }
                        })
                },
                'btn3': function (index) {
                    layer.close(index);
                }
            });
        },

        moveEvent : function (event) {
            var beginTime = event.start.format("YYYY-MM-DD HH:mm:ss")
            var endTime = event.end.format("YYYY-MM-DD HH:mm:ss")
            jQuery.post("/admin/agenda/update",
                {
                    startTime: beginTime,
                    endTime:endTime,
                    id:event.id
                }, function (data) {
                    if(data.code == 200){
                        layer.msg("操作成功",{icon:1});
                    }else{
                        layer.msg("操作失败", {icon:2});
                    }
                })
        }


    }
}(jQuery)