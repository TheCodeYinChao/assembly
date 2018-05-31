package cn.bainuo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil extends FileUtils{

	/**
	 * 关闭字节输出流
	 * 
	 * @param bos
	 */
	public static void closeQuietly(ByteArrayOutputStream bos) {
		try {
			if (bos != null) {
				bos.close();
			}
		} catch (IOException e) {
			log.error("close ByteArrayOutputStream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭输入流和文件输出流
	 * 
	 * @param is
	 * @param fos
	 */
	public static void closeQuietly(InputStream is, FileOutputStream fos) {
		try {
			if (fos != null) {
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭输入流和输出流
	 * 
	 * @param is
	 * @param outputStream
	 */
	public static void closeQuietly(InputStream is, OutputStream outputStream) {
		try {
			if (is != null) {
				is.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭输入流和缓冲输出流
	 * 
	 * @param is
	 * @param bos
	 */
	public static void closeQuietly(InputStream is, BufferedOutputStream bos) {
		try {
			if (bos != null) {
				bos.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭输入流和缓冲输出流
	 * 
	 * @param is
	 * @param bos
	 */
	public static void closeQuietly(InputStream is, BufferedOutputStream bos, FileOutputStream fos) {
		try {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭缓冲输入流和缓冲输出流
	 * 
	 * @param bis
	 * @param bos
	 */
	public static void closeQuietly(BufferedInputStream bis, BufferedOutputStream bos) {
		try {
			if (bos != null) {
				bos.close();
			}
			if (bis != null) {
				bis.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}
	
	public static void closeQuietly(BufferedReader br, FileReader fr) {
		try {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}
	
	public static void closeQuietly(BufferedWriter bw, FileWriter fw) {
		try {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}
	
	public static void closeQuietly(FileOutputStream fos, ObjectOutputStream oos) {
		try {
			if (fos != null) {
				fos.close();
			}
			if (oos != null) {
				oos.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}
	
	public static void closeQuietly(FileInputStream fis, ObjectInputStream ois) {
		try {
			if (fis != null) {
				fis.close();
			}
			if (ois != null) {
				ois.close();
			}
		} catch (IOException e) {
			log.error("close stream IOException, cause : ", e);
		}
	}

	/**
	 * 关闭输入流、文件输出流后删除文件
	 * 
	 * @param file
	 * @param fis
	 */
	public static void deleteFile(File file, InputStream is, FileOutputStream fos) {
		closeQuietly(is, fos);
		if ((file != null) && (file.isFile()) && (file.exists())) {
			boolean isDeleted = file.delete();
			if (isDeleted) {
				log.debug("delete file [{}].", file.getName());
			}
		}
	}

	/**
	 * 关闭缓冲输入流和缓冲输出流后删除文件
	 * 
	 * @param file
	 * @param bis
	 * @param bos
	 */
	public static void deleteFile(File file, BufferedInputStream bis, BufferedOutputStream bos) {
		closeQuietly(bis, bos);
		if (file.exists()) {
			if (file.delete()) {
				log.debug("delete temp file [{}].", file.getName());
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.delete()) {
				log.info("delete file [{}].", file.getName());
			}
		}
	}

	/**
	 * 关闭输入流和缓冲输出流后删除文件
	 * 
	 * @param file
	 * @param is
	 * @param bos
	 */
	public static void deleteFile(File file, InputStream is, BufferedOutputStream bos) {
		closeQuietly(is, bos);
		deleteFile(file);
	}

	public static void deleteFile(File file, InputStream is, BufferedOutputStream bos, FileOutputStream fos) {
		closeQuietly(is, bos, fos);
		deleteFile(file);
	}

	/**
	 * 复制或追加文件
	 * 
	 * @param srcFile
	 * @param newFile
	 * @param position
	 *            从当前位置开始寫入
	 * @param append
	 *            true追加文件
	 */
	public static void moveFile(File srcFile, File newFile, long position, boolean append) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(newFile, append);
			FileChannel fci = fis.getChannel();
			FileChannel fco = fos.getChannel();
			fco.transferFrom(fci, position, fci.size());
		} catch (Exception e) {
			deleteFile(newFile, fis, fos);
		} finally {
			closeQuietly(fis, fos);
		}
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameSuffix(String fileName) {
		if (fileName == null || fileName.lastIndexOf(".") == -1) {
			return "";
		}
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	/**
	 * 
	 * writeFile:写文件
	 * @author wangxf
	 * @param path
	 * @param fileName
	 * @param text
	 * @param append
	 * @since JDK 1.8
	 */
	public static void writeFile(String path, String fileName, String text, boolean append){
		File dir = new File(path);
		if(!dir.exists()){
			boolean f = dir.mkdirs();
			if(!f){
				log.warn("create file path fail,filePath:{}",path);
			}
		}
		path = path + File.separator + fileName;
		writeFile(path, text, append);
	}
	/**
	 * @Description: 写文件
	 * @param path
	 * @param fileName
	 * @param value
	 * @return void
	 */
	public static void writeFile(String path, String fileName, String text) {
		File file = new File(path);
		FileWriter writer = null;
		try {
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path + File.separator + fileName);
			writer = new FileWriter(file);
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			log.error("writeFile IOException, cause :", e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.error("close stream IOException, cause :", e);
			}
		}
	}
	
	/**
	 * 写文件
	 * @param filePath
	 * @param data
	 * @param append
	 */
	public static void writeFile(String filePath, String data, boolean append) {
		BufferedWriter bufWriter = null;
		FileWriter fileWriter = null;
        try {
        	File file = new File(filePath);
        	if (!file.exists()) {
        		file.createNewFile();
        	}
        	
        	fileWriter = new FileWriter(filePath, append);
            bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write(data + "\n");
        } catch (Exception e) {
            log.error("Write file ex.", e);
        } finally {
        	closeQuietly(bufWriter, fileWriter);
        }
	}
	
	/**
	 * 行读取文件
	 * @param filePath
	 */
	public static List<String> readFile(String filePath) {
		File file = new File(filePath);
		return readFile(file);
	}

	/**
	 * 行读取文件
	 * @param filePath
	 */
	public static List<String> readFile(File file) {
		List<String> list = new ArrayList<String>();
		FileReader reader = null;
		BufferedReader br = null;
		try {
			if (file == null || !file.exists()) {
				return null;
			}
			
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String line = null;
			while((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			log.error("Read file ex.", e);
		} finally {
			closeQuietly(br, reader);
		}
		
		return list;
	}

	/**
	 * 
	 * 读取整个文件. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 *
	 * @param filePath
	 * @return
	 * @since JDK 1.8
	 */
	public static String readWholeFile(File file) {
		FileReader reader = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			if (file == null || !file.exists()) {
				return null;
			}
			
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.error("Read file ex.", e);
		} finally {
			closeQuietly(br, reader);
		}
		
		return sb.toString();
	}
	
	/**
	 * 写对象到文件
	 * @param filePath
	 * @param obj
	 */
	public static void writeObjectToFile(String filePath, Object obj) {
        FileOutputStream out = null;
        ObjectOutputStream objOut = null;
        try {
        	File file = new File(filePath);
        	if (!file.exists()) {
        		file.createNewFile();
        	}
            out = new FileOutputStream(file);
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
        } catch (IOException e) {
            log.error("Write file ex.", e);
        } finally {
        	closeQuietly(out, objOut);
        }
    }
	
	/**
	 * 从文件中读取对象
	 * @param filePath
	 * @return
	 */
	public static Object readObjectFromFile(String filePath) {
        File file = new File(filePath);
        return readObjectFromFile(file);
    }

	/**
	 * 从文件中读取对象
	 * @param filePath
	 * @return
	 */
	public static Object readObjectFromFile(File file) {
		Object temp = null;
		FileInputStream in = null;
		ObjectInputStream objIn = null;
		try {
			in = new FileInputStream(file);
			objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			log.error("Read file ex.", e);
		} finally {
			closeQuietly(in, objIn);
		}
		
		return temp;
	}
	
	/**
	 * 移动文件
	 * @param srcFile
	 * @param dstPath
	 */
	public static void moveFile(String srcFile, String dstPath) {
		File file = new File(srcFile);
		file.renameTo(new File(dstPath));
	}
	
	/**
	 * 新建目录
	 * @param filePath
	 */
	public static void mkdirs(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}


	/**
	 * 获取目录下的所有文件
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> list(File file) {
		List<String> nameList = new ArrayList<>();
		// 判断file是否是目录
		if (file.isDirectory()) {
			File[] lists = file.listFiles();
			if (lists != null) {
				for (File temp : lists) {
					if (temp.isFile()) {
						nameList.add(temp.getName());
					}
				}
			}
		} else {
			log.info("[{}] is not directory", file);
		}
		return nameList;
	}

	public static List<String> list(File file, String suffix) {
		List<String> nameList = new ArrayList<>();
		// 判断file是否是目录
		if (file.isDirectory()) {
			
			FileFilter filter = new FileFilter() {
				
				@Override
				public boolean accept(File tempFile) {
					return tempFile.getName().endsWith(suffix);
				}
			};
			
			File[] lists = file.listFiles(filter);
			if (lists != null) {
				for (File temp : lists) {
					if (temp.isFile()) {
						nameList.add(temp.getName());
					}
				}
			}
		} else {
			log.info("[{}] is not directory", file);
		}
		return nameList;
	}
}
