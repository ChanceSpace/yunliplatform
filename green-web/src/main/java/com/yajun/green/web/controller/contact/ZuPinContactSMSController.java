package com.yajun.green.web.controller.contact;


import org.springframework.stereotype.Controller;

/**
 * Created by LiuMengKe on 2017/8/24.
 */
@Controller
public class ZuPinContactSMSController {

    /*@Autowired
    ShortMessageScheduler shortMessageScheduler;
    @Autowired
    ZuPinContactService zuPinContactService;
    @Value("${sms.default.address}")
    private  String phoneNumbers;
    *//**
     * @Author Liu MengKe
     * @Description: 合同界面销售点击给客户发送短信
     *//*
    @RequestMapping(value = "/contact/sendmessagetocustomer.html")
    public String sendMessageToCustomer(HttpServletRequest request, HttpServletResponse response, ModelMap map){
        String zuPinContactUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "zuPinContactUuid", ""));
        String keyWords = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "keyWords", ""));
        String userUuid = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "userUuid", ""));
        int tichePiCi = ServletRequestUtils.getIntParameter(request, "tiChePiCi", 0);
        List<ZuPinVehicleExecuteDTO> list = zuPinContactService.obtainSpecZuPinContactVehicleExcuteDTO(zuPinContactUuid,String.valueOf(tichePiCi));
        SMSUtils.send(list,5,ZuPinContactSMSController.class,phoneNumbers);
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        String result= "backend/zupincontactdetails.html?zuPinContactUuid="+zuPinContactUuid+"&current="+current+"&keyWords="+keyWords+"&userUuid="+userUuid;
        return "redirect:/"+result;
    }*/


}
