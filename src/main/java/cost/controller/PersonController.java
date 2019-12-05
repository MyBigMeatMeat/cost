package cost.controller;

import cost.constant.Constant;
import cost.model.CostPerson;
import cost.service.PersonService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2019/12/4.
 */
@Controller
@RequestMapping("person")
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /**
     * 获取人物列表
     * @return
     */
    @RequestMapping("getAllList")
    @ResponseBody
    public String getAllList(){
        logger.info(" request person/getAllList ");
        JSONObject result = new JSONObject();
        List<CostPerson> costPersonList = personService.getAllList();
        if(costPersonList == null || costPersonList.size() < 1){
            result.put("code","500");
            result.put("msg","未找到记录！");
        }else{
            result.put("code","0");
            result.put("msg","查询成功！");
            result.put("data", costPersonList);
        }
        return result.toString();
    }

    /**
     * 添加人物信息
     * @return
     */
    @RequestMapping("addPerson")
    @ResponseBody
    public String addPerson(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "capital", required = false) BigDecimal capital,
                            @RequestParam(value = "file", required = false) MultipartFile file){

        logger.info(" request person/addPerson  param:    name:{} | capital:{} | file:{}", name, capital, file);
        JSONObject result = new JSONObject();

        if(StringUtils.isEmpty(name) || capital == null || file == null){
            result.put("code","400");
            result.put("msg","缺少必传参数！");
            return result.toString();
        }

        String filename="";
        String savePath ="";
        String url ="";
        if (file!=null) {
            filename=file.getOriginalFilename();
        }
        if (!StringUtils.isEmpty(filename)) {
            savePath = Constant.PATH + filename;
            url = Constant.URL + filename;
            File webfile=new File(savePath);
            saveFile(webfile, file);
        }else{
            url = null;
        }

        CostPerson costPerson = new CostPerson();
        costPerson.setName(name);
        costPerson.setCapital(capital);
        costPerson.setStatus(1);
        costPerson.setUrl(url);

        costPerson = personService.addPerson(costPerson);
        if(costPerson == null){
            result.put("code","500");
            result.put("msg","添加失败！");
        }else{
            result.put("code","0");
            result.put("msg","添加成功！");
            result.put("data", costPerson);
        }
        return result.toString();
    }

    @RequestMapping("deletePerson")
    @ResponseBody
    public String deletePerson(@RequestParam(value = "name", required = false) Integer id){
        logger.info(" request person/deletePerson  param:    id:{} ", id);
        JSONObject result = new JSONObject();

        if(id == null || id == 0){
            result.put("code","400");
            result.put("msg","缺少必传参数！");
            return result.toString();
        }

        boolean b = personService.deletePerson(id);
        if(b){
            result.put("code","0");
            result.put("msg","删除成功！");
        }else{
            result.put("code","500");
            result.put("msg","删除失败！");
        }
        return result.toString();
    }

    private void saveFile(File tmpFile, MultipartFile file) {
        if(tmpFile!=null&&!file.isEmpty()){
            try {
                FileCopyUtils.copy(file.getBytes(), tmpFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
