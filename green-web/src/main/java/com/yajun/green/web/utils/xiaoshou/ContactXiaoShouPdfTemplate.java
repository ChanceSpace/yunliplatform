package com.yajun.green.web.utils.xiaoshou;

/**
 * Created by LiuMengKe on 2017/12/18.
 *  销售合同
 */
public class ContactXiaoShouPdfTemplate {

    public static final String FONTPATH = "SIMFANG.TTF";
    public static final int BIAOTISIZE = 20;
    public static final int RULESIZE = 12;
    public static final int BODYSIZE = 12;

    public static  final String LK_CONTACTTITLE = "成都雅骏汽车制造有限公司\n" + "新能源物流车销售合同书\n\n";
    public static  final String CONTACTTITLE = "\n" + "新能源物流车销售合同书\n\n";
    public static  final String JIAYISHANGFANG = "甲方（销售方）：jiafangcompanyname   \n" +
            "法定代表人：jiafangfaren\n" +
            "地址：jiafangaddress   \n" +
            "邮编：jiafangpostcode\n" +
            "\n" +
            "乙方（购买方）：companyname\n" +
            "法定代表人：faren\n" +
            "地址：address\n" +
            "邮编：postcode\n" +
            "\n" +
            "\n";

    public static  final String  YINYAN =  "   根据《中华人民共和国合同法》的规定，甲、乙双方在平等自愿的基础上，经友好协商，就乙方购买甲方新能源物流车的相关事宜签订本合同：\n";

    public static final  String  RULE_1 = "   第一条\t 乙方向甲方购买新能源物流车产品名称、型号、价格、数量如下：\n" ;

    public static final String BODY_1 = "   详细参数见附件";

    public static final  String  RULE_2 = "   第二条 产品质量标准、质量负责条件和期限：\n" ;

    public static final  String BODY_2 ="   2.1．上列产品的质量标准，按照国家相关标准的要求执行，符合出厂检验标准。\n" +
            "    2.2．质量负责条件和期限按照有关“三包”服务的规定执行（详见随车文件）。\n";

    public static final String  RULE_3 ="   第三条 付款方式\n" ;
    public static final String  BODY_3 = "   3.1．车辆购车款仅为车辆自身价格，不包括车辆购置税（依据国家现行政策执行）、车辆保险费、上牌费等其他任何费用。\n" +
            "    3.2．定金支付：乙方于本合同签署当日，向甲方支付salenumber台车辆定金合计￥dingjin元（大写：人民币djrmb元整），已付定金可抵扣购车款。如因乙方原因需解除本合同，定金不予退还。\n" +
            "    3.3．付款方式：全款支付\n" +
            "    乙方在甲方根据本合同约定向其发出开票上牌通知之日起3日内向甲方一次性支付全部购车款￥totalprice元（大写：人民币totalrmb元整）。\n" +
            "    3.4．除非双方另有约定，乙方应按照上述约定将购车款支付至甲方指定的如下帐户。支付日期和金额以甲方帐户显示收到的日期和金额为准。\n";

    public static final String BODY_3_ANJIE = "   3.1．车辆购车款仅为车辆自身价格，不包括车辆购置税（依据国家现行政策执行）、车辆保险费、上牌费等其他任何费用。\n" +
            "    3.2．定金支付：乙方于本合同签署当日，向甲方支付salenumber台车辆定金合计￥dingjin元（大写：人民币djrmb元整），已付定金可抵扣购车款。如因乙方原因需解除本合同，定金不予退还。\n" +
            "    3.3．付款方式：按揭付款\n" +
            "    乙方在甲方根据本合同约定向其发出开票上牌通知之日起3日内支付首付款￥shoufu元（大写：人民币sfrmb元整）；剩余购车款￥weikuan元（大写：人民币wkrmb元整）可以向甲方指定的金融机构借款支付，贷款最长期限为daikuanqixian年。乙方在甲方根据本合同约定向其发出开票上牌通知之日起90日内，乙方未能获得金融机构发放的足额贷款，则乙方应在上述时间内，自行支付全部尚未支付的剩余款项。\n" +
            "    3.4．除非双方另有约定，乙方应按照上述约定将购车款支付至甲方指定的如下帐户。支付日期和金额以甲方帐户显示收到的日期和金额为准。\n" ;

