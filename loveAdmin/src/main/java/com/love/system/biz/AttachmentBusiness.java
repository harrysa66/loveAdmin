package com.love.system.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.dao.IAttachmentDao;
import com.love.system.po.Attachment;
import com.love.util.UUIDGenerator;

@Service
public class AttachmentBusiness {
	
	@Resource
	private GlobalBusiness globalBusiness;
	
	@Resource
	private IAttachmentDao attachmentDao;
	
	 /**
     * 依据实体上传附件
     * @param att 附件实体
     * @return attachmentObject 返回上传的实体对象
     */
	public Attachment upload(Attachment att){
		Attachment  attachmentObject = new Attachment();
		attachmentObject.setId(UUIDGenerator.getUUID());
		attachmentObject.setUploadTime(new Date());
		if(StringUtils.isNotEmpty(att.getUploadUserId())){
			attachmentObject.setUploadUserId(att.getUploadUserId());
		}
		else{
			attachmentObject.setUploadUserId(SpringSecurityUtils.getCurrentUser().getId());
		}
		if(StringUtils.isNotEmpty(att.getUploadUserName())){
			attachmentObject.setUploadUserName(att.getUploadUserName());				
		}
		else{
			attachmentObject.setUploadUserName(SpringSecurityUtils.getCurrentUserName());		
		}
		if(att.getUrl()==null){//本地上传附件,url置为空
			attachmentObject.setFileName(att.getFileName());
			attachmentObject.setContentType(att.getContentType());
			attachmentObject.setEntityId(att.getEntityId());
			attachmentObject.setEntityType(att.getEntityType());
			attachmentObject = uploadAttachment(attachmentObject,att.getIn());
		}else{
			attachmentObject.setUrl(att.getUrl());
		}
		attachmentDao.insert(attachmentObject);
		return attachmentObject;
	}
	 /**
     * 依据实体修改附件信息
     * @param att 附件实体
     * @return attachmentObject 返回上传的实体对象
     */
	public Attachment updateAttachment(Attachment att){
		Attachment  attachmentObject = new Attachment();
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", att.getId());
		map.put("entityId", att.getEntityId());
		map.put("entityType",att.getEntityType());
		attachmentDao.updateAttachmentById(map);
		return attachmentObject;
	}
	 /**
     * 依据id返回附件对象
     * @param id 附件Id
     * @return Attachment 返回附件
     */
	public Attachment getById(String id) {
		return  attachmentDao.findAttachmentById(id);
	}
	/**依据附件id和类型查找附件路径
	 * @param map [entityId|entityType] entityId:附件id,entityType:实体类型
	 * @return String 返回附件路径
	 */
	public String findPathByEntityId(Map<String,Object> map) {
		Attachment  attachment = attachmentDao.findPathById(map);
		if(attachment==null){
			return null;
		}
		return attachment.getSavePath()+attachment.getSaveName();
	}
	
	/**
	 * 通过Entity ID和Entity Type查找附件
	 * @param entityId entityType
	 * @return
	 */
	public Attachment findAttachmentByEntity(Map<String,Object> map) {
		Attachment  attachment = attachmentDao.findPathById(map);
		return attachment;
	}
	
	/**
	 * 依据附件id删除附件
	 * @param id 附件id
	 * @return
	 */
	
	public void deleteById(String id) {
		 attachmentDao.deleteAttachmentById(id);
	}
	/**
	 * 依据业务实体id返回附件列表
	 * @param id 实体id
	 * @return List<Attachment> 附件列表
	 */
    public List<Attachment> getAttachmentByEntityId(String id){
    	return attachmentDao.findAttachmentByEntityId(id);
    }
    /**
	 * 返回附件物理根路径
	 * @return String 根目录
	 */
	public String getAttachmentBase() {
		return globalBusiness.getValue(Constants.ATTACHMENT_BASE);
	}
	
