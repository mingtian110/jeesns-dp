package com.luntan.deppon.web.front;

import com.alibaba.fastjson.JSONObject;
import com.luntan.deppon.core.dto.EditorModel;
import com.luntan.deppon.model.common.Picture;
import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.dto.ResponseModel;
import com.luntan.deppon.service.common.IPictureService;
import com.luntan.deppon.service.member.IMemberService;
import com.luntan.deppon.core.utils.Const;
import com.luntan.deppon.core.utils.ImageUtil;
import com.luntan.deppon.core.utils.StringUtils;
import com.luntan.deppon.web.common.BaseController;
import com.luntan.deppon.model.member.Member;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传
 * Created by cubc-luntan 16/9/29.
 */
@Controller
@RequestMapping("/")
public class UploadController extends BaseController {
	@Resource
	private IMemberService memberService;
	@Resource
	private IPictureService pictureService;

	@RequestMapping("${managePath}/uploadImage")
	@ResponseBody
	public Object manageUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
		return uploadImage(file, 0);
	}


	/**
	 * 微博图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/weiboUploadImage")
	@ResponseBody
	public Object weiboUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
		return uploadImage(file, 3);
	}

	/**
	 * 普通图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Object indexUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
		return uploadImage(file, 0);
	}
	@RequestMapping(value = "/uploadWangEditorImage",method = RequestMethod.POST)
	@ResponseBody
	public Object uploadWangEditorImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
			ResponseModel o = (ResponseModel) uploadImage(file, 0);
			EditorModel e = new EditorModel();
			e.setErrno("0");
			List<String> list = new ArrayList<>();
			String uploadCubcPath =(String) request.getServletContext().getAttribute("uploadCubcPath");
			list.add(uploadCubcPath + o.getUrl());
			e.setData(list);
			return e;
	}

	/**
	 * 缩略图上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/thumbnailUploadImage")
	@ResponseBody
	public Object thumbnailUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
		return uploadImage(file, 11);
	}
	@RequestMapping("/requestImage")
	public void UploadImage(String path, HttpServletResponse response){
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			File file = new File(path);
			FileInputStream fips = new FileInputStream(file);
			byte[] btImg = readStream(fips);
			os.write(btImg);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取管道中的流数据
	 */
	public byte[] readStream(InputStream inStream) {
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		int data = -1;
		try {
			while((data = inStream.read()) != -1){
				bops.write(data);
			}
			return bops.toByteArray();
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 保存图片
	 * @param file
	 * @param type 0是普通图片，1是文章图片，2是群组帖子图片，3是微博图片，11是缩略图
	 * @return
	 */
	private Object uploadImage(MultipartFile file, int type) {
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		if(suffix == null || (!".png".equals(suffix.toLowerCase()) && !".jpg".equals(suffix.toLowerCase()) && !".gif".equals(suffix.toLowerCase()) && !".jpeg".equals(suffix.toLowerCase()) && !".bmp".equals(suffix.toLowerCase()))) {
			return new ResponseModel(-1,"格式不支持");
		}
		String newFileName = UUID.randomUUID() + suffix;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		String path = Const.UPLOAD_PATH + "/images/"+ymd+"/";
		String  uploadCubcPathSub = (String)request.getServletContext().getAttribute("uploadCubcPathSub");
		String savePath = uploadCubcPathSub+path;//request.getServletContext().getRealPath(path);
		try {
			Runtime.getRuntime().exec("chmod 777 " + savePath);
		}catch (Exception e){

		}
		File baseFile = new File(savePath);
		try {
			Runtime.getRuntime().exec("chmod 777 " + savePath+"/"+newFileName);
		}catch (Exception e){

		}
		File targetFile = new File(baseFile, newFileName);

		if (!baseFile.exists()) {
			baseFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//上传成功的图片URL，如果是文章、群组帖子、微博图片，则返回图片ID
		String url = "";
		if(type == 3){
			try {
				BufferedImage sourceImg = ImageIO.read(new FileInputStream(targetFile));
				Picture picture = new Picture();
				picture.setWidth(sourceImg.getWidth());
				picture.setHeight(sourceImg.getHeight());
				picture.setMd5(DigestUtils.md5Hex(new FileInputStream(targetFile)));
				//生成缩略图
				String thumbnailName = new ImageUtil().dealImage(targetFile);
				//如果缩略图生成失败，则用原图做缩略图
				if(StringUtils.isEmpty(thumbnailName)){
					thumbnailName = newFileName;
				}
				picture.setPath(path + newFileName);
				picture.setThumbnailPath(path + thumbnailName);
				picture.setType(type);
				picture.setForeignId(0);
				pictureService.save(picture);
				url = String.valueOf(picture.getPictureId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type == 11){
			//生成缩略图
			String thumbnailName = new ImageUtil().dealImage(targetFile);
			//删除原文件
			targetFile.delete();
			url = path + thumbnailName;
		}else {
			url = path + newFileName;
		}
		return new ResponseModel(0,"上传成功",url);
	}

	/**
	 * 上传头像
	 * @param file
	 * @return
	 */
	@RequestMapping("member/uploadAvatar")
	@ResponseBody
	public Object uploadAvatar(@RequestParam(value = "__avatar1", required = false) MultipartFile file){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String fileName = UUID.randomUUID()+".jpg";
		String ymd = simpleDateFormat.format(new Date());
		String filePath = Const.UPLOAD_PATH + "/avatar/" + ymd + "/";
		String  uploadCubcPathSub = (String)request.getServletContext().getAttribute("uploadCubcPathSub");
		String savePath = uploadCubcPathSub+filePath;//request.getServletContext().getRealPath(filePath);
		File baseFile = new File(savePath);
		File targetFile = new File(savePath, fileName);

		if (!baseFile.exists()) {
			baseFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Member loginMember = MemberUtil.getLoginMember(request);
		Member findMember = memberService.findById(loginMember.getId());
		Map result = new HashMap();
		if(findMember != null){
			String oldAvatar = findMember.getAvatar();
			findMember.setAvatar(filePath + fileName);
			ResponseModel responseModel = memberService.updateAvatar(findMember,oldAvatar,request);
			if (responseModel.getCode() == 0){
				MemberUtil.setLoginMember(request, findMember);
			}
			result.put("success",responseModel.getCode() == 0);
			result.put("msg",responseModel.getMessage());
		}else {
			result.put("success",true);
			result.put("msg","会员不存在!");
		}
		return result;
	}
}