    public static final  String BODY3_YH = "帐户名：!          \n"+
            "开户行：!                                 \n"+
            "帐  号：!                                 \n";

    public static final String ROLE_4 = "   第四条 充电服务\n" ;

    public static final String BODY_4 =
            "   4.1.甲方在乙方指定场地建设交流充电桩zlnumber个和直流充电桩jlnumber个，充电桩的所有权归甲方所有，乙方有优先使用权。充电桩建设的场地、材料费和施工费由乙方承担，如乙方场地搬迁，导致此配套充电设施需搬迁，因此发生的所有费用由乙方全额承担。甲方按乙方要求在乙方指定场地建设好充电桩后，甲方将建设好的充电桩授权委托给乙方进行日常安全管理，因此充电桩主要为满足乙方使用而建设，乙方同意无条件免费对其进行日常维护和安全管理，乙方承诺在充电桩交付使用之日起至报废之日止期间内产生的任何人身伤害和财产损失由其自行承担赔偿责任，甲方不承担任何责任。\n" +
                    "    4.2.乙方同意按照以下第typenumber种方式享受充电服务：\n" +
                    "   （1）甲方为乙方提供5年或累计20万公里（先到为准）充电服务，若乙方车辆在甲方公共充电设施上充电则予以免费，若在乙方指定地点建设的充电设施充电，如果是从乙方场地的专用变压器或配电箱引电，其中发生的充电电费如结算单价（以乙方向供电局交纳的电费发票和为新能源物流车实际充电的度数显示为准）高于人民币1元/度电，甲方按人民币1元/度按月结算给乙方；如果结算单价低于人民币1元/度电，甲方按实际电费单价按月与乙方结算。\n" +
                    "   （2）乙方自行承担充电和补电服务费用。\n";

    public static final String BODY_4_ANJIE =
            "   4.1.甲方在乙方指定场地建设交流充电桩zlnumber个和直流充电桩jlnumber个，充电桩的所有权归甲方所有，乙方有优先使用权。充电桩建设的场地、材料费和施工费由乙方承担，如乙方场地搬迁，导致此配套充电设施需搬迁，因此发生的所有费用由乙方全额承担。甲方按乙方要求在乙方指定场地建设好充电桩后，甲方将建设好的充电桩授权委托给乙方进行日常维护和安全管理，因此充电桩主要为满足乙方使用而建设，乙方同意无条件免费对其进行日常维护和安全管理，乙方承诺在充电桩交付使用之日起至报废之日止期间内产生的任何人身伤害和财产损失由其自行承担赔偿责任，甲方不承担任何责任。\n" +
            "    4.2.乙方同意按照以下第typenumber种方式享受充电服务：\n" +
            "   （1）甲方为乙方提供5年或累计20万公里（先到为准）充电服务，若乙方车辆在甲方公共充电设施上充电则予以免费，若在乙方指定地点建设的充电设施充电，如果是从乙方场地的专用变压器或配电箱引电，其中发生的充电电费如结算单价（以乙方向供电局交纳的电费发票和为新能源物流车实际充电的度数显示为准）高于人民币1元/度电，甲方按人民币1元/度按月结算给乙方；如果结算单价低于人民币1元/度电，甲方按实际电费单价按月与乙方结算。\n" +
            "   （2）乙方自行承担充电和补电服务费用。                             \n";


    public static final String ROLE_5 = "   第五条 交付及验收\n" ;
    public static final String BODY_5 =
           "   5.1.交付时间：甲方承诺于      年     月      日达到新车交付条件，并向乙方发出新车开票上牌通知。\n" +
                   "    5.2．交付地点、方式：甲方指定地点，双方现场查验交付。\n" +
                   "    5.3．验收：\n" +
                   "   （1）车辆交付时，乙方应对所购车辆外观和基本使用功能等进行认真检查、确认，如有异议乙方应当场向甲方提出。\n" +
                   "   （2）验收完成后，双方应共同签署验车交接单，由双方各持一份。\n";

