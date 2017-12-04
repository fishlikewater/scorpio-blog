var agenda = new function($) {

    return {
        init: function() {
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
                select: function(start, end) {
                    console.log(start.format());
                    console.log(end.format());
                    var title = prompt('Event Title:');
                    var eventData;
                    if (title) {
                        eventData = {
                            title: title,
                            start: start,
                            end: end
                        };
                        $('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                    }
                    $('#calendar').fullCalendar('unselect');
                },
                /* dayClick: function(date){
					console.log('dayClick触发的时间为：', date.format());
                },*/
                eventClick: function(event) {
                    alert(event.title);
                },
                eventResize: function(event, dayDelta, revertFunc) {
                    //do something here...
                    console.log(' --- start --- eventResize');
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
                    console.log('--- end --- eventResize');
                    // ...
                },
                eventDrop: function(event, dayDelta, revertFunc) {
                    //do something here...
                    console.log('eventDrop --- start ---');
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
                    // ...
                },
                editable: true,
                eventLimit: true, //
                //设置是否显示周六和周日，设为false则不显示  
                weekends: true,
                //日历初始化时显示的日期，月视图显示该月，周视图显示该周，日视图显示该天，和当前日期没有关系
                //defaultDate: '2016-06-06',
                //日程数据 
                events: [{
                        title: 'All Day Event',
                        start: '2016-08-11'
                    },
                    {
                        id: 4,
                        title: '使用className:done',
                        start: '2017-12-04 09:00:00',
                        end: '2017-12-04 18:00:00',
                        color: 'blue',
                        className: 'done'

                    },
                ]
            });

            $.each($.fullCalendar.locales, function(localeCode) {
                $('#locale-selector').append(
                    $('<option/>')
                    .attr('value', localeCode)
                    .prop('selected', localeCode == initialLocaleCode)
                    .text(localeCode)
                );
            });

            // when the selected option changes, dynamically change the calendar option
            $('#locale-selector').on('change', function() {
                if (this.value) {
                    $('#calendar').fullCalendar('option', 'locale', this.value);
                }
            });
        },


    }
}(jQuery)