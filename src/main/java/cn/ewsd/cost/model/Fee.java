package cn.ewsd.cost.model;

public class Fee {

    // 提取方式
    public static final String GET_WAY                         = "3001";
    public static final String GET_WAY_SDLR                    = "f.getType.1";
    public static final String GET_WAY_GLWL                    = "f.getType.2";
    public static final String GET_WAY_GLCK                    = "f.getType.3";
    public static final String GET_WAY_GLBM                    = "f.getType.4";
    public static final String GET_WAY_SYLL                    = "f.getType.5";

    // 测算依据
    public static final String CALC_WAY                        = "3002";
    public static final String CALC_WAY_ZQ                     = "f.calcWay.1";	// 周期
    public static final String CALC_WAY_DM                     = "f.calcWay.2";	// 商品煤
    public static final String CALC_WAY_CL                     = "f.calcWay.3";	// 原煤
    public static final String CALC_WAY_YM                     = "f.calcWay.4";	// 进尺

    // 使用分母
    public static final String DENOM_TYPE                      = "3003";
    public static final String DENOM_TYPE_DW                   = "1";
    public static final String DENOM_TYPE_GS                   = "2";
    public static final String DENOM_TYPE_SJ                   = "3";

    // 分管领导
    public static final String LEADER                      	   = "3004";

    // 竞标类别
    public static final String BID_TYPE                    	   = "3005";

    // 采办方式
    public static final String BUY_TYPE                    	   = "3006";

    // 币种
    public static final String CURRENCY                    	   = "3007";

    // 工作类型
    public static final String WORK_TYPE                   	   = "3008";

    // 资金性质
    public static final String FUND_TYPE                   	   = "3009";

    // 结算性质
    public static final String ACC_TYPE                    	   = "3010";

    // 考核分类
    public static final String AWARD_TYPE                      = "3011";

    // 考核类别
    public static final String ASSESS_TYPE                     = "3012";
    public static final String ASSESS_TYPE_JT                  = "1";				// 集体
    public static final String ASSESS_TYPE_GR                  = "2";				// 个人
    public static final String ASSESS_TYPE_FZR                 = "3";				// 负责人

    // 罚款状态
    public static final String AWARD_STATUS                	   = "3013";
    public static final String AWARD_STATUS_CG             	   = "0";
    public static final String AWARD_STATUS_DFP            	   = "1";
    public static final String AWARD_STATUS_JYKZ           	   = "3";
    public static final String AWARD_STATUS_KLD            	   = "5";
    public static final String AWARD_STATUS_DG             	   = "F";

    // 分配类别
    public static final String ASSIGN_TYPE                     = "3014";
    public static final String ASSIGN_TYPE_ZR                  = "1";				// 责任部门
    public static final String ASSIGN_TYPE_ZG                  = "2";				// 主管部门
    public static final String ASSIGN_TYPE_KH                  = "3";				// 考核部门
    public static final String ASSIGN_TYPE_MD                  = "4";				// 调整分配

    // 考核周期
    public static final String ASS_PERIOD                      = "3015";
    public static final String ASS_PERIOD_ND                   = "f.khzq.1";
    public static final String ASS_PERIOD_BN                   = "f.khzq.2";
    public static final String ASS_PERIOD_JD                   = "f.khzq.3";
    public static final String ASS_PERIOD_YD                   = "f.khzq.4";
}
