var markDown = new function () {
    var simplemde = null;


    return {

        init: function () {

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

            var comment = simplemde.markdown(simplemde.value());
            jQuery.post("admin/blog/add", {
                    'name': jQuery("input[name='title']").val(),
                    'author': jQuery("input[name='author']").val(),
                    'typeId': jQuery("#type").val(),
                    'lable': jQuery("#lable").val(),
                    'isComment': jQuery("input[name='isComment']").val(),
                    'content': comment
                },

                function (data) {
                    if(data == 'ok'){
                        layer.msg("保存成功",{icon:1});
                        simplemde.setValue("");
                    }
                }
            )
            // jQuery(".editor-preview-side").addClass("markdown-body");
        },
    }
}