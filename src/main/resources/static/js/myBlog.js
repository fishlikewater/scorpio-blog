var myBlog = new function () {

    return {

        //加载分类下拉框
        loadType: function (id) {
            jQuery.get("/admin/type/list",function (data) {
                var type = jQuery("#type");
                jQuery.each(data.data,function (k,v) {
                    type.append("<option value='"+v.id+"'>"+v.name+"</option>");
                });
                if(id != "" || id != "undefined"){
                    jQuery("#type").val(id);

                }
                layui.use('form',function () {
                    var form = layui.form;
                    form.render('select');
                })

            })

        },

        //加载分类下拉框
        loadLable: function () {
            jQuery.get("admin/lable/list",function (data) {
                var type = jQuery("#lable");
                jQuery.each(data.data,function (k,v) {
                    type.append("<option value='"+v.id+"'>"+v.name+"</option>");
                })
            })
        },

        //初始化列表页
        initList: function () {
            layui.use(['table','form'], function () {
                var table = layui.table;
                var form = layui.form;
                table.on('tool(list)', function (obj) {
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值
                    var tr = obj.tr; //获得当前行 tr 的DOM对象
                    if (layEvent === 'detail') { //查看
                        myBlog.openLook(data);
                    } else if (layEvent === 'del') { //删除
                        layer.confirm('确定删除吗？', function (index) {
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                            var id = data.id;
                            var cId = data.contentid;
                            jQuery.post(contextPath + "admin/blog/detele",{id:id,cId:cId},function () {
                                layer.msg("删除成功",{icon:1})
                            })
                        });
                    } else if (layEvent === 'edit') { //编辑
                        myBlog.openUpdate(data);
                    }
                })
                form.on('checkbox(publicController)', function(obj){
                    myBlog.isPublic(this.value, obj.elem.checked)
                });
            });

        },

        isPublic : function (id, status) {
            jQuery.post("/admin/blog/public", {
                id:id,
                status:status
            }, function (data) {
                if(data.status == '001'){
                    layer.msg("操作失败", {icon:2});
                }else{
                    layer.msg("操作成功", {icon:1});
                }
            })
        },
        
        openUpdate:function (data) {
            layer.open({
                type: 2,
                title: '编辑窗口',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['1200px', '600px'],
                content:  contextPath + "admin/blog/update?id=" + data.id
            });
        },
        openLook:function (data) {
            layer.open({
                type: 2,
                title: '编辑窗口',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['1200px', '600px'],
                content:  contextPath + "/index/article/" + data.id
            });
        },

        initType: function () {
            layui.use('table', function () {
                var table = layui.table;
                table.on('tool(type)', function (obj) {
                    var data = obj.data;
                    var layEvent = obj.event;
                    var tr = obj.tr;
                    if (layEvent === 'detail') {
                        layer.msg("查看详情")
                    } else if (layEvent === 'del') {
                        layer.confirm('确定删除吗？', function (index) {
                            obj.del();
                            layer.close(index);
                            var id = data.id;
                            jQuery.post("admin/type/delete",{id:id},function () {
                                layer.msg("删除成功",{icon:1})
                            })
                        });
                    } else if (layEvent === 'edit') {
                        window.location.href = "admin/blog/edit?id=" + data.id;
                    }
                })

            });
        },

        initLable: function () {
            layui.use('table', function () {
                var table = layui.table;
                table.on('tool(lable)', function (obj) {
                    var data = obj.data;
                    var layEvent = obj.event;
                    var tr = obj.tr;
                    if (layEvent === 'detail') {
                        layer.msg("查看详情")
                    } else if (layEvent === 'del') {
                        layer.confirm('确定删除吗？', function (index) {
                            obj.del();
                            layer.close(index);
                            var id = data.id;
                            jQuery.post("admin/lable/delete",{id:id},function () {
                                layer.msg("删除成功",{icon:1})
                            })
                        });
                    } else if (layEvent === 'edit') {
                        window.location.href = "admin/blog/edit?id=" + data.id;
                    }
                })

            });
        },


        addLable: function () {
            layer.open({
                title:'添加标签',
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '180px'], //宽高
                content: jQuery("#addDIV"),
                btn: ['保存', '关闭'],
                'yes':function () {
                    jQuery.post("/admin/lable/add",{
                        'name':jQuery("#typeForm input[name='name']").val(),
                        'orderBy':jQuery("#typeForm input[name='orderBy']").val()
                    },function (msg) {
                        if(msg == null || msg == ""){
                            layer.msg("添加失败，检测是否存在该标签",{icon:2});
                        }else{
                            layer.closeAll();
                            layer.msg("添加成功",{icon:1});
                            layui.use('table', function () {
                                var table = layui.table;
                                table.reload('lable');
                            })
                        }
                    })
                },
                'bnt2':function(){
                    layer.close(this);
                }
            });
        },

        addType: function () {
            layer.open({
                title:'添加分类',
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: jQuery("#addDIV"),
                btn: ['保存', '关闭'],
                'yes':function () {
                    jQuery.post("/admin/type/add",{
                        'name':jQuery("#typeForm input[name='name']").val(),
                        'orderBy':jQuery("#typeForm input[name='orderBy']").val()
                    },function (msg) {
                        if(msg == null || msg == ""){
                            layer.msg("添加失败，检测是否存在该分类",{icon:2});
                        }else{
                            layer.closeAll();
                            layer.msg("添加成功",{icon:1});
                            layui.use('table', function () {
                                var table = layui.table;
                                table.reload('type');
                            })
                        }
                    })
                },
                'bnt2':function(){
                    layer.close(this);
                }
            });
        }
    };

};