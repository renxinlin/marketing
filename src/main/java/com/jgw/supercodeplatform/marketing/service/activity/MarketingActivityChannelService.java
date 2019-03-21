package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class MarketingActivityChannelService {

    @Autowired
    private MarketingChannelMapper mapper;


    public RestResult<List<MarketingChannel>> getActivityChannelInfoByeditPage(Long activitySetId) {
        RestResult<List<MarketingChannel>> restResult = new RestResult<List<MarketingChannel>>();
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            restResult.setState(500);
            restResult.setMsg("活动id校验失败");
            return  restResult;
        }
        // 获取中奖规则-奖次信息
        List<MarketingChannel> marketingChannels = mapper.selectByActivitySetId(activitySetId);
        // 转换渠道为树结构
        // 渠道父级编码可以不存在，但渠道编码必须存在
        List<MarketingChannel> treeMarketingChannels = getTree(marketingChannels);
        // 返回
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(treeMarketingChannels);
        return  restResult;
    }

    /**
     * 获取渠道树结构
     * @param marketingChannels
     * @return
     */
    private List<MarketingChannel> getTree(List<MarketingChannel> marketingChannels) {
        // 空数据不做处理
        if(CollectionUtils.isEmpty(marketingChannels)){
            return new LinkedList<>();
        }
        // 根节点
        List<MarketingChannel> rootTree = new LinkedList<>();
        // 剩下待处理节点
        List<MarketingChannel> newDatas = new LinkedList<>();

        for (MarketingChannel marketingChannel : marketingChannels) {
            // customerSuperior 上级编码
            if (StringUtils.isEmpty( marketingChannel.getCustomerSuperior())) {
                rootTree.add(marketingChannel);
            }else{
                newDatas.add(marketingChannel);
            }
        }
        // 递归处理树,结束后rootTree生成父子结构关系
        getSonByFatherWithAllData(rootTree,rootTree,newDatas);
        return rootTree;



    }

    /**
     *
     * @param rootTree 根节点
     * @param currentRoots 当前根节点
     * @param newDatas 待处理数据
     */
    private void getSonByFatherWithAllData(List<MarketingChannel> rootTree, List<MarketingChannel> currentRoots, List<MarketingChannel> newDatas) {

        // 总的未分配数据
        List<MarketingChannel> currentNewDatas = new LinkedList();
        // 当前所有子节点|下次所有根节点
        List<MarketingChannel> newCurrentRoot  = new LinkedList();
        // 当前父节点
        for(MarketingChannel everyRoot :currentRoots){
            List<MarketingChannel> children  = new LinkedList();
            // 当前节点增加子节点
            for(MarketingChannel maybeSon : newDatas){
                if(!StringUtils.isEmpty(everyRoot.getCustomerCode())
                        && everyRoot.getCustomerCode().equals(maybeSon.getCustomerSuperior())){
                    children.add(maybeSon);
                    newCurrentRoot.add(maybeSon);
                }
            }


            // 给当前层绑上找到的子元素
            if (!children.isEmpty()) {
                boolean setSuccess = false;
                for(MarketingChannel isAddChild : rootTree){
                    // 子元素找到要添加的树
                    if(setSuccess){
                        break;
                    }

                    if(!StringUtils.isEmpty(isAddChild.getCustomerCode()) && isAddChild.getCustomerCode().equals(everyRoot.getCustomerCode())){
                        isAddChild.setChildren(children);
                        setSuccess = true;
                        break;
                    }else{
                        // 找到everyRoot ，并设置子节点
                        setChildWithAllTreeAndChild(isAddChild,children,everyRoot,setSuccess  );
                    }
                }
            }
        }

        // 获取新的所有没有处理的数据
        for(MarketingChannel marketingChannel : newDatas){
            if(!newCurrentRoot.contains(marketingChannel)){
                currentNewDatas.add(marketingChannel);
            }
        }
        if(currentNewDatas == null || currentNewDatas.isEmpty()){
            return;
        }
        getSonByFatherWithAllData(rootTree,newCurrentRoot,currentNewDatas);




    }

    /**
     * 递归添加子元素
     * @param isAddChild
     * @param children
     * @param everyRoot
     * @param setSuccess
     */
    private void setChildWithAllTreeAndChild(MarketingChannel isAddChild, List<MarketingChannel> children, MarketingChannel everyRoot, boolean setSuccess) {
        List<MarketingChannel> sons = isAddChild.getChildren();
        if(sons == null || sons.isEmpty()){
            return;
        }

        for (MarketingChannel isAddChildSon: sons ) {
            if (setSuccess){
                break;
            }
            if(!StringUtils.isEmpty(isAddChildSon.getCustomerCode()) && isAddChildSon.getCustomerCode().equals(everyRoot.getCustomerCode())){
                isAddChildSon.setChildren(children);
                setSuccess  =true;

            }else{
                setChildWithAllTreeAndChild(isAddChildSon,children,everyRoot,setSuccess);
            }

        }

    }
}
