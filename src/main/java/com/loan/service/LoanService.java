package com.loan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.loan.dao.LoanDao;
import com.loan.dto.ResponseDto;
import com.loan.dto.UserAuthDto;
import com.loan.entity.BankBranchView;

/**
 * @author essakijothi_v
 *
 */
@Service
public class LoanService {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private LoanDao loanDao;
	
	@Autowired
	private Authentication authentication;
	
	@Autowired
	private Environment environment;
	
	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public LoanDao getLoanDao() {
		return loanDao;
	}

	public void setLoanDao(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	/**
	 * @param user
	 * @return
	 */
	public Object authenticate(String user) {
		logger.info("User :: {} trying to aunthenticate", user);
		ResponseDto dto = new ResponseDto(environment.getProperty("dataFormat"));
		int expHours = Integer.parseInt(environment.getProperty("expirationInHours"));
		UserAuthDto authDto = new UserAuthDto();
		authDto.setToken(getAuthentication().getToken(user, expHours));
		authDto.setUser(user);
		dto.setMessage("Add auth.token & auth.user in the further request header. Valid for "+expHours+" hours");
		dto.setStatus("Success");
		dto.setAuth(authDto);
		logger.info("Token for user :: {} generated", user);
		return dto;
	}

	/**
	 * @param token
	 * @param user
	 * @param ifscCode
	 * @return
	 */
	public Object getByIfscCode(String token, String user, String ifscCode) {
		String message = getAuthentication().validateToken(token, user);
		if(message.equals(environment.getProperty("token.successMessage"))){
			String query = "select bankDetails from BankBranchView bankDetails where bankDetails.ifsc = :ifsc";
			Map<String, String> param = new HashMap<String, String>();
			param.put("ifsc", ifscCode);
			BankBranchView bankDetail = (BankBranchView) getLoanDao().getFromDb(query, param, true, 0, 0);
			logger.debug("Returning bank details based on IFSC");
			return bankDetail;
		}
		logger.warn("Unauthorized access. User :: {}, Message :: {}", user, message);
		ResponseDto dto = new ResponseDto(environment.getProperty("dataFormat"));
		dto.setMessage(message);
		dto.setTime(new Date());
		dto.setStatus("500");
		return dto;
	}

	/**
	 * @param token
	 * @param user
	 * @param bankName
	 * @param city
	 * @param offset
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBranchesByBankNameCity(String token, String user, String bankName, String city, int offset, int count) {
		String message = getAuthentication().validateToken(token, user);
		if(message.equals(environment.getProperty("token.successMessage"))){
			String query = "select bankDetails from BankBranchView bankDetails where bankDetails.bankName = :bankName and bankDetails.city = :city";
			Map<String, String> param = new HashMap<String, String>();
			param.put("bankName", bankName);
			param.put("city", city);
			logger.debug("Returning bank details based on bank & city");
			return (List<BankBranchView>) getLoanDao().getFromDb(query, param, false, offset, count);
		}
		logger.warn("Unauthorized access. User :: {}, Message :: {}", user, message);
		ResponseDto dto = new ResponseDto(environment.getProperty("dataFormat"));
		dto.setMessage(message);
		dto.setTime(new Date());
		dto.setStatus("500");
		return dto;
	}

}