	/**
	 * 依据附件id物理删除附件
	 * @param id 附件id
	 * return void
	 */
	public void deleteFile(String id) {
		if (id == null) return;
		Attachment atta = attachmentDao.findAttachmentById(id);
		deleteById(id);
		String base = this.getAttachmentBase();
		String targetFileName = String.format("%s%s/%s", base, atta.getSavePath(), atta.getSaveName());
		File target = new File(targetFileName);
		target.delete();
	}
	/**
	 * 检测文件后缀。如果无后缀名，或者是后缀名中带有中文，则返回null。其他情况返回文件名后缀
	 * @param originalFileName 原始文件名
	 * @return 如果无后缀名，或者是后缀名中带有中文，则返回null。其他情况返回文件名后缀
	 */
	public String guessFileSuffix(String originalFileName) {
		String suffix = null;
		if (originalFileName == null) {
			return suffix;
		}
		
		int pos = originalFileName.lastIndexOf('.');
		if (pos != -1 && pos != originalFileName.length()) {
			suffix = originalFileName.substring(pos + 1);
		}
		
		if (suffix != null && suffix.length() > 0) {
			for (int i = 0; i < suffix.length(); i++) {
				char c = suffix.charAt(i);
				if (!(('0' <= c && c <= '9')
						|| ('A' <= c && c <= 'Z')
						|| ('a' <= c && c <= 'z'))) {
					suffix = null;
					break;
				}
			}
		}
		
		return suffix;
	} 
	/**
	 * 依据附件实体和输入流上传附件
	 * @param atta 附件实体
	 * @param fin 附件流
	 * @return atta返回上传后的附件实体对象
	 */
	private Attachment uploadAttachment(Attachment atta,InputStream fin) {
		String folder = prepareForSave(atta.getUploadTime());//创建存储目录
		String base = this.getAttachmentBase();
		atta.setSavePath(base+folder.substring(base.length()));
		// Use a UUID string as the saved filename to avoid file name encoding problem on UNIX platform
		String uuidFileName = UUID.randomUUID().toString();
		String suffix = guessFileSuffix(atta.getFileName());
		if (suffix == null || suffix.length() == 0) suffix = "unknown";
		atta.setSaveName(String.format("%s.%s", uuidFileName, suffix));
		
		String targetFileName = String.format("%s/%s", atta.getSavePath(), atta.getSaveName());
		File target = new File(targetFileName);
		try {
			target.createNewFile();
		} catch (IOException e1) {
			throw new ApplicationRuntimeException(e1);
		}
		if (fin!=null) {
			FileOutputStream fout = null;
			try {
//				fin = new FileInputStream(source);
				fout = new FileOutputStream(target);
				byte[] bs = new byte[1024];
				int readLen = -1;
				while((readLen = fin.read(bs)) != -1) {
					fout.write(bs, 0, readLen);
				}
				fout.flush();
			} catch (FileNotFoundException e) {
				throw new ApplicationRuntimeException(e);
			} catch (IOException e) {
				throw new ApplicationRuntimeException(e);
			} finally {
					try {
						if (fin != null) fin.close();
						if (fout != null) fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
		// Delete the temporary file
//		source.delete();
//		atta.setFile(null);
		return atta;
	}
	/**
	 * 为保存文件进行一些准备工作。例如创建文件夹，为了降低并发冲突的可能，将此方法声明为synchronized
	 * @param d 根据日期（$base/eerex/年/月/日）创建文件夹
	 * @return 要存放附件的路径
	 */
	private synchronized String prepareForSave(Date d) {
		if (d == null) throw new  ApplicationRuntimeException(String.format("无效的时间，无法根据时间创建文件夹"));
		String folder = this.getAttachmentBase();
		// 判断文件夹是否存在，按照每年，每月分割 
		File f = new File(folder);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				throw new ApplicationRuntimeException(String.format("无法创建目录 %s", folder));
			}
		} 
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		folder = String.format("%s/%d/%02d/%02d", folder, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DAY_OF_MONTH));
		f = new File(folder);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				throw new ApplicationRuntimeException(String.format("无法创建目录 %s", folder));
			}
		} 
		
		return folder;
	} 
	
	public Attachment downloadAttachment(Attachment att) throws IOException {
		Attachment a = getById(att.getId());
		String downLoadPath = a.getSavePath() + File.separator + a.getSaveName();
		File file = new File(downLoadPath);
		InputStream is = new FileInputStream(file);
		// 获取文件大小
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
        // 文件太大，无法读取
        throw new IOException("File is to large "+file.getName());
        }
        // 创建一个数据来保存文件数据
        byte[] bytes = new byte[(int)length];
        // 读取数据到byte数组中
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        a.setByt(bytes);
		return a;
	} 
	
	/**
	 * 附件上传历史
	 * @param id
	 * @param page
	 * @return
	 */
	public Page<Attachment> findAccountRecordListByCode(Map<String, Object> map,
			Page<Attachment> page) {
		return attachmentDao.findAttachmentByCustomerId(
				map, page);
	}
}
