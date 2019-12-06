package cost.dao;

import cost.model.CostProduct;
import cost.model.CostProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CostProductMapper {
    long countByExample(CostProductExample example);

    int deleteByExample(CostProductExample example);

    int insert(CostProduct record);

    int insertSelective(CostProduct record);

    List<CostProduct> selectByExampleWithRowbounds(CostProductExample example, RowBounds rowBounds);

    List<CostProduct> selectByExample(CostProductExample example);

    int updateByExampleSelective(@Param("record") CostProduct record, @Param("example") CostProductExample example);

    int updateByExample(@Param("record") CostProduct record, @Param("example") CostProductExample example);
}