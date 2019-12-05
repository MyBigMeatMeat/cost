package cost.dao;

import cost.model.CostPerson;
import cost.model.CostPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CostPersonMapper {
    long countByExample(CostPersonExample example);

    int deleteByExample(CostPersonExample example);

    int insert(CostPerson record);

    int insertSelective(CostPerson record);

    List<CostPerson> selectByExampleWithRowbounds(CostPersonExample example, RowBounds rowBounds);

    List<CostPerson> selectByExample(CostPersonExample example);

    int updateByExampleSelective(@Param("record") CostPerson record, @Param("example") CostPersonExample example);

    int updateByExample(@Param("record") CostPerson record, @Param("example") CostPersonExample example);
}