package com.minky.spring.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexacro.xapi.data.DataSet;
import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.data.datatype.PlatformDataType;
import com.nexacro.xapi.tx.HttpPlatformResponse;
import com.nexacro.xapi.tx.PlatformException;

@Controller
public class NexacroController {

	@RequestMapping(value="/nexacro/service", method = RequestMethod.POST)
	public void login(HttpServletResponse response) throws PlatformException {
		sendResponse("authenticatedService", response);
	}
	
	@RequestMapping(value="/nexacro/anonymous", method = RequestMethod.POST)
	public void anonymous(HttpServletResponse response) throws PlatformException {
		sendResponse("anonymousService", response);
	}
	
	public void sendResponse(String msg, HttpServletResponse response) throws PlatformException {
		HttpPlatformResponse platformResponse =  new HttpPlatformResponse(response);
		
		PlatformData platformData = new PlatformData();
		DataSet ds = new DataSet("dsResult");
		ds.addColumn("result", PlatformDataType.STRING);
		ds.newRow();
		ds.set(0, "result", msg);
		platformData.addDataSet(ds);
		
		platformResponse.setData(platformData);
		platformResponse.sendData();
	}
	
	
}
