package com.jadyer.engine.quartz;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jadyer.engine.common.base.CommonResult;
import com.jadyer.engine.common.constant.CodeEnum;
import com.jadyer.engine.quartz.model.ScheduleTask;

@Controller
@RequestMapping(value="/quartz/schedule/task")
public class ScheduleTaskController {
	@Resource
	private ScheduleTaskService scheduleTaskService;
	
	/**
	 * http://127.0.0.1:8088/engine/quartz/schedule/task/getByIds?ids=1,2
	 * @see 这里只做演示用
	 */
	@ResponseBody
	@RequestMapping(value="/getByIds")
	public CommonResult getByIds(String ids){
		return new CommonResult(scheduleTaskService.getByIds(ids));
	}
	
	/**
	 * http://127.0.0.1:8088/engine/quartz/schedule/task/update?id=16
	 * @see 这里只做演示用
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public CommonResult update(int id){
		ScheduleTask task = scheduleTaskService.getTaskById(id);
		task.setComment("testComment");
		task.setName("testName");
		ScheduleTask obj = scheduleTaskService.saveTask(task);
		return new CommonResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), obj);
	}


	@RequestMapping(value="/list")
	public String list(HttpServletRequest request){
		request.setAttribute("taskList", scheduleTaskService.getAllTask());
		return "quartzList";
	}


	@ResponseBody
	@RequestMapping(value="/add")
	public CommonResult add(ScheduleTask task, String dynamicPassword){
		if(!this.verifyDynamicPassword(dynamicPassword)){
			return new CommonResult(CodeEnum.SYSTEM_BUSY.getCode(), "动态密码不正确");
		}
		ScheduleTask obj = scheduleTaskService.saveTask(task);
		return new CommonResult(CodeEnum.SUCCESS.getCode(), String.valueOf(obj.getId()));
	}


	@ResponseBody
	@RequestMapping(value="/delete/{id}/{dynamicPassword}")
	public CommonResult delete(@PathVariable int id, @PathVariable String dynamicPassword){
		if(!this.verifyDynamicPassword(dynamicPassword)){
			return new CommonResult(CodeEnum.SYSTEM_BUSY.getCode(), "动态密码不正确");
		}
		scheduleTaskService.deleteTask(id);
		return new CommonResult();
	}


	@ResponseBody
	@RequestMapping(value="/updateStatus")
	public CommonResult updateStatus(int id, String status, String dynamicPassword){
		if(!this.verifyDynamicPassword(dynamicPassword)){
			return new CommonResult(CodeEnum.SYSTEM_BUSY.getCode(), "动态密码不正确");
		}
		if(scheduleTaskService.updateStatus(id, status)){
			return new CommonResult();
		}else{
			return new CommonResult(CodeEnum.SYSTEM_ERROR.getCode(), CodeEnum.SYSTEM_ERROR.getMessage());
		}
	}


	@ResponseBody
	@RequestMapping(value="/updateCron")
	public CommonResult updateCron(int id, String cron, String dynamicPassword){
		if(!this.verifyDynamicPassword(dynamicPassword)){
			return new CommonResult(CodeEnum.SYSTEM_BUSY.getCode(), "动态密码不正确");
		}
		if(scheduleTaskService.updateCron(id, cron)){
			return new CommonResult();
		}else{
			return new CommonResult(CodeEnum.SYSTEM_ERROR.getCode(), CodeEnum.SYSTEM_ERROR.getMessage());
		}
	}


	/**
	 * 立即执行一个QuartzJOB
	 */
	@ResponseBody
	@RequestMapping(value="/triggerJob/{id}/{dynamicPassword}")
	public CommonResult triggerJob(@PathVariable int id, @PathVariable String dynamicPassword){
		if(!this.verifyDynamicPassword(dynamicPassword)){
			return new CommonResult(CodeEnum.SYSTEM_BUSY.getCode(), "动态密码不正确");
		}
		ScheduleTask task = scheduleTaskService.getTaskById(id);
		scheduleTaskService.triggerJob(task);
		return new CommonResult();
	}


	/**
	 * 验证动态密码是否正确
	 * @see 每个动态密码有效期为10分钟
	 * @return 动态密码正确则返回true,反之false
	 */
	private boolean verifyDynamicPassword(String dynamicPassword){
		String timeFlag = DateFormatUtils.format(new Date(), "HHmm").substring(0, 3) + "0";
		String generatePassword = DigestUtils.md5Hex(timeFlag + "http://blog.csdn.net/jadyer" + timeFlag);
		return StringUtils.isNotBlank(dynamicPassword) && generatePassword.equalsIgnoreCase(dynamicPassword);
	}
}