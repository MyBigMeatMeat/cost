package cost.controller;

import cost.constant.Constant;
import cost.model.CostProduct;
import cost.service.ProductService;
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
 * Created by Administrator on 2019/12/6.
 */
@Controller
@RequestMapping("product")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping("getAllList")
    @ResponseBody
    public String getAllList(){
        logger.info(" request product/getAllList ");
        JSONObject result = new JSONObject();
        List<CostProduct> costProductList = productService.getAllList();
        if(costProductList == null || costProductList.size() < 1){
            result.put("code","500");
            result.put("msg","未找到记录");
        }else{
            result.put("code","0");
            result.put("msg","查询成功！");
            result.put("data", costProductList);
        }
        return result.toString();
    }

    /**
     * 添加商品信息
     * @return
     */
    @RequestMapping("addProduct")
    @ResponseBody
    public String addProduct(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "price", required = false) BigDecimal price,
                            @RequestParam(value = "file", required = false) MultipartFile file){

        logger.info(" request product/addProduct  param:    name:{} | price:{} | file:{}", name, price, file);
        JSONObject result = new JSONObject();

        if(StringUtils.isEmpty(name) || price == null || file == null){
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
            url = Constant.URL + filename;
            savePath = Constant.PATH + filename;
            File webfile=new File(savePath);
            saveFile(webfile, file);
        }else{
            url = null;
        }

        CostProduct costProduct = new CostProduct();
        costProduct.setName(name);
        costProduct.setPrice(price);
        costProduct.setStatus(1);
        costProduct.setUrl(url);

        costProduct = productService.addProduct(costProduct);
        if(costProduct == null){
            result.put("code","500");
            result.put("msg","添加失败");
        }else{
            result.put("code","0");
            result.put("msg","添加成功！");
            result.put("data", costProduct);
        }
        return result.toString();
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @RequestMapping("deleteProduct")
    @ResponseBody
    public String deleteProduct(@RequestParam(value = "id", required = false) Integer id){
        logger.info(" request product/deleteProduct  param:    id:{} ", id);
        JSONObject result = new JSONObject();

        if(id == null || id == 0){
            result.put("code","400");
            result.put("msg","缺少必传参数！");
            return result.toString();
        }

        boolean b = productService.deleteProduct(id);
        if(b){
            result.put("code","0");
            result.put("msg","删除成功");
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
