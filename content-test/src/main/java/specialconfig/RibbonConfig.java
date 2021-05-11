package specialconfig;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    /**
     * 配置随机请求规则
     * Ribbon细粒度的自定义配置
     *
     * @return
     */
    @Bean
    // 等价于配置<bean id="ribbonRule" class="xx.xx.RandomRule"></bean>
    public IRule ribbonRule() {
        return new RandomRule();
    }

    /**
     * 配置IPing的规则
     * Ribbon细粒度的自定义配置
     *
     * @return
     */
    @Bean
    public IPing ping() {
        return new PingUrl();
    }

    /**
     * @Configuration一种特殊的@Component注解
     * 而@Component里面包含扫描配置
     * 而Ribbon上下文的扫描不能和spring boot主上下文扫描重叠，会引发各种奇怪的问题
     * 父子上下文扫描重叠的问题，会导致事务不生效，是一个经典的spring问题
     */
}
