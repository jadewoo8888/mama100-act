package com.biostime.config;

import com.mama100.common.finagle.FinagleClientRegister;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcConfig {
	/**
	 * 各种rpc公共配置
	 * @return
	 */
	public FinagleClientRegister finagleClientRegister(){
		FinagleClientRegister f = new FinagleClientRegister();
		f.setHostConnectionIdleTime(600000);//<!-- 默认10分钟，这时间内的连接会被保留。 -->
		f.setHostConnectionMaxIdleTime(900000);//<!-- 默认15分钟 -->
		f.setTimeout(60000);//<!-- 默认60秒，超时时间，指整个完整请求的来回时间。 -->
		f.setMaxRetry(3);//<!-- 重试次数，默认3次， -->
		f.setHostConnectionLimit(200);//本客户端向每个host可发出的最大请求数
		f.setHostConnectionCoresize(50);//缓存保留的连接数
		return f;
	}
}
