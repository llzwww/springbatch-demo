package com.lz.springbatch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.lz.springbatch.domain.CCSAcct;

public class CCSAcctMapper implements FieldSetMapper<CCSAcct> {
	
	public CCSAcct mapFieldSet(FieldSet fs) throws BindException {
		CCSAcct acct = new CCSAcct();
		acct.setOrg(fs.readString(0));
		acct.setAccNbr(fs.readLong(1));
		acct.setCustId(fs.readLong(2));
		acct.setCustLmtId(fs.readLong(3));
		return acct;
	}
	
}
