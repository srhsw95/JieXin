package cn.itcast.jk.action;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.util.DownloadUtil;


public class OutProductAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//属性注入
	private String inputDate;
	
	public String getInputDate() {
		return inputDate;
	}
	
	private ContractProductService contractProductService;
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	/**
	 * 跳向打印界面
	 * @return
	 * @throws Exception
	 */
	public String toedit() throws Exception {
		return "toedit";
	}
	
	
	/**
	 * 通过加载模板文件进行输出excel操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String print() throws Exception {
		
		//获取模板文件绝对路径
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		realPath=realPath+"make/xlsprint/tOUTPRODUCT.xls";
		//读取表格文件
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(realPath));
		//读取工作表
		HSSFSheet sheetAt = wb.getSheetAt(0);
		//获取第一行
		
		//设置行列的起始值
		int rowNo=0;
		int colNo=1;
		//获取第一行
		Row row = sheetAt.getRow(rowNo++);
		Cell cell = row.getCell(colNo);
		cell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");
		//跳过第二行
		rowNo++;
		//继续获取第三行的格式
		row = sheetAt.getRow(rowNo++);
		CellStyle cellStyle1 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle2 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle3 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle4 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle5 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle6 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle7 = row.getCell(colNo++).getCellStyle();
		CellStyle cellStyle8 = row.getCell(colNo++).getCellStyle();
		
		//获取数据库数据
		List<ContractProduct> find = contractProductService.find("from ContractProduct cp where cp.contract.shipTime like '"+inputDate+"%"+"'", null);
		//contractProductService.find("from ContractProduct cp where substring(cp.contract.shipTime,0,10) like ?", new Object[]{inputDate});
		//写入表格
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (ContractProduct contractProduct : find) {
			HSSFRow createRow = sheetAt.createRow(rowNo++);
			colNo=1;
			
			HSSFCell createCell1 = createRow.createCell(colNo++);
			createCell1.setCellValue(contractProduct.getContract().getCustomerName());//客户名称
			createCell1.setCellStyle(cellStyle1);
			
			
			HSSFCell createCell2 = createRow.createCell(colNo++);
			createCell2.setCellValue(contractProduct.getContract().getContractNo());//订单号
			createCell2.setCellStyle(cellStyle2);
			
			
			HSSFCell createCell3 = createRow.createCell(colNo++);
			createCell3.setCellValue(contractProduct.getProductNo());//货号
			createCell3.setCellStyle(cellStyle3);
			
			HSSFCell createCell4 = createRow.createCell(colNo++);
			createCell4.setCellValue(contractProduct.getCnumber()+contractProduct.getPackingUnit());
			createCell4.setCellStyle(cellStyle4);
			
			HSSFCell createCell5 = createRow.createCell(colNo++);
			createCell5.setCellValue(contractProduct.getFactoryName());
			createCell5.setCellStyle(cellStyle5);
			
			HSSFCell createCell6 = createRow.createCell(colNo++);
			createCell6.setCellValue(simpleDateFormat.format(contractProduct.getContract().getDeliveryPeriod()));//交期
			createCell6.setCellStyle(cellStyle6);
			
			HSSFCell createCell7 = createRow.createCell(colNo++);
			createCell7.setCellValue(simpleDateFormat.format(contractProduct.getContract().getShipTime()));//船期
			createCell7.setCellStyle(cellStyle7);
			
			HSSFCell createCell8 = createRow.createCell(colNo++);
			createCell8.setCellValue(contractProduct.getContract().getTradeTerms());//贸易条款
			createCell8.setCellStyle(cellStyle8);
		}
		//文件下载
		//7.生成excel文件
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();	//生成流对象
		wb.write(byteArrayOutputStream);	//将excel写入流
		byteArrayOutputStream.flush();
		byteArrayOutputStream.close();
		//使用工具类来完成下载：	
		String outFile = inputDate.replace("-0", "-").replace("-", "年")+"月份出货表"+".xls";
		DownloadUtil down = new DownloadUtil();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.download(byteArrayOutputStream, response, outFile);
		return NONE;
	}
	/**
	 * 下载报表文件
	 * @return
	 * @throws Exception
	 */
	public String print1() throws Exception {
		//八个步骤
		//创建工作簿
		//Workbook wb = new XSSFWorkbook();
		Workbook wb = new SXSSFWorkbook(200);
		//创建工作表
		Sheet sheet = wb.createSheet("sheet1");
		//指定起始行
		int rowNo=0;
		//指定起始列
		int colNo=1;
		//创建一个行对象
		
		sheet.setColumnWidth(0, 1*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(1, 26*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(2, 12*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(3, 29*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(4, 12*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(5, 15*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(6, 10*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(7, 10*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(8, 8*256);//0代表的是第一列，1代表第一列的宽度
		
		Row nRow = sheet.createRow(rowNo++);
		//创建一个单元格对象
		nRow.setHeightInPoints((short)26);//设置行高
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
		Cell nCell = nRow.createCell(colNo);
		/*表格标题 
		 */
		nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");
		nCell.setCellStyle(bigTitle(wb));
		
		
		//另起一行
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints((short)26);//设置行高
		
		//新建单元格
		//以上完成表头的书写
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("客户");
		nCell.setCellStyle(title(wb));
		
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("订单号");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("货号");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("数量");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("工厂");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("工厂交期");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("船期");
		nCell.setCellStyle(title(wb));
		
		nCell = nRow.createCell(colNo++);
		nCell.setCellValue("贸易条款");
		nCell.setCellStyle(title(wb));
		
		//从数据库中查找相应的数据，即船期在指定时期内的多有的记录
		String hql="from ContractProduct cp where cp.contract.shipTime like '"+inputDate+"%"+"'";
		List<ContractProduct> find = contractProductService.find(hql, null);
		//日期工具
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//循环输出对应的信息
		for (int i = 0; i < 200; i++) {
		
			
		
		for (ContractProduct contractProduct : find) {
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints((short)26);//设置行高
			colNo=1;
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getContract().getCustomerName());
			nCell.setCellStyle(text(wb));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getContract().getContractNo());
			nCell.setCellStyle(text(wb));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getProductNo());
			nCell.setCellStyle(text(wb));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getCnumber()+" "+contractProduct.getPackingUnit());
			nCell.setCellStyle(text(wb));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getFactoryName());
			nCell.setCellStyle(text(wb));
			
			//工厂交期
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(simpleDateFormat.format(contractProduct.getContract().getDeliveryPeriod()));
			nCell.setCellStyle(text(wb));
			
			//船期
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(simpleDateFormat.format(contractProduct.getContract().getShipTime()));
			nCell.setCellStyle(text(wb));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(contractProduct.getContract().getTradeTerms());
			nCell.setCellStyle(text(wb));
		}
		}
		
		//文件下载
		//7.生成excel文件
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();			//生成流对象
		wb.write(byteArrayOutputStream);								//将excel写入流
		byteArrayOutputStream.flush();
		byteArrayOutputStream.close();
		//使用工具类来完成下载：	
		String outFile = "测试.xlsx";
		DownloadUtil down = new DownloadUtil();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.download(byteArrayOutputStream, response, outFile);
		return NONE;
	}
	
	//大标题的样式
		public CellStyle bigTitle(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)16);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			return style;
		}
		//小标题的样式
		public CellStyle title(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("黑体");
			font.setFontHeightInPoints((short)12);
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
			style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
			style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
			style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
			
			return style;
		}
		
		//文字样式
		public CellStyle text(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short)10);
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
			style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
			style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
			style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
			
			return style;
		}
}
