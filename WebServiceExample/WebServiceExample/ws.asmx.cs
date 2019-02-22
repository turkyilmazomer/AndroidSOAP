using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.Services;
using WebServiceExample.models;

namespace WebServiceExample
{
    /// <summary>
    /// Summary description for ws
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class ws : System.Web.Services.WebService
    {

        [WebMethod]
        public string GetEmployee()
        {

            Employee[] emps = new Employee[4];        
            emps[0] = new Employee(1000, "Omer Turkyilmaz");
            emps[1] = new Employee(2000, "Ali Veli");
            emps[2] = new Employee(3000, "Ayşe Ak");
            emps[3] = new Employee(4000, "Gül Çiçek");

            // Serializing object to json data  
            JavaScriptSerializer js = new JavaScriptSerializer();
            return js.Serialize(emps);
        }

        [WebMethod]
        public string GetEmployeeById(int EmployeeId)
        {
            Employee employee = new Employee(1000, "Ömer Türkyılmaz");

            // Serializing object to json data  
            JavaScriptSerializer js = new JavaScriptSerializer();
            return js.Serialize(employee);
        }




    }
}
