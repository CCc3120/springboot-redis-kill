package com.bingo.redisKill.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bingo.redisKill.model.Fruit;
import com.bingo.redisKill.service.IFruitService;
import com.bingo.redisKill.util.RedisLockUtil;

@RestController
@RequestMapping(value = "/redis")
public class FruitController {

	private static final int TIMOUT = 10 * 1000;

	@Autowired
	private IFruitService fruitService;

	@Autowired
	private RedisLockUtil redisLockUtil;

	@RequestMapping(value = "/testlock")
	public String demo(String fdId) {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						kill(fdId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		return "ok";
	}

	@CrossOrigin
	@RequestMapping(value = "/testlocka")
	public String demo1(String fdId) {
		String key = "dec_store_lock_" + fdId;
		long time = System.currentTimeMillis() + TIMOUT;
		try {
			if (!redisLockUtil.tryLock(key, String.valueOf(time))) {
				return "排队人数太多，请稍后再试.";
			}
			Fruit fruit = fruitService.findByPrimarykey(fdId);
			if (fruit.getFdNum() > 0) {
				fruit.setFdNum(fruit.getFdNum() - 1);
				fruitService.update(fruit);
				System.out.println(
						Thread.currentThread().getName() + "抢购成功，剩余" + fruit.getFdNum() + "件>>" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">>>1");
				Thread.sleep(1000);
				return "恭喜您，购买成功！";
			} else {
				System.out.println(Thread.currentThread().getName() + "抢购失败！！！" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">>>1");
				return "对不起，库存不足.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisLockUtil.unlock(key, String.valueOf(time));
		}
		return "0";
	}

	private void kill(String fdId) throws Exception {
		String key = "dec_store_lock_" + fdId;
		long time=0;
		while(true) {
			time = System.currentTimeMillis() + TIMOUT;
			if (redisLockUtil.tryLock(key, String.valueOf(time))) {
				break;
			}
		}
		// while (!redisLockUtil.tryLock(key,
		// String.valueOf(System.currentTimeMillis() + TIMOUT))) {
		// break;
		// }
		pay(fdId);

		redisLockUtil.unlock(key, String.valueOf(time));
	}

	private void pay(String fdId) {
		Fruit fruit = fruitService.findByPrimarykey(fdId);
		if (fruit.getFdNum() > 0) {
			fruit.setFdNum(fruit.getFdNum() - 1);
			fruitService.update(fruit);
			System.out.println(
					Thread.currentThread().getName() + "抢购成功，剩余" + fruit.getFdNum() + "件>>" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">>>1");
		} else {
			System.out.println(Thread.currentThread().getName() + "抢购失败！！！" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ">>>1");
		}
	}

	@RequestMapping(value = "/test")
	public String add() {
		for (int i = 0; i < 10; i++) {
			Fruit fruit = new Fruit();
			fruit.setFdName("苹果" + i);
			fruit.setFdNum(30 + i);
			fruit.setFdPrice(3.2 + i);
			fruitService.save(fruit);
		}
		return "ok";
	}

	@RequestMapping(value = "/testfind")
	public String test() {
		System.out.println("findByFdName==>>" + fruitService.findByFdName("苹果1").toString());
		System.out.println("findFirstByOrderByFdNum==>>" + fruitService.findFirstByOrderByFdNum());
		System.out.println("findTopByOrderByFdNumDesc==>>" + fruitService.findTopByOrderByFdNumDesc());
		System.out.println("findByFdNameOrFdNum==>>" + fruitService.findByFdNameOrFdNum("苹果", 2));
		System.out.println("findByFdNameAndFdNum==>>" + fruitService.findByFdNameAndFdNum("苹果", 30));
		System.out.println("findByFdNameLike==>>");
		fruitService.findByFdNameLike("苹果").forEach(f -> System.out.println(f.toString()));
		System.out.println("findByFdName%Like%==>>");
		fruitService.findByFdNameContaining("苹果").forEach(f -> System.out.println(f.toString()));
		System.out.println("countByFdName==>>" + fruitService.countByFdName("苹果1"));
		System.out.println("deleteByFdId==>>" + fruitService.deleteByFdId("4028820f73b2ec4c0173b2ed20720000"));
		return "ok";
	}

}
