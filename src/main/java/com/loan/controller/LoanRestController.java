package com.loan.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loan.dto.ResponseDto;
import com.loan.service.LoanService;

/**
 * @author essakijothi_v
 *
 */
@RestController
public class LoanRestController {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private LoanService loanService;
	
	public LoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}

	/**
	 * @param user
	 * @return ResponseDto containing user, token
	 * 
	 * Endpoint to generating token with configured expiration time.
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public @ResponseBody Object authenticate(@RequestParam(name="user", required=true)String user){
		logger.debug("######## LoanRestController::authenticate(user :: {})  ########", user);
		return getLoanService().authenticate(user);
	}

	/**
	 * @param token
	 * @param user
	 * @param ifscCode
	 * @return BankBranchView
	 * 
	 * Endpoint to get Bank details for given IFSC code if user is authorized. 
	 * No need of offset & count since IFSC code will always be unique.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getByIfscCode")
	public @ResponseBody Object getByIfscCode(@RequestHeader(name="token", required=true) String token, @RequestHeader(name="user", required=true) String user, @RequestParam(name="code", required=true)String ifscCode){
		logger.debug("######## LoanRestController::getByIfscCode(ifsc :: {})  ########", ifscCode);
		return getLoanService().getByIfscCode(token, user ,ifscCode);
	}

	/**
	 * @param token
	 * @param user
	 * @param bankName
	 * @param city
	 * @param offset
	 * @param count
	 * @return BankBranchView
	 * 
	 * Endpoint to get Bank details for given bank and city if user is authorized.
	 * User can limit number of records to be returned with the help of @param offset & @param count 
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getBranchesByBankNameCity")
	public @ResponseBody Object getBranchesByBankNameCity(@RequestHeader(name="token", required=true) String token, @RequestHeader(name="user", required=true) String user, @RequestParam(name="bankName", required=true)String bankName, @RequestParam(name="city", required=true)String city, @RequestParam(name="offset", defaultValue="0", required=false)int offset, @RequestParam(name="count", defaultValue="0", required=false)int count){
		logger.debug("######## LoanRestController::getBranchesByBankNameCity(bankName :: {}, city :: {})  ########", bankName, city);
		return getLoanService().getBranchesByBankNameCity(token, user ,bankName, city, offset, count);
	}
	
	/**
	 * 
	 * Exception handling for missing parameters/header
	 * 
	 */
	@ExceptionHandler(ServletRequestBindingException.class)
	public ResponseEntity<ResponseDto> handleHeaderError(ServletRequestBindingException ex){
		logger.error("Exception :: missing parameters/header. {}",ex.getMessage());
		ResponseDto responseObject=new ResponseDto();
		responseObject.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		responseObject.setMessage(ex.getMessage());
		logger.info("Response object set");
		ResponseEntity<ResponseDto> responseEntity=new ResponseEntity<ResponseDto>(responseObject, HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
}
