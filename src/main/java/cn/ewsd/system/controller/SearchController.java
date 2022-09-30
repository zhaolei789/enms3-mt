package cn.ewsd.system.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("searchController")
@RequestMapping("/system/search")
public class SearchController extends SystemBaseController {


	@RequestMapping("advanceSearch")
	public String advanceSearch() throws Exception {
		return display();
	}

}
