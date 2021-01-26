package com.tkcx.api.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.AreaBreakType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;

/**
 * html转pdf方法含分页
 */
@Slf4j
public class HtmlToPdf {

    private static HtmlToPdf htmlToPdf;
    /**
     * windows运行环境需要处理第一个/,根据自身环境决定是否执行该方法
     */
    private Document document = null;
    private PdfDocument pdfDoc = null;
    private OutputStream outputStream;
    private static ConverterProperties converterProperties;//初始化生成字体
    private String pdfPath;//pdf保存路径
    private PageSize pageSize;

    /**
     * @Description    ：获取设置了中文ttf字体的converterProperties
     */
    static {
        converterProperties = new ConverterProperties();
        //设置中文字体，ttf文件夹下SimSum-01和Dengb分别支持细字体和粗字体，缺一不可
        FontProvider fontProvider = new FontProvider();
        ClassPathResource resourceDengb = new ClassPathResource("ttf/Dengb.ttf")
                ,resourceSimSum = new ClassPathResource("ttf/SimSum-01.ttf");
        try {
            fontProvider.addFont(toByteArray(resourceDengb));
            fontProvider.addFont(toByteArray(resourceSimSum));
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("====>初始化字体成功");
        converterProperties.setFontProvider(fontProvider);
    }
    /**
     * 读取文件内容到数组
     * @param resource
     * @return
     * @throws IOException
     */
    private static byte[] toByteArray(ClassPathResource resource) {
        ByteArrayOutputStream bos = null;
        BufferedInputStream in = null;
        byte[] result = new byte[0];
        try {
            bos = new ByteArrayOutputStream((int) resource.contentLength());
            in = new BufferedInputStream(resource.getInputStream());
            int offset = 1024;
            byte[] buffer = new byte[offset];
            int len;
            while (-1 != (len = in.read(buffer, 0, offset))) {
                bos.write(buffer, 0, len);
            }
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private HtmlToPdf(String pdfPath, PageSize pageSize) {
        this.pdfPath = pdfPath;
        this.pageSize = pageSize;
        File pdfFile = new File(this.pdfPath);
        try{
            outputStream = new FileOutputStream(pdfFile);
            pdfDoc = new PdfDocument(new PdfWriter(outputStream), new DocumentProperties());
            log.info("创建pdf文件："+this.pdfPath);
            document = new Document(pdfDoc, PageSize.A4.rotate());
        }  catch (IOException e) {
            log.error("创建pdf文件失败,{}", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取对应html模板字符串
     * @param BusiType
     * @return
     */
    public static StringBuffer getHtmlTemplateByBusiType(Integer BusiType){
        StringBuffer HtmlStr = new StringBuffer();
        ClassPathResource resource = new ClassPathResource("template/" + BusiType +".html");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(),"utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                HtmlStr.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HtmlStr;
    }
    public static synchronized HtmlToPdf getHtmlToPdf(String pdfPath, PageSize pageSize) {
        return htmlToPdf = new HtmlToPdf(pdfPath, pageSize);
    }
    /**
     * 添加新页
     * @param htmlStr
     */
    public void addNewPage(StringBuffer htmlStr){
        List<IElement> elements;
        try {
            elements = HtmlConverter.convertToElements(htmlStr.toString(), converterProperties);
            for (IElement element: elements) {
                if(element instanceof Table){
                    document.add((IBlockElement) element);//放入内容
                    //创建新页
                    AreaBreak areaBreak = new AreaBreak(AreaBreakType.NEXT_PAGE);
                    areaBreak.setPageSize(this.pageSize);
                    document.add(areaBreak);
                }
            }
            log.info("pdf文件加入第<"+(pdfDoc.getNumberOfPages()-1)+">页");
        } catch (IOException e) {
            log.error("pdf文件加入第<"+(pdfDoc.getNumberOfPages())+">页失败,{}", e);
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void close(){
        pdfDoc.removePage(pdfDoc.getLastPage());//删除最后一个空白页
        document.close();
        log.info("html转pdf文件成功====》"+this.pdfPath);
    }
}
