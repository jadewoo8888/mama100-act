package com.biostime.config;

import com.biostime.coupon.rpc.RpcFactory;
import com.biostime.transaction.rpc.TransactionRpcFactory;
import com.mama100.common.finagle.FinagleClientRegister;
import com.mama100.merchandise.enums.FromSystem;
import com.mama100.order.rpc.OrderRpcJavaFactory;
import com.mama100.rpc.cust.CustRpcFactory;
import com.mama100.rpc.merchandise.MerchandiseRpcFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

	/**
	 * 商品库RPC注册
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "rpc.zookeeper.sku")
	public FinagleClientRegister skuRegister(){
		return finagleClientRegister();
	}
	@Bean
	public MerchandiseRpcFactory merchandiseRpcFactory(){
		MerchandiseRpcFactory merchandiseRpcFactory = new MerchandiseRpcFactory();
		merchandiseRpcFactory.setRegister(skuRegister());
		merchandiseRpcFactory.setSystemCode(FromSystem.MAMA_MOB);
		return merchandiseRpcFactory;
	}

	/**
	 * 优惠券RPC注册
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "rpc.zookeeper.coupon")
	public FinagleClientRegister couponRegister(){
		return finagleClientRegister();
	}
	@Bean
	public RpcFactory couponRpcFactory(){
		RpcFactory couponRpcFactory = new RpcFactory();
		couponRpcFactory.setFinagleClientRegister(couponRegister());
		return couponRpcFactory;
	}

	/**
	 * 订单RPC注册
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "rpc.zookeeper.order")
	public FinagleClientRegister orderRegister(){
		return finagleClientRegister();
	}
	@Bean
	public OrderRpcJavaFactory orderRpcJavaFactory(){
		OrderRpcJavaFactory factory = new OrderRpcJavaFactory();
		factory.setFinagleClientRegister(orderRegister());
		return factory;
	}

	/**
	 * 积分RPC注册
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "rpc.zookeeper.point")
	public FinagleClientRegister pointRegister(){
		return finagleClientRegister();
	}
	@Bean
	public TransactionRpcFactory transactionRpcFactory(){
		TransactionRpcFactory factory = new TransactionRpcFactory();
		factory.setRegister(pointRegister());
		return factory;
	}

	/**
	 * 会员RPC注册
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "rpc.zookeeper.cust")
	public FinagleClientRegister custRegister(){
		return finagleClientRegister();
	}
	@Bean
	public CustRpcFactory custRpcFactory(){
		CustRpcFactory factory = new CustRpcFactory();
		factory.setFinagleClientRegister(custRegister());
		return factory;
	}
}
