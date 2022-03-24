package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component("degradeFlowRuleNacosPublisher")
public class DegradeFlowRuleNacosPublisher implements
    DynamicRulePublisher<List<DegradeRuleEntity>> {

  @Resource
  private ConfigService configService;
  @Resource
  private Converter<List<DegradeRuleEntity>, String> converter;

  @Override
  public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
    AssertUtil.notEmpty(app, "app name cannot be empty");
    if (rules == null) {
      return;
    }
    configService.publishConfig(app + NacosConfigUtil.DEGRADE_FLOW_DATA_ID_POSTFIX,
        NacosConfigUtil.GROUP_ID, converter.convert(rules));
  }
}