package mall.web;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
public class ExceptionController {

	Logger logger=Logger.getLogger(ExceptionController.class);
	
	  
	  @ExceptionHandler(RuntimeException.class)  
	    public String runtimeExceptionHandler(HttpServletRequest request,RuntimeException runtimeException) {  
	      
		   logger.info(runtimeException.getLocalizedMessage());  
	      
	       request.setAttribute("message", runtimeException.getMessage());
	          
	       return "/WEB-INF/pages/error/error";
	        
	    }  
}
