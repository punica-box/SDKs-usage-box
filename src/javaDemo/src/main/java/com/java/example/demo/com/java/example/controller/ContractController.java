package com.java.example.demo.com.java.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.java.example.demo.com.java.example.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author king
 * @version 1.0
 * @date 2018.11.28
 */
@AutoConfigurationPackage
@RestController
@RequestMapping(value = "", produces = "application/json")//Contract
public class ContractController {
    private static Logger logger = LoggerFactory.getLogger(ContractController.class);

    /**
     * allTest
     */
    @RequestMapping(value = "/allTest", method = RequestMethod.GET)
    public String allTest() throws Exception {
        logger.info("ContractController allTest");
        StringBuffer stringBuffer = new StringBuffer();
        appendString(stringBuffer, "1. Test Function name start -------");
        appendString(stringBuffer, ContractService.nameTest());
        appendString(stringBuffer, "2. Test Function hello start -------");
        appendString(stringBuffer, ContractService.helloTest());
        appendString(stringBuffer, "3. Test Function testHello start -------");
        appendString(stringBuffer, ContractService.testHelloTest());
        appendString(stringBuffer, "4. Test Function testList start -------");
        appendString(stringBuffer, ContractService.testListTest());
        appendString(stringBuffer, "5. Test Function testListAndStr start -------");
        appendString(stringBuffer, ContractService.testListAndStrTest());
        appendString(stringBuffer, "6. Test Function testStructList start -------");
        appendString(stringBuffer, ContractService.testStructListTest());
        appendString(stringBuffer, "7. Test Function testStructListAndStr start -------");
        appendString(stringBuffer, ContractService.testStructListAndStrTest());
        appendString(stringBuffer, "8. Test Function testMap start -------");
        appendString(stringBuffer, ContractService.testMapTest());
        appendString(stringBuffer, "9. Test Function testGetMap start -------");
        appendString(stringBuffer, ContractService.testGetMapTest());
        appendString(stringBuffer, "10. Test Function testMapInMap start -------");
        appendString(stringBuffer, ContractService.testMapInMapTest());
        appendString(stringBuffer, "11. Test Function testMapInMap start -------");
        appendString(stringBuffer, ContractService.testGetMapInMapTest());
        appendString(stringBuffer, "12. Test Function testTransferMulti start -------");
        appendString(stringBuffer, ContractService.testTransferMultiTest());

        return stringBuffer.toString();
    }

