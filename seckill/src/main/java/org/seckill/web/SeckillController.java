package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀操作的控制层
 * @author Lenovo
 *
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;

	/**
	 * 秒杀商品列表展示
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model){
		List<Seckill> list=seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	
	/**
	 * 秒杀商品详情页面
	 * @param seckillId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId==null){
			return "redirect:/seckill/list";
		}
		Seckill seckill=seckillService.getById(seckillId);
		if(seckill==null){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	/**
	 * 秒杀按钮接口暴露
	 * @param seckillId
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/exposer",
			method=RequestMethod.POST,
			produces={"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable Long seckillId){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer=seckillService.exportSeckillUrl(seckillId);
			result=new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result=new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 进行商品秒杀
	 * @param seckillId
	 * @param md5
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/{seckillId}/{md5}/execution",
			method=RequestMethod.POST,
			produces={"application/json;charset=utf-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
			@PathVariable("seckillId")Long seckillId,
			@PathVariable("md5")String md5,
			@CookieValue(value="killPhone",required=false)Long phone){
		if(phone==null){
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		try {
			SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId, phone, md5);
			 return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (SeckillCloseException e1) {
			SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}catch (RepeatKillException e2) {
			SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}
		
	}
	
	/**
	 * 获取服务器统一的秒杀时间点
	 * @return
	 */
	@RequestMapping(value="/time/now",
			method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time(){
		Date now=new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
}
