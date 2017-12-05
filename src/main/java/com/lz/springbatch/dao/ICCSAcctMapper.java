package com.lz.springbatch.dao;

import java.util.List;

import com.lz.springbatch.domain.CCSAcct;

public interface  ICCSAcctMapper {
	List<CCSAcct> find(String name);
}