           public static final String BODY_5_ANJIE = "   5.1．交付时间：甲方收到乙方支付的首付款和乙方申请办理按揭贷款，金融机构同意乙方按揭贷款申请并放款后25个工作日内，双方办理合同车辆交付手续。\n" +
                   "    5.2．交付地点、方式：甲方指定地点，双方现场查验交付。\n" +
                   "    5.3．验收：\n" +
                   "   （1）车辆交付时，乙方应对所购车辆外观和基本使用功能等进行认真检查、确认，如有异议乙方应当场向甲方提出。\n" +
                   "   （2）验收完成后，双方应共同签署验车交接单，由双方各持一份。\n";

    public static final String ROLE_6 = "   第六条 提车方式：成都自提，其他市场另行协商。\n" ;

    public static final String ROLE_7 = "   第七条 在国家规定的质量保证期内，乙方在使用车辆的过程中若发现生产质量问题，必须在24小时内通知甲方，甲方将无条件进行调试、修复；若乙方不通知甲方自行维修，所造成的所有责任由乙方承担。\n" ;

    public static final String ROLE_8 = "   第八条 甲方向乙方交付车辆时需同时提供：\n" ;
    public static final String BODY_8 =
            "   8.1．随车文件：汽车的用户服务手册、汽车使用说明书；\n" +
                    "    8.2．随车工具（工具包）；\n" +
                    "    8.3．钥匙3把；\n";
    public static final String ROLE_9 = "   第九条 风险承担\n" ;
    public static final String BODY_9 =
            "   9.1．风险自车辆实际交付之日起从甲方转移至乙方，甲方负责交付前车辆的风险，乙方负责交付后车辆的风险。\n" +
                    "    9.2．车辆交付后，非正常、操作不当、违规违法等使用产生风险均由乙方负责。\n";
    public static final String ROLE_10 = "   第十条 售后服务\n" ;
    public static final String BODY_10 =
            "   乙方在接受车辆以后，必须仔细阅读汽车服务手册和汽车使用说明书，并严格按内容执行，按车辆性能、操作规程及相关法律、法规的规定使用所购车辆。\n" +
                    "    10.1．乙方在新车的质保期内，应按照甲方提供的服务手册，到甲方售后服务部指定地点（底盘或整车服务站）进行保养或维护；超时或放弃保养，质保期提前到期，责任由乙方自行承担。\n" +
                    "    10.2．乙方在使用过程中应按甲方提供的服务手册进行保养和维护；如不按规定进行保养和维护，质保期提前到期，车辆出现质量问题，由乙方自行承担。\n" +
                    "    10.3．甲方向乙方提供的车辆货箱三包期限为12个月，电动部分甲方为乙方免费提供5年正常使用维保，自交车之日起计算。\n" +
                    "    10.4．甲方对乙方购买车辆的电池运行状况进行适时监控，出现异常及时提醒乙方作相应处理。\n" +
                    "    10.5. 甲方向乙方销售的车辆，在质保期内，乙方按本合同约定进行正常操作和维护保养时，甲方向乙方保证其所购车辆每月不低于26天的出勤天数，遇车辆故障，甲方售后工程师经过故障初步分析，认为可以远程指导处理的，适时远程指导处理。如不能远程指导处理的，在成都绕城以内甲方售后工程师2小时内到达现场处理；绕城以外大成都范围内甲方售后工程师4小时内到达现场处理，无法在8小时内（冷藏车2小时内）处理的提供备用车辆。车辆故障若非乙方人员主观故意或错误操作而导致，售后维保费用由甲方承担，否则售后维保费用由乙方自行承担，乙方在使用甲方提供的备用车辆时，造成备用车辆损坏、丢失、违章和他人人身伤害等一切责任由乙方承担。\n" +
                    "    10.6．无偿部分的售后服务期限届满，乙方可继续选择甲方做售后服务，甲方收取500元/月.台的售后服务费，更换零部件的材料及标准工时费用由乙方承担。\n" +
                    "    10.7．乙方使用车辆前应仔细阅读用户使用手册及保修和保养手册等相关资料，如出现以下情况，责任由乙方自行承担:\n" +
                    "    (1)由于人为破坏、或者由于车辆预定用途之外的用途而造成的损坏;\n" +
                    "    (2)未经生产厂商授权的修理企业进行修理、维护、保养而造成的损坏;\n" +
                    "    (3)于未以经生产厂商认可的方式进行改装而造成的损坏或者由于使用非经生产厂商认可的配件而造成的损坏;\n" +
                    "    (4)由于不可抗力造成的损坏等。\n" +
                    "    10.8．乙方应将真实的企业信息和联系人信息（主要包括车管及驾驶员的联络电话、行驶证复印件等及其更新）告知甲方，并同意甲方及生产厂商或其授权的第三方将该个人信息用于客户服务或履行本合同项下或法律规定的义务（如保修、召回等）。乙方应积极、主动地配合完成汽车召回活动。\n";


