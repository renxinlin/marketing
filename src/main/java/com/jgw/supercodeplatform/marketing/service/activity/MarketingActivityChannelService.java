package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MarketingActivityChannelService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private MarketingChannelMapper mapper;


    public RestResult<List<MarketingChannel>> getActivityChannelInfoByeditPage(Long activitySetId)   {
        RestResult<List<MarketingChannel>> restResult = new RestResult<>();
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            restResult.setState(500);
            restResult.setMsg("活动id校验失败");
            return  restResult;
        }
        // 获取中奖规则-奖次信息
        List<MarketingChannel> marketingChannels = mapper.selectByActivitySetId(activitySetId);
        // 转换渠道为树结构： 1先获取所有根节点，2在获取所有当前父节点以及子节点，3将子节点添加到父节点
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
    protected List<MarketingChannel> getTree(List<MarketingChannel> marketingChannels)  {
        // 空数据不做处理
        if(CollectionUtils.isEmpty(marketingChannels)){
            return new LinkedList<>();
        }
        // 根节点
        List<MarketingChannel> rootTree = new LinkedList<>();
        // 剩下待处理节点
        List<MarketingChannel> newDatas = new LinkedList<>();


        // 需要把不存在的父级找出来作为root节点，同时根节点本身也要找出
        for( MarketingChannel marketingChannel: marketingChannels ){
            boolean addToRoot = true;
            for(MarketingChannel node: marketingChannels){
                if(!StringUtils.isEmpty( marketingChannel.getCustomerSuperior())){
                   if( marketingChannel.getCustomerSuperior().equals(node.getCustomerId())){
                       addToRoot = false;
                   }
                }else{
                    addToRoot = true;

                }
            }
            // 父节点添加到rootTree,不存在父节点但customerSuperior不为空的添加到rootTree
            if(addToRoot){
                rootTree.add(marketingChannel);
            }else{
                newDatas.add(marketingChannel);
            }
        }


//        for (MarketingChannel marketingChannel : marketingChannels) {
//            // customerSuperior 上级编码
//            if (StringUtils.isEmpty( marketingChannel.getCustomerSuperior())) {
//                rootTree.add(marketingChannel);
//            }else{
//                newDatas.add(marketingChannel);
//            }
//        }



        // 递归处理树,结束后rootTree生成父子结构关系
        getSonByFatherWithAllData(rootTree,rootTree,newDatas);
        return rootTree;



    }

    /**
     * 获取子节点
     * @param rootTree 根节点
     * @param currentRoots 当前根节点
     * @param newDatas 待处理数据
     */
    private void getSonByFatherWithAllData(List<MarketingChannel> rootTree, List<MarketingChannel> currentRoots, List<MarketingChannel> newDatas) {

        // 总的未分配数据
        List<MarketingChannel> currentNewDatas = new LinkedList<>();
        // 当前所有子节点|下次所有根节点
        List<MarketingChannel> newCurrentRoot  = new LinkedList<>();
        // 当前父节点
        for(MarketingChannel everyRoot :currentRoots){
            List<MarketingChannel> children  = new LinkedList<>();
            // 当前节点增加子节点
            for(MarketingChannel maybeSon : newDatas){
                if(!StringUtils.isEmpty(everyRoot.getCustomerId())
                        && everyRoot.getCustomerId().equals(maybeSon.getCustomerSuperior())){
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

                    if(!StringUtils.isEmpty(isAddChild.getCustomerId()) && isAddChild.getCustomerId().equals(everyRoot.getCustomerId())){
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
            if(!StringUtils.isEmpty(isAddChildSon.getCustomerId()) && isAddChildSon.getCustomerId().equals(everyRoot.getCustomerId())){
                isAddChildSon.setChildren(children);
                setSuccess  =true;

            }else{
                setChildWithAllTreeAndChild(isAddChildSon,children,everyRoot,setSuccess);
            }
        }
    }

    /**
     * 判断码对应产品的渠道信息和活动创建对应的渠道信息是否一致
     * @param codeId
     * @param activitySetId
     * @return
     */

    public MarketingChannel checkCodeIdConformChannel(String codeTypeId, String codeId, Long activitySetId) {
        // TODO 先直接返回，等到物流系统和码平台那边功能完成后，再启用
//        Map<String, String> customerMap = commonService.queryCurrentCustomer(codeTypeId, codeId);
//        if (customerMap.isEmpty()){
//            return null;
//        }
//        String customerId = customerMap.get("customerId");
//        List<MarketingChannel> marketChannelList = mapper.selectByCustomerIdAndActivitySetId(customerId, activitySetId);
//        if (CollectionUtils.isEmpty(marketChannelList)){
//            return null;
//        }
//        return marketChannelList.get(0);
        return new MarketingChannel();
    }


}
