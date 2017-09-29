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

namespace StockManageWebservice
{
    /// <summary>
    /// 一个操作数据库的类，所有对SQLServer的操作都写在这个类中，使用的时候实例化一个然后直接调用就可以
    /// </summary>
    public class DBOperation:IDisposable
    {
        public static SqlConnection sqlCon;  //用于连接数据库

        //将下面的引号之间的内容换成上面记录下的属性中的连接字符串
        //dfasdlfasdfsad
        private String ConServerStr = @"Data Source=BOTTLE-PC;Initial Catalog=StockManage;Integrated Security=True";
        
        //默认构造函数
        public DBOperation()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
            }
        }
         
        //关闭/销毁函数，相当于Close()
        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }
        
        /// <summary>
        /// 获取所有货物的信息
        /// </summary>
        /// <returns>所有货物信息</returns>
        public List<string> selectAllCargoInfor()
        {
            List<string> list = new List<string>();

            try
            {
                string sql = "select * from C";
                SqlCommand cmd = new SqlCommand(sql,sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());

                }

                reader.Close();
                cmd.Dispose();

            }
            catch(Exception)
            {

            }
            return list;
        }

        /// <summary>
        /// 增加一条货物信息
        /// </summary>
        /// <param name="Cname">货物名称</param>
        /// <param name="Cnum">货物数量</param>
        public bool insertCargoInfo(string Cname, int Cnum)
        {
            try
            {
                string sql = "insert into C (Cname,Cnum) values ('" + Cname + "'," + Cnum + ")";
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

        /// <summary>
        /// 删除一条货物信息
        /// </summary>
        /// <param name="Cno">货物编号</param>
        public bool deleteCargoInfo(string Cno)
        {
            try
            {
                string sql = "delete from C where Cno=" + Cno;
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
    }
}