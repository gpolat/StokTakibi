package takip;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class DataBase {
    
    String cS = "jdbc:mysql://localhost/stok?characterEncoding=utf-8&useUnicode=true";
    Connection c;
    Statement s;
    ResultSet rs;
    
    public DataBase() 
    {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            c = (Connection) DriverManager.getConnection(cS,"root","");
            c.createStatement().execute("set names utf8");
           
        } catch (Exception e) { e.printStackTrace();
        }
    }
    
    public void getUrunler(StoklaraUrunEkle sue)
    {
        String sql ="";
        try {
            s = c.createStatement();
            
            sql += "select uAd from urunler order by uAd asc";
            
            rs = s.executeQuery(sql);
            while(rs.next())
            {
                sue.jComboBox1.addItem(rs.getString(1));
            }
            s.close();
        } catch (Exception e) { e.printStackTrace();
        }
    }
    
    public void getUrunler2(MevcutMusteriler mm)
    {
        String sql ="";
        try {
            s = c.createStatement();
            
            sql += "select uAd from urunler order by uAd asc";
            
            rs = s.executeQuery(sql);
            while(rs.next())
            {
                mm.jComboBox1.addItem(rs.getString(1));
            }
            s.close();
        } catch (Exception e) { e.printStackTrace();
        }
    }
    
    public DefaultTableModel getMusteriler(MevcutMusteriler mm)
    {
        String kolonAdlari[] = { "AD", "FİRMA", "ADRES", "TELEFON" };
        DefaultTableModel dtm = new DefaultTableModel(kolonAdlari, 0);
        try {
            s = c.createStatement();
            rs = s.executeQuery("select * from musteriler");
            while(rs.next())
            {
                Object[] o = 
                {
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                };
                dtm.addRow(o);
            }
            rs.close();
            s.close();
        } catch (Exception e) { e.printStackTrace();
        }
        return dtm;
    }
    
    public boolean urunEkle(String uAd, double uStok, double uFiyat)
    {
        String sql = "";
        ArrayList<String> al = new ArrayList<String>();
        try {
            s = c.createStatement();
            
            rs = s.executeQuery("select uAd from urunler");
            while(rs.next())
            {
                al.add(rs.getString(1));
            }
            if(al.contains(uAd))
                return false;
                  
            sql += "insert into urunler values (0,'"+uAd+"',"+uStok+","+uFiyat+")";
            
            s.execute(sql);
            s.close();
            return true;
        } catch (Exception e) { return false;
        }
    }
    
    public boolean stokEkle(String uAd, double uStok)
    {
        String sql = "";
        
        try {
            s = c.createStatement();
            
            sql += "update urunler set uStok=uStok+"+uStok+" where uAd='"+uAd+"'";
            
            s.execute(sql);
            s.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); 
            return false;
        }
    }
    
    public void musteriEkle(String mAdi, String mFirma, String mAdres, String mTel)
    {
        String sql = "";
        
        try {
            s = c.createStatement();
            
            sql += "insert into musteriler values (0,'"+mAdi+"','"+mFirma+"','"+mAdres+"','"+mTel+"')";
            
            s.execute(sql);
            s.close();
        } catch (Exception e) { e.printStackTrace();
        }
    }
    
    public boolean satisYap(String mName, String uName, Double sMiktari)
    {
        String sql1 = "",sql2 = "";
        String x="",y="",z="";
        try {
            s = c.createStatement();
            
            ResultSet rs1 = s.executeQuery("select id from musteriler where mAdi = '"+mName+"'");
            while(rs1.next())
            {
                x = rs1.getString(1);
            }
            ResultSet rs2 = s.executeQuery("select id from urunler where uAd='"+uName+"'");
            while(rs2.next())
            {
                y = rs2.getString(1);
            }
            ResultSet rs3 = s.executeQuery("select uFiyat from urunler where uAd='"+uName+"'");
            while(rs3.next())
            {
                z = rs3.getString(1);
            }
            int m_id = Integer.parseInt(x);
            int u_id = Integer.parseInt(y);
            double u_fiyat = Double.parseDouble(z);
            
            rs = s.executeQuery("select uStok from urunler where id="+u_id);
            while(rs.next())
            {
                if(Double.parseDouble(rs.getString(1))<sMiktari)
                    return false;
            }
            
            sql1 += "insert into satislar values (0,"+m_id+","+u_id+","+sMiktari+","+(sMiktari*u_fiyat)+",now()); ";
            
            sql2 += "update urunler set uStok = uStok - "+sMiktari+" where id = "+u_id+";";
            
            s.execute(sql1);
            s.execute(sql2);
            s.close();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false;
        }
    }
    
    public void musteriSil(String mName)
    {
        String sql = "",x = "";
        try {
            s = c.createStatement();
            
            ResultSet rs1 = s.executeQuery("select id from musteriler where mAdi = '"+mName+"'");
            while(rs1.next())
            {
                x = rs1.getString(1);
            }
            
            int id = Integer.parseInt(x);
            
            sql += "delete from musteriler where id = "+id;
            
            s.execute(sql);
            s.close();
            
        } catch (Exception e) { e.printStackTrace();
        }
    }
    
    public DefaultTableModel getSatislar(GecmisSatislar gs)
    {
        String kolonAdlari[] = { "Satış Tarihi", "Satın Alan Firma", "Satın Alan Kişi", "Satılan Ürün ve Miktarı", "Satış Fiyatı"};
        DefaultTableModel dtm = new DefaultTableModel(kolonAdlari,0);
        try {
            s = c.createStatement();
            rs = s.executeQuery(
                    "  select s.sTarihi, m.mFirma, m.mAdi, concat(u.uAd, '   ', s.sMiktari, ' kilo'), concat(s.toplamFiyat, '  TL') "
                    + "from satislar s, musteriler m, urunler u "
                    + "where m.id = s.m_id and u.id = s.u_id");
            while(rs.next())
            {
                Object[] o = 
                {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                };
                dtm.addRow(o);
            }
            
            
            rs.close();
            s.close();
        } catch (Exception e) { e.printStackTrace();
        }
        return dtm;
    }
            
}
