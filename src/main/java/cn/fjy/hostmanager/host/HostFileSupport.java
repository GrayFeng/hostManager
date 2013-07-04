package cn.fjy.hostmanager.host;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import cn.fjy.hostmanager.pojo.Domain;

import com.google.common.base.Strings;
import com.google.common.io.Files;

/**
 * Description: HostFileSupport.java
 * All Rights Reserved.
 * @version 1.0  2013-3-18 下午5:42:17  
 * @author Gray(jy.feng@zuche.com) 
 */

public class HostFileSupport {
	
	private String hostFileName = "hosts";
	
	private String hostFilePath = "C://Windows//System32//drivers//etc";
	
	private String backupSuffix = ".hfm.bak";
	
	private String dividingLineStart = "#####hfm_start#####";
	
	private String dividingLineEnd = "#####hfm_end#####";
	
	/**
	 * Backup current hostfile
	 */
	public void backup() {
		File hostFile = getHostFile();
		File backupFile = getBackupFile();
		if (hostFile.exists() && !backupFile.exists()) {
			if (backupFile.exists()) {
				backupFile.delete();
			}
			try {
				Files.copy(hostFile, backupFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Create hostfile from backupfile
	 */
	public void recover(){
		File hostFile = getHostFile();
		File backupFile = getBackupFile();
		if (backupFile.exists()) {
			if (hostFile.exists()) {
				hostFile.delete();
			}
			try {
				Files.copy(backupFile, hostFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * add domain mapping
	 * @param domainList
	 */
	public boolean addDomainMapping(List<Domain> domainList){
		boolean result = false;
		String mappingStr = getDomainMappingStr(domainList);
		if(!Strings.isNullOrEmpty(mappingStr)){
			File hostFile = getHostFile();
			if(hostFile.exists()){
				try {
					String context = getHostFileContextWithOutMapping();
					if(context != null){
						Files.write((context + mappingStr).getBytes(),hostFile);
						result = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public boolean clearMapping(){
		boolean result = false;
		String context = getHostFileContextWithOutMapping();
		if(!Strings.isNullOrEmpty(context)){
			File hostFile = getHostFile();
			if(hostFile.exists()){
				try {
					Files.write(context.getBytes(),hostFile);
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private String getHostFileContextWithOutMapping(){
        StringBuffer buffer = null;
        List<String> list = readHostFileLines();
        if (list != null && list.size() > 0) {
            buffer = new StringBuffer();
            int startIndex = 0;
            int endIndex = 0;
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (dividingLineStart.equals(str)) {
                    startIndex = i;
                }
                if (dividingLineEnd.equals(str)) {
                    endIndex = i;
                }
            }
            if (startIndex != endIndex && startIndex < endIndex && endIndex < list.size()) {
                for (int i = 0; i < list.size(); i++) {
                    if (i < startIndex || i > endIndex) {
                        buffer.append(list.get(i));
                        buffer.append("\r\n");
                    }
                }
            } else {
                for (String str : list) {
                    buffer.append(str);
                    buffer.append("\r\n");
                }
            }
        }
        return buffer == null ? null : buffer.toString();
    }

    public String getHostFileContent(){
        StringBuffer buffer = new StringBuffer();
        List<String> list = readHostFileLines();
        if(list != null && list.size()>0){
            for(String str : list){
               buffer.append(str);
               buffer.append("\r\n");
            }
        }
        return buffer.toString();
    }

    private List<String> readHostFileLines(){
        List<String> list = null;
        File hostFile = getHostFile();
        if(hostFile.exists()){
            try {
                  list = Files.readLines(hostFile, Charset.defaultCharset());
            }catch (IOException e){
                    e.printStackTrace();
            }
        }
        return list;
    }
	
	
	private String getDomainMappingStr(List<Domain> domainList){
		StringBuffer buffer  = null;
		if(domainList != null && domainList.size() > 0){
			buffer = new StringBuffer();
			buffer.append(dividingLineStart);
			buffer.append("\r\n");
			for(Domain domain : domainList){
				buffer.append(domain.getIp() + "    " + domain.getDomain());
				buffer.append("\r\n");
			}
			buffer.append(dividingLineEnd);
			buffer.append("\r\n");
		}
		return buffer == null ? null :buffer.toString();
	}
	
	private File getHostFile(){
		return new File(hostFilePath + File.separator + hostFileName);
	}
	
	private File getBackupFile(){
		return new File(getHostFile().getAbsoluteFile() + backupSuffix);
	}
}
