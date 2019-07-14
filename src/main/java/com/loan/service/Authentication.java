package com.loan.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * @author essakijothi_v
 *
 */
@Service
public class Authentication {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private Environment environment;
	
	/**
	 * @param user
	 * @param expirationInHours
	 * @return
	 * 
	 */
	public String getToken(String user, int expirationInHours) {
		logger.info("Generating token. User :: {}", user);
		Algorithm algorithm = Algorithm.HMAC256("secret");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, expirationInHours);
		String token = JWT.create()
				.withIssuer(user)
				.withExpiresAt(cal.getTime())
				.withSubject("Fyle Loan System")
				.sign(algorithm);
		logger.debug("Generated token. Valid for {} hours. User :: {}",expirationInHours , user);
		return token;
	}

	/**
	 * @param token
	 * @param user
	 * @return
	 */
	public String validateToken(String token, String user){
		try{
			logger.info("Validating token.");
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(user)
					.withSubject("Fyle Loan System")
					.build(); 
			DecodedJWT jwt = verifier.verify(token);
			String userJwt = jwt.getIssuer();
			Date exp = jwt.getExpiresAt();
			Date date = new Date();
			if(exp.after(date) && user.equals(userJwt)){
				logger.info("Valid token.");
				return environment.getProperty("token.successMessage");
			}
		}catch(JWTDecodeException e){
			logger.error("Decode Exception. User :: {}",user, e);
		}catch(TokenExpiredException e){
			logger.error("Token expired for user :: {}",user, e);
			return "Token expired. Kindly generate token";
		}catch(InvalidClaimException e){
			logger.error("InvalidClaimException. User :: {}",user, e);
		}catch(Exception e){
			logger.error("Invalid token/user. User :: {}",user, e);
		}
		return "Invalid token/user";
	}

}
