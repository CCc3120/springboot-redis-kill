package com.bingo.redisKill.util;

import java.util.Date;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Demo {

	public static void main(String[] q) {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://8.129.145.182:6379");
		RedissonClient client = Redisson.create(config);

		RBloomFilter<String> bloomFilter = client.getBloomFilter("user");
		// 尝试初始化，预计元素55000000，期望误判率0.03
		// bloomFilter.tryInit(55000000L, 0.03);
		// 添加元素到布隆过滤器中
		// bloomFilter.add("tom");
		// bloomFilter.add("mike");
		// bloomFilter.add("rose");
		// bloomFilter.add("blue");
		System.out.println("布隆过滤器元素总数为：" + bloomFilter.count());// 布隆过滤器元素总数为：4
		System.out.println("是否包含tom：" + bloomFilter.contains("tom"));// 是否包含tom：true
		System.out.println("是否包含lei：" + bloomFilter.contains("lei"));// 是否包含lei：false
		client.shutdown();
	}

	public void date() {
		Date date = new Date();
		/* 日期转换符 */
		System.out.println("当月的第几天（1-31）：" + String.format("%te", date));
		System.out.println("月份简称：" + String.format("%tb", date));
		System.out.println("月份全称：" + String.format("%tB", date));
		System.out.println("星期简称：" + String.format("%ta", date));
		System.out.println("星期全称：" + String.format("%tA", date));
		System.out.println("日期时间全部信息：" + String.format("%tc", date));
		System.out.println("4位年份：" + String.format("%tY", date));
		System.out.println("2位年份：" + String.format("%ty", date));
		System.out.println("一年中的第几天：" + String.format("%tj", date));
		System.out.println("月份：" + String.format("%tm", date));
		System.out.println("当月的第几天（01-31）：" + String.format("%td", date));
		/* 时间格式化转换符 */
		System.out.println("24时制小时（00-23）：" + String.format("%tH", date));
		System.out.println("12时制小时（01-12）：" + String.format("%tI", date));
		System.out.println("24时制小时（00-23）：" + String.format("%tk", date));
		System.out.println("12时制小时（1-12）：" + String.format("%tl", date));
		System.out.println("分钟（00-59）：" + String.format("%tM", date));
		System.out.println("秒数（00-60）：" + String.format("%tS", date));
		System.out.println("毫秒数（000-999）：" + String.format("%tL", date));
		System.out.println("微秒数（000000000-999999999）：" + String.format("%tN", date));
		System.out.println("上午下午标记：" + String.format("%tp", date));
		System.out.println("相对于GMTRFC82格式的数字时区偏移量：" + String.format("%tz", date));
		System.out.println("时区缩写：" + String.format("%tZ", date));
		System.out.println("1970-01-01 00:00:00至今的秒数：" + String.format("%ts", date));
		System.out.println("1970-01-01 00:00:00至今的毫秒数：" + String.format("%tQ", date));
		/* 日期和时间格式组合 */
		System.out.println("yyyy-MM-dd：" + String.format("%tF", date));
		System.out.println("MM/dd/yy：" + String.format("%tD", date));
		System.out.println("日期时间全部信息：" + String.format("%tc", date));
		System.out.println("hh:mm:ss （上午/下午）12小时制：" + String.format("%tr", date));
		System.out.println("hh:mm:ss 24小时制：" + String.format("%tT", date));
		System.out.println("hh:mm 24小时制：" + String.format("%tR", date));
	}

	public void stack() {
		// Stack<TreeNode> stack = new Stack<TreeNode>();
		// TreeNode item = null;
		// stack.push(item);
		// System.out.println(stack.isEmpty());
		// Fruit a = new Fruit();
		// Fruit b = new Fruit();
		// System.out.println(a.hashCode());
		// System.out.println(b.hashCode());
		// System.out.println(a.equals(b));
		// {
		// int c = 1;
		// System.out.println(a);
		// System.out.println(c);
		// }
	}

	// public void preOrderTraverse1(TreeNode root) {
	// if (root != null) {
	// System.out.print(root.val + " ");
	// preOrderTraverse1(root.left);
	// preOrderTraverse1(root.right);
	// }
	// }
	//
	// public void inOrderTraverse1(TreeNode root) {
	// if (root != null) {
	// inOrderTraverse1(root.left);
	// System.out.print(root.val+" ");
	// inOrderTraverse1(root.right);
	// }
	// }
	//
	// public void postOrderTraverse1(TreeNode root) {
	// if (root != null) {
	// postOrderTraverse1(root.left);
	// postOrderTraverse1(root.right);
	// System.out.print(root.val+" ");
	// }
	// }
}
