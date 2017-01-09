package org.lf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.lf.jssm.service.model.PdfPathInfo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Image2Pdf {
	Logger logger = Logger.getLogger(Image2Pdf.class);
	private String Tip = "";
	private LinkedHashMap<String, Integer> MarkMap = new LinkedHashMap<String, Integer>();
	private List<PdfPathInfo> pathInfoList;
	// 用于加上下标的内部类
	private class HeaderFooter extends PdfPageEventHelper {
		/**
		 * 页眉
		 */
		@SuppressWarnings("unused")
		private String header = "";

		/**
		 * 文档字体大小，页脚页眉最好和文本大小一致
		 */
		public int presentFontSize = 16;

		/**
		 * 文档页面大小，最好前面传入，否则默认为A4纸张
		 */
		@SuppressWarnings("unused")
		public Rectangle pageSize = PageSize.A4;

		// 模板
		public PdfTemplate total;

		// 基础字体对象
		public BaseFont bf = null;

		// 利用基础字体生成的字体对象，一般用于生成中文文字
		public Font fontDetail = null;

		/**
		 * 
		 * Creates a new instance of PdfReportM1HeaderFooter 无参构造方法.
		 * 
		 */
		public HeaderFooter() {

		}

		/**
		 * 
		 * Creates a new instance of PdfReportM1HeaderFooter 构造方法.
		 * 
		 * @param yeMei
		 *            页眉字符串
		 * @param presentFontSize
		 *            数据体字体大小
		 * @param pageSize
		 *            页面文档大小，A4，A5，A6横转翻转等Rectangle对象
		 */
		@SuppressWarnings("unused")
		public HeaderFooter(String yeMei, int presentFontSize, Rectangle pageSize) {
			this.header = yeMei;
			this.presentFontSize = presentFontSize;
			this.pageSize = pageSize;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		@SuppressWarnings("unused")
		public void setPresentFontSize(int presentFontSize) {
			this.presentFontSize = presentFontSize;
		}

		/**
		 * 
		 * 文档打开时创建模板
		 * 
		 * 
		 */
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(50, 50);// 共 页
																		// 的矩形的长宽高
		}

		/**
		 * 
		 * 关闭每页的时候，写入页眉，写入'第几页共'这几个字。
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(com.itextpdf.text.pdf.PdfWriter,
		 *      com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {

			try {
				if (bf == null) {
					bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
				}
				if (fontDetail == null) {
					fontDetail = new Font(bf, presentFontSize, Font.NORMAL);// 数据体字体
				}
			} catch (DocumentException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			int count = 0;
			for(int i=0;i<pathInfoList.size();i++){
				if(pathInfoList.get(i).getMlmc().trim().equals("卷宗封面")){
					count++;
				}
				if(pathInfoList.get(i).getMlmc().trim().equals("卷宗目录")){
					count++;
				}
			}
			// 显示多少页.以0001开始
			if(count == 0){
				Integer pageS = writer.getPageNumber();
				String foot1 = new DecimalFormat("0000").format(pageS);
				Phrase footer = new Phrase(foot1, fontDetail);
				PdfContentByte cb = writer.getDirectContent();
				ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
							(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F, document.bottom() - 20, 0);
	
			}else if(count == 1){
				Integer pageS = writer.getPageNumber();
				if(pageS>1){
				String foot1 = new DecimalFormat("0000").format(pageS-1);
				Phrase footer = new Phrase(foot1, fontDetail);
				PdfContentByte cb = writer.getDirectContent();
				ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
							(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F, document.bottom() - 20, 0);
				}
			}else{
				Integer pageS = writer.getPageNumber();
				if(pageS>2){
				String foot1 = new DecimalFormat("0000").format(pageS-2);
				Phrase footer = new Phrase(foot1, fontDetail);
				PdfContentByte cb = writer.getDirectContent();
				ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
							(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F, document.bottom() - 20, 0);
				}
			}
		}

		/**
		 * 
		 * 关闭文档时，替换模板，完成整个页眉页脚组件
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(com.itextpdf.text.pdf.PdfWriter,
		 *      com.itextpdf.text.Document)
		 */
		public void onCloseDocument(PdfWriter writer, Document document) {
			// 7.最后一步了，就是关闭文档的时候，将模板替换成实际的 Y 值,至此，page x of y 制作完毕，完美兼容各种文档size。
			total.beginText();
			total.setFontAndSize(bf, presentFontSize);// 生成的模版的字体、颜色
			String foot2 = " " + (writer.getPageNumber() - 1) + " 页";
			total.showText(foot2);// 模版显示的内容
			total.endText();
			total.closePath();
		}
	}

	/**
	 * 函数功能：将inDir文件夹下所有的.jpg格式文件转成一册pdf（注意，文件夹下请不要包含其他类型文件）。
	 * 
	 * @param inDir
	 *            此参数至少要到”d:/ftp/2010/立案/阳保字/00007/公安卷-侦查卷/“这一层
	 * @param pdfFile
	 *            此参数必须含有文件名。如"D:/123.pdf";
	 * @param tip
	 *            此参数可以为null，但必须有
	 * @throws IOException
	 */
	// 单文件夹不重名简单版（视需求可以做复杂版）
	public void translate(List<PdfPathInfo> pathInfoList, File pdfFile) throws IOException {
		this.pathInfoList = pathInfoList;
		Integer pageSize = pathInfoList.size() + 1;
		this.Tip = "本卷宗连面带底共计 " + pageSize + " 页";
		getDealMap(pathInfoList);

		if (!pdfFile.exists()) {
			pdfFile.getParentFile().mkdirs();
			pdfFile.createNewFile();
		}
		Document doc = new Document(PageSize.A4,5,5,0,40); // 最后一个一定要给足
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfFile)); // 给一个写出的路径
			this.setFooter(writer);

			writer.setFullCompression();
			writer.setPdfVersion(PdfWriter.VERSION_1_4);
			doc.open();
			
			// 为导出书签服务
			PdfContentByte cb = writer.getDirectContent();
			PdfOutline root = cb.getRootOutline();
			//
			PdfPathInfo pi = null;
			Rectangle rec = null;
			for (int i = 0; i < pathInfoList.size(); i++) {
				pi = pathInfoList.get(i);
				File file = new File(pi.getPath());
				if (file.length() > 0) {
					Image png1 = Image.getInstance(pi.getPath());
					Type type = getPageType(png1.getWidth(), png1.getHeight());
					if(type == Type.A4V){
						rec = PageSize.A4;
						doc.setPageSize(rec);
						png1.scaleAbsolute(210*2.84f, 297*2.84f);
						png1.setAlignment(Image.MIDDLE); 
					}else if(type == Type.A3H){
						rec = new Rectangle(420*2.84f, 297*2.84f);
						doc.setPageSize(rec);
						png1.scaleAbsolute(420*2.84f, 297*2.84f);
						png1.setAlignment(Image.MIDDLE); 
					}else if(type == Type.A4H){
						rec = new Rectangle(297*2.84f, 210*2.84f);
						doc.setPageSize(rec);
						png1.scaleAbsolute(297*2.84f, 210*2.84f);
						png1.setAlignment(Image.MIDDLE); 
					}else if(type == Type.A3V){
						rec = new Rectangle(297*2.84f, 420*2.84f);
						doc.setPageSize(rec);
						png1.scaleAbsolute(297*2.84f, 420*2.84f);
					}
					doc.newPage();
					doc.add(png1);
				}
			}
			// ===================添加书签=======================
			Iterator<String> it = MarkMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				@SuppressWarnings("unused")
				PdfOutline title1 = new PdfOutline(root, PdfAction.gotoLocalPage(MarkMap.get(key), new PdfDestination(1), writer), key);
			}
			doc.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void setFooter(PdfWriter writer) throws DocumentException, IOException {
		// 更改事件，瞬间变身 第几页/共几页 模式。
		HeaderFooter headerFooter = new HeaderFooter();// 就是上面那个类
		headerFooter.setHeader(this.Tip);
		writer.setBoxSize("art", PageSize.A4);
		writer.setPageEvent(headerFooter);
	}

	/**
	 * 图片压缩技术2
	 * 
	 * @param h
	 *            宽度
	 * @param w
	 *            长度
	 * @return 百分比
	 */
	@SuppressWarnings("unused")
	private int getPercent2(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		p2 = 530 / w * 100;
		p = Math.round(p2);
		return p;
	}

	private void getDealMap(List<PdfPathInfo> pathInfoList) {
		for (int i = 0; i < pathInfoList.size(); i++) {
			PdfPathInfo ppif = pathInfoList.get(i);
			if (MarkMap.get(ppif.getMlmc()) == null) {
				MarkMap.put(ppif.getMlmc(), ppif.getCurrentPageNumber());
			}
		}
	}

	private enum Type {
		A4H, A4V, A3H, A3V;// A4H表示横向,A4V表示纵向
	}

	private Type getPageType(float width, float height) {
		float wScale = 0.85f;
		float hScale = 1.15f;//误差为正负0.15
		if(2970*wScale<width&&width<2970*hScale){
			if(2100*wScale<height&&height<2100*hScale){
				return Type.A4H;
			}else if(4200*wScale<height&&height<4200*hScale){
				return Type.A3V;
			}else{
				return Type.A3H;
			}
		}else if(2100*wScale<width&&width<2100*hScale){
			if(2970*wScale<height&&height<2970*hScale){
				return Type.A4V;
			}
		}else if(width>height){
			if(width<=297*hScale){
				return Type.A4H;
			}else {
				return Type.A3H;
			}
		}else if(width<=height){
			if(height<=210*hScale){
				return Type.A4V;
			}else{
				return Type.A3V;
			}
		}
		return null;
		
	}

}
