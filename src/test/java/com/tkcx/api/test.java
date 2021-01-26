package com.tkcx.api;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;*/

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

public class test {
    public static void main(String[] args) {
        File file1 = new File("D:\\spring-tool-work\\acct_print\\src\\main\\resources\\template\\8.html");
        File file2 = new File("D:\\spring-tool-work\\acct_print\\src\\main\\resources\\ttf\\SimSum-01.ttf");
        System.out.println(file1.length());
        System.out.println(file2.length());
        /*String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table align=\"center\"  id=\"table2\" border=\"1\" style=\"width: 100%;border-collapse: collapse;border:none\">\n" +
                "    <thead style=\"border: none\">\n" +
                "    <tr><td colspan=\"5\" style=\"height: 50px;font-size: 25px;text-align: center;border: none\">网贷客户借款信息</td></tr>\n" +
                "    <tr><td colspan=\"5\" style=\"border: none;\" ><div style=\"display: flex;justify-content: space-between;flex-wrap: wrap\"><span>机构名称：陕西秦农农村商业银行股份有限公司骊山支行</span><span>机构号：27010605</span><span>还款日期：XXXX年XX月XX日</span><span>币种:人民币</span><span>单位:元</span></div></td></tr>\n" +
                "    </thead>\n" +
                "    <tbody style=\"border: 1px solid black\">\n" +
                "    <tr><td>借款人:</td><td>福噶uu</td><td>身份证号:</td><td>1427271995020304441</td></tr>\n" +
                "    <tr><td>贷户住址:</td><td></td><td>合同号:</td><td></td></tr>\n" +
                "    <tr><td>存款账号:</td><td></td><td>贷款账号:</td><td></td></tr>\n" +
                "    <tr><td>借款利率:</td><td></td><td>还款方式:</td><td></td></tr>\n" +
                "    <tr><td>贷款用途:</td><td></td><td>借据种类:</td><td></td></tr>\n" +
                "    <tr><td>贷款日期:</td><td></td><td>到期日期:</td><td></td></tr>\n" +
                "    <tr><td>借款金额:</td><td colspan=\"3\">(输出格式为：小写金额+大写金额，如：¥3,000.00  人民币叁仟元整)</td></tr>\n" +
                "    </tbody>\n" +
                "    <tfoot>\n" +
                "    <tr><td colspan=\"2\" style=\"border: none;\">打印柜员：XXXX</td><td colspan=\"2\" style=\"border: none;text-align: right\">日期：XXXX年XX月XX日XX时XX分XX秒</td></tr>\n" +
                "    </tfoot>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
        try {
            parseHTML2PDFFile2("D:/html2pdf/test.pdf", html);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void parseHTML2PDFFile2(String pdfFile, String html)
            throws Exception {
/*        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();
        XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
        xmlWorkerHelper.parseXHtml(writer, document,
                new ByteArrayInputStream(html.getBytes("Utf-8")),
                Charset.forName("UTF-8"));
        document.close();*/
    }
}
