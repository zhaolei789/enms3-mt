(
    function ($) {
        // 1.第一步需要插入元素
        if ($.parser && $.parser.plugins) {
            $.parser.plugins.push('viewer');
        }

        $.fn.iViewer = function (options, param) {
            if (typeof options == 'string') {
                var method = $.fn.iViewer.methods["viewMethods"];
                return method(this,options,param);
            }
            this.each(function () {
                // 1. 获取options属性
                if (typeof options == 'string'){
                    return $.fn.iViewer.methods[options](this, param);
                }

                options = options || {};

                // 2. 存储options属性
                var state = $.data(this, 'viewer');
                if (state){
                    $.extend(state.options, options);
                } else {
                    var opts = $.extend({}, $.fn.iViewer.defaults, $.fn.iViewer.parseOptions(this), options);
                    $.data(this, 'viewer', {
                        options: opts,
                    });
                    init(this);
                }
            });
        };
        var errorMsg = "提示：请引入插件的资源文件，否则无法正常显示！";
        $.fn.iViewer.methods = {
            viewMethods:function(jq,methodsName,params){
                    return jq.each(function () {
                        var $imgBox = $(this).children("div:first");
                        // 1.判断传过来的参数是数组或者String
                        if(typeof params == 'string' || typeof params == 'boolean' || typeof params == 'number'){
                            $imgBox.viewer(methodsName,params);
                        }else if (params instanceof Array) {
                            var len = params.length;
                            switch (len) {
                                case 0: $imgBox.viewer(methodsName);break;
                                case 1: $imgBox.viewer(methodsName,params[0]);break;
                                case 2: $imgBox.viewer(methodsName,params[0],params[1]);break;
                                default : $imgBox.viewer(methodsName,params[0],params[1]);
                            }
                        }else{
                            $imgBox.viewer(methodsName)
                        }
                    })
            }
        };
        $.iViewer = {
            show : function (url,opt,title) {
                // 1.创建一个预览区域 当图片预览关闭的时候销毁它
                var urlArr =  url instanceof Array ? url : [url];

                $iViewer = $("<div class='viewer-body hidden'></div>").appendTo("body");

                var img = null;
                // TODO 实现大小图不同url
                $.each(urlArr,function (i) {
                    img = new Image();
                    img.src = urlArr[i];
                    img.alt = title;
                    $iViewer.append(img);
                });
                // 若创建失败，a会有值，创建成功a无值
                var a = $iViewer.iViewer(opt);

                if(a){
                    $(this).remove();
                    return;
                }
                $iViewer.iViewer('show')
                $iViewer.on('hidden',function () {
                    $(this).iViewer('destroy');
                    $(this).remove();
                })

            }
        };


        $.fn.iViewer.defaults = {
            backdrop:true // 背景 默认为true
        };
        $.fn.iViewer.parseOptions = function(target){
            //  $.parser.parseOptions主要是获取data-options中的属性，和其他如<img url='xxx'>等属性 ，需要在里面$.fn.iViewer.parseOptions定义才有效
            return $.extend({}, $.parser.parseOptions(target));
        };



        //创建图片预览插件
        function init(target){
            try {
                var opts = $.data(target, 'viewer').options;
                var html = $(target).html(); // 保存html元素
                // var t = $(target).empty(); // 从被选的元素中移除所有的内容
                var $imgBox = $("<div></div>");
                $imgBox.html(html);
                $(target).html($imgBox);
                $imgBox.viewer(opts);
            }catch (e) {
                console.log(errorMsg)
            }

        }
    }
)(jQuery);
