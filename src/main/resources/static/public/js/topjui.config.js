/**
 * 配置文件说明
 * @type {string}
 *
 * ctx: 在本demo中，用于跨域请求远程服务器数据的网址，
 * 在实际应用中，大部分情况下topjui.all.min.js与应用程序在同一个域下，设置为空即可
 *
 * topJUI.config.mainPagePath: 系统主页面路径，不包含域名端口及参数，
 * 如果主页面访问的地址为http://localhost:8080/，则此处填写："/"
 * 如果主页面访问的地址为http://localhost:8080/index.html，则此处填写："/index.html"
 * 如果主页面访问的地址为http://localhost:8080/index.html?param=123，则此处填写："/index.html"
 * 如果主页面访问的地址为http://localhost:8080/topjui/，则此处填写："/topjui/"
 * 如果主页面访问的地址为http://localhost:8080/topjui/index.html?param=123，则此处填写："/topjui/index.html"
 *
 * topJUI.config.authUrl: 权限控制Url，请求后台判断用户是否有权限操作某个窗体或链接，
 * 后台返回true为有权限，false为无权限，不填表示不进行权限控制
 *
 * topJUI.language: 消息提示框的中文提示，可根据情况调整
 *
 */

/* 静态演示中获取contextPath，动态演示非必须 开始 */

var contextPath = "";
var firstPathName = window.location.pathname.split("/")[1];
if (!(firstPathName == "html" || firstPathName == "json" || firstPathName == "topjui")) {
    contextPath = "/" + firstPathName;
}
/* 静态演示中获取contextPath，动态演示非必须 结束 */

var ctx = "";
var myConfig = {
    config: {
        pkName: 'uuid', //数据表主键名，用于批量提交数据时获取主键值
        singleQuotesParam: false, //是否对批量提交表格选中记录的参数值使用单引号，默认为false，true:'123','456'，false:123,456
        datagrid: {
            page: 'page', //提交到后台的显示第几页的数据
            size: 'rows', //提交到后台的每页显示多少条记录
            total: 'total', //后台返回的总记录数参数名
            rows: 'rows' //后台返回的数据行对象参数名
        },
        postJson: false, //提交表单数据时以json或x-www-form-urlencoded格式提交，true为json，false为urlencoded
        appendRefererParam: false, //自动追加来源页地址上的参数值到弹出窗口的href或表格的url上，默认为false不追加
        statusCode: {
            success: 200, //执行成功返回状态码
            failure: 300 //执行失败返回状态码
        }
    },
    language: {
        message: {
            title: {
                operationTips: "操作提示",
                confirmTips: "确认提示"
            },
            msg: {
                success: "操作成功",
                failed: "操作失败",
                error: "未知错误",
                checkSelfGrid: "请先勾选中要操作的数据前的复选框",
                selectSelfGrid: "请先选中要操作的数据",
                selectParentGrid: "请先选中主表中要操作的一条数据",
                permissionDenied: "对不起，你没有操作权限",
                confirmDelete: "你确定要删除所选的数据吗？",
                confirmMsg: "确定要执行该操作吗？"
            }
        },
        edatagrid: {
            destroyMsg: {
                norecord: {
                    title: '操作提示',
                    msg: '没有选中要操作的记录！'
                },
                confirm: {
                    title: '操作确认',
                    msg: '确定要删除选中的记录吗？'
                }
            }
        }
    },
    //https://krmis-xg.zhiguaniot.com
    //l: 'cdcfd299e9df92ef5a5747345e390c72b4557a9da78f5a0217d2ef75cd3f890471018a91591b4fb62657d5713b44890f37f45f03cfb5a23e9243d662eae6bb8d6a7096f92c9f2a8b353ecabea3c1f5aff0201672143827e0cc5809bbf409813e'
    //zhiguaniot.com主域名+2子域名
    l: '9a5f2fd49b3351bfe0a279bd95a1efbc73f2e017383042c8e4e1b4278a17585254774f708e3e9cbdacc703c693cf95738ffb06ff67899879d00c87858c9d4f9a1584579991ef93e580015868b918d1fe7e3079c966c317c880f128c9fdb936695c14a5e396db83663a8c849c21a84ae21257e0df'
    //生产:localhost&10.76.63.194新巨龙
    //l: 'e73c565f08dfb680de98cccaf1d5e4bc37f7e1e8a4b33e14267c1225cc42077da08a62351ca4d8c612ae61e7f7b715215e5efe02ef649183'
    //生产:localhost&10.255.9.51寺河
    //l: 'e73c565f08dfb680de98cccaf1d5e4bc1ca4d8c612ae61e7f7b715215e5efe02ef64918337f7e1e8a4b33e14267c1225cc42077da08a6235365359ec765ba76325e4b1edbc0beb65c5165200'
    //测试:zxr.pvgoo.com&192.168.5.104&localhost
    //l: 'e73c565f08dfb680de98cccaf1d5e4bce50925c107820499844e4ac8debce415e1b7894c1fce8c3c44dbbe3192030f7f619a0f9a3ec6d1541ca4d8c612ae61e7f7b715215e5efe02ef649183'
    //前缀:e73c565f08dfb680de98cccaf1d5e4bc
    //kr.pvgoo.com
    //l: '50e29f361667b8beca789b4eb6e89a1b75e26176dba81d753be7055a0e8c0a896ccba43f7ba91ac3893d41b2cf698cf707b7161f8afe8ad43834b717d225885bc7695e652b0518d76d62dd40964cdc1404962afcbc320e261540150bf05f91869f9ad8756770a0b5fe00da3cba8a88a639ca31977242a8265182891528324666fbf57b474d3ddced6c779325da744e0848b8196e1b06e0709645491f87f29e36a2fb646a9231a6af68f9631d39d089c5'
    //www.zhiguaniot.com
    //l: '10693e16496faff8cd8dc3e13a6ec2d8d469cba80774fe4193d3fd7eacec89b116f61f48852e6f75e696d864fa3b66a7083e2959cdadb555755bcea96b86bcb026f308bd6da0b103bf7bdb9fdf9414c5446049333e211312c5a7de80635fb84c'
}