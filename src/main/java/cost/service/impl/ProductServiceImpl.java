package cost.service.impl;

import cost.dao.CostProductMapper;
import cost.model.CostProduct;
import cost.model.CostProductExample;
import cost.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/12/6.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private CostProductMapper costProductMapper;


    @Override
    public List<CostProduct> getAllList() {
        CostProductExample costProductExample = new CostProductExample();
        costProductExample.createCriteria().andStatusEqualTo(1);
        costProductExample.setOrderByClause("capital asc ");
        return costProductMapper.selectByExample(costProductExample);
    }

    @Override
    public CostProduct addProduct(CostProduct costProduct) {
        if(costProduct == null){
            return null;
        }
        CostProductExample costProductExample = new CostProductExample();
        costProductExample.createCriteria().andStatusEqualTo(1).andPriceEqualTo(costProduct.getPrice()).andNameEqualTo(costProduct.getName());


        int i = costProductMapper.insert(costProduct);
        if(i == 1){
            List<CostProduct> costProductList = costProductMapper.selectByExample(costProductExample);
            if(costProductList != null && costProductList.size() > 0){
                return costProductList.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        CostProductExample costProductExample = new CostProductExample();
        costProductExample.createCriteria().andIdEqualTo(id);
        List<CostProduct> costProductList = costProductMapper.selectByExample(costProductExample);
        if(costProductList != null && costProductList.size() > 0){
            CostProduct costProduct = costProductList.get(0);
            costProduct.setStatus(0);
            int i = costProductMapper.updateByExample(costProduct, costProductExample);
            if(i == 1){
                logger.info("删除成功 id:{}", id);
                return true;
            }
            logger.info("删除失败 id:{}", id);
        }
        logger.info("未找到id为 {} 的记录", id);
        return false;
    }
}