    public static final String BODY_10_ANJIE = "   乙方在接受车辆以后，必须仔细阅读汽车服务手册和汽车使用说明书，并严格按内容执行，按车辆性能、操作规程及相关法律、法规的规定使用所购车辆。\n" +
            "    10.1．乙方在新车的质保期内，应按照甲方提供的服务手册，到甲方售后服务部指定地点（底盘或整车服务站）进行保养或维护；超时或放弃保养，质保期提前到期，责任由乙方自行承担。\n" +
            "    10.2．乙方在使用过程中应按甲方提供的服务手册进行保养和维护；如不按规定进行保养和维护，车辆出现质量问题，由乙方自行承担。\n" +
            "    10.3．甲方向乙方提供的车辆货箱三包期限为12个月，电动部分甲方为乙方免费提供5年正常使用维保，自交车之日起计算。\n" +
            "    10.4．甲方对乙方购买车辆的电池运行状况进行适时监控，出现异常及时提醒乙方作相应处理。\n" +
            "    10.5. 甲方向乙方销售的车辆，在质保期内，乙方按本合同约定进行正常操作和维护保养的，甲方向乙方保证其所购车辆每月不低于26天的出勤天数，遇车辆故障，甲方售后工程师经过故障初步分析，认为可以远程指导处理的，适时远程指导处理。如不能远程指导处理的，在成都绕城以内甲方售后工程师2小时内到达现场处理；绕城以外大成都范围内甲方售后工程师4小时内到达现场处理，无法在8小时内（冷藏车2小时内）处理的提供备用车辆。车辆故障若非乙方人员主观故意或错误操作而导致，售后维保费用由甲方承担，否则售后维保费用由乙方自行承担。\n" +
            "    10.6．无偿部分的售后服务期限届满，乙方可继续选择甲方做售后服务，甲方收取500元/月.台的售后服务费，更换零部件的材料及标准工时费用由乙方承担。\n" +
            "    10.7．乙方使用车辆前应仔细阅读用户使用手册及保修和保养手册等相关资料，如出现以下情况，责任由乙方自行承担:\n" +
            "    (1)由于人为破坏、或者由于车辆预定用途之外的用途而造成的损坏;\n" +
            "    (2)未经生产厂商授权的修理企业进行修理、维护、保养而造成的损坏;\n" +
            "    (3)于未以经生产厂商认可的方式进行改装而造成的损坏或者由于使用非经生产厂商认可的配件而造成的损坏;\n" +
            "    (4)由于不可抗力造成的损坏等。\n" +
            "    10.8．乙方应将真实的企业信息和联系人信息（主要包括车管及驾驶员的联络电话、行驶证复印件等及其更新）告知甲方，并同意甲方及生产厂商或其授权的第三方将该个人信息用于客户服务或履行本合同项下或法律规定的义务（如保修、召回等）。乙方应积极、主动地配合完成汽车召回活动。\n";

