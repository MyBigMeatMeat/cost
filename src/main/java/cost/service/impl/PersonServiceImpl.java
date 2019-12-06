package cost.service.impl;

import cost.dao.CostPersonMapper;
import cost.model.CostPerson;
import cost.model.CostPersonExample;
import cost.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/12/4.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private CostPersonMapper costPersonMapper;

    @Override
    public List<CostPerson> getAllList() {
        CostPersonExample costPersonExample = new CostPersonExample();
        costPersonExample.createCriteria().andStatusEqualTo(1);
        costPersonExample.setOrderByClause("capital desc ");
        return costPersonMapper.selectByExample(costPersonExample);
    }

    @Override
    public CostPerson addPerson(CostPerson costPerson) {
        if(costPerson == null){
            return null;
        }
        CostPersonExample costPersonExample = new CostPersonExample();
        costPersonExample.createCriteria().andStatusEqualTo(1).andCapitalEqualTo(costPerson.getCapital()).andNameEqualTo(costPerson.getName());


        int i = costPersonMapper.insert(costPerson);
        if(i == 1){
            List<CostPerson> costPersonList = costPersonMapper.selectByExample(costPersonExample);
            if(costPersonList != null && costPersonList.size() > 0){
                return costPersonList.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean deletePerson(Integer id) {
        CostPersonExample costPersonExample = new CostPersonExample();
        costPersonExample.createCriteria().andIdEqualTo(id);
        List<CostPerson> costPersonList = costPersonMapper.selectByExample(costPersonExample);
        if(costPersonList != null && costPersonList.size() > 0){
            CostPerson costPerson = costPersonList.get(0);
            costPerson.setStatus(0);
            int i = costPersonMapper.updateByExample(costPerson, costPersonExample);
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
