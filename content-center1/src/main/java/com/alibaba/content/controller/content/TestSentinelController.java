package com.alibaba.content.controller.content;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.tools.Trace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/testSentinelController")
public class TestSentinelController {

    @GetMapping("/test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-api", blockHandler = "block", fallback = "fallback")
    public String testSentinelApi(@RequestParam(required = false) String a) {

        if (StringUtils.isEmpty(a)) {
            throw new IllegalArgumentException("a cannot be null!");
        }
        return a;
    }

    /**
     * 处理限流或者降级
     *
     * @param a
     * @param e
     * @return
     */
    public String block(String a, BlockException e) {
        log.info("限流或者降级！", e);
        return "限流或者降级！";
    }

    /**
     * 处理降级
     * sentinel 1.6之后可以处理Throwable
     *
     * @param a
     * @param ex
     * @return
     */
    public String fallback(String a, Throwable ex) {
        log.info("限流或者降级！[fallback]", ex);
        return "限流或者降级！";
    }
}
