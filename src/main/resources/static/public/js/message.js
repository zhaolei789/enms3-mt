;(
    function ($,window,document) {
        $.stompClient = null ;
        function errorCallBack (error) {
            // 连接失败时（服务器响应 ERROR 帧）的回调方法
            console.log(error)
        }
        $.iMessager = {
            // 1.链接到socket服务器
            connect : function (point,subscribeArr) {
                var socket = new SockJS('/message/endpoint-websocket');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function (frame) {
                   console.log('链接建立成功')
                    stompClient.subscribe('/message/info/ewsd0001', function (result) {
                        $.layim.getMessage({
                            username: "Hi"
                            , avatar: "http://qzapp.qlogo.cn/qzapp/100280987/56ADC83E78CEC046F8DF2C5D0DD63CDE/100"
                            , id: "ewsd0002"
                            , type: "friend"
                            , content: JSON.parse(result.body).content
                        });

                    },errorCallBack);
                    // stompClient.subscribe('/message/info', function (result) {
                    //
                    // },errorCallBack);

                });
            }
        }
    }
)(jQuery,window,document)