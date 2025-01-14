package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component("degradeFlowRuleNacosProvider")
public class DegradeFlowRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {

  @Resource
  private ConfigService configService;
  @Resource
  private Converter<String, List<DegradeRuleEntity>> converter;

  @Override
  public List<DegradeRuleEntity> getRules(String appName) throws Exception {
    String rules = configService.getConfig(appName + NacosConfigUtil.DEGRADE_FLOW_DATA_ID_POSTFIX,
        NacosConfigUtil.GROUP_ID, 3000);
    if (StringUtil.isEmpty(rules)) {
      return new ArrayList<>();
    }
    return converter.convert(rules);
  }
}