    @RequestMapping(value = "/blockchain/v1/common/test-onto-login", method = RequestMethod.POST)
    public Object testLogin(@RequestBody Map reqObj) throws Exception {
        logger.info("RequestParam: " + JSON.toJSONString(reqObj));
        Result result = new Result();
        try {
            result.error = 0;
            result.desc = "SUCCESS";
            result.version = "v1.0.0";

            if (!reqObj.get("action").equals("login")) {
                result.error = 1;
                result.desc = "Action not found";
                return result;
            }
            result.action = (String) reqObj.get("action");
            result.result = false;
            Map param = (Map)reqObj.get("params");
            String user = (String)param.get("user");
            String message = (String)param.get("message");
            String publickey = (String)param.get("publickey");
            String signature = (String)param.get("signature");
            com.github.ontio.account.Account acct0 = new com.github.ontio.account.Account(false, Helper.hexToBytes(publickey));
            boolean b = acct0.verifySignature(message.getBytes(), Helper.hexToBytes(signature));
            String address = Address.addressFromPubKey(publickey).toBase58();
            if(!address.equals(user.replace("did:ont:",""))){
                result.error = 1;
                result.desc = "user error";
                return result;
            }
            if(b){
                result.result = true;
            }else {
                result.result = false;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.error = 1;
            result.desc = e.getMessage();
        }
        return reqObj;
    }

    @RequestMapping(value = "/qrcode/{req}", method = RequestMethod.GET)
    public Object testInvoke(@PathVariable("req")  String req) throws Exception {
        logger.info("RequestParam: " + req);
        Result result = new Result();
        try {
            String addr = req;
            String str = "{\"action\":\"invoke\",\"params\":{\"login\":true,\"message\":\"invoke smart contract test\",\"invokeConfig\":{\"contractHash\":\"cd948340ffcf11d4f5494140c93885583110f3e9\",\"functions\":[{\"operation\":\"transferNativeAsset\",\"args\":[{\"name\":\"arg0\",\"value\":\"String:ont\"},{\"name\":\"arg1\",\"value\":\"Address:"+addr+"\"},{\"name\":\"arg2\",\"value\":\"Address:AecaeSEBkt5GcBCxwz1F41TvdjX3dnKBkJ\"},{\"name\":\"arg3\",\"value\":10}]}],\"gasLimit\":30000,\"gasPrice\":500,\"payer\":\""+addr+"\"}}}";
            return JSON.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    @RequestMapping(value = "/invoke/callback", method = RequestMethod.POST)
    public Object testInvokeCallback(@RequestBody Map reqObj) throws Exception {
        logger.info("RequestParam: " + JSON.toJSONString(reqObj));
        Result result = new Result();
        result.error = 0;
        result.desc = "SUCCESS";
        result.version = "v1.0.0";
        result.action = "invokeResult";
        result.result = true;
        return result;
    }
    @RequestMapping(value = "/dapps", method = RequestMethod.GET)
    public Object testDapps() throws Exception {
        logger.info("RequestParam: " );
        Result result = new Result();
        result.error = 0;
        result.desc = "SUCCESS";
        result.version = "v1.0.0";
        result.action = "apps";
        try {
            String str = "{\"apps\":[{\"name\":\"demo\",\"icon\":\"https://raw.githubusercontent.com/ontio-community/bounty-program-report/master/image/sc-vscode-exten.png\",\"link\":\"http://101.132.193.149:5000/#/\"},{\"name\":\"luckymoon\",\"icon\":\"http://101.132.193.149/imgs/luckymoon.png\",\"link\":\"http://47.88.219.8/\"},{\"name\":\"luckymoon2\",\"icon\":\"http://101.132.193.149/imgs/luckymoon.png\",\"link\":\"https://luckymoon.io/\"},{\"name\":\"bounty\",\"icon\":\"https://bounty.ont.io/img/bounty_idea.4a0b69a0.png\",\"link\":\"https://bounty.ont.io/\"},{\"name\":\"punica\",\"icon\":\"https://developer.ont.io/img/Punica@2x.c53868c2.png\",\"link\":\"https://punica.ont.io\"},{\"name\":\"ont pass\",\"icon\":\"https://developer.ont.io/img/Pass@2x.e0f854e9.png\",\"link\":\"https://developer.ont.io/ontpass/introduction\"},{\"name\":\"dAPI\",\"icon\":\"https://developer.ont.io/img/dAPI@2x.ebb92178.png\",\"link\":\"https://dev-docs.ont.io/\"}],\"banner\":[{\"name\":\"banner 1\",\"image\":\"https://dapp.ont.io/img/dapp-dragon-big.e61c6f67.jpeg\",\"link\":\"https://dapp.ont.io/\"},{\"name\":\"banner 2\",\"image\":\"http://api.onion.fun:8080/onion/upload/images/2018/11/19/2c5898773af74615a2fa36b370ff98a9.png\",\"link\":\"http://pay.onion.fun/#/\"}]}";
            result.result = JSON.parse(str);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * deployContractTest
     */
    @RequestMapping(value = "/deployContractTest", method = RequestMethod.GET)
    public String deployContractTest() throws Exception {
        logger.info("ContractController deployContractTest");
        return ContractService.deployContractTest();
    }

    /**
     * helloTest
     */
    @RequestMapping(value = "/nameTest", method = RequestMethod.GET)
    public String nameTest() throws Exception {
        logger.info("ContractController nameTest");
        return ContractService.nameTest();
    }

    /**
     * helloTest
     */
    @RequestMapping(value = "/helloTest", method = RequestMethod.GET)
    public String helloTest() throws Exception {
        logger.info("ContractController helloTest");
        return ContractService.helloTest();
    }

    /**
     * testHelloTest
     */
    @RequestMapping(value = "/testHelloTest", method = RequestMethod.GET)
    public String testHelloTest() throws Exception {
        logger.info("ContractController testHelloTest");
        return ContractService.testHelloTest();
    }

    /**
     * testNumListTest
     */
    @RequestMapping(value = "/testNumListTest", method = RequestMethod.GET)
    public String testNumListTest() throws Exception {
        logger.info("ContractController testNumListTest");
        return ContractService.testListTest();
    }

    /**
     * testNumListAndStrTest
     */
    @RequestMapping(value = "/testNumListAndStrTest", method = RequestMethod.GET)
    public String testNumListAndStrTest() throws Exception {
        logger.info("ContractController testNumListAndStrTest");
        return ContractService.testListAndStrTest();
    }

    /**
     * testStructListTest
     */
    @RequestMapping(value = "/testStructListTest", method = RequestMethod.GET)
    public String testStructListTest() throws Exception {
        logger.info("ContractController testStructListTest");
        return ContractService.testStructListTest();
    }

    /**
     * testStructListAndStrTest
     */
    @RequestMapping(value = "/testStructListAndStrTest", method = RequestMethod.GET)
    public String testStructListAndStrTest() throws Exception {
        logger.info("ContractController testStructListAndStrTest");
        return ContractService.testStructListAndStrTest();
    }

    /**
     * testMapTest
     */
    @RequestMapping(value = "/testMapTest", method = RequestMethod.GET)
    public String testMapTest() throws Exception {
        logger.info("ContractController testMapTest");
        return ContractService.testMapTest();
    }

    /**
     * testGetMapTest
     */
    @RequestMapping(value = "/testGetMapTest", method = RequestMethod.GET)
    public String testGetMapTest() throws Exception {
        logger.info("ContractController testGetMapTest");
        return ContractService.testGetMapTest();
    }

    /**
     * testMapInMapTest
     */
    @RequestMapping(value = "/testMapInMapTest", method = RequestMethod.GET)
    public String testMapInMapTest() throws Exception {
        logger.info("ContractController testMapInMapTest");
        return ContractService.testMapInMapTest();
    }

    /**
     * testGetMapInMapTest
     */
    @RequestMapping(value = "/testGetMapInMapTest", method = RequestMethod.GET)
    public String testGetMapInMapTest() throws Exception {
        logger.info("ContractController testGetMapInMapTest");
        return ContractService.testGetMapInMapTest();
    }

    /**
     * testTransferMultiTest
     */
    @RequestMapping(value = "/testTransferMultiTest", method = RequestMethod.GET)
    public String testTransferMultiTest() throws Exception {
        logger.info("ContractController testTransferMultiTest");
        return ContractService.testTransferMultiTest();
    }

    private void appendString(StringBuffer stringBuffer, String string) {
        stringBuffer.append(string).append("\r\n");
    }
}

class Result {
    public String action;
    public long error;
    public String desc;
    public Object result;
    public String version;
}