    public static final String ROLE_11 = "   第十一条 违约责任\n" ;
    public static final String BODY_11 =
            "   11.1.甲方违约责任：\n" +
                    "   （1）经道路运输管理部门认可专业检测机构认定所购车辆达不到相应车辆运营标准的，乙方有权无条件要求甲方进行整改并达标；\n" +
                    "   （2）若甲方未能如期向乙方发出新车开票上牌通知，每逾期一日按定金的万分之三向乙方支付违约金。\n" +
                    "    11.2．乙方违约责任：\n" +
                    "   （1）如乙方不能按时支付购车款，则应当按照应付而未付的款额，自迟延支付之日起至实际支付日止，按照每日万分之三的标准向甲方支付违约金。如乙方延迟支付超过30天，则甲方有权解除本合同，甲方有权选择要求乙方按上述标准支付违约金，并不予退还定金和其他预付款项。合同解除后，甲方有权自行处置合同项下出售的车辆。\n" +
                    "   （2）如由于乙方未支付车款给甲方造成的损失超过上述约定违约金和订金数额，甲方有权获得进一步的赔偿。\n";

    public static final String ROLE_11_ANJIE = "   第十一条 有关汽车按揭的约定";
    public static final String BODY_11_ANJIE = "   在办理汽车按揭贷款手续过程中，乙方应切实履行如下义务：\n" +
            "    11.1．乙方自合同签订之日起至金融机构贷款发放之日止，须配合甲方及金融机构的资信调查工作，不得以任何理由推脱；\n" +
            "    11.2．甲方通知乙方办理开票、购买保险和上牌后，乙方须及时安排驾驶员上牌（甲方配合），上牌的同时将车辆办理抵押至贷款金融机构名下。\n" +
            "    11.3．按揭期内，乙方未经金融机构及甲方同意，不得将抵押车辆私自转让或轮候抵押给他人；如私自转让或作实物抵押，乙方承担由此引发的全部法律责任，并向甲方赔偿损失。\n" +
            "    11.4．乙方在按揭期内，须严格履行还款义务，不得以经营状况不良或交通事故等原因而影响还款义务的履行；\n" +
            "    11.5．乙方不得以所购车辆发生质量问题为由，影响还款义务的履行；\n" +
            "    11.6．乙方签订本合同及金融机构贷款合同后，应严格履行，不得私自更改抵押权人，若由此而产生的抵押问题，乙方承担全部责任；\n" +
            "    11.7．办理按揭贷款手续的公证费、抵押费等各项费用均由乙方承担。　　\n" +
            "    11.8．乙方若变更住址及联系电话，必须在变更后三日内通知甲方及贷款金融机构，否则应承担由此而产生的法律责任。\n" +
            "    11.9．如甲方作为保证人对乙方的按揭贷款提供担保，因乙方对贷款金融机构违约给甲方带来的损失（包括但不限于利息、罚息、复利、调查费、律师代理费等相关费用）。由乙方负责向甲方全额赔偿，必要时甲方要求乙方对甲方的担保向甲方提供相应价值的反担保措施。\n";


    public static final String ROLE_12 = "   第十二条 争议及解决办法\n" ;

    public static final String BODY_12 =
            "本合同项下发生的争议，双方应协商，协商不成可由甲方所在地的管辖权人民法院裁决。";


