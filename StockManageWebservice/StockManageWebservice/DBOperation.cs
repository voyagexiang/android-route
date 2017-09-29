using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Collections;
using System.Collections.Generic;
using System.Web.Configuration;
using System.Xml;
using System.Text;

namespace StockManageWebservice
{
    public class DBOperation:IDisposable
    {
        public static SqlConnection sqlCon; 
     
        //将下面的引号之间的内容换成数据库的属性中的连接字符串  
        //private String ConServerStr = "Data Source=zx-PC;Initial Catalog=android;User ID=sde;Password=10086";

        private string ConServerStr = WebConfigurationManager.ConnectionStrings["MyDB"].ConnectionString.ToString();
        public DBOperation()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
            }
        }

        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }


        //test
        public string helloWorld(string adb)
        {
           
            return "helloWorld"+adb;
        }

        //获取点的信息
        public List<string> selectCargoInfor()
        {
            List<string> list = new List<string>();
            try
            {
                if (sqlCon.State == System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }
                string sql = "select * from S where name='zhangsi' or name='zhangwu'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());

                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());

                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {

            }
            return list;
        }
        //获取所有物品的信息
        public List<string> selectAllCargoInfor()
        {
            List<string> list = new List<string>();
            try
            {
                if(sqlCon.State==System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }
                string sql = "select * from S";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());
                    list.Add(reader[4].ToString());
                    list.Add(reader[5].ToString());

                }
                reader.Close();
                cmd.Dispose();
            }
            catch(Exception)
            {
                     
            }
            return list;
        }

        //增加一条物品信息
        public bool insertCargoInfo(string name, int age)
        {
            try
            {
                if(sqlCon.State==System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }
                string sql = "insert into C (name,age) values ('" + name + "'," + age + ")";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        //删除一条物品信息
        public bool deleteCargoInfo(string name)
        {
            try
            {
                if(sqlCon.State==System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }

                string sql = "DELETE FROM C WHERE name ="+"'"+ name+"'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }




        public XmlDocument GetPlanData(int age,int cost,string name )
        {

         
            XmlDocument xmlDoc = new XmlDocument();
            //创建类型声明节点  
            XmlNode node = xmlDoc.CreateXmlDeclaration("1.0", "utf-8", "");
            xmlDoc.AppendChild(node);
            //创建根节点  
            XmlNode root = xmlDoc.CreateElement("points");
            xmlDoc.AppendChild(root);
           
 
            try
            {
                if (sqlCon.State == System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }

                StringBuilder sb = new StringBuilder();
                sb.Append("select * from PlanTable1 where ");
                sb.Append(string.Format("  name = '{0}'", name));
                string sql= getSqlString(name, age, cost);
               // string sql = sb.ToString();
             //   string sql = sql = "select * from T_1 where prodname LIKE '%" & Name & "%'";
              // string sql = "select * from S where ID=1";
              //  string sql = "select * from S where name='zhangsi' or name='zhangwu'";



                SqlCommand cmd = new SqlCommand(sql, sqlCon);

                SqlDataReader reader = cmd.ExecuteReader();

              
                while (reader.Read())
                {

                    XmlNode point = xmlDoc.CreateNode(XmlNodeType.Element, "point", null);
                    CreateNode(xmlDoc, point, "name", reader[1].ToString());
                    CreateNode(xmlDoc, point, "Cost", reader[2].ToString());
                    CreateNode(xmlDoc, point, "Introduction", reader[3].ToString());
                    CreateNode(xmlDoc, point, "Station", reader[4].ToString());
                    CreateNode(xmlDoc, point, "Age", reader[5].ToString());
                    CreateNode(xmlDoc, point, "ID", reader[0].ToString());
                    root.AppendChild(point); 
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception e)
            {
                System.Console.Write(e.Message); 

            }

            return xmlDoc;
        }
        public string getSqlString(string name, int ID_Age, int ID_Cost)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("select * from PlanTable1 where attr=1 ");
            if (name != "")
            {
                sb.Append(string.Format(" and  Subject like '%{0}%'", name));
            }
            if (ID_Cost != null)
            {
                sb.Append(string.Format("and  ID_Cost ={0}", ID_Cost));
            }
            if (ID_Age != null)
            {
                sb.Append(string.Format(" and ID_Age >= '{0}'", ID_Age));
            }
            
          
            return sb.ToString();
        }


        public XmlDocument GetPosition(int id)
        {
            XmlDocument xmlDoc = new XmlDocument();
            //创建类型声明节点  
            XmlNode node = xmlDoc.CreateXmlDeclaration("1.0", "utf-8", "");
            xmlDoc.AppendChild(node);
            //创建根节点  
            XmlNode root = xmlDoc.CreateElement("points");
            xmlDoc.AppendChild(root);


            try
            {
                if (sqlCon.State == System.Data.ConnectionState.Closed)
                {
                    sqlCon.Open();
                }
                string sql = "select * from StationTab3 where SID=" + id;
                //  string sql = "select * from S where name='zhangsi' or name='zhangwu'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();


                while (reader.Read())
                {

                    XmlNode point = xmlDoc.CreateNode(XmlNodeType.Element, "point", null);
                    CreateNode(xmlDoc, point, "Introduction", reader[1].ToString());
                    CreateNode(xmlDoc, point, "X", reader[2].ToString());
                    CreateNode(xmlDoc, point, "Y", reader[3].ToString());
                    CreateNode(xmlDoc, point, "Station", reader[4].ToString());                    
                    CreateNode(xmlDoc, point, "Data", reader[5].ToString());
                    CreateNode(xmlDoc, point, "Subject", reader[6].ToString());
                    CreateNode(xmlDoc, point, "SID", reader[7].ToString());
                    CreateNode(xmlDoc, point, "Time", reader[8].ToString());
                    CreateNode(xmlDoc, point, "name", reader[9].ToString());
                    CreateNode(xmlDoc, point, "Cost", reader[10].ToString());
                    CreateNode(xmlDoc, point, "ID", reader[0].ToString());


                    root.AppendChild(point);
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {

            }

            return xmlDoc;
        }

        public void CreateNode(XmlDocument xmlDoc, XmlNode parentNode, string name, string value)
        {
            XmlNode node = xmlDoc.CreateNode(XmlNodeType.Element, name, null);
            node.InnerText = value;
            parentNode.AppendChild(node);
        }




    }
}