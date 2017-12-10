var markDown = new function () {
    var simplemde = null;
    var layer;
    return {

        init: function () {
            layui.use('layer', function () {
                layer = layui.layer;
            });
            simplemde = new SimpleMDE({
                element: document.getElementById("fieldTest"),
                autoDownloadFontAwesome: true,
                spellChecker: false,
                autosave: {
                    enabled: true,
                    unique_id: "fieldTest",
                },
            });

            layui.use('form', function () {
                var form = layui.form;
                //监听提交
                form.on('submit(formDemo)', function (data) {
                    layer.msg(JSON.stringify(data.field));
                    return false;
                });
            });
        },

        toSave: function () {
            var comment = simplemde.value();//simplemde.markdown(simplemde.value());
            jQuery.post("/admin/blog/add", {
                    'aId':jQuery("#id").val(),
                    'tPic':jQuery("#tPic").val(),
                    'contentId':jQuery("#contentId").val(),
                    'name': jQuery("input[name='title']").val(),
                    'author': jQuery("input[name='author']").val(),
                    'typeId': jQuery("#type").val(),
                    'lables': jQuery("#lable").val(),
                    'isComment': jQuery("input[name='isComment']").val(),
                    'shortContent': jQuery("input[name='shortContent']").val(),
                    'content': comment
                },

                function (data) {
                    if(data == 'ok'){
                        layer.msg("保存成功",{icon:1});
                        simplemde.clearAutosavedValue();
                        simplemde.value("");
                    }
                }
            )
            // jQuery(".editor-preview-side").addClass("markdown-body");
        },
        
        getContent : function (id) {
            simplemde.clearAutosavedValue();
            simplemde.value("");
            jQuery.get("/admin/blog/content?id="+id,function (data) {
                simplemde.value(data.content);
            })
        }
    }
}