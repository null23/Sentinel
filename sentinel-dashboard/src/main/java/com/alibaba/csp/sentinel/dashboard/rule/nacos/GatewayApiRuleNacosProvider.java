package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Component("gatewayApiRuleNacosProvider")
public class GatewayApiRuleNacosProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {

  @Resource
  private ConfigService configService;

  @Resource
  private Converter<String, List<ApiDefinitionEntity>> converter;

  @Override
  public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
    String rules = configService.getConfig(appName + NacosConfigUtil.GATEWAY_API_DATA_ID_POSTFIX,
        NacosConfigUtil.GROUP_ID, 3000);
    if (StringUtil.isEmpty(rules)) {
      return new ArrayList<>();
    }
    return converter.convert(rules);
  }
}
