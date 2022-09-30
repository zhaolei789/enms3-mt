if (!/^http(s*):\/\//.test(location.href)) {
    alert('请部署到localhost上查看该演示');
}


layui.use('layim', function (layim) {
    $.layim = layim;
    var  URL = {
        updateSign : "/message/Im/updateSign",// 更新个性签名的地址
        clientToClientReceive : "/app/clientToClientReceive",// 发送消息的的地址
        updateStatus : "/message/Im/updateStatus" // 更新在线状态
    };

    var stompClient; // 声明一个webscoket变量
    //演示自动回复
    var autoReplay = [
        '您好，我现在有事不在，一会再和您联系。'

    ];

    //基础配置
    layim.config({
        //初始化接口
        init: {
            url: '/message/Im/getList'
            , data: {}
        }
        //查看群员接口
        , members: {
            url: '../../json/layim/getMembers.json'
            , data: {}
        }
        //上传图片接口
        , uploadImage: {
            url: '/upload/image' //（返回的数据格式见下文）
            , type: '' //默认post
        }

        //上传文件接口
        , uploadFile: {
            url: '/upload/file' //（返回的数据格式见下文）
            , type: '' //默认post
        }

        , isAudio: true //开启聊天工具栏音频
        , isVideo: true //开启聊天工具栏视频

        //扩展工具栏
        , tool: [{
            alias: 'code'
            , title: '代码'
            , icon: '&#xe64e;'
        }]

        //,brief: true //是否简约模式（若开启则不显示主面板）

        ,title: '即时聊天' //自定义主面板最小化时的标题
        //,right: '100px' //主面板相对浏览器右侧距离
        //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
        , initSkin: '3.jpg' //1-5 设置初始背景
        //,skin: ['aaa.jpg'] //新增皮肤
        // ,isfriend: false //是否开启好友
        ,isgroup: false //是否开启群组
        //,min: true //是否始终最小化主面板，默认false
        , notice: true //是否开启桌面消息提醒，默认false
        //,voice: false //声音提醒，默认开启，声音文件为：default.mp3

        , msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        , find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
        , chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

    });

    //监听在线状态的切换事件 offline-离线 online-在线 hide-隐身
    layim.on('online', function (status) {
        layer.msg(status);
        $.post(URL.updateStatus,{status:status},function (res) {
            layer.msg(res.message)
        })
        // layim.setFriendStatus('ewsd0002', 'offline');
    });
    //监听签名修改
    layim.on('sign', function (value) {
        $.post(URL.updateSign,{sign:value},function (res) {
            layer.msg(res.message)
        })
    });

    //监听自定义工具栏点击，以添加代码为例
    layim.on('tool(code)', function (insert) {
        layer.prompt({
            title: '插入代码'
            , formType: 2
            , shade: 0
        }, function (text, index) {
            layer.close(index);
            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
        });
    });

    //监听layim建立就绪
    layim.on('ready', function (res) {
        connect();
    });

    // 监听发送的消息
    layim.on('sendMessage', function(res){
        var wsData = {
            fromId:res.mine.id,
            toId:res.to.id,
            content:res.mine.content,
            avatar:res.mine.avatar,
            username:res.mine.username,
            type:res.to.type
        };
        stompClient.send(URL.clientToClientReceive, {}, JSON.stringify(wsData));
    });
    //监听聊天窗口的切换
    layim.on('chatChange', function(res){
        var type = res.data.type;
        if(type === 'friend'){
            //模拟标注好友状态 #3FDD86
            var lableArr = ['<span style="color:#3FDD86;">在线</span>','<span style="color:lightgrey;">离线请留言</span>'];
            layim.setChatStatus( res.data.status == "online" ? lableArr[0]:lableArr[1]);
        } else if(type === 'group'){
            // //模拟系统消息
            // layim.getMessage({
            //     system: true
            //     ,id: res.data.id
            //     ,type: "group"
            //     ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
            // });
        }
    });
    function connect() {
        var socket = new SockJS('/message/endpoint-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/message/info/'+userNameId, function (result) {
                var message = JSON.parse(result.body);
                layim.getMessage({
                    username: message.username
                    , avatar: message.avatar
                    , id: message.fromId
                    , type: message.type
                    , content: message.content
                });
            });
            stompClient.subscribe('/message/friendStatus',function (result) {

            })
        });
    }

});