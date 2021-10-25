package springweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import springweb.service.StorageService;

@Controller
public class DemoController {
	@Autowired
	private StorageService service;
	
	@RequestMapping(value = "/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
				return modelAndView;
	}
	
	@RequestMapping(value = "/download")
	public ResponseEntity<ByteArrayResource> downloadFiile(@RequestParam (value="filename",required = false, defaultValue="DevOps-Yogesh-Resume.pdf")String filename){
		byte[] data=service.downloadFile(filename);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length)
				.header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                .body(resource);
		
	}

	
}