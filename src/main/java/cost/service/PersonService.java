package cost.service;

import cost.model.CostPerson;

import java.util.List;

/**
 * Created by Administrator on 2019/12/4.
 */
public interface PersonService {

    /**
     * 获取人物列表
     * @return
     */
    List<CostPerson> getAllList();

    /**
     * 添加人物
     * @param costPerson
     * @return
     */
    CostPerson addPerson(CostPerson costPerson);

    /**
     * 删除人物
     * @param id
     * @return
     */
    boolean deletePerson(Integer id);
}
