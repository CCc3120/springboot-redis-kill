package com.bingo.redisKill.service.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bingo.redisKill.dao.IFruitDao;
import com.bingo.redisKill.model.Fruit;
import com.bingo.redisKill.service.IFruitService;


@Service
@Transactional
public class FruitServiceImpl implements IFruitService {
	static int count = 0;

	@Autowired
	private IFruitDao fruitDao;

	@Override
	public Fruit save(Fruit fruit) {
		return fruitDao.save(fruit);
	}

	@Override
	public Fruit findByFdName(String fdName) {
		return fruitDao.findByFdName(fdName);
	}

	@Override
	public Fruit findFirstByOrderByFdNum() {
		return fruitDao.findFirstByOrderByFdNum();
	}

	@Override
	public Fruit findTopByOrderByFdNumDesc() {
		return fruitDao.findTopByOrderByFdNumDesc();
	}

	@Override
	public Fruit findByFdNameOrFdNum(String fdName, Integer fdNum) {
		return fruitDao.findByFdNameOrFdNum(fdName, fdNum);
	}

	@Override
	public Fruit findByFdNameAndFdNum(String fdName, Integer fdNum) {
		return fruitDao.findByFdNameAndFdNum(fdName, fdNum);
	}

	@Override
	public List<Fruit> findByFdNameLike(String fdName) {
		return fruitDao.findByFdNameLike(fdName);
	}

	@Override
	public Long deleteByFdId(String fdId) {
		return fruitDao.deleteByFdId(fdId);
	}

	@Override
	public Long countByFdName(String fdName) {
		return fruitDao.countByFdName(fdName);
	}

	@Override
	public List<Fruit> findByFdNameContaining(String fdName) {
		return fruitDao.findByFdNameContaining(fdName);
	}

	@Override
	public Fruit findByPrimarykey(String fdId) {
		return fruitDao.getOne(fdId);
	}

	@Override
	public Fruit update(Fruit fruit) {
		System.err.println(++count);
		return fruitDao.save(fruit);
	}

}
