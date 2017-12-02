var formUtil = new function ($) {


    return {

        /**
         * 构建form表单post提交
         * @param url
         * @param params
         */
        post: function (url, params) {
            var tempForm = document.createElement("form");
            tempForm.action = url;
            tempForm.method = "post";
            tempForm.style.display = "none";
            for (var x in params) {
                var opt = document.createElement("input");
                opt.type = "hidden"
                opt.name = x;
                opt.value = params[x];
                tempForm.appendChild(opt);
            }
            document.body.appendChild(tempForm);
            tempForm.submit(tempForm);
            tempForm.remove()
        },

        /**
         * 构建form表单get提交
         * @param url
         * @param params
         */
        get: function (url, params) {
            var tempForm = document.createElement("form");
            tempForm.action = url;
            tempForm.method = "get";
            tempForm.style.display = "none";
            for (var x in params) {
                var opt = document.createElement("input");
                opt.type = "hidden"
                opt.name = x;
                opt.value = params[x];
                tempForm.appendChild(opt);
            }
            document.body.appendChild(tempForm);
            tempForm.submit(tempForm);
            tempForm.remove()
        },


        /**
         * 序列化form表单获取提交数据
         * @param formId
         */
        data: function (formId) {

            return $("#"+formId).serialize();
        }

    }
}(jQuery);