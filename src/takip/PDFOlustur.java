package takip;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;

public class PDFOlustur {
    GecmisSatislar gs = new GecmisSatislar();
    
    public void faturaCikar(String tarih, String firma, String kisi, String um, String fiyat)
    {
        try {
            Document doc = new Document();
            
            File f = new File("C:\\Users\\John\\Desktop\\"+kisi+".pdf");
            FileOutputStream fos = new FileOutputStream(f);
            PdfWriter.getInstance(doc, fos);
            
            doc.setMargins(100, 100, 108, 180);
            doc.setMarginMirroring(true);
            doc.open();
            
            Paragraph p = new Paragraph();
            p.setAlignment(Paragraph.ALIGN_CENTER);
            p.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));
                        
            p.add("PLT TICARET\n\n");
            p.setExtraParagraphSpace(5);
            p.add("SATIS TARIHI : "+tarih+"\n");
            p.add("SATIN ALAN FIRMA : "+firma+"\n");
            BaseFont freeSans = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf","Cp1254", true);
        Font font = new Font(freeSans,12, Font.NORMAL);
        
            p.add(new Paragraph("SATIN ALANIN ADI VE SOYADI : "+kisi+"\n",font));
            p.add("SATIN ALINAN URUN VE MIKTARI : "+um+"\n");
            p.add("TOPLAM TUTAR : "+fiyat+"\n");
            doc.add(p);
            
            doc.close();
        } catch (Exception e) {e.printStackTrace();
        }
    }
        
}