    public static final String ROLE_12_ANJIE = "   第十二条 违约责任";
    public static final String BODY_12_ANJIE = "   12.1.甲方违约责任：\n" +
            "   （1）经道路运输管理部门认可专业检测机构认定所购车辆达不到相应车辆运营标准的，乙方有权无条件要求甲方进行整改并达标；\n" +
            "   （2）甲方承诺于        年      月      日达到新车交付条件，并向乙方发出新车开票上牌通知，若未能如期通知，每逾期一日按定金/首付的万分之三向乙方支付违约金。\n" +
            "    12.2．乙方违约责任：\n" +
            "   （1）如乙方不能按时支付购车款，则应当按照应付而未付的款额，自迟延支付之日起至实际支付日止，按照每日万分之三的标准向甲方支付违约金。如乙方延迟支付超过30天，则甲方有权解除本合同，甲方有权选择要求乙方按上述标准支付违约金，并不予退还定金和其他预付款项。合同解除后，甲方有权自行处置合同项下出售的车辆。\n" +
            "   （2）如由于乙方未支付车款给甲方造成的损失超过上述约定违约金和订金数额，甲方有权获得进一步的赔偿。\n" +
            "   （3）乙方不履行按揭还款义务的，除按按揭贷款合同承担法律责任外，还应赔偿甲方由此而造成的损失（包括但不限于利息、罚息、复利、调查费、律师代理费等相关费用）。\n";



    public static final String ROLE_13 = "   第十三条 合同生效、修改\n" ;
    public static final String BODY_13 =
            "   13.1．甲、乙双方可对本合同内容以书面形式予以增加、细化作为补充条款，但不得违反有关法规及政策规定，不得违反公平原则。\n" +
                    "    13.2．本合同一式肆份，甲方持叁份，乙方持壹份，具有同等法律效力。\n";


    public static final String ROLE_13_ANJIE = "   第十三条 争议及解决办法";
    public static final String BODY_13_ANJIE = "   本合同项下发生的争议，双方应协商，协商不成可由甲方所在地的管辖权人民法院裁决。\n";



    public static final String ROLE_14 = "   第十四条 以上所有条款的内容双方自愿签订。甲方对本合同的条款已向乙方明确解释，乙方表示完全理解并签字认可。本合同为车辆销售的主法律文件，甲方提供的《售后服务承诺书》作为本合同的附件与本合同有同等效力。"
                                         +"\n    附件：《售后服务承诺书》";
    public static final String ROLE_14_ANJIE = "   第十四条 合同生效、修改" ;

    public static final String BODY_14_ANJIE = "   14.1．甲、乙双方可对本合同内容以书面形式予以增加、细化作为补充条款，但不得违反有关法规及政策规定，不得违反公平原则。\n" +
            "14.2．本合同一式肆份，甲方持叁份用于给乙方开票、办理按揭等，乙方持壹份，双方合同具有同等法律效力。\n";

    public static final String ROLE_15_ANJIE = "   第十五条 以上所有条款的内容双方自愿签订。甲方对本合同的条款已向乙方明确解释，乙方表示完全理解并签字认可。本合同为车辆销售的主法律文件，甲方提供的《售后服务承诺书》和甲乙双方与贷款银行签订的三方协议作为本合同的附件与本合同有同等效力";

    public static final String ROLE_16_ANJIE = "   第十六条 其他约定事项：";
    public static final String BODY_16_ANJIE = "   乙方可在甲方指定的保险公司购买车辆保险，必保险种为：交强险、全额车损险、第三者责任险不低于100万、全额盗抢险、自燃险、不计免赔、按乘坐人员每人不低于2万元的乘坐险，保险受益人在车辆抵押期间为贷款金融机构。";

    public static final String LK_JIEWEI = "\n" +
            "甲方：成都雅骏汽车制造有限公司          　乙方：\n" +
            "签约代表：　　　　　　　　　　　　　　　　签约代表：\n" +
            "委托代理人：　　　　　　　　　　　　　　　委托代理人：\n" +
            "开户银行：中信银行成都银河王朝支行        开户银行：\n" +
            "银行帐号：8111 0010 1390 0354 430        银行帐号：\n" +
            "签订时间：     年    月     日            签订时间：    年    月    日\n";

    public static final String JIEWEI = "\n" +
            "甲方：                      　            乙方：\n" +
            "签约代表：　　　　　　　　　　　　　　　　签约代表：\n" +
            "委托代理人：　　　　　　　　　　　　　　　委托代理人：\n" +
            "开户银行：bname       开户银行：\n" +
            "银行帐号：bnumber       银行帐号：\n" +
            "签订时间：     年    月     日            签订时间：    年    月    日\n";




}