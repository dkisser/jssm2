package org.lf.jssm.action.query;

import org.lf.jssm.service.query.NBCXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 内部查询
 */
@Controller
@RequestMapping("/query/")
public class NBCXController {
	@Autowired
	private NBCXService nbcxService;
	
	@RequestMapping("nbcxUI.do")
	public String nbcxWizard(){
		return "query/nbcx";
	}
}
