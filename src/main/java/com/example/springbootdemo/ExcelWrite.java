package com.example.springbootdemo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWrite {
	private static HSSFWorkbook workbook = null;
	private static HSSFSheet sheet;
	private static int begin;
	private static int sheetName;
	public  ExcelWrite(){
		//创建workbook
		workbook = new HSSFWorkbook();
		begin=0;
		sheetName=1;
		sheet = workbook.createSheet(String.valueOf(sheetName));
	}
	/**
	 * 判断文件是否存在.
	 * @param fileDir  文件路径
	 * @return
	 */
	public static boolean fileExist(String fileDir){
		boolean flag = false;
		File file = new File(fileDir);
		flag = file.exists();
		return flag;
	}
	/**
	 * 判断文件的sheet是否存在.
	 * @param fileDir   文件路径
	 * @param sheetName  表格索引名
	 * @return
	 */
	public static boolean sheetExist(String fileDir, String sheetName) throws Exception {
		boolean flag = false;
		File file = new File(fileDir);
		if(file.exists()){    //文件存在
			//创建workbook
			try {
				workbook = new HSSFWorkbook(new FileInputStream(file));
				//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
				HSSFSheet sheet = workbook.getSheet(sheetName);
				if(sheet!=null)
					flag = true;
			} catch (Exception e) {
				throw e;
			}

		}else{    //文件不存在
			flag = false;
		}
		return flag;
	}
	/**
	 * 创建新excel.
	 * @param fileDir  excel的路径
	 * @param sheetName 要创建的表格索引
	 * @param titleRow excel的第一行即表格头
	 */
	public static void createExcel(String fileDir, String sheetName, String titleRow[]) throws Exception {
		//创建workbook
		workbook = new HSSFWorkbook();
		//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
		HSSFSheet sheet1 = workbook.createSheet(sheetName);
		//新建文件
		FileOutputStream out = null;
		try {
			//添加表头
			HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行
			for(short i = 0;i < titleRow.length;i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(titleRow[i]);
			}
			out = new FileOutputStream(fileDir);
			workbook.write(out);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除文件.
	 * @param fileDir  文件路径
	 */
	public static boolean deleteExcel(String fileDir) {
		boolean flag = false;
		File file = new File(fileDir);
		// 判断目录或文件是否存在
		if (!file.exists()) {  // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) {  // 为文件时调用删除文件方法
				file.delete();
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * 往excel中写入(已存在的数据无法写入).
	 * @param fileDir    文件路径
	 * @throws Exception
	 */
	public static void writeToExcel(String fileDir, List<List> list) throws Exception {
		//流
		FileOutputStream out = null;
		try {
			// 获得表头行对象
			for(int rowId=0;rowId<list.size();rowId++){
				if(begin>1000){
					begin = 0;
					sheet = workbook.createSheet(String.valueOf(++sheetName));
					writeToExcel(fileDir,list.subList(begin,list.size()));
				}else {
					List list1 = list.get(rowId);
					HSSFRow newRow=sheet.createRow(begin+rowId);
					for (int columnIndex=0;columnIndex<list1.size();columnIndex++){
						HSSFCell cell = newRow.createCell(columnIndex);
						cell.setCellValue(list1.get(columnIndex).toString());
					}
				}
				if(rowId==list.size()-1){
					begin=begin+list.size();
				}
			}
			out = new FileOutputStream(fileDir);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
        /*判断文件是否存在
        System.out.println(ExcelWrite.fileExist("E:/test2.xls"));
        //创建文件
        String title[] = {"id","name","password"};
        ExcelWrite.createExcel("E:/test2.xls","sheet1",title);
        List<Map> list=new ArrayList<Map>();
        Map<String,String> map=new HashMap<String,String>();
        map.put("id", "111");
        map.put("name", "张三");
        map.put("password", "111！@#");

        Map<String,String> map2=new HashMap<String,String>();
        map2.put("id", "222");
        map2.put("name", "李四");
        map2.put("password", "222！@#");
        list.add(map);
        list.add(map2);
        ExcelWrite.writeToExcel("E:/test2.xls","sheet1",list);

        String sql="select aaa,bbb,ccc from dddd";
        String sqlForSplit = sql.substring(sql.toLowerCase().indexOf("select")+6,sql.toLowerCase().indexOf("from")).trim();
        String sqlRemoveFrom=sql.substring(sql.toLowerCase().indexOf("from")+5).trim();
        System.out.println(sqlRemoveFrom);
        String tableName=sqlRemoveFrom.indexOf(" ")==-1 ?  sqlRemoveFrom : sqlRemoveFrom.substring(0,sqlRemoveFrom.indexOf(" "));
        System.out.println(tableName);
        */

	}
}