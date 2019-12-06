package cost.service;

import cost.model.CostProduct;

import java.util.List;

/**
 * Created by Administrator on 2019/12/6.
 */
public interface ProductService {

    /**
     * 获取所有的商品列表
     * @return
     */
    List<CostProduct> getAllList();

    /**
     * 添加商品接口
     * @param costProduct
     * @return
     */
    CostProduct addProduct(CostProduct costProduct);

    /**
     * 删除商品接口
     * @param id
     * @return
     */
    boolean deleteProduct(Integer id);
}
